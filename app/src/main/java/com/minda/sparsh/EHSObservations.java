package com.minda.sparsh;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EHSObservations extends BaseActivity {
    @BindView(R.id.observations)
    RecyclerView observations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_observations);
        ButterKnife.bind(this);

    }
}
