package com.hl.base_module.util.app;

import android.app.Activity;
import android.view.View;

/**
 * https://developer.android.google.cn/training/system-ui/navigation?hl=en
 */
public class NavigationBarUtil {
    /**
     * 隐藏StatusBar
     *
     * @param context
     */
    public static void hideNavigationBar(Activity context) {
        View decorView = context.getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
