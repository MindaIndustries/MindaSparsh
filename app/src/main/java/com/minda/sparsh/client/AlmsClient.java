package com.minda.sparsh.client;

import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveTypeModel;
import com.minda.sparsh.model.LeaveValidationResponse;

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

    @FormUrlEncoded
    @POST("service/CheckLeaveApplyValidation")
    Call<List<LeaveValidationResponse>> checkLeaveValidation(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate);

    @FormUrlEncoded
    @POST("service/GetTotalLeaveDays")
    Call<List<LeaveDaysResponse>> getTotalLeaveDays(@Field("LeaveType") String LeaveType, @Field("Fromdate") String Fromdate,@Field("Todate") String Todate );

    @FormUrlEncoded
    @POST("service/ApplyLeave")
    Call<List<ApplyLeaveResponse>> applyLeave(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate, @Field("Year") String Year, @Field("LeaveType") String LeaveType, @Field("NoOfDays") String NoOfDays, @Field("ReasonCode") String ReasonCode,@Field("Session") String Session,@Field("AuthPerson") String AuthPerson, @Field("Remark") String Remark, @Field("Place") String Place, @Field("ReportyEmailID") String ReportyEmailID, @Field("ReportyEmpName") String ReportyEmpName, @Field("EmpName") String EmpName, @Field("FileName") String FileName, @Field("Files") String Files);
}
