package com.minda.sparsh.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.ApprovalRequestModel;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaveApprovalAdapter extends RecyclerView.Adapter<LeaveApprovalAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ApprovalRequestModel> approvalist;
    OnItemClickListener onItemClickListener;

    public LeaveApprovalAdapter(Context mContext, ArrayList<ApprovalRequestModel> approvalist) {
        this.mContext = mContext;
        this.approvalist = approvalist;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_approval_row, parent, false);
        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.emp_name.setText(approvalist.get(position).getEmpname());
        holder.from_dt.setText(approvalist.get(position).getFromDate());
        holder.to_date.setText(approvalist.get(position).getToDate());
        holder.leave_type.setText(approvalist.get(position).getReqTypeDesc());
        holder.leave_type.setBackgroundColor(Color.parseColor("#0089c8"));
        holder.req_no.setText(approvalist.get(position).getReqno());
       /* if (approvalist.get(position).getReqType().equals("OD")|| approvalist.get(position).getReqType().equals("FTP")) {
            holder.leave_type.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        else{
            holder.leave_type.setBackgroundColor(Color.parseColor("#dbaf1d"));

        }*/
    }

    @Override
    public int getItemCount() {
        return approvalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.emp_name)
        TextView emp_name;
        @BindView(R.id.leave_type)
        TextView leave_type;
        @BindView(R.id.from_dt_value)
        TextView from_dt;
        @BindView(R.id.to_dt_value)
        TextView to_date;
        @BindView(R.id.approve)
        Button approve;
        @BindView(R.id.reject)
        Button reject;
        @BindView(R.id.req_no)
        TextView req_no;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            approve.setOnClickListener(this);
            reject.setOnClickListener(this);
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
