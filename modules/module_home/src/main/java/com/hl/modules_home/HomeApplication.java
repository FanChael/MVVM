package com.hl.modules_home;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.appcomponent.AppConfig;
import com.hl.base_module.appcomponent.CompomentsService;
import com.hl.base_module.appcomponent.IAppComponent;
import com.hl.base_module.util.system.LogUtil;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.lib_network.NetWork;

public class HomeApplication extends Application implements IAppComponent {
    private static final String TAG = HomeApplication.class.getName();
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
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
        // 注册上下文
        NetWork.init(app);
        // 注册自己到组件服务
        CompomentsService.setiAppComponentHashMap(AppConfig.PAGE_TYPE.HOME, this);
    }
}
