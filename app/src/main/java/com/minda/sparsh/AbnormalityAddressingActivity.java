package com.minda.sparsh;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.minda.sparsh.Adapter.AbnormalityAdapter;
import com.minda.sparsh.Adapter.BusinessSpinnerAdapter;
import com.minda.sparsh.Adapter.DomainSpinnerAdapter;
import com.minda.sparsh.model.AbnormalityView_Model;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.model.AssignResponseModel;
import com.minda.sparsh.model.Business_Model;
import com.minda.sparsh.model.CategoryAbnormality;
import com.minda.sparsh.model.Department_Model;
import com.minda.sparsh.model.Domain_Model;
import com.minda.sparsh.model.GetAbnormalityImage_Model;
import com.minda.sparsh.model.Group_Model;
import com.minda.sparsh.model.Plant_Model;
import com.minda.sparsh.model.Sub_Department_Model;
import com.minda.sparsh.model.UserDetail_Model;
import com.minda.sparsh.services.AbnormalityServices;
import com.minda.sparsh.util.AbnormalityDashboard;
import com.minda.sparsh.util.FileCompressor;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AbnormalityAddressingActivity extends AppCompatActivity {
    ListView list_abnormalty;
    LinearLayout lay_two, lay_one, lay_out;
    TextView tv_view, tv_add, tv_upload;
    TextInputEditText et_finddate;
    Button tv_submit,tv_delete;
    ImageView Im_capture, im_back;
    Uri picUri = null;
    Bitmap myBitmap;
    String sImage = "";
    int mYear, mMonth, mDay;
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
   // private final ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    boolean pic_uploaded = false;
    private ProgressDialog progress = null;
    Spinner  sp_domain, sp_business;
    TextInputEditText et_descripton;
    AppCompatAutoCompleteTextView sp_plant, sp_department,sp_sdepartment, sp_category;
    SharedPreferences myPref;
    String group = "UNO Minda Group", domain = "Select Domain", business, plant = "", department = "Select Department", description, benefits, abnormalitydate, domainid = "", businessid = "";
    public String BUSINESS = "", DOMAIN = "", UNITCODE = "", plantid = "", PLANTROLE = "";
    public int sub_department, orientation;
    public static String Role;
    List<Domain_Model> Domainresponse;
    List<Business_Model> Businessresponse;
    List<Plant_Model> Plantresponse = new ArrayList<>();
    List<Department_Model> Departmentresponse = new ArrayList<>();
    List<Sub_Department_Model> Subdepartmentresponse = new ArrayList<>();
    List<CategoryAbnormality> categorymodelList = new ArrayList<>();

    Toolbar toolbar;
    TextView title;
    String empCode;

    ProgressBar progressBar;
    int category_id;

    ArrayList<String> plants = new ArrayList<>();
    ArrayList<String> departments = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> subdepartments = new ArrayList<>();

    ArrayAdapter<String> adapterUnit;
    ArrayAdapter<String> adapterDepartment;
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterSubDepartment;
    String plantselected, departmentselected ,sub_departmentselected, categoryselected;
    ActivityResultLauncher<String> cameraPermission;
    ActivityResultLauncher<Intent> startCamera,openGallery;
    File mPhotoFile;
    FileCompressor mCompressor;
    String fileName;
    byte[] bytes;
    private File mDestinationFile;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    String mUserChoosenTask = "", AbnormalID = "0";
    AbnormalityView_Model abnormalityView_model;
    int funcId,subdep_id;
    String EDOMAIN,EBUSINESS,EPLANT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_addressing);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.abnormality));
        lay_two = findViewById(R.id.lay_two);
        lay_one = findViewById(R.id.lay_one);
        lay_out = findViewById(R.id.lay_out);
        list_abnormalty = findViewById(R.id.list_abnormalty);
        progressBar = findViewById(R.id.progressBar);
        tv_view = findViewById(R.id.tv_view);
        tv_add = findViewById(R.id.tv_add);
        tv_upload = findViewById(R.id.tv_upload);
        Im_capture = findViewById(R.id.Im_capture);
        sp_department = findViewById(R.id.sp_department);
        sp_domain = findViewById(R.id.sp_domain);
//        sp_group = (Spinner) findViewById(R.id.sp_group);
        sp_business = findViewById(R.id.sp_business);
        sp_sdepartment = findViewById(R.id.sp_sdepartment);
        sp_category = findViewById(R.id.sp_category);
        sp_plant = findViewById(R.id.sp_plant);
        et_finddate = findViewById(R.id.et_finddate);
        et_descripton = findViewById(R.id.et_descripton);
        tv_submit = findViewById(R.id.tv_submit);
        tv_delete = findViewById(R.id.tv_delete);
        im_back = findViewById(R.id.im_back);
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        EDOMAIN = myPref.getString("EDOMAIN","");
        EBUSINESS = myPref.getString("EBUSINESS","");
        EPLANT = myPref.getString("EPLANT","");


       /* if(getIntent().getParcelableExtra("ab")!=null){
            abnormalityView_model = getIntent().getParcelableExtra("ab");
            description = abnormalityView_model.getDescription();
            et_descripton.setText(description);
            et_finddate.setText(abnormalityView_model.getAbnormalityDate());
            AbnormalID = String.valueOf(abnormalityView_model.getID());
            hitgetimageApi(abnormalityView_model.getID());
            tv_submit.setText("UPDATE");
            tv_delete.setVisibility(View.VISIBLE);
        }
        else{
            tv_submit.setText("SUBMIT");
            tv_delete.setVisibility(View.GONE);

        }

        if (getIntent().getStringExtra("EDOMAIN") != null && getIntent().getStringExtra("EDOMAIN").length() > 0) {
            domainid = getIntent().getStringExtra("EDOMAIN");
        } else if (getIntent().getStringExtra("EBUSINESS") != null && getIntent().getStringExtra("EBUSINESS").length() > 0) {
            businessid = getIntent().getStringExtra("EBUSINESS");

        } else {
            plantid = getIntent().getStringExtra("EPLANT");

        }
        hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid,empCode);
       */ adapterUnit = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,plants);
        sp_plant.setAdapter(adapterUnit);
        adapterDepartment = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,departments);
        sp_department.setAdapter(adapterDepartment);
        adapterCategory = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,categories);
        sp_category.setAdapter(adapterCategory);
        adapterSubDepartment = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item, subdepartments);
        sp_sdepartment.setAdapter(adapterSubDepartment);

        hitCategoryApi();

        cameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if(result){
                cameraIntent();
            }
            else{
                cameraPermission.launch(Manifest.permission.CAMERA);
            }
        });

        startCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                // onCaptureImageResult(result.getData());
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Im_capture.setVisibility(View.VISIBLE);
                fileName = mPhotoFile.getName();

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),bmOptions);
                bytes = getBytesFromBitmap(bitmap);
                sImage = Base64.encodeToString(bytes, Base64.NO_WRAP);
                Glide.with(this).load(mPhotoFile).into(Im_capture);
            }
        });

        openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                onSelectFromGalleryResult(result.getData());
            }
        });

        im_back.setOnClickListener(view -> finish());
        if (getIntent().getExtras() != null) {
            if (getIntent().getBooleanExtra("ADD", false)) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            checkorentation();
        }

        hitPlantApi(empCode);

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

        sp_category.setOnItemClickListener((adapterView, view, i, l) -> {
            CategoryAbnormality categoryAbnormality = categorymodelList.get(i);
            category_id = categoryAbnormality.getId();
            categoryselected = categoryAbnormality.getCategory();
            hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid,empCode);
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
                  /*  PlantSpinnerAdapter departmentSpinnerAdapter = new PlantSpinnerAdapter(AbnormalityAddressingActivity.this, list);
                    sp_plant.setAdapter(departmentSpinnerAdapter);
               */ }


//                hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid);
//


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_sdepartment.setOnItemClickListener((adapterView, view, i, l) -> {
            Sub_Department_Model selectedItem = Subdepartmentresponse.get(i);
            sub_department = selectedItem.getID();
            sub_departmentselected = selectedItem.getDEPARTMENTDETAIL();
            hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid,empCode);
        });
       sp_department.setOnItemClickListener((adapterView, view, i, l) -> {
         if(i>=0) {
             Department_Model selectedItem = Departmentresponse.get(i);
             department = selectedItem.getDEPARTMENT();
             departmentselected = selectedItem.getDEPARTMENT();
             sp_sdepartment.setText("");
             funcId = Integer.parseInt(selectedItem.getID());
             adapterSubDepartment = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item, subdepartments);
             sp_sdepartment.setAdapter(adapterSubDepartment);
             adapterSubDepartment.notifyDataSetChanged();

             hitSubdepartmentApi(selectedItem.getID());
             hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid,empCode);

         }


     });


        sp_plant.setOnItemClickListener((adapterView, view, i, l) -> {
            //  Plant_Model selectedItem = (Plant_Model) adapterView.getSelectedItem();
            plant = Plantresponse.get(i).getUnitName();
            plantid = Plantresponse.get(i).getUnitCode();
            plantselected = Plantresponse.get(i).getUnitName();
            hitDepartmentApi();
            hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid,empCode);

        });
        tv_add.setOnClickListener(view -> {

            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                finish();
                Intent intent = new Intent(AbnormalityAddressingActivity.this, AbnormalityAddressingActivity.class);
                intent.putExtra("EDOMAIN", EDOMAIN);
                intent.putExtra("EBUSINESS", EBUSINESS);
                intent.putExtra("EPLANT", EPLANT);
                intent.putExtra("ADD", true);
                startActivity(intent);
            }
        });
        tv_view.setOnClickListener(view -> {

            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                Intent intent = new Intent(AbnormalityAddressingActivity.this, AbnormalityDashboard.class);
                intent.putExtra("EDOMAIN", EDOMAIN);
                intent.putExtra("EBUSINESS", EBUSINESS);
                intent.putExtra("EPLANT", EPLANT);
                startActivity(intent);
            }
        });
        Im_capture.setOnClickListener(view -> selectFile());
        tv_delete.setOnClickListener(view -> showdialog());
        tv_submit.setOnClickListener(view -> {
            description = et_descripton.getText().toString();
            abnormalitydate = et_finddate.getText().toString();
            benefits = "";
            if (isvalid()) {
                hitAddAbnormalityApi(AbnormalID,group, domainid, businessid, plantid, String.valueOf(sub_department), sImage, description, benefits, abnormalitydate, myPref.getString("Id", ""), category_id, funcId);
            }
        });

    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        File f = null;
        if (mDestinationFile != null) {
            mDestinationFile.delete();
        }
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                Uri selectedImageUri = data.getData();
                final String path = getPathFromURI(selectedImageUri);
                if (path != null) {
                    f = new File(path);
                    selectedImageUri = Uri.fromFile(f);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        try {
            bm = modifyOrientation( bm, f.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utility.saveFileToSdCard(f, bm);
        fileName = f.getName();
        System.out.println("fileName" + fileName);
        //attachmentName = fileName;
        //attachmentType = "jpg";
        bytes = getBytesFromBitmap(bm);
        sImage = Base64.encodeToString(bytes, Base64.NO_WRAP);
        //   bmp = bm;
        Im_capture.setImageBitmap(bm);
        Im_capture.setVisibility(View.VISIBLE);


//        newSingleThreadExecutor();
    }

    public void selectFile() {
        requestAppPermissions();
    }
    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
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

        ActivityCompat.requestPermissions(this,
                new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",/*"Choose Document",*/
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(this);
            if (items[item].equals("Take Photo")) {
                mUserChoosenTask = "Take Photo";
                if (result) {
                    cameraPermission.launch(Manifest.permission.CAMERA);
                  }
            } else if (items[item].equals("Choose from Gallery")) {
                mUserChoosenTask = "Choose from Gallery";
                if (result) {
                    galleryIntent();
                }
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGallery.launch(pickPhoto);
        //   getActivity().startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }
    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void cameraIntent() {
        dispatchTakePictureIntent();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(AbnormalityAddressingActivity.this,
                        "com.minda.sparsh.provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startCamera.launch(takePictureIntent);

                //    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }
    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = null;
        try {
            mFile = File.createTempFile(mFileName, ".jp g", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mFile;
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

/*
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
*/

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

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("These permissions are mandatory for the application Please allow access")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                            showMessageOKCancel(
                                    (dialog, which) -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                            //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                            requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
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

    @Override
    protected void onResume() {
        super.onResume();
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        if(getIntent().getParcelableExtra("ab")!=null){
            abnormalityView_model = getIntent().getParcelableExtra("ab");
            description = abnormalityView_model.getDescription();
            et_descripton.setText(description);
            et_finddate.setText(abnormalityView_model.getAbnormalityDate());
            AbnormalID = String.valueOf(abnormalityView_model.getID());
            hitgetimageApi(abnormalityView_model.getID());
            tv_submit.setText("UPDATE");
            tv_delete.setVisibility(View.VISIBLE);
        }
        else{
            tv_submit.setText("SUBMIT");
            tv_delete.setVisibility(View.GONE);

        }

        if (getIntent().getStringExtra("EDOMAIN") != null && getIntent().getStringExtra("EDOMAIN").length() > 0) {
            domainid = getIntent().getStringExtra("EDOMAIN");
        } else if (getIntent().getStringExtra("EBUSINESS") != null && getIntent().getStringExtra("EBUSINESS").length() > 0) {
            businessid = getIntent().getStringExtra("EBUSINESS");

        } else {
            plantid = getIntent().getStringExtra("EPLANT");

        }
        if(!getIntent().getBooleanExtra("ADD",false)) {
            hitGetAbnormalityDetailApi(plantid, String.valueOf(sub_department), domainid, businessid, empCode);
        }

    }

    public void hitGroupApi() {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<Group_Model>> response = promotingMyinterface.GetGroup();
            response.enqueue(new Callback<List<Group_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<Group_Model>> call, @NotNull Response<List<Group_Model>> response) {
                    showProgress(false);
                    List<Group_Model> Groupresponse = response.body();
                    Group_Model group_model = new Group_Model();
                    group_model.setGID(0);
                    group_model.setGroupName("Select Group");
                    group_model.setIsActive(false);
                    Groupresponse.add(0, group_model);
                    if (Groupresponse != null) {

                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<Group_Model>> call, @NotNull Throwable t) {

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
                public void onResponse(@NotNull Call<List<Domain_Model>> call, @NotNull Response<List<Domain_Model>> response) {
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
                public void onFailure(@NotNull Call<List<Domain_Model>> call, @NotNull Throwable t) {

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
                public void onResponse(@NotNull Call<List<Business_Model>> call, @NotNull Response<List<Business_Model>> response) {
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
                public void onFailure(@NotNull Call<List<Business_Model>> call, @NotNull Throwable t) {

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
                public void onResponse(@NotNull Call<List<Plant_Model>> call, @NotNull Response<List<Plant_Model>> response) {
                    try {
                        showProgress(false);

                        List<Plant_Model> list = response.body();

                        if(list!= null && list.size()>0){
                            for (Plant_Model plantModel : list) {
                                Plantresponse.add(plantModel);
                                plants.add(plantModel.getUnitName());
                            }
                            adapterUnit = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,plants);
                            sp_plant.setAdapter(adapterUnit);
                            adapterUnit.notifyDataSetChanged();
                        }

                        for (int i = 0; i < list.size(); i++) {
                            if (UNITCODE.equalsIgnoreCase(Plantresponse.get(i).getUnitCode())) {
                                sp_plant.setText(""+Plantresponse.get(i).getUnitName());
                                plantselected = plants.get(0);
                                plant = Plantresponse.get(0).getUnitName();
                                plantid = Plantresponse.get(0).getUnitCode();

                                hitDepartmentApi();

                                if (PLANTROLE.equalsIgnoreCase("Best Coordinator") || PLANTROLE.equalsIgnoreCase("Plant Head")) {
                                    sp_plant.setEnabled(false);
                                } else {
                                    sp_plant.setEnabled(true);
                                }
                                break;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Plant_Model>> call, @NotNull Throwable t) {

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
                public void onResponse(@NotNull Call<List<Department_Model>> call, @NotNull Response<List<Department_Model>> response) {
                    showProgress(false);
                    Departmentresponse.addAll(response.body());
                    for (Department_Model department_model : Departmentresponse) {
                        departments.add(department_model.getDEPARTMENT());
                    }
                    adapterDepartment = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,departments);
                    sp_department.setAdapter(adapterDepartment);
                    adapterDepartment.notifyDataSetChanged();
                    if(abnormalityView_model!=null){
                        if(abnormalityView_model.getFunctionId()!=null) {
                            funcId = Integer.parseInt(abnormalityView_model.getFunctionId());
                            departmentselected = Departmentresponse.get(funcId-1).getDEPARTMENT();
                            sp_department.setText(Departmentresponse.get(funcId-1).getDEPARTMENT());
                            adapterDepartment.getFilter().filter(null);
                            hitSubdepartmentApi(String.valueOf(funcId));
                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Department_Model>> call, @NotNull Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void hitAddAbnormalityApi(String id,String group, String domain, String business, String plant, String department, String imagepath, String description, String benefits, String abnormalitydate, String UploadedBy, int category, int funcId) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
          //  progressBar.setVisibility(View.VISIBLE);
            et_descripton.setText("");
            progress.show();
            //   showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = promotingMyinterface.AddAbnormality(id,RetrofitClient2.CKEY, group, domain, business, plant, department, imagepath, description, benefits, abnormalitydate, UploadedBy, category,funcId);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<AddAbnormality_Model>> call, @NotNull Response<List<AddAbnormality_Model>> response) {
                    //   showProgress(false);
                    progress.dismiss();
                //    progressBar.setVisibility(View.GONE);

                    List<AddAbnormality_Model> Departmentresponse = response.body();

                    if (Departmentresponse != null) {
                        if (Departmentresponse.get(0).getColumn1().equalsIgnoreCase("success")) {
                  //          Toast.makeText(AbnormalityAddressingActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
//                            finish();
                            if(tv_submit.getText().toString().equals("UPDATE")){
                                showMsg("Abnormality updated successfully.", "Success");
                            }
                            else {
                                showMsg("Abnormality submitted successfully.", "Success");
                            }
                            Im_capture.setImageResource(R.drawable.demoimage);
                            tv_upload.setText("Upload Image");
                            et_descripton.setText("");

                        } else {
                            Toast.makeText(AbnormalityAddressingActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }


                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<AddAbnormality_Model>> call, @NotNull Throwable t) {
                //    progressBar.setVisibility(View.GONE);
                    progress.dismiss();


                    //   showProgress(false);

                }
            });
        } else {
            //progressBar.setVisibility(View.GONE);
            progress.dismiss();
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }


    }

    public void hitCategoryApi() {
        showProgress(true);

        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<CategoryAbnormality>> call = anInterface.getCategory(RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<CategoryAbnormality>>() {
            @Override
            public void onResponse(@NotNull Call<List<CategoryAbnormality>> call, @NotNull Response<List<CategoryAbnormality>> response) {
                showProgress(false);
                List<CategoryAbnormality> list1 = response.body();
                if (list1 != null && list1.size() > 0) {
                    categorymodelList.addAll(list1);

                    Collections.sort(categorymodelList, (o1, o2) -> {
                        if (o2.getId() < o1.getId()) {
                            return 1;
                        } else if (o2.getId() > o1.getId()) {
                            return -1;
                        } else {
                            return 0;
                        }

                    });
                    for (CategoryAbnormality categoryAbnormality : categorymodelList) {
                        categories.add(categoryAbnormality.getCategory());
                    }
                    adapterCategory = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,categories);
                    sp_category.setAdapter(adapterCategory);
                    adapterCategory.notifyDataSetChanged();
                    if(abnormalityView_model!=null && abnormalityView_model.getCategory()!=null){
                        CategoryAbnormality categoryAbnormality = new CategoryAbnormality();
                        categoryAbnormality.setCategory(abnormalityView_model.getCategory());
                        int index = categorymodelList.indexOf(categoryAbnormality);
                        if(index>=0){
                            category_id = categorymodelList.get(index).getId();
                            categoryselected = abnormalityView_model.getCategory();
                            sp_category.setText(abnormalityView_model.getCategory());
                            adapterSubDepartment.getFilter().filter(null);
                            adapterCategory = new ArrayAdapter<>(AbnormalityAddressingActivity.this, android.R.layout.simple_spinner_item,categories);
                            sp_category.setAdapter(adapterCategory);
                            adapterCategory.notifyDataSetChanged();

                        }
                       }

                }

            }

            @Override
            public void onFailure(@NotNull Call<List<CategoryAbnormality>> call, @NotNull Throwable t) {
                showProgress(false);
            }
        });

    }

    public void hitGetAbnormalityDetailApi(String plant, String department, String domain, String business,String empCode) {
        //progress.show();
        if (!getIntent().getBooleanExtra("ADD", false)) {

            if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
                showProgress(true);
                Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
                Call<List<AbnormalityView_Model>> response = promotingMyinterface.GetAbnormalityDetail(RetrofitClient2.CKEY, plant, department, domain, business, category_id,empCode);
                response.enqueue(new Callback<List<AbnormalityView_Model>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<AbnormalityView_Model>> call, @NotNull Response<List<AbnormalityView_Model>> response) {
                      /*  if (!AbnormalityAddressingActivity.this.isFinishing() && progress != null) {
                            progress.dismiss();
                        }
                      */  List<AbnormalityView_Model> AbnormalityDetail = response.body();

                        if (AbnormalityDetail != null) {
                            AbnormalityAdapter abnormalityAdapter = new AbnormalityAdapter(AbnormalityAddressingActivity.this, AbnormalityDetail);
                            list_abnormalty.setAdapter(abnormalityAdapter);
                            abnormalityAdapter.notifyDataSetChanged();
                            list_abnormalty.setOnTouchListener(new ListView.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    int action = event.getAction();
                                    switch (action) {
                                        case MotionEvent.ACTION_DOWN:
                                            // Disallow ScrollView to intercept touch events.
                                            v.getParent().requestDisallowInterceptTouchEvent(true);
                                            break;

                                        case MotionEvent.ACTION_UP:
                                            // Allow ScrollView to intercept touch events.
                                            v.getParent().requestDisallowInterceptTouchEvent(false);
                                            break;
                                    }

                                    // Handle ListView touch events.
                                    v.onTouchEvent(event);
                                    return true;
                                }
                            });
                           // setListViewHeightBasedOnChildren(list_abnormalty);


                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<List<AbnormalityView_Model>> call, @NotNull Throwable t) {
                        showProgress(false);

                    }
                });
            } else
                Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isvalid() {
        if (plantselected==null || plantselected.length()==0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Plant", Toast.LENGTH_LONG).show();
            return false;
        }
        if (departmentselected==null || departmentselected.length()==0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Department", Toast.LENGTH_LONG).show();
            return false;
        }
        if (sub_departmentselected==null || sub_departmentselected.length()==0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Sub Department", Toast.LENGTH_LONG).show();
            return false;
        }
        if (categoryselected==null || categoryselected.length()==0) {
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Select Category", Toast.LENGTH_LONG).show();
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
                (view, year, monthOfYear, dayOfMonth) -> {
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
                public void onResponse(@NotNull Call<List<UserDetail_Model>> call, @NotNull Response<List<UserDetail_Model>> response) {
                    showProgress(false);
                    List<UserDetail_Model> userDetail_models = response.body();

                    if (userDetail_models != null && userDetail_models.size() != 0) {

                        Role = userDetail_models.get(0).getRole();
                        PLANTROLE = userDetail_models.get(0).getPLANTROLE();
                        BUSINESS = userDetail_models.get(0).getBUSINESS();
                        DOMAIN = userDetail_models.get(0).getDOMAIN();
                        UNITCODE = userDetail_models.get(0).getUNITCODE();

                    } else {
                        Toast.makeText(AbnormalityAddressingActivity.this, "You Are Not Authorized", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<UserDetail_Model>> call, @NotNull Throwable t) {

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
                public void onResponse(@NotNull Call<List<Sub_Department_Model>> call, @NotNull Response<List<Sub_Department_Model>> response) {
                    showProgress(false);
                    if(response.code()== HttpsURLConnection.HTTP_OK) {
                        List<Sub_Department_Model> list = response.body();
                        Subdepartmentresponse.clear();
                        subdepartments.clear();
                        if(list!=null && list.size()>0){
                            Subdepartmentresponse.addAll(list);
                        }
                        for (Sub_Department_Model sub_department_model : Subdepartmentresponse) {
                            subdepartments.add(sub_department_model.getDEPARTMENTDETAIL());
                        }
                        adapterSubDepartment.notifyDataSetChanged();
                        if(abnormalityView_model!=null && abnormalityView_model.getDepartment()!=null) {
                            subdep_id = Integer.parseInt(abnormalityView_model.getDepartment());
                            Sub_Department_Model sub_department_model = new Sub_Department_Model();
                            sub_department_model.setID(subdep_id);
                            int index = Subdepartmentresponse.indexOf(sub_department_model);
                            if (index >= 0) {

                                sub_department = subdep_id;
                                sp_sdepartment.setText(Subdepartmentresponse.get(index).getDEPARTMENTDETAIL());
                                adapterSubDepartment.getFilter().filter(null);
                                sub_departmentselected = Subdepartmentresponse.get(index).getDEPARTMENTDETAIL();

                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<Sub_Department_Model>> call, @NotNull Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void showdialog() {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(AbnormalityAddressingActivity.this);
        builder1.setMessage("Do you want to delete this abnormality?");
        builder1.setTitle("Abnormality");
        builder1.setOnCancelListener(dialogInterface -> finish());

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                   deleteAbnormality(empCode, AbnormalID);
                       dialog.dismiss();
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.dismiss());


        builder1.show();
    }

    public void checkorentation() {

        orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lay_two.setVisibility(View.GONE);
            lay_one.setVisibility(View.VISIBLE);
            tv_add.setTextColor(getResources().getColor(R.color.white));
            tv_view.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_add.setBackground(getResources().getDrawable(R.drawable.btn_bg));
            tv_view.setBackground(getResources().getDrawable(R.drawable.roundcornercellviewwhite));
            if (!myPref.getString("Id", "").equalsIgnoreCase("")) {
                hitGetUserDetail(myPref.getString("Id", ""));
            } else {
                finish();
            }
        } else {
            lay_two.setVisibility(View.VISIBLE);
            lay_one.setVisibility(View.GONE);
            tv_view.setTextColor(getResources().getColor(R.color.white));
            tv_add.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_view.setBackground(getResources().getDrawable(R.drawable.btn_bg));
            tv_add.setBackground(getResources().getDrawable(R.drawable.roundcornercellviewwhite));
            if (!myPref.getString("Id", "").equalsIgnoreCase("")) {
                hitGetUserDetail(myPref.getString("Id", ""));
            } else {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void hitgetimageApi(int id) {
        if (Utility.isOnline(AbnormalityAddressingActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<GetAbnormalityImage_Model>> response = promotingMyinterface.GetAbnormalityImage(RetrofitClient2.CKEY, id);
            response.enqueue(new Callback<List<GetAbnormalityImage_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<GetAbnormalityImage_Model>> call, @NotNull Response<List<GetAbnormalityImage_Model>> response) {
                    showProgress(false);
                    List<GetAbnormalityImage_Model> images = response.body();

                    if (images != null) {
                        sImage = String.valueOf(images.get(0).getImagePath());
                        Im_capture.setImageBitmap(StringToBitMap(images.get(0).getImagePath().replace(" ", "+")));
                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<GetAbnormalityImage_Model>> call, @NotNull Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressingActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


    public void deleteAbnormality(String Empcode, String concernNo){
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.deleteAbnormality(carotResponse -> {
            if(carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK){
                AssignResponseModel assignResponseModel = (AssignResponseModel) carotResponse.getData();
                if (assignResponseModel != null) {
                    if (assignResponseModel.getMessage() != null && assignResponseModel.getMessage().equals("Sucess")) {
                     //   Toast.makeText(AbnormalityAddressingActivity.this, "Successfully Deleted", Toast.LENGTH_LONG).show();
                        showMsg("Successfully Deleted.","Success");
                        onBackPressed();
                    } else {
                        Toast.makeText(AbnormalityAddressingActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(AbnormalityAddressingActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
        }
        },Empcode,concernNo);

    }

    public void showMsg(String msg, String title) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder.setPositiveButton("Ok", (arg0, arg1) -> {
            arg0.dismiss();
            onBackPressed();
        });

        //alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
