package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.minda.sparsh.BottomUpConcernDetailActivity;
import com.minda.sparsh.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomUpConcernAdapter extends RecyclerView.Adapter<BottomUpConcernAdapter.ViewHolder> {

    Context mContext;
    ArrayList<String> concerns;

    public BottomUpConcernAdapter(Context mContext, ArrayList<String> concerns) {
        this.mContext = mContext;
        this.concerns = concerns;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bottomup_view_row, viewGroup, false);

        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in  = new Intent(mContext, BottomUpConcernDetailActivity.class);
                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return concerns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.concern_no_value)
        TextView concernNovalue;
        @BindView(R.id.raised_on_value)
        TextView raisedOnvalue;
        @BindView(R.id.unit_value)
        TextView unitValue;
        @BindView(R.id.responsible6M_value)
        TextView responsible6MValue;
        @BindView(R.id.view_details)
        Button viewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
