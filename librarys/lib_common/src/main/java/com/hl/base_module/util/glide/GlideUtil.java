package com.hl.base_module.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;

public class GlideUtil {

    /**
     * 设置带缓存的图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void initImageWithFileCache(Context context, String url, ImageView imageView) {
        if (null == context) {
            return;
        }
        // 这种方式可以解决因为Context为null的问题，内部做了处理
        RequestManager mRequestManager = Glide.with(new WeakReference<>(context).get());
        mRequestManager
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 设置不带缓存的图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void initImageNoCache(Context context, String url, ImageView imageView) {
        if (null == context) {
            return;
        }
        // 这种方式可以解决因为Context为null的问题，内部做了处理
        RequestManager mRequestManager = Glide.with(new WeakReference<>(context).get());
        mRequestManager
                .load(url)
                .skipMemoryCache(true)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 设置圆形图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void initRoundImageCache(Context context, String url, ImageView imageView){
        if (null == context) {
            return;
        }
        // 这种方式可以解决因为Context为null的问题，内部做了处理
        RequestManager mRequestManager = Glide.with(new WeakReference<>(context).get());
        mRequestManager
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 清理内存
     *
     * @param context
     */
    public static void clearMemoryCache(Context context) {
        if (null == context) {
            return;
        }
        try {
            Glide.get(context).clearMemory();
        } catch (Exception e) {

        }
    }

    /**
     * 清理磁盘缓存
     *
     * @param context
     */
    public static void clearFileCache(Context context) {
        if (null == context) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.get(context).clearDiskCache();
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
