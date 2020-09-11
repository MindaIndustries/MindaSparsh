package com.minda.sparsh;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IdentityAccessManagementActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView im_back, iv_approve_request, iv_view_access_request, iv_access_request;

    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_access_management);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Identity & Access Management");

        im_back = (ImageView) findViewById(R.id.im_back);
        iv_approve_request = (ImageView) findViewById(R.id.iv_approve_request);
        iv_approve_request.setOnClickListener(this);
        iv_access_request = (ImageView) findViewById(R.id.iv_access_request);
        iv_access_request.setOnClickListener(this);
        iv_view_access_request = (ImageView) findViewById(R.id.iv_view_access_request);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        intiUiSetup();
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


    private void intiUiSetup() {
//        replaceFragment(new IAMMenuFragment(), false, false);
    }

//    public void replaceFragment(final Fragment fragment, final boolean needToAddBackStack, final boolean clearStack) {
//        final FragmentManager fm = getSupportFragmentManager();
//        final FragmentTransaction ft = fm.beginTransaction();
//        if (needToAddBackStack && !clearStack) {
//            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//            ft.replace(R.id.frame_content, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
//        } else {
//            ft.replace(R.id.frame_content, fragment).commitAllowingStateLoss();
//
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_approve_request:
                Intent intent = new Intent(getApplicationContext(), ApproveListActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_access_request:
                Intent iv_access_requestIntent = new Intent(getApplicationContext(), RequestForAccessActivity.class);
                startActivity(iv_access_requestIntent);
                break;
        }
    }
}
