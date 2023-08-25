package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveRegularizeModel;
import com.minda.sparsh.model.LeaveValidationResponse;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveRegularizationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    DatePickerDialog datePicker, datePicker1;
    Calendar calendar, calendar1;
    String year, year1, session = "ES";
    int leaveType = 0;
    TextInputEditText start_date, end_date, no_of_days, comment, start_time, end_time;
    Button cancel, submit;
    AutoCompleteTextView leave_type, session_spinner;
    ArrayAdapter<String> leaveTypeAdapter, sessionAdapter;
    ArrayList<String> leavetypes = new ArrayList<>();
    List<LeaveRegularizeModel> leaveTypeList = new ArrayList<>();
    String empCode;
    SharedPreferences myPref;
    TimePickerDialog timePickerDialog, timePickerDialog1;
    Date millisecondsdailyfrom = null, millisecondsdailyto = null;
    Date millisecondsdatefrom=null,millisecondsdateto=null;

    String authperson, reportyEmailId, reportyEmpName, EmpName;
    ArrayList<String> session_values = new ArrayList<>();
    ProgressDialog progressDialog;


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
        comment = findViewById(R.id.comment);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        leave_type = findViewById(R.id.leave_type);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        session_spinner = findViewById(R.id.session);
        sessionAdapter = new ArrayAdapter<>(LeaveRegularizationActivity.this, android.R.layout.simple_list_item_1, session_values);
        session_spinner.setAdapter(sessionAdapter);
        progressDialog = new ProgressDialog(this);


        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        EmpName = myPref.getString("username", "");
        authperson = myPref.getString("REPORTY_EMP_CODE", "");
        reportyEmailId = myPref.getString("REPORTY_EMAIL", "");
        reportyEmpName = myPref.getString("REPORTY_EMP_NAME", "");

        session_values.add("Full Session");
        session_values.add("1st Session");
        session_values.add("2nd Session");
        sessionAdapter = new ArrayAdapter<>(LeaveRegularizationActivity.this, android.R.layout.simple_list_item_1, session_values);
        session_spinner.setAdapter(sessionAdapter);
        session_spinner.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                session = "ES";
            } else if (i == 1) {
                session = "FN";
            } else {
                session = "AN";
            }
        });

        initDatePicker();
        initDatePicker1();
        initTimePicker();
        initTimePicker1();

        leaveTypeAdapter = new ArrayAdapter<>(LeaveRegularizationActivity.this, android.R.layout.simple_list_item_1, leavetypes);
        leave_type.setAdapter(leaveTypeAdapter);
        getLeaveTypeRegularize(empCode);
        leave_type.setOnItemClickListener((adapterView, view, i, l) -> {
            if (start_date.getText().toString().length() == 0 && end_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRegularizationActivity.this, "Select Start Date & End Date ", Toast.LENGTH_SHORT).show();
                return;
            }
            leaveType = leaveTypeList.get(i).getLeavecode();
            checkLeaveValidation(empCode,start_date.getText().toString(),end_date.getText().toString());
            //getTotalLeaveDays(String.valueOf(leaveType), start_date.getText().toString(), end_date.getText().toString());
        });

        start_date.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });
        end_date.setOnTouchListener((view, motionEvent) -> {
            datePicker1.show();
            return false;
        });
        start_time.setOnTouchListener((view, motionEvent) -> {
            timePickerDialog.show();
            return false;
        });
        end_time.setOnTouchListener((view, motionEvent) -> {
            timePickerDialog1.show();
            return false;
        });

        cancel.setOnClickListener(view -> onBackPressed());
        submit.setOnClickListener(view -> {
            if (start_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRegularizationActivity.this, "Please select Start Date", Toast.LENGTH_LONG).show();
                return;
            } else if (end_date.getText().toString().length() == 0) {
                Toast.makeText(LeaveRegularizationActivity.this, "Please select End Date", Toast.LENGTH_LONG).show();
                return;
            } else if (leaveType == 0) {
                Toast.makeText(LeaveRegularizationActivity.this, "Please select Regularization Type", Toast.LENGTH_LONG).show();
                return;
            } else if (no_of_days.getText().toString().length() == 0 || Double.parseDouble(no_of_days.getText().toString()) <= 0) {
                return;
            }
            else if(start_time.getText().length()==0 && end_time.getText().toString().length()==0){
                Toast.makeText(LeaveRegularizationActivity.this, "Please enter Start Time & End Time", Toast.LENGTH_LONG).show();

                return;
            }
            if (Utility.isOnline(LeaveRegularizationActivity.this)) {
                 progressDialog = new ProgressDialog(this);
                    try {
                        progressDialog.show();
                    } catch (WindowManager.BadTokenException e) {

                    }
                    progressDialog.setCancelable(false);
                    progressDialog.getWindow()
                            .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    progressDialog.setContentView(R.layout.progress_bar_layout);


                    applyLeaveRegular(empCode, start_date.getText().toString(), end_date.getText().toString(), year, "" + leaveType, no_of_days.getText().toString(), "", session, authperson, comment.getText().toString(), "", reportyEmailId, reportyEmpName, EmpName, "", "", start_time.getText().toString(), end_time.getText().toString());
            }
            else{
            Toast.makeText(LeaveRegularizationActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
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
            millisecondsdatefrom = calendar.getTime();
            year = String.valueOf(calendar.get(Calendar.YEAR));
            start_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            end_date.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            leave_type.setText("");
            leaveType = 0;
            leaveTypeAdapter.getFilter().filter(null);
            no_of_days.setText("");
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
                leaveType = 0;
                leaveTypeAdapter.getFilter().filter(null);
                no_of_days.setText("");
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

        timePickerDialog = new TimePickerDialog(LeaveRegularizationActivity.this, (view, hourOfDay, minute) -> {
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

        timePickerDialog1 = new TimePickerDialog(LeaveRegularizationActivity.this, (view, hourOfDay, minute) -> {
            cal2.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal2.set(Calendar.MINUTE, minute);
            cal2.set(Calendar.AM_PM, cal2.get(Calendar.AM_PM));
            cal2.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            cal2.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            cal2.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            millisecondsdailyto = cal2.getTime();
            if (millisecondsdailyto.before(millisecondsdailyfrom)) {
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

                end_time.setText("" + hourOfDay + "." + minute_str);

            }

        }, hour_init, minute_init, true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTotalLeaveDays(String leaveType, String fromDate, String toDate) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getTotalLeaveDays(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveDaysResponse> list = (List<LeaveDaysResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    no_of_days.setText("" + list.get(0).getCount());
                }
            }
        }, leaveType, fromDate, toDate,empCode);

    }

    public void getLeaveTypeRegularize(String empcode) {
        leavetypes.clear();
        leaveTypeList.clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveTypeRegularize(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveRegularizeModel> list = (List<LeaveRegularizeModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    leaveTypeList.addAll(list);
                    for (LeaveRegularizeModel leaveRegularizeModel : leaveTypeList) {
                        leavetypes.add(leaveRegularizeModel.getLeave());
                    }
                    leaveTypeAdapter.notifyDataSetChanged();
                }
            }
        }, empcode);
    }


    public void applyLeaveRegular(String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files, String FSHrs, String SSHrs) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.applyLeaveRegular(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<ApplyLeaveResponse> list = (List<ApplyLeaveResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getLeaveRequestNo() != null && list.get(0).getLeaveRequestNo().length() > 0) {
                        showMsg("Your leave request has been created successfully.\n" +
                                "Request No: " + list.get(0).getLeaveRequestNo(), "");
                    }
                    else {
                        showMsg("Oops! something went wrong!!!", "");
                    }
                }
            }
            else{
                showMsg("Oops! something went wrong!!!", "");
            }
            progressDialog.dismiss();

        }, Empcode, Fromdate, Todate, Year, LeaveType, NoOfDays, ReasonCode, Session, AuthPerson, Remark, Place, ReportyEmailID, ReportyEmpName, EmpName, FileName, Files, FSHrs, SSHrs);


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

        //alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void checkLeaveValidation(String empcode, String fromDate, String toDate){

        AlmsServices almsServices = new AlmsServices();
        almsServices.checkLeaveValidation(carotResponse -> {
            if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                List<LeaveValidationResponse> list = (List<LeaveValidationResponse>) carotResponse.getData();
                if(list!= null && list.size()>0){
                    if(list.get(0).getData().equals("You can`t apply for this period")){
                        Toast.makeText(LeaveRegularizationActivity.this, "You can`t apply for this period", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        getTotalLeaveDays(String.valueOf(leaveType),start_date.getText().toString(),end_date.getText().toString());
                    }
                }
            }

        },empcode,fromDate, toDate,"");
    }


}
