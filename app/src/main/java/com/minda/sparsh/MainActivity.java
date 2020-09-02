package com.minda.sparsh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.minda.sparsh.model.AddAbnormality_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FlipperAdapter adapter;
    private ArrayList<String> stringArrayList;
    ImageView im_back;
    WebView wv_jagriti;
    private ProgressDialog progress = null;
    String pdfType = "";
    Toolbar toolbar;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        im_back = (ImageView) findViewById(R.id.im_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        Intent intent = getIntent();
        pdfType = intent.getStringExtra("pdfType");
        try {
            tv_title.setText(pdfType);
            title.setText(pdfType);
            if (pdfType.equalsIgnoreCase("Jagriti")) {
                hitGetPdfApi("Jagriti");
            } else if (pdfType.equalsIgnoreCase("Samwad")) {
                hitGetPdfApi("Samwad");
            } else if (pdfType.equalsIgnoreCase("Engineering")) {
                hitGetPdfApi("Engineering");
            } else {
                hitGetPdfApi("Manufacturing");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void readDataFromAssets() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("loremipsum.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                stringArrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showProgress(boolean b) {
        try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hitGetPdfApi(String type) {
        if (Utility.isOnline(MainActivity.this)) {
            showProgress(true);
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<AddAbnormality_Model>> response = anInterface.GetPdf(type, RetrofitClient2.CKEY);
            response.enqueue(new Callback<List<AddAbnormality_Model>>() {
                @Override
                public void onResponse(Call<List<AddAbnormality_Model>> call, Response<List<AddAbnormality_Model>> response) {
                    showProgress(false);
                    List<AddAbnormality_Model> pdfResponse = response.body();
                    if (pdfResponse != null && pdfResponse.size() > 0 && !pdfResponse.get(0).getColumn1().equalsIgnoreCase("")) {
                        String url = pdfResponse.get(0).getColumn1();
                        webView(url);
                    } else {
                        Toast.makeText(MainActivity.this, "Coming Soon...", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<AddAbnormality_Model>> call, Throwable t) {
                    showProgress(false);
                }
            });
        } else
            Toast.makeText(MainActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    void webView(String url) {
        wv_jagriti = (WebView) findViewById(R.id.wv_jagriti);
        wv_jagriti.getSettings().setJavaScriptEnabled(true);
        wv_jagriti.getSettings().setLoadWithOverviewMode(true);
        wv_jagriti.getSettings().setUseWideViewPort(true);
        wv_jagriti.getSettings().setBuiltInZoomControls(true);
        showProgress(true);
        wv_jagriti.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                showProgress(false);
            }
        });
        if (pdfType.equalsIgnoreCase("Jagriti")) {
            wv_jagriti.loadUrl(url);
        } else {
            wv_jagriti.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
