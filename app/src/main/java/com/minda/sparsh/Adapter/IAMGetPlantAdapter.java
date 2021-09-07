package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.minda.sparsh.R;
import com.minda.sparsh.model.IAMGetPlantModel;
import com.minda.sparsh.util.PlantInterface;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class IAMGetPlantAdapter extends RecyclerView.Adapter<IAMGetPlantAdapter.MyViewHolder> {
    private final List<IAMGetPlantModel> list;
    Context context;
    PlantInterface plantInterface;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton rb;
        public CheckBox cb;


        public MyViewHolder(View view) {
            super(view);
            rb = (RadioButton) view.findViewById(R.id.rb);
            cb = (CheckBox) view.findViewById(R.id.cb);
        }
    }

    public IAMGetPlantAdapter(List<IAMGetPlantModel> viewAppointmentModelList, Context context1, PlantInterface plantInterface1) {
        this.list = viewAppointmentModelList;
        this.context = context1;
        this.plantInterface = plantInterface1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.domain_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final IAMGetPlantModel iamGetPlantModel = list.get(position);
        holder.cb.setVisibility(View.VISIBLE);
        holder.cb.setText(iamGetPlantModel.getUnitName() + " (" + iamGetPlantModel.getPlantCode() + ")");
        holder.cb.setOnClickListener(view -> {
            if (holder.cb.isChecked()) {
                plantInterface.handleClick(iamGetPlantModel.getPlantCode(), "check", iamGetPlantModel.getUnitName(),iamGetPlantModel.getUnitCode());
            } else {
                plantInterface.handleClick(iamGetPlantModel.getPlantCode(), "unCheck", iamGetPlantModel.getUnitName(),iamGetPlantModel.getUnitCode());
            }

//                ((RequestForAccessActivity) context).plantId(iamGetPlantModel.getPlantCode());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
