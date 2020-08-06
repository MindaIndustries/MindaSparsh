package com.minda.sparsh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.IdentityAccessManagementActivity;
import com.minda.sparsh.IdentityAccessManagementActivity1;
import com.minda.sparsh.R;


public class FourFragment extends Fragment {


    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        ImageView im_right = (ImageView) view.findViewById(R.id.im_right);
        ImageView im_left = (ImageView) view.findViewById(R.id.im_left);
        ImageButton im_identity_access_management = (ImageButton) view.findViewById(R.id.im_identity_access_management);
        ImageButton im_change_request_from = (ImageButton) view.findViewById(R.id.im_change_request_from);
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
        // Inflate the layout for this fragment

        im_identity_access_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(contaxt, "Coming Soon..", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), IdentityAccessManagementActivity.class);
                contaxt.startActivity(intent);
            }
        });

        im_change_request_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(contaxt, "Coming Soon!", Toast.LENGTH_SHORT).show();

               // startActivity(new Intent(getActivity(), IdentityAccessManagementActivity1.class));

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

}
