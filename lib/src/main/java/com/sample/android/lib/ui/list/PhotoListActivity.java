package com.sample.android.lib.ui.list;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.sample.android.lib.R;


import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {

    public static final String[] PERMISSION_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(PERMISSION_STORAGE);
        }
    }


    protected List<String> checkSelfPermission(String[] permissions) {
        List<String> deniedList = new ArrayList<>(2);
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }

    protected void requestPermission(String[] permissions) {
        List<String> deniedPermissions = checkSelfPermission(permissions);
        if (deniedPermissions.isEmpty()) {
            onPermissionGranted();
        } else {
            permissions = new String[deniedPermissions.size()];
            deniedPermissions.toArray(permissions);
            ActivityCompat.requestPermissions(this, permissions, 0x1);
        }
    }


    protected void onPermissionGranted() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fg_container, new PhotoListFragment())
                .commitAllowingStateLoss();
    }

    protected void onPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setMessage("当前没有读取SD卡权限")
                .setPositiveButton("退出", (dialog, which) -> finish())
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x1 && isGrantedResult(grantResults)) {
            onPermissionGranted();
        } else {
            onPermissionDenied();
        }
    }

    protected boolean isGrantedResult(int... grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
