package com.hl.lib_banner.example;

import android.content.Context;

import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.lib_banner.R;
import com.hl.lib_banner.databinding.ActivityBannerTestBinding;

import java.util.ArrayList;
import java.util.List;

public class BannerTestActivity extends BaseWithServiceActivity<ActivityBannerTestBinding> {
    @Override
    public int setLayout() {
        return R.layout.activity_banner_test;
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public void initLayout(Context context) {

    }

    @Override
    public void requestData(Context context) {
        List<String> imgList  = new ArrayList<>();
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588156088710&di=055003d07ddf9187b8cc2aaf1b13f9f0&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201012%2F18%2F11241689kkepeiyerc77es.jpg");
        imgList.add("https://gitee.com/heyclock/mvvm_modularization/raw/master/zdoc/pic/2.png");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588156186767&di=95bb7b55315bd75a49e2fda30a82a204&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201411%2F07%2F1056118jxbvrblfrc2c72x.gif");
        getViewDataBinding().abtBvp2
                .setData(imgList)
                .start(1.5f);
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
