package com.minda.sparsh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.minda.sparsh.cvp.CVPPlanCalendar;
import com.minda.sparsh.cvp.CVPViewCalendar;
import com.minda.sparsh.cvp.CustomerVisitReport;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketingDashboard.this, CVPPlanCalendar.class);
                startActivity(intent);

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketingDashboard.this, CVPViewCalendar.class);
                startActivity(intent);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketingDashboard.this, CustomerVisitReport.class);
                startActivity(intent);
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


/*
    public void gettest() {
        Interface loginInterface = RetrofitClient.getClient1().create(Interface.class);
        Call<String> loginResponse = loginInterface.getemp("4667");
        loginResponse.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {

                if (response.body() != null) {
                    Log.d("response", String.valueOf(response));

                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                 Toast.makeText(MarketingDashboard.this, "Something Wrong", Toast.LENGTH_LONG).show();

            }

        });

}
*/


}
