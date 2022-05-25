package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.Adapter.InternalEmployeeAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AutoNameModel;
import com.minda.sparsh.services.MeetingRoomServices;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookMeetingRoom extends AppCompatActivity implements InternalEmployeeAdapter.OnItemClickListener {
    @BindView(R.id.date)
    TextInputEditText date;
    @BindView(R.id.agenda)
    TextInputEditText agenda;
    @BindView(R.id.internal_att)
    AppCompatAutoCompleteTextView internalAtt;
    @BindView(R.id.external_att)
    TextInputEditText external_Att;
    @BindView(R.id.confirm_book)
    Button confirm_book;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.internalattList)
    RecyclerView internalattList;
    String datestr,dateforapi;
    List<AutoNameModel.AutoNameModelData> autoNames = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayAdapter<String> autoNameAdapter;
    int slotId,meetingRoomID;
    String EmpCode,unitCode;
    SharedPreferences myPref;
    InternalEmployeeAdapter internalEmployeeAdapter;
    ArrayList<AutoNameModel.AutoNameModelData> autoNamesInternal = new ArrayList<>();
    boolean vc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookroom);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Book Meeting Room");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        EmpCode = myPref.getString("Id", "");
        unitCode =myPref.getString("Um_div_code","");

        if(getIntent()!=null && getIntent().getStringExtra("date")!=null){
            datestr = getIntent().getStringExtra("date");
            dateforapi = getIntent().getStringExtra("dateforapi");
            slotId = getIntent().getIntExtra("slotId",0);
            meetingRoomID = getIntent().getIntExtra("meetingRoomId",0);
            vc = getIntent().getBooleanExtra("vc",false);
            date.setText(datestr);

        }
        internalEmployeeAdapter = new InternalEmployeeAdapter(BookMeetingRoom.this,autoNamesInternal);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(BookMeetingRoom.this, LinearLayoutManager.VERTICAL, false);
        internalattList.setLayoutManager(mLayoutManager);
        internalattList.setAdapter(internalEmployeeAdapter);
        internalEmployeeAdapter.setClickListener(this);
        initAutoNameAdapter();
        internalAtt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    if(Utility.isOnline(BookMeetingRoom.this)) {
                        getAutoName(charSequence.toString());
                    }
                    else{
                        Toast.makeText(BookMeetingRoom.this,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirm_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmBooking(""+slotId,meetingRoomID,EmpCode,dateforapi,agenda.getText().toString(),autoNamesInternal.toString(),"","Booked","",unitCode,vc);
            }
        });
    }

    public void initAutoNameAdapter(){
        autoNameAdapter  = new ArrayAdapter<String>(BookMeetingRoom.this,android.R.layout.simple_spinner_item,names);
        internalAtt.setAdapter(autoNameAdapter);
        internalAtt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(autoNames.size()>0) {
                    autoNamesInternal.add(autoNames.get(i));
                    internalEmployeeAdapter.notifyDataSetChanged();
                    internalAtt.setText("");
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAutoName(String prefixText){
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getAutoName(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                autoNames.clear();
                names.clear();
                autoNameAdapter.notifyDataSetChanged();
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    AutoNameModel autoNameModel = (AutoNameModel) carotResponse.getData();
                    if(autoNameModel!=null){
                        List<AutoNameModel.AutoNameModelData> list = autoNameModel.getData();
                        if(list!=null && list.size()>0){
                            autoNames.addAll(list);
                            for (AutoNameModel.AutoNameModelData autoName : autoNames) {
                                names.add(autoName.getValue());
                            }
                            autoNameAdapter  = new ArrayAdapter<String>(BookMeetingRoom.this,android.R.layout.simple_spinner_item,names);
                            internalAtt.setAdapter(autoNameAdapter);
                            autoNameAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        },prefixText);
    }

    public void confirmBooking(String MeetingRoomSlotId,int MeetingRoomId,String EmpCode,String BookingDate,String Purpose,String AttendeeInt,String AttendeeExt,String ActType,String MeetingId,String UnitCode,boolean RoomType){

        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.takeActionForMeetingRoom(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    showMsgUpdate("Success", String.valueOf(carotResponse.getData()));
                }
                else if(carotResponse.getStatuscode() == 406){
                    showMsgUpdate("Error",""+carotResponse.getData());
                }
            }
        },MeetingRoomSlotId,MeetingRoomId,EmpCode,BookingDate,Purpose,AttendeeInt,AttendeeExt,ActType,MeetingId,UnitCode,RoomType);
    }

    @Override
    public void onClick(View view, int position) {
        autoNamesInternal.remove(position);
        internalEmployeeAdapter.notifyDataSetChanged();
    }
    public void showMsgUpdate(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            arg0.dismiss();
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
