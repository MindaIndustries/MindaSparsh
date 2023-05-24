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

import java.text.SimpleDateFormat;
import java.time.Month;
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

        if(attendanceList.get(position).getDAY_IN()!=null && attendanceList.get(position).getDAY_IN().trim().length()>0) {
            holder.punch_in_time.setText(attendanceList.get(position).getDAY_IN());
        }
        else {
            holder.punch_in_time.setText("-");

        }
        if(attendanceList.get(position).getDAY_OUT()!=null && attendanceList.get(position).getDAY_OUT().trim().length()>0) {

            holder.punch_out_time.setText(attendanceList.get(position).getDAY_OUT());
        }
        else {
            holder.punch_out_time.setText("-");

        }
        if(attendanceList.get(position).getEDATE()!=null) {
            holder.date.setText(attendanceList.get(position).getEDATE().replace(".", "/").split("/")[0]);
            if (attendanceList.get(position).getDAYNAME() != null) {
                holder.day.setText(attendanceList.get(position).getDAYNAME().toUpperCase());
            }
            Calendar cal = Calendar.getInstance();
            if (attendanceList.get(position).getEDATE() != null) {
                cal.set(Calendar.MONTH, (Integer.parseInt(attendanceList.get(position).getEDATE().replace(".", "/").split("/")[1])) - 1);
            }
            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            String month_name = month_date.format(cal.getTime());
            holder.month.setText(month_name);

            if (attendanceList.get(position).getEDATE().equals((day_now + "." + month_now + "." + year_now))) {
                holder.row.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                //   holder.date.setTextColor(Color.WHITE);
                //  holder.day.setTextColor(Color.WHITE);
                holder.punch_in_time.setTextColor(Color.WHITE);
                holder.punch_out_time.setTextColor(Color.WHITE);
                holder.punch_in.setTextColor(Color.WHITE);
                holder.punch_out.setTextColor(Color.WHITE);
            }
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
        @BindView(R.id.month)
        TextView month;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
