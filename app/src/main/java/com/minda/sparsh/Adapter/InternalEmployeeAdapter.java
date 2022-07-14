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
import com.minda.sparsh.model.AutoNameModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InternalEmployeeAdapter extends RecyclerView.Adapter<InternalEmployeeAdapter.ViewHolder>  {
    Context mContext;
    ArrayList<AutoNameModel.AutoNameModelData>list;
    private OnItemClickListener clickListener;


    public InternalEmployeeAdapter(Context mContext, ArrayList<AutoNameModel.AutoNameModelData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.internal_attendee_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.attendee.setText(""+list.get(position).getValue());

    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.attendee)
        TextView attendee;
        @BindView(R.id.cancel)
        ImageView cancel;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cancel.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public interface OnItemClickListener {
         void onClick(View view, int position);
    }
}
