package com.minda.sparsh;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.model.AutoFillMobileModel;
import com.minda.sparsh.model.VisitorDetailModel;
import com.minda.sparsh.util.Constant;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

public class VisitorManagementActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView im_back;
    Button btn_view;
    public static TextView tv_from_date, tv_to_date;
    TextView tv_start_time, tv_end_time, et_additional_no_person, tv_decrement, tv_increment;
    private static int dateType;
    LinearLayout lay_rest_content, lay_contact;
    EditText et_visitor_mobile_no, et_first_name, et_last_name, et_email, et_mobile, et_company_name, et_address, et_city, et_pin_code, et_purpose;
    RadioGroup rg;
    String rbValue = "M";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog progress = null;
    Button btn_submit;
    private SharedPreferences myPref = null;
    static String tv_from_date_value = "", tv_to_date_value = "";
    static final int PICK_CONTACT = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    String callFromActivity;
    Integer visitorId;
    Boolean callCondition = true;
    Spinner sp_start_time_hour, sp_start_time_minutes, sp_end_time_hour, sp_end_time_minutes;
    String start_time_hour, start_time_minutes, end_time_hour, end_time_minutes;
    List<String> hourList;
    List<String> minuteList;
    String strDateHour, strDateTimeWithMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_management);

        Intent intent = getIntent();
        if (intent != null) {
            callFromActivity = intent.getStringExtra(Constant.CALL_FROM_ACTIVITY);
            visitorId = intent.getIntExtra(Constant.VISITOR_ID, 0);
        }

        im_back = (ImageView) findViewById(R.id.im_back);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VisitorManagementListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        progress = new ProgressDialog(VisitorManagementActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        lay_rest_content = (LinearLayout) findViewById(R.id.lay_rest_content);
        lay_contact = (LinearLayout) findViewById(R.id.lay_contact);
//        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
//        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_decrement = (TextView) findViewById(R.id.tv_decrement);
        tv_increment = (TextView) findViewById(R.id.tv_increment);

        tv_from_date = (TextView) findViewById(R.id.tv_from_date);
        tv_to_date = (TextView) findViewById(R.id.tv_to_date);
        String currentDateTimeString = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        tv_from_date.setText(currentDateTimeString);
        tv_to_date.setText(currentDateTimeString);
        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        tv_from_date_value = currentDate;
        tv_to_date_value = currentDate;
        et_visitor_mobile_no = (EditText) findViewById(R.id.et_visitor_mobile_no);
        et_first_name = (EditText) findViewById(R.id.et_first_name);
        et_last_name = (EditText) findViewById(R.id.et_last_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_city = (EditText) findViewById(R.id.et_city);
        et_pin_code = (EditText) findViewById(R.id.et_pin_code);
        et_purpose = (EditText) findViewById(R.id.et_purpose);
        et_additional_no_person = (TextView) findViewById(R.id.et_additional_no_person);

        // spinner

        sp_start_time_hour = (Spinner) findViewById(R.id.sp_start_time_hour);
        sp_start_time_minutes = (Spinner) findViewById(R.id.sp_start_time_minutes);
        sp_end_time_hour = (Spinner) findViewById(R.id.sp_end_time_hour);
        sp_end_time_minutes = (Spinner) findViewById(R.id.sp_end_time_minutes);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH");
        strDateHour = sdf.format(c.getTime());

        // for comapre
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        strDateTimeWithMinutes = sdf1.format(c.getTime());

        initSpinner("", "", "", "");


        tv_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_additional_no_person.setText(String.valueOf((Integer.parseInt(et_additional_no_person.getText().toString())) + 1));

            }
        });

        tv_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_additional_no_person.getText().toString().equalsIgnoreCase("0")) {
                    et_additional_no_person.setText(String.valueOf((Integer.parseInt(et_additional_no_person.getText().toString())) - 1));

                }

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_male:
                        rbValue = "M";
                        break;
                    case R.id.rb_female:
                        rbValue = "F";
                        break;
                }
            }
        });
        et_visitor_mobile_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_mobile.setText(et_visitor_mobile_no.getText().toString());
                et_mobile.setEnabled(false);
                if (!et_visitor_mobile_no.getText().toString().equalsIgnoreCase(et_mobile.getText().toString())) {
                    callCondition = true;
                }
                if (charSequence.length() == 10 && callCondition) {

                    hitGetVisitorDetailbyMobileApi(RetrofitClient2.CKEY, et_visitor_mobile_no.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateType = 1;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateType = 2;
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
//        tv_start_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                timePickerDialog(1);
//            }
////        });
//        tv_end_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePickerDialog(2);
//
//            }
//        });

        lay_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                }
            }
        });


        if (callFromActivity.equalsIgnoreCase("visitor_list")) {
            hitGetVisitorDetailApi(RetrofitClient2.CKEY, String.valueOf(visitorId));
        }

    }

    private void initSpinner(String s1, String s2, String s3, String s4) {
        hourList = new ArrayList<String>();
        hourList.add("Hour");
        hourList.add("1");
        hourList.add("2");
        hourList.add("3");
        hourList.add("4");
        hourList.add("5");
        hourList.add("6");
        hourList.add("7");
        hourList.add("8");
        hourList.add("9");
        hourList.add("10");
        hourList.add("11");
        hourList.add("12");
        hourList.add("13");
        hourList.add("14");
        hourList.add("15");
        hourList.add("16");
        hourList.add("17");
        hourList.add("18");
        hourList.add("19");
        hourList.add("20");
        hourList.add("21");
        hourList.add("22");
        hourList.add("23");
        hourList.add("24");

        minuteList = new ArrayList<String>();
        minuteList.add("Minutes");
        minuteList.add("00");
        minuteList.add("15");
        minuteList.add("30");
        minuteList.add("45");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, hourList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterMinutes = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, minuteList);

        adapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_start_time_hour.setAdapter(adapter);
        sp_end_time_hour.setAdapter(adapter);

        sp_start_time_minutes.setAdapter(adapterMinutes);
        sp_end_time_minutes.setAdapter(adapterMinutes);
        sp_start_time_minutes.setSelection(1);
        sp_end_time_minutes.setSelection(1);


        if (!s1.equalsIgnoreCase("")) {
            sp_start_time_hour.setSelection(getPositionHour(sp_start_time_hour, s1));

        }
        if (!s3.equalsIgnoreCase("")) {
            sp_end_time_hour.setSelection(getPositionHour(sp_start_time_hour, s3));

        }


        if (!s2.equalsIgnoreCase("")) {
            sp_start_time_minutes.setSelection(getPositionMinutes(sp_start_time_minutes, s2));

        }
        if (!s4.equalsIgnoreCase("")) {
            sp_end_time_minutes.setSelection(getPositionMinutes(sp_end_time_minutes, s4));

        }


        sp_start_time_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                start_time_hour = sp_start_time_hour.getSelectedItem().toString();
                Log.d("###start_time_hour", start_time_hour);
                if (!callFromActivity.equalsIgnoreCase("visitor_list")) {
                    if (!start_time_hour.equalsIgnoreCase("Hour")) {
                        if (!compareDateTime(strDateHour, tv_from_date.getText().toString() + " " + start_time_hour)) {

                            sp_start_time_hour.setSelection(0);
                            Toast.makeText(VisitorManagementActivity.this, "Time Should be greater or equal from current time", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_start_time_minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                start_time_minutes = sp_start_time_minutes.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_end_time_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                end_time_hour = sp_end_time_hour.getSelectedItem().toString();
                if (!callFromActivity.equalsIgnoreCase("visitor_list")) {
                    if (!end_time_hour.equalsIgnoreCase("Hour")) {
                        if (!compareDateTime(strDateHour, tv_from_date.getText().toString() + " " + start_time_hour)) {
                            sp_end_time_hour.setSelection(0);
                            Toast.makeText(VisitorManagementActivity.this, "Time Should be greater or equal from current time", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_end_time_minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                end_time_minutes = sp_end_time_minutes.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void timePickerDialog(final int type) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(VisitorManagementActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (type == 1) {
//                    tv_start_time.setText(selectedHour + ":" + selectedMinute);

                } else {
//                    tv_end_time.setText(selectedHour + ":" + selectedMinute);

                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    @Override
    public void onClick(View view) {

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
//            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (dateType == 1) {
//                if (!checkDate(tv_to_date.getText().toString(), day + "/" + (month + 1) + "/" + year)) {
                tv_from_date.setText(day + "/" + (month + 1) + "/" + year); //dd/mm/yyyy
                tv_from_date_value = (month + 1) + "/" + day + "/" + year;
                tv_to_date.setText("");
                tv_to_date_value = "";
//                }
            } else {
                if (checkDate(tv_from_date.getText().toString(), day + "/" + (month + 1) + "/" + year)) {
                    tv_to_date.setText(day + "/" + (month + 1) + "/" + year); //dd/mm/yyyy
                    tv_to_date_value = (month + 1) + "/" + day + "/" + year;
                } else {
                    popUp(this);
                }


            }

        }

        private void popUp(DatePickerFragment datePickerFragment) {

            Toast.makeText(datePickerFragment.getActivity(), "To Date should be greater than from date.", Toast.LENGTH_SHORT).show();
            tv_to_date.setText("");
        }
    }


    private void validateFields() {
        if (TextUtils.isEmpty(et_first_name.getText().toString().trim())) {
            et_first_name.setError("Please fill it.");
            et_first_name.setFocusable(true);
            et_first_name.requestFocus();
        }
//        else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
//            et_email.setError("Please fill it.");
//            et_email.setFocusable(true);
//            et_email.requestFocus();
//        }
//        else if (!et_email.getText().toString().trim().equalsIgnoreCase("")) {
//            if (!et_email.getText().toString().trim().matches(emailPattern)) {
//                et_email.setError("Please fill valid email.");
//                et_email.setFocusable(true);
//                et_email.requestFocus();
//            }
//        }
        else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
            et_mobile.setError("Please fill it.");
            et_mobile.setFocusable(true);
            et_mobile.requestFocus();
        } else if (et_mobile.getText().toString().length() < 10) {
            et_mobile.setError("Please fill 10 digit.");
            et_mobile.setFocusable(true);
            et_mobile.requestFocus();
        } else if (TextUtils.isEmpty(et_company_name.getText().toString().trim())) {
            et_company_name.setError("Please fill it.");
            et_company_name.setFocusable(true);
            et_company_name.requestFocus();
        }
//        else if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
//            et_city.setError("Please fill it.");
//            et_city.setFocusable(true);
//            et_city.requestFocus();
//        }
        else if (TextUtils.isEmpty(et_purpose.getText().toString().trim())) {
            et_purpose.setError("Please fill it.");
            et_purpose.setFocusable(true);
            et_purpose.requestFocus();
        } else if (TextUtils.isEmpty(tv_from_date.getText().toString().trim())) {
            tv_from_date.setError("Please fill it.");
            tv_from_date.setFocusable(true);
            tv_from_date.requestFocus();
        } else if (TextUtils.isEmpty(tv_to_date.getText().toString().trim())) {
            tv_to_date.setError("Please fill it.");
            tv_to_date.setFocusable(true);
            tv_to_date.requestFocus();
        } else if (sp_start_time_hour.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Start Time", Toast.LENGTH_SHORT).show();
        } else if (sp_start_time_minutes.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Start Time", Toast.LENGTH_SHORT).show();
        } else if (sp_end_time_hour.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Start Time", Toast.LENGTH_SHORT).show();
        } else if (sp_end_time_minutes.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Start Time", Toast.LENGTH_SHORT).show();
        } else if (!compareTime(start_time_hour + ":" + start_time_minutes, end_time_hour + ":" + end_time_minutes)) {
            Toast.makeText(VisitorManagementActivity.this, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
        }
//        else if (et_additional_no_person.getText().toString().equalsIgnoreCase("0")) {
//            Toast.makeText(getApplicationContext(), "Please Select Atleast One Person.", Toast.LENGTH_SHORT).show();
//            et_additional_no_person.setError("Please Select Atleast One Person.");
//            et_additional_no_person.setFocusable(true);
//            et_additional_no_person.requestFocus();
//        }
        else {
            if (compareDateTimeWithMinutes(strDateTimeWithMinutes, tv_from_date.getText().toString() + " " + start_time_hour + ":" + start_time_minutes)) {

                if (!callFromActivity.equalsIgnoreCase("visitor_list")) {
                    hitNewVisitorApi(et_first_name.getText().toString().trim(), et_last_name.getText().toString().trim(), et_mobile.getText().toString().trim(), et_email.getText().toString().trim(), myPref.getString("Id", "Id"),
                            et_company_name.getText().toString().trim(), et_address.getText().toString().trim(), et_city.getText().toString().trim(), et_pin_code.getText().toString().trim(), rbValue,
                            et_purpose.getText().toString().trim(), start_time_hour + ":" + start_time_minutes, end_time_hour + ":" + end_time_minutes, tv_from_date_value,
                            tv_to_date_value, myPref.getString("Um_div_code", "Um_div_code"), et_additional_no_person.getText().toString().trim(), "");
                } else {
                    hitNewVisitorApi(et_first_name.getText().toString().trim(), et_last_name.getText().toString().trim(), et_mobile.getText().toString().trim(), et_email.getText().toString().trim(), myPref.getString("Id", "Id"),
                            et_company_name.getText().toString().trim(), et_address.getText().toString().trim(), et_city.getText().toString().trim(), et_pin_code.getText().toString().trim(), rbValue,
                            et_purpose.getText().toString().trim(), start_time_hour + ":" + start_time_minutes, end_time_hour + ":" + end_time_minutes, tv_from_date_value,
                            tv_to_date_value, myPref.getString("Um_div_code", "Um_div_code"), et_additional_no_person.getText().toString().trim(), String.valueOf(visitorId));
                }

            } else {
                Toast.makeText(VisitorManagementActivity.this, "Time passed you can not added the details", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void hitNewVisitorApi(String FirstName, String LastName, String Mobile, String EmailId, String EmpCode, String CompanyName, String Address, String City,
                                 String PinCode, String Gender, String Purpose, String TimeIn, String TimeOut, String VisitingDate, String AppointmentDate, String UnitCode, String AddPerson, String id) {
        if (Utility.isOnline(VisitorManagementActivity.this)) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = anInterface.NewVisitor(RetrofitClient2.CKEY, FirstName, LastName, Mobile, EmailId, EmpCode, CompanyName, Address, City,
                    PinCode, Gender, Purpose, TimeIn, TimeOut, VisitingDate, AppointmentDate, UnitCode, AddPerson, id);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
                    dismissProgress();
                    List<AddAbnormality_Model> visitorResponse = response.body();

                    if (visitorResponse != null) {
                        if (visitorResponse.get(0).getColumn1().equalsIgnoreCase("Success")) {
                            Toast.makeText(VisitorManagementActivity.this, "Entry Saved Successfully", Toast.LENGTH_LONG).show();
                            finish();

                        } else {
                            Toast.makeText(VisitorManagementActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(VisitorManagementActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        try {
            if (progress != null)
                progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissProgress() {
        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            et_visitor_mobile_no.setText(cNumber.trim().replace(" ", ""));
                            System.out.println("number is:" + cNumber);
                        }
//                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }
                }
                break;
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(VisitorManagementActivity.this, new String[]{READ_CONTACTS}, PERMISSION_REQUEST_CODE);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted)
//                        Snackbar.make(view, "Permission Granted", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    else {

//                        Snackbar.make(view, "Permission Denied", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(VisitorManagementActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
                                showMessageOKCancel("You need to allow access  the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CONTACTS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(VisitorManagementActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

// edit api

    public void hitGetVisitorDetailApi(String key, String visitorId) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<VisitorDetailModel>> response = anInterface.GetVisitorDetail(key, visitorId);
            response.enqueue(new Callback<List<VisitorDetailModel>>() {
                @Override
                public void onResponse(Call<List<VisitorDetailModel>> call, Response<List<VisitorDetailModel>> response) {
                    dismissProgress();
                    List<VisitorDetailModel> visitorDetailModels = response.body();

                    try {
                        if (visitorDetailModels != null) {

                            et_visitor_mobile_no.setText(visitorDetailModels.get(0).getMobile());
                            et_first_name.setText(visitorDetailModels.get(0).getFirstName());
                            et_last_name.setText(visitorDetailModels.get(0).getLastName() + "");
                            et_email.setText(visitorDetailModels.get(0).getEmailID());
                            et_mobile.setText(visitorDetailModels.get(0).getMobile());
                            et_company_name.setText(visitorDetailModels.get(0).getCompanyName());
                            et_address.setText(visitorDetailModels.get(0).getAddress());
                            et_city.setText(visitorDetailModels.get(0).getCity());
                            et_pin_code.setText(visitorDetailModels.get(0).getPinCode());
                            et_purpose.setText(visitorDetailModels.get(0).getPurpose() + "");
                            tv_from_date.setText(visitorDetailModels.get(0).getAppointmentDate1().toString().replace("-", "/"));
                            tv_to_date.setText(visitorDetailModels.get(0).getAppointmentDate1().toString().replace("-", "/"));
//                            tv_start_time.setText(visitorDetailModels.get(0).getTimeIn() + "");
//                            tv_end_time.setText(visitorDetailModels.get(0).getTimeOut() + "");
                            et_additional_no_person.setText(visitorDetailModels.get(0).getAddPersons() + "");
                            String str = visitorDetailModels.get(0).getTimeIn().toString();
                            String[] arrOfStr = str.split(":");
                            String strEnd = visitorDetailModels.get(0).getTimeOut().toString();
                            String[] arrOfStrEnd = strEnd.split(":");

                            initSpinner(arrOfStr[0], arrOfStr[1], arrOfStrEnd[0], arrOfStrEnd[1]);


                            // for disable
                            et_visitor_mobile_no.setEnabled(false);
                            et_first_name.setEnabled(false);
                            et_last_name.setEnabled(false);
                            et_email.setEnabled(false);
                            et_mobile.setEnabled(false);
                            et_company_name.setEnabled(false);
                            et_address.setEnabled(false);
                            et_city.setEnabled(false);
                            et_pin_code.setEnabled(false);
                            et_purpose.setEnabled(false);

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<VisitorDetailModel>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitGetVisitorDetailbyMobileApi(String key, String mobile) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AutoFillMobileModel>> response = anInterface.GetVisitorDetailbyMobile(key, mobile);
            response.enqueue(new Callback<List<AutoFillMobileModel>>() {
                @Override
                public void onResponse(Call<List<AutoFillMobileModel>> call, Response<List<AutoFillMobileModel>> response) {
                    dismissProgress();
                    List<AutoFillMobileModel> autoFillMobileModels = response.body();

                    try {
                        if (autoFillMobileModels.size() != 0) {
                            callCondition = false;
                            et_visitor_mobile_no.setText(autoFillMobileModels.get(0).getMobile());
                            et_first_name.setText(autoFillMobileModels.get(0).getFirstName());
                            et_last_name.setText(autoFillMobileModels.get(0).getLastName() + "");
                            et_email.setText(autoFillMobileModels.get(0).getEmailID());
                            et_mobile.setText(autoFillMobileModels.get(0).getMobile());
                            et_company_name.setText(autoFillMobileModels.get(0).getCompanyName());
                            et_address.setText(autoFillMobileModels.get(0).getAddress());
                            et_city.setText(autoFillMobileModels.get(0).getCity());
                            et_pin_code.setText(autoFillMobileModels.get(0).getPincode());
//                            et_purpose.setText(autoFillMobileModels.get(0).getP() + "");
//                            tv_from_date.setText(autoFillMobileModels.get(0).getAppointmentDate1() + "");
//                            tv_to_date.setText(autoFillMobileModels.get(0).getVisitingDate1() + "");
//                            tv_start_time.setText(autoFillMobileModels.get(0).getTimeIn() + "");
//                            tv_end_time.setText(autoFillMobileModels.get(0).getTimeOut() + "");
//                            et_additional_no_person.setText(autoFillMobileModels.get(0).getAddPersons() + "");
                            if (autoFillMobileModels.get(0).getGender().equalsIgnoreCase("M")) {
                                RadioButton radioButton = (RadioButton) findViewById(R.id.rb_male);
                                radioButton.setChecked(true);
                            } else {
                                RadioButton radioButton = (RadioButton) findViewById(R.id.rb_female);
                                radioButton.setChecked(true);
                            }

                        } else {
                            callCondition = false;
                            et_first_name.setText("");
                            et_last_name.setText("");
                            et_email.setText("");
//                            et_mobile.setText("");
//                            et_mobile.setEnabled(true);
                            et_company_name.setText("");
                            et_address.setText("");
                            et_city.setText("");
                            et_pin_code.setText("");
                            RadioButton radioButton = (RadioButton) findViewById(R.id.rb_male);
                            radioButton.setChecked(true);
//                            et_purpose.setText(autoFillMobileModels.get(0).getP() + "");
//                            tv_from_date.setText(autoFillMobileModels.get(0).getAppointmentDate1() + "");
//                            tv_to_date.setText(autoFillMobileModels.get(0).getVisitingDate1() + "");
//                            tv_start_time.setText(autoFillMobileModels.get(0).getTimeIn() + "");
//                            tv_end_time.setText(autoFillMobileModels.get(0).getTimeOut() + "");
//                            et_additional_no_person.setText(autoFillMobileModels.get(0).getAddPersons() + "");


//                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<AutoFillMobileModel>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private int getPositionHour(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (hourList.get(i).contains(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getPositionMinutes(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (minuteList.get(i).contains(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static boolean checkDate(String systemDate, String serverDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String str1 = systemDate;
            Date date1 = formatter.parse(str1);

            String str2 = serverDate;
            Date date2 = formatter.parse(str2);

//            if (date1.compareTo(date2) < 0)
            if (date1.getTime() <= date2.getTime()) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private boolean compareTime(String firstTime, String secondTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

            String str1 = firstTime;
            Date date1 = formatter.parse(str1);

            String str2 = secondTime;
            Date date2 = formatter.parse(str2);
            if (date1.getTime() < date2.getTime()) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private boolean compareDateTime(String firstDateTime, String secondDateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH");

            Date date1 = formatter.parse(firstDateTime);

            Date date2 = formatter.parse(secondDateTime);
            if (date1.getTime() <= date2.getTime()) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private boolean compareDateTimeWithMinutes(String firstDateTime, String secondDateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            Date date1 = formatter.parse(firstDateTime);

            Date date2 = formatter.parse(secondDateTime);
            if (date1.getTime() <= date2.getTime()) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }


}
