package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.DWMPlantModel;
import com.minda.sparsh.model.MultiTypeModel;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DWMMultiViewAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<DWMPlantModel.SubCategory> subCategoryList;

    public DWMMultiViewAdapter(Context mContext, List<DWMPlantModel.SubCategory> subCategory) {
        this.mContext = mContext;
        subCategoryList = subCategory;
    }


/*
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_rv_row, parent, false);
        return new ViewHolder(itemView);
    }
*/

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MultiTypeModel.mainType:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_rv_row, parent, false);
                return new MainViewHolder(view);
            case MultiTypeModel.bottomType:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_dwm_layout, parent, false);
                return new BottomViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int i) {


    }

    /*  @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
            holder.chkpt1_id.setText(subCategoryList.get(position).getSno());
            holder.chkpt2_id.setText(subCategoryList.get(position).getSubCat());
            holder.frq.setText(subCategoryList.get(position).getFeq());
            holder.hr.setText(subCategoryList.get(position).getHrs());
            //holder.m.setText(subCategoryList.get(position).get);
            holder.q.setText(subCategoryList.get(position).getQ());
            holder.hy.setText(subCategoryList.get(position).getHY());
            holder.total_time.setText(subCategoryList.get(position).getTotaltime());
            holder.time.setText(subCategoryList.get(position).getVal());
            holder.remarks.setText(subCategoryList.get(position).getRemark());
            if(subCategoryList.get(position).getSubSubCatName()!=null && subCategoryList.get(position).getSubSubCatName().length()>0){
                holder.sub_sub_cat.setVisibility(View.VISIBLE);
                holder.sub_sub.setText(subCategoryList.get(position).getSubSubCatName());
            }
            else{
                holder.sub_sub_cat.setVisibility(View.GONE);
            }
        }
    */
    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chkpt1_id)
        TextView chkpt1_id;
        @BindView(R.id.chkpt2_id)
        TextView chkpt2_id;
        @BindView(R.id.frq)
        TextView frq;
        @BindView(R.id.hr)
        TextView hr;
        @BindView(R.id.m)
        TextView m;
        @BindView(R.id.q)
        TextView q;
        @BindView(R.id.hy)
        TextView hy;
        @BindView(R.id.total_time)
        TextView total_time;
        @BindView(R.id.remarks)
        EditText remarks;
        @BindView(R.id.time)
        EditText time;
       /* @BindView(R.id.sub_sub_cat)
        LinearLayout sub_sub_cat;
        @BindView(R.id.sub_sub)
        TextView sub_sub;
*/
        public MainViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cp)
        TextView cp;
        @BindView(R.id.frq1)
        TextView frq1;
        @BindView(R.id.hr1)
        TextView hr1;
        @BindView(R.id.m1)
        TextView m1;
        @BindView(R.id.q1)
        TextView q1;
        @BindView(R.id.hy1)
        TextView hy1;
        @BindView(R.id.total_time1)
        TextView total_time1;
        @BindView(R.id.total_time_value)
        TextView total_time_value;



        public BottomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
