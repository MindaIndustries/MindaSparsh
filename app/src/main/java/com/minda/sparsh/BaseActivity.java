package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.minda.sparsh.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView mNavigationView;
    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ProgressDialog progressDialog;
    public ViewPager viewPager;
    SharedPreferences myPref;


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
    }


    protected Toolbar getActionBarToolbar() {

        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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


        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }

        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);


        TextView userName = (TextView) headerView.findViewById(R.id.tv_firm_name);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
         /*   Intent intent = new Intent(BaseActivity.this,ProfileActivity.class);
            startActivity(intent);
       */
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
        if (id == R.id.nav_finance) {
            viewPager.setCurrentItem(4, true);
        }

        if (id == R.id.nav_it) {
            viewPager.setCurrentItem(5, true);
        }
        if (id == R.id.nav_support) {
            viewPager.setCurrentItem(7, true);
        }
        if (id == R.id.nav_setting) {

        }
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
        alertDialogBuilder.setMessage("are you sure?");

        alertDialogBuilder.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = myPref.edit();
                mEditor.putBoolean("IsLogin", false);
                mEditor.commit();

                arg0.dismiss();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Intent myService = new Intent(getApplicationContext(), LoginActivity.class);
                stopService(myService);
                finish();

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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

}//end BaseActivity
