package com.minda.sparsh.client;

import com.minda.sparsh.model.DWMPlantModel;
import com.minda.sparsh.model.DWMPostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DWMClient {
    @FormUrlEncoded
    @POST("DWR/GetMasterData")
    Call<List<DWMPlantModel>> getDwmMasterData(@Field("Empcode") String Empcode, @Field("DATE") String DATE, @Field("Type") String Type);

    @FormUrlEncoded
    @POST("DWR/SaveRecord")
    Call<String> saveRecord(@Field("EmpCode") String Empcode, @Field("Date") String Date, @Field("Type") String Type, @Field("SaveList")List<DWMPostModel.SaveList> saveList);
}
