package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.minda.sparsh.Adapter.IAMGetAccessSubTypeAdapter;
import com.minda.sparsh.Adapter.IAMGetAuthorizationProfileAdapter;
import com.minda.sparsh.Adapter.IAMGetBusinessAdapter;
import com.minda.sparsh.Adapter.IAMGetCategoryAdapter;
import com.minda.sparsh.Adapter.IAMGetDomainAdapter;
import com.minda.sparsh.Adapter.IAMGetPlantAdapter;
import com.minda.sparsh.Adapter.IAMGetRequestTypeAdapter;
import com.minda.sparsh.Adapter.IAMGetSubCategoryAdapter;
import com.minda.sparsh.Adapter.IAMIAMGetAccessTypeAdapter;
import com.minda.sparsh.model.IAMCreateRequestModel;
import com.minda.sparsh.model.IAMGetAccessSubTypeModel;
import com.minda.sparsh.model.IAMGetAccessTypeSpinnerModel;
import com.minda.sparsh.model.IAMGetAuthorizationProfileModel;
import com.minda.sparsh.model.IAMGetBusinessModel;
import com.minda.sparsh.model.IAMGetCategorySpinnerModel;
import com.minda.sparsh.model.IAMGetDomainModel;
import com.minda.sparsh.model.IAMGetPlantModel;
import com.minda.sparsh.model.IAMGetRequestTypeSpinnerModel;

import com.minda.sparsh.model.IAMGetListOfNames;

import com.minda.sparsh.model.IAMGetSubCategoryModel;
import com.minda.sparsh.util.PlantInterface;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForAccessActivity extends AppCompatActivity implements View.OnClickListener, PlantInterface {
    private ProgressDialog progress = null;
    @BindView(R.id.recyclerViewDomain)
    RecyclerView recyclerViewDomain;
    @BindView(R.id.recyclerViewBusiness)
    RecyclerView recyclerViewBusiness;
    @BindView(R.id.recyclerViewPlant)
    RecyclerView recyclerViewPlant;
    @BindView(R.id.sp_request_type)
    Spinner sp_request_type;
    @BindView(R.id.sp_access_type)
    Spinner sp_access_type;
    @BindView(R.id.sp_access_category)
    Spinner sp_access_category;
    @BindView(R.id.sp_access_sub_category)
    Spinner sp_access_sub_category;
    @BindView(R.id.sp_access_sub_type)
    Spinner sp_access_sub_type;
    @BindView(R.id.sp_user_authorization_profile)
    Spinner sp_user_authorization_profile;
    @BindView(R.id.sp_access_for)
    Spinner sp_access_for;
    @BindView(R.id.sp_source)
    Spinner sp_source;
    @BindView(R.id.layAccessSubCategory)
    LinearLayout layAccessSubCategory;
    @BindView(R.id.layUserAuthorisationProfile)
    LinearLayout layUserAuthorisationProfile;
    @BindView(R.id.laySource)
    LinearLayout laySource;
    @BindView(R.id.layNameOrgPur)
    LinearLayout layNameOrgPur;
    @BindView(R.id.layBusiness)
    LinearLayout layBusiness;
    @BindView(R.id.layAccessSubType)
    LinearLayout layAccessSubType;
    @BindView(R.id.layAccessCategory)
    LinearLayout layAccessCategory;
    @BindView(R.id.layMultiAccessCategory)
    LinearLayout layMultiAccessCategory;
    @BindView(R.id.layPlant)
    LinearLayout layPlant;
    @BindView(R.id.et_empCode)
    AutoCompleteTextView et_empCode;
    @BindView(R.id.simpleMultiSpinner)
    MultiSpinnerSearch simpleSpinner;
    private SharedPreferences myPref = null;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    String sp_request_type_id, sp_access_type_id, sp_access_category_id, sp_access_sub_category_id = "0", sp_access_sub_type_id,
            sp_user_authorization_profile_id = "0", sp_access_for_id, sp_source_id, sp_access_category_value, sp_access_sub_category_value = "",
            sp_user_authorization_profile_value = "", catListValue = "", unitCheckId = "";

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_organisation)
    EditText et_organisation;
    @BindView(R.id.et_purpose)
    EditText et_purpose;
    @BindView(R.id.et_accessRequirementDetail)
    EditText et_accessRequirementDetail;
    @BindView(R.id.im_back)
    ImageView im_back;

    HashSet<String> set = new HashSet<String>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_access);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Access Request");

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hitIAMGetRequestTypeApi();
        hitIAMGetAccessTypeApi();
        hitIAMGetDomainApi();
        hitIAMGetListOfNamesApi();
        selectionListener();
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


    private void selectionListener() {
        sp_request_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetRequestTypeSpinnerModel selectedItem = (IAMGetRequestTypeSpinnerModel) adapterView.getSelectedItem();
                if (!selectedItem.getRequestType().equalsIgnoreCase("Please Select Request Type")) {

                    if (selectedItem.getRequestTypeId().equals(3)) {
                        sp_request_type_id = selectedItem.getRequestTypeId().toString();
                        layAccessCategory.setVisibility(View.GONE);
                        layMultiAccessCategory.setVisibility(View.VISIBLE);
                        hiIAMGetCategoryApi(selectedItem.getRequestTypeId().toString(), "3");
                    } else {
                        sp_request_type_id = selectedItem.getRequestTypeId().toString();
                        layAccessCategory.setVisibility(View.VISIBLE);
                        layMultiAccessCategory.setVisibility(View.GONE);
                        hiIAMGetCategoryApi(selectedItem.getRequestTypeId().toString(), "1");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_access_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetAccessTypeSpinnerModel selectedItem = (IAMGetAccessTypeSpinnerModel) adapterView.getSelectedItem();
                if (!selectedItem.getAccessType().equalsIgnoreCase("Please Select Access Type")) {
                    hitIAMGetAccessSubTypeApi(selectedItem.getAccessTypeId().toString());
                    sp_access_type_id = selectedItem.getAccessTypeId().toString();
                }
                if (selectedItem.getAccessTypeId().equals(3)) {
                    layAccessSubType.setVisibility(View.GONE);
                } else {
                    layAccessSubType.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_access_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetCategorySpinnerModel selectedItem = (IAMGetCategorySpinnerModel) adapterView.getSelectedItem();
                if (!selectedItem.getCategory().equalsIgnoreCase("Please Select Category")) {
                    hitIAMGetSubCategoryApi(selectedItem.getCategoryId().toString());
                    hitIAMGetAuthorizationProfileApi(selectedItem.getCategoryId().toString());
                    sp_access_category_id = selectedItem.getCategoryId().toString();
                    sp_access_category_value = selectedItem.getCategory();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_access_sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetSubCategoryModel selectedItem = (IAMGetSubCategoryModel) adapterView.getSelectedItem();
                if (!selectedItem.getSubCategory().equalsIgnoreCase("Please Select Sub Category")) {
                    sp_access_sub_category_id = selectedItem.getSubCategoryId().toString();
                    sp_access_sub_category_value = selectedItem.getSubCategory();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_access_sub_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetAccessSubTypeModel selectedItem = (IAMGetAccessSubTypeModel) adapterView.getSelectedItem();
                if (!selectedItem.getAccessSubType().equalsIgnoreCase("Please Select Access Sub Type")) {
                    sp_access_sub_type_id = selectedItem.getAccessSubTypeId().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_user_authorization_profile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IAMGetAuthorizationProfileModel selectedItem = (IAMGetAuthorizationProfileModel) adapterView.getSelectedItem();
                if (!selectedItem.getProfileName().equalsIgnoreCase("Please Select User Authorization Profile")) {
                    sp_user_authorization_profile_id = selectedItem.getProfileId().toString();
                    sp_user_authorization_profile_value = selectedItem.getProfileName();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_access_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_access_for_id = String.valueOf(sp_access_for.getSelectedItemPosition());
                if (i != 2) {
                    laySource.setVisibility(View.GONE);

                } else {
                    laySource.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_source_id = String.valueOf(sp_source.getSelectedItemPosition());
                if (i == 1) {
                    et_empCode.setVisibility(View.VISIBLE);

                } else {
                    et_empCode.setVisibility(View.GONE);

                }
                if (i == 2) {
                    layNameOrgPur.setVisibility(View.VISIBLE);

                } else {
                    layNameOrgPur.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateField()) {
                    if(sp_source_id==null){
                        sp_source_id = "";
                    }
                    hitIAMCreateRequestApi(sp_request_type_id, sp_access_type_id, sp_access_for_id, myPref.getString("Id", "0"),
                            sp_source_id, et_empCode.getText().toString(), et_organisation.getText().toString(), et_purpose.getText().toString(),
                            et_name.getText().toString(), sp_access_sub_type_id, sp_access_category_id, sp_access_sub_category_id, sp_access_category_value,
                            sp_access_sub_category_value, sp_user_authorization_profile_id, sp_user_authorization_profile_value,
                            et_accessRequirementDetail.getText().toString(), catListValue, unitCheckId);
                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {

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


    public void hitIAMGetRequestTypeApi() {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetRequestTypeSpinnerModel>> response = promotingMyinterface.IAMGetRequestType(RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetRequestTypeSpinnerModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetRequestTypeSpinnerModel>> call, Response<List<IAMGetRequestTypeSpinnerModel>> response) {
                    showProgress(false);
                    List<IAMGetRequestTypeSpinnerModel> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {
                        IAMGetRequestTypeSpinnerModel iamGetRequestTypeSpinnerModel = new IAMGetRequestTypeSpinnerModel();
                        iamGetRequestTypeSpinnerModel.setRequestTypeId(0);
                        iamGetRequestTypeSpinnerModel.setRequestType("Please Select Request Type");
                        responseList.add(0, iamGetRequestTypeSpinnerModel);

                        IAMGetRequestTypeAdapter departmentSpinnerAdapter = new IAMGetRequestTypeAdapter(RequestForAccessActivity.this, responseList);
                        sp_request_type.setAdapter(departmentSpinnerAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetRequestTypeSpinnerModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMGetAccessTypeApi() {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetAccessTypeSpinnerModel>> response = anInterface.IAMGetAccessType(RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetAccessTypeSpinnerModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetAccessTypeSpinnerModel>> call, Response<List<IAMGetAccessTypeSpinnerModel>> response) {
                    showProgress(false);
                    List<IAMGetAccessTypeSpinnerModel> responseList = response.body();

                    IAMGetAccessTypeSpinnerModel iam = new IAMGetAccessTypeSpinnerModel();
                    iam.setAccessTypeId(0);
                    iam.setAccessType("Please Select Access Type");

                    if (responseList != null && responseList.size()>0) {
                        responseList.add(0, iam);
                        IAMIAMGetAccessTypeAdapter mAdapter = new IAMIAMGetAccessTypeAdapter(RequestForAccessActivity.this, responseList);
                        sp_access_type.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetAccessTypeSpinnerModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMGetAccessSubTypeApi(String accessType) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetAccessSubTypeModel>> response = anInterface.IAMGetAccessSubType(accessType, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetAccessSubTypeModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetAccessSubTypeModel>> call, Response<List<IAMGetAccessSubTypeModel>> response) {
                    showProgress(false);
                    if(response.body()!=null && response.body().size()>0) {
                        List<IAMGetAccessSubTypeModel> responseList = response.body();
                        IAMGetAccessSubTypeModel iam = new IAMGetAccessSubTypeModel();
                        iam.setAccessSubTypeId(0);
                        iam.setAccessSubType("Please Select Access Sub Type");
                        responseList.add(0, iam);

                        if (responseList != null && responseList.size() > 0) {
                            IAMGetAccessSubTypeAdapter mAdapter = new IAMGetAccessSubTypeAdapter(RequestForAccessActivity.this, responseList);
                            sp_access_sub_type.setAdapter(mAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetAccessSubTypeModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hiIAMGetCategoryApi(String requestType, final String type) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetCategorySpinnerModel>> response = anInterface.IAMGetCategory(requestType, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetCategorySpinnerModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetCategorySpinnerModel>> call, Response<List<IAMGetCategorySpinnerModel>> response) {
                    showProgress(false);
                    if (response.body()!=null && response.body().size() != 0) {
                        List<IAMGetCategorySpinnerModel> responseList = null;
                        responseList = response.body();
                        if (!type.equalsIgnoreCase("3")) {
                            IAMGetCategorySpinnerModel iam = new IAMGetCategorySpinnerModel();
                            iam.setCategoryId(0);
                            iam.setCategory("Please Select Category");
                            responseList.add(0, iam);

                            IAMGetCategoryAdapter mAdapter = new IAMGetCategoryAdapter(RequestForAccessActivity.this, responseList);
                            sp_access_category.setAdapter(mAdapter);
                        } else {
                            final List<KeyPairBoolData> listArray = new ArrayList<KeyPairBoolData>();

                            for (int i = 0; i < responseList.size(); i++) {
                                KeyPairBoolData h = new KeyPairBoolData();
                                h.setId(responseList.get(i).getCategoryId());
                                h.setName(responseList.get(i).getCategory());
                                h.setSelected(false);
                                listArray.add(h);
                            }

                         /*   simpleSpinner.setItems(listArray, -1, new MultiSpinnerListener() {

                                @Override
                                public void onItemsSelected(List<KeyPairBoolData> items) {
                                    catListValue = "";
                                    for (int i = 0; i < items.size(); i++) {
                                        if (items.get(i).isSelected()) {
                                            catListValue = catListValue + "," + items.get(i).getId();
                                            Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                        }
                                    }
                                }
                            });
   */                     }
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetCategorySpinnerModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMGetSubCategoryApi(String categoryId) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetSubCategoryModel>> response = anInterface.IAMGetSubCategory(categoryId, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetSubCategoryModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetSubCategoryModel>> call, Response<List<IAMGetSubCategoryModel>> response) {
                    showProgress(false);
                    if (response.body()!=null && response.body().size() != 0) {
                        List<IAMGetSubCategoryModel> responseList = response.body();
                        IAMGetSubCategoryModel iam = new IAMGetSubCategoryModel();
                        iam.setCategoryId(0);
                        iam.setSubCategory("Please Select Sub Category");
                        responseList.add(0, iam);

                        IAMGetSubCategoryAdapter mAdapter = new IAMGetSubCategoryAdapter(RequestForAccessActivity.this, responseList);
                        sp_access_sub_category.setAdapter(mAdapter);
                        layAccessSubCategory.setVisibility(View.VISIBLE);

                    } else {
                        layAccessSubCategory.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetSubCategoryModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMGetAuthorizationProfileApi(String categoryId) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetAuthorizationProfileModel>> response = anInterface.IAMGetAuthorizationProfile(categoryId, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetAuthorizationProfileModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetAuthorizationProfileModel>> call, Response<List<IAMGetAuthorizationProfileModel>> response) {
                    showProgress(false);
                    if (response.body()!=null && response.body().size() != 0) {
                        List<IAMGetAuthorizationProfileModel> responseList = response.body();
                        IAMGetAuthorizationProfileModel iam = new IAMGetAuthorizationProfileModel();
                        iam.setCategoryId(0);
                        iam.setProfileName("Please Select User Authorization Profile");
                        responseList.add(0, iam);

                        IAMGetAuthorizationProfileAdapter mAdapter = new IAMGetAuthorizationProfileAdapter(RequestForAccessActivity.this, responseList);
                        sp_user_authorization_profile.setAdapter(mAdapter);
                        layUserAuthorisationProfile.setVisibility(View.VISIBLE);

                    } else {
                        layUserAuthorisationProfile.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetAuthorizationProfileModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMGetDomainApi() {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetDomainModel>> response = anInterface.IAMGetDomain(RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetDomainModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetDomainModel>> call, Response<List<IAMGetDomainModel>> response) {
                    showProgress(false);
                    List<IAMGetDomainModel> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {
                        IAMGetDomainAdapter mAdapter = new IAMGetDomainAdapter(responseList, RequestForAccessActivity.this, myPref.getString("UM_DESIG_CODE", "0"));
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerViewDomain.setLayoutManager(gridLayoutManager);
                        recyclerViewDomain.setAdapter(mAdapter);

                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetDomainModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    List<IAMGetBusinessModel> combineList = new ArrayList<>();

    public void hitIAMGetBusinessApi(String domainId, final String callFrom) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetBusinessModel>> response = anInterface.IAMGetBusiness(domainId, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetBusinessModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetBusinessModel>> call, Response<List<IAMGetBusinessModel>> response) {
                    showProgress(false);
                    List<IAMGetBusinessModel> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {
                        if (callFrom.equalsIgnoreCase("checkBox")) {
                            combineList.addAll(responseList);
                        } else {
                            combineList.clear();
                            combineList.addAll(responseList);
                        }

                        layBusiness.setVisibility(View.VISIBLE);
                        IAMGetBusinessAdapter mAdapter = new IAMGetBusinessAdapter(combineList, RequestForAccessActivity.this, myPref.getString("UM_DESIG_CODE", "0"));
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerViewBusiness.setLayoutManager(gridLayoutManager);
                        recyclerViewBusiness.setAdapter(mAdapter);

                    }


                }

                @Override
                public void onFailure(Call<List<IAMGetBusinessModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    List<IAMGetPlantModel> combineListPlant = new ArrayList<>();

    public void hitIAMGetPlantApi(String businessId, final String callType) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetPlantModel>> response = anInterface.IAMGetPlant(businessId, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetPlantModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetPlantModel>> call, Response<List<IAMGetPlantModel>> response) {
                    showProgress(false);
                    List<IAMGetPlantModel> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {

                        if (callType.equalsIgnoreCase("checkBox")) {
                            combineListPlant.addAll(responseList);
                        } else {
                            combineListPlant.clear();
                            combineListPlant.addAll(responseList);
                        }
                        layPlant.setVisibility(View.VISIBLE);
                        IAMGetPlantAdapter mAdapter = new IAMGetPlantAdapter(responseList, RequestForAccessActivity.this, RequestForAccessActivity.this);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerViewPlant.setLayoutManager(gridLayoutManager);
                        recyclerViewPlant.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<IAMGetPlantModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitIAMGetListOfNamesApi() {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetListOfNames>> response = anInterface.IAMGetListofNames("", RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetListOfNames>>() {
                @Override
                public void onResponse(Call<List<IAMGetListOfNames>> call, Response<List<IAMGetListOfNames>> response) {
                    showProgress(false);
                    List<IAMGetListOfNames> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {
                        call(responseList);
                    }
                }


                @Override
                public void onFailure(Call<List<IAMGetListOfNames>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void hitIAMCreateRequestApi(String RequestTypeId, String AccessTypeId, String AccessForTypeId, String EmpCode, String SourceTypeId, String SourceEmpCode, String Organization, String Purpose, String SourceName, String AccessSubTypeId, String CategoryId, String SubCategoryId, String CategoryName, String SubCategoryName, String ProfileId, String ProfileName, String RequirementDetail, String CategoryList, String UnitList) {
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMCreateRequestModel>> response = anInterface.IAMCreateRequest(RequestTypeId, AccessTypeId, AccessForTypeId, EmpCode, SourceTypeId, SourceEmpCode, Organization, Purpose, SourceName, AccessSubTypeId, CategoryId, SubCategoryId, CategoryName, SubCategoryName, ProfileId, ProfileName, RequirementDetail, CategoryList, UnitList, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMCreateRequestModel>>() {
                @Override
                public void onResponse(Call<List<IAMCreateRequestModel>> call, Response<List<IAMCreateRequestModel>> response) {
                    showProgress(false);
                    List<IAMCreateRequestModel> responseList = response.body();
                    if (responseList != null && responseList.size()>0) {
                        Toast.makeText(RequestForAccessActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<List<IAMCreateRequestModel>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(RequestForAccessActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    private void call(List<IAMGetListOfNames> responseList) {
        String[] values = new String[responseList.size()];
        for (int i = 0; i < responseList.size(); i++) {
            values[i] = responseList.get(i).getEMP();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, values);

        et_empCode.setThreshold(1);//will start working from first character
        et_empCode.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }


    boolean validateField() {
        if (sp_request_type.getSelectedItemPosition() == 0 && sp_request_type.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Request Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_access_type.getSelectedItemPosition() == 0 && sp_access_type.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Access Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_access_category.getSelectedItemPosition() == 0 && sp_access_category.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_access_sub_category.getSelectedItemPosition() == 0 && sp_access_sub_category.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Sub Category", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_access_sub_type.getSelectedItemPosition() == 0 && sp_access_sub_type.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Access Sub Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_user_authorization_profile.getSelectedItemPosition() == 0 && layUserAuthorisationProfile.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select User Authorization Profile", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_access_for.getSelectedItemPosition() == 0 && sp_access_for.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Access For", Toast.LENGTH_SHORT).show();
            return false;
        } else if (sp_source.getSelectedItemPosition() == 0 && laySource.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Select Source", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_empCode.getText().toString()) && et_empCode.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Fill Employee Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_name.getText().toString()) && layNameOrgPur.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Fill Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_organisation.getText().toString()) && layNameOrgPur.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Fill Organisation", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_purpose.getText().toString()) && layNameOrgPur.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Fill Purpose", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et_accessRequirementDetail.getText().toString()) && et_accessRequirementDetail.getVisibility() == View.VISIBLE) {
            Toast.makeText(RequestForAccessActivity.this, "Please Fill Access Requirement Details", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    @Override
    public void handleClick(String id, String clickCheck) {
        unitCheckId = "";
        if (clickCheck.equalsIgnoreCase("check")) {
            set.add(id);
        } else {
            set.remove(id);
        }
//        // allocate memory for string array
//        String[] array = new String[set.size()];
//
//        // copy elements from set to string array
//        int i = 0;
//        for (String s: set)
//            array[i++] = s;

//      unitCheckId=Arrays.toString(array);

        try {
            unitCheckId = StringUtil.join(set, ", ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
