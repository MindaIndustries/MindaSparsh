package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.DWMPlant;
import com.minda.sparsh.R;
import com.minda.sparsh.model.DWMPlantModel;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DWMPlantAdapter extends RecyclerView.Adapter<DWMPlantAdapter.ViewHolder> {
    Context mContext;
    List<DWMPlantModel> dataList;

    public DWMPlantAdapter(Context mContext, List<DWMPlantModel> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dwm_plant_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.itemView.setMinimumHeight(60 * (dataList.get(position).getSubCategory().size()));
        holder.itemView.setDrawingCacheEnabled(true);
        holder.sno.setText("" + (position + 1));
        holder.chckpt.setText(dataList.get(position).getCatName());
        DWMChildRecyclerViewAdapter dwmChildRecyclerViewAdapter = new DWMChildRecyclerViewAdapter(mContext, dataList.get(position).getSubCategory());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.sub_rv.setLayoutManager(mLayoutManager);
        holder.sub_rv.setAdapter(dwmChildRecyclerViewAdapter);
        ViewGroup.LayoutParams params = holder.parent_main.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = 150 *dataList.get(position).getSubCategory().size();
       // params.width = 100;
        holder.parent_main.setLayoutParams(params);
       // holder.sub_rv.setItemViewCacheSize(dataList.get(position).getSubCategory().size());
     //   holder.sub_rv.setDrawingCacheEnabled(true);
    //    holder.sub_rv.getLayoutParams().height = 60 * (dataList.get(position).getSubCategory().size());
        holder.sub_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sno)
        TextView sno;
        @BindView(R.id.chckpt)
        TextView chckpt;
        @BindView(R.id.sub_rv)
        RecyclerView sub_rv;
        @BindView(R.id.parent_main)
        LinearLayout parent_main;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
