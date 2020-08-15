package com.hl.modules_personal.view.activity;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityDongdouInfoBinding;
import com.hl.modules_personal.view.event.DongDouInfoEventHandler;

@Route(path = ArouterPath.DONGDOU_YUE)
public class DongdouInfoActivity extends BaseWithServiceActivity<ActivityDongdouInfoBinding> {
    private DongDouInfoEventHandler dongDouInfoEventHandler;

    @Override
    protected String setTitle() {
        return "动豆余额";
    }

    @Override
    protected String setSecondTitle() {
        return "动豆明细";
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_dongdou_info;
    }

    @Override
    public void initLayout(Context context) {

    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        // 标题右侧点击跳转到动豆明细
        super.setRightListenner(new RightListenner() {
            @Override
            public void rightClick() {
                dongDouInfoEventHandler.toDongDouBillDetails();
            }
        });
        if (null == getViewDataBinding().getDongdouInfoEvent()) {
            getViewDataBinding().setDongdouInfoEvent((dongDouInfoEventHandler = new DongDouInfoEventHandler(this)));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
