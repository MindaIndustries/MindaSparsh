package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.Adapter.MeetingRoomAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CityModel;
import com.minda.sparsh.model.MeetingRoomListModel;
import com.minda.sparsh.model.MeetingRoomModel;
import com.minda.sparsh.services.MeetingRoomServices;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomActivity extends AppCompatActivity {
    @BindView(R.id.roomsList)
    RecyclerView roomsList;
    @BindView(R.id.customerSpinnerLayout)
    TextInputLayout customSpinnerLayout;
    @BindView(R.id.city_spinner)
    AppCompatAutoCompleteTextView citySpinner;
    @BindView(R.id.customerSpinnerLayout1)
    TextInputLayout customSpinnerLayout1;
    @BindView(R.id.unit_spinner)
    AppCompatAutoCompleteTextView unitSpinner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toggle)
    ToggleButton toggleButton;
    String EmpCode;
    SharedPreferences myPref;
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<CityModel.City> cityModels = new ArrayList<>();
    ArrayList<String> units = new ArrayList<>();
    ArrayList<MeetingRoomModel.MeetingRoomModelData> unitModels = new ArrayList<>();
    ArrayAdapter<String> cityAdapter, unitAdapter;
    String city,unitCode;
    MeetingRoomAdapter meetingRoomAdapter;
    ArrayList<MeetingRoomListModel.MeetingRoomListModelData> meetingRooms = new ArrayList<>();
    boolean roomType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_rooms);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Book Meeting Room");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        EmpCode = myPref.getString("Id", "Id");
        initCityAdapter();
        initUnitAdapter();
        getCity(EmpCode);
        meetingRoomAdapter = new MeetingRoomAdapter(MeetingRoomActivity.this,meetingRooms);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MeetingRoomActivity.this, LinearLayoutManager.VERTICAL, false);
        roomsList.setLayoutManager(mLayoutManager);
        roomsList.setAdapter(meetingRoomAdapter);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    roomType = false;
                    compoundButton.setButtonDrawable(getResources().getDrawable(R.drawable.toggle_off));
                    compoundButton.setButtonTintList(ContextCompat.getColorStateList(MeetingRoomActivity.this,R.color.black));

                }
                else{
                    roomType = true;
                    compoundButton.setButtonDrawable(getResources().getDrawable(R.drawable.toggle_on));
                    compoundButton.setButtonTintList(ContextCompat.getColorStateList(MeetingRoomActivity.this,R.color.red));
                }
                getMeetingRooms(EmpCode,roomType);
            }
        });


    }

    public void initCityAdapter() {
        cityAdapter = new ArrayAdapter<String>(MeetingRoomActivity.this, android.R.layout.simple_spinner_item, cities);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                city = cities.get(i);
                unitSpinner.setText("");
                unitAdapter = new ArrayAdapter<String>(MeetingRoomActivity.this, android.R.layout.simple_spinner_item, units);
                unitSpinner.setAdapter(unitAdapter);
                getUnitByCity(city);
            }
        });
    }

    public void initUnitAdapter() {
        unitAdapter = new ArrayAdapter<String>(MeetingRoomActivity.this, android.R.layout.simple_spinner_item, units);
        unitSpinner.setAdapter(unitAdapter);
        unitSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                unitCode = unitModels.get(i).getUnitCode();
                getMeetingRooms(EmpCode,roomType);
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


    public void getCity(String EmpCode) {
        cityModels.clear();
        cities.clear();
        cityAdapter.notifyDataSetChanged();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getCity(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    CityModel cityModel = (CityModel) carotResponse.getData();
                    List<CityModel.City> citylist = cityModel.getData();
                    if (citylist != null && citylist.size() > 0) {
                        cityModels.addAll(citylist);
                        for (CityModel.City model : cityModels) {
                            cities.add(model.getCity());
                        }
                        cityAdapter.notifyDataSetChanged();
                          }

                }
            }
        }, "");
    }

    public void getUnitByCity(String city) {
        unitModels.clear();
        units.clear();
        unitAdapter.notifyDataSetChanged();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getUnitByCity(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    MeetingRoomModel meetingRoomModel = (MeetingRoomModel) carotResponse.getData();
                    if (meetingRoomModel != null) {
                        List<MeetingRoomModel.MeetingRoomModelData> list = meetingRoomModel.getMeetingRoomModelData();
                        if (list != null && list.size() > 0) {
                            unitModels.addAll(list);
                            for (MeetingRoomModel.MeetingRoomModelData unitModel : unitModels) {
                                units.add(unitModel.getUnitName());
                            }
                            unitAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }, city);
    }

    public void getMeetingRooms(String EmpCode, boolean roomType){

        meetingRooms.clear();
        roomsList.getRecycledViewPool().clear();
        meetingRoomAdapter.notifyDataSetChanged();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getMeetingRooms(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    MeetingRoomListModel meetingRoomListModel = (MeetingRoomListModel) carotResponse.getData();
                    List<MeetingRoomListModel.MeetingRoomListModelData> list = meetingRoomListModel.getMeetingRoomListModelData();
                    if(list!=null && list.size()>0){
                        meetingRooms.addAll(list);
                    }
                    meetingRoomAdapter.notifyDataSetChanged();

                }

            }
        },EmpCode,roomType,unitCode);

    }

}
