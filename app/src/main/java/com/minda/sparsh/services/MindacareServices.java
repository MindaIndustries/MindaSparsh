package com.minda.sparsh.services;

import com.minda.sparsh.RetrofitClient;
import com.minda.sparsh.client.MindacareClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CheckinDetailsResponse;
import com.minda.sparsh.model.CityResponse;
import com.minda.sparsh.model.QuesResponse;
import com.minda.sparsh.model.StateResponse;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MindacareServices {

    public void getCheckinDetails(OnTaskComplete onTaskComplete, String empCode) {
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

    public void clockInclockOut(OnTaskComplete onTaskComplete, String empCode, String message, String InLattitiude, String InLongitude, String OutLattitude, String OutLongitude) {
        MindacareClient mindacareClient = RetrofitClient2.createServiceMindacare(MindacareClient.class);
        Call<String> call = mindacareClient.clockInclockOut(empCode, message, InLattitiude, InLongitude, OutLattitude, OutLongitude);
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

    public void mindacarequestions(OnTaskComplete onTaskComplete,String EmpCode) {
        MindacareClient mindacareClient = RetrofitClient2.createServiceMindacare(MindacareClient.class);
        Call<List<QuesResponse>> call = mindacareClient.mindacareQuestions(EmpCode);
        call.enqueue(new Callback<List<QuesResponse>>() {
            @Override
            public void onResponse(Call<List<QuesResponse>> call, Response<List<QuesResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<QuesResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });
    }

    public void getState(OnTaskComplete onTaskComplete){
        MindacareClient mindacareClient = RetrofitClient2.createServiceMindacare(MindacareClient.class);
        Call<List<StateResponse>> call = mindacareClient.getState();
        call.enqueue(new Callback<List<StateResponse>>() {
            @Override
            public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }

    public void getCity(OnTaskComplete onTaskComplete,String stateId){
        MindacareClient mindacareClient = RetrofitClient2.createServiceMindacare(MindacareClient.class);
        Call<List<CityResponse>> call = mindacareClient.getCity(stateId);
        call.enqueue(new Callback<List<CityResponse>>() {
            @Override
            public void onResponse(Call<List<CityResponse>> call, Response<List<CityResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<CityResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }


}
