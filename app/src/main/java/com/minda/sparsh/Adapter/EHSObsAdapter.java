package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minda.sparsh.EHSInitiate;
import com.minda.sparsh.R;
import com.minda.sparsh.model.EHSObsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EHSObsAdapter extends RecyclerView.Adapter<EHSObsAdapter.ViewHolder> {

    Context mContext;
    List<EHSObsModel> myObservations;

    public EHSObsAdapter(Context mContext, List<EHSObsModel> myObservations) {
        this.mContext = mContext;
        this.myObservations = myObservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ehs_obs_row, viewGroup, false);


        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.obsType.setText(myObservations.get(i).getOBName());
        viewHolder.actNoValue.setText(myObservations.get(i).getActNo());

        String [] actDate = myObservations.get(i).getActDate().split("/");
        String actmonth = actDate[0];
        String actday = actDate[1];
        String actyear = actDate[2];
        viewHolder.obsDateValue.setText(actday+"/"+actmonth+"/"+actyear);
        String [] raisedDate = myObservations.get(i).getCreatedOn().split("/");
        String raisedmonth = raisedDate[0];
        String raisedday = raisedDate[1];
        String raisedyear = raisedDate[2];
        viewHolder.raisedOnValue.setText(raisedday+"/"+raisedmonth+"/"+raisedyear);
        viewHolder.unitValue.setText(myObservations.get(i).getUnitName());
        viewHolder.identifiedLocValue.setText(myObservations.get(i).getLocation());
        viewHolder.categoryValue.setText(myObservations.get(i).getCatName());
        if(myObservations.get(i).getStatus().equals("3")) {
            viewHolder.statusValue.setText("Completed");
        }
        else{
            viewHolder.statusValue.setText("Pending");

        }

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, EHSInitiate.class);
                in.putExtra("obsDate",myObservations.get(i).getActDate());
                in.putExtra("unit",myObservations.get(i).getUnitCode()+":"+myObservations.get(i).getUnitName());
                in.putExtra("typeOfObs",myObservations.get(i).getOBName());
                in.putExtra("safetyOfficer",myObservations.get(i).getUnitSafetyOfficer());
                in.putExtra("identifiedLoc",myObservations.get(i).getLocation());
                in.putExtra("category",myObservations.get(i).getCatName());
                in.putExtra("subCategory",myObservations.get(i).getSubCategoryID());
                in.putExtra("description",myObservations.get(i).getDescription());
                in.putExtra("actId",myObservations.get(i).getID());
                in.putExtra("actNo",myObservations.get(i).getActNo());
                in.putExtra("catId",myObservations.get(i).getCategoryID());
                in.putExtra("obsId",myObservations.get(i).getObservationID());

                mContext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myObservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.obs_type)
        TextView obsType;
        @BindView(R.id.act_no_value)
        TextView actNoValue;
        @BindView(R.id.obs_date_value)
        TextView obsDateValue;
        @BindView(R.id.raised_on_value)
        TextView raisedOnValue;
        @BindView(R.id.unit_value)
        TextView unitValue;
        @BindView(R.id.identified_loc_value)
        TextView identifiedLocValue;
        @BindView(R.id.category_value)
        TextView categoryValue;
        @BindView(R.id.status_value)
        TextView statusValue;
        @BindView(R.id.edit)
        ImageView edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
