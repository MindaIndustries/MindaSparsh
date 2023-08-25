package com.minda.sparsh.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DWMChildRecyclerViewAdapter extends RecyclerView.Adapter<DWMChildRecyclerViewAdapter.ViewHolder> {

    Context mContext;
    List<DWMPlantModel.SubCategory> subCategoryList;
    MyLeaveRequestAdapter.OnItemClickListener onItemClickListener;


    public DWMChildRecyclerViewAdapter(Context mContext, List<DWMPlantModel.SubCategory> subCategory) {
        this.mContext = mContext;
        subCategoryList = subCategory;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_rv_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
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

        holder.time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subCategoryList.get(position).setVal(holder.time.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        holder.remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subCategoryList.get(position).setRemark(holder.time.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.sub_sub_cat)
        LinearLayout sub_sub_cat;
        @BindView(R.id.sub_sub)
        TextView sub_sub;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);



        }
    }

    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
    public void setClickListener(MyLeaveRequestAdapter.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
}
