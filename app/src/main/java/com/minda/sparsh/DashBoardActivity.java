package com.minda.sparsh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.NotificationAdapter;
import com.minda.sparsh.fragment.EightFragment;
import com.minda.sparsh.fragment.FiveFragment;
import com.minda.sparsh.fragment.FourFragment;
import com.minda.sparsh.fragment.ManufacturingFragment;
import com.minda.sparsh.fragment.OneFragment;
import com.minda.sparsh.fragment.SevenFragment;
import com.minda.sparsh.fragment.SixFragment;
import com.minda.sparsh.fragment.ThreeFragment;
import com.minda.sparsh.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.minda.sparsh.R;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.util.Utility;

import org.jsoup.Jsoup;

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
    public ImageView im_right, im_left, im_logo;
    Timer timer;
    public static Integer time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(R.string.app_name);

//        ((ImageButton) findViewById(R.id.dwm_btn)).setOnClickListener(this);
//        ((ImageButton) findViewById(R.id.jagriti_btn)).setOnClickListener(this);
        tv_unread = (TextView) findViewById(R.id.tv_unread);
//        im_right= (ImageView) findViewById(R.id.im_right);
//        im_left= (ImageView) findViewById(R.id.im_left);
//        im_logo=(ImageView) findViewById(R.id.im_logo);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        lay_logout = (RelativeLayout) findViewById(R.id.lay_logout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        User = myPref.getString("username", "");
        //tv_user_name.setText(User);
//        im_left.setOnClickListener(this);
//        im_right.setOnClickListener(this);
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
//        adapter.addFragment(new SevenFragment(), "Survey");
//        adapter.addFragment(new EightFragment(), "Abnormality");
//        adapter.addFragment(new SixFragment(), "Support");


        viewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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


        tv_unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashBoardActivity.this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        HitMyorder(myPref.getString("Id", ""));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

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
            Call<List<NotificationModel>> loginResponse = loginInterface.GetPushNot(UserId, "mda@sPr$rZ#G!!");
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    response.message();
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


                    } else {
                    }


                }

                @Override
                public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                    Toast.makeText(DashBoardActivity.this, "Something Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(DashBoardActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

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
}
