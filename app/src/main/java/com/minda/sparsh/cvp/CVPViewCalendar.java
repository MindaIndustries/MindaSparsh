package com.minda.sparsh.cvp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.ViewCalendarAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.decorators.HighlightWeekendsDecorator;
import com.minda.sparsh.decorators.MySelectorDecorator;
import com.minda.sparsh.decorators.OneDayDecorator;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.services.CVPServices;
import com.minda.sparsh.util.Utility;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CVPViewCalendar extends AppCompatActivity implements OnDateSelectedListener, ViewCalendarAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    @BindView(R.id.meetings_rv)
    RecyclerView meetingsRv;
    @BindView(R.id.calendar_type_spinner)
    AppCompatAutoCompleteTextView calendarTypeSpinner;
    @BindView(R.id.heading)
    TextView heading;
    ArrayList<String> weeksList = new ArrayList<>();
    ArrayList<WeekModel.WeekData> weeks = new ArrayList<>();
    SharedPreferences myPref;
    String empcode,year,month,calendartypeId;
    List<CVPViewCalendarModel.CVPViewCalendarData> meetings = new ArrayList<>();
    ViewCalendarAdapter viewCalendarAdapter;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    ArrayList<CalendarTypeModel.CalendarTypeData> calendarTypes = new ArrayList<>();
    ArrayList<String> calendartypeList = new ArrayList<>();
    ArrayAdapter<String> calendarTypeAdapter;
    String weekId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cvp_viewcalendar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Meetings");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");
        calendarView.setDateSelected(new Date(),true);
        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        calendarView.setSelectionColor(getResources().getColor(R.color.colorPrimary));
        calendarView.state().edit().setMinimumDate(Calendar.getInstance());
        calendarView.addDecorators(
                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
        calendarView.setOnDateChangedListener(this);
        getWeek(year+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        viewCalendarAdapter = new ViewCalendarAdapter(CVPViewCalendar.this,meetings,calendartypeId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CVPViewCalendar.this, LinearLayoutManager.VERTICAL, false);
        meetingsRv.setLayoutManager(mLayoutManager);
        meetingsRv.setAdapter(viewCalendarAdapter);
        viewCalendarAdapter.setClickListener(this);
        initCalendarTypeSpinner();
        getCalendarTypes();

    }

    public void getCalendarMeetings(){
        meetings.clear();
        meetingsRv.getRecycledViewPool().clear();
        viewCalendarAdapter.notifyDataSetChanged();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCalendarMeetings(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    CVPViewCalendarModel meetingTypeModel = (CVPViewCalendarModel) carotResponse.getData();
                    if(meetingTypeModel!=null){
                        List<CVPViewCalendarModel.CVPViewCalendarData> list = meetingTypeModel.getData();
                        if(list!=null && list.size()>0){
                            meetings.addAll(list);
                        }
                        viewCalendarAdapter.notifyDataSetChanged();
                        heading.setText("Week " +meetings.get(0).getWeeks());
                    }

                }

            }
        },empcode,year,calendartypeId,weekId);
    }

    @Override
    public void onDateSelected(@NonNull @NotNull MaterialCalendarView widget, @NonNull @NotNull CalendarDay date, boolean selected) {
        year = String.valueOf(date.getYear());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH,date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        getWeek(date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void initCalendarTypeSpinner(){
        calendarTypeAdapter = new ArrayAdapter<>(CVPViewCalendar.this, android.R.layout.simple_spinner_item,calendartypeList);
        calendarTypeSpinner.setAdapter(calendarTypeAdapter);
        calendarTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=0){
                    calendartypeId = calendarTypes.get(i).getId();
                    viewCalendarAdapter.setCalendarTypeId(calendartypeId);
                    getCalendarMeetings();
                    if(calendartypeList.get(i).equalsIgnoreCase("Monthly Calender")){
                    }
                }
            }
        });
    }

    public void getCalendarTypes(){
        calendarTypes.clear();
        calendartypeList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCalendarTypes(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() ==HttpsURLConnection.HTTP_OK) {
                    CalendarTypeModel calendarTypeModel = (CalendarTypeModel) carotResponse.getData();
                    if(calendarTypeModel!=null){
                        List<CalendarTypeModel.CalendarTypeData> list = calendarTypeModel.getData();
                        if(list!=null && list.size()>0){
                            for (CalendarTypeModel.CalendarTypeData calendarTypeData : list) {
                                if(!calendarTypeData.getId().equalsIgnoreCase("2")){
                                    calendarTypes.add(calendarTypeData);
                                    calendartypeList.add(calendarTypeData.getName());
                                }
                            }
                            calendarTypeAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void getWeek(String date){
        weeks.clear();
        weeksList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getWeekData(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    WeekModel weekModel = (WeekModel) carotResponse.getData();
                    if(weekModel!=null){
                        List<WeekModel.WeekData> list = weekModel.getData();
                        if(list!=null && list.size()>0){
                            weeks.addAll(list);
                            for (WeekModel.WeekData weekData : list) {
                                weeksList.add(weekData.getName());
                            }
                            weekId  = weeks.get(0).getId();
                            getCalendarMeetings();
                        }
                    }
                }
            }
        },date);
    }


    @Override
    public void onClick(View view, int position) {
        showMsgUpdate("","Are you sure you want to delete this plan ?",true,position);
    }

    public void deleteCalendarPlan(String id, String empcode,int pos, String calendarType){
        CVPServices cvpServices = new CVPServices();
        cvpServices.deleteCalendarBooking(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    SaveCalendarResponse saveCalendarResponse = (SaveCalendarResponse) carotResponse.getData();
                    if(saveCalendarResponse!=null && saveCalendarResponse.getData()!=null){
                        showMsgUpdate("Deleted",saveCalendarResponse.getData(),false,0);
                        meetings.remove(pos);
                        viewCalendarAdapter.notifyDataSetChanged();
                    }
                    else{
                        showMsgUpdate("Message",saveCalendarResponse.getData(),false,0);

                    }
                }
                }
        },id,empcode,calendarType);
    }
    public void showMsgUpdate(String title, String message,boolean action, int pos) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            if(action){
                if(Utility.isOnline(CVPViewCalendar.this)){
                    deleteCalendarPlan(String.valueOf(meetings.get(pos).getMID()), empcode,pos,calendartypeId);
                }
            }
            arg0.dismiss();
        });
        if(action) {
            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
