package com.minda.sparsh.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FireBaseClient {
    @GET("SaveFireID")
    Call<String> saveFirebaseID(@Query("EmpCode") String EmpCode, @Query("FireID") String FirebaseID,@Query("LogInVia") String LogInVia);
}
