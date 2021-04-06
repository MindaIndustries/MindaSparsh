package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.minda.sparsh.R;
import com.minda.sparsh.RequestForAccessActivity;
import com.minda.sparsh.model.IAMGetBusinessModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class IAMGetBusinessAdapter extends RecyclerView.Adapter<IAMGetBusinessAdapter.MyViewHolder> {
    private List<IAMGetBusinessModel> list;
    Context context;
    String checkCondition;

    List<IAMGetBusinessModel> selectedBusines = new ArrayList<IAMGetBusinessModel>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton rb;
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            rb = (RadioButton) view.findViewById(R.id.rb);
            checkBox = (CheckBox) view.findViewById(R.id.cb);

        }
    }

    public IAMGetBusinessAdapter(List<IAMGetBusinessModel> viewAppointmentModelList, Context context1, String check) {
        this.list = viewAppointmentModelList;
        this.context = context1;
        this.checkCondition = check;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.domain_cell_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final IAMGetBusinessModel iamGetDomainModel = list.get(position);
        holder.rb.setText(iamGetDomainModel.getBUSINESS());
        holder.rb.setVisibility(View.GONE);
        holder.checkBox.setVisibility(View.VISIBLE);
        holder.checkBox.setText(iamGetDomainModel.getBUSINESS());


      /*  if (checkCondition.equalsIgnoreCase("7") || checkCondition.equalsIgnoreCase("8") || checkCondition.equalsIgnoreCase("48")) {
            holder.rb.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setText(iamGetDomainModel.getBUSINESS());

        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.rb.setVisibility(View.VISIBLE);
            holder.rb.setText(iamGetDomainModel.getBUSINESS());
        }
*/
        /*holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData(iamGetDomainModel.getBUSINESS());
                if (context instanceof RequestForAccessActivity) {
                    ((RequestForAccessActivity) context).hitIAMGetPlantApi(String.valueOf(iamGetDomainModel.getID()),"radioButton");
                }
            }
        });
*/
        if (iamGetDomainModel.isChecked()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (context instanceof RequestForAccessActivity) {
                    if (b) {
                        iamGetDomainModel.setSelected(true);
                        selectedBusines.add(iamGetDomainModel);
                    } else {
                        selectedBusines.remove(iamGetDomainModel);
                        iamGetDomainModel.setSelected(false);
                    }
                    ((RequestForAccessActivity) context).hitIAMGetPlantApi(String.valueOf(iamGetDomainModel.getID()), selectedBusines, "checkBox", b);

                }
            }
        });

       /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof RequestForAccessActivity) {
                    ((RequestForAccessActivity) context).hitIAMGetPlantApi(String.valueOf(iamGetDomainModel.getID()),"checkBox",b);
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
            IAMGetBusinessModel mList = list.get(i);
            if (mList.getBUSINESS().equalsIgnoreCase(itemName)) {
                mList.setChecked(true);
            } else {
                mList.setChecked(false);
            }
            notifyDataSetChanged();
        }
    }
}
