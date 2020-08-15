package com.hl.modules_login.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hl.anotation.NotProguard;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.hl.modules_login.model.bean.UserInfoBean;
import com.hl.modules_login.model.respository.UserRepository;

@NotProguard
public class UserViewModel extends ViewModel{
    private MutableLiveData<UserInfoBean> userLiveData;
    private UserRepository userRepository;

    public UserViewModel(BaseControlPresenter baseControlPresenter) {
        userRepository = new UserRepository(baseControlPresenter);
    }

    public MutableLiveData<UserInfoBean> getUserLiveData() {
        if (null == userLiveData) {
            userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }

    public void login(String phone, String verifyCode) {
        userRepository.login(phone, verifyCode);
    }
    public void getVerifyCode(String phone) {
        userRepository.getVerifyCode(phone);
    }
}
