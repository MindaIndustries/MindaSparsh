package com.minda.sparsh.cvp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.minda.sparsh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerVisitReport extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customSpinnerLayout0)
    TextInputLayout customSpinnerLayout0;
    @BindView(R.id.date_of_visit)
    AppCompatAutoCompleteTextView dateOfVisit;
    @BindView(R.id.customSpinnerLayout1)
    TextInputLayout customSpinnerLayout1;
    @BindView(R.id.custom_name)
    AppCompatAutoCompleteTextView custom_name;
    @BindView(R.id.customSpinnerLayout2)
    TextInputLayout customSpinnerLayout2;
    @BindView(R.id.location_spinner)
    AppCompatAutoCompleteTextView locationSpinner;
    @BindView(R.id.customSpinnerLayout3)
    TextInputLayout customSpinnerLayout3;
    @BindView(R.id.plant)
    AppCompatAutoCompleteTextView plant;
    @BindView(R.id.customSpinnerLayout4)
    TextInputLayout customSpinnerLayout4;
    @BindView(R.id.start_time_selector)
    AppCompatAutoCompleteTextView start_time_selector;
    @BindView(R.id.customSpinnerLayout5)
    TextInputLayout customSpinnerLayout5;
    @BindView(R.id.end_time_selector)
    AppCompatAutoCompleteTextView end_time_selector;
    @BindView(R.id.customSpinnerLayout6)
    TextInputLayout customSpinnerLayout6;
    @BindView(R.id.business)
    AppCompatAutoCompleteTextView business;
    @BindView(R.id.customSpinnerLayout7)
    TextInputLayout customSpinnerLayout7;
    @BindView(R.id.agenda_ed)
    AppCompatAutoCompleteTextView agenda_ed;
    @BindView(R.id.customSpinnerLayout8)
    TextInputLayout CustomSpinnerLayout8;
    @BindView(R.id.meeting_location)
    AppCompatAutoCompleteTextView meetingLocation;
    @BindView(R.id.customSpinnerLayout9)
    TextInputLayout CustomSpinnerLayout9;
    @BindView(R.id.mom_ref_no)
    AppCompatAutoCompleteTextView mom_ref_no;
    @BindView(R.id.customSpinnerLayout10)
    TextInputLayout customSpinnerLayout10;
    @BindView(R.id.calendar_weekNo)
    AppCompatAutoCompleteTextView calendarWeekNo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createvisitportal);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("custom Visit Report");


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
