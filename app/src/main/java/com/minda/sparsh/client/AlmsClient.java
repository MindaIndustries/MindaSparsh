package com.minda.sparsh.client;

import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.ApprovalRequestModel;
import com.minda.sparsh.model.HolidayListModel;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveRegularizationModel;
import com.minda.sparsh.model.LeaveRegularizeModel;
import com.minda.sparsh.model.LeaveRequestModel;
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
    Call<List<LeaveDaysResponse>> getTotalLeaveDays(@Field("LeaveType") String LeaveType, @Field("Fromdate") String Fromdate,@Field("Todate") String Todate, @Field("EmpCode") String EmpCode );

    @FormUrlEncoded
    @POST("service/ApplyLeave")
    Call<List<ApplyLeaveResponse>> applyLeave(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate, @Field("Year") String Year, @Field("LeaveType") String LeaveType, @Field("NoOfDays") String NoOfDays, @Field("ReasonCode") String ReasonCode,@Field("Session") String Session,@Field("AuthPerson") String AuthPerson, @Field("Remark") String Remark, @Field("Place") String Place, @Field("ReportyEmailID") String ReportyEmailID, @Field("ReportyEmpName") String ReportyEmpName, @Field("EmpName") String EmpName, @Field("FileName") String FileName, @Field("Files") String Files);

    @FormUrlEncoded
    @POST("service/ApplyLeaveRegular")
    Call<List<ApplyLeaveResponse>> ApplyLeaveRegular(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate, @Field("Year") String Year, @Field("LeaveType") String LeaveType, @Field("NoOfDays") String NoOfDays, @Field("ReasonCode") String ReasonCode,@Field("Session") String Session,@Field("AuthPerson") String AuthPerson, @Field("Remark") String Remark, @Field("Place") String Place, @Field("ReportyEmailID") String ReportyEmailID, @Field("ReportyEmpName") String ReportyEmpName, @Field("EmpName") String EmpName, @Field("FileName") String FileName, @Field("Files") String Files, @Field("FSHrs") String FSHrs,@Field("SSHrs") String SSHrs);

    @POST("service/GetLeaveTypeRegularize")
    Call<List<LeaveRegularizeModel>> GetLeaveTypeRegularize(@Query("EmpCode") String EmpCode);

    @FormUrlEncoded
    @POST("service/GetMyLeaveRequests")
    Call<List<LeaveRequestModel>> GetMyLeaveRequests(@Field("EmpCode") String EmpCode, @Field("Reqtype") String Reqtype);
    @FormUrlEncoded
    @POST("service/GetMyLeaveRequests")
    Call<List<LeaveRegularizationModel>> GetMyRegularizationRequests(@Field("EmpCode") String EmpCode, @Field("Reqtype") String Reqtype);

    @FormUrlEncoded
    @POST("service/CancelLeave")
    Call<List<ApplyLeaveResponse>> CancelLeave(@Field("EmpCode") String EmpCode, @Field("ReqNo") String ReqNo, @Field("Action") String Action,@Field("Reqtype") String Reqtype );

    @FormUrlEncoded
    @POST("service/GetApprovalRequests")
    Call<List<ApprovalRequestModel>> GetApprovalRequests(@Field("EmpCode") String EmpCode);

    @FormUrlEncoded
    @POST("service/AppRejLeave")
    Call<List<ApplyLeaveResponse>> ApplyRejectLeave(@Field("EmpCode") String EmpCode, @Field("RFormNo") String RFormNo, @Field("Action") String  Action,@Field("LOC") String LOC, @Field("Reqtype") String Reqtype, @Field("strhrs") String strhrs, @Field("ReportyEmpName") String ReportyEmpName);

    @FormUrlEncoded
    @POST("service/GetHolidayList")
    Call<List<HolidayListModel>> GetHolidayList(@Field("EmpCode") String EmpCode, @Field("Year") String Year);

    @FormUrlEncoded
    @POST("service/CreateComOffReq")
    Call<List<ApplyLeaveResponse>> CreateCompOffRequest(@Field("EmpCode") String EmpCode, @Field("Fromdate") String Fromdate, @Field("Todate") String Todate, @Field("Year") String Year, @Field("LeaveType") String LeaveType, @Field("NoOfDays") String NoOfDays,@Field("ReasonCode") String ReasonCode,@Field("Session") String Session,@Field("AuthPerson") String AuthPerson, @Field("Remark") String Remark, @Field("Place") String Place, @Field("ReportyEmailID") String ReportyEmailID, @Field("ReportyEmpName") String ReportyEmpName, @Field("EmpName") String EmpName, @Field("FileName") String FileName, @Field("Files") String Files);

}
