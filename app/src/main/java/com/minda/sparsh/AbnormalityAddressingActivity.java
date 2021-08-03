package com.minda.sparsh;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.AbnormalityAdapter;
import com.minda.sparsh.Adapter.AbnormalityCategoryAdapter;
import com.minda.sparsh.Adapter.BusinessSpinnerAdapter;
import com.minda.sparsh.Adapter.DepartmentSpinnerAdapter;
import com.minda.sparsh.Adapter.DomainSpinnerAdapter;
import com.minda.sparsh.Adapter.GroupSpinnerAdapter;
import com.minda.sparsh.Adapter.PlantSpinnerAdapter;
import com.minda.sparsh.model.AbnormalityView_Model;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.model.Business_Model;
import com.minda.sparsh.model.CategoryAbnormality;
import com.minda.sparsh.model.Department_Model;
import com.minda.sparsh.model.Domain_Model;
import com.minda.sparsh.model.Group_Model;
import com.minda.sparsh.model.Plant_Model;
import com.minda.sparsh.model.Sub_Department_Model;
import com.minda.sparsh.model.UserDetail_Model;
import com.minda.sparsh.util.AbnormalityDashboard;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class AbnormalityAddressingActivity extends AppCompatActivity {
    ListView list_abnormalty;
    LinearLayout lay_two, lay_one, lay_out, footer;
    TextView tv_view, tv_add, tv_submit, et_finddate, tv_upload;
    ImageView Im_capture, im_back;
    Uri picUri = null;
    Bitmap myBitmap;
    String sImage = "", base64String;
    int mYear, mMonth, mDay;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    boolean pic_uploaded = false;
    private ProgressDialog progress = null;
    Spinner sp_department, sp_domain, sp_group, sp_business, sp_plant, sp_sdepartment, sp_category;
    EditText et_descripton;
    SharedPreferences myPref;
    String group = "UNO Minda Group", domain = "Select Domain", business, plant = "", department = "Select Department", imagepath, description, benefits, abnormalitydate, domainid = "", businessid = "";
    public String BUSINESS = "", DOMAIN = "", UNITCODE = "", plantid = "", PLANTROLE = "";
    public int sub_department, orientation;
    public static String Role;
    List<Domain_Model> Domainresponse;
    List<Business_Model> Businessresponse;
    List<Plant_Model> Plantresponse;
    List<Department_Model> Departmentresponse = new ArrayList<Department_Model>();
    List<Sub_Department_Model> Subdepartmentresponse = new ArrayList<Sub_Department_Model>();
    Toolbar toolbar;
    TextView title;
    String empCode;

    ProgressBar progressBar;
    int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_addressing);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Abnormality");
        lay_two = (LinearLayout) findViewById(R.id.lay_two);
        lay_one = (LinearLayout) findViewById(R.id.lay_one);
        lay_out = (LinearLayout) findViewById(R.id.lay_out);
        list_abnormalty = (ListView) findViewById(R.id.list_abnormalty);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        tv_view = (TextView) findViewById(R.id.tv_view);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_upload = (TextView) findViewById(R.id.tv_upload);
        Im_capture = (ImageView) findViewById(R.id.Im_capture);
        sp_department = (Spinner) findViewById(R.id.sp_department);
        sp_domain = (Spinner) findViewById(R.id.sp_domain);
//        sp_group = (Spinner) findViewById(R.id.sp_group);
        sp_business = (Spinner) findViewById(R.id.sp_business);
        sp_sdepartment = (Spinner) findViewById(R.id.sp_sdepartment);
        sp_category = (Spinner) findViewById(R.id.sp_category);
        sp_plant = (Spinner) findViewById(R.id.sp_plant);
        et_finddate = (TextView) findViewById(R.id.et_finddate);
        et_descripton = (EditText) findViewById(R.id.et_descripton);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        im_back = (ImageView) findViewById(R.id.im_back);
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        if(getIntent().getStringExtra("EDOMAIN") !=null && getIntent().getStringExtra("EDOMAIN").length()>0){
            domainid = getIntent().getStringExtra("EDOMAIN");
        }

        else if(getIntent().getStringExtra("EBUSINESS")!=null && getIntent().getStringExtra("EBUSINESS").length()>0){
            businessid = getIntent().getStringExtra("EBUSINESS");

        }
        else {
            plantid = getIntent().getStringExtra("EPLANT");

        }
        hitCategoryApi();
        hitPlantApi(empCode);

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent().getExtras() != null) {
            if (getIntent().getBooleanExtra("ADD", false)) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                checkorentation();
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                checkorentation();
            }
        }


//        if (DashBoardActivity.time.equals(1)) {
//            showdialog();
//            DashBoardActivity.time = 2;
//        } else {
//            checkorentation();
//        }

        String date = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date());
        et_finddate.setText(date);
        sp_domain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Domain_Model selectedItem = (Domain_Model) adapterView.getSelectedItem();
                domain = selectedItem.getDomainName();
                domainid = String.valueOf(selectedItem.getDomainID());
                if (!domain.equalsIgnoreCase("Select Domain")) {
                    hitBusinessApi(String.valueOf(selectedItem.getDomainID()));
                } else {
                    List<Business_Model> list = new ArrayList<>();
                    Business_Model business_Model = new Business_Model();
                    business_Model.setBUSINESS("Select Business");
                    business_Model.setID(0);
                    list.add(business_Model);
                    BusinessSpinnerAdapter departmentSpinnerAdapter = new BusinessSpinnerAdapter(AbnormalityAddressingActivity.this, list);
                    sp_business.setAdapter(departmentSpinnerAdapter);
                }

//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategoryAbnormality categoryAbnormality = (CategoryAbnormality) parent.getSelectedItem();
                category_id = categoryAbnormality.getId();
                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_business.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Business_Model selectedItem = (Business_Model) adapterView.getSelectedItem();
                business = selectedItem.getBUSINESS();
                businessid = String.valueOf(selectedItem.getID());

                if (!business.equalsIgnoreCase("Select Business")) {
                    hitPlantApi(String.valueOf(selectedItem.getID()));
                } else {
                    List<Plant_Model> list = new ArrayList<>();
                    Plant_Model plant_model = new Plant_Model();
                    plant_model.setUnitCode("0");
                    plant_model.setUnitName("Select Plant");
                    list.add(0, plant_model);
                    PlantSpinnerAdapter departmentSpinnerAdapter = new PlantSpinnerAdapter(AbnormalityAddressingActivity.this, list);
                    sp_plant.setAdapter(departmentSpinnerAdapter);
                }


//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);
//


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_sdepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Sub_Department_Model selectedItem = (Sub_Department_Model) adapterView.getSelectedItem();
                sub_department = selectedItem.getID();
//                if (!selectedItem.getDEPARTMENTDETAIL().equalsIgnoreCase("Select SubDepartment")) {
//                    hitDepartmentApi(sub_department);

//                }
// else {
//                    List<Department_Model> list = new ArrayList<>();
//                    Department_Model department_model = new Department_Model();
//                    department_model.setDEPARTMENT("Select Department");
//                    list.add(0, department_model);
//                    GroupSpinnerAdapter departmentSpinnerAdapter = new GroupSpinnerAdapter(AbnormalityAddressingActivity.this, list);
//                    sp_sdepartment.setAdapter(departmentSpinnerAdapter);
//
//
//
//                }

//                if (orientation != Configuration.ORIENTATION_PORTRAIT) {
//                    hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);
//                }
//
//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);
//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), businessid, domainid);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Department_Model selectedItem = (Department_Model) adapterView.getSelectedItem();
                department = selectedItem.getDEPARTMENT();
                if (!selectedItem.getDEPARTMENT().equalsIgnoreCase("Select Department")) {
//                    hitDepartmentApi(sub_department);
                    hitSubdepartmentApi(selectedItem.getID());
                } else {
                    List<Sub_Department_Model> list = new ArrayList<>();
                    Sub_Department_Model sub_department_model = new Sub_Department_Model();
                    sub_department_model.setDEPARTMENTDETAIL("Select SubDepartment");
                    sub_department_model.setID(0);
                    list.add(0, sub_department_model);
                    GroupSpinnerAdapter departmentSpinnerAdapter = new GroupSpinnerAdapter(AbnormalityAddressingActivity.this, list);
                    sp_sdepartment.setAdapter(departmentSpinnerAdapter);
                }
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_plant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Plant_Model selectedItem = (Plant_Model) adapterView.getSelectedItem();
                plant = selectedItem.getUnitName();
                plantid = selectedItem.getUnitCode();
                if (!plant.equalsIgnoreCase("Select Plant")) {
//                    hitSubdepartmentApi();
                    hitDepartmentApi();
                } else {
                    List<Department_Model> list = new ArrayList<>();
                    Department_Model department_model = new Department_Model();
                    department_model.setID("0");
                    department_model.setDEPARTMENT("Select Department");
                    list.add(0, department_model);
                    DepartmentSpinnerAdapter departmentSpinnerAdapter = new DepartmentSpinnerAdapter(AbnormalityAddressingActivity.this, list);
                    sp_department.setAdapter(departmentSpinnerAdapter);


                }
//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        sp_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Group_Model selectedItem = (Group_Model) adapterView.getSelectedItem();
//                group = selectedItem.getGroupName();
//                if (!group.equalsIgnoreCase("Select Group")) {
//                    hitDomainApi();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        tv_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                    finish();
                    Intent intent = new Intent(AbnormalityAddressingActivity.this, AbnormalityAddressingActivity.class);
                    intent.putExtra("EDOMAIN", getIntent().getStringExtra("EDOMAIN"));
                    intent.putExtra("EBUSINESS", getIntent().getStringExtra("EBUSINESS"));
                    intent.putExtra("EPLANT", getIntent().getStringExtra("EPLANT"));
                    intent.putExtra("ADD", true);
                    startActivity(intent);
                }
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                Intent intent = new Intent(AbnormalityAddressingActivity.this,)


            }
        });
        tv_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    finish();
                    Intent intent = new Intent(AbnormalityAddressingActivity.this, AbnormalityDashboard.class);
//                    intent.putExtra("ADD", false);
                    intent.putExtra("EDOMAIN", getIntent().getStringExtra("EDOMAIN"));
                    intent.putExtra("EBUSINESS", getIntent().getStringExtra("EBUSINESS"));
                    intent.putExtra("EPLANT", getIntent().getStringExtra("EPLANT"));
                    startActivity(intent);
                }
            }
        });
        Im_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.checkPermission(AbnormalityAddressingActivity.this)) {
                    startActivityForResult(getPickImageChooserIntent(), 200);
                    permissions.add(CAMERA);
                    permissionsToRequest = findUnAskedPermissions(permissions);
                    //get the permissions we have asked for before but are not granted..
                    //we will store this in a global AbnormalityAddressingActivitylist to access later.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (permissionsToRequest.size() > 0)
                            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                    }
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = et_descripton.getText().toString();
                abnormalitydate = et_finddate.getText().toString();
                benefits = "";
                et_descripton.setText("");
                tv_submit.setEnabled(false);
                if (isvalid()) {
                    hitAddAbnormalityApi(group, domainid, businessid, plantid, String.valueOf(sub_department), sImage, description, benefits, abnormalitydate, myPref.getString("Id", ""), category_id);
                }
            }
        });

    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
//            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
            outputFileUri = FileProvider.getUriForFile(AbnormalityAddressingActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application Please allow access",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Im_capture.callOnClick();
                } else {
                    //code for deny
                }
                break;

        }

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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            Im_capture.setVisibility(View.VISIBLE);
            pic_uploaded = true;


            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);

                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
//                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);

                    Im_capture.setImageURI(picUri);
                    Im_capture.setImageBitmap(myBitmap);
                    sImage = getStringImage(myBitmap);
                    tv_upload.setText("Image Upload Successfully");
                   /* if (AppUtils.isNetworkAvailable(SuggestUsActivity.this)) {

                        try{
                            submitWorkDetails(sessionManager.getShopName(), sessionManager.getWorkshpName(), sessionManager.getLocation(), sessionManager.getStateCode(), sessionManager.getCityCode(), sessionManager.getMobile(), sessionManager.getZoneCode());
                        }catch (Exception e) {
                            Crashlytics.logException(e);
                            Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            // handle your exception here!
                        }

                    }*/


                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;

                Im_capture.setImageURI(picUri);
                Im_capture.setImageBitmap(myBitmap);


                sImage = getStringImage(myBitmap);
                tv_upload.setText("Image Upload Successfully");

               /* if (connectionDetector.isConnectingToInternet()) {
                    submitWorkDetails(sessionManager.getShopName(), sessionManager.getWorkshpName(), sessionManager.getLocation(), sessionManager.getStateCode(), sessionManager.getCityCode(), sessionManager.getMobile(), sessionManager.getZoneCode());


                }
*/


            }

        }

    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public void hitGroupApi() {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Group_Model>> response = promotingMyinterface.GetGroup();
            response.enqueue(new Callback<List<Group_Model>>() {
                @Override
                public void onResponse(Call<List<Group_Model>> call, Response<List<Group_Model>> response) {
                    showProgress(false);
                    List<Group_Model> Groupresponse = response.body();
                    Group_Model group_model = new Group_Model();
                    group_model.setGID(0);
                    group_model.setGroupName("Select Group");
                    group_model.setIsActive(false);
                    Groupresponse.add(0, group_model);
                    if (Groupresponse != null) {
//                        GroupSpinnerAdapter departmentSpinnerAdapter = new GroupSpinnerAdapter(AbnormalityAddressingActivity.this, Groupresponse);
//                        sp_group.setAdapter(departmentSpinnerAdapter);


                    }

                }

                @Override
                public void onFailure(Call<List<Group_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean b) {
     /*   try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
   */
    }

    public void hitDomainApi() {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Domain_Model>> response = promotingMyinterface.GetDomain(RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<Domain_Model>>() {
                @Override
                public void onResponse(Call<List<Domain_Model>> call, Response<List<Domain_Model>> response) {
                    showProgress(false);
                    Domainresponse = response.body();
                    Domain_Model domain_model = new Domain_Model();
                    domain_model.setDomainName("Select Domain");
                    domain_model.setDomainID(0);
                    Domainresponse.add(0, domain_model);

                    if (Domainresponse != null) {
                        DomainSpinnerAdapter departmentSpinnerAdapter = new DomainSpinnerAdapter(AbnormalityAddressingActivity.this, Domainresponse);
                        sp_domain.setAdapter(departmentSpinnerAdapter);

                        if (!PLANTROLE.equalsIgnoreCase("All Domain Head")) {
                            for (int i = 0; i < Domainresponse.size(); i++) {
                                if (DOMAIN.equalsIgnoreCase(String.valueOf(Domainresponse.get(i).getDomainID()))) {
                                    sp_domain.setSelection(i);
                                    sp_domain.setEnabled(false);
                                    break;
                                }
                            }
                        }


                    }

                }

                @Override
                public void onFailure(Call<List<Domain_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitBusinessApi(String Domain) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Business_Model>> response = promotingMyinterface.GetBusiness(RetrofitClient2.CKEY, Domain);
            response.enqueue(new Callback<List<Business_Model>>() {
                @Override
                public void onResponse(Call<List<Business_Model>> call, Response<List<Business_Model>> response) {
                    showProgress(false);
                    Businessresponse = response.body();
                    Business_Model business_Model = new Business_Model();
                    business_Model.setBUSINESS("Select Business");
                    business_Model.setID(0);
                    Businessresponse.add(0, business_Model);


                    if (Businessresponse != null) {
                        BusinessSpinnerAdapter departmentSpinnerAdapter = new BusinessSpinnerAdapter(AbnormalityAddressingActivity.this, Businessresponse);
                        sp_business.setAdapter(departmentSpinnerAdapter);

                        if (!PLANTROLE.equalsIgnoreCase("All Domain Head") || !PLANTROLE.equalsIgnoreCase("Domain Head")) {
                            for (int i = 0; i < Businessresponse.size(); i++) {
                                if (BUSINESS.equalsIgnoreCase(String.valueOf(Businessresponse.get(i).getID()))) {
                                    sp_business.setSelection(i);
                                    sp_business.setEnabled(false);
                                    break;
                                }
                            }
                        }


                    }

                }

                @Override
                public void onFailure(Call<List<Business_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitPlantApi(String Business) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Plant_Model>> response = promotingMyinterface.GetPlant(RetrofitClient2.CKEY, empCode);
            response.enqueue(new Callback<List<Plant_Model>>() {
                @Override
                public void onResponse(Call<List<Plant_Model>> call, Response<List<Plant_Model>> response) {
                    try {
                        showProgress(false);
                        Plantresponse = response.body();
                        Plant_Model plant_model = new Plant_Model();
                        plant_model.setUnitCode("0");
                        plant_model.setUnitName("Select Plant");
                        Plantresponse.add(0, plant_model);

                        if (Plantresponse != null) {
                            PlantSpinnerAdapter departmentSpinnerAdapter = new PlantSpinnerAdapter(AbnormalityAddressingActivity.this, Plantresponse);
                            sp_plant.setAdapter(departmentSpinnerAdapter);

                            for (int i = 0; i < Plantresponse.size(); i++) {
                                if (UNITCODE.equalsIgnoreCase(Plantresponse.get(i).getUnitCode())) {
                                    sp_plant.setSelection(i);
                                    if (PLANTROLE.equalsIgnoreCase("Best Coordinator") || PLANTROLE.equalsIgnoreCase("Plant Head")) {
                                        sp_plant.setEnabled(false);
                                    } else {
                                        sp_plant.setEnabled(true);
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<List<Plant_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitDepartmentApi() {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Department_Model>> response = promotingMyinterface.GetDepartment(RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<Department_Model>>() {
                @Override
                public void onResponse(Call<List<Department_Model>> call, Response<List<Department_Model>> response) {
                    showProgress(false);
                    Departmentresponse.clear();
                    Department_Model department_model = new Department_Model();
                    department_model.setID("0");
                    department_model.setDEPARTMENT("Select Department");
                    Departmentresponse.add(0, department_model);
                    Departmentresponse.addAll(response.body());
//                    Department_Model department_model=new Department_Model();
//                    department_model.setDEPARTMENT("Select Department");
//                    Departmentresponse.add(0,department_model);


                    if (Departmentresponse != null) {
                        DepartmentSpinnerAdapter departmentSpinnerAdapter = new DepartmentSpinnerAdapter(AbnormalityAddressingActivity.this, Departmentresponse);
                        sp_department.setAdapter(departmentSpinnerAdapter);

                    }

                }

                @Override
                public void onFailure(Call<List<Department_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitAddAbnormalityApi(String group, String domain, String business, String plant, String department, String imagepath, String description, String benefits, String abnormalitydate, String UploadedBy, int category) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            progressBar.setVisibility(View.VISIBLE);
         //   showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = promotingMyinterface.AddAbnormality(RetrofitClient2.CKEY, group, domain, business, plant, department, imagepath, description, benefits, abnormalitydate, UploadedBy, category);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
                 //   showProgress(false);
                    List<AddAbnormality_Model> Departmentresponse = response.body();

                    if (Departmentresponse != null) {
                        if (Departmentresponse.get(0).getColumn1().equalsIgnoreCase("success")) {
                            Toast.makeText(AbnormalityAddressingActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
//                            finish();

                            Im_capture.setImageResource(R.drawable.demoimage);
                            tv_upload.setText("Upload Image");
                            et_descripton.setText("");

                        } else {
                            Toast.makeText(AbnormalityAddressingActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }


                    }

                }

                @Override
                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {

                 //   showProgress(false);

                }
            });
        } else {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }

        progressBar.setVisibility(View.GONE);
        tv_submit.setEnabled(true);

    }

    public void hitCategoryApi() {
        showProgress(true);

        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<CategoryAbnormality>> call = anInterface.getCategory(RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<CategoryAbnormality>>() {
            @Override
            public void onResponse(Call<List<CategoryAbnormality>> call, Response<List<CategoryAbnormality>> response) {
                showProgress(false);
                List<CategoryAbnormality> list1 = response.body();
                if (list1 != null && list1.size() > 0) {
                    List<CategoryAbnormality> list = new ArrayList<>();
                    list.add(new CategoryAbnormality(0, "Select Category"));
                    list.addAll(list1);

                    Collections.sort(list, new Comparator<CategoryAbnormality>() {

                        @Override
                        public int compare(CategoryAbnormality o1, CategoryAbnormality o2) {
                            if (o2.getId() < o1.getId()) {
                                return 1;
                            } else if (o2.getId() > o1.getId()) {
                                return -1;
                            } else {
                                return 0;
                            }

                        }
                    });
                    AbnormalityCategoryAdapter departmentSpinnerAdapter = new AbnormalityCategoryAdapter(AbnormalityAddressingActivity.this, list);
                    sp_category.setAdapter(departmentSpinnerAdapter);


                }

            }

            @Override
            public void onFailure(Call<List<CategoryAbnormality>> call, Throwable t) {
                showProgress(false);
            }
        });

    }

    public void hitGetAbnormalityDetailApi(String plant, String department, String domain, String business) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AbnormalityView_Model>> response = promotingMyinterface.GetAbnormalityDetail(RetrofitClient2.CKEY, plant, department, domain, business, category_id);
            response.enqueue(new Callback<List<AbnormalityView_Model>>() {
                @Override
                public void onResponse(Call<List<AbnormalityView_Model>> call, Response<List<AbnormalityView_Model>> response) {
                    showProgress(false);
                    List<AbnormalityView_Model> AbnormalityDetail = response.body();

                    if (AbnormalityDetail != null) {
                        AbnormalityAdapter abnormalityAdapter = new AbnormalityAdapter(AbnormalityAddressingActivity.this, AbnormalityDetail);
                        list_abnormalty.setAdapter(abnormalityAdapter);
                        setListViewHeightBasedOnChildren(list_abnormalty);


                    }

                }

                @Override
                public void onFailure(Call<List<AbnormalityView_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public boolean isvalid() {

        if (group.equalsIgnoreCase("Select Group")) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Group", Toast.LENGTH_LONG).show();
            return false;
        }
        /*if (domain.equalsIgnoreCase("Select Domain")) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Domain", Toast.LENGTH_LONG).show();
            return false;
        }
*/
        if (department.equalsIgnoreCase("Select Department")) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Department", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_finddate.getText().toString().equalsIgnoreCase("Abnormality Finding Date")) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (description.length() == 0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Fill Description", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sImage.length() == 0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Attach Image", Toast.LENGTH_LONG).show();
            return false;
        }
        if (Role.equalsIgnoreCase("A")) {
            Toast.makeText(AbnormalityAddressingActivity.this, "You are not Authorized", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1) + 100);
        listView.setLayoutParams(params);
    }

    public void getdate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        new SimpleDateFormat("MMMM").format(mMonth);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear >= 0 && monthOfYear < 12)
                            try {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MONTH, monthOfYear);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                                simpleDateFormat.setCalendar(calendar);

                            } catch (Exception e) {
                                if (e != null)
                                    e.printStackTrace();
                            }
                        et_finddate.setText("0" + dayOfMonth + "-" + mMonth + "-" + year);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void hitGetUserDetail(String Email) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<UserDetail_Model>> response = promotingMyinterface.GetUserDetail(RetrofitClient2.CKEY, Email);
            response.enqueue(new Callback<List<UserDetail_Model>>() {
                @Override
                public void onResponse(Call<List<UserDetail_Model>> call, Response<List<UserDetail_Model>> response) {
                    showProgress(false);
                    List<UserDetail_Model> userDetail_models = response.body();

                    if (userDetail_models != null && userDetail_models.size() != 0) {

                        Role = userDetail_models.get(0).getRole();
                        PLANTROLE = userDetail_models.get(0).getPLANTROLE();
                        BUSINESS = userDetail_models.get(0).getBUSINESS();
                        DOMAIN = userDetail_models.get(0).getDOMAIN();
                        UNITCODE = userDetail_models.get(0).getUNITCODE();
                        //    hitDomainApi();


                    } else {
                        Toast.makeText(AbnormalityAddressingActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<List<UserDetail_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitSubdepartmentApi(String departmentID) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Sub_Department_Model>> response = promotingMyinterface.GetDepartmentDetail(RetrofitClient2.CKEY, departmentID);
            response.enqueue(new Callback<List<Sub_Department_Model>>() {
                @Override
                public void onResponse(Call<List<Sub_Department_Model>> call, Response<List<Sub_Department_Model>> response) {
                    showProgress(false);
                    Subdepartmentresponse = response.body();
                    Sub_Department_Model sub_department_model = new Sub_Department_Model();
                    sub_department_model.setID(0);
                    sub_department_model.setDEPARTMENTDETAIL("Select Sub Department");
                    Subdepartmentresponse.add(0, sub_department_model);

                    if (Subdepartmentresponse != null) {
                        GroupSpinnerAdapter departmentSpinnerAdapter = new GroupSpinnerAdapter(AbnormalityAddressingActivity.this, Subdepartmentresponse);
                        sp_sdepartment.setAdapter(departmentSpinnerAdapter);

                    }
                }

                @Override
                public void onFailure(Call<List<Sub_Department_Model>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

//    public void hitPlantApibyuser(String Email) {
//        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
//            showProgress(true);
//            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
//            Call<List<Plant_Model>> response = promotingMyinterface.GetPlantByUser(Email);
//            response.enqueue(new Callback<List<Plant_Model>>() {
//                @Override
//                public void onResponse(Call<List<Plant_Model>> call, Response<List<Plant_Model>> response) {
//                    showProgress(false);
//                    Plantresponse = response.body();
//
//                    if (Plantresponse != null && Plantresponse.size() != 0) {
//                        PlantSpinnerAdapter departmentSpinnerAdapter = new PlantSpinnerAdapter(AbnormalityAddressingActivity.this, Plantresponse);
//                        sp_plant.setAdapter(departmentSpinnerAdapter);
//
////                        for(int i=0;i<Plantresponse.size();i++)
////                        {
////                            if(UNITCODE.equalsIgnoreCase(Plantresponse.get(i).getUNITCODE()))
////                            {
////                                sp_plant.setSelection(i);
////
////                                sp_plant.setEnabled(true);
////                                break;
////                            }
////                        }
//
//
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Plant_Model>> call, Throwable t) {
//
//                    showProgress(false);
//
//                }
//            });
//        } else
//            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
//    }

    public void showdialog() {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(AbnormalityAddressingActivity.this);
        builder1.setMessage("Select View Or Add For Move Ahead");
        builder1.setTitle("Abnormality");
        builder1.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();

            }
        });

        builder1.setPositiveButton(
                "Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        checkorentation();
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "View",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        checkorentation();
                        dialog.dismiss();
                    }
                });


        builder1.show();
    }

    public void checkorentation() {

        orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lay_two.setVisibility(View.GONE);
            lay_one.setVisibility(View.VISIBLE);
            tv_add.setTextColor(getResources().getColor(R.color.white));
            tv_view.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_add.setBackground(getResources().getDrawable(R.color.colorPrimary));
            tv_view.setBackground(getResources().getDrawable(R.drawable.roundcornercellviewwhite));
            if (!myPref.getString("Id", "").equalsIgnoreCase("")) {
                hitGetUserDetail(myPref.getString("Id", ""));
            } else {
                finish();
            }
//            hitGetUserDetail("abhishek.kumarbest@razorse.com");


//            hitGroupApi();
            //code for portrait mode
        } else {
            lay_two.setVisibility(View.VISIBLE);
            lay_one.setVisibility(View.GONE);
            tv_view.setTextColor(getResources().getColor(R.color.white));
            tv_add.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_view.setBackground(getResources().getDrawable(R.color.colorPrimary));
            tv_add.setBackground(getResources().getDrawable(R.drawable.roundcornercellviewwhite));
//            hitGroupApi();
            if (!myPref.getString("Id", "").equalsIgnoreCase("")) {
                hitGetUserDetail(myPref.getString("Id", ""));
            } else {
                finish();
            }
//            hitGetUserDetail("abhishek.kumarbest@razorse.com");


            //code for landscape mode
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
