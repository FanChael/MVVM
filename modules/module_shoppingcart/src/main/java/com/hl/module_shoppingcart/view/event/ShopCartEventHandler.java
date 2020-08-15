package com.hl.module_shoppingcart.view.event;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;

public class ShopCartEventHandler {
    /**
     * 跳转到定位页面
     */
    public void goLocationPage(){
        ARouter.getInstance()
                .build(ArouterPath.ADDRESS_LOCATION)
                .navigation();
    }
}

