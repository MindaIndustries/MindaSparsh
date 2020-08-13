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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minda.sparsh.Adapter.BottomUpConcernAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.services.BottomUpConcernServices;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ViewConcernFragment extends Fragment {
    @BindView(R.id.bottomup_rv)
    RecyclerView bottomupRv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    BottomUpConcernAdapter bottomUpConcernAdapter;
    ArrayList<BottomUpConcern> concerns = new ArrayList<BottomUpConcern>();
    SharedPreferences myPref;
    String empCode;
    @BindView(R.id.no_list)
    TextView no_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewConcern = inflater.inflate(R.layout.view_concern, container, false);
        ButterKnife.bind(this, viewConcern);
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        bottomUpConcernAdapter = new BottomUpConcernAdapter(getActivity(), concerns);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bottomupRv.setLayoutManager(mLayoutManager);
        bottomupRv.setAdapter(bottomUpConcernAdapter);
        bottomUpConcernAdapter.notifyDataSetChanged();
        getConcerns();

        return viewConcern;
    }


    public void getConcerns(){
        progressBar.setVisibility(View.VISIBLE);

        concerns.clear();
        bottomupRv.getRecycledViewPool().clear();
        bottomUpConcernAdapter.notifyDataSetChanged();

        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.getUserConcerns(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                    List<BottomUpConcern> list = (List<BottomUpConcern>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        concerns.addAll(list);
                        no_list.setVisibility(View.GONE);

                    }
                    else{
                        no_list.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    no_list.setVisibility(View.VISIBLE);
                }
                bottomUpConcernAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        },empCode);
    }
}
