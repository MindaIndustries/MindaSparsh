package com.minda.sparsh.client;

import com.minda.sparsh.model.AssetLocResponse;
import com.minda.sparsh.model.BindGroupResponse;
import com.minda.sparsh.model.GroupAssigneeResponse;
import com.minda.sparsh.model.MyTicketsResponse;
import com.minda.sparsh.model.TicketHistoryResponse;
import com.minda.sparsh.model.TicketSubmitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IThelpdeskClient {

    @GET("GetAssetLoc")
    Call<List<AssetLocResponse>> getAssetLoc(@Query("EmpCode") String EmpCode);

    @GET("GetTicketType")
    Call<List<AssetLocResponse>> getTicketType();

    @GET("BindSubCat")
    Call<List<AssetLocResponse>> getSubCat(@Query("SubCat") String SubCat, @Query("TicketType") String TicketType, @Query("UnitCode") String UnitCode);

    @GET("BindSubCat2")
    Call<List<AssetLocResponse>> getSubCat2(@Query("SubCat") String SubCat,@Query("SubCat2") String SubCat2, @Query("TicketType") String TicketType, @Query("UnitCode") String UnitCode);

    @GET("BindSubCat3")
    Call<List<AssetLocResponse>> getSubCat3(@Query("SubCat2") String SubCat2,@Query("SubCat3") String SubCat3, @Query("TicketType") String TicketType, @Query("UnitCode") String UnitCode);

    @GET("GetReportedBy")
    Call<List<AssetLocResponse>> getReportedBy(@Query("EmpCode") String EmpCode);

    @GET("BindGroup")
    Call<List<BindGroupResponse>> getBindGroup(@Query("SubCat") String SubCat,@Query("SubCat2") String SubCat2,@Query("SubCat3") String SubCat3,@Query("TicketType")String TicketType,@Query("UnitCode") String UnitCode);

    @GET("GetGroupAssigne")
    Call<List<GroupAssigneeResponse>> getGroupAssignee(@Query("TicketType") String TicketType, @Query("UnitCode") String UnitCode);

    @GET("GetAutoNameITHelpDesk")
    Call<List<String>> getAutoName(@Query("prefixText") String prefixText);

    @GET("GetMyTickets")
    Call<List<MyTicketsResponse>> getMyTickets(@Query("EmpCode") String EmpCode,@Query("Location") String Location, @Query("TicketTypeID") String TicketTypeID,@Query("Priority") String Priority, @Query("TicketGroupID") String TicketGroupID, @Query("StatusID") String StatusID,@Query("SubCat") String SubCat,@Query("SubCat2")String SubCat2,@Query("SubCat3")String SubCat3, @Query("ReportedDate")String ReportedDate,@Query("CloserDate") String CloserDate );

    @GET("GetMyTaskTickets")
    Call<List<MyTicketsResponse>> getMyTaskTickets(@Query("EmpCode") String EmpCode,@Query("Location") String Location, @Query("TicketTypeID") String TicketTypeID,@Query("Priority") String Priority, @Query("TicketGroupID") String TicketGroupID, @Query("StatusID") String StatusID,@Query("SubCat") String SubCat,@Query("SubCat2")String SubCat2,@Query("SubCat3")String SubCat3, @Query("ReportedDate")String ReportedDate,@Query("CloserDate") String CloserDate);

    @FormUrlEncoded
    @POST("SubmitRequest")
    Call<TicketSubmitResponse> submitRequest(@Field("Location") String Location, @Field("TicketTypeID") String TicketTypeID, @Field("SubCat") String SubCat, @Field("SubCat2") String SubCat2, @Field("SubCat3") String SubCat3, @Field("TicketGroupID") String TicketGroupID,@Field("AssigneGroup") String AssigneGroup, @Field("AssigneGroupCode") String AssigneGroupCode,@Field("DefaultAssigne") String DefaultAssigne,@Field("ReportedBy") String ReportedBy, @Field("Priority") String Priority, @Field("AttachedFiles") String AttachedFiles,@Field("AttFileBytes")String AttFileBytes, @Field("Description") String Description, @Field("CC") String CC, @Field("EmpCode") String EmpCode);

    @GET("GetPriority")
    Call<List<AssetLocResponse>> getPriority();

    @GET("UpdateRemark")
    Call<TicketSubmitResponse> updateRemark(@Query("Remarks") String Remarks, @Query("EmpCode") String EmpCode, @Query("TicketNo") String TicketNo);

    @GET("GettickHis")
    Call<List<TicketHistoryResponse>> getTicketHistory(@Query("TicketNo") String TicketNo);

    @FormUrlEncoded
    @POST("MyTaskTicketUpdate")
    Call<TicketSubmitResponse> updateMyTaskTicket(@Field("TicketNo") String TicketNo, @Field("EmpCode") String EmpCode, @Field("Remark") String Remark, @Field("Status")String Status, @Field("Location") String Location, @Field("TicketTypeID") String TicketTypeID,@Field("SubCat") String SubCat, @Field("SubCat2") String SubCat2, @Field("SubCat3") String SubCat3, @Field("TicketGroupID") String TicketGroupID, @Field("AssigneGroup") String AssigneGroup, @Field("AssigneGroupCode") String AssigneGroupCode, @Field("DefaultAssigne") String DefaultAssigne, @Field("ReportedBy") String ReportedBy, @Field("Priority") String Priority, @Field("AttachedFiles") String AttachedFiles,@Field("AttFileBytes") String AttFileBytes, @Field("Description") String Description, @Field("CC") String CC);


}
