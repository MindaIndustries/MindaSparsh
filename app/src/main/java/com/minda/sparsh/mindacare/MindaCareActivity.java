package com.minda.sparsh.mindacare;

import android.os.Bundle;

import com.minda.sparsh.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class MindaCareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minda_care);
        ButterKnife.bind(this);
    }
}
