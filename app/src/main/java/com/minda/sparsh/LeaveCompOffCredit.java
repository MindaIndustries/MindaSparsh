package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.CheckCompOffValidation;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveValidationResponse;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveCompOffCredit extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    SharedPreferences myPref;

    TextInputEditText startDate, endDate, comment,days;
    Button submit, cancel;
    Calendar calendar, calendar1;
    DatePickerDialog datePicker, datePicker1;
    String year, year1;
    String empCode;
    String authperson, reportyEmailId, reportyEmpName, EmpName;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_comp_off);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        startDate  = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        comment = findViewById(R.id.comment);
        days = findViewById(R.id.days);
        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        progressDialog = new ProgressDialog(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Comp Off Credit Application");
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        EmpName = myPref.getString("username", "");
        authperson = myPref.getString("REPORTY_EMP_CODE", "");
        reportyEmailId = myPref.getString("REPORTY_EMAIL", "");
        reportyEmpName = myPref.getString("REPORTY_EMP_NAME", "");

        initDatePicker();
        initDatePicker1();
        startDate.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });
        endDate.setOnTouchListener((view, motionEvent) -> {
            datePicker1.show();
            return false;
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate.getText().toString().length() == 0) {
                    Toast.makeText(LeaveCompOffCredit.this, "Please select Start Date", Toast.LENGTH_LONG).show();
                    return;

                } else if (endDate.getText().toString().length() == 0) {
                    Toast.makeText(LeaveCompOffCredit.this, "Please select End Date", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(comment.getText().toString().length() == 0){
                    Toast.makeText(LeaveCompOffCredit.this, "Please enter Justification", Toast.LENGTH_LONG).show();
                }
                else if(days.getText().toString().equals("0")){
                    Toast.makeText(LeaveCompOffCredit.this, "You are not Eligible", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    if (Utility.isOnline(LeaveCompOffCredit.this)) {
                        progressDialog = new ProgressDialog(LeaveCompOffCredit.this);
                        try {
                            progressDialog.show();
                        } catch (WindowManager.BadTokenException e) {

                        }
                        progressDialog.setCancelable(false);
                        progressDialog.getWindow()
                                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        progressDialog.setContentView(R.layout.progress_bar_layout);

                        createCompOff(empCode, startDate.getText().toString(), endDate.getText().toString(), year, "CO", days.getText().toString(), "", "ES", authperson, comment.getText().toString(), "", reportyEmailId, reportyEmpName, EmpName, "", "");
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void checkLeaveValidation(String empcode, String fromDate, String toDate, String LeaveType) {

        AlmsServices almsServices = new AlmsServices();
        almsServices.checkLeaveValidationCompOff(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<CheckCompOffValidation> list = (List<CheckCompOffValidation>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (((list.get(0).getCoff_allow() != null && list.get(0).getCoff_allow()==1) && (list.get(0).getRemarks().equalsIgnoreCase("Eligible")) && (list.get(0).getWORK_HOURS()==1)) || ((list.get(0).getCOFF_ALLOW()!=null && list.get(0).getCOFF_ALLOW()==1 )&& (list.get(0).getREMARKS().equalsIgnoreCase("Not worked")) && (list.get(0).getWORK_HOURS()==1))) {
                        getTotalLeaveDays("CO",startDate.getText().toString(), endDate.getText().toString());
                    }
                else {
                    days.setText("");
                    Toast.makeText(LeaveCompOffCredit.this, "You can`t apply for this period", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }, empcode, fromDate, toDate,LeaveType);
    }

    public void initDatePicker() {
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
        datePicker = new DatePickerDialog(LeaveCompOffCredit.this, (datePicker, i, i1, i2) -> {
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
            startDate.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            endDate.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
         //   getTotalLeaveDays("CO",startDate.getText().toString(), endDate.getText().toString());
            checkLeaveValidation(empCode,startDate.getText().toString(), endDate.getText().toString(),"CO");
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

          datePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }

    public void initDatePicker1() {
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
        datePicker1 = new DatePickerDialog(LeaveCompOffCredit.this, (datePicker, i, i1, i2) -> {
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
            endDate.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
          //  getTotalLeaveDays("CO",startDate.getText().toString(), endDate.getText().toString());
            checkLeaveValidation(empCode,startDate.getText().toString(), endDate.getText().toString(),"CO");


        }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

          datePicker1.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createCompOff(String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files){
        AlmsServices almsServices = new AlmsServices();
        almsServices.createCompOff(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<ApplyLeaveResponse> applyLeaveResponse = (List<ApplyLeaveResponse>) carotResponse.getData();
                    if(applyLeaveResponse!=null && applyLeaveResponse.size()>0){
                        if(applyLeaveResponse.get(0)!=null){
                            if(applyLeaveResponse.get(0).getLeaveRequestNo()!=null && applyLeaveResponse.get(0).getLeaveRequestNo().length()>0){
                                showMsg("Your Comp. Off request has been created successfully.\n" +
                                        "Request No: " + applyLeaveResponse.get(0).getLeaveRequestNo(), "");
                            }
                            else{
                                showMsg("You are not eligible.", "");

                            }
                        }

                    }

                }
            }
        },Empcode,Fromdate,Todate,Year, LeaveType,NoOfDays,ReasonCode,Session,AuthPerson,Remark,Place,ReportyEmailID,ReportyEmpName,EmpName, FileName,Files);
    }
    public void getTotalLeaveDays(String leaveType, String fromDate, String toDate) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getTotalLeaveDays(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveDaysResponse> list = (List<LeaveDaysResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    days.setText("" + list.get(0).getCount());
                }

            }
        }, leaveType, fromDate, toDate,empCode);
    }


    public void showMsg(String msg, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Ok", (arg0, arg1) -> {
            arg0.dismiss();
            onBackPressed();
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
