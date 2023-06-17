package com.minda.sparsh.services;

import com.minda.sparsh.client.AlmsClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AlmsReportModel;
import com.minda.sparsh.model.ApplyLeaveResponse;
import com.minda.sparsh.model.ApprovalRequestModel;
import com.minda.sparsh.model.CheckCompOffValidation;
import com.minda.sparsh.model.HolidayListModel;
import com.minda.sparsh.model.LeaveBalanceModel;
import com.minda.sparsh.model.LeaveDaysResponse;
import com.minda.sparsh.model.LeaveRegularizationModel;
import com.minda.sparsh.model.LeaveRegularizeModel;
import com.minda.sparsh.model.LeaveRequestModel;
import com.minda.sparsh.model.LeaveTypeModel;
import com.minda.sparsh.model.LeaveValidationResponse;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AlmsServices {

    public void getConsolidatedReport(OnTaskComplete onTaskComplete, String empcode, String fromtDate, String toDate){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<AlmsReportModel>> call = almsClient.getConsolidateReport(empcode,fromtDate, toDate);
        call.enqueue(new Callback<List<AlmsReportModel>>() {
            @Override
            public void onResponse(Call<List<AlmsReportModel>> call, Response<List<AlmsReportModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<AlmsReportModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    
    public void getLeaveBalance(OnTaskComplete onTaskComplete, String empcode, String year, String leaveType){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveBalanceModel>> call = almsClient.GetLeavebalance(empcode,year,leaveType);
        call.enqueue(new Callback<List<LeaveBalanceModel>>() {
            @Override
            public void onResponse(Call<List<LeaveBalanceModel>> call, Response<List<LeaveBalanceModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveBalanceModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getLeaveTypes(OnTaskComplete onTaskComplete,String empcode){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveTypeModel>> call = almsClient.getLeaveTypes(empcode);
        call.enqueue(new Callback<List<LeaveTypeModel>>() {
            @Override
            public void onResponse(Call<List<LeaveTypeModel>> call, Response<List<LeaveTypeModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveTypeModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }
    public void checkLeaveValidation(OnTaskComplete onTaskComplete,String empcode, String fromDate, String toDate, String Leavetype){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveValidationResponse>> call = almsClient.checkLeaveValidation(empcode,fromDate,toDate,Leavetype);
        call.enqueue(new Callback<List<LeaveValidationResponse>>() {
            @Override
            public void onResponse(Call<List<LeaveValidationResponse>> call, Response<List<LeaveValidationResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveValidationResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void checkLeaveValidationCompOff(OnTaskComplete onTaskComplete,String empcode, String fromDate, String toDate, String Leavetype){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<CheckCompOffValidation>> call = almsClient.checkLeaveValidationCompOff(empcode,fromDate,toDate,Leavetype);
        call.enqueue(new Callback<List<CheckCompOffValidation>>() {
            @Override
            public void onResponse(Call<List<CheckCompOffValidation>> call, Response<List<CheckCompOffValidation>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<CheckCompOffValidation>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }


    public void getTotalLeaveDays(OnTaskComplete onTaskComplete, String LeaveType, String Fromdate, String Todate, String EmpCode){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveDaysResponse>> call = almsClient.getTotalLeaveDays(LeaveType,Fromdate,Todate, EmpCode);
        call.enqueue(new Callback<List<LeaveDaysResponse>>() {
            @Override
            public void onResponse(Call<List<LeaveDaysResponse>> call, Response<List<LeaveDaysResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveDaysResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void applyLeave(OnTaskComplete onTaskComplete, String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files, String FSHrs, String SSHrs){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApplyLeaveResponse>> call = almsClient.applyLeave(Empcode,Fromdate,Todate,Year, LeaveType,NoOfDays,ReasonCode,Session,AuthPerson,Remark,Place,ReportyEmailID,ReportyEmpName,EmpName, FileName,Files, FSHrs,SSHrs);
        call.enqueue(new Callback<List<ApplyLeaveResponse>>() {
            @Override
            public void onResponse(Call<List<ApplyLeaveResponse>> call, Response<List<ApplyLeaveResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApplyLeaveResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void applyLeaveRegular(OnTaskComplete onTaskComplete, String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files, String FSHrs, String SSHrs){

        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApplyLeaveResponse>> call = almsClient.ApplyLeaveRegular(Empcode,Fromdate,Todate,Year, LeaveType,NoOfDays,ReasonCode,Session,AuthPerson,Remark,Place,ReportyEmailID,ReportyEmpName,EmpName, FileName,Files,FSHrs,SSHrs);
        call.enqueue(new Callback<List<ApplyLeaveResponse>>() {
            @Override
            public void onResponse(Call<List<ApplyLeaveResponse>> call, Response<List<ApplyLeaveResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApplyLeaveResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getLeaveTypeRegularize(OnTaskComplete onTaskComplete, String Empcode){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveRegularizeModel>> call = almsClient.GetLeaveTypeRegularize(Empcode);
        call.enqueue(new Callback<List<LeaveRegularizeModel>>() {
            @Override
            public void onResponse(Call<List<LeaveRegularizeModel>> call, Response<List<LeaveRegularizeModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveRegularizeModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getMyLeaveRequests(OnTaskComplete onTaskComplete,String empcode, String Reqtype){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveRequestModel>> call = almsClient.GetMyLeaveRequests(empcode, Reqtype);
        call.enqueue(new Callback<List<LeaveRequestModel>>() {
            @Override
            public void onResponse(Call<List<LeaveRequestModel>> call, Response<List<LeaveRequestModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveRequestModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
    public void getMyRegularizeRequests(OnTaskComplete onTaskComplete,String empcode, String Reqtype){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<LeaveRegularizationModel>> call = almsClient.GetMyRegularizationRequests(empcode, Reqtype);
        call.enqueue(new Callback<List<LeaveRegularizationModel>>() {
            @Override
            public void onResponse(Call<List<LeaveRegularizationModel>> call, Response<List<LeaveRegularizationModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<LeaveRegularizationModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }


    public void cancelLeave(OnTaskComplete onTaskComplete, String empcode, String reqno, String Action, String ReqType){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApplyLeaveResponse>> call = almsClient.CancelLeave(empcode,reqno, Action, ReqType);
        call.enqueue(new Callback<List<ApplyLeaveResponse>>() {
            @Override
            public void onResponse(Call<List<ApplyLeaveResponse>> call, Response<List<ApplyLeaveResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApplyLeaveResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getApprovalRequests(OnTaskComplete onTaskComplete,String empcode){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApprovalRequestModel>> call = almsClient.GetApprovalRequests(empcode);
        call.enqueue(new Callback<List<ApprovalRequestModel>>() {
            @Override
            public void onResponse(Call<List<ApprovalRequestModel>> call, Response<List<ApprovalRequestModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApprovalRequestModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }
    public void applyRejectLeaves(OnTaskComplete onTaskComplete, String Empcode,String RFormNo,String Action, String LOC, String Reqtype, String strhrs, String ReportyEmpName){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApplyLeaveResponse>> call = almsClient.ApplyRejectLeave(Empcode,RFormNo,Action,LOC,Reqtype,strhrs,ReportyEmpName);
        call.enqueue(new Callback<List<ApplyLeaveResponse>>() {
            @Override
            public void onResponse(Call<List<ApplyLeaveResponse>> call, Response<List<ApplyLeaveResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApplyLeaveResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void GetHolidayList(OnTaskComplete onTaskComplete, String EmpCode, String Year){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<HolidayListModel>> call = almsClient.GetHolidayList(EmpCode,Year);
        call.enqueue(new Callback<List<HolidayListModel>>() {
            @Override
            public void onResponse(Call<List<HolidayListModel>> call, Response<List<HolidayListModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<HolidayListModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }
    public void createCompOff(OnTaskComplete onTaskComplete,String Empcode, String Fromdate, String Todate, String Year, String LeaveType, String NoOfDays, String ReasonCode, String Session, String AuthPerson, String Remark, String Place, String ReportyEmailID, String ReportyEmpName, String EmpName, String FileName, String Files){
        AlmsClient almsClient = RetrofitClient2.getClientScanQR(AlmsClient.class);
        Call<List<ApplyLeaveResponse>> call = almsClient.CreateCompOffRequest(Empcode,Fromdate,Todate,Year, LeaveType,NoOfDays,ReasonCode,Session,AuthPerson,Remark,Place,ReportyEmailID,ReportyEmpName,EmpName, FileName,Files);
        call.enqueue(new Callback<List<ApplyLeaveResponse>>() {
            @Override
            public void onResponse(Call<List<ApplyLeaveResponse>> call, Response<List<ApplyLeaveResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<ApplyLeaveResponse>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

}
