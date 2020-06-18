package com.minda.sparsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.Adapter.EHSObsAdapter;
import com.minda.sparsh.listener.CarotResponse;
import com.minda.sparsh.listener.OnTaskComplete;
import com.minda.sparsh.model.EHSObsModel;
import com.minda.sparsh.services.EHSServices;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EHSObservations extends BaseActivity {
    @BindView(R.id.observations)
    RecyclerView observations;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    EHSObsAdapter ehsObsAdapter;
    List<EHSObsModel> myObservations = new ArrayList<EHSObsModel>();
    String empCode;
    SharedPreferences myPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_observations);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("My Identified Acts");
        ehsObsAdapter = new EHSObsAdapter(EHSObservations.this,myObservations);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(EHSObservations.this, LinearLayoutManager.VERTICAL, false);
        observations.setLayoutManager(mLayoutManager);
        observations.setAdapter(ehsObsAdapter);
        myPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        empCode = myPref.getString("Id", "Id");

        getObservations(empCode);

    }
    public void getObservations(String empCode){
        EHSServices ehsServices = new EHSServices();
        ehsServices.getIdentifiedObservations(new OnTaskComplete() {
            @Override
            public void onTaskComplte(CarotResponse carotResponse) {

                if(carotResponse.getStatuscode()== HttpsURLConnection.HTTP_OK){
                    List<EHSObsModel> list = (List<EHSObsModel>) carotResponse.getData();
                    if(list!=null && list.size()>0){
                        myObservations.addAll(list);
                    }
                    ehsObsAdapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(EHSObservations.this,"Oops! Something went wrong.",Toast.LENGTH_LONG).show();
                }
            }
        },empCode);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
