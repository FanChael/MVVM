package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityUserInfoBinding;
import com.hl.modules_personal.view.event.UserInfoEventHandler;

@Route(path = ArouterPath.USER_INFO_PAGE)
public class UserInfoActivity extends BaseWithServiceActivity<ActivityUserInfoBinding> {

    @Override
    protected String setTitle() {
        return "个人信息";
    }

    @Override
    protected int getBgColor() {
        return ContextCompat.getColor(this, R.color.deep_more_gray);
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initLayout(Context context) {
        getViewDataBinding().auiUNameValue.setText(UserManager.getPhone());
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getUserInfoEvent()){
            getViewDataBinding().setUserInfoEvent(new UserInfoEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
