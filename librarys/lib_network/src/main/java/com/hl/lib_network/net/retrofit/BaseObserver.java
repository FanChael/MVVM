package com.hl.lib_network.net.retrofit;

import com.hl.lib_network.net.exception.ExceptionHandle;

import io.reactivex.Observer;

/**
 * Created by hl on 2018/7/4.
 * 1. 增加具体错误处理回调 - 给需要使用的地方(比如没有重试处理界面的地方)
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        ///< 自定义转换处理下相关信息
        onErrors(ExceptionHandle.handleException(e));
    }

    public abstract void onSuccess(T t);
    public abstract void onErrors(ExceptionHandle.ResponeThrowable responeThrowable);
}
