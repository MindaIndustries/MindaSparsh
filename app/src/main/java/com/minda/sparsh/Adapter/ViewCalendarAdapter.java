package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.cvp.CVPPlanCalendar;
import com.minda.sparsh.model.CVPViewCalendarModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewCalendarAdapter extends RecyclerView.Adapter<ViewCalendarAdapter.ViewHolder> {

    Context mContext;
    List<CVPViewCalendarModel.CVPViewCalendarData> list;
    String calendarTypeId;
    OnItemClickListener onItemClickListener;

    public ViewCalendarAdapter(Context mContext, List<CVPViewCalendarModel.CVPViewCalendarData> list, String calendarTypeId) {
        this.mContext = mContext;
        this.list = list;
        this.calendarTypeId = calendarTypeId;
        this.onItemClickListener = onItemClickListener;
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
        holder.heading.setText("Week "+list.get(position).getWeeks());
        if(position>0) {
            if (list.get(position).getWeeks().equals(list.get(position - 1).getWeeks())) {
                holder.heading.setVisibility(View.GONE);
            } else {
                holder.heading.setVisibility(View.VISIBLE);
            }
        }
        else{
            holder.heading.setVisibility(View.VISIBLE);
        }

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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, CVPPlanCalendar.class);
                in.putExtra("MID",list.get(position).getMID());
                in.putExtra("Edit",true);
                in.putExtra("calendartypeId",calendarTypeId);
                mContext.startActivity(in);
            }
        });


        switch (list.get(position).getAttcss()){
            case "Block":
                if(list.get(position).getCustomerName()!=null && list.get(position).getCustomerName().equals("")){
                    holder.cardview.setVisibility(View.GONE);
                    holder.nomeeting.setVisibility(View.VISIBLE);
                }
                holder.legend.setText("Changes Not Permitted");
                holder.cardview.setBackgroundColor(Color.parseColor("#bfbfc1"));
                break;
            case "Plan":
                holder.nomeeting.setVisibility(View.GONE);
                holder.cardview.setVisibility(View.VISIBLE);
                holder.legend.setText("Meeting Planned");
                holder.cardview.setBackgroundColor(Color.parseColor("#00bfff"));
                break;
            case "IComplete":
                holder.nomeeting.setVisibility(View.GONE);
                holder.cardview.setVisibility(View.VISIBLE);
                holder.legend.setText("Meeting Not Done");
                holder.cardview.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            case "Complete":
                holder.nomeeting.setVisibility(View.GONE);
                holder.cardview.setVisibility(View.VISIBLE);
                holder.legend.setText("Planned Meeting Done with MOM");
                holder.cardview.setBackgroundColor(Color.parseColor("#008000"));
                break;
            case "UnPlan":
                holder.nomeeting.setVisibility(View.GONE);
                holder.cardview.setVisibility(View.VISIBLE);
                holder.legend.setText("Unplanned Meeting");
                holder.cardview.setBackgroundColor(Color.parseColor("#ffc0cb"));
                break;
            case "UComplete":
                holder.nomeeting.setVisibility(View.GONE);
                holder.cardview.setVisibility(View.VISIBLE);
                holder.legend.setText("Unplanned Meeting Done with MOM");
                holder.cardview.setBackgroundColor(Color.parseColor("#d82768"));
                break;
            case "":
                holder.legend.setText("Available to Plan");
                holder.nomeeting.setVisibility(View.VISIBLE);
                holder.cardview.setVisibility(View.GONE);
                holder.cardview.setBackgroundColor(Color.parseColor("#717171"));
                break;
        }

    }

    public String getCalendarTypeId() {
        return calendarTypeId;
    }

    public void setCalendarTypeId(String calendarTypeId) {
        this.calendarTypeId = calendarTypeId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView legend,customer_name,meeting_type,days,heading,nomeeting;
        ImageView edit,delete;
        CardView cardview;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            legend = itemView.findViewById(R.id.legend);
            customer_name = itemView.findViewById(R.id.customer_name);
            meeting_type = itemView.findViewById(R.id.meeting_type);
            days = itemView.findViewById(R.id.days);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            heading = itemView.findViewById(R.id.heading);
            nomeeting = itemView.findViewById(R.id.no_meeting);
            cardview = itemView.findViewById(R.id.cardview);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) onItemClickListener.onClick(view, getAdapterPosition());

        }
    }
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }
}
