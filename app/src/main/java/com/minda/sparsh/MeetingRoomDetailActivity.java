package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.minda.sparsh.Adapter.MeetingRoomDetailAdapter;
import com.minda.sparsh.cvp.CVPPlanCalendar;
import com.minda.sparsh.cvp.CVPViewCalendar;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.MeetingRoomDetailData;
import com.minda.sparsh.services.MeetingRoomServices;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomDetailActivity extends AppCompatActivity {
    int meetingRoomId;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    AppCompatAutoCompleteTextView date;
    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.book)
    Button bookSelectedSlots;
    String meetingRoomName;
    SharedPreferences myPref;
    String EmpCode,unitCode;
    DatePickerDialog datePicker;
    Calendar calendar, calendar1;
    String year;
    String datesave;
    MeetingRoomDetailAdapter meetingRoomDetailAdapter;
    ArrayList<MeetingRoomDetailData.MeetingRoomDetailDataModel> slots = new ArrayList<>();
    boolean roomType;
    boolean btnEnable;
    ProgressDialog progressDialog;
    int slotId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_room_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        EmpCode = myPref.getString("Id", "");
        unitCode =myPref.getString("Um_div_code","");
        meetingRoomDetailAdapter = new MeetingRoomDetailAdapter(MeetingRoomDetailActivity.this,slots);
        gridView.setAdapter(meetingRoomDetailAdapter);
        progressDialog = new ProgressDialog(MeetingRoomDetailActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if(getIntent().getIntExtra("meetingRoomId",0)!=0){
            meetingRoomId = getIntent().getIntExtra("meetingRoomId",0);
            meetingRoomName = getIntent().getStringExtra("meetingRoomName");
            roomType = getIntent().getBooleanExtra("vc",false);
            title.setText(meetingRoomName);
        }
        initDatePicker();
        date.setOnTouchListener((view, motionEvent) -> {
                datePicker.show();
            return false;
        });
        bookSelectedSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnEnable) {
                    Intent in = new Intent(MeetingRoomDetailActivity.this, BookMeetingRoom.class);
                    in.putExtra("date",date.getText().toString());
                    in.putExtra("dateforapi",datesave);
                    in.putExtra("slotId",slotId);
                    in.putExtra("meetingRoomId",meetingRoomId);
                    in.putExtra("vc",roomType);
                    startActivity(in);
                }
                else{
                    Toast.makeText(MeetingRoomDetailActivity.this,"Please reserve a slot", Toast.LENGTH_LONG).show();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(slots.get(i).getBkngStatus().equals("Ready to Book")){
                    progressDialog.show();
                    takeActionForMeetingRoom(""+slots.get(i).getMeetingTimeLnkID(),slots.get(i).getMeetingRoomID(),EmpCode,datesave,"","","","Ready to Book","",unitCode,roomType);
                }
                else if(slots.get(i).getBkngStatus().equals("Reserved")){
                    takeActionForMeetingRoom(""+slots.get(i).getMeetingTimeLnkID(),slots.get(i).getMeetingRoomID(),EmpCode,datesave,"","","","Reserved","",unitCode,roomType);
                }
                slotId = slots.get(i).getMeetingTimeLnkID();
                meetingRoomId = slots.get(i).getMeetingRoomID();
            }
        });
        getMeetingRoomDetail(EmpCode,"DateChng",datesave,meetingRoomId);
      /*  finish();
        startActivity(getIntent());
  */  }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void getMeetingRoomDetail(String EmpCode, String ActType, String BookingDate, int MeetingRoomID){
        slots.clear();
        meetingRoomDetailAdapter.notifyDataSetChanged();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getMeetingRoomDetail(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    MeetingRoomDetailData meetingRoomDetailData = (MeetingRoomDetailData) carotResponse.getData();
                    if(meetingRoomDetailData!=null) {
                        List<MeetingRoomDetailData.MeetingRoomDetailDataModel> list = meetingRoomDetailData.getData();
                        if (list != null && list.size() > 0) {
                            slots.addAll(list);
                        }
                        meetingRoomDetailAdapter.notifyDataSetChanged();
                    }
                }
                for(int i=0;i<slots.size();i++){
                    if(!slots.get(i).getBkngStatus().equals("Reserved")){
                        btnEnable = false;
                      }
                    else{
                        btnEnable = true;
                        return;
                    }
                }
            }
        },EmpCode,ActType,BookingDate,MeetingRoomID);

    }
    public void initDatePicker() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.add(Calendar.DAY_OF_MONTH,24);
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

        date.setText(dayOfMonthStr + "-" + monthNo + "-" + calendar.get(Calendar.YEAR));
        datesave = calendar.get(Calendar.YEAR) + "-" + monthNo + "-" + dayOfMonthStr;
        datePicker = new DatePickerDialog(MeetingRoomDetailActivity.this, (datePicker, i, i1, i2) -> {
            int mMonth1 = i1 + 1;
            String monthNo1;
            if (mMonth1 < 10) {
                monthNo1 = "0" + mMonth1;
            } else {
                monthNo1 = "" + mMonth1;
            }
            String dayOfMonthStr1;
            if (i2 < 10) {
                dayOfMonthStr1 = "0" + i2;
            } else {
                dayOfMonthStr1 = "" + i2;
            }
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.YEAR, i);
            date.setText("" + dayOfMonthStr1 + "-" + monthNo1 + "-" + i);
            datesave = i +"-"+ monthNo1 +"-"+ dayOfMonthStr1;
            getMeetingRoomDetail(EmpCode,"DateChng",datesave,meetingRoomId);
            year = String.valueOf(i);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);
        datePicker.getDatePicker().setMaxDate(calendar1.getTimeInMillis() - 10000);
    }


    public void takeActionForMeetingRoom(String MeetingRoomSlotID,int MeetingRoomID,String EmpCode,String BookingDate, String Purpose, String AttendeeInt, String AttendeeExt,String ActType,String MeetingID, String UniteCode,boolean RoomType){
        gridView.setEnabled(false);
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.takeActionForMeetingRoom(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                gridView.setEnabled(true);
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    getMeetingRoomDetail(EmpCode,"",datesave,meetingRoomId);
                }
                else if(carotResponse.getStatuscode() == 406){
                    showMsgUpdate("Error",""+carotResponse.getData());
                }
                progressDialog.dismiss();
            }
        },MeetingRoomSlotID,MeetingRoomID,EmpCode,BookingDate,Purpose,AttendeeInt,AttendeeExt,ActType,MeetingID,UniteCode,RoomType);
}
    public void showMsgUpdate(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
                 arg0.dismiss();
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
