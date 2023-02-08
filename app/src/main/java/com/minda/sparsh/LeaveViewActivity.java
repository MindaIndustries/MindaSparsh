package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.minda.sparsh.model.LeaveRequestModel;

public class LeaveViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title,req_no_value,name_value,emp_no_value,leave_type_value,available_bal_value,no_of_days_value,remarks_value,status_value,approved_by_value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_view);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Request");
        req_no_value = findViewById(R.id.req_no_value);
        name_value = findViewById(R.id.name_value);
        emp_no_value = findViewById(R.id.emp_no_value);
        leave_type_value = findViewById(R.id.leave_type_value);
        available_bal_value = findViewById(R.id.available_bal_value);
        no_of_days_value = findViewById(R.id.no_of_days_value);
        remarks_value = findViewById(R.id.remarks_value);
        status_value = findViewById(R.id.status_value);
        approved_by_value = findViewById(R.id.approved_by_value);
        Intent in  = getIntent();
        if(in.getParcelableExtra("leave_model")!=null){
            LeaveRequestModel leaveRequestModel = in.getParcelableExtra("leave_model");
            if(leaveRequestModel!=null){
                req_no_value.setText(leaveRequestModel.getLVE_REQNO());
            }
        }
        else{

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

}
