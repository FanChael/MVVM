package com.hl.module_location;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.CommonApi;
import com.hl.base_module.appcomponent.AppConfig;
import com.hl.base_module.appcomponent.CompomentsService;
import com.hl.base_module.appcomponent.IAppComponent;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.system.LogUtil;
import com.hl.lib_network.NetWork;

public class LocationApplication extends Application implements IAppComponent {
    private static final String TAG = LocationApplication.class.getName();

    /**
     * 独立运行时调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO 这个公共工具类和Context还是需要再细分
        DensityUtil.init(this);
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

    /**
     * 主Application运行时调用注册组件
     * @param app
     */
    @Override
    public void initialize(Application app) {
        // 注册上下文
        NetWork.init(app);
        // 注册自己到组件服务
        CompomentsService.setiAppComponentHashMap(AppConfig.PAGE_TYPE.LOCATION, this);
    }
}
