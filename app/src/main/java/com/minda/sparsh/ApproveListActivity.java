package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.ApproveListAdapter;
import com.minda.sparsh.model.ApproveList;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveListActivity extends AppCompatActivity {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    private RecyclerView recyclerView;
    ImageView im_back;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_list);

        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        progress = new ProgressDialog(ApproveListActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hitGetApprovalListApi(RetrofitClient2.CKEY, myPref.getString("Id", "Id"));

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        im_back = (ImageView) findViewById(R.id.im_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        hitGetApprovalListApi(RetrofitClient2.CKEY, myPref.getString("Id", "Id"));

    }


    public void hitGetApprovalListApi(String key, String EmpCode) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ApproveList>> response = anInterface.GetApprovalList(key, EmpCode);
            response.enqueue(new Callback<List<ApproveList>>() {
                @Override
                public void onResponse(Call<List<ApproveList>> call, Response<List<ApproveList>> response) {
                    dismissProgress();
                    List<ApproveList> approveLists = response.body();

                    try {
                        if (approveLists != null) {
                            if (approveLists.get(0).getApprovalId() != null) {
                                ApproveListAdapter mAdapter = new ApproveListAdapter(response.body(), ApproveListActivity.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ApproveListActivity.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<ApproveList>> call, Throwable t) {
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

    @Override
    protected void onResume() {
        hitGetApprovalListApi(RetrofitClient2.CKEY, myPref.getString("Id", "Id"));

        super.onResume();
    }
}
