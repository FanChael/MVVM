package com.hl.modules_main;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.appcomponent.AppConfig;
import com.hl.base_module.appcomponent.CompomentsService;
import com.hl.base_module.appcomponent.IAppComponent;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.system.LogUtil;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.lib_network.NetWork;

public class MainApplication extends Application implements IAppComponent {
    private static final String TAG = MainApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        // 初始化屏幕信息
        try {
            ScreenUtil.init(this);
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

    @Override
    public void initialize(Application app) {
        // 注册自己到组件服务
        CompomentsService.setiAppComponentHashMap(AppConfig.PAGE_TYPE.MAIN, this);
    }
}
