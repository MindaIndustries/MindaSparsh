package com.minda.sparsh.fragment;


import android.os.Bundle;

import com.minda.sparsh.VisitorManagementActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    public VisitorManagementActivity activity;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((VisitorManagementActivity) getActivity());
    }
}
