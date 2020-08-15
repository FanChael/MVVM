package com.hl.base_module.util.system;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class LogUtil {
    public static final byte E = 1;
    public static final byte I = 1 << E;
    public static final byte D = 1 << I;
    public static final byte V = 1 << D;

    /**
     * 判断是否是调试模式
     *
     * @param context
     * @return
     */
    public static boolean isDebugMode(Context context) {
        int sIsDebugMode = -1;
        boolean isDebug = context.getApplicationInfo() != null
                && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        sIsDebugMode = isDebug ? 1 : 0;
        return sIsDebugMode == 1;
    }

    /**
     * 简化参数版本
     * @param _class
     * @param msg
     */
    public static void outLogger(Class _class, String msg) {
        outLogger(_class, msg, E);
    }
    /**
     * 输出自定义日志信息  带类+当前方法名称
     *
     * @param _class
     * @param msg
     * @param erroType
     */
    public static void outLogger(Class _class, String msg, byte erroType) {
        String tag = _class.getName() + "$" + Thread.currentThread().getStackTrace()[1].getMethodName();
        switch (erroType) {
            case I:
                Log.i(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case V:
                Log.v(tag, msg);
                break;
            case E:
            default:
                Log.e(tag, msg);
        }
    }
}
