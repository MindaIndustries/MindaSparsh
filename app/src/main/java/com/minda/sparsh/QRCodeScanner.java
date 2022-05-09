package com.minda.sparsh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

public class QRCodeScanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    String resultStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_scanner);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
          //  Toast.makeText(QRCodeScanner.this, result.getText(), Toast.LENGTH_SHORT).show();
            resultStr = result.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("S.NO.", result.getText().toString());
            setResult(Activity.RESULT_OK, resultIntent);
            onBackPressed();
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}
