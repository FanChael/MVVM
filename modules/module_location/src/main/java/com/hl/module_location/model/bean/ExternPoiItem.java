package com.hl.module_location.model.bean;

import com.amap.api.services.core.PoiItem;
import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class ExternPoiItem extends BaseMulDataModel {
    private PoiItem poiItem;
    public ExternPoiItem(PoiItem poiItem){
        this.poiItem = poiItem;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
