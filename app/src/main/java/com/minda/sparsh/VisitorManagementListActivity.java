package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.ViewAppointmentAdapter;
import com.minda.sparsh.model.ViewAppointmentModel;
import com.minda.sparsh.util.Constant;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorManagementListActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    RecyclerView recyclerView;
    Button btn_create;
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_management_list);

        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        progress = new ProgressDialog(VisitorManagementListActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(this);
        im_back = (ImageView) findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        hitGetVisitorListApi(myPref.getString("Id", "Id"));
    }

    public void hitGetVisitorListApi(String EmpCode) {
        if (Utility.isOnline(VisitorManagementListActivity.this)) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ViewAppointmentModel>> response = anInterface.GetVisitorList(RetrofitClient2.CKEY, EmpCode);
            response.enqueue(new Callback<List<ViewAppointmentModel>>() {
                @Override
                public void onResponse(Call<List<ViewAppointmentModel>> call, Response<List<ViewAppointmentModel>> response) {
                    dismissProgress();



                     List<ViewAppointmentModel> visitorResponse = response.body();

                    if (visitorResponse != null && visitorResponse.size()>0) {
                        if (!visitorResponse.get(0).getFirstName().equalsIgnoreCase("")) {
                            ViewAppointmentAdapter mAdapter = new ViewAppointmentAdapter(response.body(), VisitorManagementListActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VisitorManagementListActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            Toast.makeText(VisitorManagementListActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ViewAppointmentModel>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(VisitorManagementListActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                Intent intent = new Intent(getApplicationContext(), VisitorManagementActivity.class);
                intent.putExtra(Constant.CALL_FROM_ACTIVITY, "Visitor_list");
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hitGetVisitorListApi(myPref.getString("Id", "Id"));

    }
}
