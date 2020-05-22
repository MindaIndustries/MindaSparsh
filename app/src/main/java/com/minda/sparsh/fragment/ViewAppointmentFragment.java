package com.minda.sparsh.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minda.sparsh.Adapter.ViewAppointmentAdapter;
import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.model.ViewAppointmentModel;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAppointmentFragment extends BaseFragment {
    private ProgressDialog progress = null;
    private SharedPreferences myPref = null;
    RecyclerView recyclerView;

    public ViewAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_view_appointment, container, false);
        myPref = activity.getSharedPreferences("MyPref", MODE_PRIVATE);

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);

        recyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);
        hitGetVisitorListApi(myPref.getString("Id", "Id"));
        return convertView;
    }


    public void hitGetVisitorListApi(String EmpCode) {
        if (Utility.isOnline(activity)) {
            showProgress();
            Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<ViewAppointmentModel>> response = anInterface.GetVisitorList(RetrofitClient2.CKEY, EmpCode);
            response.enqueue(new Callback<List<ViewAppointmentModel>>() {
                @Override
                public void onResponse(Call<List<ViewAppointmentModel>> call, Response<List<ViewAppointmentModel>> response) {
                    dismissProgress();
                    List<ViewAppointmentModel> visitorResponse = response.body();

                    if (visitorResponse != null) {
                        if (!visitorResponse.get(0).getFirstName().equalsIgnoreCase("")) {
                            ViewAppointmentAdapter mAdapter = new ViewAppointmentAdapter(response.body(), activity);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ViewAppointmentModel>> call, Throwable t) {
                    dismissProgress();
                }
            });
        } else
            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress() {
        try {
            if (progress != null)
                progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dismissProgress() {
        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
