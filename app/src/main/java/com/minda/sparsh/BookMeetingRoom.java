package com.minda.sparsh;

import android.app.ProgressDialog;
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
import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.Adapter.InternalEmployeeAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AutoNameModel;
import com.minda.sparsh.model.MeetingBookResponse;
import com.minda.sparsh.model.MeetingDetailModel;
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
    @BindView(R.id.bookedby)
    TextInputEditText bookedby;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.internalattList)
    RecyclerView internalattList;
    @BindView(R.id.location)
    TextInputEditText location;
    @BindView(R.id.release)
    TextInputEditText releasereason;
    @BindView(R.id.bookedbylayout)
    TextInputLayout bookedbylayout;
    @BindView(R.id.locationlayout)
    TextInputLayout locationlayout;
    @BindView(R.id.releasereason)
    TextInputLayout releasereasonlayout;
    String datestr,dateforapi;
    List<AutoNameModel.AutoNameModelData> autoNames = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayAdapter<String> autoNameAdapter;
    int slotId,meetingRoomID;
    String EmpCode,unitCode;
    SharedPreferences myPref;
    InternalEmployeeAdapter internalEmployeeAdapter;
    ArrayList<AutoNameModel.AutoNameModelData> autoNamesInternal = new ArrayList<>();
    ArrayList<String> autoNamesInternalEmpcode = new ArrayList<>();

    boolean vc,release;
    String city,selected_unit;
    ProgressDialog progressDialog;
    String meetingId="";

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
        city = myPref.getString("user_city","");
        selected_unit = myPref.getString("selected_unit","");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        if(getIntent()!=null && getIntent().getStringExtra("date")!=null){
            datestr = getIntent().getStringExtra("date");
            dateforapi = getIntent().getStringExtra("dateforapi");
            slotId = getIntent().getIntExtra("slotId",0);
            meetingRoomID = getIntent().getIntExtra("meetingRoomId",0);
            meetingId = getIntent().getStringExtra("meetingId");
            vc = getIntent().getBooleanExtra("vc",false);
            release = getIntent().getBooleanExtra("release",false);
            date.setText(datestr);
            if(getIntent().getBooleanExtra("booked",false)){
                confirm_book.setVisibility(View.GONE);
            }
            else{
                confirm_book.setVisibility(View.VISIBLE);

            }

        }
        if(release){
            bookedbylayout.setVisibility(View.VISIBLE);
            releasereasonlayout.setVisibility(View.VISIBLE);
            locationlayout.setVisibility(View.VISIBLE);
            confirm_book.setText("Release");
        }

        getMeetingDetails();

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
                if (release) {
                    if (releasereason.getText().toString().length() > 0) {
                        confirmBooking("" + slotId, meetingRoomID, EmpCode, dateforapi, releasereason.getText().toString(), autoNamesInternalEmpcode.toString().replace("[", "").replace("]", "").replace(" ", ""), external_Att.getText().toString(), "Release", "", unitCode, vc);
                    } else {
                        Toast.makeText(BookMeetingRoom.this, "Enter Release Reason", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (agenda.getText().toString().length() > 0) {
                        if (internalAtt.getText().toString().length() > 0) {
                            Toast.makeText(BookMeetingRoom.this, "Enter a valid internal attendee details", Toast.LENGTH_LONG).show();
                        } else {
                            confirmBooking("" + slotId, meetingRoomID, EmpCode, dateforapi, agenda.getText().toString(), autoNamesInternalEmpcode.toString().replace("[", "").replace("]", "").replace(" ", ""), external_Att.getText().toString(), "Booked", meetingId, unitCode, vc);
                        }
                    }
                    else {
                        Toast.makeText(BookMeetingRoom.this, "Enter Agenda", Toast.LENGTH_LONG).show();
                    }
                }
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
                    if(!autoNamesInternal.contains(autoNames.get(i))) {
                        autoNamesInternal.add(autoNames.get(i));
                        autoNamesInternalEmpcode.add(autoNames.get(i).getEmpCode());
                        internalEmployeeAdapter.notifyDataSetChanged();
                    }
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
        progressDialog.show();
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.takeActionForMeetingRoom(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                progressDialog.dismiss();
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    MeetingBookResponse meetingBookResponse = (MeetingBookResponse) carotResponse.getData();
                    if (meetingBookResponse.getData().equals("Cancelled")) {
                        showMsgUpdate("Success", "Released successfully");
                    } else {
                        showMsgUpdate("Success", "" + meetingBookResponse.getData());
                    }
                }
                else if(carotResponse.getStatuscode() == 406){
                    showMsgUpdate("Error",""+carotResponse.getData());
                }
                else{
                    Toast.makeText(BookMeetingRoom.this,"Oops! Something went wrong.",Toast.LENGTH_LONG).show();
                }
            }
        },MeetingRoomSlotId,MeetingRoomId,EmpCode,BookingDate,Purpose,AttendeeInt.trim(),AttendeeExt,ActType,MeetingId,UnitCode,RoomType);
    }

    @Override
    public void onClick(View view, int position) {
        autoNamesInternal.remove(position);
        autoNamesInternalEmpcode.remove(position);
        internalEmployeeAdapter.notifyDataSetChanged();
    }
    public void showMsgUpdate(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            arg0.dismiss();
            if(title.equals("Success")){
                onBackPressed();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void getMeetingDetails(){
        MeetingRoomServices meetingRoomServices = new MeetingRoomServices();
        meetingRoomServices.getMeetingDetails(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    MeetingDetailModel meetingDetailModel = (MeetingDetailModel) carotResponse.getData();
                    if(meetingDetailModel!=null ){
                        List<MeetingDetailModel.MeetingDetailModelData> list = meetingDetailModel.getData();
                        if(list!=null && list.size()>0){
                            if(list.get(0).getPurpose()!=null){
                                agenda.setText(list.get(0).getPurpose());
                                agenda.setEnabled(false);
                                internalAtt.setText(list.get(0).getAttendeeInternal());
                                internalAtt.setEnabled(false);
                                external_Att.setText(list.get(0).getAttendeeExt());
                                external_Att.setEnabled(false);
                                date.setText(list.get(0).getBookingDate()+" at "+list.get(0).getTimeSlot());
                                bookedby.setText(""+list.get(0).getUM_USER_DESC());
                                bookedbylayout.setVisibility(View.VISIBLE);
                                location.setText(""+list.get(0).getMeetingRoom() +" at "+selected_unit+" ("+list.get(0).getDivID()+" )");
                            }
                        }
                    }
                }
            }
        }, String.valueOf(slotId),dateforapi);

    }

}
