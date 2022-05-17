package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.minda.sparsh.R;
import com.minda.sparsh.RequestForAccessActivity;
import com.minda.sparsh.model.IAMGetDomainModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by admin on 10/12/2017.
 */

public class IAMGetDomainAdapter extends RecyclerView.Adapter<IAMGetDomainAdapter.MyViewHolder> {
    private final List<IAMGetDomainModel> list;
    Context context;
    private final RadioButton lastCheckedRB = null;
    String checkCondition;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton rb;
        CheckBox checkBox;


        public MyViewHolder(View view) {
            super(view);
            rb = view.findViewById(R.id.rb);
            checkBox = view.findViewById(R.id.cb);

        }
    }


    public IAMGetDomainAdapter(List<IAMGetDomainModel> viewAppointmentModelList, Context context1, String check) {
        this.list = viewAppointmentModelList;
        this.context = context1;
        this.checkCondition = check;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.domain_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final IAMGetDomainModel iamGetDomainModel = list.get(position);
       /* if (checkCondition.equalsIgnoreCase("7") || checkCondition.equalsIgnoreCase("8") || checkCondition.equalsIgnoreCase("48")) {
            holder.rb.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setText(iamGetDomainModel.getDomainName());

        } else {
       */
        holder.checkBox.setVisibility(View.GONE);
        holder.rb.setVisibility(View.VISIBLE);
        holder.rb.setText(iamGetDomainModel.getDomainName().trim());
        //  }

        holder.rb.setOnClickListener(view -> {
            resetData(iamGetDomainModel.getDomainName());
            if (context instanceof RequestForAccessActivity) {
                ((RequestForAccessActivity) context).hitIAMGetBusinessApi(String.valueOf(iamGetDomainModel.getDomainID()), iamGetDomainModel.getDomainName(), "radioButton");
            }
        });

        if (iamGetDomainModel.isChecked()) {
            holder.rb.setChecked(true);

        } else {
            holder.rb.setChecked(false);
        }

      /*  holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof RequestForAccessActivity) {
                    ((RequestForAccessActivity) context).hitIAMGetBusinessApi(String.valueOf(iamGetDomainModel.getDomainID()),"checkBox");
                }
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private void resetData(String itemName) {
        for (int i = 0; i < list.size(); i++) {
            IAMGetDomainModel mList = list.get(i);
            if (mList.getDomainName().equalsIgnoreCase(itemName)) {
                mList.setChecked(true);
            } else {
                mList.setChecked(false);
            }
            notifyDataSetChanged();
        }
    }
}