package com.hl.module_location.view.event;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;

public class LocationAddEditorEventHandler {
    private Context mContext;

    public LocationAddEditorEventHandler(Context context){
        this.mContext = context;
    }

    /**
     * 跳转到地图点选页面
     */
    public void locationChoose(){
        // 带返回值的跳转
        Postcard postcard = ARouter.getInstance()
                .build(ArouterPath.ADDRESS_MAP_VIEW);
        LogisticsCenter.completion(postcard);
        Intent intent = new Intent(mContext, postcard.getDestination());
        intent.putExtras(postcard.getExtras());
        ((AppCompatActivity) mContext).startActivityForResult(intent, 110);
    }
}
