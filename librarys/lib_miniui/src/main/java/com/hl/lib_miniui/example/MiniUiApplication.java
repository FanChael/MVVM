package com.hl.lib_miniui.example;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.hl.base_module.appcomponent.AppConfig;
import com.hl.base_module.appcomponent.CompomentsService;
import com.hl.base_module.appcomponent.IAppComponent;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.base_module.util.glide.FlickrGlideModule;
import com.hl.base_module.util.system.LogUtil;
import com.hl.lib_network.NetWork;

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class MiniUiApplication extends Application implements IAppComponent {
    private static final String TAG = MiniUiApplication.class.getName();

    private static WeakReference<Context> context;

    /**
     * 独立运行时调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = new WeakReference<Context>(this);
        ToastUtil.init(this);
        // 解决glide加载https证书问题
        try {
            Glide.get(this).getRegistry().replace(
                    GlideUrl.class, InputStream.class,
                    new OkHttpUrlLoader.Factory(FlickrGlideModule.getSSLOkHttpClient()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 注册上下文
        NetWork.init(this);
        // ARouter路由配置
        if (LogUtil.isDebugMode(this)) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    /**
     * 主Application运行时调用注册组件
     * @param app
     */
    @Override
    public void initialize(Application app) {
        // 注册上下文
        NetWork.init(app);
        // 注册自己到组件服务
        CompomentsService.setiAppComponentHashMap(AppConfig.PAGE_TYPE.LOGIN, this);
    }

    /**
     * 获取上下文
     * @return
     */
    public static Context getContext(){
        return null == context ? null : context.get();
    }
}
