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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CheckinDetailsResponse;
import com.minda.sparsh.model.QuesResponse;
import com.minda.sparsh.services.MindacareServices;
import com.minda.sparsh.util.Utility;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MindacareActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    @BindView(R.id.wfo)
    Button wfo;
    @BindView(R.id.wfh)
    Button wfh;
    String empCode;
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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getCheckInDetails();
        mindacarequestions();
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
                               totalhr.setText("Total Working Hour: "+convertDate(list.get(0).getInTime(), list.get(0).getOutTime()));
                                // totalhr.setText("Total Working Hour: "+ list. );
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
        quesResponseList.clear();
        //  recyclerView.getRecycledViewPool().clear();
        //  ehsObsAdapter.notifyDataSetChanged();

        MindacareServices mindacareServices = new MindacareServices();
        mindacareServices.mindacarequestions(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpURLConnection.HTTP_OK) {
                    List<QuesResponse> list = (List<QuesResponse>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        quesResponseList.addAll(list);
                    }
                }
            }
        });

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

            if(hours==0){
                diff = mins+"m";
            }

            else{
                diff = hours+" hr "+mins+" m";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }
}
