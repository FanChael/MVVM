package com.hl.modules_login.model.bean;

public interface IUserModel{
    void login(String phone, String verifyCode);
    void getVerifyCode(String phone);
}
