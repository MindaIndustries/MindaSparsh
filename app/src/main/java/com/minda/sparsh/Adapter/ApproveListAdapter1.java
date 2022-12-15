package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.AccessRequestDetailsActivity;
import com.minda.sparsh.AccessRequestDetailsActivity1;
import com.minda.sparsh.R;
import com.minda.sparsh.model.ApproveList;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApproveListAdapter1 extends RecyclerView.Adapter<ApproveListAdapter1.MyViewHolder> {
    private final List<ApproveList> approveLists;
    Context context;
    OnItemClickListener onItemClickListener;


    public ApproveListAdapter1(List<ApproveList> viewAppointmentModelList, Context context1) {
        this.approveLists = viewAppointmentModelList;
        this.context = context1;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
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
        holder.tv_Approval_status.setText(approveListModel.getFlag());

      /*  if (approveListModel.getApprovalStatus().equalsIgnoreCase("Approved")) {
            holder.tv_Approval_status.setTextColor(Color.parseColor("#3b7e04"));
        } else {
            holder.tv_Approval_status.setTextColor(Color.parseColor("#d91f17"));

        }
*/
        if(approveListModel.getFlag().equalsIgnoreCase("Pending at 1st Approver")){
            //delete
            holder.btn_edit_delete.setText("Delete");
            holder.btn_edit_delete.setVisibility(View.VISIBLE);
        }
        else if( approveListModel.getFlag().equalsIgnoreCase("Back to Requestor")){
            //edit
            holder.btn_edit_delete.setText("Edit");
            holder.btn_edit_delete.setVisibility(View.VISIBLE);
        }
        else{
            holder.btn_edit_delete.setVisibility(View.GONE);

        }

        holder.btn_view.setOnClickListener(view -> {
            Intent intent = new Intent(context, AccessRequestDetailsActivity1.class);
            intent.putExtra(Utility.REQUEST_ID, approveListModel.getRequestId());
            context.startActivity(intent);
        });
        holder.tv_access_request_no.setOnClickListener(view -> {
//                Intent intent = new Intent(context, AccessRequestDetailsActivity.class);
//                intent.putExtra(Utility.REQUEST_ID, approveListModel.getRequestId());
//                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return approveLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_access_request_no, tv_request_on, tv_access_for, tv_Approval_status;
        Button btn_view, btn_edit_delete;

        public MyViewHolder(View view) {
            super(view);
            tv_access_request_no =  view.findViewById(R.id.tv_access_request_no);
            tv_request_on =  view.findViewById(R.id.tv_request_on);
            tv_access_for =  view.findViewById(R.id.tv_access_for);
//            tv_request_type =  view.findViewById(R.id.tv_request_type);
            tv_Approval_status =  view.findViewById(R.id.tv_Approval_status);
            btn_view =  view.findViewById(R.id.btn_view);
            btn_edit_delete = view.findViewById(R.id.btn_edit_delete);
            btn_edit_delete.setOnClickListener(this);
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
