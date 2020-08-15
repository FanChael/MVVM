package com.hl.modules_personal.view.event;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class SettingEventHandler {
    private AppCompatActivity context;

    public SettingEventHandler(AppCompatActivity context) {
        this.context = context;
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        UserManager.clearCount();
        context.finish();
    }

    /**
     * 跳转到个人中心 setTag可以指定自行处理未登录回调->navigation指定回调参数
     */
    public void toUserInfo(){
        ARouter.getInstance()
                .build(ArouterPath.USER_INFO_PAGE)
                //.setTag(LoginNavigationCallbackImpl.SELF_HANDLER_NAVIGATION_CALLBACK)
                .navigation();
    }
}