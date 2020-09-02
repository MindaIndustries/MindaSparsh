package com.minda.sparsh.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.minda.sparsh.VisitorManagementActivity;

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
