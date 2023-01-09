package com.minda.sparsh.services;

import com.minda.sparsh.client.AlmsClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveTypeModel;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmsServices {

    public void getConsolidatedReport(OnTaskComplete onTaskComplete, String empcode, String fromtDate, String toDate){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<AlmsReportModel>> call = almsClient.getConsolidateReport(empcode,fromtDate, toDate);
        call.enqueue(new Callback<List<AlmsReportModel>>() {
            @Override
            public void onResponse(Call<List<AlmsReportModel>> call, Response<List<AlmsReportModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<AlmsReportModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    
    public void getLeaveBalance(OnTaskComplete onTaskComplete, String empcode, String year, String leaveType){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveBalanceModel>> call = almsClient.GetLeavebalance(empcode,year,leaveType);
        call.enqueue(new Callback<List<LeaveBalanceModel>>() {
            @Override
            public void onResponse(Call<List<LeaveBalanceModel>> call, Response<List<LeaveBalanceModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveBalanceModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getLeaveTypes(OnTaskComplete onTaskComplete,String empcode){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveTypeModel>> call = almsClient.getLeaveTypes(empcode);
        call.enqueue(new Callback<List<LeaveTypeModel>>() {
            @Override
            public void onResponse(Call<List<LeaveTypeModel>> call, Response<List<LeaveTypeModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveTypeModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }
}
