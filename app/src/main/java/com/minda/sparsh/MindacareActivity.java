package com.minda.sparsh;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.minda.sparsh.Adapter.MindaCareQuesAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CheckinDetailsResponse;
import com.minda.sparsh.model.CityResponse;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.QuesResponse;
import com.minda.sparsh.model.StateResponse;
import com.minda.sparsh.services.EHSServices;
import com.minda.sparsh.services.MindacareServices;
import com.minda.sparsh.util.Utility;
import com.paperplay.myformbuilder.MyCheckbox;
import com.paperplay.myformbuilder.MyEdittext;
import com.paperplay.myformbuilder.MyRadioButton;
import com.paperplay.myformbuilder.MySpinner;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MindacareActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.wfo)
    Button wfo;
    @BindView(R.id.wfh)
    Button wfh;
    SharedPreferences myPref;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_layout)
    RelativeLayout mapLayout;
    @BindView(R.id.clock)
    Button clock;
    @BindView(R.id.questions)
    RecyclerView recyclerView;
    @BindView(R.id.time_layout)
    RelativeLayout timeLayout;
    @BindView(R.id.clockintime)
    TextView clockintime;
    @BindView(R.id.totalhr)
    TextView totalhr;
    @BindView(R.id.clockouttime)
    TextView clockouttime;

    LatLng LATLNG;
    String selected = "0";
    List<QuesResponse> quesResponseList = new ArrayList<QuesResponse>();

    private boolean permissionDenied = false;
    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    String selection = "0";
    MindaCareQuesAdapter mindaCareQuesAdapter;
    HashMap<String, List<String>> options = new HashMap<String, List<String>>();
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    ArrayList<String> unitsName = new ArrayList<String>();
    List<EHSUnitModel> units = new ArrayList<EHSUnitModel>();
    String empCode, unitcode, User, DOB, Mobile;
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> state = new ArrayList<String>();
    ArrayList<StateResponse> stateObject = new ArrayList<>();
    ArrayList<CityResponse> cityObject = new ArrayList<>();
    MySpinner spinCity;
    ArrayAdapter adapter;
    MyCheckbox myCheckboxView = null;


    /*  FormBuilder formBuilder;
    ArrayList<FormObject> formElementMutableList;
  */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minda_care);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.mindacare_selfDeclaration));
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        unitcode = myPref.getString("Um_div_code", "");
        User = myPref.getString("username", "");
        DOB = myPref.getString("DOB", "");
        Mobile = myPref.getString("Mobile", "");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mindaCareQuesAdapter = new MindaCareQuesAdapter(MindacareActivity.this, quesResponseList, options);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MindacareActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mindaCareQuesAdapter);
       /* formBuilder = new FormBuilder(MindacareActivity.this,rootLayout);
        formElementMutableList = new ArrayList<FormObject>();
       */
        getCheckInDetails();
        mindacarequestions();
        getUnits();
        getStates();

    }

    @OnClick(R.id.clock)
    public void onclickClockInClockOut() {

        if (Utility.isOnline(MindacareActivity.this)) {
            if (clock.getText().toString().equalsIgnoreCase("CLOCK IN")) {
                clockInclockOut(empCode, "", "" + LATLNG.latitude, "" + LATLNG.longitude, "", "");
                //clock.setText("CLOCK OUT");
            } else {
                clockInclockOut(empCode, "", "", "", "" + LATLNG.latitude, "" + LATLNG.longitude);
                //  clock.setText("CLOCK IN");
            }
        } else {
            Toast.makeText(MindacareActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.wfh)
    public void onClickWfh() {
        selection = "Home";

        if (Utility.isOnline(MindacareActivity.this)) {
            getCheckInDetails();
        } else {
            Toast.makeText(MindacareActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.wfo)
    public void onClickWfo() {
        mapLayout.setVisibility(View.GONE);
        selection = "Office";
        if (Utility.isOnline(MindacareActivity.this)) {
            if (selected.equalsIgnoreCase("H")) {
                Toast.makeText(MindacareActivity.this, "You have already selected working from Home.", Toast.LENGTH_LONG).show();
                return;
            }
            getCheckInDetails();
        } else {
            Toast.makeText(MindacareActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
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

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                checkGPS();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            /*PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
     */

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    public void getCheckInDetails() {
        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.getCheckinDetails(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpURLConnection.HTTP_OK) {
                    if (carotResponse.getData() != null) {
                        List<CheckinDetailsResponse> list = (List<CheckinDetailsResponse>) carotResponse.getData();
                        if (list != null && list.size() > 0) {
                            if (list.get(0).getStatus().equals("H")) {
                                selected = "H";
                                mapLayout.setVisibility(View.VISIBLE);
                                wfo.setBackgroundColor(getResources().getColor(R.color.disableBtn));
                            } else if (list.get(0).getStatus().equals("O")) {
                                selected = "O";
                                mapLayout.setVisibility(View.GONE);
                                wfh.setBackgroundColor(getResources().getColor(R.color.disableBtn));
                            } else {
                                if (selection.equals("Home")) {
                                    mapLayout.setVisibility(View.VISIBLE);
                                }
                            }
                            if (list.get(0).getInTime() != null && list.get(0).getInTime().length() > 0) {
                                clock.setText("Clock Out");
                                timeLayout.setVisibility(View.VISIBLE);
                                clockintime.setText("Clock In: " + list.get(0).getRIntime());
                            }

                            if (list.get(0).getOutTime() != null && list.get(0).getOutTime().length() > 0) {
                                clock.setVisibility(View.GONE);
                                mapLayout.setVisibility(View.GONE);
                                timeLayout.setVisibility(View.VISIBLE);
                                clockintime.setText("Clock In: " + list.get(0).getRIntime());
                                clockouttime.setText("Clock Out: " + list.get(0).getOutTime().split(" ")[1].split(":")[0] + ":" + list.get(0).getOutTime().split(" ")[1].split(":")[1] + list.get(0).getOutTime().split(" ")[2]);
                                totalhr.setText("Total Working Hour: " + convertDate(list.get(0).getInTime(), list.get(0).getOutTime()));
                                Toast.makeText(MindacareActivity.this, "Today’s Clock In and Clock Out is completed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }


            }
        }, empCode);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        checkGPS();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //  Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.getUiSettings().setMapToolbarEnabled(false);
        enableMyLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    public void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS_main", "OnSuccess");
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MindacareActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(latLng);
                                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                                    LATLNG = latLng;
                                    map.clear();
                                    map.addMarker(markerOptions);
                                }
                            }
                        });
                // GPS is ON
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MindacareActivity.this, 123);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // GPS was turned on;
                    map.setMyLocationEnabled(true);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        markerOptions.position(latLng);
                                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                                        map.clear();
                                        map.addMarker(markerOptions);

                                        // Logic to handle location object
                                    }
                                }
                            });

                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(MindacareActivity.this, "You won't be able to submit your Declaration if you Deny this Setting", Toast.LENGTH_LONG).show();
                    // User rejected turning on the GPS
                    break;
                default:
                    break;
            }
        }
    }

    public void clockInclockOut(String empCode, String message, String InLattitiude, String InLongitude, String OutLattitude, String OutLongitude) {
        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.clockInclockOut(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getData() != null && carotResponse.getData().toString().length() > 0) {
                    if (carotResponse.getStatuscode() == HttpURLConnection.HTTP_OK) {
                        if (clock.getText().toString().equalsIgnoreCase("CLOCK IN")) {
                            clock.setText("CLOCK OUT");
                            Toast.makeText(MindacareActivity.this, "Thank you for starting your day with Clock in, Fill Self declaration form.", Toast.LENGTH_LONG).show();
                        } else {
                            clock.setText("CLOCK IN");
                        }
                    }
                }
            }
        }, empCode, message, InLattitiude, InLongitude, OutLattitude, OutLongitude);
    }

    public void mindacarequestions() {
      /*  quesResponseList.clear();
        options.clear();
        recyclerView.getRecycledViewPool().clear();
      */
        mindaCareQuesAdapter.notifyDataSetChanged();
        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.mindacarequestions(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpURLConnection.HTTP_OK) {
                    List<QuesResponse> list = (List<QuesResponse>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        quesResponseList.addAll(list);

                        for (int i = 0; i < quesResponseList.size(); i++) {
                            options.put(quesResponseList.get(i).getID(), convertStringtoArrayList(quesResponseList.get(i).getOpts()));
                            switch (quesResponseList.get(i).getTypes()) {
                                case "TextBox":
                                    MyEdittext.Builder edtBuilder = new MyEdittext.Builder(MindacareActivity.this).setFormLayout(rootLayout);
                                    try {
                                        MyEdittext edittext = edtBuilder.clone().setTitle(quesResponseList.get(i).getQues()).create();
                                        edittext.setTag(quesResponseList.get(i).getID());
                                        if (quesResponseList.get(i).getQues().contains("Input Your Employee Code")) {
                                            edittext.setValue(empCode);
                                            edittext.setEnabled(false);
                                        } else if (quesResponseList.get(i).getQues().contains("Input Your Name")) {
                                            edittext.setValue(User);
                                            edittext.setEnabled(false);
                                        } else if (quesResponseList.get(i).getQues().contains("Input Your Age")) {
                                            long dob = Long.parseLong(DOB.replace("/Date", "").replace("/", "").replace("(", "").replace(")", ""));

                                            Calendar dob1 = Calendar.getInstance();
                                            dob1.setTimeInMillis(dob);
                                            Calendar curr = Calendar.getInstance();
                                            int age = curr.get(Calendar.YEAR) - dob1.get(Calendar.YEAR);
                                            if (curr.get(Calendar.DAY_OF_YEAR) < dob1.get(Calendar.DAY_OF_YEAR)) {
                                                age--;
                                            }
                                            Integer ageInt = new Integer(age);
                                            String ageS = ageInt.toString();

                                            edittext.setValue(ageS);
                                            edittext.setEnabled(false);
                                        } else if (quesResponseList.get(i).getQues().contains("Input your mobile Number")) {
                                            edittext.setValue(Mobile);
                                            edittext.setEnabled(false);
                                        } else if (quesResponseList.get(i).getQues().contains("Provide your Complete Current residence Address")) {
                                            edittext.setValue(quesResponseList.get(i).getAddress());
                                        }
                                    } catch (CloneNotSupportedException e) {
                                        e.printStackTrace();
                                    }


                                    // formElementMutableList.add(new FormElement().setTag(quesResponseList.get(i).getID()).setTitle(quesResponseList.get(i).getQues()).setType(FormElement.Type.TEXT).setHint(quesResponseList.get(i).getQues()));

                                    break;
                                case "CheckBox":
                                    ArrayList<String> optionsArr = new ArrayList<>();
                                    optionsArr.addAll(options.get(quesResponseList.get(i).getID()));
                                    TextView textView = new TextView(MindacareActivity.this);
                                    textView.setText(quesResponseList.get(i).getQues());
                                    rootLayout.addView(textView);
                                    HashMap<String, CheckBox> checkBoxItemSelected = new HashMap<String, CheckBox>();


                                    for (int a = 0; a < optionsArr.size(); a++) {
                                        CheckBox checkBox = new CheckBox(MindacareActivity.this);
                                        checkBox.setId(a);
                                        checkBox.setText(optionsArr.get(a));
                                        rootLayout.addView(checkBox);
                                        checkBoxItemSelected.put(optionsArr.get(a), checkBox);
                                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                if(!optionsArr.contains("None of the above")){
                                                    return;
                                                }
                                                if (checkBoxItemSelected.get("None of the above").isChecked()) {
                                                    for (String key : checkBoxItemSelected.keySet()) {
                                                        if (!key.equalsIgnoreCase("None of the above")) {
                                                            checkBoxItemSelected.get(key).setChecked(false);
                                                        }
                                                    }
                                                }

                                            }
                                        });

                                    }




                                     /*   myCheckboxView = new MyCheckbox.Builder(MindacareActivity.this)
                                            .setTitle(quesResponseList.get(i).getQues()).setCheckBoxItem(optionsArr).setOnCheckedListener(new MyCheckbox.OnCheckedListener() {
                                                @Override
                                                public void onchecked(String checkboxName) {
                                                    if(checkboxName.equalsIgnoreCase("None of the above")){
                                                        for(int m=0; m<myCheckboxView.getAllChecked().size();m++){
                                                            if(myCheckboxView.isChecked("None of the above")){
                                                             CheckBox checkBox =    myCheckboxView.getAllChecked().get(m).get;
                                                            }
                                                        }

                                                    }
                                                }
                                            })
                                            .setFormLayout(rootLayout).create();
                                   // ((LinearLayout) myCheckboxView.getView()).getChildAt(0);



                                    myCheckboxView.setTag(quesResponseList.get(i).getID());
*/

                                    //  formElementMutableList.add(new FormElement().setTag(quesResponseList.get(i).getID()).setTitle(quesResponseList.get(i).getQues()).setType(FormElement.Type.MULTIPLE_SELECTION).setOptions(options.get(quesResponseList.get(i).getID())).setHint(quesResponseList.get(i).getQues()));
                                    break;
                                case "RadioButton":
                                    String[] optionRadio = new String[options.get(quesResponseList.get(i).getID()).size()];
                                    for (int k = 0; k < options.get(quesResponseList.get(i).getID()).size(); k++) {
                                        optionRadio[k] = options.get(quesResponseList.get(i).getID()).get(k);
                                    }

                                    MyRadioButton rdbGender = new MyRadioButton.Builder(MindacareActivity.this)
                                            .setTitle(quesResponseList.get(i).getQues()).setOptionList(optionRadio)
                                            .setFormLayout(rootLayout).create();
                                    rdbGender.setTag(quesResponseList.get(i).getID());

                                    if (quesResponseList.get(i).getQues().contains("What is your Gender")) {
                                        rdbGender.setValue(quesResponseList.get(i).getGender());
                                    }
                                    // formElementMutableList.add(new FormElement().setTag(quesResponseList.get(i).getID()).setTitle(quesResponseList.get(i).getQues()).setType(FormElement.Type.SELECTION).setOptions(options.get(quesResponseList.get(i).getID())).setHint(quesResponseList.get(i).getQues()));
                                    break;
                                case "DropDown":
                                    ArrayList<String> tempArr = new ArrayList<>();
                                    if (quesResponseList.get(i).getQues().contains("What is your current Body Temperature ?")) {
                                        for (double l = 920; l < 1070; l++) {
                                            tempArr.add("" + (l / 10));
                                        }
                                        MySpinner spinCity = new MySpinner.Builder(MindacareActivity.this)
                                                .setTitle(quesResponseList.get(i).getQues()).setItem(tempArr).setFormLayout(rootLayout).create();

                                        spinCity.setTag(quesResponseList.get(i).getID());

                                    } else if (quesResponseList.get(i).getQues().contains("Select Your Unit")) {
                                        MySpinner spinCity = new MySpinner.Builder(MindacareActivity.this)
                                                .setTitle(quesResponseList.get(i).getQues()).setItem(unitsName).setFormLayout(rootLayout).create();

                                        spinCity.setTag(quesResponseList.get(i).getID());

                                    } else if (quesResponseList.get(i).getQues().contains("What is your current residence State and City/District ?")) {
                                        state.clear();
                                        for (StateResponse stateResponse : stateObject) {
                                            state.add(stateResponse.getState());
                                        }

                                        MySpinner spinState = new MySpinner.Builder(MindacareActivity.this)
                                                .setTitle(quesResponseList.get(i).getQues()).setItem(state).setFormLayout(rootLayout).create();
                                        spinState.setTag(quesResponseList.get(i).getID());
                                        spinState.setValue(""+quesResponseList.get(i).getState());

                                        spinState.setSpinnerOnSelectedListener(new MySpinner.OnSelectedListener() {
                                            @Override
                                            public void onSelected(String value) {
                                                city.clear();
                                                if (spinCity != null) {
                                                    synchronized (((LinearLayout) spinCity.getView()).getChildAt(1)) {
                                                        ((LinearLayout) spinCity.getView()).getChildAt(1);
                                                        Spinner spinCity1 = (Spinner) ((LinearLayout) spinCity.getView()).getChildAt(1);
                                                        adapter = new ArrayAdapter<String>(MindacareActivity.this, R.layout.city_row, city) {
                                                            @Override
                                                            public boolean isEnabled(int position) {
                                                                return super.isEnabled(position);
                                                            }

                                                            @Override
                                                            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                                                return super.getDropDownView(position, convertView, parent);
                                                            }
                                                        };
                                                        spinCity1.setAdapter(adapter);
                                                        adapter.notifyDataSetChanged();

                                                    }
                                                }

                                                StateResponse stateResponse = new StateResponse();
                                                stateResponse.setState(value);
                                                int i = stateObject.indexOf(stateResponse);
                                                getCity(stateObject.get(i).getId(), i);
                                            }
                                        });
                                        city.add("Select City");

                                        spinCity = new MySpinner.Builder(MindacareActivity.this)
                                                .setTitle("City").setItem(city).setFormLayout(rootLayout).create();
                                        spinCity.setTag(quesResponseList.get(i).getID());



                                    }
                                    // formElementMutableList.add(new FormElement().setTag(quesResponseList.get(i).getID()).setTitle(quesResponseList.get(i).getQues()).setType(FormElement.Type.SPINNER).setHint(quesResponseList.get(i).getQues()));
                                    break;

                            }
                        }
/*
                        formBuilder.build(formElementMutableList);
*/


                    }
                }
                mindaCareQuesAdapter.notifyDataSetChanged();
            }
        }, empCode);

    }

    public List<String> convertStringtoArrayList(String opts) {
        String[] optsarray = opts.split(",");
        List<String> fixedLenghtList = Arrays.asList(optsarray);
        return fixedLenghtList;

    }

    public String convertDate(String dateString1, String dateString2) {

        String diff = "";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");

        try {
            Date date1 = format.parse(dateString1);
            Date date2 = format.parse(dateString2);
            long mills = date2.getTime() - date1.getTime();
            int hours = (int) (mills / (1000 * 60 * 60));
            int mins = (int) (mills / (1000 * 60)) % 60;

            diff = hours + ":" + mins;

            if (hours == 0) {
                diff = mins + "m";
            } else {
                diff = hours + " hr " + mins + " m";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }


    public void getUnits() {

        EHSServices ehsServices = new EHSServices();
        ehsServices.getUnits(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSUnitModel> list = (List<EHSUnitModel>) carotResponse.getData();
                    units.addAll(list);
                    for (EHSUnitModel unit : units) {
                        if (unit.getUnitCode().equals(unitcode)) {
                            unitsName.add(unit.getUnitCode() + ":" + unit.getUnitName());
                        }
                    }
                }

            }
        }, unitcode);

    }

    public void getStates() {
        stateObject.clear();
        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.getState(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<StateResponse> stateResponses = (List<StateResponse>) carotResponse.getData();
                    if (stateResponses != null && stateResponses.size() > 0) {
                        stateObject.addAll(stateResponses);
                    }
                }
            }
        });
    }


    public void getCity(String StateID, int i) {
        cityObject.clear();
        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.getCity(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<CityResponse> cityResponses = (List<CityResponse>) carotResponse.getData();
                    if (cityResponses != null && cityResponses.size() > 0) {
                        cityObject.addAll(cityResponses);
                    }
                    city.clear();

                    for (CityResponse cityResponse : cityObject) {
                        city.add(cityResponse.getDistrict());
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                    if(quesResponseList.get(i).getCity()!=null && quesResponseList.get(i).getCity().length()>0){
                        spinCity.setValue(""+quesResponseList.get(i).getCity());
                    }

                }

            }
        }, StateID);


    }

}
