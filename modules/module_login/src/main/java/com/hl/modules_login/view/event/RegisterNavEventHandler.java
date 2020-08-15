package com.hl.modules_login.view.event;

import android.text.TextUtils;
import android.view.View;

import com.hl.base_module.handler.BaseHandlers;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.modules_login.databinding.FragmentRegisterBinding;
import com.hl.modules_login.view.fragment.RegisterFragment;

/**
 * 注册相关事件
*@Author: hl
*@Date: created at 2020/3/28 9:52
*@Description: com.hl.modules_login.view.event
*/
public class RegisterNavEventHandler extends BaseHandlers<RegisterFragment, FragmentRegisterBinding> {

    public RegisterNavEventHandler(RegisterFragment registerFragment) {
        super(registerFragment, registerFragment.getViewDataBinding());
    }

    /**
     * 点击登录
     * @param view
     */
    public void clickLogin(View view){
        // 获取登录后的数据，然后跳转
        if (TextUtils.isEmpty(getBindingView().frUserNameEt.getText())) {
            getBindingView().frUserNameTL.setError("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(getBindingView().frUserPassEt.getText())) {
            getBindingView().frUserPassTL.setError("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(getBindingView().frUserRePassEt.getText())) {
            getBindingView().frUserRePassTL.setError("请再次输入密码");
            return;
        }
        if (!getBindingView().frUserRePassEt.getText().toString().equals(getBindingView().frUserPassEt.getText().toString())) {
            ToastUtil.showTost("两次输入密码不一致!");
            return;
        }
        //        getBindedView().userViewModel.register(
        //                getBindingView().frUserNameEt.getText().toString(),
        //                getBindingView().frUserPassEt.getText().toString(),
        //                getBindingView().frUserRePassEt.getText().toString());
    }
}
