package com.minda.sparsh;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EHSInitiate extends BaseActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.obs_date_spinner)
    Spinner observationDateSpinner;
    @BindView(R.id.unit_spinner)
    Spinner unitSpinner;
    @BindView(R.id.safety_officer_spinner)
    Spinner safetyOfficerSpinner;
    @BindView(R.id.type_spinner)
    Spinner typeOfObservationSpinner;
    @BindView(R.id.identified_loc_spinner)
    Spinner identifiedLocationSpinner;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.sub_category_spinner)
    Spinner subCategorySpinner;
    @BindView(R.id.description_et)
    EditText descriptionEdit;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.reset)
    Button reset;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ehs_initiate);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        title.setText("EHS Initiate");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
