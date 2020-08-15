package com.hl.lib_network.net.response;

/*
*@Description: 基础网络数据封装
*@Author: hl
*@Time: 2018/9/27 16:16
*/
public class HttpResponse<T> {
    private int errorCode;
    private String errorMsg;
    private T data;

    public HttpResponse(){}

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "\"code\":" + errorCode +
                ", \"message\":\"" + errorMsg + '\"' +
                ", \"data\":\"" + data + '\"' +
                '}';
    }
}
