package com.hl.mvvm.arouter;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;

// 在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(name = "LoginInteceptor", priority = 1)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        String tag = "";
        if (null != postcard.getTag() && postcard.getTag() instanceof String) {
            tag = (String) postcard.getTag();
        }
        if (UserManager.bIsLogin()) {
            callback.onContinue(postcard);
        } else {
            switch (path) {
                case ArouterPath.SHOPING_CAR:
                case ArouterPath.USER_INFO_PAGE:
                case ArouterPath.USER_LOGITICS_PAGE:
                case ArouterPath.USER_ODERS_PAGE:
                    if (tag.contains("self")) {
                        callback.onInterrupt(new Throwable("请先登录"));
                    } else {
                        Bundle bundle = postcard.getExtras();
                        // 登录拦截下来了
                        // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
                        ARouter.getInstance()
                                .build(ArouterPath.LOGIN_ACTIVITY)
                                .with(bundle)
                                .withString("from_path", path)
                                .navigation();
                    }
                    break;
                default:
                    callback.onContinue(postcard);
                    break;
            }
        }
    }

    @Override
    public void init(Context context) { // 此方法只会走一次
        // "路由登录拦截器初始化成功"
    }
}
