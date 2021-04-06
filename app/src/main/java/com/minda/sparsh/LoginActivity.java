package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.minda.sparsh.Adapter.SlidingImage_Adapter;
import com.minda.sparsh.connection.HttpConnection;
import com.minda.sparsh.model.BannarModel;
import com.minda.sparsh.model.LoginModel;
import com.minda.sparsh.model.LoginResponse;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.parser.ParseData;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText input_email, input_password;
    private LoginFetchTask loginFetchTask = null;
    private String Id;
    private String UserName, EmailID;
    private LoginModel loginModel;
    private ViewPager mPager;
    private ParseData pd;
    private String usernameString, passString;
    private SharedPreferences myPref = null;
    private CheckBox cbTerms;
    private ProgressDialog progress = null;
    String refreshedToken;
    Timer timer;
    TimerTask timerTask;
    LinearLayout lay_vision, lay_mission, lay_values, lay_needhelp;
    boolean runtimer;
    private List<String> IMAGES = new ArrayList<>();
    public ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progress = new ProgressDialog(this);
//        IMAGES.add(R.drawable.login_background) ;
//        IMAGES.add(R.drawable.login1) ;
//        IMAGES.add(R.drawable.login2);
////        IMAGES.add(R.drawable.login3);
//        IMAGES.add(R.drawable.login4) ;
//        IMAGES.add(R.drawable.login5) ;


        lay_vision = (LinearLayout) findViewById(R.id.lay_vision);
        lay_mission = (LinearLayout) findViewById(R.id.lay_mission);
        lay_values = (LinearLayout) findViewById(R.id.lay_values);
        lay_needhelp = (LinearLayout) findViewById(R.id.lay_needhelp);
        mPager = (ViewPager) findViewById(R.id.showpager);
        hitBannerApi();
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        cbTerms = (CheckBox) findViewById(R.id.cb_terms);

        pd = new ParseData();
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    usernameString = input_email.getText().toString();
                    passString = input_password.getText().toString();
                    if (Utility.isOnline(getApplication())) {
                        hitGetLoginApi(usernameString, passString, RetrofitClient2.CKEY);
//                        loginFetchTask = new LoginFetchTask();
//                        loginFetchTask.execute();
                    } else {
                        Utility.showToast(getApplicationContext(), "Network failed. Please try later.");
                    }
                }
            }
        });
        lay_vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vision = new Intent(LoginActivity.this, VisionActivity.class);
                startActivity(vision);
            }
        });
        lay_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mission = new Intent(LoginActivity.this, MissionActivity.class);
                startActivity(mission);
            }
        });
        lay_values.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent values = new Intent(LoginActivity.this, ValuesActivity.class);
                startActivity(values);
            }
        });
        lay_needhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent needhelp = new Intent(LoginActivity.this, NeedhelpActivity.class);
                startActivity(needhelp);
            }
        });
        showslideview();
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                timer.cancel();
                runtimer = true;
                return false;
            }
        });

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

    public class LoginFetchTask extends AsyncTask<Void, Void, Boolean> {
        private String response;
        private String msg;

        @Override
        protected void onPreExecute() {
            showProgress(true);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            response = new HttpConnection().requestGetContent("GetLogin?username=" + usernameString + "&pass=" + passString + "&CKey=" + "bWRhQHNQciRyWiNHISE=");
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            loginFetchTask = null;
            if (response != null && response.length() > 5) {
                loginModel = pd.getLoginValue(response);
                Id = loginModel.Id;
                UserName = loginModel.UserName;
                EmailID = loginModel.EmainId;

                if (UserName != null) {
                    showProgress(false);
                    Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
//                    intent.putExtra("id",Id);
//                    intent.putExtra("username",UserName);
                    SharedPreferences.Editor mEditor = myPref.edit();
                    mEditor.putString("Id", Id);
                    mEditor.putString("username", UserName);
                    mEditor.putBoolean("IsLogin", true);
                    mEditor.putBoolean("IsLoginNew", true);
                    mEditor.putString("EmainId", EmailID);
                    mEditor.putString("AuthFor", loginModel.AuthFor);
                    mEditor.putString("Um_div_code", loginModel.UM_DIV_CODE);

                    arrayList.clear();
                    String currentString = loginModel.AuthFor;
                    String[] separated = currentString.split(",");
                    for (int i = 0; i < separated.length; i++) {
                        arrayList.add(separated[i]);
                    }

                    //Set the values
                    Set<String> set = new HashSet<String>();
                    set.addAll(arrayList);
                    mEditor.putStringSet("key", set);
                    if (!cbTerms.isChecked()) {
                        mEditor.putBoolean("IsLogin", false);
                        mEditor.putBoolean("IsLoginNew", false);
                    }
                    refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    HitMyorder(Id, refreshedToken);
                    mEditor.commit();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            } else
                Utility.showToast(getApplicationContext(), "Invalid Credentials.");
            showProgress(false);

//            if (promotionData == null)
//                promotionData = new ArrayList<>();
//            // Display data
//            displayData();
//            showProgress(false);
        }

//        @Override
//        protected void onCancelled() {
//            brandFetchTask = null;
//            showProgress(false);
//        }
    }

    public boolean isValidate() {
        if (input_email.getText().toString().equalsIgnoreCase("")) {
            Utility.showToast(getApplicationContext(), "Please enter User Id.");
            return false;
        } else if (input_password.getText().toString().equalsIgnoreCase("")) {
            Utility.showToast(getApplicationContext(), "Please enter Password.");
            return false;
        } else if (input_password.getText().length() < 3) {
            Utility.showToast(getApplicationContext(), "This password is too short.");
            return false;
        } else return true;
    }

    public void HitMyorder(String UserId, String deviceid) {
        if (Utility.isOnline(LoginActivity.this)) {
            Interface loginInterface = RetrofitClient.getClient().create(Interface.class);
            Call<List<NotificationModel>> loginResponse = loginInterface.ReadPushNot(UserId, deviceid, "mda@sPr$rZ#G!!");
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    response.message();
                    if (responseItem != null) {


                    } else {
                    }


                }

                @Override
                public void onFailure(Call<List<NotificationModel>> call, Throwable t) {

//                    Toast.makeText(mContext, "Something Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void showslideview() {
        mPager.setAdapter(new SlidingImage_Adapter(LoginActivity.this, IMAGES));
        mPager.setOffscreenPageLimit(6);
//        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        indicator.setViewPager(mPager);
//        final float density = getResources().getDisplayMetrics().density;
//        indicator.setRadius(3 * density);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                mPager.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            mPager.setCurrentItem((mPager.getCurrentItem() + 1) % IMAGES.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 5000, 5000);
        runtimer = false;
    }

    public void hitBannerApi() {
        if (Utility.isOnline(LoginActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<BannarModel>> Loginresponse = promotingMyinterface.GetBanner("");
            Loginresponse.enqueue(new Callback<List<BannarModel>>() {
                @Override
                public void onResponse(Call<List<BannarModel>> call, Response<List<BannarModel>> response) {
                    showProgress(false);
                    List<BannarModel> BannarModel = response.body();
                    IMAGES.clear();


                    if (BannarModel != null && BannarModel.size() > 0) {
                        if (BannarModel.get(0).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(0).getIMAGE());
                        }

                        if (BannarModel.get(1).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(1).getIMAGE());
                        }

                        if (BannarModel.get(2).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(2).getIMAGE());
                        }

                        if (BannarModel.get(3).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(3).getIMAGE());
                        }
                        if (BannarModel.get(4).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(4).getIMAGE());
                        }
                        if (BannarModel.get(5).getIMAGE().length() != 0) {
                            IMAGES.add(BannarModel.get(5).getIMAGE());
                        }

                        showslideview();


                    }

                }

                @Override
                public void onFailure(Call<List<BannarModel>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        runtimer = true;

    }

    public void hitGetLoginApi(String userName, final String password, String key) {
        if (Utility.isOnline(LoginActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<LoginResponse>> response = promotingMyinterface.GetLogin(userName, password, key);
            response.enqueue(new Callback<List<LoginResponse>>() {
                @Override
                public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                    showProgress(false);
                    List<LoginResponse> loginResponse = response.body();
                    if (loginResponse != null && loginResponse.size() > 0) {

                        try {
                            Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            SharedPreferences.Editor mEditor = myPref.edit();
                            mEditor.putString("Id", loginResponse.get(0).getUMUSERID());
                            mEditor.putString("username", loginResponse.get(0).getUMUSERDESC());
                            mEditor.putBoolean("IsLogin", true);
                            mEditor.putBoolean("IsLoginNew", true);
                            mEditor.putString("EmainId", loginResponse.get(0).getUMEMAILID());
                            mEditor.putString("AuthFor", loginResponse.get(0).getAuthFor());
                            mEditor.putString("Um_div_code", loginResponse.get(0).getUMDIVCODE());
                            mEditor.putString("UM_DESIG_CODE", loginResponse.get(0).getUMDESIGCODE());
                            mEditor.putString("DESIGNATION", loginResponse.get(0).getUMEMPDESIG());
                            mEditor.putString("DOJ", loginResponse.get(0).getDOJ());
                            mEditor.putString("UM_DEPT_NAME", loginResponse.get(0).getUMDEPTNAME());
                            mEditor.putString("UM_REPORTING_TO_NAME", loginResponse.get(0).getUMREPORTINGTONAME());
                            mEditor.putString("UM_MASCOM_CODE", loginResponse.get(0).getUMMASCOMCODE());
                            mEditor.putString("Depu_UnitName", (String) loginResponse.get(0).getDepuUnitName());
                            mEditor.putString("Depu_UnitCode", (String) loginResponse.get(0).getDepuUnitCode());
                            mEditor.putString("pass", loginResponse.get(0).getUMUSERPWD());
                            mEditor.putString("DOB", loginResponse.get(0).getDOB());
                            mEditor.putString("Mobile", loginResponse.get(0).getMobile());
                            arrayList.clear();
                            String currentString = loginResponse.get(0).getAuthFor();

                            if (currentString != null) {
                                String[] separated = currentString.split(",");
                                for (int i = 0; i < separated.length; i++) {
                                    arrayList.add(separated[i]);
                                }
                            }

                            //Set the values
                            Set<String> set = new HashSet<String>();
                            set.addAll(arrayList);
                            mEditor.putStringSet("key", set);

                            if (!cbTerms.isChecked()) {
                                mEditor.putBoolean("IsLogin", false);
                                mEditor.putBoolean("IsLoginNew", false);

                            }
                            refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            HitMyorder(Id, refreshedToken);
                            mEditor.apply();
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Utility.showToast(getApplicationContext(), "Invalid Credentials.");
                    }

                }

                @Override
                public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }


}