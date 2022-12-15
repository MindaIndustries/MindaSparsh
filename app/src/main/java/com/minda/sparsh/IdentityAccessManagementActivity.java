package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class IdentityAccessManagementActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView im_back,  iv_view_access_request;
    Button iv_approve_request,iv_access_request, view_status;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_access_management);
        toolbar =  findViewById(R.id.toolbar);
        title =  findViewById(R.id.title);
        view_status = findViewById(R.id.view_status);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.id_access_mgmt));

        im_back =  findViewById(R.id.im_back);
        iv_approve_request =  findViewById(R.id.iv_approve_request);
        iv_approve_request.setOnClickListener(this);
        iv_access_request =  findViewById(R.id.iv_access_request);
        iv_access_request.setOnClickListener(this);
        iv_view_access_request =  findViewById(R.id.iv_view_access_request);
        im_back.setOnClickListener(view -> finish());
        view_status.setOnClickListener(this);

//        intiUiSetup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


   /* private void intiUiSetup() {
//        replaceFragment(new IAMMenuFragment(), false, false);
    }
*/
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
            case R.id.view_status:
                Intent viewStatus = new Intent(getApplicationContext(), IAMViewStatusActivity.class);
                startActivity(viewStatus);
                break;
        }
    }

}
