package com.hl.modules_personal.view.event;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class OderInfoEventHandler {
    private AppCompatActivity context;

    public OderInfoEventHandler(AppCompatActivity context) {
        this.context = context;
    }

    /**
     * 跳转到物流信息页面
     */
    public void toLogisticsInfo(){
        ARouter.getInstance()
                .build(ArouterPath.USER_LOGITICS_PAGE)
                .navigation();
    }
}