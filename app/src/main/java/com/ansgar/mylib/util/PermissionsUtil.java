package com.ansgar.mylib.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 20.04.2017.
 */

public class PermissionsUtil {

    public static final int MAIN_PERMISSION_REQUEST_CODE = 1;
    public static final int LOCATION_REQUEST_CODE = 2;

    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String INTERNET = Manifest.permission.INTERNET;
    public static final String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;

    private WeakReference<Activity> mActivity;

    public PermissionsUtil(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    private boolean checkIfAlreadyPermission() {
        int result = ContextCompat.checkSelfPermission(mActivity.get(), Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) {
                ActivityCompat.requestPermissions(mActivity.get(), permissions, requestCode);
            }
        }
    }

}
