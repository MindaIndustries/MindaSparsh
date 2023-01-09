package com.minda.sparsh.client;

import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveTypeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AlmsClient {
    @FormUrlEncoded
    @POST("service/GetConsolidateReport")
    Call<List<AlmsReportModel>> getConsolidateReport(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate);

    @FormUrlEncoded
    @POST("service/GetLeavebalance")
    Call<List<LeaveBalanceModel>> GetLeavebalance(@Field("EmpCode") String EmpCode, @Field("Year") String Year, @Field("LeaveType") String LeaveType);

    @POST("service/GetLeaveType")
    Call<List<LeaveTypeModel>> getLeaveTypes(@Query("EmpCode")String EmpCode);
}
