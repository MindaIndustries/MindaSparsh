package com.minda.sparsh;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minda.sparsh.util.TouchImageView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FlipperAdapter extends BaseAdapter {

    private final AppCompatActivity appCompatActivity;
    private final List<String> strings;
    private int[] drawableIds =
            {
//            R.drawable.image_1,
//                    R.drawable.image_2,
//                    R.drawable.image_3,
//                    R.drawable.image_4,
//                    R.drawable.image_5,
//                    R.drawable.image_6,
//                    R.drawable.image_7,
//                    R.drawable.image_8,
//                    R.drawable.image_9,
//                    R.drawable.image_10,
//                    R.drawable.image_11,
//                    R.drawable.image_12,
//                    R.drawable.image_13,
//                    R.drawable.image_14,
//                    R.drawable.image_15,
//                    R.drawable.image_16,
//                    R.drawable.image_17,
//                    R.drawable.image_18,
//                    R.drawable.image_19,
//                    R.drawable.image_20,
//                    R.drawable.image_21,
//                    R.drawable.image_22,
//                    R.drawable.image_23,
//                    R.drawable.image_24,
//                    R.drawable.image_25,
//                    R.drawable.image_26,
//                    R.drawable.image_27,
//                    R.drawable.image_28,
//                    R.drawable.image_29,
//                    R.drawable.image_30,
//                    R.drawable.image_31,
//                    R.drawable.image_32,
//                    R.drawable.image_33,
//                    R.drawable.image_34,
//                    R.drawable.image_35,
//                    R.drawable.image_36,
//                    R.drawable.image_37,
//                    R.drawable.image_38,
//                    R.drawable.image_39,
//                    R.drawable.image_40,
//                    R.drawable.image_41,
//                    R.drawable.image_42,
//                    R.drawable.image_43,
//                    R.drawable.image_44,
//                    R.drawable.image_45,
//                    R.drawable.image_46,
//                    R.drawable.image_47,
//                    R.drawable.image_48,
//                    R.drawable.image_49,
//                    R.drawable.image_50,
//                    R.drawable.image_51,
//                    R.drawable.image_52,
//                    R.drawable.image_53,
//                    R.drawable.image_54,
//                    R.drawable.image_55,
//                    R.drawable.image_56,
//                    R.drawable.image_57,
//                    R.drawable.image_58,
//                    R.drawable.image_59,
//                    R.drawable.image_60,
//                    R.drawable.image_61,
//                    R.drawable.image_62,
//                    R.drawable.image_63,
//                    R.drawable.image_64,
//
//
//
//
            };

    public FlipperAdapter(AppCompatActivity appCompatActivity, List<String> strings) {
        super();
        this.strings = strings;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return strings.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) appCompatActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_page, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.textView.setText(getItem(position));
        holder.imageView.setImageResource(drawableIds[position]);

        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;
        private TouchImageView imageView;

        public ViewHolder(View v) {
            imageView =  v.findViewById(R.id.image);
//            textView = (TextView) v.findViewById(R.id.text);


        }
    }
}
