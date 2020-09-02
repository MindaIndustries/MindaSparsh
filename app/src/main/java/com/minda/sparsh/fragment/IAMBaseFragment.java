package com.minda.sparsh.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.minda.sparsh.IdentityAccessManagementActivity;

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
