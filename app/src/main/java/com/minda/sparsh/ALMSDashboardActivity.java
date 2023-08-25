package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ALMSDashboardActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6;
    ImageView img4;
    int Reporty;
    SharedPreferences myPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alms_dashboard);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Attendance & Leave Management");
        rl1 = findViewById(R.id.rl1);
        rl2 = findViewById(R.id.rl2);
        rl3 = findViewById(R.id.rl3);
        rl4 = findViewById(R.id.rl4);
        rl5 = findViewById(R.id.rl5);
        rl6 = findViewById(R.id.rl6);
        img4 = findViewById(R.id.img4);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Reporty = myPref.getInt("Reporty", 0);
        if(Reporty > 0){
            rl6.setVisibility(View.VISIBLE);
        }
        else{
            rl6.setVisibility(View.GONE);
        }


        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ALMSDashboardActivity.this, ALMSCalendarActivity.class);
                startActivity(in);
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ALMSDashboardActivity.this, ALMSCalendarActivity.class);
                in.putExtra("list", true);
                startActivity(in);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ALMSDashboardActivity.this, HolidaysActivity.class);
                startActivity(in);

            }
        });
        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu();
            }
        });
        rl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ALMSDashboardActivity.this, LeaveBalanceActivity.class);
                startActivity(in);
            }
        });
        rl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(ALMSDashboardActivity.this, LeaveApprovals.class);
                startActivity(in);
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

    public void showPopUpMenu() {
        PopupMenu popup = new PopupMenu(ALMSDashboardActivity.this, img4);
        popup.getMenuInflater().inflate(R.menu.leaves_req_reg, popup.getMenu());
        MenuItem menuItem = popup.getMenu().findItem(R.id.comp_off_credit);
        if(myPref.getInt("Level",0)<7){
            menuItem.setVisible(true);
        }
        else{
            menuItem.setVisible(false);
        }

        popup.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.leave:
                    Intent in = new Intent(ALMSDashboardActivity.this, LeaveRequestActivity.class);
                    startActivity(in);
                    break;
                case R.id.regularize:
                    Intent in1 = new Intent(ALMSDashboardActivity.this, LeaveRegularizationActivity.class);
                    startActivity(in1);
                    break;
                case R.id.comp_off_credit:
                    Intent in2 = new Intent(ALMSDashboardActivity.this, LeaveCompOffCredit.class);
                    startActivity(in2);
                    break;
            }
            return true;
        });
        popup.show();
    }


}
