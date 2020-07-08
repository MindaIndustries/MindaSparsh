package com.minda.sparsh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.MainActivity;

import com.minda.sparsh.R;


public class OneFragment extends Fragment {

    //    ImageButton dmwButton;
    ImageButton jagritiButton;


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ImageView im_right = (ImageView) view.findViewById(R.id.im_right);
        jagritiButton = (ImageButton) view.findViewById(R.id.jagriti_btn);
        ImageView im_left = (ImageView) view.findViewById(R.id.im_left);
        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        ImageButton ib_samwad = (ImageButton) view.findViewById(R.id.ib_samwad);
        ImageButton ib_engg = (ImageButton) view.findViewById(R.id.ib_engg);
        ImageButton ib_manufacturing = (ImageButton) view.findViewById(R.id.ib_manufacturing);
        im_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true);
            }
        });
        im_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true);
            }
        });
        jagritiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("pdfType", "Jagriti");
                startActivity(intent);
            }
        });
        ib_samwad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("pdfType", "Samwad");
                startActivity(intent);
            }
        });
        ib_engg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("pdfType", "Engineering");
                startActivity(intent);
            }
        });
        ib_manufacturing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("pdfType", "Manufacturing");
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
