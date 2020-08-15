package com.hl.lib_network.net.response;

import com.google.gson.Gson;
import com.hl.lib_network.net.response.TypeCallBack;

import java.util.HashMap;

/*
 *@Description: 方法url管理+类型转换管理
 *@Author: hl
 *@Time: 2018/11/22 16:18
 */
public class ParseManager {
    /**
     *  更通用的泛型转换
     * @param strData
     * @param _resultCallBack
     * @return
     */
    public static Object getBean(String strData, TypeCallBack _resultCallBack) {
        String typeName = _resultCallBack.mType.toString().replace(" ", "").replaceFirst("class", "");
        if (null == strData || strData.equals("null")){
            return "";
        }
        try {
            return new Gson().fromJson(strData, _resultCallBack.mType);
        } catch (Exception e) {
            // 不是json则直接返回(可能是字符串)
            return strData;
        }
    }

    public static Object getBeanList(String strData, TypeCallBack _resultCallBack, int _pageNum) {
        String typeName = _resultCallBack.mType.toString().replace(" ", "").replaceFirst("class", "");
        if (null == strData || strData.equals("null")){
            return "";
        }
        try {
            return new Gson().fromJson(strData, _resultCallBack.mType);
        } catch (Exception e) {
            // 不是json则直接返回(可能是字符串)
            return strData;
        }
    }
}