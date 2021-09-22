package com.minda.sparsh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.ITHelpDeskHome;
import com.minda.sparsh.IdentityAccessManagementActivity;
import com.minda.sparsh.R;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class FourFragment extends Fragment {


    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        ImageView im_right = view.findViewById(R.id.im_right);
        ImageView im_left = view.findViewById(R.id.im_left);
        CardView card_view = view.findViewById(R.id.card_view);
       // ImageButton im_identity_access_management = view.findViewById(R.id.im_identity_access_management);
        ImageButton im_change_request_from = view.findViewById(R.id.im_change_request_from);
        ImageButton it_help_desk = view.findViewById(R.id.it_help_desk);
        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        im_right.setOnClickListener(view1 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true));
        im_left.setOnClickListener(view12 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true));
        // Inflate the layout for this fragment

        card_view.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), IdentityAccessManagementActivity.class);
            contaxt.startActivity(intent);
        });
        im_change_request_from.setOnClickListener(view14 -> {
            Toast.makeText(contaxt, "Coming Soon!", Toast.LENGTH_SHORT).show();

            // startActivity(new Intent(getActivity(), IdentityAccessManagementActivity1.class));

        });

        it_help_desk.setOnClickListener(view15 -> {

            Intent intent = new Intent(getActivity(), ITHelpDeskHome.class);
            contaxt.startActivity(intent);

        });
        return view;

    }

}
