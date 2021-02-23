package com.minda.sparsh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minda.sparsh.MyTaskDetail;
import com.minda.sparsh.R;
import com.minda.sparsh.util.RetrofitClient2;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CCListAdapter extends RecyclerView.Adapter<CCListAdapter.ViewHolder> {
    Context mContext;
    ArrayList<String> arrayList;

    public CCListAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cc_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(""+arrayList.get(position));
        if(mContext instanceof MyTaskDetail){
            holder.delete.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mContext instanceof MyTaskDetail){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitClient2.itHelpAttachment+arrayList.get(position)));
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.email)
        TextView email;
        @BindView(R.id.delete)
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
