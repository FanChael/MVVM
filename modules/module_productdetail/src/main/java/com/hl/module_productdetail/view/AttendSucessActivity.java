package com.hl.module_productdetail.view;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.module_productdetail.R;
import com.hl.module_productdetail.databinding.ActivityAttendSucessBinding;

@Route(path = ArouterPath.ATTEND_SUCESS)
public class AttendSucessActivity extends BaseWithServiceActivity<ActivityAttendSucessBinding> {

    @Override
    public int setLayout() {
        return R.layout.activity_attend_sucess;
    }

    @Override
    public void initLayout(Context context) {
        ScreenUtil.setStatusBarHeightTop(getViewDataBinding().aasBackIv);
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
}
