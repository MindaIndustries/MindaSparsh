package com.minda.sparsh.client;

import com.minda.sparsh.model.AbnormalityNameModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AbnormalityClient {
    @GET("service/GetAutoNameAbnormlaity")
    Call<AbnormalityNameModel> getAutoNameAbnormlaity(@Query("prefixText") String prefixText, @Query("val") String val);
}
