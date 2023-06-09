package com.minda.sparsh.services;

import com.minda.sparsh.client.EHSClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSCategoryModel;
import com.minda.sparsh.model.EHSIdentifiedLocationModel;
import com.minda.sparsh.model.EHSObsModel;
import com.minda.sparsh.model.EHSObservationModel;
import com.minda.sparsh.model.EHSSubCategoryModel;
import com.minda.sparsh.model.EHSUnitModel;
import com.minda.sparsh.model.SafetyOfficerModel;
import com.minda.sparsh.util.RetrofitClient2;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EHSServices {

    public void getUnits(final OnTaskComplete onTaskComplete, String unitcode) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSUnitModel>> call = ehsClient.getUnits(unitcode);
        call.enqueue(new Callback<List<EHSUnitModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSUnitModel>> call, @NotNull Response<List<EHSUnitModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSUnitModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });


    }

    public void getSafetyOfficers(final OnTaskComplete onTaskComplete, String unitcode) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<SafetyOfficerModel>> call = ehsClient.getSafetyOfficers(unitcode);
        call.enqueue(new Callback<List<SafetyOfficerModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<SafetyOfficerModel>> call, @NotNull Response<List<SafetyOfficerModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<SafetyOfficerModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getObservationTypes(final OnTaskComplete onTaskComplete) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSObservationModel>> call = ehsClient.getObservationTypes();
        call.enqueue(new Callback<List<EHSObservationModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSObservationModel>> call, @NotNull Response<List<EHSObservationModel>>
                    response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSObservationModel>>
                                          call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getIdentifiedLocations(final OnTaskComplete onTaskComplete) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSIdentifiedLocationModel>> call = ehsClient.getIdentifiedLocations();
        call.enqueue(new Callback<List<EHSIdentifiedLocationModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSIdentifiedLocationModel>> call, @NotNull Response<List<EHSIdentifiedLocationModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSIdentifiedLocationModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getCategories(final OnTaskComplete onTaskComplete, String ObservationID) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSCategoryModel>> call = ehsClient.getCategories(ObservationID);
        call.enqueue(new Callback<List<EHSCategoryModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSCategoryModel>> call, @NotNull Response<List<EHSCategoryModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSCategoryModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getSubCategories(final OnTaskComplete onTaskComplete, String catId) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSSubCategoryModel>> call = ehsClient.getSubCategories(catId);
        call.enqueue(new Callback<List<EHSSubCategoryModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSSubCategoryModel>> call, @NotNull Response<List<EHSSubCategoryModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSSubCategoryModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void getIdentifiedObservations(final OnTaskComplete onTaskComplete, String empCode) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<List<EHSObsModel>> call = ehsClient.getObservations(empCode);
        call.enqueue(new Callback<List<EHSObsModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<EHSObsModel>> call, @NotNull Response<List<EHSObsModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<List<EHSObsModel>> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void sendmail(final OnTaskComplete onTaskComplete, String EmpCode, String ObservationName, String Location, String description, String ActNo, String UnitCode) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<Void> call = ehsClient.sendMail(EmpCode, ObservationName, Location, description, ActNo, UnitCode);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }

    public void saveEHS(final OnTaskComplete onTaskComplete, final String EmpCode, String ActNo, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceHour, String IncidenceMin, String IncidenceZone, String IncidenceActionTaken, final String ObservationName, final String LocationName, String filebytes) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<String> call = ehsClient.submitEHS(EmpCode, ActNo, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceHour, IncidenceMin, IncidenceZone, IncidenceActionTaken, ObservationName, LocationName,filebytes);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void update(final OnTaskComplete onTaskComplete, String ActID, String EmpCode, String ActNo, String ActDate, String HOD, String UnitSafetyOfficer, String UnitCode, String Description, String Attachment, String AttachmentType, String LocationID, String CategoryID, String SubCategoryID, String ObservationID, String IncidenceHour, String IncidenceMin, String IncidenceZone, String IncidenceActionTaken,String ObservationName,  String LocationName,  String bytes) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<String> call = ehsClient.updateEhs(ActID, EmpCode, ActNo, ActDate, HOD, UnitSafetyOfficer, UnitCode, Description, Attachment, AttachmentType, LocationID, CategoryID, SubCategoryID, ObservationID, IncidenceHour, IncidenceMin, IncidenceZone, IncidenceActionTaken,ObservationName,LocationName,bytes);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void uploadFile(final OnTaskComplete onTaskComplete, String attachment, String bytes) {
        EHSClient ehsClient = RetrofitClient2.createServiceEHS(EHSClient.class);
        Call<Void> call = ehsClient.uploadFile(attachment, bytes);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

}
