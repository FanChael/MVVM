package com.hl.base_module.appcomponent;

import java.util.HashMap;

public class AppConfig {
    public enum PAGE_TYPE {
        HOME, LOGIN, MAIN, P_DETAIL, S_CART, LOCATION
    }

    /**
     * 组件Application集合
     */
    public static HashMap<PAGE_TYPE, String> COMPONENTS = new HashMap<PAGE_TYPE, String>() {
        {
            // 主页
            put(PAGE_TYPE.HOME, "com.hl.modules_home.HomeApplication");
            // 商品详情页
            put(PAGE_TYPE.P_DETAIL, "com.hl.module_productdetail.ProductDetailApplication");
            // 领取奖品页
            put(PAGE_TYPE.S_CART, "com.hl.module_shoppingcart.ShoppingCartApplication");
            // 登录页面
            put(PAGE_TYPE.LOGIN, "com.hl.modules_login.LoginApplication");
            // 个人中心
            put(PAGE_TYPE.MAIN, "com.skl.login.PersonalApplication");
            // 收货地址模块
            put(PAGE_TYPE.LOCATION, "com.hl.module_location.LocationApplication");
        }
    };
}
