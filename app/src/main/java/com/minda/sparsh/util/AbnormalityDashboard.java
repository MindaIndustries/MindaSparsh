package com.minda.sparsh.util;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.AbnormalityAddressingActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.model.DDModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbnormalityDashboard extends AppCompatActivity implements View.OnClickListener {
    TextView tv_continue, tv_delayed, tv_closing, tv_total;
    ImageView im_back;
    private ProgressDialog progress = null;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Abnormality");

        im_back = (ImageView) findViewById(R.id.im_back);
        tv_continue = (TextView) findViewById(R.id.tv_continue);
        tv_delayed = (TextView) findViewById(R.id.tv_delayed);
        tv_closing = (TextView) findViewById(R.id.tv_closing);
        tv_total = (TextView) findViewById(R.id.tv_total);
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        tv_continue.setOnClickListener(this);
        im_back.setOnClickListener(this);
        if (getIntent().getExtras() != null) {
            hitBusinessApi(getIntent().getStringExtra("EDOMAIN"), getIntent().getStringExtra("EBUSINESS"), getIntent().getStringExtra("EPLANT"));
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_continue:
                finish();
                Intent intent = new Intent(AbnormalityDashboard.this, AbnormalityAddressingActivity.class);
                intent.putExtra("EDOMAIN", getIntent().getStringExtra("EDOMAIN"));
                intent.putExtra("EBUSINESS", getIntent().getStringExtra("EBUSINESS"));
                intent.putExtra("EPLANT", getIntent().getStringExtra("EPLANT"));
                intent.putExtra("ADD", false);
                startActivity(intent);
                break;

        }
    }

    public void hitBusinessApi(String Domain, String Business, String Plant) {
        if (Utility.isOnline(AbnormalityDashboard.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<DDModel>> response = promotingMyinterface.GetDData(RetrofitClient2.CKEY, Domain, Business, Plant);
            response.enqueue(new Callback<List<DDModel>>() {
                @Override
                public void onResponse(Call<List<DDModel>> call, Response<List<DDModel>> response) {
                    showProgress(false);
                    List<DDModel> DData = response.body();
                    if (DData != null && DData.size() != 0) {
                        tv_delayed.setText(DData.get(0).getCountAbnormalityPending().toString());
                        tv_closing.setText(DData.get(0).getCountAbnormalityClosed().toString());
                        tv_total.setText(DData.get(0).getCountAbnormality().toString());
                    } else {
                        Toast.makeText(AbnormalityDashboard.this, "Something Went wrong", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<List<DDModel>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityDashboard.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean b) {
        try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
