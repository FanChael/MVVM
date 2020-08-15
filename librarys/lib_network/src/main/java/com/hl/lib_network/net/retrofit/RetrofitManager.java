package com.hl.lib_network.net.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hl.lib_network.convert.CustomGsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by hl on 2018/3/11.
 */

public class RetrofitManager {
    private Retrofit mRetrofit = null;
    ///< 利用CookieJar来管理cookie，省的自己去保存设置【有时候自己设置还会涉及到header顺序的问题】
    private ClearableCookieJar cookieJar = null;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public <T> T getmRService(final Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 带Cookie的定义
     */
    public RetrofitManager(final Context context, String baseUrl, long timeOut) {
        cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(context));

        ///< 定义cookie请求 - 采用开源库PersistentCookieJar
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // header添加token等信息
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder();
                        // TODO 这里可以添加服务器需要的header信息
                        /*if (!UserInfoManager.getToken().equals("")){
                            // 添加/更新替换
                            requestBuilder.header("access-token", UserInfoManager.getToken());
                        }
                        if (UserInfoManager.getCityID() > -1){
                            // 添加/更新替换
                            requestBuilder.header("city-id", "" + UserInfoManager.getCityID());
                        }*/
                        // 统一添加token，注意由于没有依赖common(后期再优化模块组件)，需要字段需要对应UserManager
                        if (!TextUtils.isEmpty(SPInnerUtil.getInstance(context)
                                .getSP("user_token_temp"))) {
                            requestBuilder.header("Authorization", "bearer " +
                                    SPInnerUtil.getInstance(context)
                                            .getSP("user_token_temp"));
                            // 临时Token只用一次
                            SPInnerUtil.getInstance(context).putSP("user_token_temp", "");
                        } else {
                            // 永久token，统一添加
                            if (!TextUtils.isEmpty(SPInnerUtil.getInstance(context)
                                    .getSP("user_token"))) {
                                requestBuilder.header("Authorization", "bearer " +
                                        SPInnerUtil.getInstance(context)
                                                .getSP("user_token"));
                            }
                        }
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .cookieJar(cookieJar)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut * 2, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Cookie的释放
     */
    public void clearByCookie() {
        if (null != cookieJar) {
            cookieJar.clearSession();
            cookieJar.clear();
        }
    }
}