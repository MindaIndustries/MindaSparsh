package com.minda.sparsh.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.minda.sparsh.AbnormalityAddressingActivity;
import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.SheedActivity;
import com.minda.sparsh.model.UserDetail_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManufacturingFragment extends Fragment {

    ImageButton dmwButton, dwm_btn1, ehs;
    private ProgressDialog progress = null;
    SharedPreferences myPref;
    Set<String> loginAccess;
    CardView cardView,cardView1,cardView2;

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
        dmwButton = convertView.findViewById(R.id.dwm_btn);
       // dwm_btn1 = convertView.findViewById(R.id.dwm_btn1);
        //ehs = convertView.findViewById(R.id.ehs);
        cardView = convertView.findViewById(R.id.card_view);
        cardView1 = convertView.findViewById(R.id.card_view1);
      //  cardView2 = convertView.findViewById(R.id.card_view2);



        ImageView im_right = convertView.findViewById(R.id.im_right);
        ImageView im_left = convertView.findViewById(R.id.im_left);
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        myPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        loginAccess = myPref.getStringSet("key", null);

        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        im_right.setOnClickListener(view -> contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true));
        im_left.setOnClickListener(view -> contaxt.viewPager.setCurrentItem(contaxt.getItem(-1), true));

      /*  cardView2.setOnClickListener(v -> {
            Intent in = new Intent(getActivity(), EHS_Home.class);
            startActivity(in);

        });
      */  // Inflate the layout for this fragment

        cardView.setOnClickListener(v -> {

            try {
                if (loginAccess.contains("dwm") || loginAccess.contains("DWM") ||
                        loginAccess.contains("odwm") || loginAccess.contains("ODWM") ||
                        loginAccess.contains("mdwm") || loginAccess.contains("MDWM")) {
                    Intent intent = new Intent(getActivity(), SheedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "You Are Not Authorized", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        cardView1.setOnClickListener(view -> hitGetUserDetail(myPref.getString("Id", "")));
        return convertView;
    }


    public void hitGetUserDetail(String Email) {
        if (Utility.isOnline(getActivity())) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<UserDetail_Model>> response = promotingMyinterface.GetUserDetail(RetrofitClient2.CKEY, Email);
            response.enqueue(new Callback<List<UserDetail_Model>>() {
                @Override
                public void onResponse(@NotNull Call<List<UserDetail_Model>> call, @NotNull Response<List<UserDetail_Model>> response) {
                    showProgress(false);
                    List<UserDetail_Model> userDetail_models = response.body();
                    if (userDetail_models != null && userDetail_models.size() != 0) {
                        DashBoardActivity.time = 1;
                        Intent intent = new Intent(getActivity(), AbnormalityAddressingActivity.class);
                        intent.putExtra("EDOMAIN", userDetail_models.get(0).getDOMAIN());
                        intent.putExtra("EBUSINESS", userDetail_models.get(0).getBUSINESS());
                        intent.putExtra("EPLANT", userDetail_models.get(0).getUNITCODE());
                        intent.putExtra("ADD", true);
                        myPref.edit().putString("EDOMAIN",userDetail_models.get(0).getDOMAIN());
                        myPref.edit().putString("EBUSINESS", userDetail_models.get(0).getBUSINESS());
                        myPref.edit().putString("EPLANT",userDetail_models.get(0).getUNITCODE());
                        myPref.edit().apply();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "You Are Not Authorized", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(@NotNull Call<List<UserDetail_Model>> call, @NotNull Throwable t) {

                    showProgress(false);
                    if (t instanceof IOException) {
                        if(getActivity()!=null && isAdded())
                        Toast.makeText(getActivity(), "Please hold on a moment, the internet connectivity seems to be slow", Toast.LENGTH_LONG).show();
                    }

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
