package com.minda.sparsh.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minda.sparsh.Adapter.BottomUpConcernAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.BottomUpConcern;
import com.minda.sparsh.services.BottomUpConcernServices;
import com.minda.sparsh.util.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
        if (Utility.isOnline(getActivity())) {
            getAssignedConcern();
        } else {
            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
        }
        return viewConcern;
    }

    public void getAssignedConcern() {
        concerns.clear();
        bottomupRv.getRecycledViewPool().clear();
        bottomUpConcernAdapter.notifyDataSetChanged();

        BottomUpConcernServices bottomUpConcernServices = new BottomUpConcernServices();
        bottomUpConcernServices.getAssignedConcerns(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<BottomUpConcern> list = (List<BottomUpConcern>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        concerns.addAll(list);
                    }
                }
                bottomUpConcernAdapter.notifyDataSetChanged();
            }
        }, empCode);
    }
}
