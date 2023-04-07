package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.LeaveApprovalAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.ApprovalRequestModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class LeaveApprovals extends AppCompatActivity implements LeaveApprovalAdapter.OnItemClickListener {
    Toolbar toolbar;
    TextView title, no_record;
    SharedPreferences myPref;
    String empCode, EmpName, action;
    ArrayList<ApprovalRequestModel> approvalList = new ArrayList<>();
    RecyclerView approval_req;
    LeaveApprovalAdapter leaveApprovalAdapter;
    Button approve, reject;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_approvals);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        no_record = findViewById(R.id.no_record);
        approval_req = findViewById(R.id.approval_req);
        approve = findViewById(R.id.approve);
        reject = findViewById(R.id.reject);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Approvals");
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        EmpName = myPref.getString("username", "");
        leaveApprovalAdapter = new LeaveApprovalAdapter(LeaveApprovals.this, approvalList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(LeaveApprovals.this, LinearLayoutManager.VERTICAL, false);
        approval_req.setLayoutManager(mLayoutManager);
        approval_req.setAdapter(leaveApprovalAdapter);
        if(Utility.isOnline(LeaveApprovals.this)){
            getApprovalRequests(empCode);
        }
        else{
            Toast.makeText(LeaveApprovals.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
        }
        leaveApprovalAdapter.setClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getApprovalRequests(String empcode) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getApprovalRequests(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<ApprovalRequestModel> list = (List<ApprovalRequestModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        if(list.get(0).getReqno()!=null) {
                            approvalList.addAll(list);
                            no_record.setVisibility(View.GONE);
                        }
                        else {
                            no_record.setVisibility(View.VISIBLE);
                        }
                    } else {
                        no_record.setVisibility(View.VISIBLE);
                    }
                    leaveApprovalAdapter.notifyDataSetChanged();

                }
            }
        }, empcode);
    }

    public void applyRejectLeaves(String Empcode, String RFormNo, String Action, String LOC, String Reqtype, String strhrs, String ReportyEmpName) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.applyRejectLeaves(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<ApplyLeaveResponse> list = (List<ApplyLeaveResponse>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        if (list.get(0).getMsg().equals("Success")) {
                            if (Action.equals("A")) {
                                showMsg("Leave request " + list.get(0).getLeaveRequestNo() + " has been Approved successfully.", "");
                            } else {
                                showMsg("Leave request " + list.get(0).getLeaveRequestNo() + " has been Rejected successfully.", "");

                            }
                        }
                    }
                }
            }
        }, Empcode, RFormNo, Action, LOC, Reqtype, strhrs, ReportyEmpName);
    }

    public void showMsg(String msg, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton("Ok", (arg0, arg1) -> {
            arg0.dismiss();
            finish();
            startActivity(getIntent());
        });

        //alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view, int position) {

        if (view.getId() == R.id.approve) {
            action = "A";
        } else {
            action = "R";
        }
        if (Utility.isOnline(LeaveApprovals.this)) {
            applyRejectLeaves(empCode, approvalList.get(position).getReqno(), action, RetrofitClient2.LOC, approvalList.get(position).getReqType(), String.valueOf(approvalList.get(position).getNoOfDays()), EmpName);
        }
        else{
            Toast.makeText(LeaveApprovals.this, "Oops! Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }
}
