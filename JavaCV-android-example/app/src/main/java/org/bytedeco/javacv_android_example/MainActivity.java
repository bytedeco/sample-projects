package org.bytedeco.javacv_android_example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.bytedeco.javacv_android_example.record.RecordActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CameraXBasic";
    private static final String[] REQUIRED_PERMISSIONS = new String[] { Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
    private static final int REQUEST_CODE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnRecord).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecordActivity.class)));

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }
    private boolean allPermissionsGranted() {
        for(String permission: REQUIRED_PERMISSIONS) {
            Boolean granted = ContextCompat.checkSelfPermission(
                    getBaseContext(), permission) == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                return false;
            }
        }
        return true;
    }
}
