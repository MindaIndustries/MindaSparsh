package com.minda.sparsh;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingRoomActivity extends AppCompatActivity {
    @BindView(R.id.roomsList)
    RecyclerView roomsList;
    @BindView(R.id.customerSpinnerLayout)
    TextInputLayout customSpinnerLayout;
    @BindView(R.id.city_spinner)
    AppCompatAutoCompleteTextView citySpinner;
    @BindView(R.id.customerSpinnerLayout1)
    TextInputLayout customSpinnerLayout1;
    @BindView(R.id.unit_spinner)
    AppCompatAutoCompleteTextView unitSpinner;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_rooms);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("Book Meeting Room");



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
