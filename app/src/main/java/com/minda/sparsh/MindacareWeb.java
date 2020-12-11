package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.minda.sparsh.connection.HttpConnection;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MindacareWeb extends AppCompatActivity {

    @BindView(R.id.mindacare_web)
    WebView mindacareWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    String empcode,DOB;
    SharedPreferences myPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mindacareweb);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.mindacare_selfDeclaration));
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empcode = myPref.getString("Id", "Id");
        DOB = myPref.getString("DOB", "");
        long dob = Long.parseLong(DOB.replace("/Date", "").replace("/", "").replace("(", "").replace(")", ""));

        Calendar dob1 = Calendar.getInstance();
        dob1.setTimeInMillis(dob);
        String day,month;
        if(dob1.get(Calendar.DAY_OF_MONTH)<10){
            day = "0"+dob1.get(Calendar.DAY_OF_MONTH);
        }
        else{
            day ="" +dob1.get(Calendar.DAY_OF_MONTH);
        }
        if((dob1.get(Calendar.MONTH)+1)<10){
            month = "0"+(dob1.get(Calendar.MONTH)+1);
        }
        else{
            month =""+(dob1.get(Calendar.MONTH)+1);
        }

        String age =  ""+day+month+dob1.get(Calendar.YEAR);
        mindacareWebView.loadUrl(HttpConnection.mindacareUrl+"EmpCode="+empcode+"&Dob="+age);
        WebSettings webSettings = mindacareWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mindacareWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
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
