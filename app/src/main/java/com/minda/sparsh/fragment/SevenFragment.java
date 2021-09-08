package com.minda.sparsh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.R;
import androidx.fragment.app.Fragment;


public class SevenFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SevenFragment() {
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
        View view = inflater.inflate(R.layout.fragment_seven, container, false);
        ImageView im_right =  view.findViewById(R.id.im_right);
        ImageView im_left =  view.findViewById(R.id.im_left);
        ImageView dwm_btn =  view.findViewById(R.id.dwm_btn);

        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        im_right.setOnClickListener(view1 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true));
        im_left.setOnClickListener(view12 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true));
//        dwm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(getActivity(), AbnormalityAddressingActivity.class);
//                startActivity(intent);
//            }
//        });
        // Inflate the layout for this fragment
        return view;
    }
}
