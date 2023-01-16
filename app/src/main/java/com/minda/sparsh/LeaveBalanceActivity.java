package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveBalanceActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title,leave1,leave2,leave3,leave4,leave1_value,leave2_value,leave3_value,leave4_value,leave1_value_out,leave2_value_out,leave3_value_out,leave4_value_out;
    SharedPreferences myPref;
    String empCode, year;
    Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_balance);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Balance");
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        leave1 = findViewById(R.id.leave1);
        leave2 = findViewById(R.id.leave2);
        leave3 = findViewById(R.id.leave3);
        leave4 = findViewById(R.id.leave4);
        leave1_value = findViewById(R.id.leave1_value);
        leave2_value = findViewById(R.id.leave2_value);
        leave3_value = findViewById(R.id.leave3_value);
        leave4_value = findViewById(R.id.leave4_value);
        leave1_value_out = findViewById(R.id.leave1_value_out);
        leave2_value_out = findViewById(R.id.leave2_value_out);
        leave3_value_out = findViewById(R.id.leave3_value_out);
        leave4_value_out = findViewById(R.id.leave4_value_out);
        if(Utility.isOnline(LeaveBalanceActivity.this)) {
            getLeaveBalance(empCode, year, "LS");
        }



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getLeaveBalance(String empcode, String year, String leaveType) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveBalance(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<LeaveBalanceModel> list = (List<LeaveBalanceModel>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        for (LeaveBalanceModel leaveBalanceModel : list) {
                            switch (leaveBalanceModel.getLeaveType()){
                                case "Casual Leave":
                                    leave1.setText(leaveBalanceModel.getLeaveType());
                                    leave1_value.setText(""+leaveBalanceModel.getAvailableBalance()+"/");
                                    leave1_value_out.setText(""+leaveBalanceModel.getOpeningBalance());
                                    break;
                                case "EL (Encashable)":
                                    leave2.setText(leaveBalanceModel.getLeaveType());
                                    leave2_value.setText(""+leaveBalanceModel.getAvailableBalance()+"/");
                                    leave2_value_out.setText(""+leaveBalanceModel.getOpeningBalance());
                                    break;
                                case "EL (Non Encashable)":
                                    leave3.setText(leaveBalanceModel.getLeaveType());
                                    leave3_value.setText(""+leaveBalanceModel.getAvailableBalance()+"/");
                                    leave3_value_out.setText(""+leaveBalanceModel.getOpeningBalance());
                                    break;
                                case "Sick Leave":
                                    leave4.setText(leaveBalanceModel.getLeaveType());
                                    leave4_value.setText(""+leaveBalanceModel.getAvailableBalance()+"/");
                                    leave4_value_out.setText(""+leaveBalanceModel.getOpeningBalance());
                                    break;

                        }
                        }
                    }

                }
            }
        }, empcode, year, leaveType);

    }

}
