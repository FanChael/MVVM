package com.hl.lib_network.net.exception;

import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 错误异常处理类
 * 1.Retrifit网络错误回调，有时候需要做提示
 */
public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        Log.e("tag", "e.toString = " + e.toString());
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                    ex.message = "404！";
                    break;
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器内部错误！";
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                    ex.message = "服务器不可用！";
                    break;
                default:
                    //ex.code = httpException.code();
                    ex.message = "连接错误";
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
            /*|| e instanceof ParseException*/) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.NETWORK_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.SOCKET_TIMEOUT_ERROR);
            ex.message = "请求超时";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ResponeThrowable(e, ERROR.SOCKET_TIMEOUT_ERROR);
            ex.message = "无法连接服务器";
            return ex;
        } else if (e instanceof ServerException) {
            ex = new ResponeThrowable(e, ((ServerException) e).getErroCode());
            ex.message = "自定义错误!";
            ex.message = e.getMessage();
            return ex;
        } else {
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            ex.message = e.getMessage();
            return ex;
        }
    }


    /**
     * 约定异常 - 外面用作判断使用
     */
    public class ERROR {
        /**
         * 没网错误
         */
        public static final int NO_NETWORK = 999;
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * Socket超时
         */
        public static final int SOCKET_TIMEOUT_ERROR = 10060;

        /**
         * TOKEN过期
         */
        public static final int TOKEN = 110110;


        /**
         * 请求错误
         */
        public static final int REQUEST_ERROR = 110113;

        /**
         * 三方登录后需要绑定
         */
        public static final int THIRD_BIND = 110115;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 服务器异常定义:
     * ServerException发生后，将转换为ResponeThrowable返回
     */
    public static class ServerException extends RuntimeException {
        private int code;
        private String message;

        public ServerException(int resultCode) {
            getApiExceptionMessage(resultCode, "");
        }

        public ServerException(int resultCode, String detailMessage) {
            getApiExceptionMessage(resultCode, detailMessage);
        }

        @Override
        public String getMessage() {
            return message;
        }

        public int getErroCode() {
            return code;
        }

        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，在显示给用户
         *
         * @param _code
         * @return
         */
        private void getApiExceptionMessage(int _code, String _message) {
            code = _code;
            switch (code) {
                case ExceptionHandle.ERROR.TOKEN:
                    message = "Token过期";
                    break;
                case ExceptionHandle.ERROR.REQUEST_ERROR:
                    message = "请求错误";
                    if (null != _message && !_message.equals("")) {
                        message = _message;
                        ///< 特殊处理
                        if (message.contains("limittimes")) {
                            message = "次数过多限制!";
                        }
                    }
                    break;
                case ExceptionHandle.ERROR.NO_NETWORK:
                    message = "网络未连接";
                    break;
                case ExceptionHandle.ERROR.THIRD_BIND:
                    message = "需要绑定";
                    break;
                default:
                    message = "未知错误";
            }
        }
    }
}
