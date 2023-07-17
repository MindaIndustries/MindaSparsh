package com.minda.sparsh.connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 5/26/2017.
 */

public class HttpConnection {

    //    public static String BASE_URL = "https://www.mindasparsh.com/service.asmx/";
    //public static String BASE_URL = "http://52.172.191.61/TestMinda.com/service.asmx/";
    //  public static String BASE_URL = "http://176.9.28.166/MindaSparshTest/service.asmx/";
    //dev environment
    public static String BASE_URL = "https://dev.mindasparsh.com/service.asmx/";
    public static final String mindacareUrl = "https://dev.mindasparsh.com/mindacare/login.aspx?";
    public static final String itsurveyUrl = "https://dev.mindasparsh.com/itsurvey/defaulltM.aspx?";
    public static final String c_rurl = "https://dev.mindasparsh.com/ChangeRequest/ChangeRequestForm.aspx?";
    //  prod environment

   /* public static String BASE_URL = "https://app.mindasparsh.com/service.asmx/";
    public static final String mindacareUrl = "https://app.mindasparsh.com/mindacare/login.aspx?";
    public static final String itsurveyUrl = "https://app.mindasparsh.com/itsurvey/defaulltM.aspx?";
    public static final String cr_url = "https://app.mindasparsh.com/ChangeRequest/ChangeRequestForm.aspx?";
   */ public String requestGetContent(String url) {
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            String mUrl = BASE_URL + url;
            int lastIndex = mUrl.lastIndexOf("/");
            String match = mUrl.substring(lastIndex + 1, mUrl.length());
            URL apiUrl;
            apiUrl = new URL(mUrl);
            urlConnection = (HttpURLConnection) apiUrl.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = convertStreamToString(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }


    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


}
