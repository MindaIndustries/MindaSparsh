package com.minda.sparsh.cvp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.R;
import com.minda.sparsh.decorators.HighlightWeekendsDecorator;
import com.minda.sparsh.decorators.MySelectorDecorator;
import com.minda.sparsh.decorators.OneDayDecorator;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.CustomerModel;
import com.minda.sparsh.model.EditCalendarModel;
import com.minda.sparsh.model.LocationModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.services.CVPServices;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CVPPlanCalendar extends AppCompatActivity implements OnDateSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customerSpinnerLayout0)
    TextInputLayout getCustomerSpinnerLayout0;
    @BindView(R.id.calendar_type_spinner)
    AppCompatAutoCompleteTextView calendarTypeSpinner;
    @BindView(R.id.customerSpinnerLayout)
    TextInputLayout customerSpinnerLayout;
    @BindView(R.id.week_spinner)
    AppCompatAutoCompleteTextView weekSpinner;
    @BindView(R.id.customerSpinnerLayout1)
    TextInputLayout customerSpinnerLayout1;
    @BindView(R.id.customer_spinner)
    AppCompatAutoCompleteTextView customer_spinner;
    @BindView(R.id.customerSpinnerLayout2)
    TextInputLayout customerSpinnerLayout2;
    @BindView(R.id.location_spinner)
    AppCompatAutoCompleteTextView location_spinner;
    @BindView(R.id.customerSpinnerLayout3)
    TextInputLayout customerSpinnerLayout3;
    @BindView(R.id.mtype_spinner)
    AppCompatAutoCompleteTextView mtype_spinner;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    @BindView(R.id.customSpinnerLayout1)
    TextInputLayout datelayout;
    @BindView(R.id.date)
    AppCompatAutoCompleteTextView date;
    DatePickerDialog datePicker;
    Calendar calendar;

    ArrayAdapter<String> weekAdapter, customerAdapter, locationAdapter, meetingTypeAdapter, calendarTypeAdapter;
    ArrayList<String> weeksList = new ArrayList<>();
    ArrayList<WeekModel.WeekData> weeks = new ArrayList<>();
    ArrayList<CustomerModel.CustomerData> customer = new ArrayList<>();
    ArrayList<String> customerList = new ArrayList<>();
    ArrayList<LocationModel.LocationData> locations = new ArrayList<>();
    ArrayList<String> locationList = new ArrayList<>();
    ArrayList<MeetingTypeModel.MeetingTypeData> meetings = new ArrayList<>();
    ArrayList<String> meetingList = new ArrayList<>();
    ArrayList<CalendarTypeModel.CalendarTypeData> calendarTypes = new ArrayList<>();
    ArrayList<String> calendartypeList = new ArrayList<>();
    String empcode;
    SharedPreferences myPref;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    String weekdayId, customerId, custLocId, meetingTypeId, year, calendartypeId;
    String calendartypeIdedit, weekIdedit, customerIdedit, locationIdedit, meetingtypeIdedit;
    String currentDate;
    int meetingId;
    int currentWeekId;
    String datesave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cvp_plan_calendar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Add a Plan");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");
        calendarView.setDateSelected(new Date(), true);
        calendarView.setSelectionColor(getResources().getColor(R.color.colorPrimary));
        calendarView.state().edit().setMinimumDate(Calendar.getInstance()).commit();
        calendarView.state().edit().setFirstDayOfWeek(Calendar.MONDAY).commit();
        calendarView.addDecorators(
                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
        calendarView.setOnDateChangedListener(this);
        initWeekSpinner();
        currentDate = Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        initCustomerSpinner();
        getCustomers(empcode);
        initLocationSpinner();
        initMeetingTypeSpinner();
        getMeetingType();
        initCalendarTypeSpinner();
        getCalendarTypes();
        initDatePicker();
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                datePicker.show();
                return false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendartypeId == null || calendartypeId.length() == 0) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Calendar Type", Toast.LENGTH_LONG).show();
                    return;
                }
                if (weekdayId == null || weekdayId.length() == 0) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Week", Toast.LENGTH_LONG).show();
                    return;
                }
                if (customerId == null || customerId.length() == 0) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Customer", Toast.LENGTH_LONG).show();
                    return;
                }
                if (custLocId == null || custLocId.length() == 0) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Customer Location", Toast.LENGTH_LONG).show();
                    return;
                }
                if (meetingTypeId == null || meetingTypeId.length() == 0) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Meeting Type", Toast.LENGTH_LONG).show();
                    return;
                }
                if (date.getText().toString().equals("")) {
                    Toast.makeText(CVPPlanCalendar.this, "Select Date", Toast.LENGTH_LONG).show();
                    return;
                }

                if (submit.getText().toString().equals("Submit")) {
                    saveCalendarBooking(Integer.parseInt(weekdayId), customerId, custLocId, Integer.parseInt(meetingTypeId), year, empcode, Integer.parseInt(calendartypeId), datesave);
                } else {
                    updateCalendarBooking(meetingId, Integer.parseInt(weekdayId), empcode, custLocId, meetingTypeId, datesave);
                }
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

        date.setText(dayOfMonthStr + "-" + monthNo + "-" + calendar.get(Calendar.YEAR));
        datesave = calendar.get(Calendar.YEAR) + "-" + monthNo + "-" + dayOfMonthStr;
        datePicker = new DatePickerDialog(CVPPlanCalendar.this, new DatePickerDialog.OnDateSetListener() {
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
                getWeek(i + "-" + monthNo + "-" + dayOfMonthStr);
                date.setText("" + dayOfMonthStr + "-" + monthNo + "-" + i);
                datesave = i +"-"+ monthNo +"-"+ dayOfMonthStr;
                year = String.valueOf(i);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);
        datePicker.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initWeekSpinner() {
        weekAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, weeksList);
        weekSpinner.setAdapter(weekAdapter);
        weekSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0)
                    weekdayId = weeks.get(i).getWeeks();
            }
        });
    }

    public void initCustomerSpinner() {
        customerAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, customerList);
        customer_spinner.setAdapter(customerAdapter);
        customer_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0)
                    customerId = customer.get(i).getId();
                getLocation(customer.get(i).getId());
            }
        });
    }

    public void initLocationSpinner() {
        locationAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, locationList);
        location_spinner.setAdapter(locationAdapter);
        location_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    custLocId = locations.get(i).getID();
                }
            }
        });
    }

    public void initMeetingTypeSpinner() {
        meetingTypeAdapter = new ArrayAdapter<>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, meetingList);
        mtype_spinner.setAdapter(meetingTypeAdapter);
        mtype_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    meetingTypeId = meetings.get(i).getId();
                }
            }
        });
    }

    public void initCalendarTypeSpinner() {
        calendarTypeAdapter = new ArrayAdapter<>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, calendartypeList);
        calendarTypeSpinner.setAdapter(calendarTypeAdapter);
        calendarTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    calendartypeId = calendarTypes.get(i).getId();
                    if (calendartypeList.get(i).equalsIgnoreCase("Monthly Calender")) {
                        calendarView.state().edit().setMinimumDate(Calendar.getInstance());
                    }
                }
            }
        });
    }

    public void getWeek(String date) {
        weeks.clear();
        weeksList.clear();
        weekAdapter.notifyDataSetChanged();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getWeekData(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    WeekModel weekModel = (WeekModel) carotResponse.getData();
                    if (weekModel != null) {
                        List<WeekModel.WeekData> list = weekModel.getData();
                        if (list != null && list.size() > 0) {
                            weeks.addAll(list);
                            for (WeekModel.WeekData weekData : list) {
                                weeksList.add(weekData.getName());
                            }
                            // weekSpinner.setText("");
                            weekAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, weeksList);
                            weekSpinner.setAdapter(weekAdapter);
                            weekAdapter.notifyDataSetChanged();
                            if (weeksList.size() > 0) {
                                weekSpinner.setText(weeksList.get(0));
                                weekdayId = weeks.get(0).getWeeks();
                                if (!date.equals("")) {
                                    weekSpinner.setEnabled(false);
                                    weekSpinner.setTextColor(Color.parseColor("#333333"));
                                } else {
                                    weekSpinner.setEnabled(true);
                                }
                            }
                        }
                    }
                }
            }
        }, date);
    }


    public void getCustomers(String empcode) {
        customer.clear();
        customerList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCustomers(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    CustomerModel customerModel = (CustomerModel) carotResponse.getData();
                    if (customerModel != null) {
                        List<CustomerModel.CustomerData> list = customerModel.getData();
                        if (list != null && list.size() > 0) {
                            customer.addAll(list);
                            for (CustomerModel.CustomerData customerData : list) {
                                customerList.add(customerData.getName());
                            }
                            customerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }, empcode);
    }

    public void getLocation(String customerId) {
        locations.clear();
        locationList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getLocation(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    LocationModel locationModel = (LocationModel) carotResponse.getData();
                    if (locationModel != null) {
                        List<LocationModel.LocationData> list = locationModel.getData();
                        if (list != null && list.size() > 0) {
                            locations.addAll(list);
                            for (LocationModel.LocationData locationData : list) {
                                locationList.add(locationData.getLocation().trim());
                            }
                            location_spinner.setText("");
                            locationAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, locationList);
                            location_spinner.setAdapter(locationAdapter);
                            locationAdapter.notifyDataSetChanged();

                            if (locationList.size() == 1) {
                                location_spinner.setText(locationList.get(0));
                                custLocId = locations.get(0).getID();
                                locationAdapter.getFilter().filter(null);
                            }
                            if (locationIdedit != null) {
                                LocationModel.LocationData locationData = new LocationModel.LocationData();
                                locationData.setID(locationIdedit);
                                int locationIndex = locations.indexOf(locationData);
                                if (locationIndex >= 0) {
                                    location_spinner.setText(locationList.get(locationIndex));
                                    locationAdapter.getFilter().filter(null);
                                }
                            }
                        }
                    }

                }

            }
        }, customerId);
    }

    public void getMeetingType() {
        meetings.clear();
        meetingList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getMeetingType(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    MeetingTypeModel meetingTypeModel = (MeetingTypeModel) carotResponse.getData();
                    if (meetingTypeModel != null) {
                        List<MeetingTypeModel.MeetingTypeData> list = meetingTypeModel.getData();
                        if (list != null && list.size() > 0) {
                            meetings.addAll(list);
                            for (MeetingTypeModel.MeetingTypeData meetingTypeData : list) {
                                meetingList.add(meetingTypeData.getName());
                            }
                            meetingTypeAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull @NotNull MaterialCalendarView widget, @NonNull @NotNull CalendarDay date, boolean selected) {
        Log.d("calendar", "" + date);
        getWeek(date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay());
        year = String.valueOf(date.getYear());
    }


    public void getCalendarTypes() {
        calendarTypes.clear();
        calendartypeList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.getCalendarTypes(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    CalendarTypeModel calendarTypeModel = (CalendarTypeModel) carotResponse.getData();
                    if (calendarTypeModel != null) {
                        List<CalendarTypeModel.CalendarTypeData> list = calendarTypeModel.getData();
                        if (list != null && list.size() > 0) {
                            calendarTypes.addAll(list);
                            for (CalendarTypeModel.CalendarTypeData calendarTypeData : list) {
                                calendartypeList.add(calendarTypeData.getName());
                            }

                            calendarTypeAdapter.notifyDataSetChanged();
                            calendarTypeSpinner.setText(calendartypeList.get(0));
                            calendarTypeAdapter.getFilter().filter(null);
                            calendartypeId = calendarTypes.get(0).getId();
                            if (getIntent().getExtras() != null && getIntent().getBooleanExtra("Edit", true)) {
                                meetingId = getIntent().getIntExtra("MID", 0);
                                calendartypeIdedit = getIntent().getStringExtra("calendartypeId");
                                calendartypeId = calendartypeIdedit;
                                if (calendartypeIdedit != null) {
                                    CalendarTypeModel.CalendarTypeData calendarTypeData = new CalendarTypeModel.CalendarTypeData(calendartypeIdedit);
                                    calendarTypeSpinner.setText(calendartypeList.get(calendarTypes.indexOf(calendarTypeData)));
                                    calendarTypeSpinner.setEnabled(false);
                                }
                                getWeek("");
                                submit.setText("UPDATE");
                                editCalendarBooking(String.valueOf(meetingId), empcode);
                            } else {
                                getWeek(currentDate);
                            }

                        }
                    }

                }
            }
        });
    }

    public void saveCalendarBooking(int WeekDaysId, String CustomerId, String CustLocationId, int MeetingTypeId, String Year, String CreatedBy, int CalenderType, String CalendarBookingDate) {
        calendarTypes.clear();
        calendartypeList.clear();
        CVPServices cvpServices = new CVPServices();
        cvpServices.saveCalendarBooking(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    SaveCalendarResponse saveCalendarResponse = (SaveCalendarResponse) carotResponse.getData();
                    if (saveCalendarResponse != null) {
                        String data = saveCalendarResponse.getData();
                        if (data != null) {
                            showMsgUpdate("Success", data);
                        }
                    }
                } else {
                    Toast.makeText(CVPPlanCalendar.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }, WeekDaysId, CustomerId, CustLocationId, MeetingTypeId, Year, CreatedBy, CalenderType, CalendarBookingDate);
    }

    public void showMsgUpdate(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            arg0.dismiss();
            if(title.equals("Updated")){
                finish();
                startActivity(new Intent(CVPPlanCalendar.this,CVPViewCalendar.class));
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void editCalendarBooking(String meetingId, String empcode) {
        CVPServices cvpServices = new CVPServices();
        cvpServices.editCalendarBooking(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    EditCalendarModel editCalendarModel = (EditCalendarModel) carotResponse.getData();
                    if (editCalendarModel != null) {
                        List<EditCalendarModel.EditCalendarModelData> list = editCalendarModel.getData();
                        if (list != null && list.size() > 0) {
                            if (list.get(0).getWeekDaysID() != null) {
                                weekIdedit = list.get(0).getWeekDaysID();
                                weekdayId = weekIdedit;
                                WeekModel.WeekData weekData = new WeekModel.WeekData();
                                weekData.setWeeks(weekIdedit);
                                int weekindex = weeks.indexOf(weekData);
                                if (weekindex >= 0) {
                                    weekSpinner.setText(weeksList.get(weekindex));
                                    weekAdapter.getFilter().filter(null);
                                }
                            }
                            customerIdedit = String.valueOf(list.get(0).getCustomerID());
                            customerId = customerIdedit;
                            CustomerModel.CustomerData customerData = new CustomerModel.CustomerData();
                            customerData.setId(customerIdedit);
                            int customerIndex = customer.indexOf(customerData);
                            if (customerIndex >= 0) {
                                customerAdapter = new ArrayAdapter<String>(CVPPlanCalendar.this, android.R.layout.simple_spinner_item, customerList);
                                customer_spinner.setAdapter(customerAdapter);
                                customerAdapter.notifyDataSetChanged();
                                customer_spinner.setText(customerList.get(customerIndex));
                                customerAdapter.getFilter().filter(null);

                            }
                            customer_spinner.setEnabled(false);
                            locationIdedit = String.valueOf(list.get(0).getLocationID());
                            custLocId = locationIdedit;
                            getLocation(customerIdedit);
                            if (list.get(0).getMeetingType1() != null) {
                                meetingtypeIdedit = list.get(0).getMeetingType1();
                                meetingTypeId = meetingtypeIdedit;
                                MeetingTypeModel.MeetingTypeData meetingTypeData = new MeetingTypeModel.MeetingTypeData();
                                meetingTypeData.setId(meetingtypeIdedit);
                                int meetingIndex = meetings.indexOf(meetingTypeData);
                                if (meetingIndex >= 0) {
                                    mtype_spinner.setText(meetingList.get(meetingIndex));
                                    meetingTypeAdapter.getFilter().filter(null);
                                }
                            }
                            if (list.get(0).getCalendarBookingDate() != null && list.get(0).getCalendarBookingDate().length() > 0) {
                               String[] datevisit = list.get(0).getCalendarBookingDate().split("T");
                               String dateT = datevisit[0];
                               String[] dateTSplit = dateT.split("-");
                               String viewDate = dateTSplit[2]+"-"+dateTSplit[1]+"-"+dateTSplit[0];

                                date.setText("" + viewDate);
                            }


                        }
                    }
                }

            }
        }, meetingId, empcode);
    }

    public void updateCalendarBooking(int id, int weekdayid, String empcode, String custLocationId, String meetingTypeId, String CalendarBookingDate) {
        CVPServices cvpServices = new CVPServices();
        cvpServices.updateCalendarBooking(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    SaveCalendarResponse saveCalendarResponse = (SaveCalendarResponse) carotResponse.getData();
                    if (saveCalendarResponse != null) {
                        showMsgUpdate("Updated", saveCalendarResponse.getData());
                    }
                }
            }
        }, id, weekdayid, empcode, custLocationId, meetingTypeId, CalendarBookingDate);
    }


}
