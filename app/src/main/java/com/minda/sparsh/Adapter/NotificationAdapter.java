package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.NotificationActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.RetrofitClient;
import com.minda.sparsh.model.NotificationModel;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<NotificationModel> homeData;
    public int total;
    NotificationAdapter.ViewHolder holder;
    SharedPreferences myPref;


    public NotificationAdapter(Context applicationContext, List<NotificationModel> venueData) {
        this.mContext = applicationContext;
        inflater = LayoutInflater.from(mContext);
        this.homeData = venueData;


    }

    public static class ViewHolder {
        public TextView tv_notification;
        public LinearLayout lay_notification;


    }

    @Override
    public int getCount() {
        return homeData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            final NotificationAdapter.ViewHolder holder = new ViewHolder();
            this.holder = holder;
            convertView = inflater.inflate(R.layout.notification_call_view, null);
            holder.tv_notification =  convertView.findViewById(R.id.tv_notification);
            holder.lay_notification =  convertView.findViewById(R.id.lay_notification);
//            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_notification.setText(homeData.get(position).getNotification());
//            holder.tv_time.setText(homeData.get(position).getCreatedOn());
            if (!homeData.get(position).getIsRead()) {

                holder.tv_notification.setTypeface(holder.tv_notification.getTypeface(), Typeface.BOLD);

            }


            holder.lay_notification.setOnClickListener(view -> {
                myPref = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);


                HitMyorder(myPref.getString("Id", ""), homeData.get(position).getPushNotcID().toString(), position);


            });
            convertView.setTag(holder);


            //"\n" + promotionData.get(position).getEventDate());

        }
        return convertView;
    }

    public void HitMyorder(String UserId, String pushid, final int position) {
        if (Utility.isOnline(mContext)) {
            Interface loginInterface = RetrofitClient.getClient().create(Interface.class);
            Call<List<NotificationModel>> loginResponse = loginInterface.ReadPushNot(UserId, pushid, "mda@sPr$rZ#G!!");
            loginResponse.enqueue(new Callback<List<NotificationModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<NotificationModel>> call, @NotNull Response<List<NotificationModel>> response) {

                    List<NotificationModel> responseItem = response.body();
                    response.message();

                    if (!homeData.get(position).getIsRead()) {
                        int count = Integer.parseInt(DashBoardActivity.tv_unread.getText().toString());
                        count = count - 1;
                        DashBoardActivity.tv_unread.setText(String.valueOf(count));
                        homeData.get(position).setIsRead(true);
                        NotificationActivity contaxt1 = (NotificationActivity) mContext;
                        contaxt1.setadapter(homeData);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<NotificationModel>> call, @NotNull Throwable t) {

//                    Toast.makeText(mContext, "Something Wrong", Toast.LENGTH_LONG).show();


                }
            });
        } else
            Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }
} 
