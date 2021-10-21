package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.Adapter.ARPDAdapter;
import com.minda.sparsh.Adapter.AccessRequestApproverDetailsAdapter;
import com.minda.sparsh.Adapter.PlantDetailsAdapter;
import com.minda.sparsh.model.ARPDModel;
import com.minda.sparsh.model.AccessRequestApproverDetailsModel;
import com.minda.sparsh.model.AccessRequestDetailsModel;
import com.minda.sparsh.model.AccessRequestPlantDetailModel;
import com.minda.sparsh.model.IAMBModel;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessRequestDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    private RecyclerView rv_plant_details, rv_approval_details, rv_processor_details;
    TextView tv_access_request_no, tv_request_on, tv_request_type, tv_access_type, tv_access_sub_type, tv_access_for,
            tv_access_for_name, tv_category, tv_sub_category, tv_user_authorisation_profile, tv_approval_status,
            tv_access_requirement_details, tv_access_request_by, tv_source, tv_name, tv_organisation, tv_purpose, tv_processor_detail, tv_scroll;
    LinearLayout lay_access_request_by, lay_source, lay_name, lay_organisation, lay_purpose, lay_access_for_name, lay_sub_category,
            lay_user_authorisation_profile;
    Button btn_approve, btn_un_approve, btn_send_back;
    TextInputEditText et_approve_unapprove;
    String approvalLevel_val, approvalId_val;
    HorizontalScrollView lay_processor_view;
    ImageView im_back;

    TextView tv_scroll_Approval_Details;

    TextInputLayout tv_approve_unapprove_heading;

    HorizontalScrollView horizontalView;
    String IAMB;
    String catID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_request_details);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        progress = new ProgressDialog(AccessRequestDetailsActivity.this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        im_back = findViewById(R.id.im_back);
        im_back.setOnClickListener(view -> finish());
        btn_approve = findViewById(R.id.btn_approve);
        btn_un_approve = findViewById(R.id.btn_un_approve);
        btn_send_back = findViewById(R.id.btn_send_back);
        btn_approve.setOnClickListener(this);
        btn_un_approve.setOnClickListener(this);
        btn_send_back.setOnClickListener(this);
        lay_processor_view = findViewById(R.id.lay_processor_view);
        et_approve_unapprove = findViewById(R.id.et_approve_unapprove);
        tv_access_request_no = findViewById(R.id.tv_access_request_no);
        tv_request_on = findViewById(R.id.tv_request_on);
        tv_request_type = findViewById(R.id.tv_request_type);
        tv_access_type = findViewById(R.id.tv_access_type);
        tv_access_sub_type = findViewById(R.id.tv_access_sub_type);
        tv_access_for = findViewById(R.id.tv_access_for);
        tv_access_for_name = findViewById(R.id.tv_access_for_name);
        tv_category = findViewById(R.id.tv_category);
        tv_sub_category = findViewById(R.id.tv_sub_category);
        tv_user_authorisation_profile = findViewById(R.id.tv_user_authorisation_profile);
        tv_approval_status = findViewById(R.id.tv_approval_status);
        tv_access_requirement_details = findViewById(R.id.tv_access_requirement_details);
        tv_access_request_by = findViewById(R.id.tv_access_request_by);
        tv_source = findViewById(R.id.tv_source);
        tv_name = findViewById(R.id.tv_name);
        tv_organisation = findViewById(R.id.tv_organisation);
        tv_purpose = findViewById(R.id.tv_purpose);
        tv_approve_unapprove_heading = findViewById(R.id.tv_approve_unapprove_heading);
        tv_processor_detail = findViewById(R.id.tv_processor_detail);
        tv_scroll = findViewById(R.id.tv_scroll);
        lay_access_request_by = findViewById(R.id.lay_access_request_by);
        lay_source = findViewById(R.id.lay_source);
        lay_name = findViewById(R.id.lay_name);
        lay_organisation = findViewById(R.id.lay_organisation);
        lay_purpose = findViewById(R.id.lay_purpose);
        lay_access_for_name = findViewById(R.id.lay_access_for_name);
        lay_sub_category = findViewById(R.id.lay_sub_category);
        lay_user_authorisation_profile = findViewById(R.id.lay_user_authorisation_profile);
        rv_plant_details = findViewById(R.id.rv_plant_details);
        rv_approval_details = findViewById(R.id.rv_approval_details);
        rv_processor_details = findViewById(R.id.rv_processor_details);
        tv_scroll_Approval_Details = findViewById(R.id.tv_scroll_Approval_Details);
        horizontalView = findViewById(R.id.horizontalView);
        Intent intent = getIntent();
        if (intent != null) {
            String requestId = String.valueOf(intent.getIntExtra(Utility.REQUEST_ID, 0));
            hitGetAccessRequestDetailApi(RetrofitClient2.CKEY, requestId);
        }
        tv_scroll_Approval_Details.setOnClickListener(view -> horizontalView.postDelayed(() -> horizontalView.fullScroll(HorizontalScrollView.FOCUS_RIGHT), 100L));
        //for processor details
        tv_scroll.setOnClickListener(view -> lay_processor_view.postDelayed(() -> lay_processor_view.fullScroll(HorizontalScrollView.FOCUS_RIGHT), 100L));
    }
    public void hitGetAccessRequestDetailApi(String key, String requestId) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AccessRequestDetailsModel>> response = anInterface.GetAccessRequestDetail(key, requestId);
            response.enqueue(new Callback<List<AccessRequestDetailsModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<AccessRequestDetailsModel>> call, @NotNull Response<List<AccessRequestDetailsModel>> response) {
                    dismissProgress();
                    List<AccessRequestDetailsModel> accessRequestDetailsModels = response.body();
                    if (accessRequestDetailsModels != null) {
                        if (accessRequestDetailsModels.get(0).getAccessRequestNo() != null) {
                            tv_access_request_no.setText(accessRequestDetailsModels.get(0).getAccessRequestNo());
                            tv_request_on.setText(accessRequestDetailsModels.get(0).getCreatedOn());
                            tv_request_type.setText(accessRequestDetailsModels.get(0).getRequestType());
                            tv_access_type.setText(accessRequestDetailsModels.get(0).getAccessType());
                            tv_access_sub_type.setText(accessRequestDetailsModels.get(0).getAccessSubType());
                            tv_access_for.setText(accessRequestDetailsModels.get(0).getAccessFor());
                            tv_access_for_name.setText(accessRequestDetailsModels.get(0).getAccessForName());
                            tv_category.setText(accessRequestDetailsModels.get(0).getCategory());
                            tv_sub_category.setText(accessRequestDetailsModels.get(0).getSubCategory() + "");
                            tv_user_authorisation_profile.setText(accessRequestDetailsModels.get(0).getProfileName() + "");
                            tv_approval_status.setText(accessRequestDetailsModels.get(0).getFlag());
                            catID = String.valueOf(accessRequestDetailsModels.get(0).getCategoryId());

                           /* if(accessRequestDetailsModels.get(0).getFlag().contains("Pending")){
                                btn_un_approve.setVisibility(View.VISIBLE);
                                tv_approve_unapprove_heading.setVisibility(View.VISIBLE);
                                btn_send_back.setVisibility(View.VISIBLE);

                            }
                            else{
                                btn_un_approve.setVisibility(View.GONE);
                                tv_approve_unapprove_heading.setVisibility(View.GONE);
                                btn_send_back.setVisibility(View.GONE);

                            }
                           */
                            tv_access_requirement_details.setText(accessRequestDetailsModels.get(0).getRequirementDetail());
                            lay_access_request_by.setVisibility(View.VISIBLE);
                            tv_access_request_by.setText(accessRequestDetailsModels.get(0).getCreatedByName() + "(" + accessRequestDetailsModels.get(0).getCreatedBy() + ")");
                            if (accessRequestDetailsModels.get(0).getAccessForType() == 1) {
                                tv_access_for.setText("Self");
                                lay_source.setVisibility(View.GONE);
                                lay_organisation.setVisibility(View.GONE);
                                lay_purpose.setVisibility(View.GONE);
                                lay_name.setVisibility(View.GONE);
                                lay_access_for_name.setVisibility(View.VISIBLE);
                                tv_access_for_name.setText(accessRequestDetailsModels.get(0).getAccessForName() + "(" + accessRequestDetailsModels.get(0).getAccessFor() + ")");
                            } else {
                                tv_access_for.setText("Other");
                                lay_access_request_by.setVisibility(View.VISIBLE);
                                lay_source.setVisibility(View.VISIBLE);
                                if (accessRequestDetailsModels.get(0).getAccessForSubType() == 1) {
                                    tv_source.setText("Internal");
                                    lay_organisation.setVisibility(View.GONE);
                                    lay_purpose.setVisibility(View.GONE);
                                    lay_name.setVisibility(View.GONE);
                                    lay_access_for_name.setVisibility(View.VISIBLE);
                                    tv_access_for_name.setText(accessRequestDetailsModels.get(0).getAccessForName() + "(" + accessRequestDetailsModels.get(0).getAccessFor() + ")");
                                } else {
                                    tv_source.setText("External");
                                    lay_organisation.setVisibility(View.VISIBLE);
                                    lay_purpose.setVisibility(View.VISIBLE);
                                    lay_name.setVisibility(View.VISIBLE);
                                    lay_access_for_name.setVisibility(View.GONE);
                                    tv_organisation.setText(accessRequestDetailsModels.get(0).getOrganisation());
                                    tv_name.setText(accessRequestDetailsModels.get(0).getExternalName());
                                    tv_purpose.setText(accessRequestDetailsModels.get(0).getPurpose());
                                }
                            }

                            if (accessRequestDetailsModels.get(0).getSubCategoryId() == 0) {
                                lay_sub_category.setVisibility(View.GONE);
                            } else {
                                lay_sub_category.setVisibility(View.VISIBLE);
                                tv_sub_category.setText(accessRequestDetailsModels.get(0).getSubCategory() + "");
                            }
                            if (accessRequestDetailsModels.get(0).getProfileId() == 0) {
                                lay_user_authorisation_profile.setVisibility(View.GONE);
                            } else {
                                lay_user_authorisation_profile.setVisibility(View.VISIBLE);
                                tv_user_authorisation_profile.setText(accessRequestDetailsModels.get(0).getProfileName() + "");
                            }

                            hitGetAccessRequestPlantDetailApi(RetrofitClient2.CKEY, accessRequestDetailsModels.get(0).getAccessRequestNo());
                            hitGetAccessRequestApproverDetailApi(RetrofitClient2.CKEY, accessRequestDetailsModels.get(0).getAccessRequestNo());
                            if (accessRequestDetailsModels.get(0).getCategoryId() == 1 & accessRequestDetailsModels.get(0).getAccessTypeId() == 1) {
                                hitGetAccessRequestProcessorDetailExtApi(RetrofitClient2.CKEY, accessRequestDetailsModels.get(0).getAccessRequestNo());
                            } else {
                                hitGetAccessRequestProcessorDetailApi(RetrofitClient2.CKEY, accessRequestDetailsModels.get(0).getAccessRequestNo(), String.valueOf(accessRequestDetailsModels.get(0).getCategoryId()));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<AccessRequestDetailsModel>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitGetAccessRequestPlantDetailApi(String key, String requestId) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AccessRequestPlantDetailModel>> response = anInterface.GetAccessRequestPlantDetail(key, requestId);
            response.enqueue(new Callback<List<AccessRequestPlantDetailModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<AccessRequestPlantDetailModel>> call, @NotNull Response<List<AccessRequestPlantDetailModel>> response) {
                    dismissProgress();
                    List<AccessRequestPlantDetailModel> accessRequestPlantDetailModels = response.body();

                    if (accessRequestPlantDetailModels != null && accessRequestPlantDetailModels.size() > 0) {
                        if (accessRequestPlantDetailModels.get(0).getAccessRequestNo() != null) {
                            PlantDetailsAdapter mAdapter = new PlantDetailsAdapter(response.body(), AccessRequestDetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AccessRequestDetailsActivity.this);
                            rv_plant_details.setLayoutManager(mLayoutManager);
                            rv_plant_details.setItemAnimator(new DefaultItemAnimator());
                            rv_plant_details.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<AccessRequestPlantDetailModel>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    // for access request approver
    public void hitGetAccessRequestApproverDetailApi(String key, String accessRequestNo) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AccessRequestApproverDetailsModel>> response = anInterface.GetAccessRequestApproverDetail(key, accessRequestNo);
            response.enqueue(new Callback<List<AccessRequestApproverDetailsModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<AccessRequestApproverDetailsModel>> call, @NotNull Response<List<AccessRequestApproverDetailsModel>> response) {
                    dismissProgress();
                    List<AccessRequestApproverDetailsModel> responses = response.body();
                    if (responses != null) {
                        if (responses.get(0).getApprovalId() != null) {
                            AccessRequestApproverDetailsAdapter mAdapter = new AccessRequestApproverDetailsAdapter(response.body(), AccessRequestDetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AccessRequestDetailsActivity.this);
                            rv_approval_details.setLayoutManager(mLayoutManager);
                            rv_approval_details.setItemAnimator(new DefaultItemAnimator());
                            rv_approval_details.setAdapter(mAdapter);
                            for (int i = 0; i < responses.size(); i++) {
                                if (responses.get(i).getApprovalBy().equalsIgnoreCase(myPref.getString("Id", "Id"))) {
                                    if (responses.get(i).getStatus()) {
                                        //text edittext anf button (approved and unapproved) hide
                                        et_approve_unapprove.setVisibility(View.GONE);
                                        btn_approve.setVisibility(View.GONE);
                                        btn_un_approve.setVisibility(View.GONE);
                                        tv_approve_unapprove_heading.setVisibility(View.GONE);
                                        et_approve_unapprove.setVisibility(View.GONE);

                                    } else {
                                        //show
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        btn_approve.setVisibility(View.VISIBLE);
                                        btn_un_approve.setVisibility(View.VISIBLE);
                                        tv_approve_unapprove_heading.setVisibility(View.VISIBLE);
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        // for api
                                        approvalLevel_val = responses.get(i).getApprovalLevel();
                                        approvalId_val = String.valueOf(responses.get(i).getApprovalId());
                                    }
                                }
                            }
                            HitGetIAMBStatus(myPref.getString("Id", "Id"), catID, approvalLevel_val);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(@NotNull Call<List<AccessRequestApproverDetailsModel>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitGetAccessRequestProcessorDetailApi(String key, String accessRequestNo, String categoryId) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ARPDModel>> response = anInterface.GetAccessRequestProcessorDetail(key, accessRequestNo, categoryId);
            response.enqueue(new Callback<List<ARPDModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<ARPDModel>> call, @NotNull Response<List<ARPDModel>> response) {
                    dismissProgress();
                    List<ARPDModel> responses = response.body();
                    if (responses != null && responses.size() != 0) {
                        if (responses.get(0).getApprovalId() != null) {
                            tv_processor_detail.setVisibility(View.VISIBLE);
                            tv_scroll.setVisibility(View.VISIBLE);
                            lay_processor_view.setVisibility(View.VISIBLE);
                            ARPDAdapter mAdapter = new ARPDAdapter(response.body(), AccessRequestDetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AccessRequestDetailsActivity.this);
                            rv_processor_details.setLayoutManager(mLayoutManager);
                            rv_processor_details.setItemAnimator(new DefaultItemAnimator());
                            rv_processor_details.setAdapter(mAdapter);
                            for (int i = 0; i < responses.size(); i++) {
                                if (responses.get(i).getApprovalBy().equalsIgnoreCase(myPref.getString("Id", "Id"))) {
                                    if (responses.get(i).getStatus()) {
                                        //text edittext anf button (approved and unapproved) hide
                                        et_approve_unapprove.setVisibility(View.GONE);
                                        btn_approve.setVisibility(View.GONE);
                                        btn_un_approve.setVisibility(View.GONE);
                                        tv_approve_unapprove_heading.setVisibility(View.GONE);
                                        btn_send_back.setVisibility(View.GONE);
                                        et_approve_unapprove.setVisibility(View.GONE);

                                    } else {
                                        //show
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        btn_approve.setVisibility(View.VISIBLE);
                                        btn_un_approve.setVisibility(View.VISIBLE);
                                        tv_approve_unapprove_heading.setVisibility(View.VISIBLE);
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        btn_send_back.setVisibility(View.VISIBLE);
                                        // for api
                                        approvalLevel_val = responses.get(i).getApprovalLevel();
                                        approvalId_val = String.valueOf(responses.get(i).getApprovalId());
                                    }
                                    if (responses.get(i).getApprovalLevel().equalsIgnoreCase("Processor")) {
                                        // change button name
// complete and reject respectivelly
                                        btn_approve.setText("Complete");
                                        btn_un_approve.setText("Reject");
                                        // send back to request
                                        String val = responses.get(i).getApprovalLevel();
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(@NotNull Call<List<ARPDModel>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitGetAccessRequestProcessorDetailExtApi(String key, String accessRequestNo) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ARPDModel>> response = anInterface.GetAccessRequestProcessorDetailExt(key, accessRequestNo);
            response.enqueue(new Callback<List<ARPDModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<ARPDModel>> call, @NotNull Response<List<ARPDModel>> response) {
                    dismissProgress();
                    List<ARPDModel> responses = response.body();

                    if (responses != null && responses.size() != 0) {
                        if (responses.get(0).getApprovalId() != null) {
                            tv_processor_detail.setVisibility(View.VISIBLE);
                            tv_scroll.setVisibility(View.VISIBLE);
                            lay_processor_view.setVisibility(View.VISIBLE);
                            ARPDAdapter mAdapter = new ARPDAdapter(response.body(), AccessRequestDetailsActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AccessRequestDetailsActivity.this);
                            rv_processor_details.setLayoutManager(mLayoutManager);
                            rv_processor_details.setItemAnimator(new DefaultItemAnimator());
                            rv_processor_details.setAdapter(mAdapter);
                            for (int i = 0; i < responses.size(); i++) {
                                if (responses.get(i).getApprovalBy().equalsIgnoreCase(myPref.getString("Id", "Id"))) {
                                    if (responses.get(i).getStatus()) {
                                        //text edittext anf button (approved and unapproved) hide
                                        et_approve_unapprove.setVisibility(View.GONE);
                                        btn_approve.setVisibility(View.GONE);
                                        btn_un_approve.setVisibility(View.GONE);
                                        tv_approve_unapprove_heading.setVisibility(View.GONE);
                                        btn_send_back.setVisibility(View.GONE);
                                        et_approve_unapprove.setVisibility(View.GONE);
                                    } else {
                                        //show
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        btn_approve.setVisibility(View.VISIBLE);
                                        btn_un_approve.setVisibility(View.VISIBLE);
                                        tv_approve_unapprove_heading.setVisibility(View.VISIBLE);
                                        et_approve_unapprove.setVisibility(View.VISIBLE);
                                        btn_send_back.setVisibility(View.VISIBLE);

                                        // for api
                                        approvalLevel_val = responses.get(i).getApprovalLevel();
                                        approvalId_val = String.valueOf(responses.get(i).getApprovalId());

                                    }

                                    if (responses.get(i).getApprovalLevel().equalsIgnoreCase("Processor")) {
                                        // change button name
// complete and reject respectivelly
                                        btn_approve.setText("Complete");
                                        btn_un_approve.setText("Reject");
                                        // send back to request
                                        String val = responses.get(i).getApprovalLevel();
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<ARPDModel>> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }
    //    for IAM Approve
    public void hitIAMApproveApi(String key, String approvalId, String accessRequestNo, String empCode, String approvalText, String attachment, String approvalLevel) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<String> response = anInterface.IAMApprove(key, approvalId, accessRequestNo, empCode, approvalText, attachment, approvalLevel, IAMB);
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                    dismissProgress();
                    String responses = response.body();

                    if (responses != null && responses.equalsIgnoreCase("Request Approved successfully")) {
                        Toast.makeText(AccessRequestDetailsActivity.this, responses, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    //    for IAM Un Approve
    public void hitIAMUnApproveApi(String key, String approvalId, String accessRequestNo, String empCode, String rejectionText, String attachment, String approvalLevel) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<String> response = anInterface.IAMUnApprove(key, approvalId, accessRequestNo, empCode, rejectionText, attachment, approvalLevel);
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                    dismissProgress();
                    String responses = response.body();
                    if (responses != null && responses.equalsIgnoreCase("Request Rejected successfully")) {
                        Toast.makeText(AccessRequestDetailsActivity.this, responses, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

    }


    //    for IAM IAMBackToRequestor
    public void hitIAMBackToRequestorApi(String key, String approvalId, String accessRequestNo, String empCode, String rejectionText, String attachment, String approvalLevel) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<String> response = anInterface.IAMBacktoRequestor(key, approvalId, accessRequestNo, empCode, rejectionText, attachment, approvalLevel);
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                    dismissProgress();
                    String responses = response.body();
                    if (responses != null && responses.equalsIgnoreCase("Request Send Back to Requestor successfully")) {
                        Toast.makeText(AccessRequestDetailsActivity.this, responses, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_approve:
                if (btn_send_back.getVisibility() == View.VISIBLE) {
                    if (!et_approve_unapprove.getText().toString().equalsIgnoreCase("")) {
                        hitIAMApproveApi(RetrofitClient2.CKEY, approvalId_val, tv_access_request_no.getText().toString(), myPref.getString("Id", "Id"), et_approve_unapprove.getText().toString(), "", approvalLevel_val);
                    } else {
                        et_approve_unapprove.setError("Please Fill Rejected Remarks");
                    }
                } else {
                    hitIAMApproveApi(RetrofitClient2.CKEY, approvalId_val, tv_access_request_no.getText().toString(), myPref.getString("Id", "Id"), et_approve_unapprove.getText().toString(), "", approvalLevel_val);
                }
                break;
            case R.id.btn_un_approve:
                if (!et_approve_unapprove.getText().toString().equalsIgnoreCase("")) {
                    hitIAMUnApproveApi(RetrofitClient2.CKEY, approvalId_val, tv_access_request_no.getText().toString().trim(), myPref.getString("Id", "Id"), et_approve_unapprove.getText().toString(), "", approvalLevel_val);

                } else {
                    //Toast.makeText(AccessRequestDetailsActivity.this, "Please Fill Rejected Remarks", Toast.LENGTH_SHORT).show();
                    et_approve_unapprove.setError("Please Fill Rejected Remarks");
                }
                break;

            case R.id.btn_send_back:

                if (!et_approve_unapprove.getText().toString().equalsIgnoreCase("")) {
                    hitIAMBackToRequestorApi(RetrofitClient2.CKEY, approvalId_val, tv_access_request_no.getText().toString().trim(), myPref.getString("Id", "Id"), et_approve_unapprove.getText().toString(), "", approvalLevel_val);

                } else {
                    //Toast.makeText(AccessRequestDetailsActivity.this, "Please Fill Rejected Remarks", Toast.LENGTH_SHORT).show();
                    et_approve_unapprove.setError("Please Fill Rejected Remarks");
                }
                break;
        }
    }

    public void HitGetIAMBStatus(String empcode, String sCategoryId, String sApprovalLevel) {
        if (Utility.isOnline(getApplicationContext())) {
            showProgress();
            Interface anInterface = RetrofitClient2.createIAMBService(Interface.class);
            Call<List<IAMBModel>> response = anInterface.GetIAMBStatus(empcode, sCategoryId, sApprovalLevel);
            response.enqueue(new Callback<List<IAMBModel>>() {
                @Override
                public void onResponse(Call<List<IAMBModel>> call, Response<List<IAMBModel>> response) {
                    dismissProgress();
                    if (response.code() == HttpsURLConnection.HTTP_OK) {
                        if (response != null && response.body().size() > 0) {
                            String status = response.body().get(0).getStatus();
                            if (status != null && status.length() > 0) {
                                IAMB = status;
                                if (IAMB.equalsIgnoreCase("PSA")) {
                                    btn_approve.setText("Yes SOD conflict observed");
                                    btn_un_approve.setText("No SOD conflict observed");
                                    btn_send_back.setVisibility(View.GONE);

                                } else if (IAMB.equalsIgnoreCase("PSV")) {
                                    btn_un_approve.setText("SOD conflict observed, Send Back");
                                    btn_approve.setText("No SOD conflict observed, Approve");
                                    btn_send_back.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<IAMBModel>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }
}
