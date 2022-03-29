package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.CVPDetailModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MOMDiscussionAdapter extends RecyclerView.Adapter<MOMDiscussionAdapter.ViewHolder> {

    Context mContext;
    List<CVPDetailModel.CVPDetailData.OverAllDiscussion> discussions;
    public MOMDiscussionAdapter(Context mContext, List<CVPDetailModel.CVPDetailData.OverAllDiscussion> discussions) {
        this.mContext = mContext;
        this.discussions = discussions;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discussion_row, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.sno.setText(discussions.get(position).getSno());
        holder.business.setText(discussions.get(position).getBusiness().trim());
        holder.plant.setText(discussions.get(position).getPlant().trim());
        holder.sixm.setText(discussions.get(position).getFuction().trim());
        holder.discussion.setText(discussions.get(position).getDiscussion());
        holder.action.setText(discussions.get(position).getAction());
    }

    @Override
    public int getItemCount() {
        return discussions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sno,business,plant,sixm,discussion,action;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            business = itemView.findViewById(R.id.business);
            plant = itemView.findViewById(R.id.plant);
            sixm = itemView.findViewById(R.id.sixm);
            discussion = itemView.findViewById(R.id.discussion);
            action = itemView.findViewById(R.id.action);
        }
    }
}
