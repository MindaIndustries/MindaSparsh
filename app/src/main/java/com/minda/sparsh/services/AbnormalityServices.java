package com.minda.sparsh.services;

import com.minda.sparsh.client.AbnormalityClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AbnormalityNameModel;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbnormalityServices {

    public void getAutoNameAbnormality(OnTaskComplete onTaskComplete, String prefix, String val) {
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AbnormalityNameModel> call = abnormalityClient.getAutoNameAbnormlaity(prefix, val);
        call.enqueue(new Callback<AbnormalityNameModel>() {
            @Override
            public void onResponse(Call<AbnormalityNameModel> call, Response<AbnormalityNameModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AbnormalityNameModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }
}
