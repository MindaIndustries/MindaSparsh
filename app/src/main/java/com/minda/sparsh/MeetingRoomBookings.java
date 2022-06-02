package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.MeetingRoomBookingAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.MeetingRoomBookData;
import com.minda.sparsh.services.MeetingRoomServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomBookings extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bookings)
    RecyclerView bookings;
    @BindView(R.id.no_bookings)
    TextView no_bookings;
    ArrayList<MeetingRoomBookData.MeetingRoomBookDataModel> bookingsList = new ArrayList<>();
    MeetingRoomBookingAdapter meetingRoomBookingAdapter;
    SharedPreferences myPref;
    String EmpCode,userunitCode;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_rooms_booked);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Booked Meetings");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        EmpCode = myPref.getString("Id", "Id");
        userunitCode =myPref.getString("Um_div_code","");
        meetingRoomBookingAdapter = new MeetingRoomBookingAdapter(MeetingRoomBookings.this,bookingsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MeetingRoomBookings.this, LinearLayoutManager.VERTICAL, false);
        bookings.setLayoutManager(mLayoutManager);
        bookings.setAdapter(meetingRoomBookingAdapter);
        getBookedMeetings(EmpCode,"","","","",userunitCode,"SD");

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getBookedMeetings(String EmpCode,String FromDate, String ToDate, String SearchParameter, String SearchFor, String CompId, String SortBy){
        progressDialog.show();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getBookedMeetings(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                progressDialog.dismiss();
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    MeetingRoomBookData meetingRoomBookData = (MeetingRoomBookData) carotResponse.getData();
                    if(meetingRoomBookData!=null){
                        List<MeetingRoomBookData.MeetingRoomBookDataModel> list = (List<MeetingRoomBookData.MeetingRoomBookDataModel>) meetingRoomBookData.getData();
                        if(list!=null && list.size()>0) {
                            bookingsList.addAll(list);
                        }
                        else{
                            no_bookings.setVisibility(View.VISIBLE);
                        }
                        meetingRoomBookingAdapter.notifyDataSetChanged();
                    }
                }
            }
        },EmpCode, FromDate,ToDate,SearchParameter,SearchFor,CompId,SortBy);
    }
}
