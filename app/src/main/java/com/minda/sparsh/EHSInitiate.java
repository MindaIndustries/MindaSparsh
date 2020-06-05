package com.minda.sparsh;

import android.os.Bundle;

import butterknife.ButterKnife;

public class EHSInitiate extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ehs_initiate);
        ButterKnife.bind(this);
    }
}
