package com.minda.sparsh.fragment;


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
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAppointmentFragment extends BaseFragment {
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

    public NewAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_new_appointment, container, false);
//        myPref = activity.getSharedPreferences("MyPref", MODE_PRIVATE);
//
//        progress = new ProgressDialog(getActivity());
//        progress.setMessage("Please wait...");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
//        btn_submit = (Button) convertView.findViewById(R.id.btn_submit);
//        lay_rest_content = (LinearLayout) convertView.findViewById(R.id.lay_rest_content);
//        lay_contact = (LinearLayout) convertView.findViewById(R.id.lay_contact);
//        tv_start_time = convertView.findViewById(R.id.tv_start_time);
//        tv_end_time = convertView.findViewById(R.id.tv_end_time);
//        tv_decrement = convertView.findViewById(R.id.tv_decrement);
//        tv_increment = convertView.findViewById(R.id.tv_increment);
//
//        tv_from_date = convertView.findViewById(R.id.tv_from_date);
//        tv_to_date = convertView.findViewById(R.id.tv_to_date);
//        String currentDateTimeString = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
//        tv_from_date.setText(currentDateTimeString);
//        tv_to_date.setText(currentDateTimeString);
//        String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
//
//        tv_from_date_value = currentDate;
//        tv_to_date_value = currentDate;
//        et_visitor_mobile_no = (EditText) convertView.findViewById(R.id.et_visitor_mobile_no);
//        et_first_name = (EditText) convertView.findViewById(R.id.et_first_name);
//        et_last_name = (EditText) convertView.findViewById(R.id.et_last_name);
//        et_email = (EditText) convertView.findViewById(R.id.et_email);
//        et_mobile = (EditText) convertView.findViewById(R.id.et_mobile);
//        et_company_name = (EditText) convertView.findViewById(R.id.et_company_name);
//        et_address = (EditText) convertView.findViewById(R.id.et_address);
//        et_city = (EditText) convertView.findViewById(R.id.et_city);
//        et_pin_code = (EditText) convertView.findViewById(R.id.et_pin_code);
//        et_purpose = (EditText) convertView.findViewById(R.id.et_purpose);
//        et_additional_no_person = (TextView) convertView.findViewById(R.id.et_additional_no_person);
//        tv_increment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                et_additional_no_person.setText(String.valueOf((Integer.parseInt(et_additional_no_person.getText().toString())) + 1));
//
//            }
//        });
//
//        tv_decrement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!et_additional_no_person.getText().toString().equalsIgnoreCase("1")) {
//                    et_additional_no_person.setText(String.valueOf((Integer.parseInt(et_additional_no_person.getText().toString())) - 1));
//
//                }
//
//            }
//        });
//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validateFields();
//            }
//        });
//        rg = (RadioGroup) convertView.findViewById(R.id.rg);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.rb_male:
//                        rbValue = "M";
//                        break;
//                    case R.id.rb_female:
//                        rbValue = "F";
//                        break;
//                }
//            }
//        });
//        et_visitor_mobile_no.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////                if (charSequence.length() == 10) {
//                et_mobile.setText(et_visitor_mobile_no.getText().toString());
////                    lay_rest_content.setVisibility(View.VISIBLE);
//
////                } else {
////                    et_mobile.setText("");
////
//////                    lay_rest_content.setVisibility(View.GONE);
////                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        tv_from_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dateType = 1;
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//            }
//        });
//        tv_to_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dateType = 2;
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
//            }
//        });
//        tv_start_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                timePickerDialog(1);
//            }
//        });
//        tv_end_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                timePickerDialog(2);
//
//            }
//        });
//
//        lay_contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!checkPermission() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermission();
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                    startActivityForResult(intent, PICK_CONTACT);
//                }
//            }
//        });
        return convertView;

    }


//    private void timePickerDialog(final int type) {
//        // TODO Auto-generated method stub
//        Calendar mcurrentTime = Calendar.getInstance();
//        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//        int minute = mcurrentTime.get(Calendar.MINUTE);
//        TimePickerDialog mTimePicker;
//        mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                if (type == 1) {
//                    tv_start_time.setText(selectedHour + ":" + selectedMinute);
//
//                } else {
//                    tv_end_time.setText(selectedHour + ":" + selectedMinute);
//
//                }
//            }
//        }, hour, minute, true);//Yes 24 hour time
//        mTimePicker.setTitle("Select Time");
//        mTimePicker.show();
//
//    }
//
//
//    public static class DatePickerFragment extends DialogFragment
//            implements DatePickerDialog.OnDateSetListener {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
////            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
//            return dialog;
//        }
//
//        public void onDateSet(DatePicker view, int year, int month, int day) {
//            if (dateType == 1) {
//                tv_from_date.setText(day + "/" + (month + 1) + "/" + year); //dd/mm/yyyy
//                tv_from_date_value = (month + 1) + "/" + day + "/" + year;
//            } else {
//                tv_to_date.setText(day + "/" + (month + 1) + "/" + year); //dd/mm/yyyy
//                tv_to_date_value = (month + 1) + "/" + day + "/" + year;
//            }
//
//        }
//    }
//
//
//    private void validateFields() {
//        if (TextUtils.isEmpty(et_first_name.getText().toString().trim())) {
//            et_first_name.setError("Please fill it.");
//            et_first_name.setFocusable(true);
//            et_first_name.requestFocus();
//        }
////        else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
////            et_email.setError("Please fill it.");
////            et_email.setFocusable(true);
////            et_email.requestFocus();
////        }
//        else if (!et_email.getText().toString().trim().equalsIgnoreCase("")) {
//            if (!et_email.getText().toString().trim().matches(emailPattern)) {
//                et_email.setError("Please fill valid email.");
//                et_email.setFocusable(true);
//                et_email.requestFocus();
//            }
//        } else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
//            et_mobile.setError("Please fill it.");
//            et_mobile.setFocusable(true);
//            et_mobile.requestFocus();
//        } else if (et_mobile.getText().toString().length() < 10) {
//            et_mobile.setError("Please fill 10 digit.");
//            et_mobile.setFocusable(true);
//            et_mobile.requestFocus();
//        } else if (TextUtils.isEmpty(et_company_name.getText().toString().trim())) {
//            et_company_name.setError("Please fill it.");
//            et_company_name.setFocusable(true);
//            et_company_name.requestFocus();
//        }
////        else if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
////            et_city.setError("Please fill it.");
////            et_city.setFocusable(true);
////            et_city.requestFocus();
////        }
//        else if (TextUtils.isEmpty(tv_start_time.getText().toString().trim())) {
//            tv_start_time.setError("Please fill it.");
//            tv_start_time.setFocusable(true);
//            tv_start_time.requestFocus();
//        } else if (TextUtils.isEmpty(tv_end_time.getText().toString().trim())) {
//            tv_end_time.setError("Please fill it.");
//            tv_end_time.setFocusable(true);
//            tv_end_time.requestFocus();
//        } else {
//            hitNewVisitorApi(et_first_name.getText().toString().trim(), et_last_name.getText().toString().trim(), et_mobile.getText().toString().trim(), et_email.getText().toString().trim(), myPref.getString("Id", "Id"),
//                    et_company_name.getText().toString().trim(), et_address.getText().toString().trim(), et_city.getText().toString().trim(), et_pin_code.getText().toString().trim(), rbValue,
//                    et_purpose.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim(), tv_from_date_value,
//                    tv_to_date_value, myPref.getString("Um_div_code", "Um_div_code"), et_additional_no_person.getText().toString().trim());
//        }
//
//
//    }
//
//    public void hitNewVisitorApi(String FirstName, String LastName, String Mobile, String EmailId, String EmpCode, String CompanyName, String Address, String City,
//                                 String PinCode, String Gender, String Purpose, String TimeIn, String TimeOut, String VisitingDate, String AppointmentDate, String UnitCode, String AddPerson) {
//        if (Utility.isOnline(activity)) {
//            showProgress();
//            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
//            Call<List<AddAbnormality_Model>> response = anInterface.NewVisitor(RetrofitClient2.CKEY, FirstName, LastName, Mobile, EmailId, EmpCode, CompanyName, Address, City,
//                    PinCode, Gender, Purpose, TimeIn, TimeOut, VisitingDate, AppointmentDate, UnitCode, AddPerson);
//            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
//                @Override
//                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
//                    dismissProgress();
//                    List<AddAbnormality_Model> visitorResponse = response.body();
//
//                    if (visitorResponse != null) {
//                        if (visitorResponse.get(0).getColumn1().equalsIgnoreCase("Success")) {
//                            Toast.makeText(getActivity(), "Entry Saved Successfully", Toast.LENGTH_LONG).show();
//                            getActivity().finish();
//
//                        } else {
//                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {
//                    dismissProgress();
//                }
//            });
//        } else
//            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
//    }
//
//    private void showProgress() {
//        try {
//            if (progress != null)
//                progress.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void dismissProgress() {
//        try {
//            if (progress != null)
//                progress.dismiss();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //code
//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        switch (reqCode) {
//            case (PICK_CONTACT):
//                if (resultCode == Activity.RESULT_OK) {
//
//                    Uri contactData = data.getData();
//                    Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//
//
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//                        if (hasPhone.equalsIgnoreCase("1")) {
//                            Cursor phones = getActivity().getContentResolver().query(
//                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
//                                    null, null);
//                            phones.moveToFirst();
//                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
//                            et_visitor_mobile_no.setText(cNumber.trim().replace(" ", ""));
//                            System.out.println("number is:" + cNumber);
//                        }
////                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//
//                    }
//                }
//                break;
//        }
//    }
//
//
//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getActivity(), READ_CONTACTS);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission() {
//
//        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_CONTACTS}, PERMISSION_REQUEST_CODE);
//
//    }
//
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0) {
//
//                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (locationAccepted)
////                        Snackbar.make(view, "Permission Granted", Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
//                    else {
//
////                        Snackbar.make(view, "Permission Denied", Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//                                showMessageOKCancel("You need to allow access  the permissions",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{READ_CONTACTS},
//                                                            PERMISSION_REQUEST_CODE);
//                                                }
//                                            }
//                                        });
//                                return;
//                            }
//                        }
//
//                    }
//                }
//
//
//                break;
//        }
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(getActivity())
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

}