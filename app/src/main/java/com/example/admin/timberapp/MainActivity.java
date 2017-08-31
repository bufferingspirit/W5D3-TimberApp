package com.example.admin.timberapp;

import android.Manifest;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.urbanairship.UAirship;

import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 10;
    private ZXingScannerView scannerView;
    TextView tvLeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.tag("LifeCycles");
        Timber.d("Activity Created");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);

        tvLeak = (TextView) findViewById(R.id.tvLeak);
        Leak leak = new Leak();
        leak.execute();

        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                String resultCode = result.getText();
                Toast.makeText(MainActivity.this , resultCode, Toast.LENGTH_SHORT).show();
                Timber.d("Picture Taken");
                setContentView(R.layout.activity_main);
                scannerView.stopCamera();
            }
        });
    }
    private class Leak extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvLeak.setText("Done " + new Date().getTime());
        }

    }

    @Override
    protected  void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    public void scanCode(View view){

        setContentView(scannerView);
        scannerView.startCamera();
    }
}
