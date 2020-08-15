package com.hl.modules_login.model.respository;

import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.lib_network.net.response.TypeCallBack;
import com.hl.modules_login.model.bean.IUserModel;
import com.hl.modules_login.model.bean.UserInfoBean;

public class UserRepository implements IUserModel {
    private BaseControlPresenter baseControlPresenter;

    public UserRepository(BaseControlPresenter baseControlPresenter){
        this.baseControlPresenter = baseControlPresenter;
    }
    @Override
    public void login(String phone, String verifyCode) {
        // 请求网络获取User，这里可以用任何网络请求框架来操作；
        // 只要回调数据给 BaseControlContract.View 即可; 界面会继承BaseControlContract.View
        baseControlPresenter
                .addParam("username", phone)
                .addParam("password", verifyCode)
                .postData(
                "user/login",
                new TypeCallBack<UserInfoBean>() {
                },
                null, false);
    }

    @Override
    public void getVerifyCode(String phone) {
        baseControlPresenter
                .addParam("phone", phone)
                .postData(
                        "v1/sms/send",
                        new TypeCallBack<Boolean>() {
                        },
                        null, false);
    }
}
