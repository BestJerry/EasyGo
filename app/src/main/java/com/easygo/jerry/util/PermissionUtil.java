package com.easygo.jerry.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wujiewei on 2019/1/23
 */
public class PermissionUtil {

    public static final int MY_PERMISSIONS_REQUEST_CODE = 1;

    public static String[] mPermissionsArray = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public static List<String> mPermissionsList = new ArrayList<>();

    public static void initPermissions(Activity activity) {

        mPermissionsList.clear();

        for (int i = 0; i < mPermissionsArray.length; i++) {
            if (checkPermission(activity, mPermissionsArray[i])) {
                mPermissionsList.add(mPermissionsArray[i]);
            }
        }

        if (mPermissionsList.size() > 0) {
            requestPermission(activity, mPermissionsList.toArray(new String[mPermissionsList.size()]));
        }
    }

    public static boolean checkPermission(Activity activity, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        return permissionCheck != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission_group.STORAGE)) {

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    public static void requestPermission(Activity activity, String[] permissions) {

        ActivityCompat.requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST_CODE);
    }
}
