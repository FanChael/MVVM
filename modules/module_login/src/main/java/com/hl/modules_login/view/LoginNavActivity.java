package com.hl.modules_login.view;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_login.R;
import com.hl.modules_login.databinding.ActivityNavLoginBinding;

@Route(path = ArouterPath.LOGIN_ACTIVITY)
public class LoginNavActivity extends BaseWithServiceActivity<ActivityNavLoginBinding> {
    @Autowired
    public String from;

    private ActivityNavLoginBinding activityLoginBinding;

    @Override
    public int setLayout() {
        return R.layout.activity_nav_login;
    }

    @Override
    protected boolean bIsLightThenDark() {
        // 登录白色背景需要配暗色状态栏
        return false;
    }

    @Override
    public void initLayout(Context context) {
        activityLoginBinding = getViewDataBinding();
    }

    @Override
    public void requestData(Context context) {
    }

    @Override
    public void eventHandler(Context context) {
    }

    @Override
    public void onSucess(String _functionName, Object t) {
    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.anl_fragment);
        return NavHostFragment.findNavController(fragment).navigateUp();
    }
}
