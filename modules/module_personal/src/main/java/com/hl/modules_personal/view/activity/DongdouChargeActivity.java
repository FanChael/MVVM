package com.hl.modules_personal.view.activity;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityDongdouChargeBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.DONGDOU_CHARGE)
public class DongdouChargeActivity extends BaseWithServiceActivity<ActivityDongdouChargeBinding> {
    private static final List<String> dongdou_number = new ArrayList<String>() {
        {
            add("1动豆");
            add("5动豆");
            add("10动豆");
            add("20动豆");
            add("54动豆");
            add("100动豆");
        }
    };
    private static final List<String> dongdou_number_price = new ArrayList<String>() {
        {
            add("￥0.98元");
            add("￥4.50元");
            add("￥9.00元");
            add("￥18.00元");
            add("￥45.00元");
            add("￥90.00元");
        }
    };

    @Override
    protected String setTitle() {
        return "动豆充值";
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_dongdou_charge;
    }

    @Override
    public void initLayout(Context context) {

    }

    @Override
    public void requestData(Context context) {
        // 直接添加到组件就行了，不需要id等信息
        // 直接添加到组件就行了，不需要id等信息
        getViewDataBinding().addcDongdouSelectNumber.addRadioView(this,
                dongdou_number, dongdou_number_price, 3);
    }

    @Override
    public void eventHandler(Context context) {

    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
