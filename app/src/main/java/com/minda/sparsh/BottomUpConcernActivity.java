package com.minda.sparsh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.minda.sparsh.fragment.AssignConcernFragment;
import com.minda.sparsh.fragment.NewConcernFragment;
import com.minda.sparsh.fragment.ViewConcernFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomUpConcernActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_up_concern);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Bottom Up Concern");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new NewConcernFragment()).commitAllowingStateLoss();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new NewConcernFragment()).commitAllowingStateLoss();
                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ViewConcernFragment()).commitAllowingStateLoss();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new AssignConcernFragment()).commitAllowingStateLoss();
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
                    NewConcernFragment ehsInitiateFragment = new NewConcernFragment();
                    ehsInitiateFragment.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

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
}
