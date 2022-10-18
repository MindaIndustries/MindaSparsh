package com.minda.sparsh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AbnormalityNameModel;
import com.minda.sparsh.model.AssignResponseModel;
import com.minda.sparsh.model.GetAbnormalityImage_Model;
import com.minda.sparsh.services.AbnormalityServices;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewImageActivity extends AppCompatActivity {
    ImageView Im_before, Im_after, im_back;
    int AbnormalID;
    private ProgressDialog progress = null;
    TextView tv_discription, tv_action;
    LinearLayout lay_afterimage;

    ArrayAdapter<String> actionArrayAdapter;
    ArrayList<String> actionName = new ArrayList<>();
    AppCompatAutoCompleteTextView actionSpinner;
    MaterialAutoCompleteTextView targetDate;
    TextInputLayout assignto;
    AppCompatAutoCompleteTextView assignedto;
    Button submit;
    TextInputLayout customSpinnerLayout1;
    LinearLayout action_layout;

    DatePickerDialog datePicker;
    Calendar calendar;
    String year, empCode;
    SharedPreferences myPref;
    ArrayList<String> names = new ArrayList<>();
    List<AbnormalityNameModel.NameEmpcode> nameEmpcodes = new ArrayList<>();
    ArrayAdapter<String> autoNameAdapter;
    String role;
    TextInputEditText remarks;
    String assignedEmp, action, level;
    String username;
    String uploadedby, assigntovalue;
    TextView tv_action_dt, benefits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Im_before = findViewById(R.id.Im_before);
        Im_after = findViewById(R.id.Im_after);
        im_back = findViewById(R.id.im_back);
        tv_discription = findViewById(R.id.tv_discription);
        lay_afterimage = findViewById(R.id.lay_afterimage);
        tv_action = findViewById(R.id.tv_action);
        benefits = findViewById(R.id.benefits);
        tv_action_dt = findViewById(R.id.tv_action_dt);
        actionSpinner = findViewById(R.id.action_spinner);
        targetDate = findViewById(R.id.date);
        assignto = findViewById(R.id.assigned_to);
        assignedto = findViewById(R.id.assignedto);
        submit = findViewById(R.id.submit);
        remarks = findViewById(R.id.remarks);
        action_layout = findViewById(R.id.action_layout);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        username = myPref.getString("username", "");
        customSpinnerLayout1 = findViewById(R.id.customSpinnerLayout1);

        initDatePicker();
        targetDate.setOnTouchListener((view, motionEvent) -> {
            datePicker.show();
            return false;
        });


        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        if (getIntent().getExtras() != null) {
            AbnormalID = getIntent().getIntExtra("ID", 0);
            level = getIntent().getStringExtra("Level");
            if (getIntent().getStringExtra("uploadedby") != null) {
                uploadedby = getIntent().getStringExtra("uploadedby");
            }

            if (getIntent().getStringExtra("assignedto") != null) {
                assigntovalue = getIntent().getStringExtra("assignedto");
            }

        }
        hitgetimageApi(AbnormalID);
        actionName.clear();
        if (level.equalsIgnoreCase("Pending at HOD")) {
            if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("HOD") && assignto.equals(empCode)) {
                actionName.add("Update");
                action_layout.setVisibility(View.VISIBLE);

            } else {
                action_layout.setVisibility(View.GONE);
            }
        } else if (level.equalsIgnoreCase("Pending for assigning at BEST Coordinator")) {
            if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                action_layout.setVisibility(View.VISIBLE);
                actionName.add("Assign");
                actionName.add("Send Back to Initiator");
            } else {
                action_layout.setVisibility(View.GONE);

            }
        } else if (level.equalsIgnoreCase("Pending for approval at BEST Coordinator to be defined")) {
            if (AbnormalityAddressingActivity.Role.equalsIgnoreCase("C")) {
                actionName.add("Verify & Close");
                actionName.add("Send Back to HOD");
                action_layout.setVisibility(View.VISIBLE);
            } else {
                action_layout.setVisibility(View.GONE);
            }

        } else if (level.equalsIgnoreCase("Pending at User")) {
            if (username.equalsIgnoreCase(uploadedby)) {
                actionName.add("Update");
                action_layout.setVisibility(View.VISIBLE);

            } else {
                action_layout.setVisibility(View.GONE);

            }
        } else if (level.equalsIgnoreCase("closed")) {
            action_layout.setVisibility(View.GONE);
        } else {
            action_layout.setVisibility(View.VISIBLE);
        }
        //  actionName.add("Verify & Close");
        //   actionName.add("Update");
        actionArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, actionName);
        actionSpinner.setAdapter(actionArrayAdapter);
        im_back.setOnClickListener(view -> finish());
        initAutoNameAdapter();
        assignedto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 2) {
                    getAutoNameAbnormality(charSequence.toString(), empCode);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actionSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
            action = actionName.get(i);
            if (actionName.get(i).equalsIgnoreCase("Assign")) {
                assignto.setVisibility(View.VISIBLE);
                targetDate.setVisibility(View.VISIBLE);
                customSpinnerLayout1.setVisibility(View.VISIBLE);
            } else if (actionName.get(i).equalsIgnoreCase("closed")) {

            }
            else if(actionName.get(i).contains("Send Back")){
                targetDate.setVisibility(View.GONE);
                customSpinnerLayout1.setVisibility(View.GONE);
                assignto.setVisibility(View.GONE);
            }
            else {
                assignto.setVisibility(View.GONE);
            }
        });

        submit.setOnClickListener(view -> {
            if (targetDate.getVisibility() == View.VISIBLE && targetDate.getText().toString().length() == 0) {
                Toast.makeText(ViewImageActivity.this, "Enter Target Date", Toast.LENGTH_LONG).show();
                return;
            }
            if(remarks.getVisibility() == View.VISIBLE &&remarks.getText().toString().length()==0){
                Toast.makeText(ViewImageActivity.this, "Enter Remarks", Toast.LENGTH_LONG).show();
                return;
            }
            if(assignto.getVisibility() == View.VISIBLE && assignedto.getText().toString().length()==0){
                Toast.makeText(ViewImageActivity.this, "Enter Assign to", Toast.LENGTH_LONG).show();
                return;
            }if(action!= null) {
                if (action.equalsIgnoreCase("Assign")) {
                    assignAbnormality(String.valueOf(AbnormalID), empCode, targetDate.getText().toString(), remarks.getText().toString(), assignedEmp);
                } else if (action.equalsIgnoreCase("Verify & Close")) {
                    verifyclose(String.valueOf(AbnormalID), empCode, remarks.getText().toString());
                } else if (action.equalsIgnoreCase("Send Back to HOD")) {
                    sendBackToHOD(String.valueOf(AbnormalID), empCode, remarks.getText().toString());
                } else {
                    sendBackToUser(String.valueOf(AbnormalID), empCode, remarks.getText().toString());
                }
            }
            else{
                Toast.makeText(ViewImageActivity.this, "Select Action", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void initAutoNameAdapter() {
        autoNameAdapter = new ArrayAdapter<>(ViewImageActivity.this, android.R.layout.simple_spinner_item, names);
        assignedto.setAdapter(autoNameAdapter);
        assignedto.setOnItemClickListener((adapterView, view, i, l) -> {
            if (nameEmpcodes.size() > 0) {
                assignedEmp = nameEmpcodes.get(i).getEmpCode();
            }
        });

    }

    public void initDatePicker() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        String monthNo;
        if (mMonth < 10) {
            monthNo = "0" + mMonth;
        } else {
            monthNo = "" + mMonth;
        }
        String dayOfMonthStr;
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dayOfMonthStr = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            dayOfMonthStr = "" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        year = String.valueOf(calendar.get(Calendar.YEAR));

        targetDate.setText(dayOfMonthStr + "-" + monthNo + "-" + calendar.get(Calendar.YEAR));
        datePicker = new DatePickerDialog(ViewImageActivity.this, (datePicker, i, i1, i2) -> {
            int mMonth1 = i1 + 1;
            String monthNo1;
            if (mMonth1 < 10) {
                monthNo1 = "0" + mMonth1;
            } else {
                monthNo1 = "" + mMonth1;
            }
            String dayOfMonthStr1;
            if (i2 < 10) {
                dayOfMonthStr1 = "0" + i2;
            } else {
                dayOfMonthStr1 = "" + i2;
            }
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.YEAR, i);
            year = String.valueOf(calendar.get(Calendar.YEAR));
            targetDate.setText("" + dayOfMonthStr1 + "-" + monthNo1 + "-" + i);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //  datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 10000);

    }


    public void hitgetimageApi(int id) {
        if (Utility.isOnline(ViewImageActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<GetAbnormalityImage_Model>> response = promotingMyinterface.GetAbnormalityImage(RetrofitClient2.CKEY, id);
            response.enqueue(new Callback<List<GetAbnormalityImage_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<GetAbnormalityImage_Model>> call, @NotNull Response<List<GetAbnormalityImage_Model>> response) {
                    showProgress(false);
                    List<GetAbnormalityImage_Model> images = response.body();

                    if (images != null) {
                        Im_before.setImageBitmap(StringToBitMap(images.get(0).getImagePath().replace(" ", "+")));
                        tv_discription.setText(images.get(0).getDescription());
                    }
                    if (images!=null && images.get(0).getImagePathAfter() != null && images.get(0).getImagePathAfter().length() != 0) {
                        Im_after.setImageBitmap(StringToBitMap(images.get(0).getImagePathAfter().replace(" ", "+")));
                        if (images.get(0).getAction() != null) {
                            tv_action.setText(images.get(0).getAction());
                            tv_action_dt.setText(images.get(0).getImplementationDate());
                            benefits.setText(images.get(0).getBenefits());
                        }
                    } else {
                        lay_afterimage.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<GetAbnormalityImage_Model>> call, @NotNull Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(ViewImageActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
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

    public void getAutoNameAbnormality(String prefix, String val) {
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.getAutoNameAbnormality(carotResponse -> {
            nameEmpcodes.clear();
            names.clear();
            autoNameAdapter.notifyDataSetChanged();

            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                AbnormalityNameModel abnormalityNameModel = (AbnormalityNameModel) carotResponse.getData();
                if (abnormalityNameModel != null) {
                    List<AbnormalityNameModel.NameEmpcode> list = abnormalityNameModel.getList();
                    if (list != null && list.size() > 0) {
                        nameEmpcodes.addAll(list);
                        for (AbnormalityNameModel.NameEmpcode autoName : nameEmpcodes) {
                            names.add(autoName.getValue());
                        }
                        autoNameAdapter = new ArrayAdapter<String>(ViewImageActivity.this, android.R.layout.simple_spinner_item, names);
                        assignedto.setAdapter(autoNameAdapter);
                        autoNameAdapter.notifyDataSetChanged();

                    }

                }

            }


        }, prefix, val);

    }

    public void assignAbnormality(String id, String empCode, String targetDate, String remark, String assignTo) {
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.assignAbnormality(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                AssignResponseModel assignResponseModel = (AssignResponseModel) carotResponse.getData();
                if (assignResponseModel != null) {
                    if (assignResponseModel.getMessage() != null && assignResponseModel.getMessage().equals("Sucess")) {
                     //   Toast.makeText(ViewImageActivity.this, "Assigned Successfully", Toast.LENGTH_LONG).show();
                        showMsg("Assigned Successfully"," Success");
                      //  onBackPressed();
                    } else {
                        Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
            }

        }, id, empCode, targetDate, remark, assignTo);
    }

    public void sendBackToUser(String id, String empCode, String remarks) {
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.sendBackToUser(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                AssignResponseModel assignResponseModel = (AssignResponseModel) carotResponse.getData();
                if (assignResponseModel != null) {
                    if (assignResponseModel.getMessage() != null && assignResponseModel.getMessage().equals("Sucess")) {
                        //Toast.makeText(ViewImageActivity.this, "Successfully Sent Back to User", Toast.LENGTH_LONG).show();
                        showMsg("Successfully Sent Back to User"," Success");
                    } else {
                        Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
            }

        }, id, empCode, remarks);
    }

    public void sendBackToHOD(String id, String empCode, String remarks) {
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.sendBackToHOD(carotResponse -> {
            if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                AssignResponseModel assignResponseModel = (AssignResponseModel) carotResponse.getData();
                if (assignResponseModel != null) {
                    if (assignResponseModel.getMessage() != null && assignResponseModel.getMessage().equals("Sucess")) {
                     //   Toast.makeText(ViewImageActivity.this, "Successfully Sent Back to HOD", Toast.LENGTH_LONG).show();
                        showMsg("Successfully Sent Back to HOD"," Success");

                        onBackPressed();
                    } else {
                        Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
            }

        }, id, empCode, remarks);

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


    public void verifyclose(String id, String empCode, String remarks) {
        AbnormalityServices abnormalityServices = new AbnormalityServices();
        abnormalityServices.verifyclose(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    AssignResponseModel assignResponseModel = (AssignResponseModel) carotResponse.getData();
                    if (assignResponseModel != null) {
                        if (assignResponseModel.getMessage() != null && assignResponseModel.getMessage().equals("Sucess")) {
                           // Toast.makeText(ViewImageActivity.this, "Successfully verified & closed", Toast.LENGTH_LONG).show();
                            showMsg("Successfully verified & closed","Success");
                            //   onBackPressed();
                        } else {
                            Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(ViewImageActivity.this, "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                }

            }
        }, id, empCode, remarks);
    }
}
