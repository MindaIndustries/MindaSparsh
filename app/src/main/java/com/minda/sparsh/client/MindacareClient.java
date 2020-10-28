package com.minda.sparsh.client;

import com.minda.sparsh.model.CheckinDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MindacareClient {

    @GET("API_Mindacare_GetUserDetailsCheckin")
    Call<List<CheckinDetailsResponse>> getCheckinDetails(@Query("EmpCode") String EmpCode);


}
