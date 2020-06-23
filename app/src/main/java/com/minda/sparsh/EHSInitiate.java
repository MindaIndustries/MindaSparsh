package com.minda.sparsh;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.customview.NoDefaultSpinner;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSCategoryModel;
import com.minda.sparsh.model.EHSIdentifiedLocationModel;
import com.minda.sparsh.model.EHSObservationModel;
import com.minda.sparsh.model.EHSSubCategoryModel;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.SafetyOfficerModel;
import com.minda.sparsh.services.EHSServices;
import com.minda.sparsh.util.Utility;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EHSInitiate extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.obs_date_spinner)
    TextView observationDateSpinner;
    @BindView(R.id.unit_spinner)
    NoDefaultSpinner unitSpinner;
    @BindView(R.id.safety_officer_spinner)
    NoDefaultSpinner safetyOfficerSpinner;
    @BindView(R.id.type_spinner)
    NoDefaultSpinner typeOfObservationSpinner;
    @BindView(R.id.identified_loc_spinner)
    NoDefaultSpinner identifiedLocationSpinner;
    @BindView(R.id.category_spinner)
    NoDefaultSpinner categorySpinner;
    @BindView(R.id.sub_category_spinner)
    NoDefaultSpinner subCategorySpinner;
    @BindView(R.id.description_et)
    EditText descriptionEdit;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.reset)
    Button reset;
    @BindView(R.id.attachment)
    ImageView attachment;
    @BindView(R.id.attachtext)
    TextView attachtext;
    private File mDestinationFile;
    private String mUserChoosenTask = "";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;


    ArrayList<String> unitsName = new ArrayList<String>();
    List<EHSUnitModel> units = new ArrayList<EHSUnitModel>();
    ArrayList<String> officersName = new ArrayList<String>();
    List<SafetyOfficerModel> safetyOfficers = new ArrayList<SafetyOfficerModel>();
    ArrayList<String> observationtypeNames = new ArrayList<String>();
    List<EHSObservationModel> observationTypes = new ArrayList<EHSObservationModel>();
    ArrayList<String> identifiedLocations = new ArrayList<String>();
    List<EHSIdentifiedLocationModel> ehsIdentifiedLocations = new ArrayList<EHSIdentifiedLocationModel>();
    ArrayList<String> categories = new ArrayList<String>();
    List<EHSCategoryModel> ehsCategories = new ArrayList<EHSCategoryModel>();
    ArrayList<String> subCategories = new ArrayList<String>();
    List<EHSSubCategoryModel> ehsSubCategories = new ArrayList<EHSSubCategoryModel>();
    ArrayAdapter<String> adapterUnit, adapterSafetyOfficer, adapterObservationType, adapterIdentifiedLocation, adapterCategory, adapterSubCategory;
    String catId;
    DatePickerDialog observationDatePicker;
    Calendar cal;
    SharedPreferences myPref;
    String unitcode;
    String empCode, actId, actNo;
    String safetyOfficer, obstype, identifiedLocation, category, subcategory, locationID, subCategoryID, observationID;

    private static final int CAPTURE_FROM_CAMERA = 1;
    private static final int SELECT_FROM_GALLERY = 2;
    String ActId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ehs_initiate);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("EHS Initiate");
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        unitcode = myPref.getString("Um_div_code", "");
        empCode = myPref.getString("Id", "Id");

        initObservationDatePicker();
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
        getCategories();

        if (getIntent() != null && getIntent().hasExtra("obsDate")) {
            submit.setText("Update");
            reset.setVisibility(View.GONE);
            observationDateSpinner.setEnabled(false);
            observationDateSpinner.setTextColor(Color.parseColor("#000000"));

            if (getIntent().getStringExtra("actId") != null) {
                actId = getIntent().getStringExtra("actId");
            }
            if (getIntent().getStringExtra("actNo") != null) {
                actNo = getIntent().getStringExtra("actNo");
            }
            if (getIntent().getStringExtra("description") != null) {
                descriptionEdit.setText(getIntent().getStringExtra("description"));
            }
        } else {
            reset.setVisibility(View.VISIBLE);
            submit.setText("Submit");
            observationDateSpinner.setEnabled(true);
            observationDateSpinner.setTextColor(Color.parseColor("#000000"));
        }

        safetyOfficerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (safetyOfficers != null && safetyOfficers.size() > 0)
                    safetyOfficer = safetyOfficers.get(position - 1).getUnitOfficer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeOfObservationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obstype = observationtypeNames.get(position);
                if (observationTypes.size() > 1) {
                    if (position > 0) {
                        observationID = observationTypes.get(position - 1).getId();
                        actId = observationTypes.get(position - 1).getShortName() + "-" + unitcode+"-";
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        identifiedLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    }

    @OnClick(R.id.reset)
    public void onClickReset() {
        finish();
        startActivity(getIntent());
    }

    @OnClick(R.id.submit)
    public void onClickSubmit() {
        if (safetyOfficer != null && safetyOfficer.equals("Select") || safetyOfficer == null) {
            Toast.makeText(EHSInitiate.this, "Safety Officer not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (obstype != null && obstype.equals("Select") || obstype == null) {
            Toast.makeText(EHSInitiate.this, "Type of Observation not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (category != null && category.equals("Select") || category == null) {
            Toast.makeText(EHSInitiate.this, "Category not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (subcategory != null && subcategory.equals("Select")) {
            Toast.makeText(EHSInitiate.this, "Sub Category not selected", Toast.LENGTH_LONG).show();
            return;
        }
        if (descriptionEdit.getText().toString().equals("")) {
            Toast.makeText(EHSInitiate.this, "Please fill some description", Toast.LENGTH_LONG).show();
            return;
        }

        if (submit.getText().equals("Submit")) {

            String [] ActDate = observationDateSpinner.getText().toString().split("/");
            String newActDate = ActDate[2]+"/"+ActDate[1]+"/"+ActDate[0];
            saveEHS(empCode, newActDate, "", safetyOfficer, unitcode, descriptionEdit.getText().toString(), "", "", locationID, catId, subCategoryID, observationID, "", "", obstype, identifiedLocation);
        } else {
            String idLocaton = getIntent().getStringExtra("identifiedLoc");
            int i = idLocaton.indexOf(identifiedLocation);
            String locationId = ehsIdentifiedLocations.get(i).getID();
            String [] ActDate = observationDateSpinner.getText().toString().split("/");
            String newActDate = ActDate[2]+"/"+ActDate[1]+"/"+ActDate[0];
            updateEHS(actId, empCode, actNo, newActDate, "", getIntent().getStringExtra("safetyOfficer"), unitcode, descriptionEdit.getText().toString(), "", "", locationId, getIntent().getStringExtra("catId"), getIntent().getStringExtra("subCategory"), getIntent().getStringExtra("obsId"), "", "");
        }
    }

    @OnClick(R.id.attachtext)
    public void onClickAttachText() {

        selectFile();
    }

    @OnClick(R.id.attachment)
    public void onClickAttachment() {

        selectFile();
    }

    @OnClick(R.id.obs_date_spinner)
    public void onClickObservationDateSpinner() {
        observationDatePicker.show();
    }

    public void initObservationDatePicker() {
        cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        observationDateSpinner.setText("" + getlogDate(cal.getTimeInMillis()));
        if (getIntent().getStringExtra("obsDate") != null) {
            String givenDateString = getIntent().getStringExtra("obsDate").split(" ")[0];
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
        observationDatePicker = new DatePickerDialog(EHSInitiate.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
                observationDateSpinner.setText("" + dayOfMonthStr + "/" + monthNo + "/" + year);

            }
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
                        adapterUnit.notifyDataSetChanged();
                        if (unitsName.size() > 0) {
                            unitSpinner.setSelection(1);
                        }
                        if (getIntent().getStringExtra("unit") != null) {
                            int i = unitsName.indexOf(getIntent().getStringExtra("unit"));
                            unitSpinner.setSelection(i);

                        }
                    }

                }
            }
        }, unitcode);

    }

    public void getSafetyOfficers() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getSafetyOfficers(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                safetyOfficers.clear();
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<SafetyOfficerModel> list = (List<SafetyOfficerModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        safetyOfficers.addAll(list);
                        for (SafetyOfficerModel safetyOfficer : safetyOfficers) {
                            officersName.add(safetyOfficer.getUSName());
                        }
                        adapterSafetyOfficer.notifyDataSetChanged();
                        if (officersName.size() > 1) {
                            safetyOfficerSpinner.setSelection(1);
                        }
                        if (getIntent().getStringExtra("safetyOfficer") != null && getIntent().getStringExtra("safetyOfficer").length() > 0) {
                            int i = officersName.indexOf(getIntent().getStringExtra("safetyOfficer"));
                            safetyOfficerSpinner.setSelection(i);
                        }
                    }
                }
            }
        }, unitcode);
    }

    public void getObservationTypes() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getObservationTypes(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSObservationModel> list = (List<EHSObservationModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        observationTypes.addAll(list);
                        for (EHSObservationModel observationType : observationTypes) {
                            observationtypeNames.add(observationType.getName());
                        }
                        adapterObservationType.notifyDataSetChanged();
                        if (getIntent().getStringExtra("typeOfObs") != null) {
                            int i = observationtypeNames.indexOf(getIntent().getStringExtra("typeOfObs"));
                            typeOfObservationSpinner.setSelection(i);
                        }
                    }
                }
            }
        });
    }

    public void getIdentifiedLocations() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getIdentifiedLocations(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSIdentifiedLocationModel> list = (List<EHSIdentifiedLocationModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        ehsIdentifiedLocations.addAll(list);
                        for (EHSIdentifiedLocationModel ehsIdentifiedLocation : ehsIdentifiedLocations) {
                            identifiedLocations.add(ehsIdentifiedLocation.getName());
                        }
                        adapterIdentifiedLocation.notifyDataSetChanged();
                        if (getIntent().getStringExtra("identifiedLoc") != null) {
                            int i = identifiedLocations.indexOf(getIntent().getStringExtra("identifiedLoc"));
                            identifiedLocationSpinner.setSelection(i);
                        }
                    }
                }
            }
        });
    }

    public void getCategories() {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getCategories(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSCategoryModel> list = (List<EHSCategoryModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        ehsCategories.addAll(list);
                        for (EHSCategoryModel ehsCategory : ehsCategories) {
                            categories.add(ehsCategory.getName());
                        }
                        adapterCategory.notifyDataSetChanged();

                        if (getIntent().getStringExtra("catId") != null) {
                            // int i = ehsCategories.indexOf(getIntent().getStringExtra("catId"));
                            categorySpinner.setSelection(Integer.parseInt(getIntent().getStringExtra("catId")));
                        }

                        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                catId = ehsCategories.get(position - 1).getId();
                                category = categories.get(position);
                                subCategories.clear();
                                subCategories.add("Select");
                                initSubCategorySpinner();
                                getSubCategories(catId);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                }

            }
        }, "1");
    }

    public void getSubCategories(String catId) {
        EHSServices ehsServices = new EHSServices();
        ehsServices.getSubCategories(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSSubCategoryModel> list = (List<EHSSubCategoryModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        ehsSubCategories.addAll(list);
                        for (EHSSubCategoryModel ehsSubCategory : ehsSubCategories) {
                            subCategories.add(ehsSubCategory.getName());
                        }
                        adapterSubCategory.notifyDataSetChanged();
                        if (getIntent().getStringExtra("subCategory") != null) {
                            EHSSubCategoryModel ehsSubCategoryModel = new EHSSubCategoryModel();
                            ehsSubCategoryModel.setID(getIntent().getStringExtra("subCategory"));
                            int i = ehsSubCategories.indexOf(ehsSubCategoryModel);
                            subCategorySpinner.setSelection(i + 1);
                        }


                    }

                }
            }
        }, catId);
    }

    public void initUnitSpinner() {
        unitsName.clear();
        unitsName.add("Select");
        adapterUnit = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, unitsName) {
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

    public void initSafetyOfficerSpinner() {
        officersName.clear();
        officersName.add("Select");
        adapterSafetyOfficer = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, officersName) {
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

        adapterSafetyOfficer.setDropDownViewResource(R.layout.spinner_row);
        safetyOfficerSpinner.setAdapter(adapterSafetyOfficer);
        safetyOfficerSpinner.setSelection(0);

    }

    public void initObservationTypeSpinner() {
        observationtypeNames.clear();
        observationtypeNames.add("Select");
        adapterObservationType = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, observationtypeNames) {
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

        adapterObservationType.setDropDownViewResource(R.layout.spinner_row);
        typeOfObservationSpinner.setAdapter(adapterObservationType);
        typeOfObservationSpinner.setSelection(0);

    }

    public void initIdentifiedLocationSpinner() {
        identifiedLocations.clear();
        identifiedLocations.add("Select");
        adapterIdentifiedLocation = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, identifiedLocations) {
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

        adapterIdentifiedLocation.setDropDownViewResource(R.layout.spinner_row);
        identifiedLocationSpinner.setAdapter(adapterIdentifiedLocation);
        identifiedLocationSpinner.setSelection(0);

    }

    public void initCategorySpinner() {
        categories.clear();
        categories.add("Select");
        adapterCategory = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, categories) {
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

        adapterCategory.setDropDownViewResource(R.layout.spinner_row);
        categorySpinner.setAdapter(adapterCategory);
        categorySpinner.setSelection(0);

    }

    public void initSubCategorySpinner() {
        subCategories.clear();
        subCategories.add("Select");
        adapterSubCategory = new ArrayAdapter<String>(EHSInitiate.this, R.layout.spinner_row, subCategories) {
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
        subCategorySpinner.setAdapter(adapterSubCategory);
        subCategorySpinner.setSelection(0);

    }

    public void saveEHS(String EmpCode, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceTime, String IncidenceActionTaken, String ObservationName, String LocationName) {
        EHSServices ehsServices = new EHSServices();
        ehsServices.saveEHS(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    Toast.makeText(EHSInitiate.this, "Successfully submitted", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            onBackPressed();
                        }
                    }, 1000);

                }
            }
        }, EmpCode, actId, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceTime, IncidenceActionTaken, ObservationName, LocationName);
    }

    public void updateEHS(String ActID, String EmpCode, String ActNo, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceTime, String IncidenceActionTaken) {
        EHSServices ehsServices = new EHSServices();
        ehsServices.update(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    Toast.makeText(EHSInitiate.this, "Successfully updated", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent in = new Intent(EHSInitiate.this, EHS_Home.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                        }
                    }, 1000);


                }
            }
        }, ActID, EmpCode, ActNo, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceTime, IncidenceActionTaken);

    }


    public void selectFile() {
        requestAppPermissions();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_FROM_CAMERA);
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, SELECT_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FROM_GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAPTURE_FROM_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        Utility.saveFileToSdCard(mDestinationFile, thumbnail);
        String fileName = mDestinationFile.getAbsolutePath();
        System.out.println("fileName" + fileName);
        // addUserImage(fileName);
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (mDestinationFile != null) {
            mDestinationFile.delete();
        }
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mDestinationFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        bm = rotateImageIfRequired(EHSInitiate.this, bm, Uri.parse(mDestinationFile.toString()));
        Utility.saveFileToSdCard(mDestinationFile, bm);
        String fileName = mDestinationFile.getAbsolutePath();
        System.out.println("fileName" + fileName);


        //  addUserImage(fileName);
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
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EHSInitiate.this);
                if (items[item].equals("Take Photo")) {
                    mUserChoosenTask = "Take Photo";
                    if (result) {
                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Gallery")) {
                    mUserChoosenTask = "Choose from Gallery";
                    if (result) {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
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
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

}




