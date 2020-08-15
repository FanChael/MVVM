package com.hl.base_module.appcomponent;

import android.text.TextUtils;

import com.hl.base_module.CommonApi;
import com.hl.base_module.util.storage.SharedPreferencesUtil;

/**
 * 用户本地信息管理
 */
public class UserManager {
    /**
     * 是否登录了
     *
     * @return
     */
    public static boolean bIsLogin() {
        // TextUtils.isEmpty(getToken()) wanandroid没有返token
        if (TextUtils.isEmpty(getPhone())) {
            return false;
        }
        return true;
    }

    public static String getPhone() {
        return SharedPreferencesUtil.getInstance(CommonApi.getApplication()).getSP(phoneKey());
    }

    public static String getToken() {
        return SharedPreferencesUtil.getInstance(CommonApi.getApplication()).getSP(userToken());
    }

    public static String getUserImg() {
        return SharedPreferencesUtil.getInstance(CommonApi.getApplication()).getSP(avaterKey());
    }

    /**
     * 临时token，主要是登录成功后，获取个人信息用的；获取个人信息成功之后才能转正
     * @return
     */
    public static String getTokenTemp() {
        return SharedPreferencesUtil.getInstance(CommonApi.getApplication()).getSP(userTokenTemp());
    }

    public static void saveTokenTemp(String tempToken) {
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(userTokenTemp(), tempToken);
    }

    public static String getInviteCode() {
        return SharedPreferencesUtil.getInstance(CommonApi.getApplication()).getSP(userInviteCode());
    }

    public static void saveUser(String phone, String token, String inviteCode, String userImage) {
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(phoneKey(), phone);
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(userToken(), token);
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(userInviteCode(), inviteCode);
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(avaterKey(), userImage);
    }

    public static void clearCount() {
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(phoneKey(), "");
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(userToken(), "");
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(userInviteCode(), "");
        SharedPreferencesUtil.getInstance(CommonApi.getApplication()).putSP(avaterKey(), "");
    }

    private static String phoneKey() {
        return "phone_key";
    }
    private static String userToken() {
        return "user_token";
    }
    private static String avaterKey() {
        return "avate_key";
    }
    private static String userTokenTemp() {
        return "user_token_temp";
    }
    private static String userInviteCode() {
        return "user_invitecode";
    }
}
