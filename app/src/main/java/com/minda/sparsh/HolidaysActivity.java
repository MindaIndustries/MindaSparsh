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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.HolidayAdapter;
import com.minda.sparsh.model.HolidayListModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HolidaysActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    RecyclerView holiday_list;
    String empCode, year;
    SharedPreferences myPref;
    ArrayList<HolidayListModel> holidaylist =new ArrayList<>();
    HolidayAdapter holidayAdapter;
    ArrayAdapter<String> yearsAdapter;
    ArrayList<String> years = new ArrayList<>();

    AppCompatAutoCompleteTextView yearDropdown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_list);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        holiday_list = findViewById(R.id.holiday_list);
        yearDropdown = findViewById(R.id.year_dropdown);
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
        years.add("2023");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,31);
        cal.set(Calendar.MONTH,11);
        cal.set(Calendar.YEAR,2023);
        if(new Date(Calendar.getInstance().getTimeInMillis()).after(new Date(cal.getTimeInMillis()))){
            years.add("2024");
        }

        yearsAdapter = new ArrayAdapter<>(HolidaysActivity.this, android.R.layout.simple_spinner_item,years);
        yearDropdown.setAdapter(yearsAdapter);
      //  yearDropdown.setSelection(0);
        //yearDropdown.setText(Calendar.getInstance().get(Calendar.YEAR));

        if(Utility.isOnline(HolidaysActivity.this)) {
            getHolidayList(empCode, year);
        }
    }

    public void getHolidayList(String empcode, String year){
        AlmsServices almsServices = new AlmsServices();
        almsServices.GetHolidayList(carotResponse -> {
            if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                List<HolidayListModel> listModels = (List<HolidayListModel>) carotResponse.getData();
                if(listModels!=null && listModels.size()>0){
                    holidaylist.addAll(listModels);
                }

                holidayAdapter.notifyDataSetChanged();


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
