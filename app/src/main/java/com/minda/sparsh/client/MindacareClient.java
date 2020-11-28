package com.minda.sparsh.client;

import com.minda.sparsh.model.CheckinDetailsResponse;
import com.minda.sparsh.model.QuesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MindacareClient {

    @GET("API_Mindacare_GetUserDetailsCheckin")
    Call<List<CheckinDetailsResponse>> getCheckinDetails(@Query("EmpCode") String EmpCode);

    @GET("MindaCareClockInOut")
    Call<String> clockInclockOut(@Query("EmpCode") String EmpCode, @Query("Message") String message,@Query("InLattitiude")String InLattitiude,@Query("InLongitude") String InLongitude,@Query("OutLattitude") String OutLattitude,@Query("OutLongitude")String OutLongitude);

    @GET("MindaCareQuestions")
    Call<List<QuesResponse>>  mindacareQuestions(@Query("EmpCode") String EmpCode);



}
