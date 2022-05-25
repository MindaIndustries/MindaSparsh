package com.minda.sparsh.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minda.sparsh.R;
import com.minda.sparsh.model.MeetingRoomDetailData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MeetingRoomDetailAdapter extends ArrayAdapter<MeetingRoomDetailData.MeetingRoomDetailDataModel> {
    ArrayList<MeetingRoomDetailData.MeetingRoomDetailDataModel> list;
    Context mContext;
    public MeetingRoomDetailAdapter(Context context, ArrayList<MeetingRoomDetailData.MeetingRoomDetailDataModel> list) {
        super(context, 0,list );
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.meeting_slot_row, parent, false);
        }
        TextView time = listitemView.findViewById(R.id.time);
        TextView booking_status = listitemView.findViewById(R.id.booking_status);
        RelativeLayout slot_row = listitemView.findViewById(R.id.slot_row);
        time.setText(list.get(position).getTimeStart()+" - "+list.get(position).getTimeEnd());
        booking_status.setText(list.get(position).getBkngStatus());
        if(list.get(position).getBkngStatus().equals("Expired")){
            slot_row.setBackgroundColor(Color.parseColor("#c2c2c2"));
        }
        else if(list.get(position).getBkngStatus().equals("Ready to Book")){
            slot_row.setBackgroundColor(Color.parseColor("#49ba8e"));
        }
        else if(list.get(position).getBkngStatus().equals("Reserved")){
            slot_row.setBackgroundColor(Color.parseColor("#0089c8"));
        }
        else if(list.get(position).getBkngStatus().equals("Booked")){
            slot_row.setBackgroundColor(Color.parseColor("#d91f17"));
        }
        else if(list.get(position).getBkngStatus().equals("Meeting Started")){
            slot_row.setBackgroundColor(Color.parseColor("#f9c13c"));
        }
        else if(list.get(position).getBkngStatus().equals("Release")){
            slot_row.setBackgroundColor(Color.parseColor("#f9c13c"));
        }
        else if(list.get(position).getBkngStatus().equals("Meeting Over")){
            slot_row.setBackgroundColor(Color.parseColor("#f9c13c"));
        }
        return listitemView;
    }
}
