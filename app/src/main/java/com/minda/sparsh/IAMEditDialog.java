package com.minda.sparsh;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IAMEditDialog extends DialogFragment {
    AppCompatActivity activity;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;
    private String mUserChoosenTask = "";
    private File mDestinationFile;

    String fileName = "", fileType, fileByte = "";
    byte[] bytes;
    Bitmap bmp;
    ImageView attachment1;
    Button send;
    SharedPreferences myPref;
    TextView attachtext1;


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iam_edit_dialog, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachment1 = view.findViewById(R.id.attachment1);
        attachtext1 = view.findViewById(R.id.attachtext1);
        send = view.findViewById(R.id.send);
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);

        attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileByte.length() != 0) {
                    sendtoProcessor(myPref.getString("Id", "Id"), myPref.getString("reqId", ""), myPref.getString("access_req_no", ""), myPref.getString("category_Id", ""), myPref.getString("accesstype_Id", ""), fileName, fileByte, RetrofitClient2.CKEY);
                }
                else{
                    Toast.makeText(getActivity(), "Please upload Attachment first", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

        ActivityCompat.requestPermissions((Activity) getContext(),
                new String[]{
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, CAPTURE_FROM_CAMERA);
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Choose Document",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Utility.checkPermission(getContext());
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
      //  docView.setVisibility(View.GONE);
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
        getActivity().startActivityForResult(Intent.createChooser(intent, "ChooseFile"), SELECT_FILE);
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        bytes = getBytesFromBitmap(
                thumbnail);
        fileType = "jpg";
        bmp = thumbnail;
     //   attachtext.setText(fileName);
        this.fileName = fileName;
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
       // docView.setVisibility(View.VISIBLE);
        attachment1.setImageBitmap(thumbnail);
        attachtext1.setVisibility(View.GONE);
        attachment1.setVisibility(View.VISIBLE);

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
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(getContext(), bm, Uri.parse(mDestinationFile.toString()));
        //  docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        this.fileName = fileName;
        System.out.println("fileName" + fileName);
        fileType = "jpg";
        bytes = getBytesFromBitmap(bm);
        bmp = bm;
     //   attachtext.setText(fileName);
        fileByte = Base64.encodeToString(bytes, Base64.NO_WRAP);
       // docView.setVisibility(View.VISIBLE);
        attachment1.setImageBitmap(bm);
        attachtext1.setVisibility(View.GONE);
        attachment1.setVisibility(View.VISIBLE);

    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {

        Uri fileUri = data.getData();

        File file = new File(fileUri.getPath());
        String mimeType = getContext().getContentResolver().getType(fileUri);
        fileType = mimeType.replace("application/", "");

      //  fileName = /*file.getName()*/"Test.txt";
        Cursor returnCursor =
                getContext().getContentResolver().query(fileUri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        fileName = returnCursor.getString(nameIndex);
        returnCursor.close();
        attachtext1.setText(fileName);
        attachtext1.setVisibility(View.VISIBLE);
        attachment1.setVisibility(View.GONE);

        try {
            InputStream is = getActivity().getContentResolver().openInputStream(fileUri);
            byte[] bytesArray = new byte[is.available()];
            is.read(bytesArray);

            //write to sdcard
            /*
            File myPdf=new File(Environment.getExternalStorageDirectory(), "myPdf.pdf");
            FileOutputStream fos=new FileOutputStream(myPdf.getPath());
            fos.write(bytesArray);
            fos.close();*/

            System.out.println(bytesArray);
            fileByte = Base64.encodeToString(bytesArray, Base64.NO_WRAP);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

       /* String mimeType = getContext().getContentResolver().getType(fileUri);

        String fullFilePath =getActivity().getFilesDir()+"/"+  fileUri.getPath();
                // getPath(getContext(), fileUri);
        File file = new File(fullFilePath);
        fileName = file.getName();
        fileType = mimeType.replace("application/", "");
       // attachtext.setText(fileName);
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
*/

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions((Activity) getContext(),
                new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    public void sendtoProcessor(String EmpCode,String RequestId,String AccessRequestNo, String CategoryId,String AccessTypeId, String Attachment,String Files, String CKey){
        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<String> response = anInterface.IAMViewEDIT(EmpCode,RequestId,AccessRequestNo,CategoryId, AccessTypeId, Attachment, Files,CKey);
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.code() == HttpsURLConnection.HTTP_OK){
                    Toast.makeText(getActivity(),"Request has been sent successfully",Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((IAMViewStatusActivity)getActivity()).recreate();

    }

}
