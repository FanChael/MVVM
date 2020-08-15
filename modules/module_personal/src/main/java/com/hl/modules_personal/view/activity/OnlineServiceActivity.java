package com.hl.modules_personal.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityOnlineServiceBinding;

@Route(path = ArouterPath.USER_ONLINE_S_PAGE)
public class OnlineServiceActivity extends BaseActivity<ActivityOnlineServiceBinding> {

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    protected String setTitle() {
        return "在线客服";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_online_service;
    }

    @Override
    public void initLayout(Context context) {

    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        // 就不单独做一个事件处理类了
        getViewDataBinding().aosCallService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber(getViewDataBinding().aosCallService.getKey_word());
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
