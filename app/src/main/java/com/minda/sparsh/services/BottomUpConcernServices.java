package com.minda.sparsh.services;

import android.content.Context;
import android.util.Log;

import com.minda.sparsh.client.BottomUpClient;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.AutoSuggestModel;
import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.model.SixMModel;
import com.minda.sparsh.util.RetrofitClient2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomUpConcernServices {

    public void getSixM(final OnTaskComplete onTaskComplete) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<List<SixMModel>> call = bottomUpClient.getSixM();
        call.enqueue(new Callback<List<SixMModel>>() {
            @Override
            public void onResponse(Call<List<SixMModel>> call, Response<List<SixMModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<SixMModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });


    }

    public void getUserConcerns(final OnTaskComplete onTaskComplete, String empCode) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<List<BottomUpConcern>> call = bottomUpClient.getConcerns(empCode);
        call.enqueue(new Callback<List<BottomUpConcern>>() {
            @Override
            public void onResponse(Call<List<BottomUpConcern>> call, Response<List<BottomUpConcern>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<BottomUpConcern>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }


    public void saveConcern(final OnTaskComplete onTaskComplete, String RaisedBy, String RaisedOn, String Unit, String Department, String ReferenceNo, String ExistingSystem, String ProposedSystem, String Benefit, String ESFile, String ESFileByte, String PSFile, String PSFileByte, String BenFile, String BenFileByte, String FirstName) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<String> call = bottomUpClient.saveConcern(RaisedBy, RaisedOn, Unit, Department, ReferenceNo, ExistingSystem, ProposedSystem, Benefit, ESFile, ESFileByte, PSFile, PSFileByte, BenFile, BenFileByte, FirstName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });


    }

    public void downloadFile(final OnTaskComplete onTaskComplete, final String fileUrl) {
        BottomUpClient bottomUpClient = RetrofitClient2.downloadService(BottomUpClient.class);
        Call<ResponseBody> call = bottomUpClient.downloadFileWithDynamicUrlSync(fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());

                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });


    }

    private boolean writeResponseBodyToDisk(ResponseBody body, Context context, String fileUrl) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + "" + fileUrl);
            //  File futureStudioIconFile = new File(Environment.getExternalStorageDirectory(),
            //        System.currentTimeMillis() +""+ fileUrl);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void getAssignedConcerns(final OnTaskComplete onTaskComplete, String empcode) {

        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<List<BottomUpConcern>> call = bottomUpClient.getAssignedConcerns(empcode);
        call.enqueue(new Callback<List<BottomUpConcern>>() {
            @Override
            public void onResponse(Call<List<BottomUpConcern>> call, Response<List<BottomUpConcern>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<List<BottomUpConcern>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });


    }

    public void assignConcern(final OnTaskComplete onTaskComplete, String concernNo, String empCode, String targetDate) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<String> call = bottomUpClient.assignAConcern(concernNo, empCode, targetDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });
    }

    public void completeConcern(final OnTaskComplete onTaskComplete, String concernNo) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<String> call = bottomUpClient.markCompleteConcern(concernNo);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);
            }
        });

    }

    public void getAutoSuggestion(final OnTaskComplete onTaskComplete, String prefixText) {

        BottomUpClient bottomUpClient = RetrofitClient2.createServiceBottomUponcern(BottomUpClient.class);
        Call<List<AutoSuggestModel>> call = bottomUpClient.getAutoSuggestion(prefixText);
        call.enqueue(new Callback<List<AutoSuggestModel>>() {
            @Override
            public void onResponse(Call<List<AutoSuggestModel>> call, Response<List<AutoSuggestModel>> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(Call<List<AutoSuggestModel>> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });

    }


    public void submitSuggestion(final OnTaskComplete onTaskComplete, String suggestion, String empCode, String CostAmount, String other, String FileName, String FileType, String FileByte) {
        BottomUpClient bottomUpClient = RetrofitClient2.createServiceSuggestionBox(BottomUpClient.class);
        Call<String> call = bottomUpClient.submitSuggestion(suggestion, empCode, CostAmount, other, FileName, FileType, FileByte);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                CarotResponse carotResponse = new CarotResponse();
                carotResponse.setStatuscode(response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    carotResponse.setData(response.body());
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CarotResponse carotResponse = new CarotResponse();
                if (t instanceof IOException) {
                    carotResponse.setMessage("Please hold on a moment, the internet connectivity seems to be slow");
                }
                onTaskComplete.onTaskComplte(carotResponse);

            }
        });
    }
}


