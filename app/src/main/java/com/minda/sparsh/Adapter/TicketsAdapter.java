package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minda.sparsh.Interface;
import com.minda.sparsh.MyTaskDetail;
import com.minda.sparsh.R;
import com.minda.sparsh.TicketDetail;
import com.minda.sparsh.fragment.CreateTicketFragment;
import com.minda.sparsh.fragment.MyTickets;
import com.minda.sparsh.model.MyTicketsResponse;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.ViewHolder> {

    Context mContext;
    List<MyTicketsResponse> myTickets;

    public TicketsAdapter(Context mContext, List<MyTicketsResponse> myTickets) {
        this.mContext = mContext;
        this.myTickets = myTickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_tickets_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.status.setText("" + myTickets.get(position).getStatus());
        holder.tkt_type_value.setText("" + myTickets.get(position).getTicketType());
        holder.tkt_no_value.setText("" + myTickets.get(position).getTicketNo());
        holder.reported_dt_time_value.setText("" + myTickets.get(position).getReporteddate());
        holder.assignee_value.setText("" + myTickets.get(position).getAssigne());
        holder.cc_value.setText("" + myTickets.get(position).getCC());
        holder.assign_loc_value.setText("" + myTickets.get(position).getLocation());
        holder.reported_by_value.setText("" + myTickets.get(position).getReportedby());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.framelayout);
                if (fragment instanceof MyTickets) {
                    if (myTickets.get(position).getStatus().equals("Open")) {
                        Intent in = new Intent(mContext, TicketDetail.class);
                        in.putExtra("ticketno", (Serializable) myTickets.get(position));
                        mContext.startActivity(in);
                    }
                } else {
                    Intent in = new Intent(mContext, MyTaskDetail.class);
                    in.putExtra("ticketno", (Serializable) myTickets.get(position));
                    mContext.startActivity(in);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myTickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.tkt_type_value)
        TextView tkt_type_value;
        @BindView(R.id.tkt_no_value)
        TextView tkt_no_value;
        @BindView(R.id.reported_dt_time_value)
        TextView reported_dt_time_value;
        @BindView(R.id.assignee_value)
        TextView assignee_value;
        @BindView(R.id.cc_value)
        TextView cc_value;
        @BindView(R.id.assign_loc_value)
        TextView assign_loc_value;
        @BindView(R.id.reported_by_value)
        TextView reported_by_value;
        @BindView(R.id.row)
        RelativeLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
