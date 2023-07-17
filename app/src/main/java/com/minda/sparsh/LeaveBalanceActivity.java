package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.Adapter.MyLeaveRequestAdapter;
import com.minda.sparsh.Adapter.MyRegularizationAdapter;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveRegularizationModel;
import com.minda.sparsh.model.LeaveRequestModel;
import com.minda.sparsh.services.AlmsServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class LeaveBalanceActivity extends AppCompatActivity implements MyLeaveRequestAdapter.OnItemClickListener, MyRegularizationAdapter.OnItemClickListener1 {

    Toolbar toolbar;
    TextView title, leave1, leave2, leave3, leave4, leave1_value, leave2_value, leave3_value, leave4_value, leave1_value_out, leave2_value_out, leave3_value_out, leave4_value_out;
    SharedPreferences myPref;
    String empCode, year;
    Calendar calendar;
    RecyclerView leave_req, reg_req;
    ArrayList<LeaveRequestModel> leaveRequests = new ArrayList<>();
    ArrayList<LeaveRegularizationModel> regularzationRequests = new ArrayList<>();
    MyLeaveRequestAdapter myLeaveRequestAdapter;
    MyRegularizationAdapter myRegularizationAdapter;
    AutoCompleteTextView leave_type;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> leaveTypes = new ArrayList<>();
    String reqType;
    View view;
    TextView no_record,no_record1, balance_value, availed1, applied1, balance_value1, availed2, applied_value, balance_value3, availed3, applied_value3, balance_value4, availed4, applied_value4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_balance);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Leave Balance");
        no_record = findViewById(R.id.no_record);
        no_record1 = findViewById(R.id.no_record1);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));

        balance_value = findViewById(R.id.balance_value);
        availed1 = findViewById(R.id.availed1);
        applied1 = findViewById(R.id.applied1);
        balance_value1 = findViewById(R.id.balance_value1);
        availed2 = findViewById(R.id.availed2);
        applied_value = findViewById(R.id.applied_value);
        balance_value3 = findViewById(R.id.balance_value3);
        availed3 = findViewById(R.id.availed3);
        applied_value3 = findViewById(R.id.applied_value3);
        balance_value4 = findViewById(R.id.balance_value4);
        availed4 = findViewById(R.id.availed4);
        applied_value4 = findViewById(R.id.applied_value4);


        leave1 = findViewById(R.id.leave1);
        leave2 = findViewById(R.id.leave2);
        leave3 = findViewById(R.id.leave3);
        leave4 = findViewById(R.id.leave4);
        leave1_value = findViewById(R.id.leave1_value);
        leave2_value = findViewById(R.id.leave2_value);
        leave3_value = findViewById(R.id.leave3_value);
        leave4_value = findViewById(R.id.leave4_value);
        leave1_value_out = findViewById(R.id.leave1_value_out);
        leave2_value_out = findViewById(R.id.leave2_value_out);
        leave3_value_out = findViewById(R.id.leave3_value_out);
        leave4_value_out = findViewById(R.id.leave4_value_out);
        leave_req = findViewById(R.id.leave_req);
        reg_req = findViewById(R.id.reg_req);
        leave_type = findViewById(R.id.leave_type);
        leaveTypes.add("Leaves");
        leaveTypes.add("Regularize");
        if (Utility.isOnline(LeaveBalanceActivity.this)) {
            getLeaveBalance(empCode, year, "LS");
        }
        arrayAdapter = new ArrayAdapter<>(LeaveBalanceActivity.this, android.R.layout.simple_spinner_item, leaveTypes);
        leave_type.setAdapter(arrayAdapter);
        leave_type.setSelection(0);
        leave_type.setText("Leaves");
        arrayAdapter.getFilter().filter(null);
        leave_type.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                reg_req.setVisibility(View.GONE);
                leave_req.setVisibility(View.VISIBLE);
                reqType = "L";
                getMyLeaveRequests();
            } else {
                reg_req.setVisibility(View.VISIBLE);
                leave_req.setVisibility(View.GONE);
                reqType = "R";
                getMyRegularizeRequests();
            }

        });
        myLeaveRequestAdapter = new MyLeaveRequestAdapter(LeaveBalanceActivity.this, leaveRequests);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(LeaveBalanceActivity.this, LinearLayoutManager.VERTICAL, false);
        leave_req.setLayoutManager(mLayoutManager);
        leave_req.setAdapter(myLeaveRequestAdapter);

        getMyLeaveRequests();
        myRegularizationAdapter = new MyRegularizationAdapter(LeaveBalanceActivity.this, regularzationRequests);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(LeaveBalanceActivity.this, LinearLayoutManager.VERTICAL, false);
        reg_req.setLayoutManager(mLayoutManager1);
        reg_req.setAdapter(myRegularizationAdapter);
        myLeaveRequestAdapter.setClickListener(this);
        myRegularizationAdapter.setClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getLeaveBalance(String empcode, String year, String leaveType) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.getLeaveBalance(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveBalanceModel> list = (List<LeaveBalanceModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    for (LeaveBalanceModel leaveBalanceModel : list) {
                        switch (leaveBalanceModel.getLeaveType()) {
                            case "Casual Leave":
                                leave1.setText(leaveBalanceModel.getLeaveType());
                                balance_value.setText("" + leaveBalanceModel.getAvailableBalance());
                                availed1.setText("" + leaveBalanceModel.getAvailed());
                                applied1.setText("" + leaveBalanceModel.getApplied());
                                break;
                            case "EL (Encashable)":
                                leave2.setText(leaveBalanceModel.getLeaveType());
                                balance_value1.setText("" + leaveBalanceModel.getAvailableBalance());
                                availed2.setText("" + leaveBalanceModel.getAvailed());
                                applied_value.setText("" + leaveBalanceModel.getApplied());
                                break;
                            case "EL (Non Encashable)":
                                leave3.setText(leaveBalanceModel.getLeaveType());
                                balance_value3.setText("" + leaveBalanceModel.getAvailableBalance());
                                availed3.setText("" + leaveBalanceModel.getAvailed());
                                applied_value3.setText("" + leaveBalanceModel.getApplied());
                                break;
                            case "Sick Leave":
                                leave4.setText(leaveBalanceModel.getLeaveType());
                                balance_value4.setText("" + leaveBalanceModel.getAvailableBalance());
                                availed4.setText("" + leaveBalanceModel.getAvailed());
                                applied_value4.setText("" + leaveBalanceModel.getApplied());
                                break;
                        }
                    }
                }

            }
        }, empcode, year, leaveType, "", "");
    }

    public void getMyLeaveRequests() {
        leaveRequests.clear();
        leave_req.getRecycledViewPool().clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getMyLeaveRequests(carotResponse -> {

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveRequestModel> list = (List<LeaveRequestModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if(list.get(0).getLVE_REQNO()!=null) {
                        leaveRequests.addAll(list);
                        no_record.setVisibility(View.GONE);

                        Collections.sort(leaveRequests, (o1, o2) -> {
                            String[] date1 = o1.getLVE_RECEIVED_DT().replace(".", "/").split("/");
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
                            calendar.set(Calendar.MONTH, (Integer.parseInt(date1[1]) - 1));
                            calendar.set(Calendar.YEAR, Integer.parseInt(date1[2]));
                            String[] date2 = o1.getLVE_RECEIVED_DT().replace(".", "/").split("/");
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
                            calendar1.set(Calendar.MONTH, (Integer.parseInt(date2[1]) - 1));
                            calendar1.set(Calendar.YEAR, Integer.parseInt(date2[2]));
                            if (calendar1.before(calendar)) {
                                return 1;
                            } else if (calendar1.after(calendar)) {
                                return -1;
                            } else {
                                return 0;
                            }
                        });
                    }
                    else{
                        no_record.setVisibility(View.VISIBLE);

                    }
                    myLeaveRequestAdapter.notifyDataSetChanged();
                }
            }

        }, empCode, "L");

    }


    public void getMyRegularizeRequests() {
        regularzationRequests.clear();
        reg_req.getRecycledViewPool().clear();
        AlmsServices almsServices = new AlmsServices();
        almsServices.getMyRegularizeRequests(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<LeaveRegularizationModel> list = (List<LeaveRegularizationModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if(list.get(0).getReqNo()!=null) {
                        no_record1.setVisibility(View.GONE);
                        regularzationRequests.addAll(list);
                        Collections.sort(regularzationRequests, (o1, o2) -> {
                            String[] date1 = o1.getCreatedOn().replace(".", "/").split("/");
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date1[0]));
                            calendar.set(Calendar.MONTH, (Integer.parseInt(date1[1]) - 1));
                            calendar.set(Calendar.YEAR, Integer.parseInt(date1[2]));

                            String[] date2 = o1.getCreatedOn().replace(".", "/").split("/");
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date2[0]));
                            calendar1.set(Calendar.MONTH, (Integer.parseInt(date2[1]) - 1));
                            calendar1.set(Calendar.YEAR, Integer.parseInt(date2[2]));


                            if (calendar1.before(calendar)) {
                                return 1;
                            } else if (calendar1.after(calendar)) {
                                return -1;
                            } else {
                                return 0;
                            }

                        });
                    }
                    else {
                        no_record1.setVisibility(View.VISIBLE);
                    }
                    myRegularizationAdapter.notifyDataSetChanged();

                }
            }

        }, empCode, "R");

    }

    @Override
    public void onClick(View view, int position) {
        this.view = view;
        if (leave_req.getVisibility() == View.VISIBLE) {
            showMsgUpdate("", "Are you sure you want to cancel this request?", leaveRequests.get(position).getLVE_REQNO(), "L", "");
        } else {
            showMsgUpdate("", "Are you sure you want to cancel this request?", regularzationRequests.get(position).getReqNo(), "R", regularzationRequests.get(position).getReqType());
        }

        //showPopUpMenu(position);
    }

    @Override
    public void onClick1(View view, int position) {
        this.view = view;
        if (leave_req.getVisibility() == View.VISIBLE) {
            showMsgUpdate("", "Are you sure you want to cancel this request?", leaveRequests.get(position).getLVE_REQNO(), "L", "");
        } else {
            showMsgUpdate("", "Are you sure you want to cancel this request?", regularzationRequests.get(position).getReqNo(), "R", regularzationRequests.get(position).getReqType());
        }
    }

    public void showPopUpMenu(int position) {
        PopupMenu popup = new PopupMenu(LeaveBalanceActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.leaves_menu, popup.getMenu());
        MenuItem menuItem = popup.getMenu().findItem(R.id.cancel);
        if (leave_req.getVisibility() == View.VISIBLE) {
            if (leaveRequests.get(position).getLVE_STATUS().equalsIgnoreCase("Completed")) {
                menuItem.setVisible(false);
            } else {
                menuItem.setVisible(true);
            }
        } else {
            if (regularzationRequests.get(position).getActiveDesc().equalsIgnoreCase("Completed")) {
                menuItem.setVisible(false);
            } else {
                menuItem.setVisible(true);
            }

        }

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.view:
                    Intent in = new Intent(LeaveBalanceActivity.this, LeaveViewActivity.class);

                    if (leave_req.getVisibility() == View.VISIBLE) {
                        in.putExtra("leave_model", (Parcelable) leaveRequests.get(position));
                    } else {
                        in.putExtra("regular_model", (Parcelable) regularzationRequests.get(position));

                    }
                    startActivity(in);
                    break;
                case R.id.cancel:
                    if (leave_req.getVisibility() == View.VISIBLE) {
                        showMsgUpdate("", "Are you sure you want to cancel this request?", leaveRequests.get(position).getLVE_REQNO(), "", "");
                    } else {
                        showMsgUpdate("", "Are you sure you want to cancel this request?", regularzationRequests.get(position).getReqNo(), "", "");
                    }
                    break;
            }


            return true;
        });
        popup.show();
    }

    public void cancelRequest(String reqno, String action, String req) {
        AlmsServices almsServices = new AlmsServices();
        almsServices.cancelLeave(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<ApplyLeaveResponse> list = (List<ApplyLeaveResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    if (list.get(0).getLeaveRequestNo() != null && list.get(0).getLeaveRequestNo().length() > 0) {
                        showMsg("Your leave request has been cancelled.", "");
                    }
                }
            }
        }, empCode, reqno, action, req);

    }

    public void showMsgUpdate(String title, String message, String reqno, String action, String req) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("YES", (arg0, arg1) -> {
            if (Utility.isOnline(LeaveBalanceActivity.this)) {
                cancelRequest(reqno, action, req);
            }
            arg0.dismiss();
        });
        alertDialogBuilder.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
