package com.hl.lib_network.controller;

import com.hl.lib_network.net.response.TypeCallBack;

import java.util.HashMap;

/*
*@Description: 服务+视图层的纽带
*@Author: hl
*@Time: 2018/11/21 13:55
*/
public interface BaseControlContract {
    interface View extends BaseView{
        void onSucess(String _functionName, Object t);
        void onNomoreData(String _functionName, Object t);
        void onFailed(String _functionName, String _message);
    }

    /**
     * 负责请求网络获取数据
     */
    interface Presenter{
        /**
         * 请求数据【对象】
         * @param _paramList
         */
        void requestData(String _function, TypeCallBack _resultCallBack, HashMap<String, String> _paramList, boolean _bShowDialog);
        /**
         * 请求数据【对象】
         * @param _paramList
         */
        void postData(String _function, TypeCallBack _resultCallBack, HashMap<String, String> _paramList, boolean _bShowDialog);

        /**
         * 请求数据【对象】 - 分页
         * @param _paramList
         */
        void requestData(String _function, TypeCallBack _resultCallBack, HashMap<String, String> _paramList, int page, boolean _bRefresh);
        /**
         * 请求数据【对象】
         * @param _paramList - 分页
         */
        void postData(String _function, TypeCallBack _resultCallBack, HashMap<String, String> _paramList, int page, boolean _bRefresh);

        /**
         * 释放资源
         */
        void releaseResource();
    }
}
