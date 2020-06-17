package com.minda.sparsh.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minda.sparsh.R;
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
