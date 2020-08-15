package com.hl.modules_login.view;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.viewmodel.SelfViewModelFactory;
import com.hl.modules_login.R;
import com.hl.modules_login.databinding.ActivityLoginBinding;
import com.hl.modules_login.model.bean.UserInfoBean;
import com.hl.modules_login.view.event.LoginEventHandler;
import com.hl.modules_login.viewmodel.UserViewModel;


// 另外还Navigation方式，一个界面多个碎片切换，方便注册，登录等跳转
// @Route(path = ArouterPath.LOGIN_ACTIVITY)
public class LoginActivity extends BaseWithServiceActivity<ActivityLoginBinding> {
    @Autowired
    public String from;

    public UserViewModel userViewModel;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean bIsLightThenDark() {
        // 登录白色背景需要配暗色状态栏
        return false;
    }

    @Override
    public void initLayout(Context context) {
        // 设置距离标题栏的高度(系统默认标题栏高度为56dp)
        ScreenUtil.setStatusBarHeightTop(getViewDataBinding().alExitIv);
    }

    @Override
    public void requestData(Context context) {
        // 自定义ModelFactory创建ViewModel
        userViewModel = new ViewModelProvider(this, new SelfViewModelFactory(baseControlPresenter)).get(UserViewModel.class);
        // 监听Live数据变化
        userViewModel.getUserLiveData().observe(this, new Observer<UserInfoBean>() {
            @Override
            public void onChanged(UserInfoBean userInfoBean) {
                // 0.保存用户信息
                UserManager.saveUser(userInfoBean.getUsername(),
                        userInfoBean.getToken(),
                        "", "");

                // 1. 直接返回结果到跳转页面
                Intent intent = new Intent();
                intent.putExtra("phone", userInfoBean.getUsername());
                intent.putExtra("user_image", "");
                setResult(RESULT_OK, intent);

                // 2. 用Eventbus通知其他页面
                //                EventBus.getDefault().post(new MessageEvent(userInfoBean.getUsername()));

                // 3. 路由到主页面，然后切换到某个碎片，实现跨页面跳转
                //                ARouter.getInstance()
                //                        .build(ArouterPath.HOME_ACTIVITY)
                //                        .withInt(HomePath.WHICH, HomePath.HOME_PAGE)
                //                        .navigation();

                finish();
            }
        });
    }

    @Override
    public void eventHandler(Context context) {
        // 注册事件对象
        if (null == getViewDataBinding().getLoginHandler()) {
            getViewDataBinding().setLoginHandler(new LoginEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {
        if (t instanceof UserInfoBean) {
            UserInfoBean userInfoBean = (UserInfoBean) t;
            userViewModel.getUserLiveData().setValue(userInfoBean);
        } else if (t instanceof Boolean) {
            showToast("获取短信验证码成功!");
        }
    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
