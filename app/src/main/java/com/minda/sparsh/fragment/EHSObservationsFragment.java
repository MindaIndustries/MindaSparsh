package com.minda.sparsh.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.EHSObsAdapter;
import com.minda.sparsh.R;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSObsModel;
import com.minda.sparsh.services.EHSServices;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class EHSObservationsFragment extends Fragment {
    @BindView(R.id.observations)
    RecyclerView observations;
    EHSObsAdapter ehsObsAdapter;
    List<EHSObsModel> myObservations = new ArrayList<EHSObsModel>();
    String empCode;
    SharedPreferences myPref;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_list)
    TextView no_list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ehsInitiate = inflater.inflate(R.layout.my_observations, container, false);
        ButterKnife.bind(this, ehsInitiate);
        ehsObsAdapter = new EHSObsAdapter(getActivity(), myObservations);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        observations.setLayoutManager(mLayoutManager);
        observations.setAdapter(ehsObsAdapter);
        myPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");
        getObservations(empCode);
        return ehsInitiate;
    }


    public void getObservations(String empCode) {
        myObservations.clear();
        observations.getRecycledViewPool().clear();
        ehsObsAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        EHSServices ehsServices = new EHSServices();
        ehsServices.getIdentifiedObservations(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {
                if (carotResponse.getStatuscode() == HttpsURLConnection.HTTP_OK) {
                    List<EHSObsModel> list = (List<EHSObsModel>) carotResponse.getData();
                    if (list != null && list.size() > 0) {
                        myObservations.addAll(list);
                        no_list.setVisibility(View.GONE);

                    } else {
                        no_list.setVisibility(View.VISIBLE);
                    }
                    ehsObsAdapter.notifyDataSetChanged();
                } else {
                    if (getActivity() != null && isAdded()) {
                        Toast.makeText(getActivity(), "Oops! Something went wrong.", Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        }, empCode);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //  onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
