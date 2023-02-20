package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.HolidayListModel;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.ViewHolder> {
    Context mContext;
    ArrayList<HolidayListModel> holidays;

    public HolidayAdapter(Context mContext, ArrayList<HolidayListModel> holidays) {
        this.mContext = mContext;
        this.holidays = holidays;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holiday_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.dt.setText(holidays.get(position).getDate());
        holder.day.setText(holidays.get(position).getDay());
        holder.holiday.setText(holidays.get(position).getDesc());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.dt)
        TextView dt;
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.holiday)
        TextView holiday;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
