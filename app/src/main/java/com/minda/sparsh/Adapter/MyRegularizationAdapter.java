package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.model.LeaveRegularizationModel;
import com.minda.sparsh.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRegularizationAdapter extends RecyclerView.Adapter<MyRegularizationAdapter.ViewHolder> {
    Context mContext;
    ArrayList<LeaveRegularizationModel> leaveRequests;
    OnItemClickListener1 onItemClickListener;


    public MyRegularizationAdapter(Context mContext, ArrayList<LeaveRegularizationModel> leaveRequests) {
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
        if(leaveRequests.get(position).getReqNo()!=null) {
            holder.req_no.setText(leaveRequests.get(position).getReqNo());
        }
        if(leaveRequests.get(position).getFromDate()!=null)
            holder.from.setText(leaveRequests.get(position).getFromDate());
        if(leaveRequests.get(position).getToDate()!=null)
            holder.to.setText(leaveRequests.get(position).getToDate());
        if(leaveRequests.get(position).getActiveDesc()!=null) {
            if (leaveRequests.get(position).getActiveDesc().equalsIgnoreCase("Completed")) {
                if (leaveRequests.get(position).getReqAuthDesc().equalsIgnoreCase("Cancelled")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.orange));
                } else if (leaveRequests.get(position).getReqAuthDesc().equalsIgnoreCase("Rejected")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                } else if (leaveRequests.get(position).getReqAuthDesc().equalsIgnoreCase("Approved")) {
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
                }
                holder.status.setText(leaveRequests.get(position).getReqAuthDesc());

            } else {
                holder.status.setText(leaveRequests.get(position).getActiveDesc());
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
            if (onItemClickListener != null) onItemClickListener.onClick1(view, getAdapterPosition());
        }
    }
    public void setClickListener(OnItemClickListener1 itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener1 {
        public void onClick1(View view, int position);
    }
}
