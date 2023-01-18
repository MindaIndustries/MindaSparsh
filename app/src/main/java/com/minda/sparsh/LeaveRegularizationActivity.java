package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.services.AlmsServices;

import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveRegularizationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    DatePickerDialog datePicker, datePicker1;
    Calendar calendar, calendar1;
    String year, year1;
    TextInputEditText start_date, end_date,no_of_days;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_regular);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Regularization");
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        no_of_days = findViewById(R.id.no_of_days);
        initDatePicker();
        initDatePicker1();


        start_date.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });
        end_date.setOnTouchListener((view, motionEvent) -> {
            datePicker1.show();
            return false;
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

        //   start_date.setText(dayOfMonthStr+"-"+monthNo+"-"+calendar.get(Calendar.YEAR));
        datePicker = new DatePickerDialog(LeaveRegularizationActivity.this, (datePicker, i, i1, i2) -> {
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
            year = String.valueOf(calendar.get(Calendar.YEAR));
            start_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            end_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
          /*  leave_type.setText("");
            leaveTypeAdapter.getFilter().filter(null);
            leaveType="";
            no_of_days.setText("");
            available_bal.setText("");
      */  },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }
    public void initDatePicker1(){
        calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        int mMonth = calendar1.get(Calendar.MONTH) + 1;
        String monthNo;
        if (mMonth < 10) {
            monthNo = "0" + mMonth;
        } else {
            monthNo = "" + mMonth;
        }
        String dayOfMonthStr;
        if (calendar1.get(Calendar.DAY_OF_MONTH) < 10) {
            dayOfMonthStr = "0" + calendar1.get(Calendar.DAY_OF_MONTH);
        } else {
            dayOfMonthStr = "" + calendar1.get(Calendar.DAY_OF_MONTH);
        }
        year1 = String.valueOf(calendar1.get(Calendar.YEAR));

        //   start_date.setText(dayOfMonthStr+"-"+monthNo+"-"+calendar.get(Calendar.YEAR));
        datePicker1 = new DatePickerDialog(LeaveRegularizationActivity.this, (datePicker, i, i1, i2) -> {
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
            calendar1.set(Calendar.DAY_OF_MONTH, i2);
            calendar1.set(Calendar.MONTH, i1);
            calendar1.set(Calendar.YEAR, i);
            year1 = String.valueOf(calendar1.get(Calendar.YEAR));
            end_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
          /*  leave_type.setText("");
            leaveType="";
            leaveTypeAdapter.getFilter().filter(null);
            no_of_days.setText("");
            available_bal.setText("");
      */  },calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

        //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTotalLeaveDays(String leaveType,String fromDate, String toDate){
        AlmsServices almsServices = new AlmsServices();
        almsServices.getTotalLeaveDays(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                    List<LeaveDaysResponse> list = (List<LeaveDaysResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        no_of_days.setText(""+list.get(0).getCount());
                    }
                  }
            }
        }, leaveType, fromDate,toDate);

    }

}
