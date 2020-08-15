package com.hl.lib_network.net.url;

import com.hl.lib_network.BuildConfig;

/**
 * Created by hl on 2019/12/11.
 */

public class NetUrl {
    // 通用url
    public static final String base_url = BuildConfig.DEBUG ?
            "https://www.wanandroid.com/":
            "https://www.wanandroid.com/";
    // 其他三方url或者另外一个新的服务地址
    public static final String other_url = "https://xxx.xxx.yyy";
}
