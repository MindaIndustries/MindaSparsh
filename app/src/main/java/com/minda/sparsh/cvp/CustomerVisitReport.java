package com.minda.sparsh.cvp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CustomerModel;
import com.minda.sparsh.model.LocationModel;
import com.minda.sparsh.model.WeekByCustomerModel;
import com.minda.sparsh.services.CVPServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerVisitReport extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customSpinnerLayout0)
    TextInputLayout customSpinnerLayout0;
    @BindView(R.id.date_of_visit)
    AppCompatAutoCompleteTextView dateOfVisit;
    @BindView(R.id.customSpinnerLayout1)
    TextInputLayout customSpinnerLayout1;
    @BindView(R.id.custom_name)
    AppCompatAutoCompleteTextView customer_spinner;
    @BindView(R.id.customSpinnerLayout2)
    TextInputLayout customSpinnerLayout2;
    @BindView(R.id.location_spinner)
    AppCompatAutoCompleteTextView locationSpinner;
    @BindView(R.id.customSpinnerLayout3)
    TextInputLayout customSpinnerLayout3;
    @BindView(R.id.plant)
    AppCompatAutoCompleteTextView plant;
    @BindView(R.id.customSpinnerLayout4)
    TextInputLayout customSpinnerLayout4;
    @BindView(R.id.start_time_selector)
    AppCompatAutoCompleteTextView start_time_selector;
    @BindView(R.id.customSpinnerLayout5)
    TextInputLayout customSpinnerLayout5;
    @BindView(R.id.end_time_selector)
    AppCompatAutoCompleteTextView end_time_selector;
    @BindView(R.id.customSpinnerLayout6)
    TextInputLayout customSpinnerLayout6;
    @BindView(R.id.business)
    AppCompatAutoCompleteTextView business;
    @BindView(R.id.customSpinnerLayout7)
    TextInputLayout customSpinnerLayout7;
    @BindView(R.id.agenda_ed)
    AppCompatAutoCompleteTextView agenda_ed;
    @BindView(R.id.customSpinnerLayout8)
    TextInputLayout CustomSpinnerLayout8;
    @BindView(R.id.meeting_location)
    AppCompatAutoCompleteTextView meetingLocation;
    @BindView(R.id.customSpinnerLayout9)
    TextInputLayout CustomSpinnerLayout9;
    @BindView(R.id.mom_ref_no)
    AppCompatAutoCompleteTextView mom_ref_no;
    @BindView(R.id.customSpinnerLayout10)
    TextInputLayout customSpinnerLayout10;
    @BindView(R.id.calendar_weekNo)
    AppCompatAutoCompleteTextView calendarWeekNo;
    @BindView(R.id.add_attendees)
    TextView addAttendees;
    @BindView(R.id.add_internal)
    TextView add_internal;
    @BindView(R.id.add_customer)
    TextView add_customer;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.add_rl)
    RelativeLayout add_rl;
    DatePickerDialog datePicker;
    Calendar calendar;
    TimePickerDialog timePicker,timePicker1;
    ArrayAdapter<String> customerAdapter,locationAdapter, customerWeekAdapter;
    ArrayList<CustomerModel.CustomerData> customer = new ArrayList<>();
    ArrayList<String> customerList = new ArrayList<>();
    ArrayList<LocationModel.LocationData> locations = new ArrayList<>();
    ArrayList<String> locationList = new ArrayList<>();
    ArrayList<WeekByCustomerModel.WeekByCustomerData> customerWeeks = new ArrayList<>();
    ArrayList<String> customerWeekList = new ArrayList<>();
    SharedPreferences myPref;
    String empcode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createvisitportal);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");
        plant.setText(myPref.getString("UM_MASCOM_CODE",""));
        title.setText("Customer Visit Report");
        initDatePicker();
        initTimePicker();
        initTimePicker1();
        initCustomerSpinner();
        getCustomers(empcode);
        initLocationSpinner();
        initCustomerWeekSpinner();
        dateOfVisit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    datePicker.show();
                }
                return false;
            }
        });
        start_time_selector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    timePicker.show();
                }
                    return false;
            }
        });
        end_time_selector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    timePicker1.show();
                }
                    return false;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_rl.getVisibility()== View.VISIBLE){
                    add_rl.setVisibility(View.GONE);
                    add.setImageDrawable(getResources().getDrawable(R.drawable.add));
                }
                else{
                    add_rl.setVisibility(View.VISIBLE);
                    add.setImageDrawable(getResources().getDrawable(R.drawable.minus));
                }
            }
        });

        add_internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAttendees();

               }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initDatePicker(){
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker = new DatePickerDialog(CustomerVisitReport.this, new DatePickerDialog.OnDateSetListener() {
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
                dateOfVisit.setText("" + dayOfMonthStr + "-" + monthNo + "-" + i);
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }
    public void initTimePicker(){
        timePicker = new TimePickerDialog(CustomerVisitReport.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String minute_str,hour;
                if (i1 < 10) {
                    minute_str = "0" + i1;
                } else {
                    minute_str = "" + i1;
                }
                if(i<10){
                   hour = "0"+i ;
                }
                else{
                    hour ="" +i;
                }
                start_time_selector.setText(""+hour+":"+minute_str);
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);

    }
    public void initTimePicker1(){
        timePicker1 = new TimePickerDialog(CustomerVisitReport.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String minute_str,hour;
                if (i1 < 10) {
                    minute_str = "0" + i1;
                } else {
                    minute_str = "" + i1;
                }
                if(i<10){
                    hour = "0"+i ;
                }
                else{
                    hour ="" +i;
                }
                end_time_selector.setText(""+hour+":"+minute_str);
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);

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
    public void initCustomerSpinner() {
        customerAdapter = new ArrayAdapter<String>(CustomerVisitReport.this, android.R.layout.simple_spinner_item, customerList);
        customer_spinner.setAdapter(customerAdapter);
        customer_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0)
                {
                    locationSpinner.setText("");
                    getLocation(customer.get(i).getId());
                    getWeekByCustomer(customer.get(i).getId(),empcode);
                }
            }
        });
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
                            locationAdapter = new ArrayAdapter<String>(CustomerVisitReport.this, android.R.layout.simple_spinner_item, locationList);
                            locationSpinner.setAdapter(locationAdapter);

                            locationAdapter.notifyDataSetChanged();
                          }
                    }

                }

            }
        }, customerId);
    }
    public void initLocationSpinner() {
        locationAdapter = new ArrayAdapter<String>(CustomerVisitReport.this, android.R.layout.simple_spinner_item, locationList);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {

                }
            }
        });
    }

    public void initCustomerWeekSpinner(){
        customerWeekAdapter = new ArrayAdapter<String>(CustomerVisitReport.this,android.R.layout.simple_spinner_item, customerWeekList);
        calendarWeekNo.setAdapter(customerWeekAdapter);
        calendarWeekNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public void getWeekByCustomer(String customerId, String empcode){
        CVPServices cvpServices = new CVPServices();
        cvpServices.getWeekByCustomer(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    WeekByCustomerModel weekByCustomerModel = (WeekByCustomerModel) carotResponse.getData();
                    if(weekByCustomerModel!=null){
                        List<WeekByCustomerModel.WeekByCustomerData> list = weekByCustomerModel.getData();
                        if(list!=null && list.size()>0){
                            customerWeeks.addAll(list);
                            for (WeekByCustomerModel.WeekByCustomerData weekByCustomerData : list) {
                                customerWeekList.add(weekByCustomerData.getWeeks());
                            }
                            customerWeekAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        },customerId,empcode);
    }

    public void addAttendees(){

        Dialog dialog = new Dialog(CustomerVisitReport.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.addattendees);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }
}
