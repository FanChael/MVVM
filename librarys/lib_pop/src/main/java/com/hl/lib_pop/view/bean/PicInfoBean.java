package com.hl.lib_pop.view.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class PicInfoBean extends BaseMulDataModel {
    private String url;

    public PicInfoBean(String url){
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
