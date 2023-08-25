package com.minda.sparsh.services;

import com.minda.sparsh.client.DWMClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.DWMPlantModel;
import com.minda.sparsh.model.DWMPostModel;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DWMServices {

    public void getDwmMasterData(OnTaskComplete onTaskComplete, String empcode, String date, String type){
        DWMClient dwmClient = RetrofitClient2.getClientScanQR(DWMClient.class);
        Call<List<DWMPlantModel>> call = dwmClient.getDwmMasterData(empcode,date,type);
        call.enqueue(new Callback<List<DWMPlantModel>>() {
            @Override
            public void onResponse(Call<List<DWMPlantModel>> call, Response<List<DWMPlantModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<DWMPlantModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void saveRecord(OnTaskComplete onTaskComplete, String empcode, String date,String type,List<DWMPostModel.SaveList> saveList ){
        DWMClient dwmClient = RetrofitClient2.getClientScanQR(DWMClient.class);
        Call<String> call = dwmClient.saveRecord(empcode,date,type,saveList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }
}
