package com.minda.sparsh;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NeedhelpActivity extends AppCompatActivity {
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needhelp);
        getSupportActionBar().hide();
        im_back = (ImageView) findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
