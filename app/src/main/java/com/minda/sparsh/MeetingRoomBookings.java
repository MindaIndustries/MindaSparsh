package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.MeetingRoomBookingAdapter;
import com.minda.sparsh.model.MeetingRoomBookData;
import com.minda.sparsh.services.MeetingRoomServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
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
    @BindView(R.id.dateFrom)
    AppCompatAutoCompleteTextView dateFrom;
    @BindView(R.id.dateTo)
    AppCompatAutoCompleteTextView dateTo;
    @BindView(R.id.search)
    Button search;
    DatePickerDialog datePicker, datePicker1;
    ArrayList<MeetingRoomBookData.MeetingRoomBookDataModel> bookingsList = new ArrayList<>();
    MeetingRoomBookingAdapter meetingRoomBookingAdapter;
    SharedPreferences myPref;
    String EmpCode, userunitCode;
    ProgressDialog progressDialog;
    Calendar calendar;
    Calendar calendarTo, calendarTo1;
    String year, year1, dateFromString = "", dateToString = "", depucode;


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
        userunitCode = myPref.getString("Um_div_code", "");
        depucode = myPref.getString("Depu_UnitCode", "");
        if (depucode != null && depucode.length() > 0) {
            userunitCode = depucode;
        }

        meetingRoomBookingAdapter = new MeetingRoomBookingAdapter(MeetingRoomBookings.this, bookingsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MeetingRoomBookings.this, LinearLayoutManager.VERTICAL, false);
        bookings.setLayoutManager(mLayoutManager);
        bookings.setAdapter(meetingRoomBookingAdapter);
        getBookedMeetings(EmpCode, "", "", "", "", userunitCode, "SD");
        initDatePickerFrom();
        initDatePickerTo();

        dateFrom.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });
        dateTo.setOnTouchListener((view, motionEvent) -> {
            datePicker1.show();
            return false;
        });
        search.setOnClickListener(view -> getBookedMeetings(EmpCode, dateFromString, dateToString, "", "", userunitCode, "SD"));
    }

    public void initDatePickerFrom() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //  calendar1 = Calendar.getInstance();
        // calendar1.setTimeInMillis(System.currentTimeMillis());
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

        //  dateFrom.setText(dayOfMonthStr + "-" + monthNo + "-" + calendar.get(Calendar.YEAR));
        //dateFromString = calendar.get(Calendar.YEAR) + "-" + monthNo + "-" + dayOfMonthStr;
        datePicker = new DatePickerDialog(MeetingRoomBookings.this, (datePicker, i, i1, i2) -> {
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
            dateFrom.setText("" + dayOfMonthStr1 + "-" + monthNo1 + "-" + i);
            dateFromString =  dayOfMonthStr1+"/"+monthNo1+"/"+i;
            year = String.valueOf(i);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
      //  datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 10000);
    }

    public void initDatePickerTo() {
        calendarTo = Calendar.getInstance();
        calendarTo.setTimeInMillis(System.currentTimeMillis());
        calendarTo1 = Calendar.getInstance();
        calendarTo1.setTimeInMillis(System.currentTimeMillis());
        int mMonth = calendarTo.get(Calendar.MONTH) + 1;
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
        year1 = String.valueOf(calendar.get(Calendar.YEAR));

        //  dateFrom.setText(dayOfMonthStr + "-" + monthNo + "-" + calendar.get(Calendar.YEAR));
        //dateFromString = calendar.get(Calendar.YEAR) + "-" + monthNo + "-" + dayOfMonthStr;
        datePicker1 = new DatePickerDialog(MeetingRoomBookings.this, (datePicker, i, i1, i2) -> {
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
            calendarTo.set(Calendar.DAY_OF_MONTH, i2);
            calendarTo.set(Calendar.MONTH, i1);
            calendarTo.set(Calendar.YEAR, i);
            dateTo.setText("" + dayOfMonthStr1 + "-" + monthNo1 + "-" + i);
            dateToString = dayOfMonthStr1 + "/" + monthNo1 + "/" + i;
            year1 = String.valueOf(i);
        }, calendarTo.get(Calendar.YEAR), calendarTo.get(Calendar.MONTH), calendarTo.get(Calendar.DAY_OF_MONTH));
       // datePicker1.getDatePicker().setMaxDate(calendarTo.getTimeInMillis() - 10000);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getBookedMeetings(String EmpCode, String FromDate, String ToDate, String SearchParameter, String SearchFor, String CompId, String SortBy) {
        bookingsList.clear();
        bookings.getRecycledViewPool().clear();
        meetingRoomBookingAdapter.notifyDataSetChanged();
        progressDialog.show();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getBookedMeetings(carotResponse -> {
            progressDialog.dismiss();
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                MeetingRoomBookData meetingRoomBookData = (MeetingRoomBookData) carotResponse.getData();
                if (meetingRoomBookData != null) {
                    List<MeetingRoomBookData.MeetingRoomBookDataModel> list = (List<MeetingRoomBookData.MeetingRoomBookDataModel>) meetingRoomBookData.getData();
                    if (list != null && list.size() > 0) {
                        bookingsList.addAll(list);
                        no_bookings.setVisibility(View.GONE);

                    } else {
                        no_bookings.setVisibility(View.VISIBLE);
                    }
                    meetingRoomBookingAdapter.notifyDataSetChanged();
                }
            } else {
                no_bookings.setVisibility(View.VISIBLE);

            }
        }, EmpCode, FromDate, ToDate, SearchParameter, SearchFor, CompId, SortBy);
    }
}
