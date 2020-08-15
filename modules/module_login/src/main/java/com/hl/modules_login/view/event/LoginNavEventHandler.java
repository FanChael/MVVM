package com.hl.modules_login.view.event;

import android.text.TextUtils;
import android.view.View;

import androidx.navigation.Navigation;

import com.hl.base_module.handler.BaseHandlers;
import com.hl.modules_login.R;
import com.hl.modules_login.databinding.FragmentLoginBinding;
import com.hl.modules_login.view.fragment.LoginFragment;

/**
 * 登录相关事件
*@Author: hl
*@Date: created at 2020/3/28 9:52
*@Description: com.hl.modules_login.view.event
*/
public class LoginNavEventHandler extends BaseHandlers<LoginFragment, FragmentLoginBinding> {

    public LoginNavEventHandler(LoginFragment loginFragment) {
        super(loginFragment, loginFragment.getViewDataBinding());
    }

    /**
     * 点击登录
     * @param view
     */
    public void clickLogin(View view){
        // 获取登录后的数据，然后跳转
        if (TextUtils.isEmpty(getBindingView().flUserNameEt.getText())) {
            getBindingView().flUserNameTL.setError("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(getBindingView().flUserPassEt.getText())) {
            getBindingView().flUserPassTL.setError("请输入密码");
            return;
        }
        getBindedView().userViewModel.login(
                getBindingView().flUserNameEt.getText().toString(),
                getBindingView().flUserPassEt.getText().toString());
    }

    /**
     * 跳转到注册页面
     */
    public void toRegister(View view){
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
    }
}
