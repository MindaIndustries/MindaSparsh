package com.minda.sparsh;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.CCListAdapter;
import com.minda.sparsh.Adapter.TicketHistoryAdapter;
import com.minda.sparsh.customview.NoDefaultSpinner;
import com.minda.sparsh.model.AssetLocResponse;
import com.minda.sparsh.model.BindGroupResponse;
import com.minda.sparsh.model.GroupAssigneeResponse;
import com.minda.sparsh.model.MyTicketsResponse;
import com.minda.sparsh.model.TicketHistoryResponse;
import com.minda.sparsh.services.ITHelpDeskServices;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTaskDetail extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.asset_loc_spinner)
    NoDefaultSpinner assetLocSPinner;
    @BindView(R.id.ticket_type_spinner)
    NoDefaultSpinner ticketTypeSpinner;
    @BindView(R.id.ticket_group_spinner)
    NoDefaultSpinner ticketgroupSpinner;
    @BindView(R.id.assignee_spinner)
    NoDefaultSpinner asigneeSpinner;
    @BindView(R.id.reportedby_spinner)
    NoDefaultSpinner reportedBySpinner;
    @BindView(R.id.priority_spinner)
    NoDefaultSpinner prioritySpinner;
    @BindView(R.id.reported_date_value)
    TextView reportedDate;
    @BindView(R.id.description_et)
    EditText descriptionEditText;
    @BindView(R.id.remarks_et)
    EditText remarks_et;
    @BindView(R.id.attachment)
    ImageView attachment;
    @BindView(R.id.attachtext)
    TextView attachtext;
    @BindView(R.id.doc_view)
    ImageView docView;
    @BindView(R.id.cc_et)
    AutoCompleteTextView ccEt;
    @BindView(R.id.cclist)
    RecyclerView cclist;
    @BindView(R.id.resolver_file_list)
    RecyclerView resolver_file_list;
    ArrayList<String> recyclerview_list = new ArrayList<>();
    ArrayList<String> resolver_files_list = new ArrayList<>();
    @BindView(R.id.reset)
    Button cancel;
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.ticket_type_sub_cat)
    TextView ticket_type_sub_cat;
    @BindView(R.id.ticket_type_spinner_sub_cat)
    NoDefaultSpinner ticket_type_spinner_sub_cat;
    @BindView(R.id.ll9)
    LinearLayout ll9;
    @BindView(R.id.ticket_type_sub_cat2)
    TextView ticket_type_sub_cat2;
    @BindView(R.id.ticket_type_spinner_sub_cat2)
    NoDefaultSpinner ticket_type_spinner_sub_cat2;
    @BindView(R.id.ll10)
    LinearLayout ll10;
    @BindView(R.id.ticket_type_sub_cat3)
    TextView ticket_type_sub_cat3;
    @BindView(R.id.ticket_type_spinner_sub_cat3)
    NoDefaultSpinner ticket_type_spinner_sub_cat3;
    @BindView(R.id.ll11)
    LinearLayout ll11;
    @BindView(R.id.ticket_history)
    TextView ticketHistory;


    @BindView(R.id.isclosed)
    CheckBox isClosed;
    @BindView(R.id.ishold)
    CheckBox isHold;
    @BindView(R.id.resumed)
    CheckBox resumed;
    @BindView(R.id.header_history)
    RelativeLayout headerHistory;
    private static final int CAPTURE_FROM_CAMERA = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;

    private File mDestinationFile;
    byte[] bytes;
    Bitmap bmp;
    String attachmentName = "", attachmentType = "";
    private String mUserChoosenTask = "";
    MyTicketsResponse myTicket;


    SharedPreferences myPref;
    String unitcode, depucode, username, empcode;

    ArrayList<String> assetloc = new ArrayList<>();
    ArrayList<AssetLocResponse> assetList = new ArrayList<>();
    ArrayAdapter<String> assetAdapter, ticketTypeAdapter, ticketGroupAdapter, groupAssigneeAdapter, priorityAdapter, reportedbyAdapter, subcatAdapter, subcat2Adapter, subcat3Adapter;
    ArrayList<String> tickettypes = new ArrayList<>();
    ArrayList<AssetLocResponse> ticketTypeList = new ArrayList<>();
    ArrayList<String> ticketGroups = new ArrayList<>();
    ArrayList<BindGroupResponse> ticketGroupList = new ArrayList<>();
    ArrayList<String> groupAssignee = new ArrayList<>();
    ArrayList<GroupAssigneeResponse> groupAssigneeList = new ArrayList<>();
    ArrayList<String> priorities = new ArrayList<>();
    ArrayList<AssetLocResponse> priorityList = new ArrayList<>();

    ArrayList<String> reportedbylist = new ArrayList<>();
    ArrayList<AssetLocResponse> reportedByListResponse = new ArrayList<>();
    String location, tickettypeid, tickettypegroupid, assigneegroup, asigneGroupCode, DefaultAssigne, reportedby, priority;
    CCListAdapter ccListAdapter, resolverListAdapter;


    ArrayList<AssetLocResponse> subcatList = new ArrayList<>();
    ArrayList<AssetLocResponse> subcat2List = new ArrayList<>();
    ArrayList<AssetLocResponse> subcat3List = new ArrayList<>();
    ArrayList<String> subcats = new ArrayList<>();
    ArrayList<String> subcats2 = new ArrayList<>();
    ArrayList<String> subcats3 = new ArrayList<>();
    ArrayList<String> attachmentNames = new ArrayList<>();
    ArrayList<String> attachmentFiles = new ArrayList<>();
    String subcat = "0", subcat2 = "0", subcat3 = "0";

    String statusStr = "Update";


    @BindView(R.id.tkt_history)
    RecyclerView tktHistoryRv;
    TicketHistoryAdapter ticketsAdapter;
    ArrayList<TicketHistoryResponse> tktHistoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytask_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Ticket History");
        initUI();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initUI() {
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        depucode = myPref.getString("Depu_UnitCode", "");
        username = myPref.getString("username", "");
        empcode = myPref.getString("Id", "");
        // reportedby = empcode;


        if (getIntent() != null && getIntent().getSerializableExtra("ticketno") != null) {
            myTicket = (MyTicketsResponse) getIntent().getSerializableExtra("ticketno");
            if (myTicket != null && myTicket.getDescription() != null) {
                descriptionEditText.setText(myTicket.getDescription());
            }
            if (myTicket != null && myTicket.getStatus().equals("Open")) {
                isClosed.setVisibility(View.VISIBLE);
                isHold.setVisibility(View.VISIBLE);
                resumed.setVisibility(View.INVISIBLE);
            } else if (myTicket != null && myTicket.getStatus().equals("Hold")) {
                resumed.setVisibility(View.VISIBLE);
                isClosed.setVisibility(View.GONE);
                isHold.setVisibility(View.GONE);
            } else {
                resumed.setVisibility(View.INVISIBLE);
                isHold.setVisibility(View.GONE);
                isClosed.setVisibility(View.GONE);
            }

        }

        ticketsAdapter = new TicketHistoryAdapter(MyTaskDetail.this, tktHistoryList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyTaskDetail.this, LinearLayoutManager.VERTICAL, false);
        tktHistoryRv.setLayoutManager(mLayoutManager);
        tktHistoryRv.setAdapter(ticketsAdapter);
        if (myTicket != null && myTicket.getTicketNo() != null) {
            ticketHistory.setText("Ticket: " + myTicket.getTicketNo());
            getTicketHistory(myTicket.getTicketNo());
        }
        if (myTicket != null && myTicket.getTicketGroup() != null) {
            tickettypegroupid = myTicket.getTicketGroup();
        }
        if (myTicket != null && myTicket.getAssigne() != null) {
            assigneegroup = myTicket.getAssigne();
        }


        resumed.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                statusStr = "Resume";
            }
        });


        isClosed.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                statusStr = "Close";
                isHold.setVisibility(View.GONE);
            } else {
                isHold.setVisibility(View.VISIBLE);
            }
        });
        isHold.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isClosed.setVisibility(View.GONE);
                statusStr = "Hold";
            } else {
                isClosed.setVisibility(View.VISIBLE);
            }
        });
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthStr, dayStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        reportedDate.setText("" + dayStr + "/" + monthStr + "/" + calendar.get(Calendar.YEAR));
        if (myTicket != null && myTicket.getReporteddate() != null) {
            reportedDate.setText(myTicket.getReporteddate().split(" ")[0]);
        }
        initAssetLocSpinner();
        initTicketTypeSpinner();
        initTicketGroupSpinner();
        initGroupAssigneeSpinner();
        getAssetLoc();
        getTicketType();
        initPrioritySpinner();
        initReportedBySpinner();
        getPriority();
        getReportedBy();
        ccListAdapter = new CCListAdapter(MyTaskDetail.this, recyclerview_list);
        final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(MyTaskDetail.this, LinearLayoutManager.VERTICAL, false);
        cclist.setLayoutManager(mLayoutManager1);
        cclist.setAdapter(ccListAdapter);
        resolverListAdapter = new CCListAdapter(MyTaskDetail.this, resolver_files_list);
        final LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(MyTaskDetail.this, LinearLayoutManager.VERTICAL, false);
        resolver_file_list.setLayoutManager(mLayoutManager2);
        resolver_file_list.setAdapter(resolverListAdapter);

        if (myTicket != null && myTicket.getFiles() != null) {
            recyclerview_list.clear();
            String[] files = myTicket.getFiles().split(",");
            Collections.addAll(recyclerview_list, files);
            ccListAdapter.notifyDataSetChanged();


        }
        if (myTicket != null && myTicket.getFiles2() != null) {
            resolver_files_list.clear();
            String[] files2 = myTicket.getFiles2().split(",");
            for (String s : files2) {
                resolver_files_list.add(s);
            }
            resolverListAdapter.notifyDataSetChanged();
        }


        reportedBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    reportedby = reportedByListResponse.get(i - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        assetLocSPinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    location = assetList.get(i - 1).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       /* ccEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Utility.isOnline(MyTaskDetail.this)) {
                    getAutoName(ccEt.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
        ticket_type_spinner_sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0 && subcatList.size() > 0) {
                    subcat = subcatList.get(i - 1).getId();

                    if (subcatList.get(i - 1).getNextlevel() != null && subcatList.get(i - 1).getNextlevel().equals("C")) {
                        initSubcat2Spinner();
                        getSubCat2(subcatList.get(i - 1).getId(), "", tickettypeid, unitcode);
                        initSubCatSpinner3();
                        ticket_type_sub_cat2.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.VISIBLE);
                        ll10.setVisibility(View.VISIBLE);
                    } else {
                        ticket_type_sub_cat2.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.GONE);
                        ll10.setVisibility(View.GONE);
                        ticket_type_sub_cat3.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.GONE);
                        ll11.setVisibility(View.GONE);

                        initTicketGroupSpinner();
                        getTicketGroup(ticketTypeList.get(i - 1).getId(), unitcode);
                        groupAssignee.clear();
                        groupAssignee.add("Select");
                        initGroupAssigneeSpinner();

                    }
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_type_spinner_sub_cat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0 && subcat2List.size() > 0) {
                    subcat2 = subcat2List.get(i - 1).getId();

                    if (subcat2List.get(i - 1).getNextlevel() != null && subcat2List.get(i - 1).getNextlevel().equals("C")) {
                        initSubCatSpinner3();
                        getSubCat3(subcat2List.get(i - 1).getId(), "", tickettypeid, unitcode);
                        ticket_type_sub_cat3.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.VISIBLE);
                        ll11.setVisibility(View.VISIBLE);
                    } else {
                        ticket_type_sub_cat3.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.GONE);
                        ll11.setVisibility(View.GONE);
                        ticket_type_sub_cat2.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.GONE);
                        ll10.setVisibility(View.GONE);

                        initTicketGroupSpinner();
                        getTicketGroup(ticketTypeList.get(i - 1).getId(), unitcode);
                        groupAssignee.clear();
                        groupAssignee.add("Select");
                        initGroupAssigneeSpinner();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_type_spinner_sub_cat3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0 && subcat3List.size() > 0) {
                    subcat3 = subcat3List.get(i - 1).getId();
                    initTicketGroupSpinner();
                    getTicketGroup(ticketTypeList.get(i - 1).getId(), unitcode);
                    groupAssignee.clear();
                    groupAssignee.add("Select");
                    initGroupAssigneeSpinner();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ticketTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0 && ticketTypeList.size() > 0) {
                    if (ticketTypeList.get(i - 1).getNextLevel() != null && ticketTypeList.get(i - 1).getNextLevel().equals("G")) {
                        ticket_type_sub_cat.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat.setVisibility(View.GONE);
                        ll9.setVisibility(View.GONE);
                        ticket_type_sub_cat2.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.GONE);
                        ll10.setVisibility(View.GONE);
                        ticket_type_sub_cat3.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.GONE);
                        ll11.setVisibility(View.GONE);
                        initTicketGroupSpinner();
                        getTicketGroup(ticketTypeList.get(i - 1).getId(), unitcode);
                        groupAssignee.clear();
                        groupAssignee.add("Select");
                        initGroupAssigneeSpinner();
                        tickettypeid = ticketTypeList.get(i - 1).getId();
                    } else {
                        ticket_type_sub_cat.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat.setVisibility(View.VISIBLE);
                        ll9.setVisibility(View.VISIBLE);
                        initTicketGroupSpinner();
                        initGroupAssigneeSpinner();
                        initSubCatSpinner();
                        tickettypeid = ticketTypeList.get(i - 1).getId();
                        getSubCat("", ticketTypeList.get(i - 1).getId(), unitcode);
                        initSubcat2Spinner();
                        initSubCatSpinner3();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticketgroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0 && ticketGroupList.size() > 0) {
                    groupAssignee.clear();
                    groupAssignee.add("Select");
                    initGroupAssigneeSpinner();
                    tickettypegroupid = ticketGroupList.get(i - 1).getId();
                    getGroupAssignee(ticketGroupList.get(i - 1).getId(), unitcode);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        asigneeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    assigneegroup = groupAssigneeList.get(i - 1).getName();
                    asigneGroupCode = groupAssigneeList.get(i - 1).getData();
                    DefaultAssigne = groupAssigneeList.get(i - 1).getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    priority = priorityList.get(i - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.reset)
    public void onClickReset() {
        finish();
    }

    @OnClick(R.id.submit)
    public void onClickSubmit() {

        if (location.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select Asset Location ", Toast.LENGTH_LONG).show();
            return;
        }

        if (tickettypeid.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select Ticket Type ", Toast.LENGTH_LONG).show();
            return;
        }
        if (tickettypegroupid.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select Ticket Group ", Toast.LENGTH_LONG).show();
            return;
        }
        if (assigneegroup.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select Assignee ", Toast.LENGTH_LONG).show();
            return;
        }
       /* if(DefaultAssigne.length()==0){
            Toast.makeText(getActivity(), "Select Ticket Type ", Toast.LENGTH_LONG).show();
            return;
        }
       */
        if (reportedby.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select ReportedBy ", Toast.LENGTH_LONG).show();
            return;
        }
        if (priority.length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Select Priority ", Toast.LENGTH_LONG).show();
            return;
        }
        if (descriptionEditText.getText().toString().length() == 0) {
            Toast.makeText(MyTaskDetail.this, "Enter Description ", Toast.LENGTH_LONG).show();
            return;
        }

        if (Utility.isOnline(MyTaskDetail.this)) {
            if (remarks_et.getText().toString().length() > 0) {
                updateTicket();
            } else {
                Toast.makeText(MyTaskDetail.this, "Enter Remarks ", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(MyTaskDetail.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.attachment)
    public void onClickAttachment() {
        selectFile();
    }

    @OnClick(R.id.attachtext)
    public void onClickAttachText() {
        selectFile();
    }


    public void initPrioritySpinner() {
        priorities.add("Select");
        priorityAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, priorities) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        priorityAdapter.setDropDownViewResource(R.layout.spinner_row);
        prioritySpinner.setAdapter(priorityAdapter);
        priorityAdapter.notifyDataSetChanged();
    }

    public void initReportedBySpinner() {
        reportedbylist.add("Select");
        // reportedbylist.add(username+" ("+empcode+")");
        reportedbyAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, reportedbylist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        reportedbyAdapter.setDropDownViewResource(R.layout.spinner_row);
        reportedBySpinner.setAdapter(reportedbyAdapter);
        reportedbyAdapter.notifyDataSetChanged();
        if (reportedbylist.size() > 0)
            reportedBySpinner.setSelection(1);
    }


    public void initAssetLocSpinner() {
        assetloc.clear();
        assetloc.add("Select");
        assetAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, assetloc) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        assetAdapter.setDropDownViewResource(R.layout.spinner_row);
        assetLocSPinner.setAdapter(assetAdapter);
        assetAdapter.notifyDataSetChanged();
    }

    public void initTicketTypeSpinner() {
        tickettypes.clear();
        tickettypes.add("Select");
        ticketTypeAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, tickettypes) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        ticketTypeAdapter.setDropDownViewResource(R.layout.spinner_row);
        ticketTypeSpinner.setAdapter(ticketTypeAdapter);
        ticketTypeAdapter.notifyDataSetChanged();
    }

    public void initTicketGroupSpinner() {
        ticketGroups.clear();
        ticketGroups.add("Select");
        ticketGroupAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, ticketGroups) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        ticketGroupAdapter.setDropDownViewResource(R.layout.spinner_row);
        ticketgroupSpinner.setAdapter(ticketGroupAdapter);
        ticketGroupAdapter.notifyDataSetChanged();
    }

    public void initGroupAssigneeSpinner() {
        groupAssigneeAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, groupAssignee) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        groupAssigneeAdapter.setDropDownViewResource(R.layout.spinner_row);
        asigneeSpinner.setAdapter(groupAssigneeAdapter);
        groupAssigneeAdapter.notifyDataSetChanged();
    }

    public void initSubcat2Spinner() {
        subcats2.clear();
        subcats2.add("Select");
        subcat2Adapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, subcats2) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        subcat2Adapter.setDropDownViewResource(R.layout.spinner_row);
        ticket_type_spinner_sub_cat2.setAdapter(subcat2Adapter);
        subcat2Adapter.notifyDataSetChanged();
    }

    public void initSubCatSpinner3() {
        subcats3.clear();
        subcats3.add("Select");
        subcat3Adapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, subcats3) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        subcat3Adapter.setDropDownViewResource(R.layout.spinner_row);
        ticket_type_spinner_sub_cat3.setAdapter(subcat3Adapter);
        subcat3Adapter.notifyDataSetChanged();
    }

    public void initSubCatSpinner() {
        subcats.clear();
        subcats.add("Select");
        subcatAdapter = new ArrayAdapter<String>(MyTaskDetail.this, R.layout.spinner_row, subcats) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView itemTv = (TextView) view;
                itemTv.setSingleLine(true);
                itemTv.setPadding(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    itemTv.setBackground(getResources().getDrawable(R.drawable.darkline));
                }
                if (position == 0) {
                    itemTv.setTextColor(Color.parseColor("#999999"));
                } else {
                    itemTv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        subcatAdapter.setDropDownViewResource(R.layout.spinner_row);
        ticket_type_spinner_sub_cat.setAdapter(subcatAdapter);
        subcatAdapter.notifyDataSetChanged();
    }


    public void getAssetLoc() {
        assetList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getAssetLoc(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    assetList.addAll(list);

                    for (AssetLocResponse assetLocResponse : assetList) {
                        // if (assetLocResponse.getId().equals(unitcode)) {
                        assetloc.add(assetLocResponse.getName());
                        //}
                    }
                    if (assetloc.size() > 0) {
                        assetLocSPinner.setSelection(1);
                    }

                    if (myTicket != null && myTicket.getUnitCode() != null) {
                        int i = assetloc.indexOf(myTicket.getUnitCode());
                        assetLocSPinner.setSelection(i);
                    }
                    assetAdapter.notifyDataSetChanged();
                }
            }

        }, empcode);

    }

    public void getTicketType() {
        ticketTypeList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketType(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    ticketTypeList.addAll(list);
                    for (AssetLocResponse assetLocResponse : ticketTypeList) {
                        tickettypes.add(assetLocResponse.getName());
                    }
                }
            }

            if (myTicket != null && myTicket.getTicketType() != null) {
                int i = tickettypes.indexOf(myTicket.getTicketType());
                ticketTypeSpinner.setSelection(i);
            }
            ticketTypeAdapter.notifyDataSetChanged();
        });
    }

    public void getTicketGroup(String ticketType, String unitcode) {
        ticketGroups.clear();
        ticketGroups.add("Select");
        ticketGroupList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketGroup(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<BindGroupResponse> list = (List<BindGroupResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    ticketGroupList.addAll(list);
                    for (BindGroupResponse bindGroupResponse : ticketGroupList) {
                        ticketGroups.add(bindGroupResponse.getName());
                    }
                }

                if (myTicket != null && myTicket.getTicketGroup() != null) {
                    BindGroupResponse bindGroupResponse = new BindGroupResponse();
                    bindGroupResponse.setId(myTicket.getTicketGroup());
                    try {
                        int i = ticketGroupList.indexOf(bindGroupResponse);
                        ticketgroupSpinner.setSelection(i + 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                ticketGroupAdapter.notifyDataSetChanged();
            }

        }, ticketType, unitcode, subcat, subcat2, subcat3);
    }

    public void getGroupAssignee(String ticketType, String unitcode) {
        groupAssignee.clear();
        groupAssignee.add("Select");
        groupAssigneeList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getGroupAssignee(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<GroupAssigneeResponse> list = (List<GroupAssigneeResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    groupAssigneeList.addAll(list);
                    for (GroupAssigneeResponse groupAssigneeResponse : groupAssigneeList) {
                        groupAssignee.add(groupAssigneeResponse.getName());
                    }
                }

                if (myTicket != null && myTicket.getAssigne() != null) {
                    int i = groupAssignee.indexOf(myTicket.getAssigne());
                    asigneeSpinner.setSelection(i);
                }
                groupAssigneeAdapter.notifyDataSetChanged();
            }

        }, ticketType, unitcode);

    }


    public void getAutoName(String prefix) {

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getAutoName(carotResponse -> {

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<String> names = (List<String>) carotResponse.getData();
                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (MyTaskDetail.this, android.R.layout.select_dialog_item, names);

                ccEt.setThreshold(3);
                ccEt.setAdapter(adapter);
                ccEt.setOnItemClickListener((adapterView, view, i, l) -> {
                    if (recyclerview_list.size() < 6) {
                        recyclerview_list.add(names.get(i));
                        ccListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MyTaskDetail.this, "You can add upto 5 emails in CC", Toast.LENGTH_LONG).show();
                    }
                    ccEt.setText("");
                });
                adapter.notifyDataSetChanged();
            }
        }, prefix);
    }

    public void submitRequest(String location, String tickettypeid, String subcat, String subcat2, String subcat3, String tickettypegroupid, String assigneegroup, String asigneGroupCode, String DefaultAssigne, String reportedby, String priority, String attachedfiles, String attFileBytes, String description, String cc) {
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.submitRequest(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(MyTaskDetail.this, "Successfully submitted", Toast.LENGTH_LONG).show();
                Intent in = new Intent(MyTaskDetail.this, ITHelpDeskHome.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();

            }
        }, location, tickettypeid, subcat, subcat2, subcat3, tickettypegroupid, assigneegroup, asigneGroupCode, DefaultAssigne, reportedby, priority, attachedfiles, attFileBytes, description, cc, empcode);

    }


    public void getPriority() {
        priorities.clear();
        priorities.add("Select");
        priorityList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getPriority(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    priorityList.addAll(list);
                }
                for (AssetLocResponse assetLocResponse : priorityList) {
                    priorities.add(assetLocResponse.getName());
                }


                if (myTicket != null && myTicket.getPriority() != null) {
                    AssetLocResponse assetLocResponse = new AssetLocResponse();
                    assetLocResponse.setId(myTicket.getPriority());
                    int i = priorityList.indexOf(assetLocResponse);
                    prioritySpinner.setSelection(i + 1);
                }
                priorityAdapter.notifyDataSetChanged();

            }

        });
    }

    public void getReportedBy() {
        reportedbylist.clear();
        reportedbylist.add("Select");
        reportedByListResponse.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getReportedBy(carotResponse -> {

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    reportedByListResponse.addAll(list);
                }
                for (AssetLocResponse assetLocResponse : reportedByListResponse) {
                    reportedbylist.add(assetLocResponse.getName().toUpperCase());
                }

                if (myTicket != null && myTicket.getReportedby() != null) {
                    int i = reportedbylist.indexOf(myTicket.getReportedby().toUpperCase());
                    reportedBySpinner.setSelection(i);
                }

                reportedbyAdapter.notifyDataSetChanged();
            }


        }, empcode);
    }

    public void getSubCat(String SubCat, String TicketType, String UnitCode) {
        subcats.clear();
        subcats.add("Select");
        subcatList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    subcatList.addAll(list);

                }
                for (AssetLocResponse assetLocResponse : subcatList) {
                    subcats.add(assetLocResponse.getName());
                }

                if (myTicket != null && myTicket.getSubCat() != null) {
                    AssetLocResponse assetLocResponse = new AssetLocResponse();
                    assetLocResponse.setId(myTicket.getSubCat());
                    int i = subcatList.indexOf(assetLocResponse);

                    if (i >= 0) {
                        ticket_type_spinner_sub_cat.setSelection(i + 1);
                    }
                }
                subcatAdapter.notifyDataSetChanged();

            }

        }, SubCat, TicketType, UnitCode);
    }

    public void getSubCat2(String SubCat, String SubCat2, String TicketType, String UnitCode) {
        subcats2.clear();
        subcats2.add("Select");
        subcat2List.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat2(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    subcat2List.addAll(list);

                }
                for (AssetLocResponse assetLocResponse : subcat2List) {
                    subcats2.add(assetLocResponse.getName());

                }

                if (myTicket != null && myTicket.getSubCat2() != null) {
                    AssetLocResponse assetLocResponse = new AssetLocResponse();
                    assetLocResponse.setId(myTicket.getSubCat2());
                    int i = subcat2List.indexOf(assetLocResponse);
                    if (i >= 0) {
                        ticket_type_spinner_sub_cat2.setSelection(i + 1);
                    }
                }
                subcat2Adapter.notifyDataSetChanged();
            }
        }, SubCat, SubCat2, TicketType, UnitCode);
    }

    public void getSubCat3(String SubCat2, String SubCat3, String TicketType, String UnitCode) {
        subcats3.clear();
        subcats3.add("Select");
        subcat3List.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat3(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    subcat3List.addAll(list);
                }

                for (AssetLocResponse assetLocResponse : subcat3List) {
                    if (assetLocResponse.getName() != null) {
                        subcats3.add(assetLocResponse.getName());
                    } else {
                        subcats3.add(assetLocResponse.getNamecat3());
                    }
                }

                if (myTicket != null && myTicket.getSubCat3() != null) {
                    AssetLocResponse assetLocResponse = new AssetLocResponse();
                    assetLocResponse.setId(myTicket.getSubCat3());
                    int i = subcat3List.indexOf(assetLocResponse);
                    if (i >= 0) {
                        ticket_type_spinner_sub_cat3.setSelection(i + 1);
                    }
                }
                subcat3Adapter.notifyDataSetChanged();

            }

        }, SubCat2, SubCat3, TicketType, UnitCode);
    }


    public void selectFile() {
        requestAppPermissions();
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            selectImage();

            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            selectImage();

            return;
        }

        ActivityCompat.requestPermissions(MyTaskDetail.this,
                new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_FROM_CAMERA);

      /*  Intent in = new Intent(getActivity(), FilePickerActivity.class);
        in.putExtra(FilePickerConstants.CAMERA, true);
        startActivityForResult(in, CAPTURE_FROM_CAMERA);
*/

    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Choose Document",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyTaskDetail.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(MyTaskDetail.this);
            if (items[item].equals("Take Photo")) {
                mUserChoosenTask = "Take Photo";
                if (result) {
                    requestCameraPermission();
                    if (hasCameraPermission())
                        cameraIntent();
                }
            } else if (items[item].equals("Choose from Gallery")) {
                mUserChoosenTask = "Choose from Gallery";
                if (result) {
                    galleryIntent();
                }

            } else if (items[item].equals("Choose Document")) {
                mUserChoosenTask = "Choose Document";
                if (result) {
                    fileIntent();
                }

            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void fileIntent() {
        String[] mimeTypes =
                {"application/pdf", "application/msword", "application/vnd.ms-powerpoint", "application/vnd.ms-excel", "text/plain", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), SELECT_FILE);
    }

    private void onCaptureImageResult(Intent data) {

      /*  docView.setImageURI(FilePickerUriHelper.getUri(data));

        Uri uri = FilePickerUriHelper.getUri(data);
       Bitmap thumbnail=  uriToBitmap(uri);
*/
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        bytes = getBytesFromBitmap(thumbnail);
        attachmentName = fileName;
        attachmentType = "jpg";
        bmp = thumbnail;
        attachmentNames.add(attachmentName);
        String fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        attachmentFiles.add(fileByte);


       /* if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView1.setVisibility(View.VISIBLE);
            docView1.setImageBitmap(thumbnail);
        }
*/

    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation = getRotation(context, selectedImage);
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        } else {
            return img;
        }
    }

    private static int getRotation(Context context, Uri selectedImage) {

        int rotation = 0;
        ContentResolver content = context.getContentResolver();

        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{"orientation", "date_added"},
                null, null, "date_added desc");

        if (mediaCursor != null && mediaCursor.getCount() != 0) {
            while (mediaCursor.moveToNext()) {
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (mDestinationFile != null) {
            mDestinationFile.delete();
        }
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(MyTaskDetail.this.getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(MyTaskDetail.this, bm, Uri.parse(mDestinationFile.toString()));
        //  docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        attachmentName = fileName;
        attachmentType = "jpg";
        bytes = getBytesFromBitmap(bm);
        bmp = bm;
        attachmentNames.add(attachmentName);
        String fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        attachmentFiles.add(fileByte);


/*

        if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView1.setVisibility(View.VISIBLE);
            docView1.setImageBitmap(bm);
        }
*/
        //  addUserImage(fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FROM_GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAPTURE_FROM_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == SELECT_FILE)
                onSelectFile(data);
        }
        attachtext.setText(attachmentFiles.size() + " files attached");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {

        Uri fileUri = data.getData();
        String mimeType = getContentResolver().getType(fileUri);

        String fullFilePath = UriUtils.getPathFromUri(MyTaskDetail.this, fileUri);
        File file = new File(fullFilePath);
        attachmentName = file.getName();
        attachmentType = mimeType.replace("application/", "");
        attachtext.setText(attachmentName);
        bytes = new byte[(int) file.length()];

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes); //read file into bytes[]
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        attachmentNames.add(attachmentName);
        String fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        attachmentFiles.add(fileByte);


     /*   if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);


        }
*/
        //   return getStringFile(file);
    }


    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(MyTaskDetail.this,
                new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code


    }


    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(MyTaskDetail.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(MyTaskDetail.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(MyTaskDetail.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }


    public void updateTicket() {
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.updateMyTaskTicket(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                Toast.makeText(MyTaskDetail.this, "Ticket Updated successfully", Toast.LENGTH_LONG).show();
                Intent in = new Intent(MyTaskDetail.this, ITHelpDeskHome.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();

            }

        }, myTicket.getTicketNo(), empcode, remarks_et.getText().toString(), statusStr, location, tickettypeid, subcat, subcat2, subcat3, tickettypegroupid, assigneegroup, asigneGroupCode, DefaultAssigne, reportedby, priority, attachmentNames.toString().replace("[", "").replace("]", ""), attachmentFiles.toString().replace("[", "").replace("]", ""), "", "");
    }

    public void getTicketHistory(String ticketNo) {
        tktHistoryList.clear();
        tktHistoryRv.getRecycledViewPool().clear();
        ticketsAdapter.notifyDataSetChanged();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketHistory(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {

                List<TicketHistoryResponse> list = (List<TicketHistoryResponse>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    tktHistoryList.addAll(list);
                }
                if (tktHistoryList.size() > 0) {
                    headerHistory.setVisibility(View.VISIBLE);
                } else {
                    headerHistory.setVisibility(View.GONE);
                }
            } else {
                headerHistory.setVisibility(View.GONE);

            }
            ticketsAdapter.notifyDataSetChanged();
        }, ticketNo);
    }

}
