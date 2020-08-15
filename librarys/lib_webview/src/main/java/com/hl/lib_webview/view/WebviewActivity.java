package com.hl.lib_webview.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.lib_network.controller.BaseControlContract;
import com.hl.lib_webview.R;
import com.hl.lib_webview.databinding.ActivityCustomWebviewBinding;

import java.util.ArrayList;

/**
 * @Author: hl
 * @Date: created at 2019/7/30 10:02
 * @Description: 内置Webview封装 + App定制处理
 */
public class WebviewActivity extends BaseWithServiceActivity<ActivityCustomWebviewBinding> implements BaseControlContract.View {
    public SWebview sWebview;

    private String html = "";
    private String from = "";

    private String fixTitle = "";
    private String shareTitle = "";
    private String shareContent = "";
    private String sharePic = "";

    @Override
    protected String setTitle() {
        return getIntent().getExtras().getString("share_title", "浏览");
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_custom_webview;
    }

    @Override
    public void initLayout(Context context) {
        Bundle bundle = getIntent().getExtras();
        html = bundle.getString("html", "");
        from = bundle.getString("from", "");
        fixTitle = bundle.getString("title", "");
        // 如果给了分享title和内容，则用下面的内容分享。否则就分享title
        shareTitle = bundle.getString("share_title", "");
        shareContent = bundle.getString("share_content", "");
        sharePic = bundle.getString("share_pic", "");

        // 自定义Webview控件
        sWebview = getViewDataBinding().acwSwebview;

        // 标题自定义，不改变!
        if (!TextUtils.isEmpty(fixTitle)) {
            // setBarTitle(fixTitle);
        }
        //        // 右侧分享按钮
        //        super.setOnTitleClickListener(null, new OnRightClickEvent() {
        //            @Override
        //            public void onClick(View view) {
        //                if (!html.startsWith("http")) {
        //                    showToast("非链接，不可分享!");
        //                    return;
        //                }
        //                extendsCommonPopUtil.showShareLinker(BaseWebviewActivity.this, view,
        //                        html, shareTitle, shareContent, sharePic,
        //                        new ExtendsCommonPopUtil.OnClickback() {
        //                            @Override
        //                            public void onShareSelfDefine(int id, BasePop.Builder builder) {
        //
        //                            }
        //                        }, false);
        //            }
        //        });
        // 支持自适应屏幕，不然有些网站不好看！
        if (fixTitle.contains("隐私政策") || fixTitle.contains("用户协议")) {
            // nothing
        } else {
            sWebview.fitScreen();
        }
    }

    @Override
    public void requestData(Context context) {
        showDialog();
        loadHtml();
    }

    @Override
    public void eventHandler(Context context) {
        // 加载失败，重新加载网页
        //        super.setOnRetryClickListener(new StateView.OnRetryClickListener() {
        //            @Override
        //            public void onRetryClick() {
        //                // 重新加载
        //                requestData(null);
        //            }
        //        });
        sWebview.setLoadListenner(new SWebview.LoadCallBack() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                retryDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                disDialog();
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (TextUtils.isEmpty(shareTitle)
                        && null != title
                        && !title.equals("")) {
                    shareTitle = title;
                }
                // 如果shareContent为空字符串的时候，则设置为shareTitle
                if (TextUtils.isEmpty(shareContent)) {
                    shareContent = shareTitle;
                }
                if (TextUtils.isEmpty(fixTitle)) {
                    WebviewActivity.this.setBarTitle(shareTitle);
                }
            }

            @Override
            public void onImageClick(ArrayList<String> list, int current_index) {

            }
        });
    }

    /**
     * 加载html
     */
    private void loadHtml() {
        if (null == html || html.equals("")) {
            return;
        }
        // 加载html或者html字符串
        if (html.startsWith("http")) {
            sWebview.setUrl(html);
        } else {
            sWebview.setHtml(html);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
        // UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        // 处理文件选择回调事件
        sWebview.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 正常情况下super会返回true...
        boolean bKey = super.onKeyDown(keyCode, event);
        if (bKey) {
            if (sWebview.canGoBack()) {
                sWebview.goBack();
                return false;
            }
            if (from.equals("splash")) {
                ///< 跳转到主页面
                // toClass(BaseWebviewActivity.this, MainActivity.class);
            }
            finish();
        }
        return bKey;
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
