package com.minda.sparsh;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.util.FileCompressor;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class AbnormalityAddressing2Activity extends AppCompatActivity {

    ListView list_abnormalty;
    LinearLayout lay_out;
    TextView  tv_upload, tv_Department, tv_plant, tv_business, tv_domain, et_finddate;
    ImageView Im_capture, im_back;
    Button tv_submit;
    Uri picUri = null;
    Bitmap myBitmap;
    String sImage = "";
    private ArrayList<String> permissionsToRequest;
    private final ArrayList<String> permissionsRejected = new ArrayList<>();
    private final ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    boolean pic_uploaded = false;
    private ProgressDialog progress = null;
    EditText et_descripton, et_benefits;
    String ImplementationDate, Action;
    int AbnormalID;
    SharedPreferences myPref;
    ActivityResultLauncher<String> cameraPermission;
    ActivityResultLauncher<Intent> startCamera,openGallery;
    File mPhotoFile;
    FileCompressor mCompressor;
    String fileName;
    byte[] bytes;
    private File mDestinationFile;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    String mUserChoosenTask = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormality_addressing2);
        tv_upload =  findViewById(R.id.tv_upload);
        tv_Department =  findViewById(R.id.tv_Department);
        tv_plant =  findViewById(R.id.tv_plant);
        tv_business =  findViewById(R.id.tv_business);
        tv_domain =  findViewById(R.id.tv_domain);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mCompressor = new FileCompressor(this);

        if (getIntent().getExtras() != null) {
            AbnormalID = getIntent().getIntExtra("ID", 0);
            tv_Department.setText(getIntent().getStringExtra("department"));
            tv_plant.setText(getIntent().getStringExtra("plant"));
            tv_business.setText(getIntent().getStringExtra("business"));
            tv_domain.setText(getIntent().getStringExtra("domain"));

        }
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
                } catch (IOException e) {
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


        lay_out =  findViewById(R.id.lay_out);
        list_abnormalty =  findViewById(R.id.list_abnormalty);


        Im_capture =  findViewById(R.id.Im_capture);
        im_back =  findViewById(R.id.im_back);

        et_finddate =  findViewById(R.id.et_finddate);
        et_descripton =  findViewById(R.id.et_descripton);
        et_benefits =  findViewById(R.id.et_benefits);
        tv_submit =  findViewById(R.id.tv_submit);
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        im_back.setOnClickListener(view -> finish());
        String date = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date());
        et_finddate.setText(date);


        Im_capture.setOnClickListener(view -> {
            selectFile();
           /* if (Utility.checkPermission(AbnormalityAddressing2Activity.this)) {
                startActivityForResult(getPickImageChooserIntent(), 200);
                permissions.add(CAMERA);
                permissionsToRequest = findUnAskedPermissions(permissions);
                //get the permissions we have asked for before but are not granted..
                //we will store this in a global list to access later.

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (permissionsToRequest.size() > 0)
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
            }*/
        });
        tv_submit.setOnClickListener(view -> {
            Action = et_descripton.getText().toString();
            ImplementationDate = et_finddate.getText().toString();

            if (Action.length() != 0) {
                if (sImage.length() != 0) {
                    hitUpdateAbnormalityApi(AbnormalID, sImage, Action, ImplementationDate, et_benefits.getText().toString(), myPref.getString("Id", ""));
                } else {
                    Toast.makeText(AbnormalityAddressing2Activity.this, "Please Attach Image", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(AbnormalityAddressing2Activity.this, "Please fill Action", Toast.LENGTH_LONG).show();
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

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

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
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",/*"Choose Document",*/
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Warranty Card");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(this);
            if (items[item].equals("Take Photo")) {
                mUserChoosenTask = "Take Photo";
                if (result) {
                    cameraPermission.launch(Manifest.permission.CAMERA);
                    //  requestCameraPermission();
                  /*  if (hasCameraPermission())
                        Toast.makeText(getActivity(),"Place your camera closer to warranty card",Toast.LENGTH_LONG).show();
                        cameraIntent();
               */ }
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

    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
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
                Uri photoURI = FileProvider.getUriForFile(AbnormalityAddressing2Activity.this,
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
            mFile = File.createTempFile(mFileName, ".jpg", storageDir);
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
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

  /*  @Override
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
                    sImage = getStringImage(myBitmap);
                    tv_upload.setText("Image Upload Successfully");
                   *//* if (AppUtils.isNetworkAvailable(SuggestUsActivity.this)) {

                        try{
                            submitWorkDetails(sessionManager.getShopName(), sessionManager.getWorkshpName(), sessionManager.getLocation(), sessionManager.getStateCode(), sessionManager.getCityCode(), sessionManager.getMobile(), sessionManager.getZoneCode());
                        }catch (Exception e) {
                            Crashlytics.logException(e);
                            Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            // handle your exception here!
                        }

                    }*//*


                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {


                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;

                Im_capture.setImageURI(picUri);
                Im_capture.setImageBitmap(myBitmap);
                tv_upload.setText("Image Upload Successfully");


                sImage = getStringImage(myBitmap);
               *//* if (connectionDetector.isConnectingToInternet()) {
                    submitWorkDetails(sessionManager.getShopName(), sessionManager.getWorkshpName(), sessionManager.getLocation(), sessionManager.getStateCode(), sessionManager.getCityCode(), sessionManager.getMobile(), sessionManager.getZoneCode());


                }
*//*


            }

        }

    }
*/
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


    public void hitUpdateAbnormalityApi(int AbnormalID, String ImagePathAfter, String Action, String ImplementationDate, String benifits, String UpdatedBy) {
        if (Utility.isOnline(AbnormalityAddressing2Activity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = promotingMyinterface.UpdateAbnormality(RetrofitClient2.CKEY, AbnormalID, ImagePathAfter, Action, ImplementationDate, benifits, UpdatedBy);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<AddAbnormality_Model>> call, @NotNull Response<List<AddAbnormality_Model>> response) {
                    showProgress(false);
                    List<AddAbnormality_Model> Departmentresponse = response.body();

                    if (Departmentresponse != null && Departmentresponse.size() > 0) {
                        if (Departmentresponse.get(0).getColumn1().equalsIgnoreCase("success")) {
                            Toast.makeText(AbnormalityAddressing2Activity.this, "Date Save Successfully", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(AbnormalityAddressing2Activity.this, AbnormalityAddressingActivity.class);
                            intent.putExtra("ADD", false);
                            startActivity(intent);
                            finish();


                        } else {
                            Toast.makeText(AbnormalityAddressing2Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }


                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<AddAbnormality_Model>> call, @NotNull Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(AbnormalityAddressing2Activity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }
}
