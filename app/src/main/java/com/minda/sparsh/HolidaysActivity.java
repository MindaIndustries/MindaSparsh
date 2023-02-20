package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.HolidayAdapter;
import com.minda.sparsh.Adapter.MyLeaveRequestAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.HolidayListModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HolidaysActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    RecyclerView holiday_list;
    String empCode, year;
    SharedPreferences myPref;
    ArrayList<HolidayListModel> holidaylist =new ArrayList<HolidayListModel>();
    HolidayAdapter holidayAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_list);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        holiday_list = findViewById(R.id.holiday_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Holiday List");
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        holidayAdapter = new HolidayAdapter(HolidaysActivity.this, holidaylist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(HolidaysActivity.this, LinearLayoutManager.VERTICAL, false);
        holiday_list.setLayoutManager(mLayoutManager);
        holiday_list.setAdapter(holidayAdapter);
        if(Utility.isOnline(HolidaysActivity.this)) {
            getHolidayList(empCode, year);
        }
    }

    public void getHolidayList(String empcode, String year){
        AlmsServices almsServices = new AlmsServices();
        almsServices.GetHolidayList(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<HolidayListModel> listModels = (List<HolidayListModel>) carotResponse.getData();
                    if(listModels!=null && listModels.size()>0){
                        holidaylist.addAll(listModels);
                    }

                    holidayAdapter.notifyDataSetChanged();


                }
            }
        },empcode, year);
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
