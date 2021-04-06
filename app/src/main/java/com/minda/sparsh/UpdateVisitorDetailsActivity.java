package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVisitorDetailsActivity extends AppCompatActivity {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    private RecyclerView recyclerView;
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_visitor_details);
        progress = new ProgressDialog(UpdateVisitorDetailsActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        im_back = (ImageView) findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }


    public void hitGetApprovalListApi(String key, String visitorId, String status) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = anInterface.UpdateVisitorStatus(key, visitorId, status);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
                    dismissProgress();
                    List<AddAbnormality_Model> approveLists = response.body();

                    try {
                        if (approveLists != null) {
                            if (approveLists.get(0).getColumn1() != null) {


                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        try {
            if (progress != null)
                progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissProgress() {
        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
