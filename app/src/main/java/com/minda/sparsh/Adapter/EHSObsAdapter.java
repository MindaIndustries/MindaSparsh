package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minda.sparsh.EHSInitiate;
import com.minda.sparsh.R;
import com.minda.sparsh.fragment.EHSInitiateFragment;
import com.minda.sparsh.model.EHSObsModel;

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

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.obsType.setText(myObservations.get(i).getOBName());
        viewHolder.actNoValue.setText(myObservations.get(i).getActNo());
        String[] actDate = myObservations.get(i).getActDate().split("/");
        String actmonth = actDate[0];
        String actday = actDate[1];
        String actyear = actDate[2];
        viewHolder.obsDateValue.setText(actday + "/" + actmonth + "/" + actyear);
        String[] raisedDate = myObservations.get(i).getCreatedOn().split("/");
        String raisedmonth = raisedDate[0];
        String raisedday = raisedDate[1];
        String raisedyear = raisedDate[2];
        viewHolder.raisedOnValue.setText(raisedday + "/" + raisedmonth + "/" + raisedyear);
        viewHolder.unitValue.setText(myObservations.get(i).getUnitName());
        viewHolder.identifiedLocValue.setText(myObservations.get(i).getLocation());
        viewHolder.categoryValue.setText(myObservations.get(i).getCatName());
        if (myObservations.get(i).getStatus().equals("3")) {
            viewHolder.statusValue.setText("Completed");
        } else {
            viewHolder.statusValue.setText("Pending");
        }

        if(myObservations.get(i).getStatus().equals("1")){
            viewHolder.edit.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.edit.setVisibility(View.GONE);
        }

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("obsDate", myObservations.get(i).getActDate());
                bundle.putString("unit", myObservations.get(i).getUnitCode() + ":" + myObservations.get(i).getUnitName());
                bundle.putString("typeOfObs", myObservations.get(i).getOBName());
                bundle.putString("safetyOfficer", myObservations.get(i).getUnitSafetyOfficer());
                bundle.putString("identifiedLoc", myObservations.get(i).getLocation());
                bundle.putString("category", myObservations.get(i).getCatName());
                bundle.putString("subCategory", myObservations.get(i).getSubCategoryID());
                bundle.putString("description", myObservations.get(i).getDescription());
                bundle.putString("actId", myObservations.get(i).getID());
                bundle.putString("actNo", myObservations.get(i).getActNo());
                bundle.putString("catId", myObservations.get(i).getCategoryID());
                bundle.putString("obsId", myObservations.get(i).getObservationID());
                bundle.putString("incidenceTime",myObservations.get(i).getIncidenceTime());
                bundle.putString("incidenceAction",myObservations.get(i).getIncidentceAction());
                bundle.putString("attachment",myObservations.get(i).getAttachment());
                bundle.putString("status",myObservations.get(i).getStatus());
                EHSInitiateFragment ehsInitiateFragment = new EHSInitiateFragment();
                ehsInitiateFragment.setArguments(bundle);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, ehsInitiateFragment)
                        .addToBackStack(null)
                        .commit();

             /*   Intent in = new Intent(mContext, EHSInitiate.class);
                in.putExtra("obsDate", myObservations.get(i).getActDate());
                in.putExtra("unit", myObservations.get(i).getUnitCode() + ":" + myObservations.get(i).getUnitName());
                in.putExtra("typeOfObs", myObservations.get(i).getOBName());
                in.putExtra("safetyOfficer", myObservations.get(i).getUnitSafetyOfficer());
                in.putExtra("identifiedLoc", myObservations.get(i).getLocation());
                in.putExtra("category", myObservations.get(i).getCatName());
                in.putExtra("subCategory", myObservations.get(i).getSubCategoryID());
                in.putExtra("description", myObservations.get(i).getDescription());
                in.putExtra("actId", myObservations.get(i).getID());
                in.putExtra("actNo", myObservations.get(i).getActNo());
                in.putExtra("catId", myObservations.get(i).getCategoryID());
                in.putExtra("obsId", myObservations.get(i).getObservationID());
                mContext.startActivity(in);
      */      }
        });

        viewHolder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("obsDate", myObservations.get(i).getActDate());
                bundle.putString("unit", myObservations.get(i).getUnitCode() + ":" + myObservations.get(i).getUnitName());
                bundle.putString("typeOfObs", myObservations.get(i).getOBName());
                bundle.putString("safetyOfficer", myObservations.get(i).getUnitSafetyOfficer());
                bundle.putString("identifiedLoc", myObservations.get(i).getLocation());
                bundle.putString("category", myObservations.get(i).getCatName());
                bundle.putString("subCategory", myObservations.get(i).getSubCategoryID());
                bundle.putString("description", myObservations.get(i).getDescription());
                bundle.putString("actId", myObservations.get(i).getID());
                bundle.putString("actNo", myObservations.get(i).getActNo());
                bundle.putString("catId", myObservations.get(i).getCategoryID());
                bundle.putString("obsId", myObservations.get(i).getObservationID());
                bundle.putString("incidenceTime",myObservations.get(i).getIncidenceTime());
                bundle.putString("incidenceAction",myObservations.get(i).getIncidentceAction());
                bundle.putString("attachment",myObservations.get(i).getAttachment());
                bundle.putString("status",myObservations.get(i).getStatus());
                EHSInitiateFragment ehsInitiateFragment = new EHSInitiateFragment();
                ehsInitiateFragment.setArguments(bundle);
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, ehsInitiateFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myObservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.row)
        RelativeLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
