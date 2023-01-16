package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import com.minda.sparsh.Adapter.SlidingImage_Adapter;
import com.minda.sparsh.connection.HttpConnection;
import com.minda.sparsh.model.BannarModel;
import com.minda.sparsh.model.LoginModel;
import com.minda.sparsh.model.LoginResponse;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.model.VersionModel;
import com.minda.sparsh.parser.ParseData;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AlertDialog;
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
    private final List<String> IMAGES = new ArrayList<>();
    public ArrayList<String> arrayList = new ArrayList<>();
    String version = "";
    ImageView view_pass;
    boolean ispassvisible;
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


        lay_vision =  findViewById(R.id.lay_vision);
        lay_mission =  findViewById(R.id.lay_mission);
        lay_values =  findViewById(R.id.lay_values);
        lay_needhelp =  findViewById(R.id.lay_needhelp);
        mPager =  findViewById(R.id.showpager);
        view_pass = findViewById(R.id.view_pass);
       // hitBannerApi();
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        cbTerms =  findViewById(R.id.cb_terms);

        pd = new ParseData();
        input_email =  findViewById(R.id.input_email);
        input_password =  findViewById(R.id.input_password);
        btn_login =  findViewById(R.id.btn_login);

        btn_login.setOnClickListener(v -> {
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
        });
        lay_vision.setOnClickListener(view -> {
            Intent vision = new Intent(LoginActivity.this, VisionActivity.class);
            startActivity(vision);
        });
        lay_mission.setOnClickListener(view -> {
            Intent mission = new Intent(LoginActivity.this, MissionActivity.class);
            startActivity(mission);
        });
        lay_values.setOnClickListener(view -> {
            Intent values = new Intent(LoginActivity.this, ValuesActivity.class);
            startActivity(values);
        });
        lay_needhelp.setOnClickListener(view -> {
            Intent needhelp = new Intent(LoginActivity.this, NeedhelpActivity.class);
            startActivity(needhelp);
        });
        view_pass.setOnClickListener(view -> {
            if(ispassvisible){
                view_pass.setImageResource(R.drawable.visibility_off);
                input_password.setTransformationMethod(new PasswordTransformationMethod());
            }
            else{
                view_pass.setImageResource(R.drawable.pass_view);
                input_password.setTransformationMethod(null);
            }

            input_password.setSelection(input_password.length());
            ispassvisible= !ispassvisible;

        });
      //  showslideview();
     /*   mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                timer.cancel();
                runtimer = true;
                return false;
            }
        });
*/
        if(Utility.isOnline(LoginActivity.this)) {
            getAppVersion();
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
                    for (String s : separated) {
                        arrayList.add(s);
                    }

                    //Set the values
                    Set<String> set = new HashSet<String>();
                    set.addAll(arrayList);
                    mEditor.putStringSet("key", set);
                    if (!cbTerms.isChecked()) {
                        mEditor.putBoolean("IsLogin", false);
                        mEditor.putBoolean("IsLoginNew", false);
                    }
                    refreshedToken = FirebaseMessaging.getInstance().getToken().getResult();
                    HitMyorder(Id, refreshedToken);
                    mEditor.apply();
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
                public void onResponse(@NotNull Call<List<NotificationModel>> call, @NotNull Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    response.message();
                }

                @Override
                public void onFailure(@NotNull Call<List<NotificationModel>> call, @NotNull Throwable t) {

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
                mPager.post(() -> {
                    try {
                        mPager.setCurrentItem((mPager.getCurrentItem() + 1) % IMAGES.size());
                    } catch (Exception e) {
                        e.printStackTrace();
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
                public void onResponse(@NotNull Call<List<BannarModel>> call, @NotNull Response<List<BannarModel>> response) {
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
                public void onFailure(@NotNull Call<List<BannarModel>> call, @NotNull Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
  //      runtimer = true;

    }

    public void hitGetLoginApi(String userName, final String password, String key) {
        if (Utility.isOnline(LoginActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<LoginResponse>> response = promotingMyinterface.GetLogin(userName, password, key);
            response.enqueue(new Callback<List<LoginResponse>>() {
                @Override
                public void onResponse(@NotNull Call<List<LoginResponse>> call, @NotNull Response<List<LoginResponse>> response) {
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
                            mEditor.putInt("Level",loginResponse.get(0).getUMEMPLEVEL());
                            mEditor.putBoolean("cvp",loginResponse.get(0).isCVPAccess());
                            mEditor.putString("REPORTY_EMAIL",loginResponse.get(0).getUMREPORTINGTOEMAIL());
                            mEditor.putString("REPORTY_EMP_NAME",loginResponse.get(0).getUMREPORTINGTONAME());
                            mEditor.putString("REPORTY_EMP_CODE",loginResponse.get(0).getUMREPORTINGTO());
                            arrayList.clear();
                            String currentString = loginResponse.get(0).getAuthFor();

                            if (currentString != null) {
                                String[] separated = currentString.split(",");
                                for (String s : separated) {
                                    arrayList.add(s);
                                }
                            }

                            //Set the values
                            Set<String> set = new HashSet<>();
                            set.addAll(arrayList);
                            mEditor.putStringSet("key", set);

                            if (!cbTerms.isChecked()) {
                                mEditor.putBoolean("IsLogin", false);
                                mEditor.putBoolean("IsLoginNew", false);

                            }
                         //   refreshedToken = FirebaseMessaging.getInstance().getToken().getResult();
                           // HitMyorder(Id, refreshedToken);
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
                public void onFailure(@NotNull Call<List<LoginResponse>> call, @NotNull Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void getAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<VersionModel>> call = anInterface.getAppVersion();
        call.enqueue(new Callback<List<VersionModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<VersionModel>> call, @NotNull Response<List<VersionModel>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    List<VersionModel> list = response.body();
                    if (list != null && list.size() > 0) {
                        if (list.get(0) != null && list.get(0).getAndriodVersion() != null) {
                            String androidVersion = list.get(0).getAndriodVersion().trim();
                            if (!version.equals(androidVersion)) {
                                showMsgUpdate();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<VersionModel>> call, @NotNull Throwable t) {
                System.out.println("Api failed");

            }
        });
    }
    public void showMsgUpdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        alertDialogBuilder.setTitle("New Update");
        alertDialogBuilder.setMessage("A new Version of Minda Sparsh is available on Play Store. Please Update.");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", (arg0, arg1) -> {
            arg0.dismiss();

            //  finish();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitClient2.playstoreURL));
            startActivity(browserIntent);
        });

       /* alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               // finish();
            }
        });
*/
        if(!isFinishing()) {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}