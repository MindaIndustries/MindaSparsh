package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.minda.sparsh.fragment.FiveFragment;
import com.minda.sparsh.fragment.FourFragment;
import com.minda.sparsh.fragment.ManufacturingFragment;
import com.minda.sparsh.fragment.OneFragment;
import com.minda.sparsh.fragment.ThreeFragment;
import com.minda.sparsh.fragment.TwoFragment;
import com.minda.sparsh.model.GuidelineModel;
import com.minda.sparsh.model.NotiCount;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.model.UpdateGuideLine;
import com.minda.sparsh.model.VersionModel;
import com.minda.sparsh.services.FirebaseService;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {
    public TextView tv_user_name;
    SharedPreferences myPref;
    String User, currentVersion;
    RelativeLayout lay_logout;
    int count = 0;
    public static TextView tv_unread;
    public ImageView im_right, im_left;
    String empCode;
    public static Integer time = 0;

    String version = "";
    String deviceTokenFcm;
    String Description, title;
    int applicableId;
   /* String[] arr = new String[]{"9355689701", "9940192973", "8097999231", "8800557829", "9818427061", "9717796433", "9650193399", "9999333169", "9840883640", "8754049307", "9818427240", "9818427036", "9350096933", "9818427002", "9890006908", "9810943561", "9971794619", "8130137008", "9928550070", "9896143642", "9828821905", "9971032266", "9650806914", "9024220382", "9818427168", "8130048944", "8056353622", "9818424439", "9890266866","9466228761","8220017386","9673331863","9717562275",
            "9818460853", "9818427067", "9899039541", "9654518323", "8222962288", "9911701579", "8094109999", "9890006913", "7027767408", "9560809890", "9766304169", "9810922442", "9790033354", "9818739538", "8295911089", "9818427300", "9717366544", "9818002575", "7023004956", "9810400163", "9890006947", "9890990778", "9717829666", "9560477440","9818157619", "9075011597", "9818739510", "9372848930", "9811907489", "8600162227", "8130186005", "9971527523", "9818739583", "8220014371", "9910133945", "9810404516", "8600466111","9910001518","9958411669","9971527523", "9971527523", "9871609512", "8111040258", "9890006939", "9711173687", "9818427073", "8600134713", "9650060295", "8800411447","9999036832","8000790770","9818427006","9910569900","9999687797","9818200007","9818300001","8800688442","9599316186","8826509991","8527383833","9029099879","9818427052","9990093331","9818427139","9811299200","9997027039","9810638285","7045447464","9868043733","9650737666","9818427221","9818427024","9873696305","9007565245","9890006932","9890006912","9810320096","7888037712","9818427142","9871597675","9810121312","8600167059","9810823442","7410049951","9910115954","9818427008",
            "9871200830","9818427010","9811100346","9996780179","9994982119","9650863811","9654110649","9632222536","9810904992","9560180666","9899008822","9677186386","8295911042","9818556910","9818898553","9818427055","9818427088","9871298031","9997027003","9873360005","8600045067","9960694331","9971711506","9818427131","7838195575","9999666294","9971527523","9686600697","9500057970","9818427021","9442232615","9850309363","9971527523","1244974871","9958288808","9818427220","9818427216","8754592090","9552596770","9810895454","9994982115","9654518323","8860449853","7414096096","9958697150","9890006904","9871092300","9871222733","9718298215","9818427093","9650807262","9811056302","9810178667","9822344410","9840252990","9900236265","8826435442","8860544203","9560700408","9595808539","7791978823","9890006942","9718660299","9922963504","7774015468","7503025998","9823009736","9782225106","9818427077","7837206053","9910302898","9999816665","7703814651","8975921184","7040054340","9529628375","7056761155","9789012543","9766304185","9810904255","9873118111","9933366464","8754007994","9894603809","8744998927","9718626995","9999767086","9953629409","8076509775","8800549250",
            "8308338999","9850570028","9841391878","9003508784", "9552587909", "8057881428","9654930407"};
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        tv_unread = findViewById(R.id.tv_unread);
        tv_user_name = findViewById(R.id.tv_user_name);
        lay_logout = findViewById(R.id.lay_logout);
        viewPager = findViewById(R.id.pager);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        User = myPref.getString("username", "");
        saveFirebaseToken(empCode);
        getAppVersion();
        viewPager.setOffscreenPageLimit(0);
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        getGuidelineDetails();
      /*  GetVersionCode getVersionCode = new GetVersionCode();
        getVersionCode.execute();
*/

        StringBuilder appended = new StringBuilder();
       /* for (int i = 0; i < arr.length; i++) {

            appended.append("{" +
                    "\"@FROM\"" + ":" + "\"918800233383\"" + "," +
                    "\"@TO\"" + ":" + "\"91" + arr[i] + "\"" + "," +
                    "\"@SEQ\"" + ":" + "\"1\"" + "," +
                    "\"@TAG\"" + ":" + "\"some client side random data\"" +
                    "},");
        }
        System.out.println("Append" + appended.toString());
*/
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TwoFragment(), "About Us");
        adapter.addFragment(new OneFragment(), "Newsletter");
        adapter.addFragment(new ManufacturingFragment(), "Manufacturing");
        adapter.addFragment(new ThreeFragment(), "HR/Admin");
//        adapter.addFragment(new FiveFragment(), "Finance");
        adapter.addFragment(new FourFragment(), "IT");
        adapter.addFragment(new FiveFragment(), "Marketing");
//        adapter.addFragment(new SevenFragment(), "Survey");
//        adapter.addFragment(new EightFragment(), "Abnormality");
//        adapter.addFragment(new SixFragment(), "Support");


        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                viewPager.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % 8);
//
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 5000, 5000);


        tv_user_name.setOnClickListener(view -> {
            tv_unread.setVisibility(View.GONE);
            Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        getNotifcount(empCode);

        //HitMyorder(myPref.getString("Id", ""));


    }

    @Override
    public void onClick(View v) {
        //  switch (v.getId()) {
//            case R.id.im_left:

//                viewPager.setCurrentItem(getItem(-1), true);
////                if(getItem(-1)==0)
////                {im_left.setVisibility(View.GONE);}
////                else {im_left.setVisibility(View.VISIBLE);}
//                break;
//            case R.id.im_right:
//                viewPager.setCurrentItem(getItem(+1), true);
////                if(getItem(+1)==6)
////                {im_right.setVisibility(View.GONE);}
////                else {im_right.setVisibility(View.VISIBLE);}
//
//                break;
        // }
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void HitMyorder(String UserId) {
        if (Utility.isOnline(DashBoardActivity.this)) {
            Interface loginInterface = RetrofitClient.getClient().create(Interface.class);
            Call<List<NotificationModel>> loginResponse = loginInterface.GetPushNot(UserId, RetrofitClient2.CKEY);
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NotificationModel>> call, @NotNull Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    if (responseItem != null && responseItem.size() > 0) {

                        for (int i = 0; i < responseItem.size(); i++) {
                            if (!responseItem.get(i).getIsRead()) {
                                count = count + 1;
                            }

                        }
                        tv_unread.setText(String.valueOf(count));
//                        if(count==0)
//                        {
//                            tv_unread.setVisibility(View.GONE);
//                        }


                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<NotificationModel>> call, @NotNull Throwable t) {
                    Toast.makeText(DashBoardActivity.this, "Something went Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(DashBoardActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

/*
    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
//            appupdate=Utility.showProgressbar(HomeActivity.this);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + DashBoardActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
//            appupdate.dismiss();
            try {
                if (onlineVersion != null && !onlineVersion.isEmpty() && onlineVersion.contains("1.")) {
                    if (!currentVersion.equalsIgnoreCase(onlineVersion)) {
                        //show dialog
                        updateapp();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void updateapp() {
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(DashBoardActivity.this);
            builder1.setMessage("New Update Available! Please update your app.");
            builder1.setCancelable(false);

//            builder1.setPositiveButton(
//                    "Later",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });

            builder1.setNegativeButton(
                    "Update",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse("https://play.google.com/store/apps/details?id=com.minda.sparsh"));
                            startActivity(viewIntent);

                        }
                    });

            android.app.AlertDialog alert11 = builder1.create();
            try {
                alert11.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
*/


    public void saveFirebaseToken(String empCode) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                deviceTokenFcm = task.getResult();
            }
            if (Utility.isOnline(DashBoardActivity.this)) {
                FirebaseService firebaseService = new FirebaseService();
                firebaseService.saveFirebaseID(carotResponse -> {
                    if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {


                    }
                }, empCode, deviceTokenFcm, "Android");
            }

        });
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

    public void getGuidelineDetails() {
        Interface aninterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<GuidelineModel>> call = aninterface.getGuidelinePending(empCode, RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<GuidelineModel>>() {
            @Override
            public void onResponse(Call<List<GuidelineModel>> call, Response<List<GuidelineModel>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    List<GuidelineModel> list = response.body();
                    if (list != null && list.size() > 0) {
                        if (list.get(0).getDescription() != null) {
                            Description = list.get(0).getDescription();
                        }
                        if (list.get(0).getTitle() != null) {
                            title = list.get(0).getTitle();
                        }
                        if (list.get(0).getApplicableID() != 0) {
                            applicableId = list.get(0).getApplicableID();
                        }
                        showAlertForGuideLines();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GuidelineModel>> call, Throwable t) {
                System.out.println("Api failed");
            }
        });


    }


    public void showMsgUpdate() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashBoardActivity.this, R.style.AlertDialogCustom);
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
        if (!isFinishing()) {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void getNotifcount(String UserId) {
        Interface loginInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<java.util.List<NotiCount>> call = loginInterface.GetPushNotCount(UserId, RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<NotiCount>>() {
            @Override
            public void onResponse(Call<List<NotiCount>> call, Response<List<NotiCount>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    List<NotiCount> list = response.body();
                    if (list != null && list.size() > 0) {
                        if (list.get(0).getVal() > 0) {
                            tv_unread.setText("" + list.get(0).getVal());
                            tv_unread.setVisibility(View.VISIBLE);
                        } else {
                            tv_unread.setVisibility(View.GONE);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<NotiCount>> call, Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Something went Wrong", Toast.LENGTH_LONG).show();

            }
        });

    }


    public void showAlertForGuideLines() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        final View customLayout
                = getLayoutInflater()
                .inflate(R.layout.guidelines_layout, null);
        builder.setView(customLayout);
        AlertDialog dialog
                = builder.create();
        dialog.setCancelable(false);
        TextView textView = customLayout.findViewById(R.id.textView);
        TextView title1 = customLayout.findViewById(R.id.title);
        CheckBox chkbox = customLayout.findViewById(R.id.check);
        Button accept = customLayout.findViewById(R.id.accept);
        title1.setText(title);
        chkbox.setText("I agree and accept above " + title);
        textView.setText(Html.fromHtml(Description));
        chkbox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                accept.setVisibility(View.VISIBLE);
            } else {
                accept.setVisibility(View.INVISIBLE);
            }
        });
        accept.setOnClickListener(view -> {
            if (Utility.isOnline(DashBoardActivity.this)) {
                updateGuideLine(dialog);
            }
        });
        if (!isFinishing()) {
            dialog.show();
        }
    }

    public void updateGuideLine(AlertDialog alertDialog) {
        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<UpdateGuideLine>> call = anInterface.updatePendingGuidLine(empCode, applicableId, RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<UpdateGuideLine>>() {
            @Override
            public void onResponse(Call<List<UpdateGuideLine>> call, Response<List<UpdateGuideLine>> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    List<UpdateGuideLine> updateGuideLine = (List<UpdateGuideLine>) response.body();
                    if (updateGuideLine != null && updateGuideLine.size() > 0) {
                        if (updateGuideLine.get(0).getColumn1() != null) {
                            if (updateGuideLine.get(0).getColumn1().equalsIgnoreCase("Success")) {
                                alertDialog.dismiss();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UpdateGuideLine>> call, Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Something went Wrong", Toast.LENGTH_LONG).show();

            }
        });

    }
}

