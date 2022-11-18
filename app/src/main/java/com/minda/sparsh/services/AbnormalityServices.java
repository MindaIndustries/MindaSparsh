package com.minda.sparsh.services;

import com.minda.sparsh.client.AbnormalityClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AbnormalityNameModel;
import com.minda.sparsh.model.AssignResponseModel;
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

    public void assignAbnormality(OnTaskComplete onTaskComplete, String ConcernNo, String EmpCode, String TargetDt, String Remark, String AssignTo){
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AssignResponseModel> call = abnormalityClient.assignAbnormality(ConcernNo,EmpCode,TargetDt,Remark,AssignTo);
        call.enqueue(new Callback<AssignResponseModel>() {
            @Override
            public void onResponse(Call<AssignResponseModel> call, Response<AssignResponseModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AssignResponseModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void sendBackToUser(OnTaskComplete onTaskComplete, String ConcernNo, String EmpCode, String remark){
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AssignResponseModel> call = abnormalityClient.sendBacktoUser(ConcernNo,EmpCode, remark);
        call.enqueue(new Callback<AssignResponseModel>() {
            @Override
            public void onResponse(Call<AssignResponseModel> call, Response<AssignResponseModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AssignResponseModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void sendBackToHOD(OnTaskComplete onTaskComplete, String ConcernNo, String EmpCode, String remark){
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AssignResponseModel> call = abnormalityClient.sendBacktoHOD(ConcernNo,EmpCode, remark);
        call.enqueue(new Callback<AssignResponseModel>() {
            @Override
            public void onResponse(Call<AssignResponseModel> call, Response<AssignResponseModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AssignResponseModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void verifyclose(OnTaskComplete onTaskComplete, String ConcernNo, String EmpCode, String remark){
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AssignResponseModel> call = abnormalityClient.verifyClose(ConcernNo,EmpCode,remark);
        call.enqueue(new Callback<AssignResponseModel>() {
            @Override
            public void onResponse(Call<AssignResponseModel> call, Response<AssignResponseModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AssignResponseModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void deleteAbnormality(OnTaskComplete onTaskComplete, String Empcode, String concerno){
        AbnormalityClient abnormalityClient = RetrofitClient2.getClientScanQR(AbnormalityClient.class);
        Call<AssignResponseModel> call = abnormalityClient.deleteAbnormality(Empcode, concerno);
        call.enqueue(new Callback<AssignResponseModel>() {
            @Override
            public void onResponse(Call<AssignResponseModel> call, Response<AssignResponseModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AssignResponseModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
}
