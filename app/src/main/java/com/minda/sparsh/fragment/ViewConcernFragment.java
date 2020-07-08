package com.minda.sparsh.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minda.sparsh.Adapter.BottomUpConcernAdapter;
import com.minda.sparsh.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewConcernFragment extends Fragment {
    @BindView(R.id.bottomup_rv)
    RecyclerView bottomupRv;
    BottomUpConcernAdapter bottomUpConcernAdapter;
    ArrayList<String> concerns = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewConcern = inflater.inflate(R.layout.view_concern, container, false);
        ButterKnife.bind(this, viewConcern);
        bottomUpConcernAdapter = new BottomUpConcernAdapter(getActivity(), concerns);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bottomupRv.setLayoutManager(mLayoutManager);
        bottomupRv.setAdapter(bottomUpConcernAdapter);
        bottomUpConcernAdapter.notifyDataSetChanged();
        return viewConcern;
    }
}
