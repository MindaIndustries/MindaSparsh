package com.minda.sparsh;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.minda.sparsh.fragment.CreateTicketFragment;
import com.minda.sparsh.fragment.MyTasks;
import com.minda.sparsh.fragment.MyTickets;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ITHelpDeskHome extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.it_help_desk);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("IT Help Desk");
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new CreateTicketFragment()).commitAllowingStateLoss();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new CreateTicketFragment()).commitAllowingStateLoss();
                } else if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MyTickets()).commitAllowingStateLoss();

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new MyTasks()).commitAllowingStateLoss();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
