package com.minda.sparsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.minda.sparsh.model.NotiCount;
import com.minda.sparsh.model.NotificationModel;
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
      /*  GetVersionCode getVersionCode = new GetVersionCode();
        getVersionCode.execute();
*/

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TwoFragment(), "About Us");
        adapter.addFragment(new OneFragment(), "Newsletter");
        adapter.addFragment(new ManufacturingFragment(), "Manufacturing");
        adapter.addFragment(new ThreeFragment(), "HR/Admin");
//        adapter.addFragment(new FiveFragment(), "Finance");
        adapter.addFragment(new FourFragment(), "IT");
        adapter.addFragment(new FiveFragment(),"Marketing");
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
            if(task.isSuccessful()) {
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
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void getNotifcount(String UserId){
        Interface loginInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<java.util.List<NotiCount>> call = loginInterface.GetPushNotCount(UserId, RetrofitClient2.CKEY);
        call.enqueue(new Callback<List<NotiCount>>() {
            @Override
            public void onResponse(Call<List<NotiCount>> call, Response<List<NotiCount>> response) {
                if(response.code()==HttpsURLConnection.HTTP_OK){
                    List<NotiCount> list = response.body();
                    if(list!=null && list.size()>0){
                        if(list.get(0).getVal()>0) {
                            tv_unread.setText("" + list.get(0).getVal());
                            tv_unread.setVisibility(View.VISIBLE);
                        }
                        else{
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


}

