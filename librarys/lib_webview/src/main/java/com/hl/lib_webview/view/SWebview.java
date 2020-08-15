package com.hl.lib_webview.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hl.lib_webview.R;
import com.hl.lib_webview.view.util.WebviewUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Description: 自定义Webview
 *@Des
 * 1.
 *@Author: hl
 *@Time: 2019/4/19 11:33
 */
public class SWebview extends WebView {
    private WebSettings settings;
    private ProgressBar mProgressBar;
    private LoadCallBack loadListenner;

    // 增加本地文件选择调用
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private static final int FILE_CHOOSER_RESULT_CODE = 12;

    /**
     * 用来控制字体大小
     */
    private int fontSize = 1;
    private int lastFonSize = 1;

    public SWebview(Context context) {
        super(context);
        init(context, null);
    }

    public SWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    // 过时了
    //    public SWebview(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
    //        super(context, attrs, defStyleAttr, privateBrowsing);
    //        init(context, attrs);
    //    }

    /**
     * 初始化方法
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            intStyledAttributes(context, attrs);
        }
        WebviewUtil.initWebView(this, null, null,
                new MyWebChromClient(context), new MyWebClient(context));
        settings = this.getSettings();
        lastFonSize = fontSize = WebviewUtil.getTextZoom(this);
    }

    /**
     * 设置JS方法
     *
     * @param obj
     * @param interfaceName
     */
    @SuppressLint("JavascriptInterface")
    public void setJsOperator(Object obj, String interfaceName) {
        ///< 加入JS接口
        if (null != obj && null != interfaceName && !interfaceName.equals("")) {
            this.addJavascriptInterface(obj, interfaceName);
        }
    }

    /**
     * 开启缩放 - 默认是不允许缩放
     *
     * @param bHideControlTool - 支持缩放的同时是否显示缩放控件
     */
    public void enableZoom(boolean bHideControlTool) {
        // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放
        settings.setSupportZoom(true);
        ///< 设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        ///< 隐藏缩放控件
        if (bHideControlTool) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                WebviewUtil.setZoomControlGoneX(settings, new Object[]{bHideControlTool});
            } else {
                // WebviewUtil.setZoomControlGone(this, View.GONE);
            }
        }
        ///< 设置此属性,可任意比例缩放,设置webview推荐使用的窗口
        settings.setUseWideViewPort(true);
    }

    /**
     * 缩放网页字体 - 循环缩放
     */
    public void scalText() {
        fontSize += 10;
        if (fontSize > 139) {
            fontSize = lastFonSize;
        }
        settings.setTextZoom(fontSize);
    }

    /**
     * 缩放网页字体 - 循环缩放
     * 这种情况需要关闭自适应(自适应屏幕，字太小了)
     */
    public void setTextZoom(int _fontSize) {
        //settings.setUseWideViewPort(true);
        settings.setTextZoom(_fontSize);
    }

    /**
     * 自适应设置
     */
    public void fitScreen() {
        settings.setUseWideViewPort(true);
    }

    /**
     * 设置Url/注入Js - url
     *
     * @param urlOrJs
     */
    public void setUrl(String urlOrJs) {
        if (null == urlOrJs || urlOrJs.equals("")) {
            return;
        }
        this.loadUrl(urlOrJs);
    }

    /**
     * 设置html文本 - data字符串
     *
     * @param hmtlData
     * @2019.1.1 -支持了下Img适应屏幕宽度
     */
    public void setHtml(String hmtlData) {
        //  || hmtlData.equals("")
        if (null == hmtlData) {
            return;
        }
        String support_img = hmtlData;
        // 先把所有的style干掉 - 正则表达式
        String regEx = " style=\"(.*?)\"";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(support_img);
        if (m.find()) {
            support_img = m.replaceAll("");
        }
        support_img = support_img.replace("<img", "<img style=\"max-width:100%;height:auto\"");
        // body加了就行了，这里就不用再加了
        //support_img = support_img.replace("<a", "<a style=\"word-break:break-all\"");
        support_img = "<a onselectstart = \"return false\">" + support_img + "</a>";
        // 整体加个自适应换行、字体大小和颜色、背景颜色 - color:#333333;
        support_img = "<body style=\"word-break:break-all; font-size:15px; background:#F7F7F7; line-height:1.8;\"" + support_img + "</body>";
        this.loadDataWithBaseURL(null, support_img, "text/html", "utf-8", null);
    }

    /**
     * 设置html文本 - data字符串
     *
     * @param hmtlData
     * @2019.1.1 -支持了下Img适应屏幕宽度
     */
    public void setHtmlOriginBgGreen(String hmtlData) {
        //  || hmtlData.equals("")
        if (null == hmtlData) {
            return;
        }
        String support_img = hmtlData;
        support_img = support_img.replace("<img", "<img style=\"max-width:100%;height:auto\"");
        // body加了就行了，这里就不用再加了
        //support_img = support_img.replace("<a", "<a style=\"word-break:break-all\"");
        support_img = "<a onselectstart = \"return false\">" + support_img + "</a>";
        // 整体加个自适应换行、字体大小和颜色、背景颜色 - color:#333333;
        support_img = "<body style=\"background:#2051fa;margin:0px\"" + support_img + "</body>";
        this.loadDataWithBaseURL(null, support_img, "text/html", "utf-8", null);
    }

    /**
     * 设置html文本 - data字符串
     *
     * @param hmtlData
     * @2019.1.1 -支持了下Img适应屏幕宽度
     */
    public void setHtmlOrigin(String hmtlData) {
        //  || hmtlData.equals("")
        if (null == hmtlData) {
            return;
        }
        String support_img = hmtlData;
        support_img = support_img.replace("<img", "<img style=\"max-width:100%;height:auto\"");
        this.loadDataWithBaseURL(null, support_img, "text/html", "utf-8", null);
    }

    /**
     * 设置加载监听回调 - 同时此时注入图片点击js事件对象
     *
     * @param _loadListenner
     */
    public void setLoadListenner(LoadCallBack _loadListenner) {
        this.loadListenner = _loadListenner;
        // 设置回调后，注入图片点击处理js方法
        setJsOperator(new JsOperation(loadListenner), "image_click");
    }

    /*
     *@Description: WebViewClient
     *@Author: hl
     *@Time: 2019/4/19 11:49
     */
    private class MyWebClient extends WebViewClient {
        private Context mContext;

        public MyWebClient(Context _context) {
            this.mContext = _context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            ///< 在开始加载网页时会回调
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //            ///< 交给Webview自己处理 - 也能解决加载百度后无法返回退出(不停的刷新百度)
            //            //view.loadUrl(url);
            //            //return true;
            //            return false;
            String url = request.getUrl().toString();
            // 自定义加载回调 - 如果回调返回true，就不再执行后面的操作
            if (null != loadListenner && loadListenner.shouldOverrideUrlLoading(view, url)) {
                return true;
            }

            ///< 拦截支付宝支付
            if (url.startsWith("alipays://platformapi/startApp?") ||
                    url.startsWith("weixin://wap/pay?")) {
                Intent intent;
                try {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            } else if (url.contains("platformapi/startapp")) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setComponent(null);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            ///< android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
            else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                    && (url.contains("platformapi") && url.contains("startapp"))) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setComponent(null);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
            ///< 如果是下载的情况，则跳转到浏览器
            else if (url.contains("download")) { ///<  && url.contains(".apk")
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            } else if (url.endsWith(".apk")) { ///<  && url.contains(".apk")
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // 加载结束回调
            if (null != loadListenner) {
                loadListenner.onPageFinished(view, url);
                // 注入图片点击事件js代码
                addImageClickListner();
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            // 加载错误回调
            if (null != loadListenner) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    loadListenner.onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
                } else {
                    // 低版本不用太过关心了
                    loadListenner.onReceivedError(view, 1, "error:" + error, request.getUrl().toString());
                }
            }
            ///< 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
            super.onReceivedError(view, request, error);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          WebResourceRequest request) {
            ///< 在每一次请求资源时，都会通过这个函数来回调
            return super.shouldInterceptRequest(view, request);
        }
    }

    /*
     *@Description: 自定义WebChromeClient
     *@Author: hl
     *@Time: 2019/4/19 11:49
     */
    private class MyWebChromClient extends WebChromeClient {
        private Context mContext;

        public MyWebChromClient(Context _context) {
            this.mContext = _context;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (null != loadListenner) {
                loadListenner.onReceivedTitle(view, getWebTitle());
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (null != mProgressBar) {
                if (100 == newProgress) {
                    mProgressBar.setVisibility(View.GONE);      ///< 加载完网页进度条消失
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);   ///< 开始加载网页时显示进度条
                    mProgressBar.setProgress(newProgress);      ///< 设置进度值
                }
            }
        }

        //                @Override
        //                public boolean onConsoleMessage(ConsoleMessage cm) {
        //                    Log.d("test", cm.message() + " -- From line "
        //                            + cm.lineNumber() + " of "
        //                            + cm.sourceId() );
        //                    return true;
        //                }
        //
        //                ///< 这个地方是个坑，当时为了调试增加了；这样Js调用Alert的话就会吐司，导致我的页面不能滑动，坑;
        //                @Override
        //                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        //                    Toast.makeText(InfoNewsDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        //                    //return true;  ///< 这个会导致卡死!
        //                    return false;
        //                }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            openFileChooserActivity(mContext);
        }

        // For Android 3.0+
        @SuppressWarnings("unchecked")
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            openFileChooserActivity(mContext);
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            openFileChooserActivity(mContext);
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openFileChooserActivity(mContext);
            return true;
        }
    }

    /**
     * 打开本地文件/相册修改为i.setType("image/*");
     */
    private void openFileChooserActivity(Context context) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Chooser"), FILE_CHOOSER_RESULT_CODE);
        }
    }

    /**
     * 界面的onActivityResult需要调用这个方法处理选择的文件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == mUploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != -1/*RESULT_OK*/ ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    /**
     * 初始化界面，添加顶部进度条
     *
     * @param context
     * @param proh
     * @param proRadius
     * @param pColor
     * @param bgColor
     */
    private void setTopProgressBar(Context context, int proh, int proRadius, int pColor, int bgColor) {
        mProgressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, proh);
        mProgressBar.setLayoutParams(layoutParams);

        GradientDrawable p = new GradientDrawable();
        p.setCornerRadius(proRadius);
        p.setColor(pColor);
        ClipDrawable progress = new ClipDrawable(p, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        GradientDrawable background = new GradientDrawable();
        background.setColor(bgColor);
        background.setCornerRadius(proRadius);

        LayerDrawable pd = new LayerDrawable(new Drawable[]{background, progress});
        mProgressBar.setProgressDrawable(pd);
        this.addView(mProgressBar);
    }

    /**
     * 初始化/获取属性
     *
     * @param context
     * @param attrs
     */
    private void intStyledAttributes(Context context, AttributeSet attrs) {
        ///< @b 获取自定义属性数值值-方便取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Progress_Bar);

        ///< 进度条高度
        int proH = ta.getDimensionPixelOffset(R.styleable.Progress_Bar_s_progress_h, 8);

        ///< 进度条圆角半径
        int proRadius = ta.getDimensionPixelOffset(R.styleable.Progress_Bar_s_progress_corner_radius, 1);

        ///< 进度条背景颜色+进度条进度颜色
        int pro_p_color = ta.getColor(R.styleable.Progress_Bar_s_progress_color, -1);
        int pro_bg_color = ta.getColor(R.styleable.Progress_Bar_s_progress_bg_color, -1);

        if (-1 != pro_p_color && -1 != pro_bg_color) {
            setTopProgressBar(context, proH, proRadius, pro_bg_color, pro_p_color);
        }
    }

    /**
     * 正确获取Webview的标题
     *
     * @return
     */
    public String getWebTitle() {
        WebBackForwardList forwardList = this.copyBackForwardList();
        WebHistoryItem item = forwardList.getCurrentItem();
        if (item != null) {
            return item.getTitle();
        }
        return null;
    }

    /**
     * 注入页面图片点击监听事件
     */
    private void addImageClickListner() {
        ///< 遍历页面中所有img的节点，因为节点里面的图片的url即objs[i].src，保存所有图片的src.
        ///< 为每个图片设置点击事件，objs[i].onclick
        String jsCode = "javascript:(()=>{" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "if (imgs[i].parentNode.tagName == \"DIV\") continue;" +
                "window.image_click.readImageUrl(imgs[i].src);  " +
                "imgs[i].onclick=function(){" +
                "window.image_click.openImage(this.src);" +
                "}}})()";
        setUrl(jsCode);
    }

    /**
     * js调用android方法 - 为了注入图片点击事件
     */
    class JsOperation {
        private List<String> picList = new ArrayList<>();
        private LoadCallBack loadCallBack;

        public JsOperation(LoadCallBack _loadCallBack) {
            this.loadCallBack = _loadCallBack;
        }

        /**
         * 注册代码，带参数
         *
         * @param _param
         */
        @JavascriptInterface
        public void readImageUrl(final String _param) {
            String path = _param.substring(6);
            picList.add(path);
        }

        @JavascriptInterface
        public void openImage(final String _param) {
            String path = _param.substring(6);
            int index = 0;
            ArrayList<String> list = addImages(picList);
            for (String url : list)
                if (url.equals(path)) index = list.indexOf(path);   ///< 获取点击图片在整个页面图片中的位置
            if (null != loadCallBack) loadCallBack.onImageClick(list, index);
        }

        /**
         * 注册代码，带参数
         *
         * @param _param
         */
        @JavascriptInterface
        public void recommondOnclik(final String _param) {
            //Log.e("test", "recommondOnclik");
        }

        /**
         * 去重复
         *
         * @return
         */
        @SuppressWarnings("unchecked")
        private ArrayList<String> addImages(List<String> _picList) {
            ArrayList<String> list = new ArrayList<>();
            Set set = new HashSet();
            for (String cd : _picList) {
                if (set.add(cd)) {
                    list.add(cd);
                }
            }
            return list;
        }
    }

    /**
     * 加载回调
     */
    public interface LoadCallBack {
        boolean shouldOverrideUrlLoading(WebView view, String url);

        void onReceivedError(WebView view, int errorCode,
                             String description, String failingUrl);

        void onPageFinished(WebView view, String url);

        void onReceivedTitle(WebView view, String title);

        void onImageClick(ArrayList<String> list, int current_index);
    }
}
