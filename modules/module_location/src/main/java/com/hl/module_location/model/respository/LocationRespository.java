package com.hl.module_location.model.respository;

import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.module_location.model.bean.ILocationModel;

public class LocationRespository implements ILocationModel {
    private BaseControlPresenter baseControlPresenter;

    public LocationRespository(BaseControlPresenter baseControlPresenter) {
        this.baseControlPresenter = baseControlPresenter;
        if (null == baseControlPresenter) {
            throw new NullPointerException("请求服务未创建!");
        }
    }

    @Override
    public void getAddreeList(int page) {

    }
}
