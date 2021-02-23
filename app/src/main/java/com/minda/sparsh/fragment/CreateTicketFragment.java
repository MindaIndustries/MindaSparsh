package com.minda.sparsh.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.CCListAdapter;
import com.minda.sparsh.ITHelpDeskHome;
import com.minda.sparsh.R;
import com.minda.sparsh.customview.NoDefaultSpinner;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AssetLocResponse;
import com.minda.sparsh.model.BindGroupResponse;
import com.minda.sparsh.model.GroupAssigneeResponse;
import com.minda.sparsh.model.MyTicketsResponse;
import com.minda.sparsh.services.ITHelpDeskServices;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class CreateTicketFragment extends Fragment {

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
    ArrayList<String> recyclerview_list = new ArrayList<>();
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
    String unitcode,depucode,username,empcode;

    ArrayList<String> assetloc = new ArrayList<String>();
    ArrayList<AssetLocResponse> assetList = new ArrayList<>();
    ArrayAdapter<String> assetAdapter, ticketTypeAdapter, ticketGroupAdapter,groupAssigneeAdapter, priorityAdapter,reportedbyAdapter,subcatAdapter,subcat2Adapter,subcat3Adapter;
    ArrayList<String> tickettypes = new ArrayList<String>();
    ArrayList<AssetLocResponse> ticketTypeList = new ArrayList<>();
    ArrayList<String> ticketGroups = new ArrayList<String>();
    ArrayList<BindGroupResponse> ticketGroupList = new ArrayList<>();
    ArrayList<String> groupAssignee = new ArrayList<String>();
    ArrayList<GroupAssigneeResponse> groupAssigneeList = new ArrayList<>();
    ArrayList<String> priorities = new ArrayList<>();
    ArrayList<AssetLocResponse> priorityList = new ArrayList<>();

    ArrayList<String> reportedbylist = new ArrayList<>();
    ArrayList<AssetLocResponse> reportedByListResponse = new ArrayList<>();
    String location,tickettypeid,tickettypegroupid,assigneegroup,asigneGroupCode,DefaultAssigne,reportedby,priority;
    CCListAdapter ccListAdapter;



    ArrayList<AssetLocResponse> subcatList = new ArrayList<>();
    ArrayList<AssetLocResponse> subcat2List = new ArrayList<>();
    ArrayList<AssetLocResponse> subcat3List = new ArrayList<>();
    ArrayList<String> subcats = new ArrayList<>();
    ArrayList<String> subcats2 = new ArrayList<>();
    ArrayList<String> subcats3 = new ArrayList<>();
    ArrayList<String> attachmentNames = new ArrayList<>();
    ArrayList<String> attachmentFiles = new ArrayList<>();
    String subcat="0",subcat2 ="0",subcat3="0";




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View createTicket = inflater.inflate(R.layout.create_ticket, container, false);
        ButterKnife.bind(this, createTicket);
        initUI();

        return createTicket;
    }
    public void initUI(){
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        depucode = myPref.getString("Depu_UnitCode","");
        username = myPref.getString("username","");
        empcode = myPref.getString("Id","");
       // reportedby = empcode;



        if(getArguments()!=null && getArguments().get("TicketData")!=null){
            myTicket = (MyTicketsResponse) getArguments().get("TicketData");
            if(myTicket!=null){


            }
        }
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        String monthStr,dayStr;
        if(month<10){
            monthStr = "0"+month;
        }
        else{
            monthStr = ""+month;
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(day<10){
            dayStr = "0"+day;
        }
        else{
            dayStr = ""+day;
        }
        reportedDate.setText(""+ dayStr+"/"+monthStr+"/"+calendar.get(Calendar.YEAR));
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
         ccListAdapter = new CCListAdapter(getActivity(),recyclerview_list);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cclist.setLayoutManager(mLayoutManager);
        cclist.setAdapter(ccListAdapter);





        reportedBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    reportedby = reportedByListResponse.get(i-1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        assetLocSPinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0)
                location = assetList.get(i-1).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ccEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Utility.isOnline(getActivity())) {
                    getAutoName(ccEt.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ticket_type_spinner_sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i>0 && subcatList.size()>0) {
                    subcat = subcatList.get(i-1).getId();

                    if (subcatList.get(i - 1).getNext() != null && subcatList.get(i - 1).getNext().equals("C")) {
                        initSubcat2Spinner();
                        getSubCat2(subcatList.get(i - 1).getId(), "", tickettypeid, unitcode);
                        initSubCatSpinner3();
                        ticket_type_sub_cat2.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.VISIBLE);
                        ll10.setVisibility(View.VISIBLE);
                    }
                    else{
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

                if(i>0 && subcat2List.size()>0){
                    subcat2 = subcat2List.get(i-1).getId();

                    if(subcat2List.get(i-1).getNext() !=null && subcat2List.get(i-1).getNext().equals("C")){
                        initSubCatSpinner3();
                        getSubCat3(subcat2List.get(i-1).getId(),"",tickettypeid,unitcode);
                        ticket_type_sub_cat3.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.VISIBLE);
                        ll11.setVisibility(View.VISIBLE);
                    }
                    else{
                        ticket_type_sub_cat3.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat3.setVisibility(View.GONE);
                        ll11.setVisibility(View.GONE);
                       /* ticket_type_sub_cat2.setVisibility(View.GONE);
                        ticket_type_spinner_sub_cat2.setVisibility(View.GONE);
                        ll10.setVisibility(View.GONE);
*/
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
                if(i>0 && subcat3List.size()>0) {
                    subcat3 = subcat3List.get(i-1).getId();
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
                if( i>0 && ticketTypeList.size()>0) {
                    if(ticketTypeList.get(i-1).getNextLevel()!=null && ticketTypeList.get(i-1).getNextLevel().equals("G")) {
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
                    }
                    else{
                        ticket_type_sub_cat.setVisibility(View.VISIBLE);
                        ticket_type_spinner_sub_cat.setVisibility(View.VISIBLE);
                        ll9.setVisibility(View.VISIBLE);
                        initTicketGroupSpinner();
                        initGroupAssigneeSpinner();
                        initSubCatSpinner();
                        tickettypeid = ticketTypeList.get(i - 1).getId();
                        getSubCat("",ticketTypeList.get(i - 1).getId(), unitcode);
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
                if(i>0 && ticketGroupList.size()>0){
                    groupAssignee.clear();
                    groupAssignee.add("Select");
                    initGroupAssigneeSpinner();
                    tickettypegroupid = ticketGroupList.get(i-1).getId();
                    getGroupAssignee(ticketGroupList.get(i-1).getId(), unitcode);

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
                    DefaultAssigne = groupAssigneeList.get(i - 1).getDefault();
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

    @OnClick(R.id.submit)
    public void onClickSubmit() {

        if (location.length() == 0) {
            Toast.makeText(getActivity(), "Select Asset Location ", Toast.LENGTH_LONG).show();
            return;
        }

        if (tickettypeid.length() == 0) {
            Toast.makeText(getActivity(), "Select Ticket Type ", Toast.LENGTH_LONG).show();
            return;
        }
        if (tickettypegroupid.length() == 0) {
            Toast.makeText(getActivity(), "Select Ticket Group ", Toast.LENGTH_LONG).show();
            return;
        }
        if (assigneegroup.length() == 0) {
            Toast.makeText(getActivity(), "Select Assignee ", Toast.LENGTH_LONG).show();
            return;
        }
       /* if(DefaultAssigne.length()==0){
            Toast.makeText(getActivity(), "Select Ticket Type ", Toast.LENGTH_LONG).show();
            return;
        }
       */
        if (reportedby.length() == 0) {
            Toast.makeText(getActivity(), "Select ReportedBy ", Toast.LENGTH_LONG).show();
            return;
        }
        if (priority.length() == 0) {
            Toast.makeText(getActivity(), "Select Priority ", Toast.LENGTH_LONG).show();
            return;
        }
        if (descriptionEditText.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Enter Description ", Toast.LENGTH_LONG).show();
            return;
        }

        if (Utility.isOnline(getActivity())) {
            submitRequest(location, tickettypeid, subcat, subcat2, subcat3, tickettypegroupid, assigneegroup, asigneGroupCode, DefaultAssigne, reportedby, priority, attachmentNames.toString().replace("[", "").replace("]", ""), attachmentFiles.toString().replace("[", "").replace("]", ""), descriptionEditText.getText().toString(), recyclerview_list.toString().replace("[", "").replace("]", ""));
        }
        else{
            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.attachment)
    public void onClickAttachment(){
        selectFile();
    }
    @OnClick(R.id.attachtext)
    public void onClickAttachText(){
        selectFile();
    }


  public void initPrioritySpinner(){
        priorities.add("Select");
        priorityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,priorities){
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
    public void initReportedBySpinner(){
        reportedbylist.add("Select");
       // reportedbylist.add(username+" ("+empcode+")");
        reportedbyAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,reportedbylist){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }            }

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
        if(reportedbylist.size()>0)
        reportedBySpinner.setSelection(1);
    }


    public void initAssetLocSpinner(){
        assetloc.clear();
        assetloc.add("Select");
        assetAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row, assetloc){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }            }

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

    public void initTicketTypeSpinner(){
        tickettypes.clear();
        tickettypes.add("Select");
        ticketTypeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row, tickettypes){
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

    public void initTicketGroupSpinner(){
        ticketGroups.clear();
        ticketGroups.add("Select");
        ticketGroupAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row, ticketGroups){
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

    public void initGroupAssigneeSpinner(){
        groupAssigneeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row, groupAssignee){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }            }

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

    public void initSubcat2Spinner(){
        subcats2.clear();
        subcats2.add("Select");
        subcat2Adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row,subcats2){

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

    public void initSubCatSpinner3(){
        subcats3.clear();
        subcats3.add("Select");
        subcat3Adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row,subcats3){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }            }

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
                return view;                }
        };
        subcat3Adapter.setDropDownViewResource(R.layout.spinner_row);
        ticket_type_spinner_sub_cat3.setAdapter(subcat3Adapter);
        subcat3Adapter.notifyDataSetChanged();
    }
    public void initSubCatSpinner(){
        subcats.clear();
        subcats.add("Select");
        subcatAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,subcats){
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
                return view;            }
        };

        subcatAdapter.setDropDownViewResource(R.layout.spinner_row);
        ticket_type_spinner_sub_cat.setAdapter(subcatAdapter);
        subcatAdapter.notifyDataSetChanged();
    }


    public void getAssetLoc(){
        assetList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getAssetLoc(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                 if(list!=null && list.size()>0) {
                     assetList.addAll(list);

                     for (AssetLocResponse assetLocResponse : assetList) {
                        // if (assetLocResponse.getId().equals(unitcode)) {
                             assetloc.add(assetLocResponse.getName());
                         //}
                     }
                     if (assetloc.size() > 0) {
                         assetLocSPinner.setSelection(1);
                     }

                     if(myTicket!=null && myTicket.getLocation()!=null){
                         int  i = assetloc.indexOf(myTicket.getLocation());
                         //loc pending
                     }
                     assetAdapter.notifyDataSetChanged();
                 }
                 }

            }
        },empcode);

    }

    public void getTicketType(){
        ticketTypeList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketType(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        ticketTypeList.addAll(list);
                        for (AssetLocResponse assetLocResponse : ticketTypeList) {
                            tickettypes.add(assetLocResponse.getName());
                        }
                    }
                    }

                if(myTicket!=null && myTicket.getTicketType()!=null){
                    int i = tickettypes.indexOf(myTicket.getTicketType());
                    ticketTypeSpinner.setSelection(i);
                }
                ticketTypeAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getTicketGroup(String ticketType, String unitcode){
        ticketGroups.clear();
        ticketGroups.add("Select");
        ticketGroupList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getTicketGroup(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<BindGroupResponse> list = (List<BindGroupResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        ticketGroupList.addAll(list);
                        for (BindGroupResponse bindGroupResponse : ticketGroupList) {
                            ticketGroups.add(bindGroupResponse.getName());
                        }
                    }

                    ticketGroupAdapter.notifyDataSetChanged();
                }

            }
        },ticketType,unitcode,subcat,subcat2,subcat3);
    }

    public void getGroupAssignee(String ticketType, String unitcode){
        groupAssignee.clear();
        groupAssignee.add("Select");
        groupAssigneeList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getGroupAssignee(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<GroupAssigneeResponse> list = (List<GroupAssigneeResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        groupAssigneeList.addAll(list);
                        for (GroupAssigneeResponse groupAssigneeResponse : groupAssigneeList) {
                            groupAssignee.add(groupAssigneeResponse.getName());
                        }
                    }
                    groupAssigneeAdapter.notifyDataSetChanged();
                }

            }
        },ticketType,unitcode);

    }


    public void getAutoName(String prefix){

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getAutoName(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    List<String> names = (List<String>) carotResponse.getData();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.select_dialog_item, names);

                    ccEt.setThreshold(3);
                    ccEt.setAdapter(adapter);
                    ccEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(recyclerview_list.size()<6) {
                                recyclerview_list.add(names.get(i));
                                ccListAdapter.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(getActivity(),"You can add upto 5 emails in CC",Toast.LENGTH_LONG).show();
                            }
                            ccEt.setText("");
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
        },prefix);
    }

    public void submitRequest(String location,String tickettypeid,String subcat,String subcat2, String subcat3,String tickettypegroupid,String assigneegroup,String asigneGroupCode, String DefaultAssigne, String reportedby, String priority, String attachedfiles, String attFileBytes, String description, String cc){
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.submitRequest(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()==HttpsURLConnection.HTTP_OK){
                    Toast.makeText(getActivity(), "Successfully submitted", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getActivity(), ITHelpDeskHome.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    getActivity().finish();

                }
                }
        },location,tickettypeid,subcat,subcat2,subcat3,tickettypegroupid,assigneegroup,asigneGroupCode,DefaultAssigne,reportedby,priority,attachedfiles,attFileBytes,description,cc,empcode);

    }



    public void getPriority(){
        priorities.clear();
        priorities.add("Select");
        priorityList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getPriority(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        priorityList.addAll(list);
                    }
                    for (AssetLocResponse assetLocResponse : priorityList) {
                        priorities.add(assetLocResponse.getName());
                        priorityAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
    public void getReportedBy(){
        reportedbylist.clear();
        reportedbylist.add("Select");
        reportedByListResponse.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getReportedBy(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        reportedByListResponse.addAll(list);
                    }
                    for (AssetLocResponse assetLocResponse : reportedByListResponse) {
                        reportedbylist.add(assetLocResponse.getName());
                    }
                    reportedbyAdapter.notifyDataSetChanged();
                }



            }
        },empcode);
    }

    public void getSubCat(String SubCat, String TicketType, String UnitCode){
        subcats.clear();
        subcats.add("Select");
        subcatList.clear();
        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        subcatList.addAll(list);

                    }
                    for (AssetLocResponse assetLocResponse : subcatList) {
                        subcats.add(assetLocResponse.getName());
                    }
                    subcatAdapter.notifyDataSetChanged();

                }

            }
        },SubCat,TicketType,UnitCode);
    }
    public void getSubCat2(String SubCat,String SubCat2, String TicketType, String UnitCode){
        subcats2.clear();
        subcats2.add("Select");
        subcat2List.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat2(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        subcat2List.addAll(list);

                    }
                    for (AssetLocResponse assetLocResponse : subcat2List) {
                        if (assetLocResponse.getName() != null) {
                            subcats2.add(assetLocResponse.getName());
                        } else if (assetLocResponse.getNamecat3() != null) {
                            subcats2.add(assetLocResponse.getNamecat3());

                        }
                    }
                    subcat2Adapter.notifyDataSetChanged();
                }
            }
        },SubCat,SubCat2,TicketType,UnitCode);
    }
    public void getSubCat3(String SubCat2,String SubCat3, String TicketType, String UnitCode){
        subcats3.clear();
        subcats3.add("Select");
        subcat3List.clear();

        ITHelpDeskServices itHelpDeskServices = new ITHelpDeskServices();
        itHelpDeskServices.getSubCat3(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                    List<AssetLocResponse> list = (List<AssetLocResponse>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        subcat3List.addAll(list);
                    }

                    for (AssetLocResponse assetLocResponse : subcat3List) {
                        if(assetLocResponse.getName()!=null) {
                            subcats3.add(assetLocResponse.getName());
                        }
                        else{
                            subcats3.add(assetLocResponse.getNamecat3());
                        }
                    }
                    subcat3Adapter.notifyDataSetChanged();

                }

            }
        },SubCat2,SubCat3,TicketType,UnitCode);
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

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
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
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(getActivity(), bm, Uri.parse(mDestinationFile.toString()));
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
        attachtext.setText(attachmentFiles.size()+" files attached");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {

        Uri fileUri = data.getData();
        String mimeType = getActivity().getContentResolver().getType(fileUri);

        String fullFilePath = UriUtils.getPathFromUri(getActivity(), fileUri);
        File file = new File(fullFilePath);
        attachmentName = file.getName();
        attachmentType = mimeType.replace("application/", "");
        attachtext.setText(attachmentName);
        bytes = new byte[(int) file.length()];

        FileInputStream fis = null;
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

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code


    }


    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }


}
