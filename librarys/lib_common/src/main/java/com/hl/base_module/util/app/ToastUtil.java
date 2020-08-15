package com.hl.base_module.util.app;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ToastUtil {
    private static WeakReference<Context> contextWeakReference;

    public static void init(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    /**
     * 默认短时吐司
     * @param msg
     */
    public static void showTost(String msg) {
        showTost(msg, true);
    }
    /**
     * 包装一下吐司
     *
     * @param msg
     * @param bShort
     */
    public static void showTost(String msg, boolean bShort) {
        if (null == contextWeakReference)
            throw new NullPointerException("请调用init在app模块的application中初始化上下文!");
        boolean isMainThread = (Looper.myLooper() == Looper.getMainLooper());
        if (isMainThread) {
            show(msg, bShort);
        } else {
            Looper.prepare();
            show(msg, bShort);
            Looper.loop();
        }
    }

    private static void show(String msg, boolean bShort) {
        if (null != contextWeakReference.get()) {
            if (bShort) {
                Toast.makeText(contextWeakReference.get(), msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(contextWeakReference.get(), msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
