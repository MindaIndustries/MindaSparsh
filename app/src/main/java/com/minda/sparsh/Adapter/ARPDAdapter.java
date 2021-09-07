package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.ARPDModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ARPDAdapter extends RecyclerView.Adapter<ARPDAdapter.MyViewHolder> {
    private final List<ARPDModel> dataList;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_s_no, tv_emp_code, tv_name, tv_status, tv_processor_no,
                tv_request_on, tv_processed_rejected_on, tv_remarks, processed_by;

        public MyViewHolder(View view) {
            super(view);
            tv_s_no = (TextView) view.findViewById(R.id.tv_s_no);
            tv_emp_code = (TextView) view.findViewById(R.id.tv_emp_code);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_processor_no = (TextView) view.findViewById(R.id.tv_processor_no);
            tv_request_on = (TextView) view.findViewById(R.id.tv_request_on);
            tv_processed_rejected_on = (TextView) view.findViewById(R.id.tv_processed_rejected_on);
            processed_by = (TextView) view.findViewById(R.id.processed_by);
            tv_remarks = (TextView) view.findViewById(R.id.tv_remarks);
        }
    }


    public ARPDAdapter(List<ARPDModel> viewAppointmentModelList, Context context1) {
        this.dataList = viewAppointmentModelList;
        this.context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arpd_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ARPDModel arpdModel = dataList.get(position);
        holder.tv_s_no.setText(String.valueOf(position + 1));
        holder.tv_emp_code.setText(arpdModel.getApprovalBy());
        holder.tv_name.setText(arpdModel.getApprovalByName());
        holder.tv_processor_no.setText(arpdModel.getProcessorNo() + "");
        holder.tv_request_on.setText(arpdModel.getCreatedOn() + "");

        if (arpdModel.getApprovalStatus().equalsIgnoreCase("Approved")) {
            holder.tv_status.setText("Completed");
        } else {
            holder.tv_status.setText(arpdModel.getApprovalStatus());

        }

        if (arpdModel.getFlag() != null) {
            holder.processed_by.setText(arpdModel.getFlag() + "");
        }

        if (arpdModel.getUpdatedOn() != null) {
            holder.tv_processed_rejected_on.setText(arpdModel.getUpdatedOn() + "");
        }

        if (arpdModel.getRemark() != null) {
            holder.tv_remarks.setText(arpdModel.getRemark() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
