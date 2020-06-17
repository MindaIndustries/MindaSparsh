package com.minda.sparsh;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.minda.sparsh.fragment.IAMMenuFragment;

public class IdentityAccessManagementActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView im_back, iv_approve_request, iv_view_access_request, iv_access_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_access_management);
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
