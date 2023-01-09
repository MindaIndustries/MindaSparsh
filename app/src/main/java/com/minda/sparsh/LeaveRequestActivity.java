package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveTypeModel;
import com.minda.sparsh.services.AlmsServices;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveRequestActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    AppCompatAutoCompleteTextView leave_type;
    TextInputEditText start_date, end_date, comment;
    ArrayList<LeaveBalanceModel> leaveBalanceList = new ArrayList<>();
    String empCode;
    SharedPreferences myPref;
    ArrayAdapter<String> leaveTypeAdapter;
    ArrayList<String> leavetypes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_leave_req);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        leave_type = findViewById(R.id.leave_type);
        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        comment = findViewById(R.id.comment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Request");
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        leaveTypeAdapter = new ArrayAdapter<>(LeaveRequestActivity.this, android.R.layout.simple_spinner_item, leavetypes);
        leave_type.setAdapter(leaveTypeAdapter);
        getLeaveBalance(empCode,"2022","LS");
        getLeaveTypes(empCode);
    }

    public void getLeaveBalance(String empcode, String year, String leaveType) {
       leaveBalanceList.clear();

        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveBalance(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<LeaveBalanceModel> list = (List<LeaveBalanceModel>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        leaveBalanceList.addAll(list);

                    }
                }
            }
        }, empcode, year, leaveType);

    }

    public void getLeaveTypes(String empcode){
        leavetypes.clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveTypes(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<LeaveTypeModel> list = (List<LeaveTypeModel>) carotResponse.getData();
                    if(list!=null && list.size()>0) {
                        for (LeaveTypeModel leaveTypeModel : list) {
                            leavetypes.add(leaveTypeModel.getLve_type_desc());
                            leaveTypeAdapter.notifyDataSetChanged();
                        }
                    }
                    }
                }
        },empcode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
