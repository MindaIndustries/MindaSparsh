package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.ALMSRecyclerViewAdapter;
import com.minda.sparsh.decorators.EventDecorator;
import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;
import com.nambimobile.widgets.efab.ExpandableFabLayout;
import com.nambimobile.widgets.efab.FabOption;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class ALMSCalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendar_view, calendar2;
    Toolbar toolbar;
    LinearLayout ll1;
    CardView total_days_card;
    TextView title, date_current, date, day, punch_in_time, punch_out_time, total_days_count, present_day_count, absent_day_count, status, leaves, leaves_count;
    RelativeLayout calendarLayout, listLayout, main_container;
    RecyclerView attendance_rv;
    ALMSRecyclerViewAdapter almsRecyclerViewAdapter;
    String empCode, fromDate, toDate;
    int Reporty;
    SharedPreferences myPref;
    ArrayList<AlmsReportModel> attendanceReport = new ArrayList<>();
    ArrayList<CalendarDay> presentList = new ArrayList<>();
    ArrayList<CalendarDay> absentList = new ArrayList<>();
    ArrayList<CalendarDay> wolist = new ArrayList<>();
    ArrayList<CalendarDay> odlist = new ArrayList<>();
    ArrayList<CalendarDay> ftplist = new ArrayList<>();
    ArrayList<CalendarDay> leavelist = new ArrayList<>();
    ArrayList<CalendarDay> shortList = new ArrayList<>();
    ArrayList<CalendarDay> holidayList = new ArrayList<>();

    DatePickerDialog datePicker;
    Calendar calendar;
    String year;
    CardView punch_details;
    Calendar calendar1;
    String month_name;
    FabOption leave_req_btn, leave_regular_btn, leave_balance_btn, leave_approvals, holidays, leave_req_btn1, leave_regular_btn1, leave_balance_btn1, holidays1;
    ExpandableFabLayout expandable_fab, expandable_fab1;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alms_calendar_layout);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("My Attendance");
        calendarLayout = findViewById(R.id.calendar_layout);
        listLayout = findViewById(R.id.list_layout);
        main_container = findViewById(R.id.main_container);
        attendance_rv = findViewById(R.id.attendance_rv);
        date_current = findViewById(R.id.date_current);
        punch_details = findViewById(R.id.punch_details);
        total_days_card = findViewById(R.id.total_days_card);
        date = findViewById(R.id.date);
        day = findViewById(R.id.day);
        punch_in_time = findViewById(R.id.punch_in_time);
        punch_out_time = findViewById(R.id.punch_out_time);
        total_days_count = findViewById(R.id.total_days_count);
        present_day_count = findViewById(R.id.present_day_count);
        absent_day_count = findViewById(R.id.absent_day_count);
        leaves = findViewById(R.id.leaves);
        leaves_count = findViewById(R.id.leaves_count);
        leave_req_btn = findViewById(R.id.leave_req_btn);
        leave_balance_btn = findViewById(R.id.leave_balance_btn);
        leave_regular_btn = findViewById(R.id.leave_regular_btn);
        leave_approvals = findViewById(R.id.leave_approvals);
        leave_req_btn1 = findViewById(R.id.leave_req_btn1);
        leave_balance_btn1 = findViewById(R.id.leave_balance_btn1);
        leave_regular_btn1 = findViewById(R.id.leave_regular_btn1);
        holidays = findViewById(R.id.holiday_list);
        holidays1 = findViewById(R.id.holiday_list1);
        progressDialog = new ProgressDialog(this);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        progressDialog.setCancelable(false);
        progressDialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_bar_layout);
        // dialog.setMessage(Message);


        expandable_fab = findViewById(R.id.expandable_fab);
        expandable_fab1 = findViewById(R.id.expandable_fab1);

        status = findViewById(R.id.status);
        ll1 = findViewById(R.id.ll1);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        Reporty = myPref.getInt("Reporty", 0);
        calendar_view = findViewById(R.id.calendar_view);
        calendar2 = findViewById(R.id.calendar2);
        almsRecyclerViewAdapter = new ALMSRecyclerViewAdapter(ALMSCalendarActivity.this, attendanceReport);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ALMSCalendarActivity.this, LinearLayoutManager.VERTICAL, false);
        attendance_rv.setLayoutManager(mLayoutManager);
        attendance_rv.setAdapter(almsRecyclerViewAdapter);
        calendar1 = Calendar.getInstance();

        int day = calendar1.get(Calendar.DAY_OF_MONTH);
        int month = calendar1.get(Calendar.MONTH) + 1;
        int year = calendar1.get(Calendar.YEAR);
        LocalDate localDate = LocalDate.of(year, month, day);

        if (getIntent().getBooleanExtra("list", false)) {
            listLayout.setVisibility(View.VISIBLE);
            calendarLayout.setVisibility(View.GONE);
        }

        calendar_view.setCurrentDate(CalendarDay.from(localDate));
        calendar_view.getCurrentDate();

        if (calendar_view.getCurrentDate().getMonth() == 1) {
            fromDate = "21." + "12" + "." + calendar_view.getCurrentDate().getYear();
        } else {
            fromDate = "21." + (calendar_view.getCurrentDate().getMonth() - 1) + "." + calendar_view.getCurrentDate().getYear();
        }
        toDate = day + "." + month + "." + year;
        //   toDate = "20." + (calendar_view.getCurrentDate().getMonth()) + "." + calendar_view.getCurrentDate().getYear();
        initDatePicker();
        date_current.setOnClickListener(view -> datePicker.show());

        if (Utility.isOnline(ALMSCalendarActivity.this)) {
            getConsolidatedReport(empCode, fromDate, toDate);
        }
        if (Reporty > 0) {
            expandable_fab.setVisibility(View.VISIBLE);
            expandable_fab1.setVisibility(View.GONE);

        } else {
            expandable_fab1.setVisibility(View.VISIBLE);
            expandable_fab.setVisibility(View.GONE);

        }
        leave_approvals.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveApprovals.class);
            startActivity(in);
        });
        //calendar_view.state().edit().setMaximumDate(CalendarDay.from(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1,calendar1.get(Calendar.DAY_OF_MONTH))).commit();
        calendar_view.setOnDateChangedListener((widget, date, selected) -> {

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.DAY_OF_MONTH, date.getDay());
            cal2.set(Calendar.MONTH, date.getMonth() - 1);
            cal2.set(Calendar.YEAR, date.getYear());

            if (new Date(cal1.getTimeInMillis()).after(new Date(cal2.getTimeInMillis()))) {
                punch_details.setVisibility(View.VISIBLE);
                fromDate = date.getDay() + "." + (date.getMonth()) + "." + date.getYear();
                toDate = date.getDay() + "." + (date.getMonth()) + "." + date.getYear();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                cal.set(Calendar.MONTH, date.getMonth() - 1);
                String month_name = month_date.format(cal.getTime());
                date_current.setText(month_name + ", " + date.getYear());
                getConsolidatedReport1(empCode, toDate, toDate);
            } else {
                punch_details.setVisibility(View.GONE);
            }
        });
        leave_req_btn.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveRequestActivity.class);
            startActivity(in);
        });
        leave_req_btn1.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveRequestActivity.class);
            startActivity(in);
        });


        leave_balance_btn.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveBalanceActivity.class);
            startActivity(in);
        });
        leave_balance_btn1.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveBalanceActivity.class);
            startActivity(in);
        });


        leave_regular_btn.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveRegularizationActivity.class);
            startActivity(in);
        });
        leave_regular_btn1.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, LeaveRegularizationActivity.class);
            startActivity(in);
        });
        holidays.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, HolidaysActivity.class);
            startActivity(in);

        });
        holidays1.setOnClickListener(view -> {
            Intent in = new Intent(ALMSCalendarActivity.this, HolidaysActivity.class);
            startActivity(in);

        });

        calendar_view.setOnMonthChangedListener((widget, date) -> {
            if (date.getMonth() == 1) {
                fromDate = "21." + "12" + "." + (date.getYear() - 1);
            } else {
                fromDate = "21." + (date.getMonth() - 1) + "." + date.getYear();
            }
            if (date.getMonth() == calendar1.get(Calendar.MONTH) + 1) {
                toDate = day + "." + month + "." + year;
            } else {
                toDate = "20." + (date.getMonth()) + "." + date.getYear();
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, date.getMonth() - 1);
            String month_name = month_date.format(cal.getTime());
            date_current.setText(month_name + ", " + date.getYear());
            getConsolidatedReport(empCode, fromDate, toDate);
            //current month
            punch_details.setVisibility(View.GONE);
        });
        calendar2.setOnMonthChangedListener((widget, date) -> {
            if (date.getMonth() == 1) {
                fromDate = "21." + "12" + "." + (date.getYear() - 1);
            } else {
                fromDate = "21." + (date.getMonth() - 1) + "." + date.getYear();
            }
            if (date.getMonth() == calendar1.get(Calendar.MONTH) + 1) {
                toDate = day + "." + month + "." + year;
            } else {
                toDate = "20." + (date.getMonth()) + "." + date.getYear();
            }
            //    toDate = "20." + (date.getMonth()) + "." + date.getYear();
            getConsolidatedReport(empCode, fromDate, toDate);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.alms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
           /* if (calendarLayout.getVisibility() == View.VISIBLE) {
                onBackPressed();
            } else {
                listLayout.setVisibility(View.GONE);
                calendarLayout.setVisibility(View.VISIBLE);
                item.setIcon(R.drawable.listview_icon);
            }
*/
            return true;
        }/* else if (item.getItemId() == R.id.listview) {
            if (calendarLayout.getVisibility() == View.VISIBLE) {
                listLayout.setVisibility(View.VISIBLE);
                calendarLayout.setVisibility(View.GONE);
                item.setIcon(R.drawable.calendar_white);

            } else {
                listLayout.setVisibility(View.GONE);
                calendarLayout.setVisibility(View.VISIBLE);
                item.setIcon(R.drawable.listview_icon);
            }
        }*/
        return super.onOptionsItemSelected(item);
    }


    public void getConsolidatedReport(String empcode, String fromDate, String toDate) {
        if (!isFinishing()) {
            progressDialog.show();
        }
        attendanceReport.clear();
        attendance_rv.getRecycledViewPool().clear();
        AlmsServices almsServices = new AlmsServices();
        almsRecyclerViewAdapter.notifyDataSetChanged();
        almsServices.getConsolidatedReport(carotResponse -> {
            if (!isFinishing())
                progressDialog.dismiss();
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AlmsReportModel> list = (List<AlmsReportModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getMsg() != null) {
                        attendanceReport.clear();
                    } else {
                        attendanceReport.addAll(list);
                    }
                }
                almsRecyclerViewAdapter.notifyDataSetChanged();

                if (attendanceReport.size() > 0) {
                    for (int i = 0; i < attendanceReport.size(); i++) {
                        if (attendanceReport.get(i).getEDATE() != null) {


                            String date[] = attendanceReport.get(i).getEDATE().replace(".", "/").split("/");
                            int day = Integer.parseInt(date[0]);
                            int month = Integer.parseInt(date[1]);
                            int year = Integer.parseInt(date[2]);
                            LocalDate localDate = LocalDate.of(year, month, day);
                            CalendarDay calendarDay = CalendarDay.from(localDate);
                            if (attendanceReport.get(i).getSTAT() != null && attendanceReport.get(i).getSTATUS2() != null) {
                                if (attendanceReport.get(i).getSTAT().equals("P") && attendanceReport.get(i).getSTATUS2().equals("P")) {
                                    presentList.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("A") || attendanceReport.get(i).getSTATUS2().equals("A")) {
                                    absentList.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("WO")) {
                                    wolist.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("OD") && attendanceReport.get(i).getSTATUS2().equals("OD")) {
                                    odlist.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("FTP") && attendanceReport.get(i).getSTATUS2().equals("FTP")) {
                                    ftplist.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("EL") || attendanceReport.get(i).getSTAT().equals("CL") || attendanceReport.get(i).getSTAT().equals("SL")) {
                                    leavelist.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTATUS2().equals("EL") || attendanceReport.get(i).getSTATUS2().equals("CL") || attendanceReport.get(i).getSTATUS2().equals("SL")) {
                                    leavelist.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("SH") || attendanceReport.get(i).getSTATUS2().equals("SH")) {
                                    shortList.add(calendarDay);
                                } else if (attendanceReport.get(i).getSTAT().equals("H") || attendanceReport.get(i).getSTATUS2().equals("H")) {
                                    holidayList.add(calendarDay);
                                }
                            }
                        }
                    }
                    total_days_count.setText(attendanceReport.get(0).getTotalPWD());
                    present_day_count.setText(attendanceReport.get(0).getTotalPD());
                    absent_day_count.setText(attendanceReport.get(0).getTotalAD());
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#00A36C"), presentList));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#FF0000"), absentList));
                    calendar_view.addDecorator(new EventDecorator(Color.GRAY, wolist));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#FF9800"), odlist));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#FF9800"), ftplist));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#dbaf1d"), leavelist));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#ffc0cb"), shortList));
                    calendar_view.addDecorator(new EventDecorator(Color.parseColor("#0089c8"), holidayList));
                }
            }
        }, empcode, fromDate, toDate);
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
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        month_name = month_date.format(calendar.getTime());

        date_current.setText(month_name + ", " + calendar.get(Calendar.YEAR));
        datePicker = new DatePickerDialog(ALMSCalendarActivity.this, (datePicker, i, i1, i2) -> {
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
            month_name = month_date.format(calendar.getTime());
            if (calendar.get(Calendar.MONTH) == 0) {
                fromDate = "21." + (calendar.get(Calendar.MONTH) + 12) + "." + (calendar.get(Calendar.YEAR) - 1);
            } else {
                fromDate = "21." + (calendar.get(Calendar.MONTH)) + "." + calendar.get(Calendar.YEAR);
            }
            toDate = "20." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
            getConsolidatedReport(empCode, fromDate, toDate);
            date_current.setText(month_name + ", " + calendar.get(Calendar.YEAR));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);
        datePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);


    }

    public void getConsolidatedReport1(String empcode, String fromDate, String toDate) {
        if (!isFinishing()) {
            progressDialog.show();
        }
        attendanceReport.clear();
        attendance_rv.getRecycledViewPool().clear();
        AlmsServices almsServices = new AlmsServices();
        almsRecyclerViewAdapter.notifyDataSetChanged();
        almsServices.getConsolidatedReport(carotResponse -> {
            if (!isFinishing())
                progressDialog.dismiss();
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AlmsReportModel> list = (List<AlmsReportModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {

                    if (list.get(0).getDAY_IN() != null && list.get(0).getDAY_IN().trim().length() > 0) {
                        punch_in_time.setText(list.get(0).getDAY_IN());
                    } else {
                        punch_in_time.setText("-");
                    }
                    if (list.get(0).getDAY_OUT() != null && list.get(0).getDAY_OUT().trim().length() > 0) {
                        punch_out_time.setText(list.get(0).getDAY_OUT());
                    } else {
                        punch_out_time.setText("-");
                    }
                    status.setVisibility(View.VISIBLE);
                    if (list.get(0).getSTAT() != null) {
                        if (list.get(0).getSTAT().equals("OD") || list.get(0).getSTATUS2().equals("OD")) {
                            status.setText("OD");
                            status.setBackgroundColor(Color.parseColor("#FF9800"));
                        } else if (list.get(0).getSTAT().equals("FTP") || list.get(0).getSTATUS2().equals("FTP")) {
                            status.setText("FTP");
                            status.setBackgroundColor(Color.parseColor("#FF9800"));
                        } else if (list.get(0).getSTAT().equals("CL") || list.get(0).getSTATUS2().equals("CL")) {
                            status.setText("CL");
                            status.setBackgroundColor(Color.parseColor("#dbaf1d"));

                        } else if (list.get(0).getSTAT().equals("EL") || list.get(0).getSTATUS2().equals("EL")) {
                            status.setText("EL");
                            status.setBackgroundColor(Color.parseColor("#dbaf1d"));

                        } else if (list.get(0).getSTAT().equals("SL") || list.get(0).getSTATUS2().equals("SL")) {
                            status.setText("SL");
                            status.setBackgroundColor(Color.parseColor("#dbaf1d"));

                        } else if (list.get(0).getSTAT().equals("P") && list.get(0).getSTATUS2().equals("P")) {
                            status.setText("P");
                            status.setBackgroundColor(Color.parseColor("#00A36C"));
                        } else if (list.get(0).getSTAT().equals("WO")) {
                            status.setText("WO");
                            status.setBackgroundColor(Color.GRAY);

                        } else if (list.get(0).getSTAT().equals("A") || list.get(0).getSTATUS2().equals("A")) {
                            status.setText("A");
                            status.setBackgroundColor(Color.parseColor("#FF0000"));

                        } else if (list.get(0).getSTAT().equals("SH") || list.get(0).getSTATUS2().equals("SH")) {
                            status.setText("SH");
                            status.setBackgroundColor(Color.parseColor("#ffc0cb"));

                        } else if (list.get(0).getSTAT().equals("H") || list.get(0).getSTATUS2().equals("H")) {
                            status.setText("H");
                            status.setBackgroundColor(Color.parseColor("#0089c8"));
                        } else {
                            status.setVisibility(View.GONE);
                        }
                    } else {
                        status.setVisibility(View.GONE);
                    }
                    if (list.get(0).getDAYNAME() != null) {
                        day.setText(list.get(0).getDAYNAME().toUpperCase());
                    }
                    if (list.get(0).getEDATE() != null) {
                        date.setText(list.get(0).getEDATE().replace(".", "/").split("/")[0]);
                    }
                }

            }
        }, empcode, fromDate, toDate);
    }


}
