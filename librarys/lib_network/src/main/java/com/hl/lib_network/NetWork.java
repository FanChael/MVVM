package com.hl.lib_network;

import android.content.Context;

import com.hl.lib_network.net.retrofit.RetrofitManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 网络请求框架开发api
 *
 * @Author: hl
 * @Date: created at 2020/3/29 12:10
 * @Description: com.hl.lib_network
 */
public class NetWork {
    private static WeakReference<Context> contextWeakReference;
    private static HashMap<String, RetrofitManager> retrofitManager = new HashMap<>();

    /**
     * Application里面注册一下
     *
     * @param _context
     */
    public static void init(Context _context) {
        if (null == contextWeakReference ||
                ( null != contextWeakReference.get() &&
                contextWeakReference.get() != _context)) {
            contextWeakReference = new WeakReference<>(_context);
        }
    }

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public static <T> T getmRService(final String _baseUrl, final Class<T> service, long timeOut) {
        if (!retrofitManager.containsKey(_baseUrl)) {
            retrofitManager.put(_baseUrl, new RetrofitManager(contextWeakReference.get(), _baseUrl, timeOut));
        }
        return retrofitManager.get(_baseUrl).getmRService(service);
    }

    /**
     * 清除Cookie
     */
    public static void clearByCookie(final String _baseUrl) {
        retrofitManager.get(_baseUrl).clearByCookie();
    }
}