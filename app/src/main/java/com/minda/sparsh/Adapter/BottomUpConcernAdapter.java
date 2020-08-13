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
import android.widget.Toast;

import com.minda.sparsh.BottomUpConcernDetailActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomUpConcernAdapter extends RecyclerView.Adapter<BottomUpConcernAdapter.ViewHolder> {

    Context mContext;
    ArrayList<BottomUpConcern> concerns;


    public BottomUpConcernAdapter(Context mContext, ArrayList<BottomUpConcern> concerns) {
        this.mContext = mContext;
        this.concerns = concerns;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bottomup_view_row, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (concerns.get(i).getStatus().equalsIgnoreCase("True")) {
            viewHolder.status.setText("Closed");
        } else {
            if (concerns.get(i).getFlag().equalsIgnoreCase("True")) {
                viewHolder.status.setText("Assigned");

            } else {
                viewHolder.status.setText("Pending");
            }
        }

        viewHolder.concernNovalue.setText(concerns.get(i).getConcernNo());
        viewHolder.raisedOnvalue.setText(concerns.get(i).getRaisedOn());
        viewHolder.responsible6MValue.setText(concerns.get(i).getDeptName());
        viewHolder.unitValue.setText(concerns.get(i).getUnit() + ":" + concerns.get(i).getUnitName());


        viewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isOnline(mContext)) {
                    Intent in = new Intent(mContext, BottomUpConcernDetailActivity.class);
                    in.putExtra("concernModel", (Serializable) concerns.get(i));
                    mContext.startActivity(in);
                } else {
                    Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return concerns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
            ButterKnife.bind(this, itemView);

        }
    }


}
