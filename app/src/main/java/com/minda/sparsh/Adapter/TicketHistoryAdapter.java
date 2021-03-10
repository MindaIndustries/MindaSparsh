package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.TicketHistoryResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketHistoryAdapter extends RecyclerView.Adapter<TicketHistoryAdapter.ViewHolder> {

    Context mContext;
    List<TicketHistoryResponse> ticketHistoryResponseList;

    public TicketHistoryAdapter(Context mContext, List<TicketHistoryResponse> ticketHistoryResponseList) {
        this.mContext = mContext;
        this.ticketHistoryResponseList = ticketHistoryResponseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tkt_detail_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tkt_no.setText(ticketHistoryResponseList.get(position).getTicketNo());
        holder.emp_name.setText(ticketHistoryResponseList.get(position).getEName());
        holder.remarks.setText(ticketHistoryResponseList.get(position).getRemark());
        holder.status.setText(ticketHistoryResponseList.get(position).getRemarkFor());
        holder.created_on.setText(ticketHistoryResponseList.get(position).getCreatedOn());
    }

    @Override
    public int getItemCount() {
        return ticketHistoryResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tkt_no)
        TextView tkt_no;
        @BindView(R.id.emp_name)
        TextView emp_name;
        @BindView(R.id.remarks)
        TextView remarks;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.created_on)
        TextView created_on;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
