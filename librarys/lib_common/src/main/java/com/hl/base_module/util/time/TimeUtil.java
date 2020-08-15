package com.hl.base_module.util.time;

/**
 * 时间工具类
*@Author: hl
*@Date: created at 2020/3/27 14:39
*@Description: com.hl.base_module.util
*/
public class TimeUtil {
    private static long lastClickTime;
    private static final int CLICK_TIME = 800; //快速点击间隔时间

    /**
    *@Author: hl
    *@Date: created at 2020/3/27 14:39
    *@Description: 是否快速点击判断
    */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < CLICK_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
