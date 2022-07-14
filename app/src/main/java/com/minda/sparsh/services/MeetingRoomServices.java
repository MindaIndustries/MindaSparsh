package com.minda.sparsh.services;

import com.minda.sparsh.client.MeetingRoomClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AutoNameModel;
import com.minda.sparsh.model.CityModel;
import com.minda.sparsh.model.MeetingBookResponse;
import com.minda.sparsh.model.MeetingDetailModel;
import com.minda.sparsh.model.MeetingRoomBookData;
import com.minda.sparsh.model.MeetingRoomDetailData;
import com.minda.sparsh.model.MeetingRoomListModel;
import com.minda.sparsh.model.MeetingRoomModel;
import com.minda.sparsh.util.RetrofitClient2;

import org.json.JSONObject;

import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class MeetingRoomServices {

    public void getCity(OnTaskComplete onTaskComplete, String EmpCode){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<CityModel> call = meetingRoomClient.getCity(EmpCode);
        call.enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getUnitByCity(OnTaskComplete onTaskComplete, String city){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingRoomModel> call = meetingRoomClient.getUnitByCity(city);
        call.enqueue(new Callback<MeetingRoomModel>() {
            @Override
            public void onResponse(Call<MeetingRoomModel> call, Response<MeetingRoomModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingRoomModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getMeetingRooms(OnTaskComplete onTaskComplete, String EmpCode, boolean RoomType, String UnitCode ){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingRoomListModel> call = meetingRoomClient.getMeetingRooms(EmpCode,RoomType,UnitCode);
        call.enqueue(new Callback<MeetingRoomListModel>() {
            @Override
            public void onResponse(Call<MeetingRoomListModel> call, Response<MeetingRoomListModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingRoomListModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });
    }
    public void getMeetingRoomDetail(OnTaskComplete onTaskComplete,String EmpCode,String ActType, String BookingDate,int MeetingRoomID, String UnitCode){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingRoomDetailData> call = meetingRoomClient.getMeetingRoomDetail(EmpCode,ActType,BookingDate,MeetingRoomID,UnitCode);
        call.enqueue(new Callback<MeetingRoomDetailData>() {
            @Override
            public void onResponse(Call<MeetingRoomDetailData> call, Response<MeetingRoomDetailData> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
            @Override
            public void onFailure(Call<MeetingRoomDetailData> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void takeActionForMeetingRoom(OnTaskComplete onTaskComplete,String MeetingRoomSlotID,int MeetingRoomID,String EmpCode,String BookingDate, String Purpose, String AttendeeInt, String AttendeeExt,String ActType, String MeetingID, String UnitCode,boolean RoomType){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingBookResponse> call = meetingRoomClient.bookMeetingRoom(MeetingRoomSlotID,MeetingRoomID,EmpCode,BookingDate,Purpose,AttendeeInt,AttendeeExt,ActType,MeetingID,UnitCode,RoomType, "A");
        call.enqueue(new Callback<MeetingBookResponse>() {
            @Override
            public void onResponse(Call<MeetingBookResponse> call, Response<MeetingBookResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                else if(response.code()==406){
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        carotResponse.setData(error.get("data"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingBookResponse> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getAutoName(OnTaskComplete onTaskComplete, String prefixText){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<AutoNameModel> call = meetingRoomClient.getAutoName(prefixText);
        call.enqueue(new Callback<AutoNameModel>() {
            @Override
            public void onResponse(Call<AutoNameModel> call, Response<AutoNameModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<AutoNameModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getMeetingDetails(OnTaskComplete onTaskComplete,String MeetingRoomSlotID, String BookingDate){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingDetailModel> call = meetingRoomClient.getMeetingDetails(MeetingRoomSlotID,BookingDate);
        call.enqueue(new Callback<MeetingDetailModel>() {
            @Override
            public void onResponse(Call<MeetingDetailModel> call, Response<MeetingDetailModel> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingDetailModel> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void checkReservedRoom(OnTaskComplete onTaskComplete,String EmpCode,String BookingDate,String MeetingRoomID){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingBookResponse> call = meetingRoomClient.checkReservedRoom(EmpCode,BookingDate,MeetingRoomID);
        call.enqueue(new Callback<MeetingBookResponse>() {
            @Override
            public void onResponse(Call<MeetingBookResponse> call, Response<MeetingBookResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                else if(response.code()==406){
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        carotResponse.setData(error.get("data"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code()==400){
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        carotResponse.setData(error.get("data"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingBookResponse> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getBookedMeetings(OnTaskComplete onTaskComplete,String EmpCode,String FromDate, String ToDate,String SearchParameter, String SearchFor, String CompID, String SortBy){
        MeetingRoomClient meetingRoomClient = RetrofitClient2.createServiceMR(MeetingRoomClient.class);
        Call<MeetingRoomBookData> call =meetingRoomClient.getBookedMeetings(EmpCode,FromDate,ToDate,SearchParameter,SearchFor,CompID,SortBy);
        call.enqueue(new Callback<MeetingRoomBookData>() {
            @Override
            public void onResponse(Call<MeetingRoomBookData> call, Response<MeetingRoomBookData> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<MeetingRoomBookData> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
}
