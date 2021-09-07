package com.minda.sparsh.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minda.sparsh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;


/**
 * Created by Admin on 20-Nov-17.
 */

public class SlidingImage_Adapter extends PagerAdapter {
    private List<String> IMAGES;
    private final LayoutInflater inflater;
    private final Context context;
    private final int size;
    private final boolean isImageAvailable;

    public SlidingImage_Adapter(Context context, List<String> IMAGES) {
        this.context = context;
        size = IMAGES.size();
        this.IMAGES = IMAGES;
        isImageAvailable = false;
        inflater = LayoutInflater.from(context);
    }

    public SlidingImage_Adapter(Context context, int count) {
        this.context = context;
        this.size = count;
        //  this.IMAGES = IMAGES;
        isImageAvailable = false;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

        try {
            String input = IMAGES.get(position);
            input = input.replace(" ", "%20");
            Uri imgUri = Uri.parse("http://88.198.45.166/MindaSparsh/" + input);


            Picasso.with(context)
                    .load(imgUri)
                    .into(imageView);


        } catch (Exception e) {
            e.printStackTrace();
        }

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}

