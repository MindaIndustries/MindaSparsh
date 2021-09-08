package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IdentityAccessManagementActivity1 extends AppCompatActivity {

    ImageView iv_approve, im_back, iv_process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_access_management1);

        iv_approve = findViewById(R.id.iv_approve);
        iv_process = findViewById(R.id.iv_process);
        im_back =  findViewById(R.id.im_back);

        im_back.setOnClickListener(view -> finish());

        iv_approve.setOnClickListener(view -> startActivity(new Intent(IdentityAccessManagementActivity1.this, ApproveListActivity1.class)));

        iv_process.setOnClickListener(view -> Toast.makeText(IdentityAccessManagementActivity1.this, "Coming Soon !", Toast.LENGTH_SHORT).show());

    }
}
