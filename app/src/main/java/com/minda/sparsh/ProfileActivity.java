package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.model.LoginResponse;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
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
    @BindView(R.id.deputedunit)
    TextView deputedunit;
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
        title.setText(getResources().getString(R.string.my_profile));
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        pass = myPref.getString("pass", "");

        empCode_str = myPref.getString("Id", "Id");
        if (empCode_str.length() > 0) {
            empCode.setText(empCode_str);
        }
        if (myPref.getString("username", "").length() > 0)
            userNametext.setText("" + myPref.getString("username", ""));
        if (myPref.getString("EmainId", "").length() > 0)
            emailValue.setText("" + myPref.getString("EmainId", ""));
        if (myPref.getString("DOJ", "").length() > 0) {
            String doj = myPref.getString("DOJ", "").replaceAll("/", "").replace("Date", "").replaceAll("\\(", "").replaceAll("\\)", "   ").replaceAll(" ", "");
            dojvalue.setText("" + getlogDate(Long.parseLong(doj)));
        }

        if (myPref.getString("UM_DEPT_NAME", "").length() > 0)
            deptName.setText("" + myPref.getString("UM_DEPT_NAME", ""));
        if (myPref.getString("UM_REPORTING_TO_NAME", "").length() > 0)
            reportOfcrName.setText(myPref.getString("UM_REPORTING_TO_NAME", ""));
        if (myPref.getString("DESIGNATION", "").length() > 0)
            designationName.setText("" + myPref.getString("DESIGNATION", ""));
        if (myPref.getString("UM_MASCOM_CODE", "").length() > 0)
            unitValue.setText("" + myPref.getString("UM_MASCOM_CODE", ""));
        if (myPref.getString("Depu_UnitName", "").length() > 0) {
            deputedunitValue.setVisibility(View.VISIBLE);
            deputedunit.setVisibility(View.VISIBLE);
            deputedunitValue.setText("" + myPref.getString("Depu_UnitName", ""));
        }
      /*  if (Utility.isOnline(ProfileActivity.this)) {
            hitGetLoginApi(empCode_str, pass, RetrofitClient2.CKEY);
        } else {
            Toast.makeText(ProfileActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    */
    }


    public void hitGetLoginApi(String userName, String password, String key) {
        if (Utility.isOnline(ProfileActivity.this)) {
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<LoginResponse>> response = promotingMyinterface.GetLogin(userName, password, key);
            response.enqueue(new Callback<List<LoginResponse>>() {
                @Override
                public void onResponse(@NotNull Call<List<LoginResponse>> call, @NotNull Response<List<LoginResponse>> response) {
                    List<LoginResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        try {
                            SharedPreferences.Editor mEditor = myPref.edit();
                            mEditor.putString("Id", loginResponse.get(0).getUMUSERID());
                            mEditor.putString("username", loginResponse.get(0).getUMUSERDESC());
                            mEditor.putBoolean("IsLogin", true);
                            mEditor.putBoolean("IsLoginNew", true);
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
                            mEditor.putString("Depu_UnitCode", (String) loginResponse.get(0).getDepuUnitCode());
                            mEditor.putString("pass", loginResponse.get(0).getUMUSERPWD());
                            mEditor.apply();
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
                public void onFailure(@NotNull Call<List<LoginResponse>> call, @NotNull Throwable t) {
                }
            });
        } else
            Toast.makeText(ProfileActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
