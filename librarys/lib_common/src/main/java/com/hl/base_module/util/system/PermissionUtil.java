package com.hl.base_module.util.system;

import android.Manifest;

public class PermissionUtil {
    public static final int REQUEST_NEED = 0;
    public static final int REQUEST_STORAGE = 1;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_LOCATION = 3;


    public static String[] PERMISSIONS_NEED = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    public static String[] PERMISSIONS_WR = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    public static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
}
