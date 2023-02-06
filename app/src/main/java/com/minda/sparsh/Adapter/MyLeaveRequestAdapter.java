package com.minda.sparsh.Adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

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
        if(leaveRequests.get(position).getLVE_REQNO()!=null) {
            holder.req_no.setText(leaveRequests.get(position).getLVE_REQNO());
        }
        if(leaveRequests.get(position).getLVE_FROMDT()!=null) {
            holder.from.setText(leaveRequests.get(position).getLVE_FROMDT());
        }
        if(leaveRequests.get(position).getLVE_TODT()!=null) {
            holder.to.setText(leaveRequests.get(position).getLVE_TODT());
        }
        if(leaveRequests.get(position).getLVE_STATUS()!=null) {
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
            } else {
                holder.status.setText(leaveRequests.get(position).getLVE_STATUS());
                holder.status.setTextColor(mContext.getResources().getColor(R.color.yellow));

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
            ButterKnife.bind(this,itemView);
            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) onItemClickListener.onClick(view, getAdapterPosition());

        }
    }
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }


    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
