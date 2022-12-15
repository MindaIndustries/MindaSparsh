package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.minda.sparsh.Adapter.ApproveListAdapter1;
import com.minda.sparsh.model.ApproveList;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IAMViewStatusActivity extends AppCompatActivity implements ApproveListAdapter1.OnItemClickListener {
    Toolbar toolbar;
    TextView title;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApproveListAdapter1 mAdapter;
    List<ApproveList> approveLists = new ArrayList<>();
    private ProgressDialog progress = null;
    ImageView im_back;
    private SharedPreferences myPref = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iam_view_status_list);
        toolbar =  findViewById(R.id.toolbar);
        title =  findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("View Status");
        im_back =  findViewById(R.id.im_back);
        im_back.setOnClickListener(view -> finish());
        recyclerView =  findViewById(R.id.recycler_view);
        mAdapter = new ApproveListAdapter1(approveLists, IAMViewStatusActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IAMViewStatusActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progress = new ProgressDialog(IAMViewStatusActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        mSwipeRefreshLayout =  findViewById(R.id.swipeToRefresh);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            hitgetViewStatusList(RetrofitClient2.CKEY, myPref.getString("Id", "Id"));
            mSwipeRefreshLayout.setRefreshing(false);
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }}

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

    public void hitgetViewStatusList(String key, String EmpCode) {
        approveLists.clear();
        recyclerView.getRecycledViewPool().clear();
        mAdapter.notifyDataSetChanged();


        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ApproveList>> response = anInterface.IAMViewSTATUS(key, EmpCode);
            response.enqueue(new Callback<List<ApproveList>>() {
                @Override
                public void onResponse(@NotNull Call<List<ApproveList>> call, @NotNull Response<List<ApproveList>> response) {
                    dismissProgress();
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        List<ApproveList> approveLists1 = response.body();

                        try {
                            if (approveLists1 != null && approveLists1.size() > 0) {
                               // if (approveLists1.get(0).getApprovalId() != null) {
                                    approveLists.addAll(approveLists1);
                               // } else {
                                 //   Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                //}
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setClickListener(IAMViewStatusActivity.this);

                }

                @Override
                public void onFailure(@NotNull Call<List<ApproveList>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        hitgetViewStatusList(RetrofitClient2.CKEY, myPref.getString("Id", "Id"));
        super.onResume();
    }

    @Override
    public void onClick(View view, int position) {
        if(((Button)(view)).getText().toString().equalsIgnoreCase("Edit")){
            System.out.println("Edit");
            (myPref.edit().putString("reqId", String.valueOf(approveLists.get(position).getRequestId()))).apply();
            myPref.edit().putString("access_req_no",approveLists.get(position).getAccessRequestNo()).apply();
            myPref.edit().putString("category_Id", String.valueOf(approveLists.get(position).getCategoryId())).apply();
            myPref.edit().putString("accesstype_Id", String.valueOf(approveLists.get(position).getAccessTypeId())).apply();
            myPref.edit().commit();
            IAMEditDialog dialog = new IAMEditDialog();
            dialog.show(getSupportFragmentManager(), "IAMEditDialog");

        }
        else{
            System.out.println("Delete");

            showMsg(position);

        }
    }

    public void deleteRequest(String empcode, String reqid,String arNo, String Ckey){
        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<String> response = anInterface.IAMViewDelete(empcode,reqid,arNo,Ckey);
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    Toast.makeText(IAMViewStatusActivity.this,getResources().getString(R.string.deleted_iam_req),Toast.LENGTH_LONG).show();
                    hitgetViewStatusList(RetrofitClient2.CKEY,myPref.getString("Id", "Id"));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Failed");
            }
        });

    }
    public void showMsg(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to Delete this request?");

        alertDialogBuilder.setPositiveButton("Yes", (arg0, arg1) -> {
            deleteRequest( myPref.getString("Id", "Id"), String.valueOf(approveLists.get(position).getRequestId()),approveLists.get(position).getAccessRequestNo(), RetrofitClient2.CKEY);
        });

        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
