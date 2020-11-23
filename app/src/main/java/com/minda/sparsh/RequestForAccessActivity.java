package com.minda.sparsh;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
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
import com.minda.sparsh.model.CategoryAbnormality;
import com.minda.sparsh.model.IAMCreateRequestModel;
import com.minda.sparsh.model.IAMGetAccessSubTypeModel;
import com.minda.sparsh.model.IAMGetAccessTypeSpinnerModel;
import com.minda.sparsh.model.IAMGetAuthorizationProfileModel;
import com.minda.sparsh.model.IAMGetBusinessModel;
import com.minda.sparsh.model.IAMGetCategorySpinnerModel;
import com.minda.sparsh.model.IAMGetDomainModel;
import com.minda.sparsh.model.IAMGetListOfNames;
import com.minda.sparsh.model.IAMGetPlantModel;
import com.minda.sparsh.model.IAMGetRequestTypeSpinnerModel;
import com.minda.sparsh.model.IAMGetSubCategoryModel;
import com.minda.sparsh.util.PlantInterface;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import org.jsoup.helper.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForAccessActivity extends AppCompatActivity implements View.OnClickListener, PlantInterface {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;
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
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    ArrayList<String> catlistarray = new ArrayList<String>();
    String sp_request_type_id, sp_access_type_id, sp_access_category_id ="0", sp_access_sub_category_id = "0", sp_access_sub_type_id,
            sp_user_authorization_profile_id = "0", sp_access_for_id, sp_source_id, sp_access_category_value="0", sp_access_sub_category_value = "",
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
    @BindView(R.id.attachment1)
    ImageView attachment;
    @BindView(R.id.attachtext1)
    TextView attachtext;
    @BindView(R.id.doc_view)
    ImageView docView;
    String fileName = "", fileType, fileByte = "";
    byte[] bytes;
    Bitmap bmp;
    List<IAMGetBusinessModel> combineList = new ArrayList<>();
    List<IAMGetPlantModel> combineListPlant = new ArrayList<>();
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    private String mUserChoosenTask = "";
    private File mDestinationFile;

    String TYPE ;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_access);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.access_req));

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

    @OnClick(R.id.attachtext1)
    public void onClickAttachText() {
        selectFile();

    }

    @OnClick(R.id.attachment1)
    public void onClickAttachment() {
        selectFile();
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
                    if (sp_source_id == null) {
                        sp_source_id = "";
                    }
                    hitIAMCreateRequestApi(sp_request_type_id, sp_access_type_id, sp_access_for_id, myPref.getString("Id", "0"),
                            sp_source_id, et_empCode.getText().toString(), et_organisation.getText().toString(), et_purpose.getText().toString(),
                            et_name.getText().toString(), sp_access_sub_type_id, sp_access_category_id, sp_access_sub_category_id, sp_access_category_value,
                            sp_access_sub_category_value, sp_user_authorization_profile_id, sp_user_authorization_profile_value,
                            et_accessRequirementDetail.getText().toString(), catlistarray.toString().replace("[","").replace("]",""), unitCheckId);
                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
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
                    if (responseList != null && responseList.size() > 0) {
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

                    if (responseList != null && responseList.size() > 0) {
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
                    if (response.body() != null && response.body().size() > 0) {
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
       TYPE = type;
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetCategorySpinnerModel>> response = anInterface.IAMGetCategory(requestType, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetCategorySpinnerModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetCategorySpinnerModel>> call, Response<List<IAMGetCategorySpinnerModel>> response) {
                    showProgress(false);
                    if (response.body() != null && response.body().size() != 0) {
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
                            simpleSpinner.setItems(listArray, new MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                                    catListValue = "";
                                    catlistarray.clear();
                                    for (int i = 0; i < selectedItems.size(); i++) {
                                        if (selectedItems.get(i).isSelected()) {
                                            catlistarray.add(String.valueOf(selectedItems.get(i).getId()));
                                            catListValue +=  selectedItems.get(i).getId()+", ";
                                            Log.i("TAG", i + " : " + selectedItems.get(i).getName() + " : " + selectedItems.get(i).isSelected());
                                        }

                                    }
                                }
                            });

                        }
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
                    if (response.body() != null && response.body().size() != 0) {
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
                    if (response.body() != null && response.body().size() != 0) {
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
                    if (responseList != null && responseList.size() > 0) {
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

    public void hitIAMGetBusinessApi(String domainId, final String callFrom) {
        layPlant.setVisibility(View.GONE);
        if (Utility.isOnline(RequestForAccessActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<IAMGetBusinessModel>> response = anInterface.IAMGetBusiness(domainId, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<IAMGetBusinessModel>>() {
                @Override
                public void onResponse(Call<List<IAMGetBusinessModel>> call, Response<List<IAMGetBusinessModel>> response) {
                    showProgress(false);
                    List<IAMGetBusinessModel> responseList = response.body();
                    if (responseList != null && responseList.size() > 0) {
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
                    if (responseList != null && responseList.size() > 0) {
                        if (callType.equalsIgnoreCase("checkBox")) {
                            combineListPlant.addAll(responseList);
                        } else {
                            combineListPlant.clear();
                            combineListPlant.addAll(responseList);
                        }
                        layPlant.setVisibility(View.VISIBLE);
                        IAMGetPlantAdapter mAdapter = new IAMGetPlantAdapter(responseList, RequestForAccessActivity.this, RequestForAccessActivity.this);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        recyclerViewPlant.setLayoutManager
                                (gridLayoutManager);
                        recyclerViewPlant.setAdapter(mAdapter);
                    }
                    else{
                        layPlant.setVisibility(View.GONE);
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
                    if (responseList != null && responseList.size() > 0) {
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
            Call<List<IAMCreateRequestModel>> response = anInterface.IAMCreateRequest(RequestTypeId, AccessTypeId, AccessForTypeId, EmpCode, SourceTypeId, SourceEmpCode, Organization, Purpose, SourceName, AccessSubTypeId, CategoryId, SubCategoryId, CategoryName, SubCategoryName, ProfileId, ProfileName, RequirementDetail, CategoryList, UnitList, RetrofitClient2.CKEY, fileName, fileByte);
            response.enqueue(new Callback<List<IAMCreateRequestModel>>() {
                @Override
                public void onResponse(Call<List<IAMCreateRequestModel>> call, Response<List<IAMCreateRequestModel>> response) {
                    showProgress(false);
                    List<IAMCreateRequestModel> responseList = response.body();
                    if (responseList != null && responseList.size() > 0) {

                        if (responseList.get(0).getColumn2() != null) {
                            showMsg(responseList.get(0).getColumn2());
                        }
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
        }

        else if ( TYPE!=null && TYPE!="3" && sp_access_category.getSelectedItemPosition() == 0 && sp_access_category.getVisibility() == View.VISIBLE) {
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

        ActivityCompat.requestPermissions(RequestForAccessActivity.this,
                new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_FROM_CAMERA);
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Choose Document",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestForAccessActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RequestForAccessActivity.this);
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

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        bytes = getBytesFromBitmap(thumbnail);
        fileType = "jpg";
        bmp = thumbnail;
        attachtext.setText(fileName);
        this.fileName = fileName;
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        docView.setVisibility(View.VISIBLE);
        docView.setImageBitmap(thumbnail);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (mDestinationFile != null) {
            mDestinationFile.delete();
        }
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(RequestForAccessActivity.this, bm, Uri.parse(mDestinationFile.toString()));
        //  docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        this.fileName = fileName;
        System.out.println("fileName" + fileName);
        fileType = "jpg";
        bytes = getBytesFromBitmap(bm);
        bmp = bm;
        attachtext.setText(fileName);
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
        docView.setVisibility(View.VISIBLE);
        docView.setImageBitmap(bm);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {

        Uri fileUri = data.getData();
        String mimeType = getContentResolver().getType(fileUri);

        String fullFilePath = UriUtils.getPathFromUri(RequestForAccessActivity.this, fileUri);
        File file = new File(fullFilePath);
        fileName = file.getName();
        fileType = mimeType.replace("application/", "");
        attachtext.setText(fileName);
        bytes = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes); //read file into bytes[]
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(RequestForAccessActivity.this,
                new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(RequestForAccessActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(RequestForAccessActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(RequestForAccessActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    public void showMsg(String reqno) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Request raised successfully.\n" +
                "Access Request No. is "+reqno);


        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                finish();

            }
        });

        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
