package com.minda.sparsh.services;


import com.minda.sparsh.client.FireBaseClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.util.RetrofitClient2;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseService {

    public void saveFirebaseID(OnTaskComplete onTaskComplete, String EmpCode, String firebaseToken) {

        FireBaseClient fireBaseClient = RetrofitClient2.createServiceSaveFirebaseID(FireBaseClient.class);
        Call<String> call = fireBaseClient.saveFirebaseID(EmpCode, firebaseToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

}
