
package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityOderInfoBinding;
import com.hl.modules_personal.view.event.OderInfoEventHandler;

@Route(path = ArouterPath.USER_ODERINFO_PAGE)
public class OderInfoActivity extends BaseWithServiceActivity<ActivityOderInfoBinding> {
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
        return "订单详情";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_oder_info;
    }

    @Override
    public void initLayout(Context context) {

    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getOrderInfoEvent()){
            getViewDataBinding().setOrderInfoEvent(new OderInfoEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
