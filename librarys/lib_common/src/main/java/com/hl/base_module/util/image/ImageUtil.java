package com.hl.base_module.util.image;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/*
 *@Description: 图形相关工具
 *@Author: hl
 *@Time: 2019/4/3 15:15
 */
public class ImageUtil {
    /**
     * 缩放Drawable
     *
     * @drawable 原来的Drawable
     * @w 指定的宽
     * @h 指定的高
     */
    public static Drawable zoomDrawable(Context mContext, Drawable drawable, int w, int h) {
        //获取原来Drawable的宽高
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //将Drawable转换成Bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        //计算scale
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        //生成新的Bitmap
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        //设置bitmap转成drawable后尺寸不变
        //这个很关键后面解释！！
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //Resources resources = new Resources(mContext.getAssets(), metrics, null);
        if (null != oldbmp){
            oldbmp.recycle();
        }
        return new BitmapDrawable(mContext.getResources(), newbmp);
    }

    /**
     * 将Drawable转换为Bitmap
     *
     * @param drawable
     * @return
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {
        /*
        //取drawable的宽高
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        //取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        //创建对应的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        //创建对应的bitmap的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        //把drawable内容画到画布中
        drawable.draw(canvas);
        return bitmap;
         */
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }
}
