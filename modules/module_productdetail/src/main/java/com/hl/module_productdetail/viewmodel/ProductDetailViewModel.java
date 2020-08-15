package com.hl.module_productdetail.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hl.anotation.NotProguard;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.module_productdetail.model.IDetailModel;
import com.hl.module_productdetail.model.bean.ChallengeSuccessBean;
import com.hl.module_productdetail.model.bean.TaskDetailBean;
import com.hl.module_productdetail.model.respository.DetailModelRespository;

@NotProguard
public class ProductDetailViewModel extends ViewModel implements IDetailModel {
    private MutableLiveData<ChallengeSuccessBean> successBeanMutableLiveData;
    private MutableLiveData<TaskDetailBean> detailBeanMutableLiveData;
    private DetailModelRespository detailModelRespository;

    public ProductDetailViewModel(BaseControlPresenter baseControlPresenter) {
        this.detailModelRespository = new DetailModelRespository(baseControlPresenter);
    }

    public MutableLiveData<ChallengeSuccessBean> getSuccessBeanMutableLiveData() {
        if (null == successBeanMutableLiveData) {
            successBeanMutableLiveData = new MutableLiveData<>();
        }
        return successBeanMutableLiveData;
    }

    public MutableLiveData<TaskDetailBean> getDetailBeanMutableLiveData() {
        if (null == detailBeanMutableLiveData) {
            detailBeanMutableLiveData = new MutableLiveData<>();
        }
        return detailBeanMutableLiveData;
    }

    @Override
    public void joinTask(int taskId) {
        detailModelRespository.joinTask(taskId);
    }

    @Override
    public void getTaskDetail(int taskId) {
        detailModelRespository.getTaskDetail(taskId);
    }
}
