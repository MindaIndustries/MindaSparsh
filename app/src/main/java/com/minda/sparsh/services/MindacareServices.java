package com.minda.sparsh.services;

import com.minda.sparsh.client.MindacareClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CheckinDetailsResponse;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MindacareServices {

    public  void getCheckinDetails(OnTaskComplete onTaskComplete, String empCode){
        MindacareClient mindacareClient = RetrofitClient2.createServiceMindacare(MindacareClient.class);
        Call<List<CheckinDetailsResponse>> call = mindacareClient.getCheckinDetails(empCode);
        call.enqueue(new Callback<List<CheckinDetailsResponse>>() {
            @Override
            public void onResponse(Call<List<CheckinDetailsResponse>> call, Response<List<CheckinDetailsResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<CheckinDetailsResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }
}
