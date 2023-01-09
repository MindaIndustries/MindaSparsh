package com.minda.sparsh.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.AlmsReportModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ALMSRecyclerViewAdapter extends RecyclerView.Adapter<ALMSRecyclerViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<AlmsReportModel> attendanceList;
    Calendar calendar_now;

    public ALMSRecyclerViewAdapter(Context mContext, ArrayList<AlmsReportModel> attendanceList) {
        this.mContext = mContext;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alms_rv_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        calendar_now = Calendar.getInstance();
        int day_now = calendar_now.get(Calendar.DAY_OF_MONTH);
        int month_now = calendar_now.get(Calendar.MONTH)+1;
        int year_now = calendar_now.get(Calendar.YEAR);

        holder.punch_in_time.setText(attendanceList.get(position).getDAY_IN());
        holder.punch_out_time.setText(attendanceList.get(position).getDAY_OUT());
        holder.date.setText(attendanceList.get(position).getEDATE().replace(".","/").split("/")[0]);
        holder.day.setText(attendanceList.get(position).getDAYNAME().toUpperCase());

        if(attendanceList.get(position).getEDATE().equals((day_now+"."+month_now+"."+year_now))){
            holder.row.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
         //   holder.date.setTextColor(Color.WHITE);
          //  holder.day.setTextColor(Color.WHITE);
            holder.punch_in_time.setTextColor(Color.WHITE);
            holder.punch_out_time.setTextColor(Color.WHITE);
            holder.punch_in.setTextColor(Color.WHITE);
            holder.punch_out.setTextColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.punch_in_time)
        TextView punch_in_time;
        @BindView(R.id.punch_out_time)
        TextView punch_out_time;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.row)
        RelativeLayout row;
        @BindView(R.id.punch_in)
        TextView punch_in;
        @BindView(R.id.punch_out)
        TextView punch_out;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
