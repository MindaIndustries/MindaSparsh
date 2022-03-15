package com.minda.sparsh.cvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.minda.sparsh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAttendees extends AppCompatActivity {
    @BindView(R.id.attendee)
    AppCompatAutoCompleteTextView attendee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addattendees);
        ButterKnife.bind(this);





    }
}
