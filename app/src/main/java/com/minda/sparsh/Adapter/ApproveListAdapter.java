package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.minda.sparsh.AccessRequestDetailsActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.model.ApproveList;
import com.minda.sparsh.util.Utility;

import java.util.List;

public class ApproveListAdapter extends RecyclerView.Adapter<ApproveListAdapter.MyViewHolder> {
    private List<ApproveList> approveLists;
    Context context;


    public ApproveListAdapter(List<ApproveList> viewAppointmentModelList, Context context1) {
        this.approveLists = viewAppointmentModelList;
        this.context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approve_list_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ApproveList approveListModel = approveLists.get(position);
        holder.tv_access_request_no.setText(approveListModel.getAccessRequestNo());
        holder.tv_request_on.setText(approveListModel.getCreatedOn());
        holder.tv_access_for.setText(approveListModel.getAccessForName());
//        holder.tv_request_type.setText(approveListModel.getRequestType());
        holder.tv_Approval_status.setText(approveListModel.getApprovalStatus());

        if (approveListModel.getApprovalStatus().equalsIgnoreCase("Approved")) {
            holder.tv_Approval_status.setTextColor(Color.parseColor("#3b7e04"));
        } else {
            holder.tv_Approval_status.setTextColor(Color.parseColor("#d91f17"));

        }


        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccessRequestDetailsActivity.class);
                intent.putExtra(Utility.REQUEST_ID, approveListModel.getRequestId());
                context.startActivity(intent);
            }
        });
        holder.tv_access_request_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, AccessRequestDetailsActivity.class);
//                intent.putExtra(Utility.REQUEST_ID, approveListModel.getRequestId());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return approveLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_access_request_no, tv_request_on, tv_access_for, tv_request_type, tv_Approval_status;
        Button btn_view;

        public MyViewHolder(View view) {
            super(view);
            tv_access_request_no = (TextView) view.findViewById(R.id.tv_access_request_no);
            tv_request_on = (TextView) view.findViewById(R.id.tv_request_on);
            tv_access_for = (TextView) view.findViewById(R.id.tv_access_for);
//            tv_request_type = (TextView) view.findViewById(R.id.tv_request_type);
            tv_Approval_status = (TextView) view.findViewById(R.id.tv_Approval_status);
            btn_view = (Button) view.findViewById(R.id.btn_view);
        }
    }


}
