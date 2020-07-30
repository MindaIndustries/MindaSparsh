package com.minda.sparsh.fragment;

import android.content.SharedPreferences;
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
import com.minda.sparsh.model.BottomUpConcern;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class AssignConcernFragment extends Fragment {

    @BindView(R.id.bottomup_rv)
    RecyclerView bottomupRv;
    BottomUpConcernAdapter bottomUpConcernAdapter;
    ArrayList<BottomUpConcern> concerns = new ArrayList<BottomUpConcern>();
    SharedPreferences myPref;
    String empCode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewConcern = inflater.inflate(R.layout.assign_concern, container, false);
        ButterKnife.bind(this, viewConcern);
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);

        empCode = myPref.getString("Id", "Id");

        bottomUpConcernAdapter = new BottomUpConcernAdapter(getActivity(), concerns);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bottomupRv.setLayoutManager(mLayoutManager);
        bottomupRv.setAdapter(bottomUpConcernAdapter);
        bottomUpConcernAdapter.notifyDataSetChanged();

        return viewConcern;
    }
}
