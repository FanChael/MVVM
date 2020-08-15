package com.hl.base_module.util.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.hl.base_module.R;

public class StatusBarUtil {
    /**
     * 设置状态栏颜色
     */
    public static void initColor(Activity activity, String statusColor) {
        setColor(activity, statusColor, true);
    }

    /**
     * 设置默认白色
     *
     * @param activity
     */
    public static void initAppColor(Activity activity) {
        setColor(activity, ContextCompat.getColor(activity, R.color.app_main_activatedcolor), true);
    }

    /**
     * 设置默认白色
     *
     * @param activity
     */
    public static void initWhiteLight(Activity activity) {
        setColor(activity, null, true);
    }

    /**
     * 设置默认黑色
     *
     * @param activity
     */
    public static void initBlackLight(Activity activity) {
        setColor(activity, null, false);
    }

    /**
     * 辅助方法
     *
     * @param activity
     * @param statusColor
     * @param bWhite
     */
    private static void setColor(Activity activity, Object statusColor, boolean bWhite) {
        Window window = activity.getWindow();

        if (null != statusColor) {
            // 注意要清除 FLAG_TRANSLUCENT_STATUS flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色 - Window负责系统bar的background 绘制
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (statusColor instanceof String) {
                String colors = (String) statusColor;
                window.setStatusBarColor(Color.parseColor(colors));
            } else if (statusColor instanceof Integer) {
                window.setStatusBarColor((Integer) statusColor);
            }
        } else {
            if (bWhite) {
                //设置状态栏字体颜色为白色
                window.getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else {
                //设置状态栏字体颜色为黑色
                window.getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

            //设置状态栏颜色为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置导航键颜色也为透明
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param activity 需要设置的activity
     * @return 状态栏矩形条
     */
    public static int getStatusHeight(Activity activity) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }
}
