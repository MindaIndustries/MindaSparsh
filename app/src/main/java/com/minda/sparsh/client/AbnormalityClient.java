package com.minda.sparsh.client;

import com.minda.sparsh.model.AbnormalityNameModel;
import com.minda.sparsh.model.AssignResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AbnormalityClient {
    @GET("service/GetAutoNameAbnormlaity")
    Call<AbnormalityNameModel> getAutoNameAbnormlaity(@Query("prefixText") String prefixText, @Query("val") String val);

    @FormUrlEncoded
    @POST("service/AssignConcern")
    Call<AssignResponseModel> assignAbnormality(@Field("ConcernNo") String ConcernNo, @Field("EmpCode") String EmpCode, @Field("TargetDt") String TargetDt, @Field("Remark") String Remark, @Field("AssignTo") String AssignTo);

    @FormUrlEncoded
    @POST("service/SendBackTOUser")
    Call<AssignResponseModel> sendBacktoUser(@Field("ConcernNo") String ConcernNo, @Field("EmpCode") String EmpCode);

    @FormUrlEncoded
    @POST("service/SendBackTOHOD")
    Call<AssignResponseModel> sendBacktoHOD(@Field("ConcernNo") String ConcernNo, @Field("EmpCode") String EmpCode);


}
