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
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.services.BottomUpConcernServices;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuggestionBox extends BaseActivity {

    @BindView(R.id.drop_suggestion)
    EditText dropSuggestion;
    @BindView(R.id.expected_cost_saving)
    EditText expectedCostSaving;
    @BindView(R.id.othr_ben)
    EditText otherBenefit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.add_attach)
    TextView addAttach;
    @BindView(R.id.addAttachment)
    ImageView addAttachment;
    @BindView(R.id.doc_view)
    ImageView docView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String empCode;
    SharedPreferences myPref;
    String fileName, fileType, fileByte;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private String mUserChoosenTask = "";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;

    private File mDestinationFile;
    byte[] bytes;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_box);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.cost_saving_ideas));
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");

    }


    @OnClick(R.id.add_attach)
    public void onClickAttachText() {
        selectFile();

    }

    @OnClick(R.id.addAttachment)
    public void onClickAddAttachment() {
        selectFile();

    }


    @OnClick(R.id.submit)
    public void onClickSuggestion() {

        if (dropSuggestion.getText().toString().length() == 0) {
            Toast.makeText(SuggestionBox.this, "Enter Suggestion", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Utility.isOnline(SuggestionBox.this)) {
            submitSuggestion(dropSuggestion.getText().toString(), empCode, expectedCostSaving.getText().toString(), otherBenefit.getText().toString(), fileName, fileType, fileByte);
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

    public void submitSuggestion(String suggestion, String empCode, String CostAmount, String other, String fileName, String fileType, String fileByte) {
        progressBar.setVisibility(View.VISIBLE);
        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.submitSuggestion(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                if (carotResponse.getData().toString().contains("Sucess")) {
                    Toast.makeText(SuggestionBox.this, "Successfully submitted", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            } else {
                Toast.makeText(SuggestionBox.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
            progressBar.setVisibility(View.GONE);

        }, suggestion, empCode, CostAmount, other, fileName, fileType, fileByte);
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

        ActivityCompat.requestPermissions(SuggestionBox.this,
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SuggestionBox.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(SuggestionBox.this);
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

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        bytes = getBytesFromBitmap(thumbnail);
        fileType = "jpg";
        bmp = thumbnail;
        addAttach.setText(fileName);
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
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(SuggestionBox.this, bm, Uri.parse(mDestinationFile.toString()));
        //  docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        this.fileName = fileName;
        System.out.println("fileName" + fileName);
        fileType = "jpg";
        bytes = getBytesFromBitmap(bm);
        bmp = bm;
        addAttach.setText(fileName);
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

        String fullFilePath = UriUtils.getPathFromUri(SuggestionBox.this, fileUri);
        File file = new File(fullFilePath);
        fileName = file.getName();
        fileType = mimeType.replace("application/", "");
        addAttach.setText(fileName);
        bytes = new byte[(int) file.length()];

        FileInputStream fis;
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

        ActivityCompat.requestPermissions(SuggestionBox.this,
                new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
    }


    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(SuggestionBox.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(SuggestionBox.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(SuggestionBox.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }


}
