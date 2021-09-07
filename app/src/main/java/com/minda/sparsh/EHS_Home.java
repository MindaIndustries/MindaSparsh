package com.minda.sparsh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.minda.sparsh.fragment.EHSInitiateFragment;
import com.minda.sparsh.fragment.EHSObservationsFragment;
import com.minda.sparsh.util.Utility;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EHS_Home extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ehs_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.environment_healthnSafety));

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new EHSInitiateFragment()).commitAllowingStateLoss();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new EHSInitiateFragment()).commitAllowingStateLoss();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new EHSObservationsFragment()).commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                case 2:
                case 3:
                    EHSInitiateFragment ehsInitiateFragment = new EHSInitiateFragment();
                    ehsInitiateFragment.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick(R.id.initiate_card)
    public void onClickInitiate() {
        if (Utility.isOnline(EHS_Home.this)) {

        } else {
            Toast.makeText(EHS_Home.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }

    }

    @OnClick(R.id.observation_card)
    public void onClickObservations() {
        if (Utility.isOnline(EHS_Home.this)) {

        } else {
            Toast.makeText(EHS_Home.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
