package com.minda.sparsh.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.minda.sparsh.R;
import com.minda.sparsh.model.Department_Model;

import java.util.List;

/**
 * Created by admin on 10/12/2017.
 */

public class DepartmentSpinnerAdapter implements SpinnerAdapter {
    private final List<Department_Model> spinnerData;
    private final LayoutInflater mInflater;


    public DepartmentSpinnerAdapter(Context applicationContext, List<Department_Model> data) {
        this.spinnerData = data;
        mInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return spinnerData.size();
    }

    @Override
    public Object getItem(int position) {
        return  spinnerData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // This function called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = mInflater.inflate(R.layout.spinner_cell_view, parent, false);
        TextView mTxt =  row.findViewById(R.id.spinner_txt_title);
        mTxt.setText(spinnerData.get(position).getDEPARTMENT());


        return row;
    }
}
