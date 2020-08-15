package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.storage.DataManagerUtil;
import com.hl.base_module.util.system.AppUtil;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivitySettingBinding;
import com.hl.modules_personal.view.event.SettingEventHandler;

@Route(path = ArouterPath.SETTING_PAGE)
public class SettingActivity extends BaseWithServiceActivity<ActivitySettingBinding> {

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    protected int getBgColor() {
        return ContextCompat.getColor(this, R.color.deep_more_gray);
    }

    @Override
    protected String setTitle() {
        return "设置中心";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initLayout(Context context) {
        if (!UserManager.bIsLogin()) {
            getViewDataBinding().asLoginOut.setText("未登录");
            getViewDataBinding().asLoginOut.setEnabled(false);
        }
        try {
            getViewDataBinding().asMemValue.setText(DataManagerUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getViewDataBinding().asVersionalValue.setText(AppUtil.getLocalVersionName(this));
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getSettingHandler()) {
            getViewDataBinding().setSettingHandler(new SettingEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
