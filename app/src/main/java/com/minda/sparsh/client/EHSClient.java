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
import retrofit2.http.Body;
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

    @GET("SaveEHS")
    Call<String> submitEHS(@Query("EmpCode") String EmpCode, @Query("ActNo") String ActNo, @Query("ActDate") String ActDate, @Query("HOD") String HOD, @Query("UnitSafetyOfficer") String UnitSafetyOfficer, @Query("UnitCode") String UnitCode, @Query("Description") String Description, @Query("Attachment") String Attachment, @Query("AttachmentType") String AttachmentType, @Query("LocationID") String LocationID, @Query("CategoryID") String CategoryID, @Query("SubCategoryID") String SubCategoryID, @Query("ObservationID") String ObservationID, @Query("IncidenceHour") String IncidenceHour,@Query("IncidenceMin") String IncidenceMin, @Query("IncidenceZone") String IncidenceZone, @Query("IncidenceActionTaken") String IncidenceActionTaken, @Query("ObservationName") String ObservationName, @Query("LocationName") String LocationName);

    @GET("UpdateEhs")
    Call<String> updateEhs(@Query("ActID") String ActID, @Query("EmpCode") String EmpCode, @Query("ActNo") String ActNo, @Query("ActDate") String ActDate, @Query("HOD") String HOD, @Query("UnitSafetyOfficer") String UnitSafetyOfficer, @Query("UnitCode") String UnitCode, @Query("Description") String Description, @Query("Attachment") String Attachment, @Query("AttachmentType") String AttachmentType, @Query("LocationID") String LocationID, @Query("CategoryID") String CategoryID, @Query("SubCategoryID") String SubCategoryID, @Query("ObservationID") String ObservationID,@Query("IncidenceHour") String IncidenceHour,@Query("IncidenceMin") String IncidenceMin, @Query("IncidenceZone") String IncidenceZone, @Query("IncidenceActionTaken") String IncidenceActionTaken);

    @GET("Sendmail")
    Call<Void> sendMail(@Query("EmpCode") String EmpCode,@Query("ObservationName") String ObservationName,@Query("Location") String Location,@Query("description") String description, @Query("ActNo") String ActNo, @Query("UnitCode") String UnitCode );

    @FormUrlEncoded
    @POST("UploadFile")
    Call<Void> uploadFile(@Field("Attachment") String Attachment, @Field("Files") String bytes);
}
