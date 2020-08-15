package com.hl.base_module.util.system;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.List;

/**
 * Created by hl on 2018/4/3.
 */

public class AppUtil {
    /**
     * 获取本地软件版本号
     */
    @SuppressWarnings("deprecation")
    public static long getLocalVersion(Context ctx) {
        long localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                localVersion = packageInfo.getLongVersionCode();
            } else {
                localVersion = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        return localVersion;
    }

    /*
     * 採用了新的办法获取APK图标。之前的失败是由于android中存在的一个BUG,通过
     * appInfo.publicSourceDir = apkPath;来修正这个问题，详情參见:
     * http://code.google.com/p/android/issues/detail?id=9151
     */
    @SuppressWarnings("deprecation")
    public static long getApkVersionCode(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    return info.getLongVersionCode();
                } else {
                    return info.versionCode;
                }
            } catch (OutOfMemoryError e) {
                return -1;
            }
        }
        return -1;
    }

    /**
     * 版本号比较
     *
     * @param localVersion
     * @param serviceVersion
     * @return
     */
    public static boolean compareVersion(int localVersion, int serviceVersion) {
        return localVersion < serviceVersion;
    }

    /**
     * 用于解决如果从应用市场打开后，点击Home键回到桌面点击icon再次打开一遍启动页面
     */
    public static boolean isFirstOpen(Activity context) {
        if (!context.isTaskRoot()) {
            Intent intent = context.getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                context.finish();
                return true;
            }
        }
        return false;
    }

    /**
     * 获取进程名称
     *
     * @param context
     * @param pid
     * @return
     */
    public static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
