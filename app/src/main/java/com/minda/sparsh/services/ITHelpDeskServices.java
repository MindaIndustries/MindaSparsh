package com.minda.sparsh.services;

import com.minda.sparsh.client.IThelpdeskClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AssetLocResponse;
import com.minda.sparsh.model.BindGroupResponse;
import com.minda.sparsh.model.GroupAssigneeResponse;
import com.minda.sparsh.model.MyTicketsResponse;
import com.minda.sparsh.model.TicketHistoryResponse;
import com.minda.sparsh.model.TicketSubmitResponse;
import com.minda.sparsh.util.RetrofitClient2;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ITHelpDeskServices {

    public void getAssetLoc(OnTaskComplete onTaskComplete, String EmpCode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getAssetLoc(EmpCode);
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getTicketType(OnTaskComplete onTaskComplete) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getTicketType();
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getTicketGroup(OnTaskComplete onTaskComplete, String ticketType, String unitCode, String subcat, String subcat2, String subcat3) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<BindGroupResponse>> call = iThelpdeskClient.getBindGroup(subcat, subcat2, subcat3, ticketType, unitCode);
        call.enqueue(new Callback<List<BindGroupResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<BindGroupResponse>> call, @NotNull Response<List<BindGroupResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<BindGroupResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getGroupAssignee(OnTaskComplete onTaskComplete, String ticketType, String unitcode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<GroupAssigneeResponse>> call = iThelpdeskClient.getGroupAssignee(ticketType, unitcode);
        call.enqueue(new Callback<List<GroupAssigneeResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<GroupAssigneeResponse>> call, @NotNull Response<List<GroupAssigneeResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<GroupAssigneeResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getAutoName(OnTaskComplete onTaskComplete, String prefixText) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<String>> call = iThelpdeskClient.getAutoName(prefixText);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NotNull Call<List<String>> call, @NotNull Response<List<String>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<String>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }


    public void submitRequest(OnTaskComplete onTaskComplete, String location, String tickettypeId, String subcat, String subcat2, String subcat3, String ticketGroupId, String assignGroup, String AssigneGroupCode, String DefaultAssigne, String reportedby, String priority, String attachedfiles, String attFileBytes, String description, String cc, String empcode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<TicketSubmitResponse> call = iThelpdeskClient.submitRequest(location, tickettypeId, subcat, subcat2, subcat3, ticketGroupId, assignGroup, AssigneGroupCode, DefaultAssigne, reportedby, priority, attachedfiles, attFileBytes, description, cc, empcode);
        call.enqueue(new Callback<TicketSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<TicketSubmitResponse> call, @NotNull Response<TicketSubmitResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(@NotNull Call<TicketSubmitResponse> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getMyTickets(OnTaskComplete onTaskComplete, String EmpCode, String Location, String TicketTypeId, String Priority, String TicketGroupID, String StatusID, String SubCat, String SubCat2, String SubCat3, String ReportedDate, String CloserDate) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<MyTicketsResponse>> call = iThelpdeskClient.getMyTickets(EmpCode, Location, TicketTypeId, Priority, TicketGroupID, StatusID, SubCat, SubCat2, SubCat3, ReportedDate, CloserDate);
        call.enqueue(new Callback<List<MyTicketsResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<MyTicketsResponse>> call, @NotNull Response<List<MyTicketsResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<MyTicketsResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }


    public void getMyTaskTickets(OnTaskComplete onTaskComplete, String EmpCode, String Location, String TicketTypeId, String Priority, String TicketGroupID, String StatusID, String SubCat, String SubCat2, String SubCat3, String ReportedDate, String CloserDate) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<MyTicketsResponse>> call = iThelpdeskClient.getMyTaskTickets(EmpCode, Location, TicketTypeId, Priority, TicketGroupID, StatusID, SubCat, SubCat2, SubCat3, ReportedDate, CloserDate);
        call.enqueue(new Callback<List<MyTicketsResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<MyTicketsResponse>> call, @NotNull Response<List<MyTicketsResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<MyTicketsResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }


    public void getPriority(OnTaskComplete onTaskComplete) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getPriority();
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getReportedBy(OnTaskComplete onTaskComplete, String EmpCode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getReportedBy(EmpCode);
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>>
                    response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getSubCat(OnTaskComplete onTaskComplete, String SubCat, String TicketType, String UnitCode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getSubCat(SubCat, TicketType, UnitCode);
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getSubCat2(OnTaskComplete onTaskComplete, String SubCat, String SubCat2, String TicketType, String UnitCode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getSubCat2(SubCat, SubCat2, TicketType, UnitCode);
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getSubCat3(OnTaskComplete onTaskComplete, String SubCat2, String SubCat3, String TicketType, String UnitCode) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<AssetLocResponse>> call = iThelpdeskClient.getSubCat3(SubCat2, SubCat3, TicketType, UnitCode);
        call.enqueue(new Callback<List<AssetLocResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<AssetLocResponse>> call, @NotNull Response<List<AssetLocResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<AssetLocResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void updateRemark(OnTaskComplete onTaskComplete, String Remarks, String EmpCode, String TicketNo) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<TicketSubmitResponse> call = iThelpdeskClient.updateRemark(Remarks, EmpCode, TicketNo);
        call.enqueue(new Callback<TicketSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<TicketSubmitResponse> call, @NotNull Response<TicketSubmitResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<TicketSubmitResponse> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getTicketHistory(OnTaskComplete onTaskComplete, String ticketNo) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<List<TicketHistoryResponse>> call = iThelpdeskClient.getTicketHistory(ticketNo);
        call.enqueue(new Callback<List<TicketHistoryResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<TicketHistoryResponse>> call, @NotNull Response<List<TicketHistoryResponse>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<TicketHistoryResponse>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }


    public void updateMyTaskTicket(OnTaskComplete onTaskComplete, String TicketNo, String EmpCode, String Remark, String Status, String Location, String TicketTypeID, String SubCat, String SubCat2, String SubCat3, String TicketGroupID, String AssigneGroup, String AssigneGroupCode, String DefaultAssigne, String ReportedBy, String Priority, String AttachedFiles, String AttFileBytes, String Description, String CC) {
        IThelpdeskClient iThelpdeskClient = RetrofitClient2.createServiceitHelpDesk(IThelpdeskClient.class);
        Call<TicketSubmitResponse> call = iThelpdeskClient.updateMyTaskTicket(TicketNo, EmpCode, Remark, Status, Location, TicketTypeID, SubCat, SubCat2, SubCat3, TicketGroupID, AssigneGroup, AssigneGroupCode, DefaultAssigne, ReportedBy, Priority, AttachedFiles, AttFileBytes, Description, CC);
        call.enqueue(new Callback<TicketSubmitResponse>() {
            @Override
            public void onResponse(@NotNull Call<TicketSubmitResponse> call, @NotNull Response<TicketSubmitResponse> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(@NotNull Call<TicketSubmitResponse> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }
}
