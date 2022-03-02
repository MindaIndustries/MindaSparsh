package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.CVPViewCalendarModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewCalendarAdapter extends RecyclerView.Adapter<ViewCalendarAdapter.ViewHolder> {

    Context mContext;
    List<CVPViewCalendarModel.CVPViewCalendarData> list;

    public ViewCalendarAdapter(Context mContext, List<CVPViewCalendarModel.CVPViewCalendarData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cvp_view_row, parent, false);



        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.legend.setText(list.get(position).getAttcss());
        holder.customer_name.setText(list.get(position).getCustomerName());
        holder.meeting_type.setText(list.get(position).getMeetingType());
        holder.days.setText(list.get(position).getRangeDate());

        if(list.get(position).getIsDelete()==0){
            holder.delete.setVisibility(View.GONE);
        }
        else{
            holder.delete.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getIsEdit()==0){
            holder.edit.setVisibility(View.GONE);
        }
        else{
            holder.edit.setVisibility(View.VISIBLE);
        }

        switch (list.get(position).getAttcss()){
            case "Block":
                break;
            case "Plan":
                break;
            case "IComplete":
                break;


        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView legend,customer_name,meeting_type,days;
        ImageView edit,delete;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            legend = itemView.findViewById(R.id.legend);
            customer_name = itemView.findViewById(R.id.customer_name);
            meeting_type = itemView.findViewById(R.id.meeting_type);
            days = itemView.findViewById(R.id.days);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
