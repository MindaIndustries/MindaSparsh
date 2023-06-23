package com.minda.sparsh;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveTypeModel;
import com.minda.sparsh.model.LeaveValidationResponse;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveRequestActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    AppCompatAutoCompleteTextView leave_type, session_spinner;
    TextInputEditText start_date, end_date, comment, available_bal, no_of_days, start_time, end_time;
    TextInputLayout customerSpinnerLayout9, customerSpinnerLayout8, customerSpinnerLayout00, customerSpinnerLayout01;
    ArrayList<LeaveBalanceModel> leaveBalanceList = new ArrayList<>();
    String empCode;
    SharedPreferences myPref;
    ArrayAdapter<String> leaveTypeAdapter, sessionAdapter;
    ArrayList<String> leavetypes = new ArrayList<>();
    DatePickerDialog datePicker, datePicker1;
    Calendar calendar, calendar1;
    String year, year1;
    Button cancel, submit;
    String leaveType, leavetypeAbb, session = "ES";
    TextView m_certificate;
    ImageView upload;
    String authperson, reportyEmailId, reportyEmpName, EmpName;
    private File mDestinationFile;
    String fileName = "", fileType, fileByte = "";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;
    private String mUserChoosenTask = "";

    byte[] bytes;
    Bitmap bmp;
    boolean no_sick_leave_allowed = false, no_sick_leave_allowed1 = false;
    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog, timePickerDialog1;
    Date millisecondsdailyfrom = null, millisecondsdailyto = null;
    Date millisecondsdatefrom=null,millisecondsdateto=null;
    List<LeaveTypeModel> LeaveTypesList = new ArrayList<>();
    ArrayList<String> session_values = new ArrayList<>();
    String no_of_days_api;
    public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    int hh_start,mm_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_leave_req);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        leave_type = findViewById(R.id.leave_type);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        comment = findViewById(R.id.comment);
        available_bal = findViewById(R.id.available_bal);
        no_of_days = findViewById(R.id.no_of_days);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        m_certificate = findViewById(R.id.m_certificate);
        upload = findViewById(R.id.upload_certificate);
        customerSpinnerLayout9 = findViewById(R.id.customerSpinnerLayout9);
        customerSpinnerLayout8 = findViewById(R.id.customerSpinnerLayout8);
        customerSpinnerLayout00 = findViewById(R.id.customerSpinnerLayout00);
        customerSpinnerLayout01 = findViewById(R.id.customerSpinnerLayout01);
        session_spinner = findViewById(R.id.session);
        session_values.add("Full Session");
        session_values.add("1st Session");
        session_values.add("2nd Session");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Request");
        progressDialog = new ProgressDialog(this);

        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        EmpName = myPref.getString("username", "");
        authperson = myPref.getString("REPORTY_EMP_CODE", "");
        reportyEmailId = myPref.getString("REPORTY_EMAIL", "");
        reportyEmpName = myPref.getString("REPORTY_EMP_NAME", "");
        upload.setOnClickListener(view -> selectFile());

        leaveTypeAdapter = new ArrayAdapter<>(LeaveRequestActivity.this, android.R.layout.simple_list_item_1, leavetypes);
        leave_type.setAdapter(leaveTypeAdapter);
        sessionAdapter = new ArrayAdapter<>(LeaveRequestActivity.this, android.R.layout.simple_list_item_1, session_values);
        session_spinner.setAdapter(sessionAdapter);
        getLeaveTypes(empCode);
        initDatePicker();
        initDatePicker1();
        initTimePicker();
        initTimePicker1();

        start_time.setOnTouchListener((view, motionEvent) -> {
            timePickerDialog.show();
            return false;
        });
        end_time.setOnTouchListener((view, motionEvent) -> {
            timePickerDialog1.show();
            return false;
        });
        session_spinner.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                session = "ES";
            } else if (i == 1) {
                session = "FN";
            } else {
                session = "AN";
            }

             if(leaveType.contains("Leave Without Pay") && (session.equals("FN") || session.equals("AN"))){
                 if(Double.parseDouble(no_of_days_api)>=1) {
                     no_of_days.setText(""+Double.parseDouble(no_of_days_api)/2);
                 }

             }
             else{
                 no_of_days.setText(""+no_of_days_api);

             }
        });
        leave_type.setOnItemClickListener((adapterView, view, i, l) -> {
            if (start_date.getText().toString().length() == 0 && end_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRequestActivity.this, "Select Start Date & End Date ", Toast.LENGTH_SHORT).show();
                return;
            }
            leaveType = leavetypes.get(i);
            leavetypeAbb = LeaveTypesList.get(i).getShort_Desc().toUpperCase();
            if (leavetypes.get(i).contains("First Half")) {
                session = "FN";

               // customerSpinnerLayout8.setVisibility(View.GONE);
            } else if (leavetypes.get(i).contains("Second Half")) {
                session = "AN";
              //  customerSpinnerLayout8.setVisibility(View.GONE);
            }
            else {
                session = "ES";
                customerSpinnerLayout8.setVisibility(View.VISIBLE);
            }
            if (leavetypes.get(i).equalsIgnoreCase("Short Leave") || leavetypes.get(i).equalsIgnoreCase("Special Birthday Leave")) {
                customerSpinnerLayout9.setVisibility(View.VISIBLE);
                session_values.clear();
                session_values.add("1st Session");
                session_values.add("2nd Session");
               if(leavetypes.get(i).equalsIgnoreCase("Short Leave")){
                   customerSpinnerLayout00.setVisibility(View.VISIBLE);
                   customerSpinnerLayout01.setVisibility(View.VISIBLE);
               }
               else{
                   customerSpinnerLayout00.setVisibility(View.GONE);
                   customerSpinnerLayout01.setVisibility(View.GONE);
               }

            }
            else if(leavetypes.get(i).contains("Leave Without Pay")){
                customerSpinnerLayout9.setVisibility(View.VISIBLE);
                session_values.clear();
                session_values.add("Full Session");
                session_values.add("1st Session");
                session_values.add("2nd Session");
                customerSpinnerLayout00.setVisibility(View.GONE);
                customerSpinnerLayout01.setVisibility(View.GONE);
                Toast.makeText(LeaveRequestActivity.this, "For Leave without pay, balance is not required.", Toast.LENGTH_LONG).show();
            }
            else {
                customerSpinnerLayout9.setVisibility(View.GONE);
                customerSpinnerLayout00.setVisibility(View.GONE);
                customerSpinnerLayout01.setVisibility(View.GONE);
                session_values.clear();
                session_values.add("Full Session");
                session_values.add("1st Session");
                session_values.add("2nd Session");

            }
          //  getLeaveBalance(empCode, year, leavetypeAbb);

            if (leavetypes.get(i).contains("Sick Leave") && (no_sick_leave_allowed || no_sick_leave_allowed1)) {
                Toast.makeText(LeaveRequestActivity.this, "Future date is not allowed in the Sick Leave", Toast.LENGTH_LONG).show();
                leaveType = "";
                leavetypeAbb = "";
                leave_type.setText("");
                leaveTypeAdapter.getFilter().filter(null);
                return;
            }
            getLeaveBalance(empCode, year, leavetypeAbb);


        });
        start_date.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });
        end_date.setOnTouchListener((view, motionEvent) -> {
            datePicker1.show();
            return false;
        });

        cancel.setOnClickListener(view -> onBackPressed());
        submit.setOnClickListener(view -> {

            if (start_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRequestActivity.this, "Please select Start Date", Toast.LENGTH_LONG).show();
                return;

            } else if (end_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRequestActivity.this, "Please select End Date", Toast.LENGTH_LONG).show();
                return;
            } else if (leaveType.length() == 0) {
                Toast.makeText(LeaveRequestActivity.this, "Please select Leave Type", Toast.LENGTH_LONG).show();
                return;
            } else if (leaveType.contains("Leave Without Pay") && Double.parseDouble(available_bal.getText().toString()) <= 0) {
              //  Toast.makeText(LeaveRequestActivity.this, "For Leave without pay, balance is not required.", Toast.LENGTH_LONG).show();
               // return;
            }
            else if(leaveType.equals("Short Leave") && (start_time.getText().toString().length()==0 || end_time.getText().toString().length()==0)){
                Toast.makeText(LeaveRequestActivity.this, "Please select Start Time & End Time", Toast.LENGTH_LONG).show();
                return;
            }
            else if ((no_of_days.getVisibility()== View.VISIBLE) &&( no_of_days.getText().toString().length() == 0 || Double.parseDouble(no_of_days.getText().toString()) <= 0)) {
                return;
            }
            else if(Double.parseDouble(no_of_days.getText().toString()) >Double.parseDouble(available_bal.getText().toString())){
                Toast.makeText(LeaveRequestActivity.this, "Your Leave days are exceeding leave balance", Toast.LENGTH_LONG).show();

                return;
            }


            if (Utility.isOnline(LeaveRequestActivity.this)) {
                progressDialog = new ProgressDialog(this);
                try {
                    progressDialog.show();
                } catch (WindowManager.BadTokenException e) {

                }
                progressDialog.setCancelable(false);
                progressDialog.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setContentView(R.layout.progress_bar_layout);

                applyLeave(empCode, start_date.getText().toString(), end_date.getText().toString(), year, leavetypeAbb, no_of_days_api, "", session, authperson, comment.getText().toString(), "", reportyEmailId, reportyEmpName, EmpName, fileName, fileByte,start_time.getText().toString(), end_time.getText().toString());
            }
        });
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
        datePicker = new DatePickerDialog(LeaveRequestActivity.this, (datePicker, i, i1, i2) -> {

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
            millisecondsdatefrom = calendar.getTime();
            year = String.valueOf(calendar.get(Calendar.YEAR));
            start_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            end_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            leave_type.setText("");
            leaveTypeAdapter.getFilter().filter(null);
            leaveType = "";
            no_of_days.setText("");
            available_bal.setText("");
            if (calendar.after(Calendar.getInstance())) {
                no_sick_leave_allowed = true;
            } else {
                no_sick_leave_allowed = false;
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

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
        datePicker1 = new DatePickerDialog(LeaveRequestActivity.this, (datePicker, i, i1, i2) -> {
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
            millisecondsdateto = calendar1.getTime();
            if(millisecondsdatefrom==null){
                Toast.makeText(this, "Select Start Date", Toast.LENGTH_LONG).show();
            }
           else if (millisecondsdatefrom.after(millisecondsdateto)) {
                Toast.makeText(this, "End Date can't be less than Start Date", Toast.LENGTH_LONG).show();
                end_time.setText("");
                return;
            }
            else {

                year1 = String.valueOf(calendar1.get(Calendar.YEAR));
                end_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
                leave_type.setText("");
                leaveType = "";
                leaveTypeAdapter.getFilter().filter(null);
                no_of_days.setText("");
                available_bal.setText("");
                if (calendar1.after(Calendar.getInstance())) {
                    no_sick_leave_allowed = true;
                } else {
                    no_sick_leave_allowed = false;
                }
            }
        }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));

        //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }


    public void initTimePicker() {

        final Calendar cal1 = Calendar.getInstance();
        final int hour_init, minute_init;
        cal1.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        cal1.add(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        hour_init = cal1.get(Calendar.HOUR_OF_DAY);
        minute_init = cal1.get(Calendar.MINUTE);
        cal1.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        cal1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        cal1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        millisecondsdailyfrom = cal1.getTime();

        timePickerDialog = new TimePickerDialog(LeaveRequestActivity.this, (view, hourOfDay, minute) -> {
            cal1.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal1.set(Calendar.MINUTE, minute);
            cal1.set(Calendar.AM_PM, cal1.get(Calendar.AM_PM));
            cal1.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            cal1.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            cal1.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            millisecondsdailyfrom = cal1.getTime();
              /*  if(millisecondsdailyfrom.before(Calendar.getInstance().getTime())){
                    Toast.makeText(this, "End Time can't be less than Start Time", Toast.LENGTH_LONG).show();
                    start_time.setText("");
                    return;
                }
                else{*/
            String minute_str =null ;
            if (minute < 10) {
                minute_str = "0" + minute;
            } else {
                minute_str = "" + minute;
            }

            start_time.setText("" + hourOfDay + "." + minute_str);
            hh_start = hourOfDay;
            mm_start = Integer.parseInt(minute_str);
            end_time.setText("");

            //   }

        }, hour_init, minute_init, true);


    }

    public void initTimePicker1() {
        final Calendar cal2 = Calendar.getInstance();
        final int hour_init, minute_init;
        cal2.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        cal2.add(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        hour_init = cal2.get(Calendar.HOUR_OF_DAY);
        minute_init = cal2.get(Calendar.MINUTE);
        cal2.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        cal2.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        cal2.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        millisecondsdailyto = cal2.getTime();

        timePickerDialog1 = new TimePickerDialog(LeaveRequestActivity.this, (view, hourOfDay, minute) -> {
            cal2.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal2.set(Calendar.MINUTE, minute);
            cal2.set(Calendar.AM_PM, cal2.get(Calendar.AM_PM));
            cal2.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            cal2.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            cal2.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            millisecondsdailyto = cal2.getTime();
            if(millisecondsdailyfrom == null){
                Toast.makeText(this, "Select Satrt Time", Toast.LENGTH_LONG).show();
                return;
            }
            if (millisecondsdailyfrom.after(millisecondsdailyto)) {
                Toast.makeText(this, "End Time can't be less than Start Time", Toast.LENGTH_LONG).show();
                end_time.setText("");
                return;
            } else {
                String minute_str = null;
                if (minute < 10) {
                    minute_str = "0" + minute;
                } else {
                    minute_str = "" + minute;
                }
                long two_hrs = (1000 * 60 * 120)+1000 ; //120 minutes in milliseconds
                long differ = (millisecondsdailyto.getTime() - millisecondsdailyfrom.getTime());
                if (differ <= two_hrs && differ >= -two_hrs ){
                    end_time.setText("" + hourOfDay + "." + minute_str);
                    // under +/-120 minutes, do the work
                }else{
                    // over 120 minutes
                    Toast.makeText(this, "Short leave can be of max. 2 hrs.", Toast.LENGTH_LONG).show();

                }

            }

        }, hour_init, minute_init, true);


    }

    public void getLeaveBalance(String empcode, String year, String leaveType) {
        leaveBalanceList.clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveBalance(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveBalanceModel> list = (List<LeaveBalanceModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    leaveBalanceList.addAll(list);
                    available_bal.setText("" + leaveBalanceList.get(0).getBalance());
                    if ((!leaveBalanceList.get(0).getLeaveType().equals("BL") && leaveBalanceList.get(0).getBalance() == 0.5)) {
                        //   Toast.makeText(LeaveRequestActivity.this,"You do not have Leave balance!", Toast.LENGTH_LONG).show();

                    }
                    if (!(leaveBalanceList.get(0).getLeaveType().equals("LWP") || leaveBalanceList.get(0).getLeaveType().equals("LWPESIC")) && leaveBalanceList.get(0).getBalance() <= 0) {
                        Toast.makeText(LeaveRequestActivity.this, "You do not have Leave balance!", Toast.LENGTH_LONG).show();

                    }
                    checkLeaveValidation(empcode, start_date.getText().toString(), end_date.getText().toString());
                }
            }
        }, empcode, year, leaveType);

    }

    public void getLeaveTypes(String empcode) {
        LeaveTypesList.clear();
        leavetypes.clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveTypes(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveTypeModel> list = (List<LeaveTypeModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    LeaveTypesList.addAll(list);
                    for (LeaveTypeModel leaveTypeModel : list) {
                        if (leaveTypeModel.getLvtyp() != null) {
                            leavetypes.add(leaveTypeModel.getLvtyp());
                            leaveTypeAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }, empcode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void checkLeaveValidation(String empcode, String fromDate, String toDate) {

        AlmsServices almsServices = new AlmsServices();
        almsServices.checkLeaveValidation(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveValidationResponse> list = (List<LeaveValidationResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getData().equals("You can`t apply for this period")) {
                        Toast.makeText(LeaveRequestActivity.this, "You can`t apply for this period", Toast.LENGTH_SHORT).show();
                    } else {
                        getTotalLeaveDays(leavetypeAbb, start_date.getText().toString(), end_date.getText().toString());
                    }
                }
            }

        }, empcode, fromDate, toDate,"");
    }

    public void getTotalLeaveDays(String shortleavetyp, String fromDate, String toDate) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getTotalLeaveDays(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveDaysResponse> list = (List<LeaveDaysResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    no_of_days_api = ""+list.get(0).getCount();
                    no_of_days.setText("" + list.get(0).getCount());
                }
               /* if (leavetypeAbb.equals("BL")) {
                    no_of_days.setText("0.5");
                   // session = "AN";
                }*/

                if(leaveType.equals("Special Birthday Leave") || leaveType.contains("Half") ){
                    no_of_days.setText("" + (list.get(0).getCount()/2));
                    if(leaveType.equals("Special Birthday Leave")) {
                        no_of_days_api = "0.5";
                    }
                }
                if (leavetypeAbb.equals("SL") && list.get(0).getCount() > 3) {
                    m_certificate.setVisibility(View.VISIBLE);
                    upload.setVisibility(View.VISIBLE);
                } else {
                    m_certificate.setVisibility(View.GONE);
                    upload.setVisibility(View.GONE);
                }
            }
        }, shortleavetyp, fromDate, toDate,empCode);

    }

    public void applyLeave(String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files, String fs_hrs, String ss_hrs) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.applyLeave(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<ApplyLeaveResponse> list = (List<ApplyLeaveResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getLeaveRequestNo() != null && list.get(0).getLeaveRequestNo().length() > 0) {
                        showMsg("Your leave request has been created successfully.\n" +
                                "Request No: " + list.get(0).getLeaveRequestNo(), "");
                    }
                }
            }
            progressDialog.dismiss();

        }, Empcode, Fromdate, Todate, Year, LeaveType, NoOfDays, ReasonCode, Session, AuthPerson, Remark, Place, ReportyEmailID, ReportyEmpName, EmpName, FileName, Files,fs_hrs,ss_hrs);
    }

    public void selectFile() {
        requestAppPermissions();
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            selectImage();
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            selectImage();
            return;
        }

        ActivityCompat.requestPermissions(LeaveRequestActivity.this,
                new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_FROM_CAMERA);
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Choose Document",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(LeaveRequestActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(LeaveRequestActivity.this);
            if (items[item].equals("Take Photo")) {
                mUserChoosenTask = "Take Photo";
                if (result) {
                    requestCameraPermission();
                    if (hasCameraPermission())
                        cameraIntent();
                }
            } else if (items[item].equals("Choose from Gallery")) {
                mUserChoosenTask = "Choose from Gallery";
                if (result) {
                    galleryIntent();
                }

            } else if (items[item].equals("Choose Document")) {
                mUserChoosenTask = "Choose Document";
                if (result) {
                    fileIntent();
                }

            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void fileIntent() {
        //docView.setVisibility(View.GONE);
        String[] mimeTypes =
                {"application/pdf", "application/msword", "application/vnd.ms-powerpoint", "application/vnd.ms-excel", "text/plain", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), SELECT_FILE);
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        bytes = getBytesFromBitmap(thumbnail);
        fileType = "jpg";
        bmp = thumbnail;
        //  attachtext.setText(fileName);
        this.fileName = fileName;
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        //  docView.setVisibility(View.VISIBLE);
        //docView.setImageBitmap(thumbnail);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (mDestinationFile != null) {
            mDestinationFile.delete();
        }
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(LeaveRequestActivity.this, bm, Uri.parse(mDestinationFile.toString()));
        //  docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        this.fileName = fileName;
        System.out.println("fileName" + fileName);
        fileType = "jpg";
        bytes = getBytesFromBitmap(bm);
        bmp = bm;
        // attachtext.setText(fileName);
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        //  docView.setVisibility(View.VISIBLE);
        //   docView.setImageBitmap(bm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FROM_GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAPTURE_FROM_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == SELECT_FILE)
                onSelectFile(data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {
        Uri fileUri = data.getData();

      //  File file = new File(fileUri.getPath());
        String mimeType = getContentResolver().getType(fileUri);
        fileType = mimeType.replace("application/", "");

        //  fileName = /*file.getName()*/"Test.txt";
        Cursor returnCursor =
                getContentResolver().query(fileUri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        fileName = returnCursor.getString(nameIndex);
        returnCursor.close();
        // attachtext.setText(fileName);
        //attachtext.setVisibility(View.VISIBLE);
        //docView.setVisibility(View.GONE);

        try {
            InputStream is = getContentResolver().openInputStream(fileUri);
            byte[] bytesArray = new byte[is.available()];
            is.read(bytesArray);

            //write to sdcard
            /*
            File myPdf=new File(Environment.getExternalStorageDirectory(), "myPdf.pdf");
            FileOutputStream fos=new FileOutputStream(myPdf.getPath());
            fos.write(bytesArray);
            fos.close();*/

            System.out.println(bytesArray);
            fileByte = Base64.encodeToString(bytesArray, Base64.NO_WRAP);

        } catch (Exception e) {
            e.printStackTrace();
        }


       /* Uri fileUri = data.getData();
        String mimeType = getContentResolver().getType(fileUri);

        String fullFilePath = UriUtils.getPathFromUri(RequestForAccessActivity.this, fileUri);
        File file = new File(fullFilePath);
        fileName = file.getName();
        fileType = mimeType.replace("application/", "");
        attachtext.setText(fileName);
        bytes = new byte[(int) file.length()];

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes); //read file into bytes[]
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
*/
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(LeaveRequestActivity.this,
                new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(LeaveRequestActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(LeaveRequestActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(LeaveRequestActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation = getRotation(context, selectedImage);
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        } else {
            return img;
        }
    }

    private static int getRotation(Context context, Uri selectedImage) {

        int rotation = 0;
        ContentResolver content = context.getContentResolver();

        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{"orientation", "date_added"},
                null, null, "date_added desc");

        if (mediaCursor != null && mediaCursor.getCount() != 0) {
            while (mediaCursor.moveToNext()) {
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
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
