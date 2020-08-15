package com.hl.module_productdetail.model.respository;

import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.lib_network.net.response.TypeCallBack;
import com.hl.module_productdetail.model.IDetailModel;
import com.hl.module_productdetail.model.bean.ChallengeSuccessBean;
import com.hl.module_productdetail.model.bean.TaskDetailBean;

public class DetailModelRespository implements IDetailModel {
    private BaseControlPresenter baseControlPresenter;

    public DetailModelRespository(BaseControlPresenter baseControlPresenter) {
        this.baseControlPresenter = baseControlPresenter;
        if (null == baseControlPresenter) {
            throw new NullPointerException("请求服务未创建!");
        }
    }

    @Override
    public void joinTask(int taskId) {
        baseControlPresenter
                .addParam("taskId", taskId)
                .postData("v1/task/join",
                        new TypeCallBack<ChallengeSuccessBean>() {},
                        null, false);
    }

    @Override
    public void getTaskDetail(int taskId) {
        baseControlPresenter
                .addParam("taskId", taskId)
                .postData("v1/task/info",
                        new TypeCallBack<TaskDetailBean>() {},
                        null, true);
    }
}
