package com.minda.sparsh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.analytics.FirebaseAnalytics;


public class SplashActivity extends Activity {
    private static final int SPLASH_TIME_OUT = 3000;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Splash");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Enter");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        new Handler().postDelayed(() -> {
            SharedPreferences myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

            Intent i;
            if (myPref.getBoolean("IsLoginNew", false))
                i = new Intent(SplashActivity.this, DashBoardActivity.class);
            else
                i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
