package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.NotificationAdapter;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    ListView list_notification;
    java.util.List<String> List=new ArrayList<>();
    SharedPreferences myPref;
    ImageView im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().hide();
        im_back=(ImageView) findViewById(R.id.im_back);
        list_notification=(ListView) findViewById(R.id.list_notification);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        HitMyorder(myPref.getString("Id",""));
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void HitMyorder( String UserId) {
        if (Utility.isOnline(NotificationActivity.this)) {
            Interface loginInterface = RetrofitClient.getClient().create(Interface.class);
            Call<java.util.List<NotificationModel>> loginResponse = loginInterface.GetPushNot( UserId,"mda@sPr$rZ#G!!");
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    response.message();
                    if (responseItem != null) {
                        setadapter(responseItem);




                    }
                    else {}


                }

                @Override
                public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                    Toast.makeText(NotificationActivity.this, "Something Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(NotificationActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }
    public  void setadapter(List<NotificationModel> list)
    {
        NotificationAdapter notificationAdapter=new NotificationAdapter(NotificationActivity.this,list);
        list_notification.setAdapter(notificationAdapter);
    }
}