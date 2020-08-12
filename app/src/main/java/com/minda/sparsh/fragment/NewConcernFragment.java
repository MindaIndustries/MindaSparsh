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
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.BottomUpConcernActivity;
import com.minda.sparsh.EHS_Home;
import com.minda.sparsh.R;
import com.minda.sparsh.customview.NoDefaultSpinner;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.SixMModel;
import com.minda.sparsh.services.BottomUpConcernServices;
import com.minda.sparsh.services.EHSServices;
import com.minda.sparsh.util.UriUtils;
import com.minda.sparsh.util.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dk.nodes.filepicker.uriHelper.FilePickerUriHelper;

import static android.content.Context.MODE_PRIVATE;

public class NewConcernFragment extends Fragment {

    @BindView(R.id.new_concern)
    RelativeLayout newConcern;
    @BindView(R.id.concern_date_spinner)
    TextView concernDateText;
    @BindView(R.id.unit_spinner)
    NoDefaultSpinner unitSpinner;
    @BindView(R.id.responsible_spinner)
    NoDefaultSpinner responsibleSpinner;
    @BindView(R.id.msm_reference_value)
    EditText msmReferenceValue;
    @BindView(R.id.existing_system_value)
    EditText existingSystemValue;
    @BindView(R.id.proposed_system_value)
    EditText proposedSystemValue;
    @BindView(R.id.benefit_value)
    EditText benefitValue;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.attachtext1)
    TextView attachtext1;
    @BindView(R.id.attachtext2)
    TextView attachtext2;
    @BindView(R.id.attachtext3)
    TextView attachtext3;
    @BindView(R.id.doc_view1)
    ImageView docView1;
    @BindView(R.id.doc_view2)
    ImageView docView2;
    @BindView(R.id.doc_view3)
    ImageView docView3;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    String unitcode, empCode, username;
    SharedPreferences myPref;
    ArrayList<String> unitsName = new ArrayList<String>();
    ArrayList<String> sixMNames = new ArrayList<String>();

    List<EHSUnitModel> units = new ArrayList<EHSUnitModel>();
    List<SixMModel> sixMs = new ArrayList<SixMModel>();
    ArrayAdapter<String> adapterUnit, adapterResponsible6M;
    String department;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private String mUserChoosenTask = "";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;
    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int SELECT_FILE = 3;

    private File mDestinationFile;
    byte[] bytes;
    Bitmap bmp;
    String attachmentName = "", attachmentType = "";
    TextView attachtext;
    String esFilename = "", esFilebyte = "", psfilename = "", psfilebyte = "", benFilename = "", benfilebyte = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View newConcern = inflater.inflate(R.layout.new_concern, container, false);
        ButterKnife.bind(this, newConcern);
        concernDateText.setText(getlogDate(System.currentTimeMillis()));
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        empCode = myPref.getString("Id", "Id");
        username = myPref.getString("username", "");

        msmReferenceValue.setMovementMethod(new ScrollingMovementMethod());
        existingSystemValue.setMovementMethod(new ScrollingMovementMethod());
        proposedSystemValue.setMovementMethod(new ScrollingMovementMethod());
        benefitValue.setMovementMethod(new ScrollingMovementMethod());
        ScrollingMovementMethod.getInstance();
        msmReferenceValue.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                msmReferenceValue.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }


        });
        existingSystemValue.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                existingSystemValue.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }


        });
        proposedSystemValue.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                proposedSystemValue.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }


        });

        benefitValue.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                benefitValue.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }


        });


        initUnitSpinner();
        getUnits();
        initSixMSpinner();
        getSixMList();


        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                } else {
                    //  unitcode = unitsName.get(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        responsibleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                } else {
                    department = sixMs.get(position - 1).getID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return newConcern;
    }

    @OnClick(R.id.save)
    public void onClickSave() {

        if(department.length()==0){
            Toast.makeText(getActivity(), "Select Responsible 6M", Toast.LENGTH_LONG).show();
            return;
        }
        if (msmReferenceValue.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "MSM/ Reference needs to be filled", Toast.LENGTH_LONG).show();
            return;
        }
        if (existingSystemValue.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Existing System needs to be filled", Toast.LENGTH_LONG).show();
            return;
        }
        if (proposedSystemValue.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Proposed System needs to be filled", Toast.LENGTH_LONG).show();
            return;
        }

        if (benefitValue.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Benefit needs to be filled", Toast.LENGTH_LONG).show();
            return;
        }
        if (Utility.isOnline(getActivity())) {
            saveConcern(empCode, concernDateText.getText().toString(), unitcode, department, msmReferenceValue.getText().toString(), existingSystemValue.getText().toString(), proposedSystemValue.getText().toString(), benefitValue.getText().toString(), esFilename, esFilebyte, psfilename, psfilebyte, benFilename, benfilebyte, username);
        } else {
            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.reset)
    public void onClickReset() {
        Intent in = new Intent(getActivity(), BottomUpConcernActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        getActivity().finish();
    }

    @OnClick(R.id.attachtext1)
    public void onClickattach1() {
        attachtext = attachtext1;
        selectFile();

    }

    @OnClick(R.id.attachment1)
    public void onClickattacmnt1() {
        attachtext = attachtext1;
        selectFile();

    }

    @OnClick(R.id.attachtext2)
    public void onClickattach2() {
        attachtext = attachtext2;
        selectFile();

    }

    @OnClick(R.id.attachment2)
    public void onClickattcmnt2() {
        attachtext = attachtext2;
        selectFile();
    }


    @OnClick(R.id.attachtext3)
    public void onClickattach3() {
        attachtext = attachtext3;
        selectFile();
    }

    @OnClick(R.id.attachment3)
    public void onClickattcmnt3() {
        attachtext = attachtext3;
        selectFile();

    }

    public String getlogDate(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hr = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int am_pm = cal.get(Calendar.AM_PM);
        String AM_PM;
        if (am_pm == 0) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        month = month + 1;
        String monthNo;
        if (month < 10) {
            monthNo = "0" + month;
        } else {
            monthNo = "" + month;
        }
        String dayOfMonthStr;
        if (day < 10) {
            dayOfMonthStr = "0" + day;
        } else {
            dayOfMonthStr = "" + day;
        }

        return dayOfMonthStr + "-" + monthNo + "-" + year + " " + hr + ":" + min + " " + AM_PM;
    }

    public void getUnits() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getUnits(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                units.clear();
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSUnitModel> list = (List<EHSUnitModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        units.addAll(list);
                        for (EHSUnitModel unit : units) {
                            unitsName.add(unit.getUnitCode() + ":" + unit.getUnitName());
                        }
                        if (unitsName.size() > 0) {
                            unitSpinner.setSelection(1);
                        }
                        adapterUnit.notifyDataSetChanged();
                    }
                }
            }
        }, unitcode);

    }

    public void getSixMList() {
        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.getSixM(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                sixMs.clear();
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<SixMModel> list = (List<SixMModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        sixMs.addAll(list);
                        for (SixMModel sixM : sixMs) {
                            if (sixM.getName().equalsIgnoreCase("Others") || sixM.getName().equalsIgnoreCase("Strategy")) {

                            } else {
                                sixMNames.add(sixM.getName());
                            }
                        }
                        adapterResponsible6M.notifyDataSetChanged();

                    }

                }
            }
        });
    }

    public void initUnitSpinner() {
        unitsName.clear();
        unitsName.add("Select");
        adapterUnit = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, unitsName) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
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

        adapterUnit.setDropDownViewResource(R.layout.spinner_row);
        unitSpinner.setAdapter(adapterUnit);
        unitSpinner.setSelection(0);

    }

    public void initSixMSpinner() {
        sixMNames.clear();
        sixMNames.add("Select");
        adapterResponsible6M = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, sixMNames) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
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
        adapterResponsible6M.setDropDownViewResource(R.layout.spinner_row);
        responsibleSpinner.setAdapter(adapterResponsible6M);
        responsibleSpinner.setSelection(0);
    }

    public void saveConcern(String RaisedBy, String RaisedOn, String Unit, String Department, String ReferenceNo, String ExistingSystem, String ProposedSystem, String Benefit, String ESFile, String ESFileByte, String PSFile, String PSFileByte, String BenFile, String BenFileByte, String FirstName) {
        progressBar.setVisibility(View.VISIBLE);
        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.saveConcern(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {

                }
                progressBar.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (getActivity() != null && isAdded()) {
                            Toast.makeText(getActivity(), "Successfully submitted", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getActivity(), BottomUpConcernActivity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            getActivity().finish();
                        }
                    }
                }, 1000);

            }

        }, RaisedBy, RaisedOn, Unit, Department, ReferenceNo, ExistingSystem, ProposedSystem, Benefit, ESFile, ESFileByte, PSFile, PSFileByte, BenFile, BenFileByte, FirstName);

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

        if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView1.setVisibility(View.VISIBLE);
            docView1.setImageBitmap(thumbnail);


        } else if (attachtext == attachtext2) {
            attachtext2.setText(attachmentName);
            psfilename = attachmentName;
            psfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView2.setVisibility(View.VISIBLE);
            docView2.setImageBitmap(thumbnail);


        } else {
            benFilename = attachmentName;
            attachtext3.setText(attachmentName);
            benfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView3.setVisibility(View.VISIBLE);
            docView3.setImageBitmap(thumbnail);


        }


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


        if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView1.setVisibility(View.VISIBLE);
            docView1.setImageBitmap(bm);
        } else if (attachtext == attachtext2) {
            attachtext2.setText(attachmentName);
            psfilename = attachmentName;
            psfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView2.setVisibility(View.VISIBLE);
            docView2.setImageBitmap(bm);
        } else {
            benFilename = attachmentName;
            attachtext3.setText(attachmentName);
            benfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);
            docView3.setVisibility(View.VISIBLE);
            docView3.setImageBitmap(bm);
        }

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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onSelectFile(Intent data) {

        Uri fileUri = data.getData();
        String mimeType = getActivity().getContentResolver().getType(fileUri);

        String fullFilePath = UriUtils.getPathFromUri(getActivity(), fileUri);
        File file = new File(fullFilePath);
        attachmentName = file.getName();
        attachmentType = mimeType.replace("application/","");
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

        if (attachtext == attachtext1) {
            attachtext1.setText(attachmentName);
            esFilename = attachmentName;
            esFilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);


        } else if (attachtext == attachtext2) {
            attachtext2.setText(attachmentName);
            psfilename = attachmentName;
            psfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);


        } else {
            benFilename = attachmentName;
            attachtext3.setText(attachmentName);
            benfilebyte = Base64.encodeToString(bytes, Base64.NO_WRAP);


        }
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
