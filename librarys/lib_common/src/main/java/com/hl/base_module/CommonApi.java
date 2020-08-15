package com.hl.base_module;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.hl.base_module.appcomponent.AppConfig;
import com.hl.base_module.appcomponent.IAppComponent;
import com.hl.base_module.util.glide.FlickrGlideModule;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.app.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;

public class CommonApi {
    // 刷新请求头配置
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    // 上下文弱引用保存
    private static WeakReference<Application> applicationWeakReference;

    /**
     * 初始化相关配置工具类等->app/Application
     *
     * @param context
     */
    public static void init(Context context) {
        if (context instanceof Application) {
            applicationWeakReference = new WeakReference<>((Application) context);
        }
        // 初始化全局Toast
        ToastUtil.init(context);
        // 解决glide加载https证书问题
        try {
            Glide.get(context).getRegistry().replace(
                    GlideUrl.class, InputStream.class,
                    new OkHttpUrlLoader.Factory(FlickrGlideModule.getSSLOkHttpClient()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 初始化屏幕信息
        try {
            ScreenUtil.init(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 各个模块初始化
        initialize((Application) context);
        // 夜间白天模式初始化
        // DayNightUtil.setDayOrNight(context);
    }

    /**
     * 各个模块初始化
     *
     * @param app
     */
    private static void initialize(Application app) {
        // 遍历所有的组件的Application类，依次用反射的方式实现组件初始化和注册
        for (Map.Entry<AppConfig.PAGE_TYPE, String> entry : AppConfig.COMPONENTS.entrySet()) {
            try {
                Class classz = Class.forName(entry.getValue());
                Object object = classz.newInstance();
                // 实例化后，调用各个组件的initialize方法（init会实现组件的注册）
                if (object instanceof IAppComponent) {
                    ((IAppComponent) object).initialize(app);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Application上下文
     * @return
     */
    public static Application getApplication() {
        return null != applicationWeakReference ? applicationWeakReference.get() : null;
    }
}
