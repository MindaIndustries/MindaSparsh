package com.minda.sparsh.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
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
import android.text.method.TextKeyListener;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.EHS_Home;
import com.minda.sparsh.R;
import com.minda.sparsh.ViewEHSImage;
import com.minda.sparsh.model.EHSCategoryModel;
import com.minda.sparsh.model.EHSIdentifiedLocationModel;
import com.minda.sparsh.model.EHSObservationModel;
import com.minda.sparsh.model.EHSSubCategoryModel;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.SafetyOfficerModel;
import com.minda.sparsh.services.EHSServices;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dk.nodes.filepicker.FilePickerActivity;
import dk.nodes.filepicker.FilePickerConstants;
import dk.nodes.filepicker.uriHelper.FilePickerUriHelper;

import static android.content.Context.MODE_PRIVATE;

public class EHSInitiateFragment extends Fragment {
    @BindView(R.id.obs_date_spinner)
    TextInputEditText observationDateSpinner;
    @BindView(R.id.unit_spinner)
    AppCompatAutoCompleteTextView unitSpinner;
    @BindView(R.id.safety_officer_spinner)
    AppCompatAutoCompleteTextView safetyOfficerSpinner;
    @BindView(R.id.type_spinner)
    AppCompatAutoCompleteTextView typeOfObservationSpinner;
    @BindView(R.id.identified_loc_spinner)
    AppCompatAutoCompleteTextView identifiedLocationSpinner;
    @BindView(R.id.category)
    TextView categoryText;
    @BindView(R.id.category_spinner)
    AppCompatAutoCompleteTextView categorySpinner;
    @BindView(R.id.sub_category_spinner)
    AppCompatAutoCompleteTextView subCategorySpinner;
    @BindView(R.id.customerSpinnerLayout4)
    TextInputLayout customerSpinnerLayout4;

    @BindView(R.id.customerSpinnerLayout5)
    TextInputLayout customerSpinnerLayout5;
    @BindView(R.id.customerSpinnerLayout8)
    TextInputLayout customerSpinnerLayout8;

    @BindView(R.id.sub_category)
    TextView subCategorytext;
    @BindView(R.id.ll7)
    LinearLayout ll7;
    @BindView(R.id.description_et)
    TextInputEditText descriptionEdit;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.attachment)
    ImageView attachment;
    @BindView(R.id.attachtext)
    TextView attachtext;
    @BindView(R.id.time_selector)
    TextInputEditText timeSelector;
    @BindView(R.id.customerSpinnerLayout6)
    TextInputLayout customerSpinnerLayout6;
    @BindView(R.id.ll8)
    LinearLayout ll8;
    @BindView(R.id.action_taken_et)
    TextInputEditText actionTakenEt;
    @BindView(R.id.doc_view)
    ImageView docView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ll6)
    LinearLayout ll6;

    private File mDestinationFile;
    private String mUserChoosenTask = "";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;

    Date millisecondsdailyfrom = null, millisecondsdailyto = null;


    ArrayList<String> unitsName = new ArrayList<>();
    List<EHSUnitModel> units = new ArrayList<>();
    ArrayList<String> officersName = new ArrayList<>();
    List<SafetyOfficerModel> safetyOfficers = new ArrayList<>();
    ArrayList<String> observationtypeNames = new ArrayList<>();
    List<EHSObservationModel> observationTypes = new ArrayList<>();
    ArrayList<String> identifiedLocations = new ArrayList<>();
    List<EHSIdentifiedLocationModel> ehsIdentifiedLocations = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    List<EHSCategoryModel> ehsCategories = new ArrayList<>();
    ArrayList<String> subCategories = new ArrayList<>();
    List<EHSSubCategoryModel> ehsSubCategories = new ArrayList<>();
    ArrayAdapter<String> adapterUnit, adapterSafetyOfficer, adapterObservationType, adapterIdentifiedLocation, adapterCategory, adapterSubCategory;
    String catId;
    DatePickerDialog observationDatePicker;
    TimePickerDialog observationTimePicker;
    Calendar cal;
    SharedPreferences myPref;
    String unitcode, depucode;
    String empCode, actId, actNo;
    String safetyOfficer, obstype, identifiedLocation, category, subcategory, locationID, subCategoryID, observationID;

    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    private static final int LOAD_IMAGE_RESULTS = 3;
    String ActID;

    Calendar cal2;
    byte[] bytes;
    String attachmentName = "", attachmentType = "";
    String incidencehr = "0", incidencemin = "0", incidencezone = "";

    String imgString;
    Bitmap bmp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ehsInitiate = inflater.inflate(R.layout.ehs_initiate, container, false);
        ButterKnife.bind(this, ehsInitiate);
        cal2 = Calendar.getInstance();
        initUI();

        return ehsInitiate;
    }

    public void initUI() {
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        depucode = myPref.getString("Depu_UnitCode", "");
        if (depucode != null && depucode.length() > 0) {
            unitcode = depucode;
        }
        empCode = myPref.getString("Id", "Id");

        initObservationDatePicker();
        initTimePicker();
        initUnitSpinner();
        initSafetyOfficerSpinner();
        initObservationTypeSpinner();
        initIdentifiedLocationSpinner();
        initCategorySpinner();
        initSubCategorySpinner();
        getUnits();
        getSafetyOfficers();
        getObservationTypes();
        getIdentifiedLocations();

        if (getArguments() != null && getArguments().getString("obsDate") != null) {
            submit.setText("Update");
            reset.setVisibility(View.GONE);
            observationDateSpinner.setEnabled(false);
            observationDateSpinner.setTextColor(Color.parseColor("#000000"));

            if (getArguments().getString("actId") != null) {
                actId = getArguments().getString("actId");
            }
            if (getArguments().getString("actNo") != null) {
                actNo = getArguments().getString("actNo");
            }
            if (getArguments().getString("description") != null) {
                descriptionEdit.setText(getArguments().getString("description"));
            }

            if (getArguments().getString("catId") != null) {
                catId = getArguments().getString("catId");
            }
            if (getArguments().getString("obsId") != null) {
                observationID = getArguments().getString("obsId");
            }
            if (getArguments().getString("subCategory") != null) {
                subCategoryID = getArguments().getString("subCategory");
            }
            if (getArguments().getString("incidenceTime") != null) {
                timeSelector.setText("" + getArguments().getString("incidenceTime"));
            }
            if (getArguments().getString("incidenceAction") != null) {
                actionTakenEt.setText("" + getArguments().getString("incidenceAction"));
            }
            if (getArguments().getString("attachment") != null) {
                if (getArguments().getString("attachment").contains(".jpg") || getArguments().getString("attachment").contains(".png")) {
                    attachmentName = getArguments().getString("attachment");
                    Glide.with(getActivity()).load(RetrofitClient2.ehs_img + getArguments().getString("attachment")).into(docView);
                }
                attachtext.setText("" + getArguments().getString("attachment"));
            }


            actionTakenEt.setOnTouchListener((v, motionEvent) -> {
                if (v.getId() == R.id.action_taken_et) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if ((motionEvent.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }

                return false;
            });

            if (getArguments().getString("status") != null) {
                if (getArguments().getString("status").equals("1")) {
                    submit.setVisibility(View.VISIBLE);
                    unitSpinner.setEnabled(true);
                    safetyOfficerSpinner.setEnabled(true);
                    typeOfObservationSpinner.setEnabled(true);
                    typeOfObservationSpinner.setKeyListener(TextKeyListener.getInstance(false, TextKeyListener.Capitalize.WORDS));
                    identifiedLocationSpinner.setEnabled(true);
                    identifiedLocationSpinner.setKeyListener(TextKeyListener.getInstance(false, TextKeyListener.Capitalize.WORDS));
                    categorySpinner.setEnabled(true);
                    categorySpinner.setKeyListener(TextKeyListener.getInstance(false, TextKeyListener.Capitalize.WORDS));
                    subCategorySpinner.setKeyListener(TextKeyListener.getInstance(false, TextKeyListener.Capitalize.WORDS));
                    subCategorySpinner.setEnabled(true);
                    descriptionEdit.setEnabled(true);
                    actionTakenEt.setEnabled(true);
                    attachtext.setEnabled(true);
                    attachment.setEnabled(true);
                    timeSelector.setEnabled(true);

                    if (getArguments().getString("assigned").equalsIgnoreCase("True")) {
                        submit.setVisibility(View.GONE);
                        unitSpinner.setEnabled(false);
                        safetyOfficerSpinner.setEnabled(false);
                        typeOfObservationSpinner.setEnabled(false);
                        identifiedLocationSpinner.setEnabled(false);
                        categorySpinner.setEnabled(false);
                        subCategorySpinner.setEnabled(false);
                        descriptionEdit.setEnabled(false);


                   /*     actionTakenEt.setFocusableInTouchMode(false);
                        actionTakenEt.clearFocus();
                  //      actionTakenEt.setEnabled(false);
                   */     attachtext.setEnabled(false);
                        attachment.setEnabled(false);
                        timeSelector.setEnabled(false);

                    }

                } else {
                    submit.setVisibility(View.GONE);
                    unitSpinner.setEnabled(false);
                    safetyOfficerSpinner.setEnabled(false);
                    typeOfObservationSpinner.setEnabled(false);
                    identifiedLocationSpinner.setEnabled(false);
                    categorySpinner.setEnabled(false);
                    subCategorySpinner.setEnabled(false);
                    descriptionEdit.setEnabled(false);
                  /*  actionTakenEt.setFocusableInTouchMode(false);
                    actionTakenEt.clearFocus();

                    //   actionTakenEt.setEnabled(false);
                  */  attachtext.setEnabled(false);
                    attachment.setEnabled(false);
                    timeSelector.setEnabled(false);
                }
            }


        } else {
            reset.setVisibility(View.VISIBLE);
            submit.setText("Submit");
            observationDateSpinner.setEnabled(true);
            observationDateSpinner.setTextColor(Color.parseColor("#000000"));
        }

        safetyOfficerSpinner.setOnItemClickListener((adapterView, view, position, l) -> {
            if (position == 0) {
                return;
            }
            if (officersName != null && officersName.size() > 0)
                safetyOfficer = safetyOfficers.get(position).getUnitOfficer();

        });
/*
        safetyOfficerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                if (officersName != null && officersName.size() > 1)
                    safetyOfficer = safetyOfficers.get(position - 1).getUnitOfficer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        typeOfObservationSpinner.setOnItemClickListener((adapterView, view, position, l) -> {
            obstype = observationtypeNames.get(position);
            if (observationTypes.size() > 0) {
                if (position >=0) {
                    observationID = observationTypes.get(position).getId();
                    ActID = observationTypes.get(position).getShortName() + "-" + unitcode + "-";
                    if (observationID.equals("4")) {
                        categories.clear();
                        ehsCategories.clear();
                     //   categories.add("Select");
                        adapterCategory.notifyDataSetChanged();
                        subCategorySpinner.setVisibility(View.GONE);
                        customerSpinnerLayout5.setVisibility(View.GONE);
                        subCategorytext.setVisibility(View.GONE);
                    //    ll7.setVisibility(View.GONE);
                     //   time.setVisibility(View.VISIBLE);
                        timeSelector.setVisibility(View.VISIBLE);
                        customerSpinnerLayout6.setVisibility(View.VISIBLE);
                     //   ll8.setVisibility(View.VISIBLE);
                        customerSpinnerLayout8.setVisibility(View.VISIBLE);
                        actionTakenEt.setVisibility(View.VISIBLE);
                    //    categoryText.setVisibility(View.VISIBLE);
                        categorySpinner.setVisibility(View.VISIBLE);
                     //   categorySpinner.setText("");
                        customerSpinnerLayout4.setVisibility(View.VISIBLE);
                    //    ll6.setVisibility(View.VISIBLE);

                    } else if (observationID.equals("3")) {
                        categoryText.setVisibility(View.GONE);
                        categorySpinner.setVisibility(View.GONE);
                        customerSpinnerLayout4.setVisibility(View.GONE);
                        ll6.setVisibility(View.GONE);
                        subCategorySpinner.setVisibility(View.GONE);
                        customerSpinnerLayout5.setVisibility(View.GONE);
                        subCategorytext.setVisibility(View.GONE);
                        ll7.setVisibility(View.GONE);
                        timeSelector.setVisibility(View.GONE);
                        customerSpinnerLayout6.setVisibility(View.GONE);


                    } else {
                        categories.clear();
                        ehsCategories.clear();
                        //     categories.add("Select");
                        adapterCategory.notifyDataSetChanged();
                        subCategorySpinner.setVisibility(View.VISIBLE);
                       // subCategorySpinner.setText("");
                        customerSpinnerLayout5.setVisibility(View.VISIBLE);

                        // subCategorytext.setVisibility(View.VISIBLE);
                      //  ll7.setVisibility(View.VISIBLE);
                     //   time.setVisibility(View.GONE);
                        timeSelector.setVisibility(View.GONE);
                        customerSpinnerLayout6.setVisibility(View.GONE);
                      //  ll8.setVisibility(View.GONE);
                        customerSpinnerLayout8.setVisibility(View.GONE);
                        actionTakenEt.setVisibility(View.GONE);
                   //     categoryText.setVisibility(View.VISIBLE);
                        categorySpinner.setVisibility(View.VISIBLE);
                   //     categorySpinner.setText("");
                        customerSpinnerLayout4.setVisibility(View.VISIBLE);
                        //   ll6.setVisibility(View.VISIBLE);


                    }
                    getCategories(observationID);


                }
            }

        });

/*
        typeOfObservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obstype = observationtypeNames.get(position);
                if (observationTypes.size() > 1) {
                    if (position > 0) {
                        observationID = observationTypes.get(position - 1).getId();
                        ActID = observationTypes.get(position - 1).getShortName() + "-" + unitcode + "-";
                        if (observationID.equals("4")) {
                            categories.clear();
                            ehsCategories.clear();
                            categories.add("Select");
                            adapterCategory.notifyDataSetChanged();
                            subCategorySpinner.setVisibility(View.GONE);
                            subCategorytext.setVisibility(View.GONE);
                            ll7.setVisibility(View.GONE);
                            time.setVisibility(View.VISIBLE);
                            timeSelector.setVisibility(View.VISIBLE);
                            ll8.setVisibility(View.VISIBLE);
                            actiontaken.setVisibility(View.VISIBLE);
                            actionTakenEt.setVisibility(View.VISIBLE);
                            categoryText.setVisibility(View.VISIBLE);
                            categorySpinner.setVisibility(View.VISIBLE);
                            ll6.setVisibility(View.VISIBLE);

                        } else if (observationID.equals("3")) {
                            categoryText.setVisibility(View.GONE);
                            categorySpinner.setVisibility(View.GONE);
                            ll6.setVisibility(View.GONE);
                            subCategorySpinner.setVisibility(View.GONE);
                            subCategorytext.setVisibility(View.GONE);
                            ll7.setVisibility(View.GONE);

                        } else {
                            categories.clear();
                            ehsCategories.clear();
                            categories.add("Select");
                            adapterCategory.notifyDataSetChanged();
                            subCategorySpinner.setVisibility(View.VISIBLE);
                            subCategorytext.setVisibility(View.VISIBLE);
                            ll7.setVisibility(View.VISIBLE);
                            time.setVisibility(View.GONE);
                            timeSelector.setVisibility(View.GONE);
                            ll8.setVisibility(View.GONE);
                            actiontaken.setVisibility(View.GONE);
                            actionTakenEt.setVisibility(View.GONE);
                            categoryText.setVisibility(View.VISIBLE);
                            categorySpinner.setVisibility(View.VISIBLE);
                            ll6.setVisibility(View.VISIBLE);


                        }
                        getCategories(observationID);


                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        identifiedLocationSpinner.setOnItemClickListener((adapterView, view, position, l) -> {
            identifiedLocation = identifiedLocations.get(position);
            if (ehsIdentifiedLocations.size() > 0) {
                if (position >= 0)
                    locationID = ehsIdentifiedLocations.get(position).getID();
            }

        });
      /*  identifiedLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                identifiedLocation = identifiedLocations.get(position);
                if (ehsIdentifiedLocations.size() > 0) {
                    if (position > 0)
                        locationID = ehsIdentifiedLocations.get(position - 1).getID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      */
        subCategorySpinner.setOnItemClickListener((adapterView, view, position, l) -> {
            subcategory = subCategories.get(position);
            if (ehsSubCategories.size() > 1) {
                if (position >= 0)
                    subCategoryID = ehsSubCategories.get(position).getID();
            }

        });

/*
        subCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subcategory = subCategories.get(position);
                if (ehsSubCategories.size() > 1) {
                    if (position > 0)
                        subCategoryID = ehsSubCategories.get(position - 1).getID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

    }


    @OnClick(R.id.reset)
    public void onClickReset() {
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());
    }

    @OnClick(R.id.submit)
    public void onClickSubmit() {
        if (safetyOfficer != null && safetyOfficer.equals("Select") || safetyOfficer == null) {
            Toast.makeText(getActivity(), "Safety Officer not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (obstype != null && obstype.equals("Select") || obstype == null) {
            Toast.makeText(getActivity(), "Type of Observation not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (category != null && category.equals("Select") || category == null) {
            if (!obstype.equals("Near Miss Reporting")) {
                Toast.makeText(getActivity(), "Category not selected", Toast.LENGTH_LONG).show();
                return;
            }

        }
        if (subcategory != null && subcategory.equals("Select")) {
            if (!obstype.equals("Near Miss Reporting")) {
                Toast.makeText(getActivity(), "Sub Category not selected", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (descriptionEdit.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please fill some description", Toast.LENGTH_LONG).show();
            return;
        }

        if (observationID.equals("4")) {
            subCategoryID = "0";
            String[] incidencetime = timeSelector.getText().toString().split(":");
            incidencehr = incidencetime[0];
            String[] min_am = incidencetime[1].split(" ");
            if (min_am.length != 0) {
                incidencemin = min_am[0];
            }
            if (min_am.length == 2) {
                incidencezone = min_am[1];
            }
        }
        if (observationID.equals("3")) {
            subCategoryID = "0";
            catId = "0";
        }

        if (submit.getText().equals("Submit")) {
            String[] ActDate = observationDateSpinner.getText().toString().split("/");
            String newActDate = ActDate[2] + "/" + ActDate[1] + "/" + ActDate[0];
            saveEHS(empCode, newActDate, "", safetyOfficer, unitcode, descriptionEdit.getText().toString(), attachmentName, attachmentType, locationID, catId, subCategoryID, observationID, incidencehr, incidencemin, incidencezone, actionTakenEt.getText().toString(), obstype, identifiedLocation);
        } else {

            String[] ActDate = observationDateSpinner.getText().toString().split("/");
            String newActDate = ActDate[2] + "/" + ActDate[1] + "/" + ActDate[0];
          /*  String [] incidencetime = timeSelector.getText().toString().split(":");
            incidencehr = incidencetime[0];
            String [] min_am = incidencetime[1].split(" ");
            incidencemin = min_am[0];
            incidencezone = min_am[1];
          */
            updateEHS(actId, empCode, actNo, newActDate, "", getArguments().getString("safetyOfficer"), unitcode, descriptionEdit.getText().toString(), attachmentName, attachmentType, locationID, catId, subCategoryID, observationID, incidencehr, incidencemin, incidencezone, actionTakenEt.getText().toString());
        }
    }

    @OnClick(R.id.attachtext)
    public void onClickAttachText() {
        selectFile();
    }

    @OnClick(R.id.doc_view)
    public void onClickViewDoc() {

        if (getArguments() != null && getArguments().getString("attachment") != null) {
            Intent in = new Intent(getActivity(), ViewEHSImage.class);
            in.putExtra("attachment", attachmentName);
            if(getActivity()!=null && isAdded())
                getActivity().startActivity(in);
        } else if (bmp != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Intent in = new Intent(getActivity(), ViewEHSImage.class);
            in.putExtra("bitmap", byteArray);
            if(getActivity()!=null && isAdded())
            getActivity().startActivity(in);

        }
    }

    @OnClick(R.id.attachment)
    public void onClickAttachment() {
        selectFile();
    }

    @OnClick(R.id.time_selector)
    public void onClicktimeSelector() {
        observationTimePicker.show();
    }

    @OnClick(R.id.obs_date_spinner)
    public void onClickObservationDateSpinner() {
        observationDatePicker.show();
    }

    public void initTimePicker() {
        final Calendar cal1 = Calendar.getInstance();
        final int hour_init, minute_init;
        cal1.add(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        cal1.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        hour_init = cal1.get(Calendar.HOUR_OF_DAY);
        minute_init = cal1.get(Calendar.MINUTE);
        cal1.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal1.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal1.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        millisecondsdailyto = cal1.getTime();

        observationTimePicker = new TimePickerDialog(getActivity(), (view, hourOfDay, minute) -> {
            String AM_PM;
            if (cal1.get(Calendar.AM_PM) == Calendar.AM) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            cal1.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal1.set(Calendar.MINUTE, minute);
            cal1.set(Calendar.AM_PM, cal1.get(Calendar.AM_PM));
            cal1.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
            cal1.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            cal1.set(Calendar.YEAR, cal.get(Calendar.YEAR));

            millisecondsdailyto = cal1.getTime();


            if (hourOfDay > 12) {
                hourOfDay -= 12;
            }
            cal2.setTimeInMillis(System.currentTimeMillis());

            if (millisecondsdailyto.before(cal2.getTime())) {
                String minute_str = null;
                if (minute < 10) {
                    minute_str = "0" + minute;
                } else {
                    minute_str = "" + minute;
                }
                timeSelector.setText("" + hourOfDay + ":" + minute_str + " " + AM_PM);
            } else {
                timeSelector.setText("");

                Toast.makeText(getActivity(), "Invalid time selected", Toast.LENGTH_LONG).show();
            }
        }, hour_init, minute_init, false);
    }

    public void initObservationDatePicker() {
        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        millisecondsdailyfrom = cal.getTime();
        observationDateSpinner.setText("" + getlogDate(cal.getTimeInMillis()));
        try {

            if (getArguments() != null) {
                if (getArguments().getString("obsDate") != null) {
                    String givenDateString = getArguments().getString("obsDate").split(" ")[0];
                    String givenDate[] = givenDateString.split("/");
                    String monthgiven;
                    String daygiven;
                    if (Integer.parseInt(givenDate[0].toString()) < 10) {
                        monthgiven = "0" + givenDate[0];
                    } else {
                        monthgiven = givenDate[0];
                    }

                    if (Integer.parseInt(givenDate[1].toString()) < 10) {
                        daygiven = "0" + givenDate[1];
                    } else {
                        daygiven = givenDate[1];
                    }
                    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(daygiven));
                    cal.set(Calendar.MONTH, Integer.parseInt(monthgiven) - 1);
                    cal.set(Calendar.YEAR, Integer.parseInt(givenDate[2]));
                    millisecondsdailyfrom = cal.getTime();

                    observationDateSpinner.setText("" + getlogDate(cal.getTimeInMillis()));



           /* String givenDateFull = daygiven+"/"+monthgiven+"/"+givenDate[2];
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            try {
                Date mDate = sdf.parse(givenDateFull);
                long timeInMilliseconds = mDate.getTime();
                observationDateSpinner.setText(""+getlogDate(timeInMilliseconds));
                System.out.println("Date in milli :: " + timeInMilliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        observationDatePicker = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            int mMonth = month + 1;
            String monthNo;
            if (mMonth < 10) {
                monthNo = "0" + mMonth;
            } else {
                monthNo = "" + mMonth;
            }
            String dayOfMonthStr;
            if (dayOfMonth < 10) {
                dayOfMonthStr = "0" + dayOfMonth;
            } else {
                dayOfMonthStr = "" + dayOfMonth;
            }
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            millisecondsdailyfrom = cal.getTime();
            timeSelector.setText("");

            observationDateSpinner.setText("" + dayOfMonthStr + "/" + monthNo + "/" + year);

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        try {

            observationDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 10000);
            long previoustime = Calendar.getInstance().getTimeInMillis() - (7 * 24 * 60 * 60 * 1000);
            observationDatePicker.getDatePicker().setMinDate((previoustime));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getlogDate(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
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

        return dayOfMonthStr + "/" + monthNo + "/" + year;
    }


    public void getUnits() {

        EHSServices ehsServices = new EHSServices();
        ehsServices.getUnits(carotResponse -> {
            units.clear();
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<EHSUnitModel> list = (List<EHSUnitModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    units.addAll(list);
                    for (EHSUnitModel unit : units) {
                        unitsName.add(unit.getUnitCode() + ":" + unit.getUnitName());
                    }
                    adapterUnit.notifyDataSetChanged();
                    if (unitsName.size() > 0) {
                        unitSpinner.setText(unitsName.get(0));
                    }
                    if (getArguments() != null && getArguments().getString("unit") != null) {
                        int i = unitsName.indexOf(getArguments().getString("unit"));
                        if(i>=0)
                        unitSpinner.setText(unitsName.get(i));

                    }
                }

            }
        }, unitcode);

    }

    public void getSafetyOfficers() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getSafetyOfficers(carotResponse -> {
            safetyOfficers.clear();
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<SafetyOfficerModel> list = (List<SafetyOfficerModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    safetyOfficers.addAll(list);
                    for (SafetyOfficerModel safetyOfficer : safetyOfficers) {
                        officersName.add(safetyOfficer.getUSName());
                    }
                    adapterSafetyOfficer.notifyDataSetChanged();
                    if (officersName.size() > 0) {
                        safetyOfficerSpinner.setText(officersName.get(0));
                        safetyOfficer = safetyOfficers.get(0).getUnitOfficer();
                    }
                    if (getArguments() != null && getArguments().getString("safetyOfficer") != null && getArguments().getString("safetyOfficer").length() > 0) {
                        int i = officersName.indexOf(getArguments().getString("safetyOfficer"));
                        if(i>=0)
                        safetyOfficerSpinner.setText(officersName.get(i));
                    }
                }
            }
        }, unitcode);
    }

    public void getObservationTypes() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getObservationTypes(carotResponse -> {

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<EHSObservationModel> list = (List<EHSObservationModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    observationTypes.addAll(list);
                    for (EHSObservationModel observationType : observationTypes) {
                        observationtypeNames.add(observationType.getName());
                    }
                    adapterObservationType.notifyDataSetChanged();
                    if (getArguments() != null && getArguments().getString("typeOfObs") != null) {
                        int i = observationtypeNames.indexOf(getArguments().getString("typeOfObs"));
                        typeOfObservationSpinner.setText(observationtypeNames.get(i));
                        observationID = observationTypes.get(i).getId();
                        obstype = observationtypeNames.get(i);
                        if (observationTypes.size() > 0) {
                            if (i >=0) {
                                observationID = observationTypes.get(i).getId();
                                ActID = observationTypes.get(i).getShortName() + "-" + unitcode + "-";
                                if (observationID.equals("4")) {
                                    categories.clear();
                                    ehsCategories.clear();
                                    //   categories.add("Select");
                                    adapterCategory.notifyDataSetChanged();
                                    subCategorySpinner.setVisibility(View.GONE);
                                    customerSpinnerLayout5.setVisibility(View.GONE);
                                    subCategorytext.setVisibility(View.GONE);
                                    //    ll7.setVisibility(View.GONE);
                                    //   time.setVisibility(View.VISIBLE);
                                    timeSelector.setVisibility(View.VISIBLE);
                                    customerSpinnerLayout6.setVisibility(View.VISIBLE);
                                    //   ll8.setVisibility(View.VISIBLE);
                                    customerSpinnerLayout8.setVisibility(View.VISIBLE);
                                    actionTakenEt.setVisibility(View.VISIBLE);
                                    //    categoryText.setVisibility(View.VISIBLE);
                                    categorySpinner.setVisibility(View.VISIBLE);
                                //    categorySpinner.setText("");
                                    customerSpinnerLayout4.setVisibility(View.VISIBLE);
                                    //    ll6.setVisibility(View.VISIBLE);

                                } else if (observationID.equals("3")) {
                                    categoryText.setVisibility(View.GONE);
                                    categorySpinner.setVisibility(View.GONE);
                                    customerSpinnerLayout4.setVisibility(View.GONE);
                                    ll6.setVisibility(View.GONE);
                                    subCategorySpinner.setVisibility(View.GONE);
                                    customerSpinnerLayout5.setVisibility(View.GONE);
                                    subCategorytext.setVisibility(View.GONE);
                                    ll7.setVisibility(View.GONE);

                                } else {
                                    categories.clear();
                                    ehsCategories.clear();
                                    //     categories.add("Select");
                                    adapterCategory.notifyDataSetChanged();
                                    subCategorySpinner.setVisibility(View.VISIBLE);
                               //     subCategorySpinner.setText("");
                                    customerSpinnerLayout5.setVisibility(View.VISIBLE);

                                    // subCategorytext.setVisibility(View.VISIBLE);
                                    //  ll7.setVisibility(View.VISIBLE);
                                    //   time.setVisibility(View.GONE);
                                    timeSelector.setVisibility(View.GONE);
                                    customerSpinnerLayout6.setVisibility(View.GONE);
                                    //  ll8.setVisibility(View.GONE);
                                    customerSpinnerLayout8.setVisibility(View.GONE);
                                    actionTakenEt.setVisibility(View.GONE);
                                    //     categoryText.setVisibility(View.VISIBLE);
                                    categorySpinner.setVisibility(View.VISIBLE);
                             //       categorySpinner.setText("");
                                    customerSpinnerLayout4.setVisibility(View.VISIBLE);
                                    //   ll6.setVisibility(View.VISIBLE);


                                }
                                getCategories(observationID);


                            }
                        }


                    }
                }
            }
        });
    }

    public void getIdentifiedLocations() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getIdentifiedLocations(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<EHSIdentifiedLocationModel> list = (List<EHSIdentifiedLocationModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    ehsIdentifiedLocations.addAll(list);
                    for (EHSIdentifiedLocationModel ehsIdentifiedLocation : ehsIdentifiedLocations) {
                        identifiedLocations.add(ehsIdentifiedLocation.getName());
                    }
                    adapterIdentifiedLocation.notifyDataSetChanged();
                    if (getArguments() != null && getArguments().getString("identifiedLoc") != null) {
                        int i = identifiedLocations.indexOf(getArguments().getString("identifiedLoc"));
                       // identifiedLocationSpinner.setSelection(i);
                        identifiedLocationSpinner.setText(identifiedLocations.get(i));
                        locationID = ehsIdentifiedLocations.get(i).getID();
                    }
                }
            }
        });
    }

    public void getCategories(String observationID) {
        categories.clear();
        ehsCategories.clear();
        EHSServices ehsServices = new EHSServices();
        ehsServices.getCategories(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<EHSCategoryModel> list = (List<EHSCategoryModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    ehsCategories.addAll(list);
                    for (EHSCategoryModel ehsCategory : ehsCategories) {
                        categories.add(ehsCategory.getName());
                    }
                    categorySpinner.setText("");
                    subCategorySpinner.setText("");
                    subCategoryID = "0";
                    catId = "0";
                    adapterCategory = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
                    categorySpinner.setAdapter(adapterCategory);
                    adapterCategory.notifyDataSetChanged();

                    if (getArguments() != null && getArguments().getString("catId") != null) {
                        for (int i = 0; i < ehsCategories.size(); i++) {
                            if (getArguments().getString("catId").equals(ehsCategories.get(i).getId())) {
                                categorySpinner.setText(categories.get(i));
                                catId = ehsCategories.get(i).getId();
                                getSubCategories(catId);
                            }
                        }
                    }


                    categorySpinner.setOnItemClickListener((adapterView, view, position, l) -> {
                        if (position >= 0) {
                            catId = ehsCategories.get(position).getId();
                            category = categories.get(position);
                            subCategories.clear();
                      //      subCategories.add("Select");
                            initSubCategorySpinner();
                            getSubCategories(catId);
                        } else {
                            subCategories.clear();
                         //   subCategories.add("Select");
                            adapterSubCategory.notifyDataSetChanged();
                        }
                    });
/*
                    categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position > 0) {
                                catId = ehsCategories.get(position - 1).getId();
                                category = categories.get(position);
                                subCategories.clear();
                                subCategories.add("Select");
                                initSubCategorySpinner();
                                getSubCategories(catId);
                            } else {
                                subCategories.clear();
                                subCategories.add("Select");
                                adapterSubCategory.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
*/
                }

            }

        }, observationID);
    }

    public void getSubCategories(String catId) {
        subCategories.clear();
      //  subCategories.add("Select");
        ehsSubCategories.clear();
        EHSServices ehsServices = new EHSServices();
        ehsServices.getSubCategories(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                List<EHSSubCategoryModel> list = (List<EHSSubCategoryModel>) carotResponse.getData();
                if (list != null && list.size() > 0) {
                    ehsSubCategories.addAll(list);
                    for (EHSSubCategoryModel ehsSubCategory : ehsSubCategories) {
                        subCategories.add(ehsSubCategory.getName());
                    }
                    subCategorySpinner.setText("");
                    subCategoryID = "0";
                    adapterSubCategory = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subCategories);
                    subCategorySpinner.setAdapter(adapterSubCategory);
                    adapterSubCategory.notifyDataSetChanged();
                    try {
                        if (getArguments() != null && getArguments().getString("subCategory") != null) {
                            EHSSubCategoryModel ehsSubCategoryModel = new EHSSubCategoryModel();
                            ehsSubCategoryModel.setID(getArguments().getString("subCategory"));
                            int i = ehsSubCategories.indexOf(ehsSubCategoryModel);
                            subCategoryID = getArguments().getString("subCategory");
                            subCategorySpinner.setText(subCategories.get(i));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }, catId);
    }

    public void initUnitSpinner() {
        unitsName.clear();
   //     unitsName.add("Select");
        adapterUnit = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, unitsName);/* {
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
                                        @NotNull ViewGroup parent) {
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
*/
    //    adapterUnit.setDropDownViewResource(R.layout.spinner_row);
        unitSpinner.setAdapter(adapterUnit);
       // unitSpinner.setSelection(0);

    }

    public void initSafetyOfficerSpinner() {
        officersName.clear();
     //   officersName.add("Select");
        adapterSafetyOfficer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, officersName); /*{
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
                                        @NotNull ViewGroup parent) {
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

        adapterSafetyOfficer.setDropDownViewResource(R.layout.spinner_row);*/
        safetyOfficerSpinner.setAdapter(adapterSafetyOfficer);
      //  safetyOfficerSpinner.setSelection(0);

    }

    public void initObservationTypeSpinner() {
        observationtypeNames.clear();
      //  observationtypeNames.add("Select");
        adapterObservationType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, observationtypeNames);/* {
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
        };*/

      //  adapterObservationType.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeOfObservationSpinner.setAdapter(adapterObservationType);
     //   typeOfObservationSpinner.setSelection(0);

    }

    public void initIdentifiedLocationSpinner() {
        identifiedLocations.clear();
       // identifiedLocations.add("Select");
        adapterIdentifiedLocation = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, identifiedLocations);/* {
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

        adapterIdentifiedLocation.setDropDownViewResource(R.layout.spinner_row);*/
        identifiedLocationSpinner.setAdapter(adapterIdentifiedLocation);
      //  identifiedLocationSpinner.setSelection(0);

    }

    public void initCategorySpinner() {
        categories.clear();
      //  categories.add("Select");
        adapterCategory = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);/* {
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

        adapterCategory.setDropDownViewResource(R.layout.spinner_row);*/
        categorySpinner.setAdapter(adapterCategory);

    }

    public void initSubCategorySpinner() {
        adapterSubCategory = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subCategories);/* {
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

        adapterSubCategory.setDropDownViewResource(R.layout.spinner_row);
*/        subCategorySpinner.setAdapter(adapterSubCategory);

    }

    public void saveEHS(final String EmpCode, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, final String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceHour, String IncidenceMin, String IncidenceZone, String IncidenceActionTaken, final String ObservationName, final String LocationName) {

        progressBar.setVisibility(View.VISIBLE);
        EHSServices ehsServices = new EHSServices();
        ehsServices.saveEHS(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                String[] response;

                if (((String) carotResponse.getData()).contains("/")) {
                    response = ((String) carotResponse.getData()).split("/");
                    uploadFile(response[1], imgString);
                    sendMail(EmpCode, ObservationName, LocationName, Description, ((String) carotResponse.getData()).split("/")[0], unitcode);

                } else {
                    sendMail(EmpCode, ObservationName, LocationName, Description, (String) carotResponse.getData(), unitcode);
                }
            }
            progressBar.setVisibility(View.GONE);

        }, EmpCode, ActID, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceHour, IncidenceMin, IncidenceZone, IncidenceActionTaken, ObservationName, LocationName);
    }

    public void updateEHS(String ActID, final String EmpCode, final String ActNo, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, final String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceHour, String IncidenceMin, String IncidenceZone, String IncidenceActionTaken) {
        EHSServices ehsServices = new EHSServices();
        progressBar.setVisibility(View.VISIBLE);
        ehsServices.update(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                if (carotResponse.getData() != null) {
                    uploadFile(carotResponse.getData().toString(), imgString);
                    sendMail(EmpCode, obstype, identifiedLocation, Description, ActNo, unitcode);
                }
            }
            progressBar.setVisibility(View.GONE);

        }, ActID, EmpCode, ActNo, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceHour, IncidenceMin, IncidenceZone, IncidenceActionTaken);

    }


    public void selectFile() {
        requestAppPermissions();
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

    private void fileIntent() {
        chooseFile();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FROM_GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAPTURE_FROM_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == LOAD_IMAGE_RESULTS)
                onSelectFile(data);
        }
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
        imgString = Base64.encodeToString(bytes,
                Base64.NO_WRAP);
        attachmentName = fileName;
        attachtext.setText(attachmentName);
        attachmentType = "jpg";
        bmp = thumbnail;
        docView.setImageBitmap(thumbnail);


        // addUserImage(fileName);
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
        docView.setImageBitmap(bm);
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getName();
        System.out.println("fileName" + fileName);
        attachmentName = fileName;
        attachmentType = "jpg";
        attachtext.setText(attachmentName);
        bytes = getBytesFromBitmap(bm);
        imgString = Base64.encodeToString(bytes, Base64.NO_WRAP);
        bmp = bm;

        //  addUserImage(fileName);
    }


    private String onSelectFile(Intent data) {

       /*String Filepath = data.getData().getPath();
       imgString = getStringFile(new File(Filepath));
       return Filepath;*/


        File file = FilePickerUriHelper.getFile(getActivity(), data);
        attachmentName = file.getName();
        attachmentType = "doc";
        attachtext.setText(attachmentName);
        return getStringFile(file);




       /* Uri selectedFileUri = data.getData();
        String selectedFilePath = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            selectedFilePath = FilePath.getPath(getActivity(), selectedFileUri);
        }
        try {
            File f = new File(selectedFilePath);
            imgString = getStringFile(f);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

       /* Uri uri = data.getData();
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String path = myFile.getAbsolutePath();
        String displayName = null;

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                 //   txtFilename.setText(displayName);
                    attachmentName = displayName;

                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
         //   txtFilename.setText(displayName);
            attachmentName = displayName;

        }

        attachmentType="doc";
     //   bytes =
                convertFileToByteArray(myFile);
       // imgString = getStringFile(myFile);*/
    }


    public String getStringFile(File f) {
        InputStream inputStream;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getPath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    public byte[] convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 11];
            int bytesRead;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();

            Log.e("Byte array", ">" + byteArray);
            imgString = Base64.encodeToString(byteArray, Base64.NO_WRAP);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
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

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",/*"Choose Document",*/
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
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

            }
            /*else if(items[item].equals("Choose Document")){
                mUserChoosenTask = "Choose Document";
                if(result){
                    fileIntent();
                }

            }
            */
            else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
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

    public void sendMail(String Empcode, String ObservationName, String Location, String description, String ActNo, String UnitCode) {
        EHSServices ehsServices = new EHSServices();
        progressBar.setVisibility(View.VISIBLE);
        ehsServices.sendmail(carotResponse -> new Handler().postDelayed(() -> {

            if (getActivity() != null && isAdded()) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Successfully submitted", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getActivity(), EHS_Home.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                getActivity().finish();
            }
        }, 1000), Empcode, ObservationName, Location, description, ActNo, UnitCode);
    }

    public void uploadFile(String attachmentName, String bytes) {
        progressBar.setVisibility(View.VISIBLE);

        EHSServices ehsServices = new EHSServices();
        ehsServices.uploadFile(carotResponse -> progressBar.setVisibility(View.GONE), attachmentName, bytes);

    }


    private void chooseFile() {
       /* try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/*");
            try {
                startActivityForResult(intent, LOAD_IMAGE_RESULTS);

            } catch (ActivityNotFoundException e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Intent in = new Intent(getActivity(), FilePickerActivity.class);
        in.putExtra(FilePickerConstants.FILE, true);
        in.putExtra(FilePickerConstants.MULTIPLE_TYPES, new String[]{/*FilePickerConstants.MIME_IMAGE,*/ FilePickerConstants.MIME_PDF, FilePickerConstants.MIME_TEXT_PLAIN, "application/xls", "application/docx", "application/doc"});
        startActivityForResult(in, LOAD_IMAGE_RESULTS);


    }

/*
    private Bitmap uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(Uri.fromFile(new File(selectedFileUri.getPath())), "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
            return  image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
*/
}




