package com.hl.lib_webview.view.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//import android.widget.ZoomButtonsController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 *@Description: Webview工具类
 *@Author: hl
 *@Time: 2019/4/19 11:40
 */
public class WebviewUtil {
    /**
     * 初始化Webview
     *
     * @param detialWv
     * @param obj           - js接口对象(比如new JsOperation(this))
     * @param interfaceName - js接口名称(js调用Android方法需要使用)
     * @return
     */
    @SuppressLint("JavascriptInterface")
    public static WebView initWebView(WebView detialWv, Object obj, String interfaceName) {
        return initWebView(detialWv, obj, interfaceName, null, null);
    }

    @SuppressLint("JavascriptInterface")
    public static WebView initWebView(WebView detialWv, Object obj, String interfaceName,
                                      WebChromeClient webChromeClient, WebViewClient webViewClient) {
        WebSettings settings = detialWv.getSettings();
        ///< 开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        ///< 加入JS接口
        if (null != obj && null != interfaceName && !interfaceName.equals("")) {
            detialWv.addJavascriptInterface(obj, interfaceName);
        }
        // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是false，不支持缩放
        settings.setSupportZoom(false);
        ///< 设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        settings.setBuiltInZoomControls(false);
        ///< 设置此属性,可任意比例缩放,设置webview推荐使用的窗口
        settings.setUseWideViewPort(false);
        ///< 显示缩放控件
        settings.setDisplayZoomControls(false);

        //自适应屏幕 - 4.4以后webView的适配，setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)失效
        // settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ///< 设置webview加载的页面的模式,缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);

        ///< 设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        settings.setDomStorageEnabled(true);
        ///< 触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        detialWv.requestFocus();

        ///< 允许webview对文件的操作
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
        }
        ///< 设置加载状态
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        ///< 设置加载状态
        if (null != webChromeClient) {
            detialWv.setWebChromeClient(webChromeClient);
        }

        ///< 设置进度
        if (null != webViewClient) {
            detialWv.setWebViewClient(webViewClient);
        }

        //        detialWv.setDownloadListener(new DownloadListener() {
        //            @Override
        //            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        //                //Log.e("test", "下载");
        //            }
        //        });
        return detialWv;
    }

    /**
     * 隐藏webview的缩放按钮 适用于3.0和以后
     *
     * @param view
     * @param args
     */
    public static void setZoomControlGoneX(WebSettings view, Object[] args) {
        Class classType = view.getClass();
        try {
            Class[] argsClass = new Class[args.length];

            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
            Method[] ms = classType.getMethods();
            for (int i = 0; i < ms.length; i++) {
                if (ms[i].getName().equals("setDisplayZoomControls")) {
                    try {
                        ms[i].invoke(view, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    /**
    //     * 隐藏webview的缩放按钮 适用于3.0以前
    //     *
    //     * @param view
    //     * @param visibility View.VISIBLE
    //     */
    //    public static void setZoomControlGone(View view, int visibility) {
    //        Class classType;
    //        Field field;
    //        try {
    //            classType = WebView.class;
    //            field = classType.getDeclaredField("mZoomButtonsController");
    //            field.setAccessible(true);
    //            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
    //            mZoomButtonsController.getZoomControls().setVisibility(visibility);
    //            try {
    //                field.set(view, mZoomButtonsController);
    //            } catch (IllegalArgumentException e) {
    //                e.printStackTrace();
    //            } catch (IllegalAccessException e) {
    //                e.printStackTrace();
    //            }
    //        } catch (SecurityException e) {
    //            e.printStackTrace();
    //        } catch (NoSuchFieldException e) {
    //            e.printStackTrace();
    //        }
    //    }

    /**
     * 正确获取Webview的标题
     *
     * @param webView
     * @return
     */
    public static String getWebTitle(WebView webView) {
        WebBackForwardList forwardList = webView.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            return item.getTitle();
        }
        return null;
    }

    /**
     * 获取文本大小
     *
     * @param webView
     * @return
     */
    public static int getTextZoom(WebView webView) {
        return webView.getSettings().getTextZoom();
    }
}
