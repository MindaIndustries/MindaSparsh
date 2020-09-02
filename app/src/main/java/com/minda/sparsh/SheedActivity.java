package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class SheedActivity extends AppCompatActivity {
    Button tv_operation, tv_menufacturing, tv_dwm;
    ImageView im_back;
    SharedPreferences myPref;
    Set<String> loginAccess;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheed);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("DWM");

        tv_dwm = (Button) findViewById(R.id.tv_dwm);
        im_back = (ImageView) findViewById(R.id.im_back);

        tv_menufacturing = (Button) findViewById(R.id.tv_menufacturing);
        tv_operation = (Button) findViewById(R.id.tv_operation);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        loginAccess = myPref.getStringSet("key", null);

        tv_dwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginAccess.contains("dwm") || loginAccess.contains("DWM")) {
                    Intent intent = new Intent(SheedActivity.this, DWMActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
                }

            }
        });


        tv_menufacturing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginAccess.contains("mdwm") || loginAccess.contains("MDWM")) {
                    Intent intent = new Intent(SheedActivity.this, DWMMfgHeadActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginAccess.contains("odwm") || loginAccess.contains("ODWM")) {
                    Intent intent = new Intent(SheedActivity.this, DWMOperationHeadActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
                }
            }
        });
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
