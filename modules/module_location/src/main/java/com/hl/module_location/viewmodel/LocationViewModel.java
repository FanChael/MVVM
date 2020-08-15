package com.hl.module_location.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hl.anotation.NotProguard;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.module_location.model.bean.AddressListBean;
import com.hl.module_location.model.bean.ILocationModel;
import com.hl.module_location.model.respository.LocationRespository;

import java.util.List;

@NotProguard
public class LocationViewModel extends ViewModel implements ILocationModel {
    private MutableLiveData<List<AddressListBean>> addrLiveData;
    private LocationRespository locationRespository;

    public LocationViewModel(BaseControlPresenter baseControlPresenter){
        this.locationRespository = new LocationRespository(baseControlPresenter);
    }

    public MutableLiveData<List<AddressListBean>> getAddrLiveData() {
        if (null == addrLiveData) {
            addrLiveData = new MutableLiveData<>();
        }
        return addrLiveData;
    }

    @Override
    public void getAddreeList(int page) {

    }
}
