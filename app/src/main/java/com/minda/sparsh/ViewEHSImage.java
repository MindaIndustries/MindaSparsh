package com.minda.sparsh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.minda.sparsh.customview.TouchImageView;
import com.minda.sparsh.util.RetrofitClient2;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewEHSImage extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.docImage)
    TouchImageView docImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ehs_img);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText(getResources().getString(R.string.environment_healthnSafety));

        if (getIntent().getStringExtra("attachment") != null) {
            String attachment = getIntent().getStringExtra("attachment");
            if (attachment != null) {
                Glide.with(ViewEHSImage.this).load(RetrofitClient2.ehs_img + attachment).apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .fitCenter()
                        .dontTransform())
                        .into(docImage);

            }
        } else {
            if (getIntent().getByteArrayExtra("bitmap") != null) {
                byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                docImage.setImageBitmap(bmp);
            }
        }
        docImage.setMaxZoom(4f);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
