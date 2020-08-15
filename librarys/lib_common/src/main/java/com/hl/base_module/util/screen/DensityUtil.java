package com.hl.base_module.util.screen;

import android.content.Context;
import android.util.TypedValue;

import com.hl.base_module.CommonApi;

import java.lang.ref.WeakReference;

/**
 * Created by hl on 2018/3/13.
 */

public class DensityUtil {
    private static WeakReference<Context> weakReference;

    public static void init(Context context) {
        weakReference = new WeakReference<>(context);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (null == CommonApi.getApplication()) {
            if (null == weakReference ||
                    null == weakReference.get()) {
                try {
                    throw new Exception("这个不能作为组件运行时被使用!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return dip2px(weakReference.get(), dpValue);
            }
        }
        return dip2px(CommonApi.getApplication(), dpValue);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2pxF(float dpValue) {
        if (null == CommonApi.getApplication()) {
            if (null == weakReference ||
                    null == weakReference.get()) {
                try {
                    throw new Exception("这个不能作为组件运行时被使用!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return dip2pxF(weakReference.get(), dpValue);
            }
        }
        return dip2pxF(CommonApi.getApplication(), dpValue);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2pxF(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}