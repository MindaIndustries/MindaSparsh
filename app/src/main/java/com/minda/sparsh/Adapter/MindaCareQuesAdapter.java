package com.minda.sparsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.QuesResponse;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MindaCareQuesAdapter extends RecyclerView.Adapter<MindaCareQuesAdapter.ViewHolder> {


    Context mContext;
    List<QuesResponse> list;
    HashMap<String, List<String>> options;


    public MindaCareQuesAdapter(Context mContext, List<QuesResponse> list, HashMap<String, List<String>> options) {
        this.mContext = mContext;
        this.list = list;
        this.options = options;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.minda_care_qn_row, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.qn.setText(list.get(position).getQues());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.qn)
        TextView qn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
