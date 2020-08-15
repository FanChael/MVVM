package com.hl.modules_personal.view.event;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.handler.BaseHandlers;
import com.hl.modules_personal.databinding.ActivityDongdouInfoBinding;
import com.hl.modules_personal.view.activity.DongdouInfoActivity;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class DongDouInfoEventHandler extends BaseHandlers<DongdouInfoActivity, ActivityDongdouInfoBinding> {

    public DongDouInfoEventHandler(DongdouInfoActivity dongdouInfoActivity) {
        super(dongdouInfoActivity, dongdouInfoActivity.getViewDataBinding());
    }

    /**
     * 跳转到动豆明细
     */
    public void toDongDouBillDetails(){
        ARouter.getInstance()
                .build(ArouterPath.DONGDOU_LIST)
                .navigation();
    }

    /**
     * 跳转到动豆充值
     */
    public void toDongDouChage(){
        ARouter.getInstance()
                .build(ArouterPath.DONGDOU_CHARGE)
                .navigation();
    }
}
