package com.hl.modules_main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hl.anotation.NotProguard;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.modules_main.model.bean.Home_Task_Item_Bean;
import com.hl.modules_main.model.bean.IHomeModel;
import com.hl.modules_main.model.respository.HomeModelRespository;

import java.util.List;

@NotProguard
public class HomeViewModel extends ViewModel implements IHomeModel {
    private MutableLiveData<List<Home_Task_Item_Bean>> homeLd;
    private HomeModelRespository homeModelRespository;

    public HomeViewModel(BaseControlPresenter baseControlPresenter) {
        homeModelRespository = new HomeModelRespository(baseControlPresenter);
    }

    public MutableLiveData<List<Home_Task_Item_Bean>> getHomeLd() {
        if (null == homeLd) {
            homeLd = new MutableLiveData<>();
        }
        return homeLd;
    }

    @Override
    public void getHomeList(int taskType, int page, boolean _bRefresh) {
        homeModelRespository.getHomeList(taskType, page, _bRefresh);
    }

    @Override
    public void updateIncreateStepSum(int stepSum) {
        homeModelRespository.updateIncreateStepSum(stepSum);
    }
}
