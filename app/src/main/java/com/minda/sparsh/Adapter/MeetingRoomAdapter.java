package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.MeetingRoomDetailActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.model.MeetingRoomListModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomAdapter extends RecyclerView.Adapter<MeetingRoomAdapter.ViewHolder> {
    Context mContext;
    ArrayList<MeetingRoomListModel.MeetingRoomListModelData> list;

    public MeetingRoomAdapter(Context mContext, ArrayList<MeetingRoomListModel.MeetingRoomListModelData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_room_row, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.room_name.setText(list.get(position).getMeetingRoom());
        if (list.get(position).isAC()) {
            holder.ac.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.ac.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }
        if (list.get(position).isWiFi()) {
            holder.wifi.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.wifi.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }
        if (list.get(position).isMIC()) {
            holder.mike.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.mike.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }
        if (list.get(position).isWhiteBoard()) {
            holder.wb.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.wb.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }
        if (list.get(position).isProjecter()) {
            holder.projector.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.projector.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }
        if (list.get(position).isVideoConference()) {
            holder.vc.setImageTintList(ContextCompat.getColorStateList(mContext, R.color.colorPrimary));
        } else {
            holder.vc.setImageTintList(ContextCompat.getColorStateList(mContext, android.R.color.darker_gray));
        }

        holder.book.setOnClickListener(view -> {
            Intent in = new Intent(mContext, MeetingRoomDetailActivity.class);
            in.putExtra("meetingRoomId",list.get(position).getMeetingRoomID());
            in.putExtra("meetingRoomName", list.get(position).getMeetingRoom());
          //  in.putExtra("vc",list.get(position).isVideoConference());
            in.putExtra("roomUnitCode",list.get(position).getDivID());
            mContext.startActivity(in);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.room_name)
        TextView room_name;
        @BindView(R.id.book)
        Button book;
        @BindView(R.id.chair)
        ImageView chair;
        @BindView(R.id.ac)
        ImageView ac;
        @BindView(R.id.wifi)
        ImageView wifi;
        @BindView(R.id.mike)
        ImageView mike;
        @BindView(R.id.wb)
        ImageView wb;
        @BindView(R.id.projector)
        ImageView projector;
        @BindView(R.id.vc)
        ImageView vc;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            chair.setOnClickListener(this);
            ac.setOnClickListener(this);
            wifi.setOnClickListener(this);
            mike.setOnClickListener(this);
            wb.setOnClickListener(this);
            projector.setOnClickListener(this);
            vc.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.chair:
                    Toast.makeText(mContext,"No. of Seats: "+list.get(getAdapterPosition()).getNoOfSeats(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.ac:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getACText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.wifi:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getWiFiText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.mike:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getMICText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.wb:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getWhiteBoardText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.projector:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getProjectorText(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.vc:
                    Toast.makeText(mContext,""+list.get(getAdapterPosition()).getVCText(),Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }
}
