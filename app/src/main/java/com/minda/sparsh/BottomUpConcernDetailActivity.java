package com.minda.sparsh;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.services.BottomUpConcernServices;
import com.minda.sparsh.util.BackgroundNotificationService;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class BottomUpConcernDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.concern_no_value)
    TextView concern_no_value;
    @BindView(R.id.concern_date_spinner)
    TextView concern_date_spinner;
    @BindView(R.id.unit_spinner)
    TextView unit_spinner;
    @BindView(R.id.responsible_spinner)
    TextView responsible_spinner;
    BottomUpConcern bottomUpConcern;
    @BindView(R.id.raisedbyvalue)
    TextView raisedbyvalue;
    @BindView(R.id.status_value)
    TextView status_value;
    @BindView(R.id.msm_reference_value)
    TextView msm_reference_value;
    @BindView(R.id.existing_system_value)
    TextView existing_system_value;
    @BindView(R.id.proposed_system_value)
    TextView proposed_system_value;
    @BindView(R.id.benefit_value)
    TextView benefit_value;
    @BindView(R.id.attachtext1)
    TextView attachtext1;
    @BindView(R.id.attachtext2)
    TextView attachtext2;
    @BindView(R.id.attachtext3)
    TextView attachtext3;
    @BindView(R.id.doc_view1)
    ImageView docView1;
    @BindView(R.id.doc_view2)
    ImageView docView2;
    @BindView(R.id.doc_view3)
    ImageView docView3;
    @BindView(R.id.assign_detail_layout)
    RelativeLayout assignDetailLayout;
    @BindView(R.id.btn_layout)
    LinearLayout btnLayout;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.btn_assign)
    Button btnAssign;
    @BindView(R.id.assignedDetails)
    RelativeLayout assignedDetails;
    @BindView(R.id.action_suggestion_assigned_value)
    TextView action_suggestion_assigned_value;
    @BindView(R.id.assigned_to_value)
    TextView assigned_to_value;
    @BindView(R.id.target_dated_value)
    TextView target_dated_value;
    @BindView(R.id.remarked_value)
    TextView remarked_value;
    SharedPreferences myPref;
    String name;

    private static final int PERMISSION_REQUEST_CODE = 1;

    public static final String PROGRESS_UPDATE = "progress_update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bottom_up_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Bottom Up Concern");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);


        if (getIntent() != null && getIntent().getSerializableExtra("concernModel") != null) {
            bottomUpConcern = (BottomUpConcern) getIntent().getSerializableExtra("concernModel");
        }
        if (bottomUpConcern != null) {
            if (bottomUpConcern.getConcernNo() != null) {
                concern_no_value.setText(bottomUpConcern.getConcernNo());
            }
            if (bottomUpConcern.getRaisedOn() != null) {
                concern_date_spinner.setText(bottomUpConcern.getRaisedOn());
            }
            if (bottomUpConcern.getUnit() != null) {
                unit_spinner.setText(bottomUpConcern.getUnit());
            }
            responsible_spinner.setText(bottomUpConcern.getDeptName());
            raisedbyvalue.setText(bottomUpConcern.getRaisedBy());

            if (bottomUpConcern.getStatus().equalsIgnoreCase("True")) {
                status_value.setText("Closed");
                assignDetailLayout.setVisibility(View.GONE);
                btnLayout.setVisibility(View.GONE);

            } else {
                if (bottomUpConcern.getFlag().equalsIgnoreCase("True")) {
                    status_value.setText("Assigned");

                } else {
                    status_value.setText("Pending");
                }
            }
            if(bottomUpConcern.getAssignedTo()!=null && bottomUpConcern.getAssignedTo().length()>0){
                assignedDetails.setVisibility(View.VISIBLE);
                btnLayout.setVisibility(View.GONE);
                assigned_to_value.setText(bottomUpConcern.getAssignedName());
                action_suggestion_assigned_value.setText(bottomUpConcern.getAction());
                target_dated_value.setText(bottomUpConcern.getTargetDate());
                remarked_value.setText(bottomUpConcern.getRemarks1());
            }
            else{
                assignedDetails.setVisibility(View.GONE);
                btnLayout.setVisibility(View.VISIBLE);
            }

            msm_reference_value.setText(bottomUpConcern.getReferenceNo());
            existing_system_value.setText(bottomUpConcern.getExistingSystem());
            proposed_system_value.setText(bottomUpConcern.getProposedSystem());
            benefit_value.setText(bottomUpConcern.getBenefit());
            if (bottomUpConcern.getESDocument() != null) {
                attachtext1.setText(bottomUpConcern.getESDocument());
                if (bottomUpConcern.getESDocument().contains("png") || bottomUpConcern.getESDocument().contains("jpg") || bottomUpConcern.getESDocument().contains("jpeg")){

                Glide.with(BottomUpConcernDetailActivity.this).load(RetrofitClient2.bottomup_img + bottomUpConcern.getESDocument()).apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .fitCenter()
                        .dontTransform())
                        .into(docView1);
            }

            }
            if (bottomUpConcern.getPSDocument() != null) {
                attachtext2.setText(bottomUpConcern.getPSDocument());
                if (bottomUpConcern.getPSDocument().contains("png") || bottomUpConcern.getPSDocument().contains("jpg") || bottomUpConcern.getPSDocument().contains("jpeg")) {

                    Glide.with(BottomUpConcernDetailActivity.this).load(RetrofitClient2.bottomup_img + bottomUpConcern.getPSDocument()).apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .fitCenter()
                            .dontTransform())
                            .into(docView2);
                }
            }

            if (bottomUpConcern.getBDocument() != null) {
                attachtext3.setText(bottomUpConcern.getBDocument());
                if (bottomUpConcern.getBDocument().contains("png") || bottomUpConcern.getBDocument().contains("jpg") || bottomUpConcern.getBDocument().contains("jpeg")) {

                    Glide.with(BottomUpConcernDetailActivity.this).load(RetrofitClient2.bottomup_img + bottomUpConcern.getBDocument()).apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .fitCenter()
                            .dontTransform())
                            .into(docView3);
                }

            }
        }
        registerReceiver();


        existing_system_value.setMovementMethod(new ScrollingMovementMethod());
        proposed_system_value.setMovementMethod(new ScrollingMovementMethod());
        benefit_value.setMovementMethod(new ScrollingMovementMethod());

        existing_system_value.setMovementMethod(new ScrollingMovementMethod());
        ScrollingMovementMethod.getInstance();


        existing_system_value.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                existing_system_value.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }


        });

        proposed_system_value.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                proposed_system_value.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }


        });

        benefit_value.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                benefit_value.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }


        });

    }


    @OnClick(R.id.attachtext1)
    public void onClickAttach1(){
        if(Utility.isOnline(BottomUpConcernDetailActivity.this)) {
            name = bottomUpConcern.getESDocument();
            if(hasWritePermissions()) {
                myPref.edit().putString("download", bottomUpConcern.getESDocument()).commit();
                startImageDownload(bottomUpConcern.getESDocument());
            }
            else {
                requestPermission();
            }
        }
        else{
            Toast.makeText(BottomUpConcernDetailActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }
    }
    @OnClick(R.id.attachtext2)
    public void onClickAttach2(){
        if(Utility.isOnline(BottomUpConcernDetailActivity.this)) {
            name = bottomUpConcern.getPSDocument();
            if(hasWritePermissions()) {
                myPref.edit().putString("download", bottomUpConcern.getPSDocument()).commit();
                startImageDownload(bottomUpConcern.getPSDocument());
            }
            else {
                requestPermission();
            }
        }
        else{
            Toast.makeText(BottomUpConcernDetailActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }

    }

    @OnClick(R.id.attachtext3)
    public void onClickAttach3() {
        if (Utility.isOnline(BottomUpConcernDetailActivity.this)) {
            name = bottomUpConcern.getBDocument();
            if (hasWritePermissions()) {
                myPref.edit().putString("download", bottomUpConcern.getBDocument()).commit();
                startImageDownload(bottomUpConcern.getBDocument());
            } else {
                requestPermission();
            }
        } else {
            Toast.makeText(BottomUpConcernDetailActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }
    }

    @OnClick(R.id.btn_assign)
    public void onClickBtnAssign(){
        if(assignDetailLayout.getVisibility()== View.GONE){
            assignDetailLayout.setVisibility(View.VISIBLE);
            btnComplete.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_complete)
    public void onClickBtnComplete(){

    }

    @OnClick(R.id.btn_cancel)
    public void onClickCancel() {
        onBackPressed();
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



    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PROGRESS_UPDATE);
        bManager.registerReceiver(mBroadcastReceiver, intentFilter);

    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(PROGRESS_UPDATE)) {

                boolean downloadComplete = intent.getBooleanExtra("downloadComplete", false);
                if (downloadComplete) {
                    Toast.makeText(getApplicationContext(), "File download completed", Toast.LENGTH_SHORT).show();



                }
            }
        }
    };

    public void startImageDownload(String name) {
        BackgroundNotificationService backgroundNotificationService = new BackgroundNotificationService(name);
        Intent intent = new Intent(this, BackgroundNotificationService.class);
        startService(intent);
    }
    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(BottomUpConcernDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startImageDownload(name);
                } else {

                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }
}
