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
   /* String[] arr = new String[]{"9355689701", "9818427076",
            "9958050362",
            "7082007046",
            "8861002125",
            "8800688442",
            "9810256524",
            "9560912004",
            "9996116094",
            "9810423729",
            "9810239037",
            "9910577190",
            "9555559670",
            "7043555074",
            "9767896645",
            "9971079479",
            "9650811877",
            "7505463780",
            "9818541002",
            "9818124886",
            "9971108528",
            "9910373999",
            "9818002575",
            "8130615588",
            "9860870353",
            "9643295956",
            "9599772289",
            "9711644857",
            "9541353318",
            "8894275656",
            "9997199468",
            "9971022850",
            "9818002575",
            "9719250198",
            "7056817184",
            "8398987261",
            "8221958771",
            "9205050075",
            "8222833088",
            "9717837614",
            "9588728955",
            "7500708376",
            "7010437966",
            "8830542838",
            "8208592409",
            "8319301386",
            "8306208882",
            "8128819008",
            "9898759363",
            "9548538613",
            "8547745336",
            "8440909461",
            "9711932214",
            "7840037080",
            "9311683060",
            "8346873722",
            "8081024823",
            "8750226395",
            "9559594416",
            "7774002344",
            "7056817184",
            "9717837614",
            "7827916926",
            "9555150068",
            "9411116516",
            "9500868585",
            "7838933371",
            "8860887091",
            "9164816186",
            "8901178681",
            "8433466755",
            "8221958771",
            "8800602249",
            "9752571667",
            "8669016772",
            "8149076757",
            "9130348704",
            "8130394960",
            "9958933128",
            "9971696310",
            "7290032216",
            "9306045010",
            "7015849132",
            "9205050076",
            "9992184343",
            "9710580992",
            "9491326565",
            "9667016392",
            "7418522326",
            "7389109502",
            "9718860264",
            "7568738110",
            "9560366280",
            "9813948710",
            "8637440343",
            "9759505757",
            "8709781722",
            "8668056620",
            "9587726631",
            "9303816510",
            "8888159895",
            "8910196889",
            "9810542369",
            "1234567890",
            "8789530119",
            "7042412921",
            "9528613879",
            "7056222476",
            "7457023406",
            "9773672476",
            "7071145192",
            "7210018500",
            "9416708420",
            "9870634831",
            "9654996956",
            "9500564744",
            "8307677209",
            "9871746462",
            "9910554668",
            "8196071837",
            "9500517880",
            "7397723220",
            "8294128459",
            "7053938609",
            "9582866749",
            "8248753199",
            "7027021859",
            "9717791919",
            "7027783222",
            "7827678265",
            "7503680876",
            "8446294073",
            "8971789487",
            "9738008389",};
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

      /*  StringBuilder appended = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {

            appended.append("{" +
                    "\"@FROM\"" + ":" + "\"918800233383\"" + "," +
                    "\"@TO\"" + ":" + "\"91" + arr[i] + "\"" + "," +
                    "\"@SEQ\"" + ":" + "\"1\"" + "," +
                    "\"@TAG\"" + ":" + "\"some client side random data\"" +
                    "},");
        }
        System.out.println("Append" + appended.toString());
      */  ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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

