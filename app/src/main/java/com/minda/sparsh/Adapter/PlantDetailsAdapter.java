package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.AccessRequestPlantDetailModel;

import java.util.List;

public class PlantDetailsAdapter extends RecyclerView.Adapter<PlantDetailsAdapter.MyViewHolder> {
    private List<AccessRequestPlantDetailModel> plantDetailModelList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_s_no, tv_domain, tv_business, tv_plant;

        public MyViewHolder(View view) {
            super(view);
            tv_s_no = (TextView) view.findViewById(R.id.tv_s_no);
            tv_domain = (TextView) view.findViewById(R.id.tv_domain);
            tv_business = (TextView) view.findViewById(R.id.tv_business);
            tv_plant = (TextView) view.findViewById(R.id.tv_plant);
        }
    }


    public PlantDetailsAdapter(List<AccessRequestPlantDetailModel> viewAppointmentModelList, Context context1) {
        this.plantDetailModelList = viewAppointmentModelList;
        this.context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_details_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AccessRequestPlantDetailModel approveListModel = plantDetailModelList.get(position);
        holder.tv_s_no.setText(String.valueOf(position + 1));
        holder.tv_domain.setText(approveListModel.getDomainName());
        holder.tv_business.setText(approveListModel.getBUSINESS());
        holder.tv_plant.setText(approveListModel.getUnitName());


    }

    @Override
    public int getItemCount() {
        return plantDetailModelList.size();
    }
}
