package com.minda.sparsh;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.minda.sparsh.services.CVPServices;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

//import com.whiteelephant.monthpicker.MonthPickerDialog;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView mNavigationView;
    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ProgressDialog progressDialog;
    public ViewPager viewPager;
    SharedPreferences myPref;
    ActivityResultLauncher activityResultLauncher;
    String empcode, randomNumber;


    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1234;

    protected boolean useToolbar() {
        return true;
    }

    protected boolean useDrawerToggle() {
        return true;
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getActionBarToolbar();
        setupNavDrawer();
    }//end setContentView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                randomNumber = bundle.getString("S.NO.");
                updateRandomNumber();
            }
        });

    }


    protected Toolbar getActionBarToolbar() {

        if (mActionBarToolbar == null) {
            mActionBarToolbar =  findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription(getResources()
                        .getString(R.string.app_name));
                //setSupportActionBar(mActionBarToolbar);

//                ActionBar actionBar = getSupportActionBar();
//                mActionBarToolbar.setCustomView(R.layout.main_top_header_bar);
//                mActionBarToolbar.set
                if (useToolbar()) {
                    setSupportActionBar(mActionBarToolbar);
                } else {
                    mActionBarToolbar.setVisibility(View.GONE);
                }

            }
        }

        return mActionBarToolbar;
    }


    private void setupNavDrawer() {
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        // use the hamburger menu
        if (useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
//                    .getDrawable(this, R.drawable.));
        }


        mNavigationView =  findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);


        TextView userName =  headerView.findViewById(R.id.tv_firm_name);
//        if (!sessionManager.getFirmName().equalsIgnoreCase("")) {
        userName.setText(myPref.getString("username", "username"));
//
//        } else {
//            userName.setText(sessionManager.getName());
//
//        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.profile) {
            Intent intent = new Intent(BaseActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        if(id == R.id.scan){
            requestCameraPermission();
            if (hasCameraPermission()) {
                Intent intent = new Intent(BaseActivity.this, QRCodeScanner.class);
                activityResultLauncher.launch(intent);
            }
        }
        if(id == R.id.marketing){
            viewPager.setCurrentItem(5, true);
        }

        if (id == R.id.mindacare) {


          /*  Calendar today = Calendar.getInstance();


            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(BaseActivity.this, new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {

                }
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

            builder.setActivatedMonth(Calendar.JANUARY)
                    .setMinYear(1990)
                    .setActivatedYear(2021)
                    .setMaxYear(2021)
                    .setMinMonth(Calendar.JANUARY)
                    .setTitle("Select month")
                    .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                    // .setMaxMonth(Calendar.OCTOBER)
                    // .setYearRange(1890, 1890)
                    // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                    //.showMonthOnly()
                    // .showYearOnly()
                    .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                        @Override
                        public void onMonthChanged(int selectedMonth) { // on month selected
                        }
                    }).setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                @Override
                public void onYearChanged(int selectedYear) { // on year selected
                }
            })
                    .build().show();
*/
            Intent in = new Intent(this, MindacareWeb.class);
            startActivity(in);
        }
        if (id == R.id.nav_aboutus) {
            viewPager.setCurrentItem(0, true);
        }
        if (id == R.id.nav_newsletter) {
            viewPager.setCurrentItem(1, true);
        }
        if (id == R.id.hradmin) {
            viewPager.setCurrentItem(3, true);
        }
        if (id == R.id.nav_manufacturing) {
            viewPager.setCurrentItem(2, true);
        }
       /* if (id == R.id.nav_finance) {
            viewPager.setCurrentItem(4, true);
        }
*/
        if (id == R.id.nav_it) {
            viewPager.setCurrentItem(4, true);
        }
        if (id == R.id.nav_support) {
            viewPager.setCurrentItem(7, true);
        }
       /* if (id == R.id.nav_setting) {

        }
       */
        if (id == R.id.nav_signout) {
            showMsg();
        }

//        else {
//            showComingSoonAlert();
//        }

        closeNavDrawer();
//        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
        return true;
    }


    public void showMsg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to Logout?");

        alertDialogBuilder.setPositiveButton("Sign out", (arg0, arg1) -> {
            SharedPreferences myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = myPref.edit();
            mEditor.putBoolean("IsLogin", false);
            mEditor.putBoolean("IsLoginNew", false);
            mEditor.apply();

            arg0.dismiss();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Intent myService = new Intent(getApplicationContext(), LoginActivity.class);
            stopService(myService);
            finish();

        });

        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    private Context context;

    public Context getCurrentContext() {
        return context;
    }

    public void setCurrentContext(Context context) {
        this.context = context;
    }

    private void requestCameraPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA); // your request code
    }

    private boolean hasCameraPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

    }

    public void updateRandomNumber(){
        CVPServices cvpServices = new CVPServices();
        /*cvpServices.updateRandomNumber(carotResponse -> {

        },empcode, randomNumber);
   */ }


}//end BaseActivity
