package com.minda.sparsh.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Toast;

import com.minda.sparsh.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by admin on 5/26/2017.
 */

public class Utility {
    public static String REQUEST_ID;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static boolean isOnline(Context ctx) {
        boolean isConnected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            isConnected = cm.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception ex) {
            isConnected = false;
        }
        return isConnected;
    }

    public static void saveFileToSdCard(File destinationFile, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            FileOutputStream fo;
            destinationFile.createNewFile();
            fo = new FileOutputStream(destinationFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToast(Context applicationContext, String string) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show();
    }

    public static boolean isValidEmail(String email, Context context) {
        String EMAIL_REGEX = context.getResources().getString(R.string.email_val);
        boolean b = email.matches(EMAIL_REGEX);
        System.out.println("is e-mail: " + email + " :Valid = " + b);
        return b;
    }

    public static String getDisplayFromDb(String d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(d);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String convertedDateString = df.format(convertedDate);
        return convertedDateString;
    }

    public static String getDBToDisplay(String d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
        Date convertedDate = new Date();

        try {
            convertedDate = df.parse(d);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String convertedDateString = dateFormat.format(convertedDate);

        return convertedDateString;
    }

    public static String getDBToExpDisplay(String d) {
        String convertedDateString = "";
        SimpleDateFormat getFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat setFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        Date convertedDate = new Date();
        try {
            convertedDate = getFormat.parse(d);
            convertedDateString = setFormat.format(convertedDate);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convertedDateString;
    }

    public static String getDateWithMMM(String quizDate) {
        SimpleDateFormat getFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat setFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
        Date convertedDate = new Date();

        try {
            convertedDate = getFormat.parse(quizDate);
            String convertedDateString = setFormat.format(convertedDate);

            return convertedDateString;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String getString(InputStream in) {
        StringBuilder sb = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String replace(String _text, String _searchStr, String _replacementStr) {
        // String buffer to store str
        StringBuffer sb = new StringBuffer();

        // Search for search
        int searchStringPos = _text.indexOf(_searchStr);
        int startPos = 0;
        int searchStringLength = _searchStr.length();

        // Iterate to add string
        while (searchStringPos != -1) {
            sb.append(_text.substring(startPos, searchStringPos)).append(
                    _replacementStr);
            startPos = searchStringPos + searchStringLength;
            searchStringPos = _text.indexOf(_searchStr, startPos);
        }

        // Create string
        sb.append(_text.substring(startPos, _text.length()));

        return sb.toString();

    }

    public static String convertTimeToDisplay(String checked_date) {
        String strDate = "";
        //    TimeZone tz = TimeZone.getDefault();
        //    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //    df.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        Long ll = null;
        if (checked_date != null && !checked_date.equalsIgnoreCase("")) {
            ll = new Long(checked_date);
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(ll * 1000);
            // calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currenTimeZone = (Date) calendar.getTime();
            String ChatDate = sdf.format(currenTimeZone);
            String currentDate = getCDate(ChatDate);

            if (currentDate != null) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                Date currenTimeZone1 = (Date) calendar.getTime();
                strDate = sdf1.format(currenTimeZone1);
                //          String[] str = strDate.split("");
                //          strDate = str[3];
            } else
                strDate = ChatDate;

        }
        return strDate;

    }

    private static String getCDate(String strDate) {
        String date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String CurrentDate = getCurrentDate(df);
        if (strDate.equalsIgnoreCase(CurrentDate))
            date = "";

        return date;
    }


    private static String getCurrentDate(SimpleDateFormat df) {
        // TODO Auto-generated method stub
        Date date = new Date();
        return df.format(date);
    }


    public static String getMyTimeToDisplay(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startD = format.parse(time);
            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
            String s2 = format2.format(startD);
            return s2;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDateDayAndTime(String startDateTime) {
        String value = "Date : ";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startD = format.parse(startDateTime);
            Calendar c = Calendar.getInstance();
            c.setTime(startD);

            SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
            value = value + formatter.format(c.getTime()) + " | ";
//            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            SimpleDateFormat format2 = new SimpleDateFormat("MMM dd, yyyy");
            String s2 = format2.format(startD);
            value = value + s2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getTimeDisplay(String startDateTime, String endDateTime) {
        String value = "Time : ";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startD = format.parse(startDateTime);
            Date endD = format.parse(endDateTime);
            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
            value = value + format2.format(startD) + " - " + format2.format(endD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getTimeDisplayNotification(String startDateTime, String endDateTime, String country) {
        String value = "Hello! This is just a reminder that your booking on ";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startD = format.parse(startDateTime);
            Date endD = format.parse(endDateTime);
            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat format3 = new SimpleDateFormat("MMM dd, yyyy");
            value = value + format3.format(startD) + " at " + format2.format(startD) + " - " + format2.format(endD) + ", " + country;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
           /* if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }*/
            if (hasReadPermissions(context) && hasWritePermissions(context)) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

        } else {
            return true;
        }
        return false;

    }

    public static boolean hasReadPermissions(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasWritePermissions(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
}