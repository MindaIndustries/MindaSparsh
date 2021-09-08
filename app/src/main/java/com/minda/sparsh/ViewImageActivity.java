package com.minda.sparsh;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minda.sparsh.model.GetAbnormalityImage_Model;
import com.minda.sparsh.util.RetrofitClient2;
import com.minda.sparsh.util.Utility;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewImageActivity extends AppCompatActivity {
    ImageView Im_before, Im_after, im_back;
    int AbnormalID;
    private ProgressDialog progress = null;
    TextView tv_discription, tv_action;
    LinearLayout lay_afterimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Im_before =  findViewById(R.id.Im_before);
        Im_after =  findViewById(R.id.Im_after);
        im_back =  findViewById(R.id.im_back);
        tv_discription =  findViewById(R.id.tv_discription);
        lay_afterimage =  findViewById(R.id.lay_afterimage);
        tv_action =  findViewById(R.id.tv_action);

        progress = new ProgressDialog(this);
        progress.setMessage("Please wait...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        if (getIntent().getExtras() != null) {
            AbnormalID = getIntent().getIntExtra("ID", 0);
        }
        hitgetimageApi(AbnormalID);
        im_back.setOnClickListener(view -> finish());
    }

    public void hitgetimageApi(int id) {
        if (Utility.isOnline(ViewImageActivity.this)) {
            showProgress(true);
            Interface promotingMyinterface = RetrofitClient2.getClient().create(Interface.class);
            Call<List<GetAbnormalityImage_Model>> response = promotingMyinterface.GetAbnormalityImage(RetrofitClient2.CKEY, id);
            response.enqueue(new Callback<List<GetAbnormalityImage_Model>>() {
                @Override
                public void onResponse(Call<List<GetAbnormalityImage_Model>> call, Response<List<GetAbnormalityImage_Model>> response) {
                    showProgress(false);
                    List<GetAbnormalityImage_Model> images = response.body();

                    if (images != null) {
                        Im_before.setImageBitmap(StringToBitMap(images.get(0).getImagePath().replace(" ", "+")));
                        tv_discription.setText(images.get(0).getDescription());
                    }
                    if (images.get(0).getImagePathAfter() != null && images.get(0).getImagePathAfter().length() != 0) {
                        Im_after.setImageBitmap(StringToBitMap(images.get(0).getImagePathAfter().replace(" ", "+")));
                        if (images.get(0).getAction() != null) {
                            tv_action.setText(images.get(0).getAction());
                        }
                    } else {
                        lay_afterimage.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<List<GetAbnormalityImage_Model>> call, Throwable t) {

                    showProgress(false);

                }
            });
        } else
            Toast.makeText(ViewImageActivity.this, "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
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

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
