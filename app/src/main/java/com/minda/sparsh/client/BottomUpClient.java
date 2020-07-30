package com.minda.sparsh.client;

import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.model.SixMModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BottomUpClient {

    @GET("GetSixEmHeads")
    Call<List<SixMModel>> getSixM();

    @GET("GetConcerns")
    Call<List<BottomUpConcern>> getConcerns(@Query("EmpCode") String EmpCode);
   
    @FormUrlEncoded
    @POST("SaveBottomUp")
    Call<String> saveConcern(@Field("RaisedBy") String RaisedBy, @Field("RaisedOn") String RaisedOn, @Field("Unit") String Unit, @Field("Department") String Department, @Field("ReferenceNo") String ReferenceNo, @Field("ExistingSystem") String ExistingSystem, @Field("ProposedSystem") String ProposedSystem, @Field("Benefit") String Benefit, @Field("ESFile") String ESFile, @Field("ESFileByte") String ESFileByte, @Field("PSFile") String PSFile, @Field("PSFileByte") String PSFileByte, @Field("BenFile") String BenFile, @Field("BenFileByte") String BenFileByte, @Field("FirstName") String FirstName);


    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);


}
