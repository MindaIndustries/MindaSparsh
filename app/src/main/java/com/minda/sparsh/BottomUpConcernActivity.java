package com.minda.sparsh;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minda.sparsh.Adapter.BottomUpConcernAdapter;
import com.minda.sparsh.Adapter.EHSObsAdapter;
import com.minda.sparsh.customview.NoDefaultSpinner;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomUpConcernActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.new_concern)
    RelativeLayout newConcern;
    @BindView(R.id.view_concern)
    RelativeLayout viewConcern;
    @BindView(R.id.concern_date_spinner)
    TextView concernDateText;
    @BindView(R.id.unit_spinner)
    NoDefaultSpinner unitSpinner;
    @BindView(R.id.responsible_spinner)
    NoDefaultSpinner responsibleSpinner;
    @BindView(R.id.msm_reference_value)
    EditText msmReferenceValue;
    @BindView(R.id.existing_system_value)
    EditText existingSystemValue;
    @BindView(R.id.proposed_system_value)
    EditText proposedSystemValue;
    @BindView(R.id.benefit_value)
    EditText benefitValue;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.bottomup_rv)
    RecyclerView bottomupRv;
    BottomUpConcernAdapter bottomUpConcernAdapter;
    ArrayList<String> concerns = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_up_concern);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Bottom Up Concern");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    viewConcern.setVisibility(View.GONE);
                    newConcern.setVisibility(View.VISIBLE);
                }
                else{
                    viewConcern.setVisibility(View.VISIBLE);
                    newConcern.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bottomUpConcernAdapter = new BottomUpConcernAdapter(BottomUpConcernActivity.this,concerns);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(BottomUpConcernActivity.this, LinearLayoutManager.VERTICAL, false);
        bottomupRv.setLayoutManager(mLayoutManager);
        bottomupRv.setAdapter(bottomUpConcernAdapter);
        bottomUpConcernAdapter.notifyDataSetChanged();
        concernDateText.setText(getlogDate(System.currentTimeMillis()));

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
        if(am_pm ==0){
            AM_PM="AM";
        }
        else{
            AM_PM="PM";
        }
        month = month + 1;
        String monthNo;
        if(month <10){
            monthNo = "0"+month;
        }
        else{
            monthNo = ""+month;
        }
        String dayOfMonthStr;
        if(day<10){
            dayOfMonthStr = "0"+day;
        }
        else{
            dayOfMonthStr =""+ day;
        }

        return dayOfMonthStr  + "-" + monthNo + "-" + year+" "+hr+":"+min+" "+AM_PM;
    }

    @OnClick(R.id.save)
    public void onClickSave(){

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
}
