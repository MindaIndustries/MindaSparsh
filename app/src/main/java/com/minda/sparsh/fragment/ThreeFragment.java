package com.minda.sparsh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.BottomUpConcernActivity;
import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.EHS_Home;
import com.minda.sparsh.MeetingRoomActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.SuggestionBox;
import com.minda.sparsh.VisitorManagementActivity;
import com.minda.sparsh.util.Constant;
import com.minda.sparsh.util.Utility;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


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
        ImageView im_right =  convertView.findViewById(R.id.im_right);
        ImageView im_left =  convertView.findViewById(R.id.im_left);
        ImageButton Ib_visitor = convertView.findViewById(R.id.Ib_visitor);
     /*   ImageButton bottom_up =  convertView.findViewById(R.id.bottom_up);
        ImageButton suggestion_box =  convertView.findViewById(R.id.suggestion_box);

        ImageButton meetings = convertView.findViewById(R.id.meetings);
*/
        CardView cardView = convertView.findViewById(R.id.card_view);
        CardView cardView1 = convertView.findViewById(R.id.card_view1);
        CardView cardView2 = convertView.findViewById(R.id.card_view2);
        CardView cardView3 = convertView.findViewById(R.id.card_view3);
        CardView cardView4 = convertView.findViewById(R.id.card_view4);
        CardView cardView6 = convertView.findViewById(R.id.card_view6);


        cardView4.setOnClickListener(v -> {
            Intent in = new Intent(getActivity(), EHS_Home.class);
            startActivity(in);

        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        im_right.setOnClickListener(view -> contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true));
        im_left.setOnClickListener(view -> contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true));
        cardView3.setOnClickListener(view -> {
//                startActivity(new Intent(getActivity(), VisitorManagementActivity.class));
            Intent intent = new Intent(getActivity(), VisitorManagementActivity.class);
            intent.putExtra(Constant.CALL_FROM_ACTIVITY, "fragment");
            startActivity(intent);


        });

        cardView1.setOnClickListener(v -> {
            Intent in =new Intent(getActivity(),MeetingRoomActivity.class);
            startActivity(in);
        });

        cardView.setOnClickListener(v -> {
            if (Utility.isOnline(getActivity())) {
                Intent in = new Intent(getActivity(), BottomUpConcernActivity.class);
                startActivity(in);
            } else {
                Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
            }
        });

        cardView2.setOnClickListener(v -> {
            Intent in = new Intent(getActivity(), SuggestionBox.class);
            startActivity(in);

        });
        return convertView;
    }
}
