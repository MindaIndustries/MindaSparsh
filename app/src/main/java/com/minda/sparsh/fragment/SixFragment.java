package com.minda.sparsh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.R;

import androidx.fragment.app.Fragment;


public class SixFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SixFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_six, container, false);
//        ImageView im_right=(ImageView) view.findViewById(R.id.im_right);
        ImageView im_left = (ImageView) view.findViewById(R.id.im_left);
        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
//        im_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true);
//            }
//        });
        im_left.setOnClickListener(view1 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true));
        // Inflate the layout for this fragment
        return view;
    }


}
