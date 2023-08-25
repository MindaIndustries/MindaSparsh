package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.Adapter.ALMSRecyclerViewAdapter;
import com.minda.sparsh.Adapter.DWMPlantAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.DWMPlantModel;
import com.minda.sparsh.model.DWMPostModel;
import com.minda.sparsh.services.DWMServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DWMPlant extends AppCompatActivity {
    String empcode, type, date;
    SharedPreferences myPref;
    RecyclerView rv;
    DWMPlantAdapter dwmPlantAdapter;
    List<DWMPlantModel> dataList = new ArrayList<>();
    TextInputEditText date_selection;
    DatePickerDialog datePicker;
    Calendar calendar;
    String year;
    Date millisecondsdatefrom;
    TextView user_name,headertextview;
    Button submit;
    List<DWMPostModel.SaveList> saveList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_head_layout);
        rv = findViewById(R.id.recycler_view);
        date_selection = findViewById(R.id.date_selection);
        user_name = findViewById(R.id.user_name);
        headertextview = findViewById(R.id.headertextview);
        submit = findViewById(R.id.submit);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode =myPref.getString("Id", "Id");
        user_name.setText(myPref.getString("username", "username"));
        Intent in = getIntent();
        type =  in.getStringExtra("type");
        if(type.equals("PH")){
            headertextview.setText("Daily Work Management----Plant Head");
        }
        else{
            headertextview.setText("Daily Work Management----Operation Head");

        }
        dwmPlantAdapter = new DWMPlantAdapter(DWMPlant.this, dataList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DWMPlant.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(dwmPlantAdapter);
        initDatePicker();
        date_selection.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });

        getMasterData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    public void initDatePicker() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        String monthNo;
        if (mMonth < 10) {
            monthNo = "0" + mMonth;
        } else {
            monthNo = "" + mMonth;
        }
        date= calendar.get(Calendar.YEAR)+"-"+monthNo+"-"+calendar.get(Calendar.DAY_OF_MONTH);

        String dayOfMonthStr;
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dayOfMonthStr = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            dayOfMonthStr = "" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        year = String.valueOf(calendar.get(Calendar.YEAR));

        //   start_date.setText(dayOfMonthStr+"-"+monthNo+"-"+calendar.get(Calendar.YEAR));
        datePicker = new DatePickerDialog(DWMPlant.this, (datePicker, i, i1, i2) -> {

            int mMonth1 = i1 + 1;
            String monthNo1;
            if (mMonth1 < 10) {
                monthNo1 = "0" + mMonth1;
            } else {
                monthNo1 = "" + mMonth1;
            }
            String dayOfMonthStr1;
            if (i2 < 10) {
                dayOfMonthStr1 = "0" + i2;
            } else {
                dayOfMonthStr1 = "" + i2;
            }
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.YEAR, i);
            millisecondsdatefrom = calendar.getTime();
            year = String.valueOf(calendar.get(Calendar.YEAR));
            date_selection.setText("" + dayOfMonthStr1 + "." + monthNo1 + "." + i);
            date= i+"-"+monthNo1+"-"+dayOfMonthStr1;
            if (calendar.after(Calendar.getInstance())) {
            } else {
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

          datePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);
          datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-(2*24*60*60*1000));

    }


    public void getMasterData(){
        DWMServices dwmServices = new DWMServices();
        dwmServices.getDwmMasterData(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                    List<DWMPlantModel> list = (List<DWMPlantModel>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        dataList.addAll(list);
                        dwmPlantAdapter.notifyDataSetChanged();
                    }
                }
            }
        },empcode, date, type);
    }


    public void submit(){
        DWMServices dwmServices = new DWMServices();
        dwmServices.saveRecord(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

            }
        },empcode,date,type,saveList);


    }
}
