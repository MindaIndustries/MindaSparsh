package com.minda.sparsh.cvp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.ViewCalendarAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.services.CVPServices;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CVPViewCalendar extends AppCompatActivity {
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    @BindView(R.id.meetings_rv)
    RecyclerView meetingsRv;
    SharedPreferences myPref;
    String empcode,year,month,calendarType;
    List<CVPViewCalendarModel.CVPViewCalendarData> meetings = new ArrayList<>();
    ViewCalendarAdapter viewCalendarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cvp_viewcalendar);
        ButterKnife.bind(this);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");
        year = "2022";
        month="January";
        calendarType="1";
        viewCalendarAdapter = new ViewCalendarAdapter(CVPViewCalendar.this,meetings);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CVPViewCalendar.this, LinearLayoutManager.VERTICAL, false);
        meetingsRv.setLayoutManager(mLayoutManager);
        meetingsRv.setAdapter(viewCalendarAdapter);

        getCalendarMeetings();

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
                    }

                }

            }
        },empcode,year,month,calendarType);
    }
}
