package com.hl.modules_main.model.respository;

import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.lib_network.net.response.TypeCallBack;
import com.hl.modules_main.model.bean.Home_Step_State_Bean;
import com.hl.modules_main.model.bean.Home_Task_Item_Bean;
import com.hl.modules_main.model.bean.IHomeModel;

import java.util.List;

public class HomeModelRespository implements IHomeModel {
    private BaseControlPresenter baseControlPresenter;

    public HomeModelRespository(BaseControlPresenter baseControlPresenter) {
        this.baseControlPresenter = baseControlPresenter;
        if (null == baseControlPresenter) {
            throw new NullPointerException("请求服务未创建!");
        }
    }

    @Override
    public void getHomeList(int taskType, int page, boolean _bRefresh) {
        baseControlPresenter
                .addParam("taskType", taskType)
                .postData("v1/task/list",
                new TypeCallBack<List<Home_Task_Item_Bean>>() {},
                        null, page, _bRefresh);
    }

    @Override
    public void updateIncreateStepSum(int stepSum) {
        baseControlPresenter
                .addParam("stepNum", stepSum)
                .postData("v1/step/upload",
                        new TypeCallBack<List<Home_Step_State_Bean>>() {},
                        null, false);
    }
}
