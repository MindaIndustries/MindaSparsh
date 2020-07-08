package com.minda.sparsh;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dmin on 2/23/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    String TAG = "test";

    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
}
