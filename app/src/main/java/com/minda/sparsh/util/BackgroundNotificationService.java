package com.minda.sparsh.util;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.services.BottomUpConcernServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.HttpsURLConnection;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import okhttp3.ResponseBody;

public class BackgroundNotificationService extends IntentService {

    public static final String PROGRESS_UPDATE = "progress_update";

    public BackgroundNotificationService() {
        super("BackgroundNotificationService");
    }


    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    String name;
    SharedPreferences myPref;

    public BackgroundNotificationService(String name) {
        super(name);
        this.name = name;

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);

        name = myPref.getString("download", "");

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent();
        intent1.setAction(Intent.ACTION_VIEW);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "" + name);

        //  File file = new File(getExternalFilesDir(null) + File.separator + ""+name); //
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);

        intent1.setDataAndType(photoURI, "*/*"); //
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setDefaults(0)
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        downloadFile();
    }

    public void downloadFile() {
        //    Toast.makeText(BottomUpConcernDetailActivity.this,"Downoading...",Toast.LENGTH_LONG).show();
        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.downloadFile(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    ResponseBody responseBody = (ResponseBody) carotResponse.getData();
                    boolean writtenToDisk = writeResponseBodyToDisk(responseBody, name);

                }

            }
        }, name);
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileUrl) {
        try {
            // todo change the file location/name according to your needs
            //   File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + ""+fileUrl);
            //  File futureStudioIconFile = new File(Environment.getExternalStorageDirectory(),
            //        System.currentTimeMillis() +""+ fileUrl);

            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "" + name);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                long total = 0;
                boolean downloadComplete = false;

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    total += read;
                    int progress = (int) ((double) (total * 100) / (double) fileSize);
                    updateNotification(progress);

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    downloadComplete = true;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                onDownloadComplete(downloadComplete);

                outputStream.flush();

                return true;
            } catch (Exception e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            return false;
        }
    }


    private void updateNotification(int currentProgress) {

        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendProgressUpdate(boolean downloadComplete) {

        Intent intent = new Intent(PROGRESS_UPDATE);
        intent.putExtra("downloadComplete", downloadComplete);
        LocalBroadcastManager.getInstance(BackgroundNotificationService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean downloadComplete) {
        sendProgressUpdate(downloadComplete);
        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Download Complete")/*.setProgress(0,0,false)*/;
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }


}

