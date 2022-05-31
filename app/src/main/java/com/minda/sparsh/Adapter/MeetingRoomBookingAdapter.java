package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.MeetingRoomBookData;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomBookingAdapter extends RecyclerView.Adapter<MeetingRoomBookingAdapter.ViewHolder> {
    Context mContext;
    ArrayList<MeetingRoomBookData.MeetingRoomBookDataModel> list;

    public MeetingRoomBookingAdapter(Context mContext, ArrayList<MeetingRoomBookData.MeetingRoomBookDataModel> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booked_meeting_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MeetingRoomBookingAdapter.ViewHolder holder, int position) {

        holder.status.setText(list.get(position).getBkngStatus());
        holder.date.setText(list.get(position).getCreatedDate());
        holder.location.setText(list.get(position).getMeetingRoom()+" | "+ list.get(position).getUnitName());
        holder.internal.setText(list.get(position).getAttendeeInt());
        holder.external.setText(list.get(position).getAttendeeExt());
        holder.agenda.setText(list.get(position).getPurpose());
    }
    @Override
    public int getItemViewType(int position) {
        return (position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.agenda)
        TextView agenda;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.internal)
        TextView internal;
        @BindView(R.id.external)
        TextView external;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
