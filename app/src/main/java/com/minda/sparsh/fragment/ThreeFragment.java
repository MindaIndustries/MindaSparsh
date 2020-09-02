package com.minda.sparsh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.BottomUpConcernActivity;
import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.SuggestionBox;
import com.minda.sparsh.VisitorManagementActivity;
import com.minda.sparsh.util.Constant;
import com.minda.sparsh.util.Utility;


public class ThreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    ImageButton jagritiButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ThreeFragment() {
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

        View convertView = inflater.inflate(R.layout.fragment_three, container, false);
        ImageView im_right = (ImageView) convertView.findViewById(R.id.im_right);
        ImageView im_left = (ImageView) convertView.findViewById(R.id.im_left);
        ImageButton Ib_visitor = convertView.findViewById(R.id.Ib_visitor);
        ImageButton bottom_up = (ImageButton) convertView.findViewById(R.id.bottom_up);
        ImageButton suggestion_box = (ImageButton) convertView.findViewById(R.id.suggestion_box);

        ImageButton meetings = convertView.findViewById(R.id.meetings);

        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        im_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true);
            }
        });
        im_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true);
            }
        });
        Ib_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), VisitorManagementActivity.class));
                Intent intent = new Intent(getActivity(), VisitorManagementActivity.class);
                intent.putExtra(Constant.CALL_FROM_ACTIVITY, "fragment");
                startActivity(intent);


            }
        });

        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming Soon!", Toast.LENGTH_LONG).show();
            }
        });

        bottom_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(getActivity())) {
                    Intent in = new Intent(getActivity(), BottomUpConcernActivity.class);
                    startActivity(in);
                }else{
                    Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        suggestion_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), SuggestionBox.class);
                startActivity(in);

            }
        });

        return convertView;
        // Inflate the layout for this fragment

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {


    }
}
