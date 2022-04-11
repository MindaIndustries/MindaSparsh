package com.minda.sparsh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.NotificationAdapter;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    ListView list_notification;
    SharedPreferences myPref;
    ImageView im_back;
    Toolbar toolbar;
    TextView title,no_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        //   getSupportActionBar().hide();

        toolbar =  findViewById(R.id.toolbar);
        title =  findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.notifications));

        im_back =  findViewById(R.id.im_back);
        list_notification =  findViewById(R.id.list_notification);
        no_list = findViewById(R.id.no_list);
        myPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        HitMyorder(myPref.getString("Id", ""));
        im_back.setOnClickListener(view -> finish());
    }

    public void HitMyorder(String UserId) {
        if (Utility.isOnline(NotificationActivity.this)) {
            Interface loginInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<java.util.List<NotificationModel>> loginResponse = loginInterface.GetPushNot(UserId, RetrofitClient2.CKEY);
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NotificationModel>> call, @NotNull Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    if (responseItem != null && responseItem.size() > 0) {
                        setadapter(responseItem);
                        no_list.setVisibility(View.GONE);
                    }
                    else{
                        no_list.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onFailure(@NotNull Call<List<NotificationModel>> call, @NotNull Throwable t) {
                    Toast.makeText(NotificationActivity.this, "Oops! Something went Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(NotificationActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    public void setadapter(List<NotificationModel> list) {
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, list);
        list_notification.setAdapter(notificationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
