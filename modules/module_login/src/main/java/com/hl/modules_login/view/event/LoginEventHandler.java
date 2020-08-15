package com.hl.modules_login.view.event;

import android.view.View;

import com.hl.base_module.handler.BaseHandlers;
import com.hl.base_module.util.data.CheckUITextUtil;
import com.hl.modules_login.view.LoginActivity;
import com.hl.modules_login.databinding.ActivityLoginBinding;

/**
 * 登录相关事件
 *
 * @Author: hl
 * @Date: created at 2020/3/28 9:52
 * @Description: com.hl.modules_login.view.event
 */
public class LoginEventHandler extends BaseHandlers<LoginActivity, ActivityLoginBinding> {

    public LoginEventHandler(LoginActivity loginActivity) {
        super(loginActivity, loginActivity.getViewDataBinding());
    }

    /**
     * 点击登录
     *
     * @param view
     */
    public void clickLogin(View view) {
        if (CheckUITextUtil.bIsNullString(
                getBindingView().alPhoneNumber,
                getBindingView().alVericode)) {
            return;
        }
        getBindedView().userViewModel.login(
                getBindingView().alPhoneNumber.getText().toString(),
                getBindingView().alVericode.getText().toString());
    }

    /**
     * 页面退出
     */
    public void exit() {
        getBindedView().finish();
    }

    /**
     * 清楚输入的电话
     */
    public void phoneClear() {
        getBindingView().alPhoneNumber.setText("");
    }

    /**
     * 获取验证码
     */
    public void getVerifyCode() {
        if (CheckUITextUtil.bIsNullString(getBindingView().alPhoneNumber)) {
            return;
        }
        getBindedView()
                .userViewModel
                .getVerifyCode(getBindingView().alPhoneNumber.getText().toString());
    }
}
