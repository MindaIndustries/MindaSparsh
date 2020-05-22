package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.DepartmentSpinnerAdapter;
import com.minda.sparsh.model.DWMDetailResponse;
import com.minda.sparsh.model.Department_Model;
import com.minda.sparsh.model.DwmResponse;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DWMMfgHeadActivity extends BaseActivity {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;

    TextView tv_submit, tv_select_date, tv_total, user_name;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    String Addtype = "Add";


    EditText H_1_1, C_1_1,

    H_2_1, H_2_2, H_2_3, H_2_4, H_2_5, H_2_6, H_2_7, H_2_8, H_2_9,
            C_2_1, C_2_2, C_2_3, C_2_4, C_2_5, C_2_6, C_2_7, C_2_8, C_2_9,

    H_3_1, H_3_2, H_3_3, H_3_4, H_3_5, H_3_6, H_3_7,
            C_3_1, C_3_2, C_3_3, C_3_4, C_3_5, C_3_6, C_3_7,

    H_4_1, C_4_1,

    check_C_4_1, check_4_1,

    H_5_1, C_5_1,

    H_6_1, H_6_2, H_6_3, H_6_4,
            C_6_1, C_6_2, C_6_3, C_6_4,

    H_7_1, H_7_2, H_7_3,
            C_7_1, C_7_2, C_7_3,

    H_8_1, H_8_2, C_8_1, C_8_2,

    H_9_1, H_9_2, H_9_3, H_9_4, H_9_5, H_9_6, H_9_7, H_9_8,
            C_9_1, C_9_2, C_9_3, C_9_4, C_9_5, C_9_6, C_9_7, C_9_8,

    H_10_1, H_10_2, H_10_3,
            C_10_1, C_10_2, C_10_3,

    H_11_1, H_11_2, H_11_3, H_11_4, H_11_5, H_11_6, H_11_7, H_11_8,
            C_11_1, C_11_2, C_11_3, C_11_4, C_11_5, C_11_6, C_11_7, C_11_8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwmmfg_head);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_select_date = (TextView) findViewById(R.id.tv_select_date);

        String day, month;

        if (String.valueOf(mDay).length() == 1) {
            day = "0" + mDay;
        } else {
            day = String.valueOf(mDay);
        }
        if (String.valueOf(mMonth + 1).length() == 1) {
            month = "0" + (mMonth + 1);
        } else {
            month = String.valueOf(mMonth + 1);
        }

//        tv_select_date.setText(mYear + "-" + month + "-"
//                + day);
        tv_select_date.setText(day + "-" + month + "-" + mYear);


        H_1_1 = (EditText) findViewById(R.id.H_1_1);
        C_1_1 = (EditText) findViewById(R.id.C_1_1);

        H_2_1 = (EditText) findViewById(R.id.H_2_1);
        H_2_2 = (EditText) findViewById(R.id.H_2_2);
        H_2_3 = (EditText) findViewById(R.id.H_2_3);
        H_2_4 = (EditText) findViewById(R.id.H_2_4);
        H_2_5 = (EditText) findViewById(R.id.H_2_5);
        H_2_6 = (EditText) findViewById(R.id.H_2_6);
        H_2_7 = (EditText) findViewById(R.id.H_2_7);
        H_2_8 = (EditText) findViewById(R.id.H_2_8);
        H_2_9 = (EditText) findViewById(R.id.H_2_9);

        C_2_1 = (EditText) findViewById(R.id.C_2_1);
        C_2_2 = (EditText) findViewById(R.id.C_2_2);
        C_2_3 = (EditText) findViewById(R.id.C_2_3);
        C_2_4 = (EditText) findViewById(R.id.C_2_4);
        C_2_5 = (EditText) findViewById(R.id.C_2_5);
        C_2_6 = (EditText) findViewById(R.id.C_2_6);
        C_2_7 = (EditText) findViewById(R.id.C_2_7);
        C_2_8 = (EditText) findViewById(R.id.C_2_8);
        C_2_9 = (EditText) findViewById(R.id.C_2_9);

        H_3_1 = (EditText) findViewById(R.id.H_3_1);
        H_3_2 = (EditText) findViewById(R.id.H_3_2);
        H_3_3 = (EditText) findViewById(R.id.H_3_3);
        H_3_4 = (EditText) findViewById(R.id.H_3_4);
        H_3_5 = (EditText) findViewById(R.id.H_3_5);
        H_3_6 = (EditText) findViewById(R.id.H_3_6);
        H_3_7 = (EditText) findViewById(R.id.H_3_7);
        C_3_1 = (EditText) findViewById(R.id.C_3_1);
        C_3_2 = (EditText) findViewById(R.id.C_3_2);
        C_3_3 = (EditText) findViewById(R.id.C_3_3);
        C_3_4 = (EditText) findViewById(R.id.C_3_4);
        C_3_5 = (EditText) findViewById(R.id.C_3_5);
        C_3_6 = (EditText) findViewById(R.id.C_3_6);
        C_3_7 = (EditText) findViewById(R.id.C_3_7);

        check_C_4_1 = (EditText) findViewById(R.id.check_C_4_1);
        check_4_1 = (EditText) findViewById(R.id.check_4_1);

        H_4_1 = (EditText) findViewById(R.id.H_4_1);
        C_4_1 = (EditText) findViewById(R.id.C_4_1);


        H_5_1 = (EditText) findViewById(R.id.H_5_1);
        C_5_1 = (EditText) findViewById(R.id.C_5_1);

        H_6_1 = (EditText) findViewById(R.id.H_6_1);
        H_6_2 = (EditText) findViewById(R.id.H_6_2);
        H_6_3 = (EditText) findViewById(R.id.H_6_3);
        H_6_4 = (EditText) findViewById(R.id.H_6_4);
        C_6_1 = (EditText) findViewById(R.id.C_6_1);
        C_6_2 = (EditText) findViewById(R.id.C_6_2);
        C_6_3 = (EditText) findViewById(R.id.C_6_3);
        C_6_4 = (EditText) findViewById(R.id.C_6_4);

        H_7_1 = (EditText) findViewById(R.id.H_7_1);
        H_7_2 = (EditText) findViewById(R.id.H_7_2);
        H_7_3 = (EditText) findViewById(R.id.H_7_3);
        C_7_1 = (EditText) findViewById(R.id.C_7_1);
        C_7_2 = (EditText) findViewById(R.id.C_7_2);
        C_7_3 = (EditText) findViewById(R.id.C_7_3);

        H_8_1 = (EditText) findViewById(R.id.H_8_1);
        C_8_1 = (EditText) findViewById(R.id.C_8_1);
        H_8_2 = (EditText) findViewById(R.id.H_8_2);
        C_8_2 = (EditText) findViewById(R.id.C_8_2);

        H_9_1 = (EditText) findViewById(R.id.H_9_1);
        H_9_2 = (EditText) findViewById(R.id.H_9_2);
        H_9_3 = (EditText) findViewById(R.id.H_9_3);
        H_9_4 = (EditText) findViewById(R.id.H_9_4);
        H_9_5 = (EditText) findViewById(R.id.H_9_5);
        H_9_6 = (EditText) findViewById(R.id.H_9_6);
        H_9_7 = (EditText) findViewById(R.id.H_9_7);
        H_9_8 = (EditText) findViewById(R.id.H_9_8);
        C_9_1 = (EditText) findViewById(R.id.C_9_1);
        C_9_2 = (EditText) findViewById(R.id.C_9_2);
        C_9_3 = (EditText) findViewById(R.id.C_9_3);
        C_9_4 = (EditText) findViewById(R.id.C_9_4);
        C_9_5 = (EditText) findViewById(R.id.C_9_5);
        C_9_6 = (EditText) findViewById(R.id.C_9_6);
        C_9_7 = (EditText) findViewById(R.id.C_9_7);
        C_9_8 = (EditText) findViewById(R.id.C_9_8);


        H_10_1 = (EditText) findViewById(R.id.H_10_1);
        H_10_2 = (EditText) findViewById(R.id.H_10_2);
        H_10_3 = (EditText) findViewById(R.id.H_10_3);
        C_10_1 = (EditText) findViewById(R.id.C_10_1);
        C_10_2 = (EditText) findViewById(R.id.C_10_2);
        C_10_3 = (EditText) findViewById(R.id.C_10_3);


        H_11_1 = (EditText) findViewById(R.id.H_11_1);
        H_11_2 = (EditText) findViewById(R.id.H_11_2);
        H_11_3 = (EditText) findViewById(R.id.H_11_3);
        H_11_4 = (EditText) findViewById(R.id.H_11_4);
        H_11_5 = (EditText) findViewById(R.id.H_11_5);
        H_11_6 = (EditText) findViewById(R.id.H_11_6);
        H_11_7 = (EditText) findViewById(R.id.H_11_7);
        H_11_8 = (EditText) findViewById(R.id.H_11_8);
        C_11_1 = (EditText) findViewById(R.id.C_11_1);
        C_11_2 = (EditText) findViewById(R.id.C_11_2);
        C_11_3 = (EditText) findViewById(R.id.C_11_3);
        C_11_4 = (EditText) findViewById(R.id.C_11_4);
        C_11_5 = (EditText) findViewById(R.id.C_11_5);
        C_11_6 = (EditText) findViewById(R.id.C_11_6);
        C_11_7 = (EditText) findViewById(R.id.C_11_7);
        C_11_8 = (EditText) findViewById(R.id.C_11_8);

        tv_total = (TextView) findViewById(R.id.tv_total);
        user_name = (TextView) findViewById(R.id.user_name);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        user_name.setText(myPref.getString("username", ""));


        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);


//        hitGetManufacturingDetails(parseDateToddMMyyyy(tv_select_date.getText().toString()).replace("-", "/"));
        hitGetManufacturingDetails(tv_select_date.getText().toString().replace("-", "/"));


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitNewManufacturingHead();
            }
        });


        tv_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        tv_select_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


//                hitGetManufacturingDetails(parseDateToddMMyyyy(tv_select_date.getText().toString()).replace("-", "/"));
                hitGetManufacturingDetails(tv_select_date.getText().toString().replace("-", "/"));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        changeListenerAdd();

    }


    public void hitNewManufacturingHead() {
        if (Utility.isOnline(DWMMfgHeadActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<DwmResponse>> response = promotingMyinterface.GetNewManufacturingHead(myPref.getString("Id", "Id"),
                    H_1_1.getText().toString(), H_2_1.getText().toString(), H_2_2.getText().toString(), H_2_3.getText().toString(),
                    H_2_4.getText().toString(), H_2_5.getText().toString(), H_2_6.getText().toString(), H_2_7.getText().toString(), H_2_8.getText().toString(),
                    H_2_9.getText().toString(), H_3_1.getText().toString(), H_3_2.getText().toString(), H_3_3.getText().toString(), H_3_4.getText().toString(),
                    H_3_5.getText().toString(), H_3_6.getText().toString(), H_3_7.getText().toString(), H_4_1.getText().toString(), H_5_1.getText().toString(),
                    H_6_1.getText().toString(), H_6_2.getText().toString(), H_6_3.getText().toString(), H_6_4.getText().toString(),
                    H_7_1.getText().toString(), H_7_2.getText().toString(), H_7_3.getText().toString(), H_8_1.getText().toString(), H_8_2.getText().toString(),
                    H_9_1.getText().toString(), H_9_2.getText().toString(), H_9_3.getText().toString(), H_9_4.getText().toString(), H_9_5.getText().toString(),
                    H_9_6.getText().toString(), H_9_7.getText().toString(), H_9_8.getText().toString(), H_10_1.getText().toString(), H_10_2.getText().toString(),
                    H_10_3.getText().toString(),
                    H_11_1.getText().toString(), H_11_2.getText().toString(), H_11_3.getText().toString(), H_11_4.getText().toString(), H_11_5.getText().toString(),
                    H_11_6.getText().toString(), H_11_7.getText().toString(), H_11_8.getText().toString(), C_1_1.getText().toString(),
                    C_2_1.getText().toString(), C_2_2.getText().toString(), C_2_3.getText().toString(), C_2_4.getText().toString(), C_2_5.getText().toString(),
                    C_2_6.getText().toString(), C_2_7.getText().toString(), C_2_8.getText().toString(), C_2_9.getText().toString(), C_3_1.getText().toString(),
                    C_3_2.getText().toString(), C_3_3.getText().toString(), C_3_4.getText().toString(), C_3_5.getText().toString(), C_3_6.getText().toString(),
                    C_3_7.getText().toString(), C_4_1.getText().toString(),
                    C_5_1.getText().toString(), C_6_1.getText().toString(), C_6_2.getText().toString(), C_6_3.getText().toString(), C_6_4.getText().toString(),
                    C_7_1.getText().toString(), C_7_2.getText().toString(), C_7_3.getText().toString(), C_8_1.getText().toString(), "",
                    C_9_1.getText().toString(), C_9_2.getText().toString(), C_9_3.getText().toString(), C_9_4.getText().toString(), C_9_5.getText().toString(),
                    C_9_6.getText().toString(), C_9_7.getText().toString(), C_9_8.getText().toString(),
                    C_10_1.getText().toString(), C_10_2.getText().toString(), C_10_3.getText().toString(), C_11_1.getText().toString(), C_11_2.getText().toString(),
                    C_11_3.getText().toString(), C_11_4.getText().toString(), C_11_5.getText().toString(), C_11_6.getText().toString(), C_11_7.getText().toString(),
                    C_11_8.getText().toString(),
                    "", "", parseDateToddMMyyyy(tv_select_date.getText().toString()), "", "", "", Addtype, RetrofitClient2.CKEY,
                    check_4_1.getText().toString(), check_C_4_1.getText().toString());
            response.enqueue(new Callback<List<DwmResponse>>() {
                @Override
                public void onResponse(Call<List<DwmResponse>> call, Response<List<DwmResponse>> response) {
                    showProgress(false);
                    List<DwmResponse> Responsedata = response.body();
                    if (Responsedata != null) {
                        if (Responsedata.get(0).getColumn1().equalsIgnoreCase("Success")) {
                            Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
//                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), Responsedata.get(0).getColumn1(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<DwmResponse>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(DWMMfgHeadActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean b) {
        try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {

        DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay);
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 0); // Add 0 days to Ca
        Date newDate = calendar.getTime();
        datePicker.getDatePicker().setMinDate(newDate.getTime() - 172800000L);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePicker;
//                new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String day = null, month = null;
            if (String.valueOf(selectedDay).length() == 1) {
                day = "0" + selectedDay;
            } else {
                day = String.valueOf(selectedDay);
            }
            if (String.valueOf(selectedMonth + 1).length() == 1) {
                month = "0" + (selectedMonth + 1);
            } else {
                month = String.valueOf(selectedMonth + 1);
            }


//            tv_select_date.setText(selectedYear + "-" + month + "-"
//                    + day);

            tv_select_date.setText(day + "-" + month + "-" + selectedYear);
        }
    };
//    picturePath =picturePath !=null?picturePath :"";

    public int Gettotal() {

        String s1 = H_1_1.getText().toString();
        String s2 = H_2_1.getText().toString();
        String s3 = H_2_2.getText().toString();
        String s4 = H_2_3.getText().toString();
        String s5 = H_2_4.getText().toString();
        String s6 = H_2_5.getText().toString();
        String s7 = H_2_6.getText().toString();
        String s8 = H_2_7.getText().toString();
        String s9 = H_2_8.getText().toString();
        String s10 = H_2_9.getText().toString();
        String s11 = H_3_1.getText().toString();
        String s12 = H_3_2.getText().toString();
        String s13 = H_3_3.getText().toString();
        String s14 = H_3_4.getText().toString();
        String s15 = H_3_5.getText().toString();
        String s16 = H_3_6.getText().toString();
        String s17 = H_3_7.getText().toString();
        String s18 = H_4_1.getText().toString();
        String s19 = H_5_1.getText().toString();
        String s20 = H_6_1.getText().toString();
        String s21 = H_6_2.getText().toString();
        String s22 = H_6_3.getText().toString();
        String s23 = H_6_4.getText().toString();
        String s24 = H_7_1.getText().toString();
        String s25 = H_7_2.getText().toString();
        String s26 = H_7_3.getText().toString();
        String s27 = H_8_1.getText().toString();
        String s28 = H_8_1.getText().toString();
        String s29 = H_9_1.getText().toString();
        String s30 = H_9_2.getText().toString();
        String s31 = H_9_3.getText().toString();
        String s32 = H_9_4.getText().toString();
        String s33 = H_9_5.getText().toString();
        String s34 = H_9_6.getText().toString();
        String s35 = H_9_7.getText().toString();
        String s36 = H_9_8.getText().toString();
        String s37 = H_10_1.getText().toString();
        String s38 = H_10_2.getText().toString();
        String s39 = H_10_3.getText().toString();
        String s40 = H_11_1.getText().toString();
        String s41 = H_11_2.getText().toString();
        String s42 = H_11_3.getText().toString();
        String s43 = H_11_4.getText().toString();
        String s44 = H_11_5.getText().toString();
        String s45 = H_11_6.getText().toString();
        String s46 = H_11_7.getText().toString();
        String s47 = H_11_8.getText().toString();
        String s48 = check_4_1.getText().toString();

        s1 = !s1.equals("") ? s1 : "0";
        s2 = !s2.equals("") ? s2 : "0";
        s3 = !s3.equals("") ? s3 : "0";
        s4 = !s4.equals("") ? s4 : "0";
        s5 = !s5.equals("") ? s5 : "0";
        s6 = !s6.equals("") ? s6 : "0";
        s7 = !s7.equals("") ? s7 : "0";
        s8 = !s8.equals("") ? s8 : "0";
        s9 = !s9.equals("") ? s9 : "0";
        s10 = !s10.equals("") ? s10 : "0";
        s11 = !s11.equals("") ? s11 : "0";
        s12 = !s12.equals("") ? s12 : "0";
        s13 = !s13.equals("") ? s13 : "0";
        s14 = !s14.equals("") ? s14 : "0";
        s15 = !s15.equals("") ? s15 : "0";
        s16 = !s16.equals("") ? s16 : "0";
        s17 = !s17.equals("") ? s17 : "0";
        s18 = !s18.equals("") ? s18 : "0";
        s19 = !s19.equals("") ? s19 : "0";
        s20 = !s20.equals("") ? s20 : "0";
        s21 = !s21.equals("") ? s21 : "0";
        s22 = !s22.equals("") ? s22 : "0";
        s23 = !s23.equals("") ? s23 : "0";
        s24 = !s24.equals("") ? s24 : "0";
        s25 = !s25.equals("") ? s25 : "0";
        s26 = !s26.equals("") ? s26 : "0";
        s27 = !s27.equals("") ? s27 : "0";
        s28 = !s28.equals("") ? s28 : "0";
        s29 = !s29.equals("") ? s29 : "0";
        s30 = !s30.equals("") ? s30 : "0";
        s31 = !s31.equals("") ? s31 : "0";
        s32 = !s32.equals("") ? s32 : "0";
        s33 = !s33.equals("") ? s33 : "0";
        s34 = !s34.equals("") ? s34 : "0";
        s35 = !s35.equals("") ? s35 : "0";
        s36 = !s36.equals("") ? s36 : "0";
        s37 = !s37.equals("") ? s37 : "0";
        s38 = !s38.equals("") ? s38 : "0";
        s39 = !s39.equals("") ? s39 : "0";
        s40 = !s40.equals("") ? s40 : "0";
        s41 = !s41.equals("") ? s41 : "0";
        s42 = !s42.equals("") ? s42 : "0";
        s43 = !s43.equals("") ? s43 : "0";
        s44 = !s44.equals("") ? s44 : "0";
        s45 = !s45.equals("") ? s45 : "0";
        s46 = !s46.equals("") ? s46 : "0";
        s47 = !s47.equals("") ? s47 : "0";
        s48 = !s48.equals("") ? s48 : "0";

        int total = Integer.parseInt(s1) +
                Integer.parseInt(s2) +
                Integer.parseInt(s3) +
                Integer.parseInt(s4) +
                Integer.parseInt(s5) +
                Integer.parseInt(s6) +
                Integer.parseInt(s7) +
                Integer.parseInt(s8) +
                Integer.parseInt(s9) +
                Integer.parseInt(s10) +
                Integer.parseInt(s11) +
                Integer.parseInt(s12) +
                Integer.parseInt(s13) +
                Integer.parseInt(s14) +
                Integer.parseInt(s15) +
                Integer.parseInt(s16) +
                Integer.parseInt(s17) +
                Integer.parseInt(s18) +
                Integer.parseInt(s19) +
                Integer.parseInt(s20) +
                Integer.parseInt(s21) +
                Integer.parseInt(s22) +
                Integer.parseInt(s23) +
                Integer.parseInt(s24) +
                Integer.parseInt(s25) +
                Integer.parseInt(s26) +
                Integer.parseInt(s27) +
                Integer.parseInt(s28) +
                Integer.parseInt(s29) +
                Integer.parseInt(s30) +
                Integer.parseInt(s31) +
                Integer.parseInt(s32) +
                Integer.parseInt(s33) +
                Integer.parseInt(s34) +
                Integer.parseInt(s35) +
                Integer.parseInt(s36) +
                Integer.parseInt(s37) +
                Integer.parseInt(s38) +
                Integer.parseInt(s39) +
                Integer.parseInt(s40) +
                Integer.parseInt(s41) +
                Integer.parseInt(s42) +
                Integer.parseInt(s43) +
                Integer.parseInt(s44) +
                Integer.parseInt(s45) +
                Integer.parseInt(s46) +
                Integer.parseInt(s47) +
                Integer.parseInt(s48);


        return total;
    }

    public void hitGetManufacturingDetails(String date) {
        if (Utility.isOnline(DWMMfgHeadActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<DWMDetailResponse>> response = promotingMyinterface.GetManufacturingDetails(myPref.getString("Id", "Id"), date, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<DWMDetailResponse>>() {
                @Override
                public void onResponse(Call<List<DWMDetailResponse>> call, Response<List<DWMDetailResponse>> response) {
                    showProgress(false);
                    List<DWMDetailResponse> Responsedata = response.body();
//


                    if (Responsedata != null && Responsedata.size() != 0) {

                        setvalue(Responsedata);
                        Addtype = "Update";


                    } else {
                        Addtype = "Add";
                        setvalue();
//                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<DWMDetailResponse>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(DWMMfgHeadActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void setvalue(List<DWMDetailResponse> list) {
        H_1_1.setText(list.get(0).getDailyMMeeting());
        H_2_1.setText(list.get(0).getDailyOneLine());
        H_2_2.setText(list.get(0).getBoardReview());
        H_2_3.setText(list.get(0).getProductionRules());
        H_2_4.setText(list.get(0).getDailyShortageReview());
        H_2_5.setText(list.get(0).getSReview());
        H_2_6.setText(list.get(0).getMaterialFlow());
        H_2_7.setText(list.get(0).getBreakdownReview());
        H_2_8.setText(list.get(0).getLossessReview());
        H_2_9.setText(list.get(0).getDepartmentDWM());
        H_3_1.setText(list.get(0).getQualityReview());
        H_3_2.setText(list.get(0).getConcernCorner());
        H_3_3.setText(list.get(0).getRedBinArea());
        H_3_4.setText(list.get(0).getMControl());
        H_3_5.setText(list.get(0).getQIPReview());
        H_3_6.setText(list.get(0).getInternalRejection());
        H_3_7.setText(list.get(0).getToolDuplicationReview());
        H_4_1.setText(list.get(0).getAllProductionDepartment());
        H_5_1.setText(list.get(0).getCustomerVisits());
        H_6_1.setText(list.get(0).getMaterialsaving());
        H_6_2.setText(list.get(0).getInventoryReview());
        H_6_3.setText(list.get(0).getIn1Review());
        H_6_4.setText(list.get(0).getEnergyReview());
        H_7_1.setText(list.get(0).getUnitAddress());
        H_7_2.setText(list.get(0).getTraining());
        H_7_3.setText(list.get(0).getBESTSupport());
        H_8_1.setText(list.get(0).getUnitQCDreview());


        H_8_2.setText(list.get(0).getMeetingasperInterunit());
        H_9_1.setText(list.get(0).getMMC());
        H_9_2.setText(list.get(0).getSystemMeeting());
        H_9_3.setText(list.get(0).getIndent());
        H_9_4.setText(list.get(0).getDevelopment());
        H_9_5.setText(list.get(0).getSAPAdherence());
        H_9_6.setText(list.get(0).getManufacturingRoadMap());
        H_9_7.setText(list.get(0).getSafetyReview());
        H_9_8.setText(list.get(0).getMPCPReview());
        H_10_1.setText(list.get(0).getCommunicationMeeting());
        H_10_2.setText(list.get(0).getWorstSupplierMeeting());
        H_10_3.setText(list.get(0).getSupplierVisits());

        C_1_1.setText(list.get(0).getDailyMMeetingRmk());
        C_2_1.setText(list.get(0).getDailyOneLineRmk());
        C_2_2.setText(list.get(0).getBoardReviewRmk());
        C_2_3.setText(list.get(0).getProductionRulesRmk());
        C_2_4.setText(list.get(0).getDailyShortageReviewRmk());
        C_2_5.setText(list.get(0).getSReviewRmk());
        C_2_6.setText(list.get(0).getMaterialFlowRmk());
        C_2_7.setText(list.get(0).getBreakdownReviewRmk());
        C_2_8.setText(list.get(0).getLossessReviewRmk());
        C_2_9.setText(list.get(0).getDepartmentDWMRmk());
        C_3_1.setText(list.get(0).getQualityReviewRmk());
        C_3_2.setText(list.get(0).getConcernCornerRmk());
        C_3_3.setText(list.get(0).getRedBinAreaRmk());
        C_3_4.setText(list.get(0).getMControlRmk());
        C_3_5.setText(list.get(0).getQIPReviewRmk());
        C_3_6.setText(list.get(0).getInternalRejectionRmk());
        C_3_7.setText(list.get(0).getToolDuplicationReviewRmk());
        C_4_1.setText(list.get(0).getAllProductionDepartmentRmk());
        C_5_1.setText(list.get(0).getCustomerVisitsRmk());
        C_6_1.setText(list.get(0).getMaterialsavingRmk());
        C_6_2.setText(list.get(0).getInventoryReviewRmk());
        C_6_3.setText(list.get(0).getIn1ReviewRmk());
        C_6_4.setText(list.get(0).getEnergyReviewRmk());
        C_7_1.setText(list.get(0).getUnitAddressRmk());
        C_7_2.setText(list.get(0).getTrainingRmk());
        C_7_3.setText(list.get(0).getBESTSupportRmk());
        C_8_1.setText(list.get(0).getDailyMMeetingRmk());
        C_8_1.setText(list.get(0).getMeetingasperInterunitRmk());
        C_9_1.setText(list.get(0).getMMCRmk());
        C_9_2.setText(list.get(0).getSystemMeetingRmk());
        C_9_3.setText(list.get(0).getIndentRmk());
        C_9_4.setText(list.get(0).getDevelopmentRmk());
        C_9_5.setText(list.get(0).getSAPAdherenceRmk());
        C_9_6.setText(list.get(0).getManufacturingRoadMapRmk());
        C_9_7.setText(list.get(0).getSafetyReviewRmk());
        C_9_8.setText(list.get(0).getMPCPReviewRmk());
        C_10_1.setText(list.get(0).getCommunicationMeetingRmk());
        C_10_2.setText(list.get(0).getWorstSupplierMeetingRmk());
        C_10_3.setText(list.get(0).getSupplierVisitsRmk());


        C_11_1.setText(list.get(0).getOtherActRmk());
        C_11_2.setText(list.get(0).getOtherAct2Rmk());
        C_11_3.setText(list.get(0).getOtherAct3Rmk());
        C_11_4.setText(list.get(0).getOtherAct4Rmk());
        C_11_5.setText(list.get(0).getOtherAct5Rmk());
        C_11_6.setText(list.get(0).getOtherAct6Rmk());
        C_11_7.setText(list.get(0).getOtherActGRmk());
        C_11_8.setText(list.get(0).getOtherActHRmk());


        H_11_1.setText(list.get(0).getOtherAct());
        H_11_2.setText(list.get(0).getOtherAct2());
        H_11_3.setText(list.get(0).getOtherAct3());
        H_11_4.setText(list.get(0).getOtherAct4());
        H_11_5.setText(list.get(0).getOtherAct5());
        H_11_6.setText(list.get(0).getOtherAct6());
        H_11_7.setText(list.get(0).getOtherActG());
        H_11_8.setText(list.get(0).getOtherActH());
        check_4_1.setText(list.get(0).getCheckoncheck());
        check_C_4_1.setText(list.get(0).getCheckoncheckrmk());

        try {
            tv_total.setText(String.valueOf(Gettotal()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setvalue() {
        H_1_1.setText("");
        H_2_1.setText("");
        H_2_2.setText("");
        H_2_3.setText("");
        H_2_4.setText("");
        H_2_5.setText("");
        H_2_6.setText("");
        H_2_7.setText("");
        H_2_8.setText("");
        H_2_9.setText("");
        H_3_1.setText("");
        H_3_2.setText("");
        H_3_3.setText("");
        H_3_4.setText("");
        H_3_5.setText("");
        H_3_6.setText("");
        H_3_7.setText("");
        H_4_1.setText("");
        H_5_1.setText("");
        H_6_1.setText("");
        H_6_2.setText("");
        H_6_3.setText("");
        H_6_4.setText("");
        H_7_1.setText("");
        H_7_2.setText("");
        H_7_3.setText("");
        H_8_1.setText("");


        H_8_2.setText("");
        H_9_1.setText("");
        H_9_2.setText("");
        H_9_3.setText("");
        H_9_4.setText("");
        H_9_5.setText("");
        H_9_6.setText("");
        H_9_7.setText("");
        H_9_8.setText("");
        H_10_1.setText("");
        H_10_2.setText("");
        H_10_3.setText("");

        C_1_1.setText("");
        C_2_1.setText("");
        C_2_2.setText("");
        C_2_3.setText("");
        C_2_4.setText("");
        C_2_5.setText("");
        C_2_6.setText("");
        C_2_7.setText("");
        C_2_8.setText("");
        C_2_9.setText("");
        C_3_1.setText("");
        C_3_2.setText("");
        C_3_3.setText("");
        C_3_4.setText("");
        C_3_5.setText("");
        C_3_6.setText("");
        C_3_7.setText("");
        C_4_1.setText("");
        C_5_1.setText("");
        C_6_1.setText("");
        C_6_2.setText("");
        C_6_3.setText("");
        C_6_4.setText("");
        C_7_1.setText("");
        C_7_2.setText("");
        C_7_3.setText("");
        C_8_1.setText("");
        C_8_1.setText("");
        C_9_1.setText("");
        C_9_2.setText("");
        C_9_3.setText("");
        C_9_4.setText("");
        C_9_5.setText("");
        C_9_6.setText("");
        C_9_7.setText("");
        C_9_8.setText("");
        C_10_1.setText("");
        C_10_2.setText("");
        C_10_3.setText("");

        H_11_1.setText("");
        H_11_2.setText("");
        H_11_3.setText("");
        H_11_4.setText("");
        H_11_5.setText("");
        H_11_6.setText("");
        H_11_7.setText("");
        H_11_8.setText("");

        C_11_1.setText("");
        C_11_2.setText("");
        C_11_3.setText("");
        C_11_4.setText("");
        C_11_5.setText("");
        C_11_6.setText("");
        C_11_7.setText("");
        C_11_8.setText("");
        check_4_1.setText("");
        check_C_4_1.setText("");

        tv_total.setText("");
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void changeListenerAdd() {

        H_1_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_2_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_2_9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_3_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_3_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_4_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_5_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_6_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_6_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_6_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_7_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_7_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_7_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_8_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_8_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_9_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_9_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_10_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_10_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_10_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        H_11_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        H_11_8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        check_4_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    try {
                        tv_total.setText(String.valueOf(Gettotal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
