package com.hl.module_location.view.event;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;

public class LocationListEventHandler {

    /**
     * 跳转到编辑地址页面
     */
    public void editAddress(){
        ARouter.getInstance()
                .build(ArouterPath.ADDRESS_EDITOR_ADD_LOCATION)
                .withString("tag", "编辑")
                .navigation();
    }

    /**
     * 跳转到新地址页面
     */
    public void newAddress(){
        ARouter.getInstance()
                .build(ArouterPath.ADDRESS_EDITOR_ADD_LOCATION)
                .withString("tag", "新建")
                .navigation();
    }
}
