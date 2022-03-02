package com.minda.sparsh.services;

import com.minda.sparsh.client.CVPClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.CVPViewCalendarModel;
import com.minda.sparsh.model.CalendarTypeModel;
import com.minda.sparsh.model.CustomerModel;
import com.minda.sparsh.model.LocationModel;
import com.minda.sparsh.model.MeetingTypeModel;
import com.minda.sparsh.model.SaveCalendarResponse;
import com.minda.sparsh.model.WeekModel;
import com.minda.sparsh.model.YearModel;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class CVPServices {

    public void getWeekData(OnTaskComplete onTaskComplete, String date){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<WeekModel> call = cvpClient.getWeeks(date);
        call.enqueue(new Callback<WeekModel>() {
            @Override
            public void onResponse(Call<WeekModel> call, Response<WeekModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<WeekModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getCustomers(OnTaskComplete onTaskComplete, String empcode){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<CustomerModel> call = cvpClient.getCustomers(empcode);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<CustomerModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getLocation(OnTaskComplete onTaskComplete, String customerId){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<LocationModel> call = cvpClient.getLocation(customerId);
        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getMeetingType(OnTaskComplete onTaskComplete){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<MeetingTypeModel> call = cvpClient.getMeetingType();
        call.enqueue(new Callback<MeetingTypeModel>() {
            @Override
            public void onResponse(Call<MeetingTypeModel> call, Response<MeetingTypeModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(Call<MeetingTypeModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }

    public void getCalendarTypes(OnTaskComplete onTaskComplete){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<CalendarTypeModel> call = cvpClient.getCalendarTypes();
        call.enqueue(new Callback<CalendarTypeModel>() {
            @Override
            public void onResponse(Call<CalendarTypeModel> call, Response<CalendarTypeModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<CalendarTypeModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void saveCalendarBooking(OnTaskComplete onTaskComplete,int WeekDaysId,String CustomerId, String CustLocationId, int MeetingTypeId, String Year, String CreatedBy, int CalenderType){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<SaveCalendarResponse> call = cvpClient.saveCalendarBooking(WeekDaysId,CustomerId,CustLocationId,MeetingTypeId,Year,CreatedBy,CalenderType);
        call.enqueue(new Callback<SaveCalendarResponse>() {
            @Override
            public void onResponse(Call<SaveCalendarResponse> call, Response<SaveCalendarResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<SaveCalendarResponse> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getYear(OnTaskComplete onTaskComplete){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<YearModel> call = cvpClient.getYear();
        call.enqueue(new Callback<YearModel>() {
            @Override
            public void onResponse(Call<YearModel> call, Response<YearModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(Call<YearModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });
    }
    public void getCalendarMeetings(OnTaskComplete onTaskComplete, String empCode, String Year, String Month, String CalendarType){
        CVPClient cvpClient = RetrofitClient2.getClientCVP(CVPClient.class);
        Call<CVPViewCalendarModel> call = cvpClient.getMeetings(empCode,Year,Month,CalendarType);
        call.enqueue(new Callback<CVPViewCalendarModel>() {
            @Override
            public void onResponse(Call<CVPViewCalendarModel> call, Response<CVPViewCalendarModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<CVPViewCalendarModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
}
