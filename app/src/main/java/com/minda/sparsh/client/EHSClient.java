package com.minda.sparsh.client;

import com.minda.sparsh.model.EHSCategoryModel;
import com.minda.sparsh.model.EHSIdentifiedLocationModel;
import com.minda.sparsh.model.EHSObsModel;
import com.minda.sparsh.model.EHSObservationModel;
import com.minda.sparsh.model.EHSSubCategoryModel;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.SafetyOfficerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EHSClient {
    @GET("GetObservations")
    Call<List<EHSObservationModel>> getObservationTypes();

    @GET("GetIdentifiedS")
    Call<List<EHSObsModel>> getObservations(@Query("Empcode") String empcode);

    @GET("GetUnits")
    Call<List<EHSUnitModel>> getUnits(@Query("unitcode") String unitcode);

    @GET("GetUnitSafetyOfficers")
    Call<List<SafetyOfficerModel>> getSafetyOfficers(@Query("unitcode") String unitcode);

    @GET("GetIdentifiedLocations")
    Call<List<EHSIdentifiedLocationModel>> getIdentifiedLocations();

    @GET("GetCategories")
    Call<List<EHSCategoryModel>> getCategories(@Query("ObservationID") String ObservationID);

    @GET("GetSubCategories")
    Call<List<EHSSubCategoryModel>> getSubCategories(@Query("CatID") String CatID);

    @FormUrlEncoded
    @POST("SaveEHSV2")
    Call<String> submitEHS(@Field("EmpCode") String EmpCode, @Field("ActNo") String ActNo, @Field("ActDate") String ActDate, @Field("HOD") String HOD, @Field("UnitSafetyOfficer") String UnitSafetyOfficer, @Field("UnitCode") String UnitCode, @Field("Description") String Description, @Field("Attachment") String Attachment, @Field("AttachmentType") String AttachmentType, @Field("LocationID") String LocationID, @Field("CategoryID") String CategoryID, @Field("SubCategoryID") String SubCategoryID, @Field("ObservationID") String ObservationID, @Field("IncidenceHour") String IncidenceHour, @Field("IncidenceMin") String IncidenceMin, @Field("IncidenceZone") String IncidenceZone, @Field("IncidenceActionTaken") String IncidenceActionTaken, @Field("ObservationName") String ObservationName, @Field("LocationName") String LocationName,@Field("Files") String bytes);

    @FormUrlEncoded
    @POST("UpdateEhsV2")
    Call<String> updateEhs(@Field("ActID") String ActID, @Field("EmpCode") String EmpCode, @Field("ActNo") String ActNo, @Field("ActDate") String ActDate, @Field("HOD") String HOD, @Field("UnitSafetyOfficer") String UnitSafetyOfficer, @Field("UnitCode") String UnitCode, @Field("Description") String Description, @Field("Attachment") String Attachment, @Field("AttachmentType") String AttachmentType, @Field("LocationID") String LocationID, @Field("CategoryID") String CategoryID, @Field("SubCategoryID") String SubCategoryID, @Field("ObservationID") String ObservationID, @Field("IncidenceHour") String IncidenceHour, @Field("IncidenceMin") String IncidenceMin, @Field("IncidenceZone") String IncidenceZone, @Field("IncidenceActionTaken") String IncidenceActionTaken,@Field("ObservationName") String ObservationName,@Field("LocationName") String LocationName,@Field("Files") String bytes);

    @GET("Sendmail")
    Call<Void> sendMail(@Query("EmpCode") String EmpCode, @Query("ObservationName") String ObservationName, @Query("Location") String Location, @Query("description") String description, @Query("ActNo") String ActNo, @Query("UnitCode") String UnitCode);

    @FormUrlEncoded
    @POST("UploadFile")
    Call<Void> uploadFile(@Field("Attachment") String Attachment, @Field("Files") String bytes);
}
