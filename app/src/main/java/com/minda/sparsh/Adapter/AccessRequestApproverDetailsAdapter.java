package com.minda.sparsh.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.AccessRequestApproverDetailsModel;

import java.util.List;

public class AccessRequestApproverDetailsAdapter extends RecyclerView.Adapter<AccessRequestApproverDetailsAdapter.MyViewHolder> {
    private List<AccessRequestApproverDetailsModel> dataList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_s_no, tv_approved_by_emp_code, tv_approved_by_name, tv_approved_status, tv_approval_level,
                tv_approval_request_on, tv_approval_on, tv_remarks;

        public MyViewHolder(View view) {
            super(view);
            tv_s_no = (TextView) view.findViewById(R.id.tv_s_no);
            tv_approved_by_emp_code = (TextView) view.findViewById(R.id.tv_approved_by_emp_code);
            tv_approved_by_name = (TextView) view.findViewById(R.id.tv_approved_by_name);
            tv_approved_status = (TextView) view.findViewById(R.id.tv_approved_status);
            tv_approval_level = (TextView) view.findViewById(R.id.tv_approval_level);
            tv_approval_request_on = (TextView) view.findViewById(R.id.tv_approval_request_on);
            tv_approval_on = (TextView) view.findViewById(R.id.tv_approval_on);
            tv_remarks = (TextView) view.findViewById(R.id.tv_remarks);
        }
    }


    public AccessRequestApproverDetailsAdapter(List<AccessRequestApproverDetailsModel> viewAppointmentModelList, Context context1) {
        this.dataList = viewAppointmentModelList;
        this.context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approver_celll_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AccessRequestApproverDetailsModel approveListModel = dataList.get(position);
        holder.tv_s_no.setText(String.valueOf(position + 1));
        holder.tv_approved_by_emp_code.setText(approveListModel.getApprovalBy());
        holder.tv_approved_by_name.setText(approveListModel.getApprovalByName());
        holder.tv_approved_status.setText(approveListModel.getApprovalStatus());
        holder.tv_approval_level.setText(approveListModel.getApprovalLevel());
        holder.tv_approval_request_on.setText(approveListModel.getCreatedOn() + "");
        if (approveListModel.getUpdatedOn()!=null) {
            holder.tv_approval_on.setText(approveListModel.getUpdatedOn()+"");
        }

        if (approveListModel.getRemark()!=null) {
            holder.tv_remarks.setText(approveListModel.getRemark() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
