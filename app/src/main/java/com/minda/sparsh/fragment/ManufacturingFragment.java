package com.minda.sparsh.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.AbnormalityAddressingActivity;
import com.minda.sparsh.DWMActivity;

import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.EHS_Home;
import com.minda.sparsh.Interface;
import com.minda.sparsh.LoginActivity;
import com.minda.sparsh.R;
import com.minda.sparsh.SheedActivity;
import com.minda.sparsh.model.UserDetail_Model;
import com.minda.sparsh.util.AbnormalityDashboard;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManufacturingFragment extends Fragment {

    ImageButton dmwButton, dwm_btn1,ehs;
    private ProgressDialog progress = null;
    SharedPreferences myPref;
    Set<String> loginAccess;

    public ManufacturingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_manufacturing, container, false);
        dmwButton = (ImageButton) convertView.findViewById(R.id.dwm_btn);
        dwm_btn1 = (ImageButton) convertView.findViewById(R.id.dwm_btn1);
        ehs = convertView.findViewById(R.id.ehs);
        ImageView im_right = (ImageView) convertView.findViewById(R.id.im_right);
        ImageView im_left = (ImageView) convertView.findViewById(R.id.im_left);
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        myPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        loginAccess = myPref.getStringSet("key", null);

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

        ehs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(), EHS_Home.class);
                startActivity(in);

            }
        });
        // Inflate the layout for this fragment

        dmwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginAccess.contains("dwm")||loginAccess.contains("DWM")||
                        loginAccess.contains("odwm") ||loginAccess.contains("ODWM")||
                        loginAccess.contains("mdwm")||loginAccess.contains("MDWM")) {
                    Intent intent = new Intent(getActivity(), SheedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "You Are Not Authorized", Toast.LENGTH_LONG).show();
                }




            }
        });
        dwm_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    hitGetUserDetail(myPref.getString("EmainId", ""));



            }
        });
        return convertView;
    }


    public void hitGetUserDetail(String Email) {
        if (Utility.isOnline(getActivity())) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<UserDetail_Model>> response = promotingMyinterface.GetUserDetail(RetrofitClient2.CKEY, Email);
            response.enqueue(new Callback<List<UserDetail_Model>>() {
                @Override
                public void onResponse(Call<List<UserDetail_Model>> call, Response<List<UserDetail_Model>> response) {
                    showProgress(false);
                    List<UserDetail_Model> userDetail_models = response.body();
                    if (userDetail_models != null && userDetail_models.size() != 0) {
                        DashBoardActivity.time = 1;
                        Intent intent = new Intent(getActivity(), AbnormalityAddressingActivity.class);
                        intent.putExtra("EDOMAIN",userDetail_models.get(0).getDOMAIN());
                        intent.putExtra("EBUSINESS",userDetail_models.get(0).getBUSINESS());
                        intent.putExtra("EPLANT",userDetail_models.get(0).getUNITCODE());
                        intent.putExtra("ADD", true);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You Are Not Authorized", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<List<UserDetail_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(getActivity(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
    }

    private void showProgress(boolean b) {
        try {
            if (b)
                progress.show();
            else
                progress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
