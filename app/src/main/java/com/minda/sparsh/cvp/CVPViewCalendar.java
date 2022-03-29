package com.minda.sparsh.cvp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.Adapter.ViewCalendarAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.decorators.HighlightWeekendsDecorator;
import com.minda.sparsh.decorators.MySelectorDecorator;
import com.minda.sparsh.decorators.OneDayDecorator;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.model.YearModel;
import com.minda.sparsh.services.CVPServices;
import com.minda.sparsh.util.Utility;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

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
    @BindView(R.id.customerSpinnerLayout)
    TextInputLayout customerSpinnerLayout;
    @BindView(R.id.week_spinner)
    AppCompatAutoCompleteTextView weekSpinner;
    @BindView(R.id.customSpinnerLayout1)
    TextInputLayout datelayout;
    @BindView(R.id.date)
    AppCompatAutoCompleteTextView date;

    DatePickerDialog datePicker;
    Calendar calendar;

    TextView textView;
    ArrayList<String> weeksList = new ArrayList<>();
    ArrayList<WeekModel.WeekData> weeks = new ArrayList<>();
    SharedPreferences myPref;
    String empcode,year,month,calendartypeId;
    List<CVPViewCalendarModel.CVPViewCalendarData> meetings = new ArrayList<>();
    ViewCalendarAdapter viewCalendarAdapter;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    ArrayList<CalendarTypeModel.CalendarTypeData> calendarTypes = new ArrayList<>();
    ArrayList<String> calendartypeList = new ArrayList<>();
    ArrayAdapter<String> calendarTypeAdapter,weekAdapter;
    String weekId;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int weekIdMin=1, weekIdMax=53;
    MonthPickerDialog.Builder builder;
    ArrayList<YearModel.YearModelData> years = new ArrayList<>();
    int currentWeekId;

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
        month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        calendarView.setSelectionColor(getResources().getColor(R.color.colorPrimary));
       // calendarView.state().edit().setMinimumDate(Calendar.getInstance());
        calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).commit();
        initDatePicker();
        getYear();
        initWeekSpinner();
        calendarView.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","test"+ view.getId());
                 textView = (TextView) view;
                 builder.build().show();

            }
        });
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                datePicker.show();
                return false;
            }
        });


        calendarView.addDecorators(
                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
        calendarView.setOnDateChangedListener(this);

        initCalendarTypeSpinner();
        getCalendarTypes();
       // getWeek("");
      //  getWeekSelected(year+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        viewCalendarAdapter = new ViewCalendarAdapter(CVPViewCalendar.this,meetings,calendartypeId);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CVPViewCalendar.this, LinearLayoutManager.VERTICAL, false);
        meetingsRv.setLayoutManager(mLayoutManager);
        meetingsRv.setAdapter(viewCalendarAdapter);
        viewCalendarAdapter.setClickListener(this);

      /*  meetingsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            loading = true;
                            if(weekIdMin <= weekIdMax)
                            getCalendarMeetingsDynamicWeek(String.valueOf(weekIdMin));
                        }
                    }
                }
                else{
                    Log.v("...", "First Item Wow !");

                }
            }
        });
*/
    }

    public void initWeekSpinner() {
        weekAdapter = new ArrayAdapter<String>(CVPViewCalendar.this, android.R.layout.simple_spinner_item, weeksList);
        weekSpinner.setAdapter(weekAdapter);
        weekSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    weekId = weeks.get(i).getWeeks();
                }
                getCalendarMeetingsCurrentWeek();

            }
        });
    }
    public void initDatePicker(){
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        String monthNo;
        if (mMonth < 10) {
            monthNo = "0" + mMonth;
        } else {
            monthNo = "" + mMonth;
        }
        String dayOfMonthStr;
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dayOfMonthStr = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            dayOfMonthStr = "" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        year = String.valueOf(calendar.get(Calendar.YEAR));

        date.setText(dayOfMonthStr+"-"+monthNo+"-"+calendar.get(Calendar.YEAR));
        getWeek(calendar.get(Calendar.YEAR)+"-"+monthNo+"-"+dayOfMonthStr);
        datePicker = new DatePickerDialog(CVPViewCalendar.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int mMonth = i1 + 1;
                String monthNo;
                if (mMonth < 10) {
                    monthNo = "0" + mMonth;
                } else {
                    monthNo = "" + mMonth;
                }
                String dayOfMonthStr;
                if (i2 < 10) {
                    dayOfMonthStr = "0" + i2;
                } else {
                    dayOfMonthStr = "" + i2;
                }
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.YEAR, i);
                year = String.valueOf(calendar.get(Calendar.YEAR));
                getWeek(i+"-"+monthNo+"-"+dayOfMonthStr);
                date.setText("" + dayOfMonthStr + "-" + monthNo + "-" + i);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

      //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }

    public void getCalendarMeetingsCurrentWeek(){
        ProgressDialog progressDialog = new ProgressDialog(CVPViewCalendar.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if(weekId!=null && weekId.length()>0) {
            meetings.clear();
            meetingsRv.getRecycledViewPool().clear();
            viewCalendarAdapter.notifyDataSetChanged();
            CVPServices cvpServices = new CVPServices();
            cvpServices.getCalendarMeetings(carotResponse -> {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    CVPViewCalendarModel meetingTypeModel = (CVPViewCalendarModel) carotResponse.getData();
                    if (meetingTypeModel != null) {
                        List<CVPViewCalendarModel.CVPViewCalendarData> list = meetingTypeModel.getData();
                        if (list != null && list.size() > 0) {
                            meetings.addAll(list);
                        }
                  /*  if (list != null && list.size()>0 && list.size() < 3){
                        int week = Integer.parseInt(weekId) + 1;
                    weekId = "" + week;
                    weekIdMin = Integer.parseInt(weekId);
                    getCalendarMeetingsDynamicWeek(weekId);
                }*/

                        viewCalendarAdapter.notifyDataSetChanged();

                           heading.setText("Week " +meetings.get(0).getWeeks());
                    }

                }
                progressDialog.dismiss();

            }, empcode, year, calendartypeId, weekId);
        }
    }

    public void getCalendarMeetingsDynamicWeek(final String weekId){
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCalendarMeetings(carotResponse -> {
            if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                CVPViewCalendarModel meetingTypeModel = (CVPViewCalendarModel) carotResponse.getData();
                if(meetingTypeModel!=null){
                    List<CVPViewCalendarModel.CVPViewCalendarData> list = meetingTypeModel.getData();
                    if(list!=null && list.size()>0){
                        meetings.addAll(list);
                    }
                    if (list != null && list.size()>0 && list.size() < 3) {
                        int week = Integer.parseInt(weekId) + 1;
                        String weekid = String.valueOf(week);
                        weekIdMin = Integer.parseInt(weekid);
                        if(weekIdMin <= weekIdMax) {
                            getCalendarMeetingsDynamicWeek(String.valueOf(weekIdMin));
                        }
                    //    getCalendarMeetingsDynamicWeek(weekid);
                        calendarView.goToNext();
                    }
                        viewCalendarAdapter.notifyDataSetChanged();
                 //   heading.setText("Week " +meetings.get(0).getWeeks());
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
        getWeekSelected(date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay());
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
        calendarTypeSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
            if(i>=0){
                calendartypeId = calendarTypes.get(i).getId();
                viewCalendarAdapter.setCalendarTypeId(calendartypeId);
                getCalendarMeetingsCurrentWeek();
                if(calendartypeList.get(i).equalsIgnoreCase("Monthly Calender")){
                }
            }
        });
    }

    public void getCalendarTypes(){
        calendarTypes.clear();
        calendartypeList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCalendarTypes(carotResponse -> {
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
                        calendarTypeSpinner.setText(""+calendartypeList.get(0));
                        calendartypeId = calendarTypes.get(0).getId();
                        viewCalendarAdapter.setCalendarTypeId(calendartypeId);
                        calendarTypeAdapter.getFilter().filter(null);
                        getCalendarMeetingsCurrentWeek();
                        calendarTypeAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void getWeek(String date){
        weeks.clear();
        weeksList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getWeekData(carotResponse -> {
            if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                WeekModel weekModel = (WeekModel) carotResponse.getData();
                if(weekModel!=null){
                    List<WeekModel.WeekData> list = weekModel.getData();
                    if(list!=null && list.size()>0){
                        weeks.addAll(list);
                        for (WeekModel.WeekData weekData : list) {
                            weeksList.add(weekData.getName());
                        }

                        weekAdapter = new ArrayAdapter<String>(CVPViewCalendar.this, android.R.layout.simple_spinner_item, weeksList);
                        weekSpinner.setAdapter(weekAdapter);
                        weekAdapter.notifyDataSetChanged();
                        weekSpinner.setText(list.get(0).getName());
                        weekId = list.get(0).getWeeks();
                        weekAdapter.getFilter().filter(null);
                        getCalendarMeetingsCurrentWeek();
                    }
                }
            }
        },date);
    }
    public void getWeekSelected(String date){
        CVPServices cvpServices = new CVPServices();
        cvpServices.getWeekData(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    WeekModel weekModel = (WeekModel) carotResponse.getData();
                    if (weekModel != null) {
                        List<WeekModel.WeekData> list = weekModel.getData();
                        if (list != null && list.size() > 0) {
                            weekSpinner.setText(list.get(0).getName());
                            weekId = list.get(0).getWeeks();
                            weekAdapter.getFilter().filter(null);
                            getCalendarMeetingsCurrentWeek();
                        }
                    }
                }
            }
        }, date);
    }


    @Override
    public void onClick(View view, int position) {
        showMsgUpdate("","Are you sure you want to delete this plan ?",true,position);
    }

    public void deleteCalendarPlan(String id, String empcode,int pos, String calendarType){
        CVPServices cvpServices = new CVPServices();
        cvpServices.deleteCalendarBooking(carotResponse -> {
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
            alertDialogBuilder.setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss());
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void initMonthPicker(int minYear, int maxYear){
        Calendar today = Calendar.getInstance();
         builder = new MonthPickerDialog.Builder(CVPViewCalendar.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                Log.d("TAG", "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
               // Toast.makeText(CVPViewCalendar.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                Calendar mcal = Calendar.getInstance();
                mcal.set(Calendar.MONTH,selectedMonth);
                mcal.set(Calendar.YEAR,selectedYear);
                mcal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                calendarView.setCurrentDate(CalendarDay.from(new Date(mcal.getTimeInMillis())),true);
                calendarView.setDateSelected(new Date(mcal.getTimeInMillis()),true);
                textView.setText(mcal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +" "+ mcal.get(Calendar.YEAR));

                year = String.valueOf(selectedYear);
                getWeek(year+"-"+(selectedMonth+1)+"-"+mcal.get(Calendar.DAY_OF_MONTH));

                 }
        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(minYear)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(maxYear)
               // .setMinMonth(Calendar.FEBRUARY)
               // .setTitle("")
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                // .setMaxMonth(Calendar.OCTOBER)
                // .setYearRange(1890, 1890)
                // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                //.showMonthOnly()
                // .showYearOnly()
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        Log.d("TAG1", "Selected month : " + selectedMonth);
                        // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) {
                        Log.d("TAG3", "Selected year : " + selectedYear);
                        // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void getYear(){
        years.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getYear(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    YearModel yearModel = (YearModel) carotResponse.getData();
                    if(yearModel!=null){
                        List<YearModel.YearModelData> list = yearModel.getData();
                        if(list!=null && list.size()>0){
                            years.addAll(list);
                            Calendar calendar = Calendar.getInstance();
                            Calendar calendar1 = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH,1);
                            calendar.set(Calendar.MONTH,0);
                            calendar.set(Calendar.YEAR, Integer.parseInt(years.get(0).getYear()));
                            calendar1.set(Calendar.YEAR, Integer.parseInt(years.get(years.size()-1).getYear()));
                            calendar1.set(Calendar.DAY_OF_MONTH,31);
                            calendar1.set(Calendar.MONTH,11);

                            datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis()-10000);
                            datePicker.getDatePicker().setMaxDate(calendar1.getTimeInMillis()-10000);
                            datePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);

                          //  initMonthPicker(Integer.parseInt(years.get(0).getYear()),Integer.parseInt(years.get(years.size()-1).getYear()));

                        }
                    }
                }

            }
        });

    }

}
