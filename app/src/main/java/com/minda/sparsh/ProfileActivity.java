package com.minda.sparsh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.model.LoginResponse;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {
    @BindView(R.id.first_name)
    TextView userNametext;
    @BindView(R.id.email_value)
    TextView emailValue;
    @BindView(R.id.emp_code)
    TextView empCode;
    @BindView(R.id.dojvalue)
    TextView dojvalue;
    @BindView(R.id.deptName)
    TextView deptName;
    @BindView(R.id.reportOfcrName)
    TextView reportOfcrName;
    @BindView(R.id.designation_name)
    TextView designationName;
    @BindView(R.id.unit_value)
    TextView unitValue;
    @BindView(R.id.deputedunit_value)
    TextView deputedunitValue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    String empCode_str, pass;
    SharedPreferences myPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("My Profile");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        pass = myPref.getString("pass", "");

        empCode_str = myPref.getString("Id", "Id");
        empCode.setText(empCode_str);
        userNametext.setText("" + myPref.getString("username", ""));
        emailValue.setText("" + myPref.getString("EmainId", ""));
        String doj = myPref.getString("DOJ", "").replaceAll("/", "").replace("Date", "").replaceAll("\\(", "").replaceAll("\\)", "   ").replaceAll(" ", "");
        dojvalue.setText("" + getlogDate(Long.parseLong(doj)));
        deptName.setText("" + myPref.getString("UM_DEPT_NAME", ""));
        reportOfcrName.setText(myPref.getString("UM_REPORTING_TO_NAME", ""));
        designationName.setText("" + myPref.getString("DESIGNATION", ""));
        unitValue.setText("" + myPref.getString("UM_MASCOM_CODE", ""));
        deputedunitValue.setText("" + myPref.getString("Depu_UnitName", ""));
        if (Utility.isOnline(ProfileActivity.this)) {
            hitGetLoginApi(empCode_str, pass, RetrofitClient2.CKEY);
        } else {
            Toast.makeText(ProfileActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }


    public void hitGetLoginApi(String userName, String password, String key) {
        if (Utility.isOnline(ProfileActivity.this)) {
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<LoginResponse>> response = promotingMyinterface.GetLogin(userName, password, key);
            response.enqueue(new Callback<List<LoginResponse>>() {
                @Override
                public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                    List<LoginResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        try {
                            SharedPreferences.Editor mEditor = myPref.edit();
                            mEditor.putString("Id", loginResponse.get(0).getUMUSERID());
                            mEditor.putString("username", loginResponse.get(0).getUMUSERDESC());
                            mEditor.putBoolean("IsLogin", true);
                            mEditor.putString("EmainId", loginResponse.get(0).getUMEMAILID());
                            mEditor.putString("AuthFor", loginResponse.get(0).getAuthFor());
                            mEditor.putString("Um_div_code", loginResponse.get(0).getUMDIVCODE());
                            mEditor.putString("UM_DESIG_CODE", loginResponse.get(0).getUMDESIGCODE());
                            mEditor.putString("DESIGNATION", loginResponse.get(0).getUMEMPDESIG());
                            mEditor.putString("DOJ", loginResponse.get(0).getDOJ());
                            mEditor.putString("UM_DEPT_NAME", loginResponse.get(0).getUMDEPTNAME());
                            mEditor.putString("UM_REPORTING_TO_NAME", loginResponse.get(0).getUMREPORTINGTONAME());
                            mEditor.putString("UM_MASCOM_CODE", loginResponse.get(0).getUMMASCOMCODE());
                            mEditor.putString("Depu_UnitName", (String) loginResponse.get(0).getDepuUnitName());
                            mEditor.putString("pass", loginResponse.get(0).getUMUSERPWD());
                            mEditor.commit();
                            empCode_str = myPref.getString("Id", "Id");
                            empCode.setText(empCode_str);
                            userNametext.setText("" + myPref.getString("username", ""));
                            emailValue.setText("" + myPref.getString("EmainId", ""));
                            String doj = myPref.getString("DOJ", "").replaceAll("/", "").replace("Date", "").replaceAll("\\(", "").replaceAll("\\)", "   ");
                            dojvalue.setText("" + getlogDate(Long.parseLong(doj)));
                            deptName.setText("" + myPref.getString("UM_DEPT_NAME", ""));
                            reportOfcrName.setText(myPref.getString("UM_REPORTING_TO_NAME", ""));
                            designationName.setText("" + myPref.getString("DESIGNATION", ""));
                            unitValue.setText("" + myPref.getString("UM_MASCOM_CODE", ""));
                            deputedunitValue.setText("" + myPref.getString("Depu_UnitName", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Utility.showToast(getApplicationContext(), "Invalid Credentials.");
                    }

                }

                @Override
                public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                }
            });
        } else
            Toast.makeText(ProfileActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getlogDate(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hr = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int am_pm = cal.get(Calendar.AM_PM);
        String AM_PM;
        if (am_pm == 0) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        month = month + 1;
        String monthNo;
        if (month < 10) {
            monthNo = "0" + month;
        } else {
            monthNo = "" + month;
        }
        String dayOfMonthStr;
        if (day < 10) {
            dayOfMonthStr = "0" + day;
        } else {
            dayOfMonthStr = "" + day;
        }

        return dayOfMonthStr + "/" + monthNo + "/" + year;
    }

}
