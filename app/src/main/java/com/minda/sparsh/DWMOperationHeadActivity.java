package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.model.DWMOprationDetailResponse;
import com.minda.sparsh.model.DwmResponse;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DWMOperationHeadActivity extends AppCompatActivity {

    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;

    TextView tv_submit, tv_select_date, user_name, tv_total;

    Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    String Addtype = "";


    EditText H_1_1, C_1_1,

    H_2_1, H_2_2, H_2_3, H_2_4,
            C_2_1, C_2_2, C_2_3, C_2_4,
            check_C_3_1, check_3_1,

    H_3_1,
            C_3_1,

    H_4_1, H_4_2, H_4_3, H_4_4,
            C_4_1, C_4_2, C_4_3, C_4_4,

    H_5_1, H_5_2, H_5_3, H_5_4, H_5_5,
            C_5_1, C_5_2, C_5_3, C_5_4, C_5_5,

    H_6_1, H_6_2, H_6_3, H_6_4, H_6_5,
            C_6_1, C_6_2, C_6_3, C_6_4, C_6_5,

    H_7_1, H_7_2, H_7_3, H_7_4, H_7_5, H_7_6, H_7_7,
            C_7_1, C_7_2, C_7_3, C_7_4, C_7_5, C_7_6, C_7_7,

    H_8_1, H_8_2, H_8_3,
            C_8_1, C_8_2, C_8_3,

    H_9_1, H_9_2,
            C_9_1, C_9_2,

    H_10_1, H_10_2, H_10_3,
            C_10_1, C_10_2, C_10_3,
            H_11_1, H_11_2, H_11_3, H_11_4, H_11_5, H_11_6, H_11_7, H_11_8,
            C_11_1, C_11_2, C_11_3, C_11_4, C_11_5, C_11_6, C_11_7, C_11_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwmoperation_head);
        tv_submit =  findViewById(R.id.tv_submit);
        tv_total =  findViewById(R.id.tv_total);
        tv_select_date =  findViewById(R.id.tv_select_date);

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


        H_1_1 =  findViewById(R.id.H_1_1);
        C_1_1 =  findViewById(R.id.C_1_1);

        H_2_1 =  findViewById(R.id.H_2_1);
        H_2_2 =  findViewById(R.id.H_2_2);
        H_2_3 =  findViewById(R.id.H_2_3);
        H_2_4 =  findViewById(R.id.H_2_4);


        C_2_1 =  findViewById(R.id.C_2_1);
        C_2_2 =  findViewById(R.id.C_2_2);
        C_2_3 =  findViewById(R.id.C_2_3);
        C_2_4 =  findViewById(R.id.C_2_4);

        check_3_1 =  findViewById(R.id.check_3_1);
        check_C_3_1 =  findViewById(R.id.check_C_3_1);

        H_3_1 =  findViewById(R.id.H_3_1);
        C_3_1 =  findViewById(R.id.C_3_1);


        H_4_1 =  findViewById(R.id.H_4_1);
        H_4_2 =  findViewById(R.id.H_4_2);
        H_4_3 =  findViewById(R.id.H_4_3);
        H_4_4 =  findViewById(R.id.H_4_4);

        C_4_1 =  findViewById(R.id.C_4_1);
        C_4_2 =  findViewById(R.id.C_4_2);
        C_4_3 =  findViewById(R.id.C_4_3);
        C_4_4 =  findViewById(R.id.C_4_4);

        H_5_1 =  findViewById(R.id.H_5_1);
        H_5_2 =  findViewById(R.id.H_5_2);
        H_5_3 =  findViewById(R.id.H_5_3);
        H_5_4 =  findViewById(R.id.H_5_4);
        H_5_5 =  findViewById(R.id.H_5_5);

        C_5_1 =  findViewById(R.id.C_5_1);
        C_5_2 =  findViewById(R.id.C_5_2);
        C_5_3 =  findViewById(R.id.C_5_3);
        C_5_4 =  findViewById(R.id.C_5_4);
        C_5_5 =  findViewById(R.id.C_5_5);

        H_6_1 =  findViewById(R.id.H_6_1);
        H_6_2 =  findViewById(R.id.H_6_2);
        H_6_3 =  findViewById(R.id.H_6_3);
        H_6_4 =  findViewById(R.id.H_6_4);
        H_6_5 =  findViewById(R.id.H_6_5);

        C_6_1 =  findViewById(R.id.C_6_1);
        C_6_2 =  findViewById(R.id.C_6_2);
        C_6_3 =  findViewById(R.id.C_6_3);
        C_6_4 =  findViewById(R.id.C_6_4);
        C_6_5 =  findViewById(R.id.C_6_5);

        H_7_1 =  findViewById(R.id.H_7_1);
        H_7_2 =  findViewById(R.id.H_7_2);
        H_7_3 =  findViewById(R.id.H_7_3);
        H_7_4 =  findViewById(R.id.H_7_4);
        H_7_5 =  findViewById(R.id.H_7_5);
        H_7_6 =  findViewById(R.id.H_7_6);
        H_7_7 =  findViewById(R.id.H_7_7);


        C_7_1 =  findViewById(R.id.C_7_1);
        C_7_2 =  findViewById(R.id.C_7_2);
        C_7_3 =  findViewById(R.id.C_7_3);
        C_7_4 =  findViewById(R.id.C_7_4);
        C_7_5 =  findViewById(R.id.C_7_5);
        C_7_6 =  findViewById(R.id.C_7_6);
        C_7_7 =  findViewById(R.id.C_7_7);


        H_8_1 =  findViewById(R.id.H_8_1);
        H_8_2 =  findViewById(R.id.H_8_2);
        H_8_3 =  findViewById(R.id.H_8_3);

        C_8_1 =  findViewById(R.id.C_8_1);
        C_8_2 =  findViewById(R.id.C_8_2);
        C_8_3 =  findViewById(R.id.C_8_3);


        H_9_1 =  findViewById(R.id.H_9_1);
        H_9_2 =  findViewById(R.id.H_9_2);

        C_9_1 =  findViewById(R.id.C_9_1);
        C_9_2 =  findViewById(R.id.C_9_2);


        H_10_1 =  findViewById(R.id.H_10_1);
        H_10_2 =  findViewById(R.id.H_10_2);
        H_10_3 =  findViewById(R.id.H_10_3);

        C_10_1 =  findViewById(R.id.C_10_1);
        C_10_2 =  findViewById(R.id.C_10_2);
        C_10_3 =  findViewById(R.id.C_10_3);


        H_11_1 =  findViewById(R.id.H_11_1);
        H_11_2 =  findViewById(R.id.H_11_2);
        H_11_3 =  findViewById(R.id.H_11_3);
        H_11_4 =  findViewById(R.id.H_11_4);
        H_11_5 =  findViewById(R.id.H_11_5);
        H_11_6 =  findViewById(R.id.H_11_6);
        H_11_7 =  findViewById(R.id.H_11_7);
        H_11_8 =  findViewById(R.id.H_11_8);
        C_11_1 =  findViewById(R.id.C_11_1);
        C_11_2 =  findViewById(R.id.C_11_2);
        C_11_3 =  findViewById(R.id.C_11_3);
        C_11_4 =  findViewById(R.id.C_11_4);
        C_11_5 =  findViewById(R.id.C_11_5);
        C_11_6 =  findViewById(R.id.C_11_6);
        C_11_7 =  findViewById(R.id.C_11_7);
        C_11_8 =  findViewById(R.id.C_11_8);


        user_name =  findViewById(R.id.user_name);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        user_name.setText(myPref.getString("username", ""));

//        hitGetManufacturingDetails(parseDateToddMMyyyy(tv_select_date.getText().toString()).replace("-", "/"));
        hitGetManufacturingDetails(tv_select_date.getText().toString().replace("-", "/"));


        tv_submit.setOnClickListener(view -> hitNewManufacturingHead());


        tv_select_date.setOnClickListener(v -> showDialog(0));

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
        String date = parseDateToddMMyyyy(tv_select_date.getText().toString());
        if (Utility.isOnline(DWMOperationHeadActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<DwmResponse>> response = promotingMyinterface.GetNewOperationHead("1", myPref.getString("Id", "Id"),
                    H_1_1.getText().toString(),
                    H_2_1.getText().toString(), H_2_2.getText().toString(), H_2_3.getText().toString(), H_2_4.getText().toString(),
                    H_3_1.getText().toString(),
                    H_4_1.getText().toString(), H_4_2.getText().toString(), H_4_3.getText().toString(), H_4_4.getText().toString(),
                    H_5_1.getText().toString(), H_5_2.getText().toString(), H_5_3.getText().toString(), H_5_4.getText().toString(), H_5_5.getText().toString(),
                    H_6_1.getText().toString(), H_6_2.getText().toString(), H_6_3.getText().toString(), H_6_4.getText().toString(), H_6_5.getText().toString(),
                    H_7_1.getText().toString(), H_7_2.getText().toString(), H_7_3.getText().toString(), H_7_4.getText().toString(), H_7_5.getText().toString(), H_7_6.getText().toString(), H_7_7.getText().toString(),
                    H_8_1.getText().toString(), H_8_2.getText().toString(), H_8_3.getText().toString(),
                    H_9_1.getText().toString(), H_9_2.getText().toString(),
                    H_10_1.getText().toString(), H_10_2.getText().toString(), H_10_3.getText().toString(),
                    C_1_1.getText().toString(),
                    C_2_1.getText().toString(), C_2_2.getText().toString(), C_2_3.getText().toString(), C_2_4.getText().toString(),
                    C_3_1.getText().toString(),
                    C_4_1.getText().toString(), C_4_2.getText().toString(), C_4_3.getText().toString(), C_4_4.getText().toString(),
                    C_5_1.getText().toString(), C_5_2.getText().toString(), C_5_3.getText().toString(), C_5_4.getText().toString(), C_5_5.getText().toString(),
                    C_6_1.getText().toString(), C_6_2.getText().toString(), C_6_3.getText().toString(), C_6_4.getText().toString(), C_6_5.getText().toString(),
                    C_7_1.getText().toString(), C_7_2.getText().toString(), C_7_3.getText().toString(), C_7_4.getText().toString(), C_7_5.getText().toString(), C_7_6.getText().toString(), C_7_7.getText().toString(),
                    C_8_1.getText().toString(), C_8_2.getText().toString(), C_8_3.getText().toString(),
                    C_9_1.getText().toString(), C_9_2.getText().toString(),
                    C_10_1.getText().toString(), C_10_2.getText().toString(), C_10_3.getText().toString(),
                    "", "", date, RetrofitClient2.CKEY, Addtype,
                    H_11_1.getText().toString(), H_11_2.getText().toString(), H_11_3.getText().toString(), H_11_4.getText().toString(), H_11_5.getText().toString(), H_11_6.getText().toString(), H_11_7.getText().toString(), H_11_8.getText().toString(),
                    C_11_1.getText().toString(), C_11_2.getText().toString(), C_11_3.getText().toString(), C_11_4.getText().toString(), C_11_5.getText().toString(), C_11_6.getText().toString(), C_11_7.getText().toString(), C_11_8.getText().toString(),
                    check_3_1.getText().toString(), check_C_3_1.getText().toString());
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
                        try {
                            Toast.makeText(getApplicationContext(), Responsedata.get(0).getColumn1(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<DwmResponse>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(DWMOperationHeadActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
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

//        return new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay);
    }

    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String day, month;
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
////                    + day);

            tv_select_date.setText(day + "-" + month + "-" + selectedYear);

        }
    };

    public int Gettotal() {

        String s1 = H_1_1.getText().toString();
        String s2 = H_2_1.getText().toString();
        String s3 = H_2_2.getText().toString();
        String s4 = H_2_3.getText().toString();
        String s5 = H_2_4.getText().toString();

        String s11 = H_3_1.getText().toString();

        String s18_1 = H_4_1.getText().toString();
        String s18_2 = H_4_2.getText().toString();
        String s18_3 = H_4_3.getText().toString();
        String s18_4 = H_4_4.getText().toString();
        String s19_1 = H_5_1.getText().toString();
        String s19_2 = H_5_2.getText().toString();
        String s19_3 = H_5_3.getText().toString();
        String s19_4 = H_5_4.getText().toString();
        String s19_5 = H_5_5.getText().toString();
        String s20 = H_6_1.getText().toString();
        String s21 = H_6_2.getText().toString();
        String s22 = H_6_3.getText().toString();
        String s23 = H_6_4.getText().toString();
        String s23_2 = H_6_5.getText().toString();
        String s24 = H_7_1.getText().toString();
        String s25 = H_7_2.getText().toString();
        String s26 = H_7_3.getText().toString();
        String s26_4 = H_7_4.getText().toString();
        String s26_5 = H_7_5.getText().toString();
        String s26_6 = H_7_6.getText().toString();
        String s26_7 = H_7_7.getText().toString();
        String s27 = H_8_1.getText().toString();
        String s28 = H_8_2.getText().toString();
        String s28_2 = H_8_3.getText().toString();
        String s29 = H_9_1.getText().toString();
        String s30 = H_9_2.getText().toString();

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
        String s48 = check_3_1.getText().toString();


        s1 = !s1.equals("") ? s1 : "0";
        s2 = !s2.equals("") ? s2 : "0";
        s3 = !s3.equals("") ? s3 : "0";
        s4 = !s4.equals("") ? s4 : "0";
        s5 = !s5.equals("") ? s5 : "0";

        s11 = !s11.equals("") ? s11 : "0";

        s18_1 = !s18_1.equals("") ? s18_1 : "0";
        s18_2 = !s18_2.equals("") ? s18_2 : "0";
        s18_3 = !s18_3.equals("") ? s18_3 : "0";
        s18_4 = !s18_4.equals("") ? s18_4 : "0";
        s19_1 = !s19_1.equals("") ? s19_1 : "0";
        s19_2 = !s19_2.equals("") ? s19_2 : "0";
        s19_3 = !s19_3.equals("") ? s19_3 : "0";
        s19_4 = !s19_4.equals("") ? s19_4 : "0";
        s19_5 = !s19_5.equals("") ? s19_5 : "0";
        s20 = !s20.equals("") ? s20 : "0";
        s21 = !s21.equals("") ? s21 : "0";
        s22 = !s22.equals("") ? s22 : "0";
        s23 = !s23.equals("") ? s23 : "0";
        s23_2 = !s23_2.equals("") ? s23_2 : "0";
        s24 = !s24.equals("") ? s24 : "0";
        s25 = !s25.equals("") ? s25 : "0";
        s26 = !s26.equals("") ? s26 : "0";
        s26_4 = !s26_4.equals("") ? s26_4 : "0";
        s26_5 = !s26_5.equals("") ? s26_5 : "0";
        s26_6 = !s26_6.equals("") ? s26_6 : "0";
        s26_7 = !s26_7.equals("") ? s26_7 : "0";
        s27 = !s27.equals("") ? s27 : "0";
        s28 = !s28.equals("") ? s28 : "0";
        s28_2 = !s28_2.equals("") ? s28_2 : "0";
        s29 = !s29.equals("") ? s29 : "0";
        s30 = !s30.equals("") ? s30 : "0";

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

                Integer.parseInt(s11) +

                Integer.parseInt(s18_1) +
                Integer.parseInt(s18_2) +
                Integer.parseInt(s18_3) +
                Integer.parseInt(s18_4) +
                Integer.parseInt(s19_1) +
                Integer.parseInt(s19_2) +
                Integer.parseInt(s19_3) +
                Integer.parseInt(s19_4) +
                Integer.parseInt(s19_5) +
                Integer.parseInt(s20) +
                Integer.parseInt(s21) +
                Integer.parseInt(s22) +
                Integer.parseInt(s23) +
                Integer.parseInt(s23_2) +
                Integer.parseInt(s24) +
                Integer.parseInt(s25) +
                Integer.parseInt(s26) +
                Integer.parseInt(s26_4) +
                Integer.parseInt(s26_5) +
                Integer.parseInt(s26_6) +
                Integer.parseInt(s26_7) +
                Integer.parseInt(s27) +
                Integer.parseInt(s28) +
                Integer.parseInt(s28_2) +
                Integer.parseInt(s29) +
                Integer.parseInt(s30) +

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
        if (Utility.isOnline(DWMOperationHeadActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<DWMOprationDetailResponse>> response = promotingMyinterface.GetGetOperationDetails(myPref.getString("Id", "Id"), date, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<DWMOprationDetailResponse>>() {
                @Override
                public void onResponse(Call<List<DWMOprationDetailResponse>> call, Response<List<DWMOprationDetailResponse>> response) {
                    showProgress(false);
                    List<DWMOprationDetailResponse> Responsedata = response.body();
//


                    if (Responsedata != null && Responsedata.size() != 0) {
                        Addtype = "Update";

                        setvalue(Responsedata);


                    } else {
                        Addtype = "Add";
                        setvalue();


                    }

                }

                @Override
                public void onFailure(Call<List<DWMOprationDetailResponse>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(DWMOperationHeadActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void setvalue(List<DWMOprationDetailResponse> list) {
        H_1_1.setText(list.get(0).getDWMGemba());

        H_2_1.setText(list.get(0).getQualityConcerns());
        H_2_2.setText(list.get(0).getQualityWarrnaty());
        H_2_3.setText(list.get(0).getQualitySatisfection());
        H_2_4.setText(list.get(0).getQualityQIP());

        H_3_1.setText(list.get(0).getCustomerVisits());

        H_4_1.setText(list.get(0).getCostingInventory());
        H_4_2.setText(list.get(0).getCostingCopq());
        H_4_3.setText(list.get(0).getCostingDWM());
        H_4_4.setText(list.get(0).getCostingVA());


        H_5_1.setText(list.get(0).getPeopleUnit());
        H_5_2.setText(list.get(0).getPeopleReview());
        H_5_3.setText(list.get(0).getPeopleTraning());
        H_5_4.setText(list.get(0).getPeopleBEST());
        H_5_5.setText(list.get(0).getPeopleCPD());


        H_6_1.setText(list.get(0).getManagementCorp());
        H_6_2.setText(list.get(0).getManagementProtivity());
        H_6_3.setText(list.get(0).getManagementStaff());
        H_6_4.setText(list.get(0).getManagementQCD());
        H_6_5.setText(list.get(0).getManagementInterUnit());

        H_7_1.setText(list.get(0).getRegularMMC());
        H_7_2.setText(list.get(0).getRegularSystem());
        H_7_3.setText(list.get(0).getRegularIndent());
        H_7_4.setText(list.get(0).getRegularSAP());
        H_7_5.setText(list.get(0).getRegularManufect());
        H_7_6.setText(list.get(0).getRegularMPCP());
        H_7_7.setText(list.get(0).getRegularSafety());


        H_8_1.setText(list.get(0).getSupplierCommu());
        H_8_2.setText(list.get(0).getSupplierWost());
        H_8_3.setText(list.get(0).getSupplierAncillaries());


        H_9_1.setText(list.get(0).getStrategicContri());
        H_9_2.setText(list.get(0).getStrategicReview());


        H_10_1.setText(list.get(0).getDevelopmentReview());
        H_10_2.setText(list.get(0).getDevelopmentMeeting());
        H_10_3.setText(list.get(0).getDevelopmentCustomer());


        C_1_1.setText(list.get(0).getDWMGembaRemark());

        C_2_1.setText(list.get(0).getQualityConcernsRemark());
        C_2_2.setText(list.get(0).getQualityWarrnatyRemark());
        C_2_3.setText(list.get(0).getQualitySatisfectionRemark());
        C_2_4.setText(list.get(0).getQualityQIPRemark());

        C_3_1.setText(list.get(0).getCustomerVisitsRemark());

        C_4_1.setText(list.get(0).getCostingInventoryRemark());
        C_4_2.setText(list.get(0).getCostingCopqRemark());
        C_4_3.setText(list.get(0).getCostingDWMRemark());
        C_4_4.setText(list.get(0).getCostingVARemark());


        C_5_1.setText(list.get(0).getPeopleUnitRemark());
        C_5_2.setText(list.get(0).getPeopleReviewRemark());
        C_5_3.setText(list.get(0).getPeopleTraningRemark());
        C_5_4.setText(list.get(0).getPeopleBESTRemark());
        C_5_5.setText(list.get(0).getPeopleCPDRemark());


        C_6_1.setText(list.get(0).getManagementCorpRemark());
        C_6_2.setText(list.get(0).getManagementProtivityRemark());
        C_6_3.setText(list.get(0).getManagementStaffRemark());
        C_6_4.setText(list.get(0).getManagementQCDRemark());
        C_6_5.setText(list.get(0).getManagementInterUnitRemark());

        C_7_1.setText(list.get(0).getRegularMMCRemark());
        C_7_2.setText(list.get(0).getRegularSystemRemark());
        C_7_3.setText(list.get(0).getRegularIndentRemark());
        C_7_4.setText(list.get(0).getRegularSAPRemark());
        C_7_5.setText(list.get(0).getRegularManufectRemark());
        C_7_6.setText(list.get(0).getRegularMPCPRemark());
        C_7_7.setText(list.get(0).getRegularSafetyRemark());


        C_8_1.setText(list.get(0).getSupplierCommuRemark());
        C_8_2.setText(list.get(0).getSupplierWostRemark());
        C_8_3.setText(list.get(0).getSupplierAncillariesRemark());


        C_9_1.setText(list.get(0).getStrategicContriRemark());
        C_9_2.setText(list.get(0).getStrategicReviewRemark());


        C_10_1.setText(list.get(0).getDevelopmentReview());
        C_10_2.setText(list.get(0).getDevelopmentMeetingRemark());
        C_10_3.setText(list.get(0).getDevelopmentCustomerRemark());


        C_11_1.setText(list.get(0).getOtherActRemark());
        C_11_2.setText(list.get(0).getOtherAct2Remark());
        C_11_3.setText(list.get(0).getOtherAct3Remark());
        C_11_4.setText(list.get(0).getOtherAct4Remark());
        C_11_5.setText(list.get(0).getOtherAct5Remark());
        C_11_6.setText(list.get(0).getOtherAct6Remark());
        C_11_7.setText(list.get(0).getOtherActGRemark());
        C_11_8.setText(list.get(0).getOtherActHRemark());


        H_11_1.setText(list.get(0).getOtherAct());
        H_11_2.setText(list.get(0).getOtherAct2());
        H_11_3.setText(list.get(0).getOtherAct3());
        H_11_4.setText(list.get(0).getOtherAct4());
        H_11_5.setText(list.get(0).getOtherAct5());
        H_11_6.setText(list.get(0).getOtherAct6());
        H_11_7.setText(list.get(0).getOtherActG());
        H_11_8.setText(list.get(0).getOtherActH());

        check_3_1.setText(list.get(0).getCheckoncheck());
        check_C_3_1.setText(list.get(0).getCheckoncheckrmk());

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

        H_3_1.setText("");

        H_4_1.setText("");
        H_4_2.setText("");
        H_4_3.setText("");
        H_4_4.setText("");


        H_5_1.setText("");
        H_5_2.setText("");
        H_5_3.setText("");
        H_5_4.setText("");
        H_5_5.setText("");


        H_6_1.setText("");
        H_6_2.setText("");
        H_6_3.setText("");
        H_6_4.setText("");
        H_6_5.setText("");

        H_7_1.setText("");
        H_7_2.setText("");
        H_7_3.setText("");
        H_7_4.setText("");
        H_7_5.setText("");
        H_7_6.setText("");
        H_7_7.setText("");


        H_8_1.setText("");
        H_8_2.setText("");
        H_8_3.setText("");


        H_9_1.setText("");
        H_9_2.setText("");


        H_10_1.setText("");
        H_10_2.setText("");
        H_10_3.setText("");


        C_1_1.setText("");

        C_2_1.setText("");
        C_2_2.setText("");
        C_2_3.setText("");
        C_2_4.setText("");

        C_3_1.setText("");

        C_4_1.setText("");
        C_4_2.setText("");
        C_4_3.setText("");
        C_4_4.setText("");


        C_5_1.setText("");
        C_5_2.setText("");
        C_5_3.setText("");
        C_5_4.setText("");
        C_5_5.setText("");


        C_6_1.setText("");
        C_6_2.setText("");
        C_6_3.setText("");
        C_6_4.setText("");
        C_6_5.setText("");

        C_7_1.setText("");
        C_7_2.setText("");
        C_7_3.setText("");
        C_7_4.setText("");
        C_7_5.setText("");
        C_7_6.setText("");
        C_7_7.setText("");


        C_8_1.setText("");
        C_8_2.setText("");
        C_8_3.setText("");


        C_9_1.setText("");
        C_9_2.setText("");


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
        check_C_3_1.setTag("");
        check_3_1.setText("");

        tv_total.setText("");
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
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

        H_4_2.addTextChangedListener(new TextWatcher() {
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

        H_4_3.addTextChangedListener(new TextWatcher() {
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
        H_4_4.addTextChangedListener(new TextWatcher() {
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
        H_5_2.addTextChangedListener(new TextWatcher() {
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
        H_5_3.addTextChangedListener(new TextWatcher() {
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
        H_5_4.addTextChangedListener(new TextWatcher() {
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
        H_5_5.addTextChangedListener(new TextWatcher() {
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

        H_6_3.addTextChangedListener(new TextWatcher() {
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

        H_6_5.addTextChangedListener(new TextWatcher() {
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
        H_7_4.addTextChangedListener(new TextWatcher() {
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
        H_7_5.addTextChangedListener(new TextWatcher() {
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
        H_7_6.addTextChangedListener(new TextWatcher() {
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
        H_7_7.addTextChangedListener(new TextWatcher() {
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

        H_8_3.addTextChangedListener(new TextWatcher() {
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

        check_3_1.addTextChangedListener(new TextWatcher() {
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
