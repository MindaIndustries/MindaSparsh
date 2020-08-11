package com.minda.sparsh.client;

import com.minda.sparsh.model.AssignedConcernModel;
import com.minda.sparsh.model.AutoSuggestModel;
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

    @GET("GetAssignConcerns")
    Call<List<BottomUpConcern>> getAssignedConcerns(@Query("EmpCode") String EmpCode);

    @GET("AssignConcern")
    Call<String> assignAConcern(@Query("ConcernNo") String ConcernNo,@Query("EmpCode") String EmpCode,@Query("TargetDt") String TargetDt);

    @GET("CompleteConcern")
    Call<String> markCompleteConcern(@Query("ConcernNo") String ConcernNo);

    @FormUrlEncoded
    @POST("SaveData")
    Call<String> submitSuggestion(@Field("Suggestion") String Suggestion, @Field("EmpCode") String EmpCode,@Field("CostAmount") String CostAmount, @Field("Other") String Other,@Field("FileName") String FileName, @Field("FileType") String FileType, @Field("FileByte") String FileByte);

    @GET("GetAutoName")
    Call<List<AutoSuggestModel>> getAutoSuggestion(@Query("prefixText") String prefixText);

}
