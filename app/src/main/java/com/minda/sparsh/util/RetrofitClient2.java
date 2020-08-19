package com.minda.sparsh.util;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient2 {
    //        public static final String BASE_URL = "http://52.172.191.61/testminda.com/Service.asmx/ ";
    //public static final String BASE_URL = "https://app.mindasparsh.com/service.asmx/";
    //master public static final String BASE_URL = "http://176.9.28.166/MindaSparshTest/Service.asmx/";
    // public static final String BASE_URL = "http://52.172.191.61/Test.mindasparsh.com/Service.asmx/";
    //dev
    public static final String BASE_URL = "http://dev.mindasparsh.com/Service.asmx/";
    public static final String EHS_BASE_URL = "http://dev.mindasparsh.com/ServiceEHS.asmx/";
    public static final String ehs_img = "http://dev.mindasparsh.com/ehs/files/";
    public static final String bottomup_img = "http://dev.mindasparsh.com/bottomup/files/";

    public static final String BottomUpBaseUrl = "http://dev.mindasparsh.com/BottomUpApi.asmx/";
    public static final String suggestionBaseUrl = "http://dev.mindasparsh.com/SuggestionAPI.asmx/";

    //prod
    /*  public static final String BASE_URL = "https://app.mindasparsh.com/Service.asmx/";
      public static final String ehs_img = "https://app.mindasparsh.com/ehs/files/";
      public static final String EHS_BASE_URL = "https://app.mindasparsh.com/ServiceEHS.asmx/";
          public static final String BottomUpBaseUrl = "https://app.mindasparsh.com/BottomUpApi.asmx/";
    public static final String bottomup_img = "https://app.mindasparsh.com/bottomup/files/";
    public static final String suggestionBaseUrl = "https://app.mindasparsh.com/SuggestionAPI.asmx/";

*/
    // public static final String CKEY = "bWRhQHNQciRyWiNHISE=";
    public static final String CKEY = "mda@sPr$rZ#G!!";

    private static Retrofit retrofit = null;
    static Dispatcher dispatcher1 = new Dispatcher();

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

    public static <S> S createServiceEHS(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("User", "Minda89652$%@@#")
                        .addHeader("Password", "98541$%#85%$PUAtadniM")
                        .method(original.method(), original.body())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        dispatcher1.setMaxRequests(3000);
        httpClient.dispatcher(dispatcher1);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EHS_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }
    public static <S> S createServiceBottomUponcern(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("User", "MindaBtm56$#45#@")
                        .addHeader("Password", "985Btm56$#AtadniM")
                        .method(original.method(), original.body())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        dispatcher1.setMaxRequests(3000);
        httpClient.dispatcher(dispatcher1);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BottomUpBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }
    public static <S> S downloadService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("User", "MindaBtm56$#45#@")
                        .addHeader("Password", "985Btm56$#AtadniM")
                        .method(original.method(), original.body())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        dispatcher1.setMaxRequests(3000);
        httpClient.dispatcher(dispatcher1);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(bottomup_img)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceSuggestionBox(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("User", "MindaSugg56$#45#@")
                        .addHeader("Password", "985Sugg656$#AtadniM")
                        .method(original.method(), original.body())
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        dispatcher1.setMaxRequests(3000);
        httpClient.dispatcher(dispatcher1);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(suggestionBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }




    public static <S> S createServiceEHSUpload(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Response response = chain.proceed(original);
                return response;
            }
        });

        dispatcher1.setMaxRequests(3000);
        httpClient.dispatcher(dispatcher1);

        OkHttpClient client = httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EHS_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

} 
