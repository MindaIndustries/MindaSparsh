package com.minda.sparsh;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MissionActivity extends AppCompatActivity {
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        getSupportActionBar().hide();
        im_back =  findViewById(R.id.im_back);
        im_back.setOnClickListener(view -> finish());
    }
}
