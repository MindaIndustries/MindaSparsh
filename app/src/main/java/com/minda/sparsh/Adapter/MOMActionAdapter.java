package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.CVPDetailModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MOMActionAdapter extends RecyclerView.Adapter<MOMActionAdapter.ViewHolder> {
    Context mContext;
    List<CVPDetailModel.CVPDetailData.ActionPoint> actions;

    public MOMActionAdapter(Context mContext, List<CVPDetailModel.CVPDetailData.ActionPoint> actions) {
        this.mContext = mContext;
        this.actions = actions;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.action_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.sno.setText(actions.get(position).getSno());
        holder.business.setText(actions.get(position).getBusiness());
        holder.plant.setText(actions.get(position).getPlant());
        holder.sixm.setText(actions.get(position).getFuction());
        holder.actionpoint.setText(actions.get(position).getActionPoint());
        holder.responsible.setText(actions.get(position).getResponsiblePerson());
        holder.target_date.setText(actions.get(position).getTargetDate());
        holder.remarks.setText(actions.get(position).getRemark());
//        holder.actiontaken.setText(actions.get(position).geta);
        holder.completed_date.setText(actions.get(position).getCompletedDate());
        holder.status.setText(actions.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sno,business,plant,sixm,actionpoint,responsible,target_date,remarks,actiontaken,completed_date,status;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            business = itemView.findViewById(R.id.business);
            plant = itemView.findViewById(R.id.plant);
            sixm = itemView.findViewById(R.id.sixm);
            actionpoint = itemView.findViewById(R.id.actionpoint);
            responsible = itemView.findViewById(R.id.responsible);
            target_date = itemView.findViewById(R.id.target_date);
            remarks = itemView.findViewById(R.id.remarks);
            actiontaken = itemView.findViewById(R.id.actiontaken);
            completed_date = itemView.findViewById(R.id.completed_date);
            status = itemView.findViewById(R.id.status);

        }
    }
}
