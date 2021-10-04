package com.minda.sparsh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minda.sparsh.DashBoardActivity;
import com.minda.sparsh.Interface;
import com.minda.sparsh.R;
import com.minda.sparsh.model.AboutUsDetails;
import com.minda.sparsh.model.DashboardImagesModel;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TwoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


  /*  @BindView(R.id.im_web)
    ImageView im_web;
  */  @BindView(R.id.im_right)
    ImageView im_right;
    @BindView(R.id.trnover_value)
    TextView trnover_value;
    @BindView(R.id.grp)
    TextView grp;
   /* @BindView(R.id.fy)
    TextView fy;
   */ @BindView(R.id.plants_glbl_value)
    TextView plants_glbl_value;
    @BindView(R.id.plants_glbl)
    TextView plants_glbl;
    @BindView(R.id.jv_value)
    TextView jv_value;
    @BindView(R.id.jv)
    TextView jv;
    @BindView(R.id.acquis_value)
    TextView acquis_value;
    @BindView(R.id.acquis)
    TextView acquis;
    @BindView(R.id.rnd_center_value)
    TextView rnd_center_value;
    @BindView(R.id.rnd_cntr)
    TextView rnd_cntr;
    @BindView(R.id.product_lines_value)
    TextView product_lines_value;
    @BindView(R.id.prd_lines)
    TextView prd_lines;
    @BindView(R.id.product_patents_value)
    TextView product_patents_value;
    @BindView(R.id.prd_ptnt)
    TextView prd_ptnt;
    @BindView(R.id.design_reg_value)
    TextView design_reg_value;
    @BindView(R.id.design_reg)
    TextView design_reg;
    @BindView(R.id.carouselView)
    CarouselView carouselView;
    ArrayList<DashboardImagesModel> banners = new ArrayList<>();


    public TwoFragment() {
        // Required empty public constructor
    }

    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
     //   ImageView im_web = (ImageView) view.findViewById(R.id.im_web);
      //  ImageView im_right = (ImageView) view.findViewById(R.id.im_right);
        ButterKnife.bind(this,view);
        final DashBoardActivity contaxt = (DashBoardActivity) getActivity();
        if(Utility.isOnline(getActivity())) {
            getAboutUsInfo();
            getDahboardImages();
        }


        im_right.setOnClickListener(view1 -> contaxt.viewPager.setCurrentItem(contaxt.getItem(+1), true));


        /*im_web.setOnClickListener(view12 -> {
            String url = "https://unominda.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });
        */// Inflate the layout for this fragment
        return view;
    }


    public void getAboutUsInfo(){
        Interface anInterface = RetrofitClient2.getClient().create(Interface.class);
        Call<List<AboutUsDetails>> call = anInterface.getAboutDetails();
        call.enqueue(new Callback<List<AboutUsDetails>>() {
            @Override
            public void onResponse(@NotNull Call<List<AboutUsDetails>> call, @NotNull Response<List<AboutUsDetails>> response) {

                if(response.code()== HttpsURLConnection.HTTP_OK){
                    List<AboutUsDetails> list = response.body();
                    if(list!=null && list.size()>0){
                        if(list.get(0)!=null){
                            AboutUsDetails aboutUsDetails = list.get(0);

                           /* if(aboutUsDetails.getFY()!=null && aboutUsDetails.getFY().length()>0) {
                                fy.setText(aboutUsDetails.getFY());
                            }
                           */ if(aboutUsDetails.getGrpTrnOver()!=null && aboutUsDetails.getGrpTrnOver().length()>0){
                                trnover_value.setText(aboutUsDetails.getGrpTrnOver()+" ");
                            }
                            if(aboutUsDetails.getPlantsGlobally()!=null && aboutUsDetails.getPlantsGlobally().length()>0){
                                plants_glbl_value.setText(aboutUsDetails.getPlantsGlobally()+" ");
                            }
                            if(aboutUsDetails.getJointVenture()!=null && aboutUsDetails.getJointVenture().length()>0){
                                jv_value.setText(aboutUsDetails.getJointVenture()+" ");
                            }
                            if(aboutUsDetails.getAcquisition()!=null && aboutUsDetails.getAcquisition().length()>0){
                                acquis_value.setText(aboutUsDetails.getAcquisition()+" ");
                            }
                            if(aboutUsDetails.getRDCenter()!=null && aboutUsDetails.getRDCenter().length()>0){
                                rnd_center_value.setText(aboutUsDetails.getRDCenter());
                            }
                            if(aboutUsDetails.getProductLine()!=null && aboutUsDetails.getProductLine().length()>0){
                                product_lines_value.setText(aboutUsDetails.getProductLine());
                            }
                            if(aboutUsDetails.getProductPatent()!=null && aboutUsDetails.getProductPatent().length()>0){
                                product_patents_value.setText(aboutUsDetails.getProductPatent());
                            }
                            if(aboutUsDetails.getDesignRegsistration()!=null && aboutUsDetails.getDesignRegsistration().length()>0){
                                design_reg_value.setText(aboutUsDetails.getDesignRegsistration());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<AboutUsDetails>> call, @NotNull Throwable t) {
                System.out.println("Api failed");
            }
        });

    }
    public void getDahboardImages(){
        banners.clear();
        Interface anInterface = RetrofitClient2.createServiceDashboardImages(Interface.class);
        Call<List<DashboardImagesModel>> call = anInterface.getDashboardImages();
        call.enqueue(new Callback<List<DashboardImagesModel>>() {
            @Override
            public void onResponse(Call<List<DashboardImagesModel>> call, Response<List<DashboardImagesModel>> response) {
                if(response.code()== HttpsURLConnection.HTTP_OK) {
                    List<DashboardImagesModel> list = response.body();
                    banners.addAll(list);
                    carouselView.setImageListener(imageListener);
                    carouselView.setPageCount(banners.size());
                    carouselView.setIndicatorVisibility(View.GONE);
                    carouselView.setImageClickListener(imageClickListener);
                }
                }

            @Override
            public void onFailure(Call<List<DashboardImagesModel>> call, Throwable t) {
                System.out.println("Api failed");
            }
        });

    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getActivity()).load(banners.get(position).getImgsrc()).into(imageView);
        }
    };

    ImageClickListener imageClickListener = new ImageClickListener() {
        @Override
        public void onClick(int position) {
            // to be added
        }
    };

}
