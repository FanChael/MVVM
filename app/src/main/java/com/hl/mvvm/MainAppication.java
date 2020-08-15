package com.hl.mvvm;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.CommonApi;
import com.hl.base_module.util.system.AppUtil;
import com.hl.base_module.util.system.LogUtil;

public class MainAppication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 防止多次初始化: 当多个进程时，oncreate会多次调用
        String pkgName = getPackageName();
        if (null != pkgName && pkgName.equals(
                AppUtil.getProcessName(this,
                        android.os.Process.myPid()))) {
            // 基础模块相关初始化
            CommonApi.init(this);
            // ARouter路由配置
            if (LogUtil.isDebugMode(this)) {
                ARouter.openLog();
                ARouter.openDebug();
            }
            ARouter.init(this);
        }
    }

    /**
     * onTerminate()会在app关闭的时候调用,但是就像onDestroy()一样，不能保证一定会被调用。所以最好不要依赖这个方法做重要的处理， 这个方法最多可以用来销毁一写对象，清除一下缓存，但是也并不能保证一定会清除掉，其他操作，例如想在程序结束保存数据，用这个方法明显是错误的。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    /**
     * 当应用进程退到后台正在被Cached的时候，可能会接收到从onTrimMemory()中返回的下面的值之一：
     * <p>
     * TRIM_MEMORY_BACKGROUND 系统正运行于低内存状态并且你的进程正处于LRU缓存名单中最不容易杀掉的位置。尽管你的应用进程并不是处于被杀掉的高危险状态，系统可能已经开始杀掉LRU缓存中的其他进程了。你应该释放那些容易恢复的资源，以便于你的进程可以保留下来，这样当用户回退到你的应用的时候才能够迅速恢复。
     * <p>
     * TRIM_MEMORY_MODERATE 系统正运行于低内存状态并且你的进程已经已经接近LRU名单的中部位置。如果系统开始变得更加内存紧张，你的进程是有可能被杀死的。
     * <p>
     * TRIM_MEMORY_COMPLETE 系统正运行于低内存的状态并且你的进程正处于LRU名单中最容易被杀掉的位置。你应该释放任何不影响你的应用恢复状态的资源。
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
