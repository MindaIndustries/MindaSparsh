package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EHS_Home extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ehs_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Environment Health & Safety");
    }

    @OnClick(R.id.initiate_card)
    public void onClickInitiate() {
        if (Utility.isOnline(EHS_Home.this)) {
            Intent in = new Intent(EHS_Home.this, EHSInitiate.class);
            startActivity(in);
        } else {
            Toast.makeText(EHS_Home.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }

    }

    @OnClick(R.id.observation_card)
    public void onClickObservations() {
        if (Utility.isOnline(EHS_Home.this)) {

            Intent in = new Intent(EHS_Home.this, EHSObservations.class);
            startActivity(in);
        } else {
            Toast.makeText(EHS_Home.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

        }
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
