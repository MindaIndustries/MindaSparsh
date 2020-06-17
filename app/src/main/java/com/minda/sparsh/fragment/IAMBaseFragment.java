package com.minda.sparsh.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minda.sparsh.IdentityAccessManagementActivity;
import com.minda.sparsh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IAMBaseFragment extends Fragment {
    public IdentityAccessManagementActivity activity;

    public IAMBaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ((IdentityAccessManagementActivity) getActivity());
    }
}
