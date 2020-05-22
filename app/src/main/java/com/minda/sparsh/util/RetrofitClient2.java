package com.minda.sparsh.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
//        public static final String BASE_URL = "http://52.172.191.61/testminda.com/Service.asmx/ ";
    //public static final String BASE_URL = "https://app.mindasparsh.com/service.asmx/";
    public static final String BASE_URL = "http://176.9.28.166/MindaSparshTest/Service.asmx/";
    //public static final String BASE_URL = "http://52.172.191.61/Test.mindasparsh.com/Service.asmx/";

    public static final String CKEY = "bWRhQHNQciRyWiNHISE=";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
} 
