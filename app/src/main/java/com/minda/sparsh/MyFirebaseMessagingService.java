package com.minda.sparsh;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.minda.sparsh.NotificationActivity;
import com.minda.sparsh.R;

/**
 * Created by dmin on 2/23/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "test";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("body"));
            //sendMyNotification(remoteMessage.getData().get("body"));
            //  sendMyNotification(remoteMessage.getData().get("body"),remoteMessage.getData().get("delivery"),remoteMessage.getData().get("location"),remoteMessage.getData().get("instruction"));


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            // sendMyNotification(remoteMessage.getData().get("body"),remoteMessage.getData().get("delivery"),remoteMessage.getData().get("location"),remoteMessage.getData().get("instruction"));
            sendMyNotification(remoteMessage.getNotification().getBody().toString());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        // ,String delivery,String location,String instruction
    }

    private void sendMyNotification(String body) {

        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.putExtra("Orderid", body);
//        intent.putExtra("delivery",delivery);
//        intent.putExtra("location",location);
//        intent.putExtra("instruction",instruction);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.loncherm)
                .setContentTitle("Minda Sparsh")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());


    }

}
