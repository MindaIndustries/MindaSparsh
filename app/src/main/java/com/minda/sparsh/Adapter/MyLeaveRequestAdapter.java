package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.LeaveRequestModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLeaveRequestAdapter extends RecyclerView.Adapter<MyLeaveRequestAdapter.ViewHolder> {
    Context mContext;
    ArrayList<LeaveRequestModel> leaveRequests;
    OnItemClickListener onItemClickListener;


    public MyLeaveRequestAdapter(Context mContext, ArrayList<LeaveRequestModel> leaveRequests) {
        this.mContext = mContext;
        this.leaveRequests = leaveRequests;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_req_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (leaveRequests.get(position).getLVE_REQNO() != null) {
            holder.req_no.setText(leaveRequests.get(position).getLVE_REQNO());
        }

        if (leaveRequests.get(position).getLVE_FROMDT() != null) {
            String[] fromdt = leaveRequests.get(position).getLVE_FROMDT().replace(".", "/").split("/");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fromdt[0]));
            calendar.set(Calendar.MONTH, (Integer.parseInt(fromdt[1]) - 1));
            calendar.set(Calendar.YEAR, Integer.parseInt(fromdt[2]));
            //  holder.from.setText(leaveRequests.get(position).getLVE_FROMDT());
            holder.from.setText(getDateFormat(calendar));
        }
        if (leaveRequests.get(position).getLVE_TODT() != null) {
            String[] todt = leaveRequests.get(position).getLVE_TODT().replace(".", "/").split("/");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(todt[0]));
            calendar.set(Calendar.MONTH, (Integer.parseInt(todt[1]) - 1));
            calendar.set(Calendar.YEAR, Integer.parseInt(todt[2]));

            //  holder.to.setText(leaveRequests.get(position).getLVE_TODT());
            holder.to.setText(getDateFormat(calendar));

        }
        if (leaveRequests.get(position).getLVE_STATUS() != null) {
            if (leaveRequests.get(position).getLVE_STATUS().equalsIgnoreCase("Completed")) {
                if (leaveRequests.get(position).getLVE_AUTH().trim().equalsIgnoreCase("R")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.orange));
                    holder.status.setText("Cancelled");
                } else if (leaveRequests.get(position).getLVE_AUTH().trim().equalsIgnoreCase("N")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.status.setText("Rejected");
                } else if (leaveRequests.get(position).getLVE_AUTH().trim().equalsIgnoreCase("Y")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
                    holder.status.setText("Approved");
                }
                holder.options.setVisibility(View.GONE);

            } else {
                if (leaveRequests.get(position).getLVE_STATUS().equalsIgnoreCase("Pending")) {
                    holder.options.setVisibility(View.VISIBLE);
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.yellow_attendance));
                } else {
                    holder.options.setVisibility(View.GONE);
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                }
                holder.status.setText(leaveRequests.get(position).getLVE_STATUS());

            }


        }

    }

    @Override
    public int getItemCount() {
        return leaveRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.req_no)
        TextView req_no;
        @BindView(R.id.from)
        TextView from;
        @BindView(R.id.to)
        TextView to;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.options)
        ImageView options;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());

        }
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }


    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }


    public String getDateFormat(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,yy");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        return dateTime;
    }
}
