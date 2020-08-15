package com.hl.lib_network.net.response;

import com.hl.lib_network.net.exception.ExceptionHandle;

import io.reactivex.functions.Function;

/*
 *@Description: 请求数据封装 - 增加中间处理过程(可以选择处理或者直接返回)
 *@Author: hl
 *@Time: 2018/9/29 15:38
 * W - 表示输入的数据，也就是请求获得的data数据(对象，字符串等格式)
 * T - 表示返回的数据，最终到onNext(Ojbect o)
 * W输入数据通过回调CallMe.onCall可以进行中间处理过程，然后返回T
 */
public class ResponsePreHandle<T, W> implements Function<HttpResponse<W>, T> {
    private CallMe<T, W> callMe;

    public ResponsePreHandle(CallMe<T, W> callMe) {
        this.callMe = callMe;
    }

    @Override
    public T apply(HttpResponse<W> wHttpResponse) {
        if (0 == wHttpResponse.getErrorCode()) {
            ///< 成功
            return callMe.onCall(wHttpResponse.getData(), "nothing");
        } else if (606 == wHttpResponse.getErrorCode()) {
            ///< Token过期
            throw new ExceptionHandle.ServerException(ExceptionHandle.ERROR.TOKEN, wHttpResponse.getErrorMsg());
        }
        else {
            ///< 其他情况 - 返回真实的message信息
            throw new ExceptionHandle.ServerException(ExceptionHandle.ERROR.REQUEST_ERROR, wHttpResponse.getErrorMsg());
        }
    }

    public interface CallMe<T, W> {
        public T onCall(W data, String requestTime);
    }
}
