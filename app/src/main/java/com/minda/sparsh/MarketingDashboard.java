package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.minda.sparsh.cvp.CVPPlanCalendar;
import com.minda.sparsh.cvp.CVPViewCalendar;
import com.minda.sparsh.cvp.CustomerVisitReport;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketingDashboard extends AppCompatActivity {
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.card2)
    CardView card2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.card3)
    CardView card3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketing_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Customer Visit Portal");

        card1.setOnClickListener(view -> {
            Intent intent = new Intent(MarketingDashboard.this, CVPPlanCalendar.class);
            startActivity(intent);

        });
        card2.setOnClickListener(view -> {
            Intent intent = new Intent(MarketingDashboard.this, CVPViewCalendar.class);
            startActivity(intent);

        });
        card3.setOnClickListener(view -> {
            Intent intent = new Intent(MarketingDashboard.this, CustomerVisitReport.class);
            startActivity(intent);
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
