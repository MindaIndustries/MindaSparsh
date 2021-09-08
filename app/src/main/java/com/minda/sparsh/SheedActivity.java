package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        toolbar =  findViewById(R.id.toolbar);
        title =  findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.dwm));

        tv_dwm =  findViewById(R.id.tv_dwm);
        im_back =  findViewById(R.id.im_back);

        tv_menufacturing =  findViewById(R.id.tv_menufacturing);
        tv_operation =  findViewById(R.id.tv_operation);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        loginAccess = myPref.getStringSet("key", null);

        tv_dwm.setOnClickListener(view -> {

            if (loginAccess.contains("dwm") || loginAccess.contains("DWM")) {
                Intent intent = new Intent(SheedActivity.this, DWMActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
            }

        });


        tv_menufacturing.setOnClickListener(view -> {

            if (loginAccess.contains("mdwm") || loginAccess.contains("MDWM")) {
                Intent intent = new Intent(SheedActivity.this, DWMMfgHeadActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
            }
        });

        tv_operation.setOnClickListener(view -> {

            if (loginAccess.contains("odwm") || loginAccess.contains("ODWM")) {
                Intent intent = new Intent(SheedActivity.this, DWMOperationHeadActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SheedActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
            }
        });
        im_back.setOnClickListener(view -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
