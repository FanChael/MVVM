package com.hl.lib_network.service;

import com.hl.lib_network.net.response.HttpResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/*
*@Description: 服务请求方法
*@Author: hl
*@Time: 2018/11/21 12:02
*/
public interface BaseService{
    @GET    ///< GET方法 - 获取数据【对象】
    Observable<HttpResponse<String>> getData(@Url String url, @QueryMap HashMap<String, String> paramMap);

    @FormUrlEncoded
    @POST   ///< POST方法 - 获取数据【对象】
    Observable<HttpResponse<String>> postData(@Url String url, @FieldMap HashMap<String, String> paramMap);

}
