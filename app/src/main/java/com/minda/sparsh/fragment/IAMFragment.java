package com.minda.sparsh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minda.sparsh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IAMFragment extends IAMBaseFragment {


    public IAMFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iam, container, false);
    }

}
