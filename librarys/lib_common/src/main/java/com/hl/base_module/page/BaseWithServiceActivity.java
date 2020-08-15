package com.hl.base_module.page;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.nukc.stateview.StateView;
import com.hl.base_module.R;
import com.hl.base_module.page.observer.ActivityObserver;
import com.hl.base_module.util.app.StatusBarUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.time.TimeUtil;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.lib_network.controller.BaseControlContract;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * 基础Activity页面 + 初始化了请求服务
 */
public abstract class BaseWithServiceActivity<T extends ViewDataBinding> extends AppCompatActivity implements BaseControlContract.View {
    /**
     * 请求服务
     */
    public BaseControlPresenter baseControlPresenter = null;

    public String TAG = getClass().getName();
    private T viewDataBinding;

    // 全局加载进度条，增加一个是否开启全局进度条的方式
    private StateView mStateView;
    public int current_page = 1;

    // 事件处理
    private BackListenner backListenner;
    private RightListenner rightListenner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 0. Inflate the layout for this AppCompatActivity
        setContentView(R.layout.activity_base);
        // 0.01. 统一注解Arouter - 提早一点注解，否则标题参数拿不到
        ARouter.getInstance().inject(this);
        // 0.1 沉浸式走起
        if (bIsLightThenDark()) {
            StatusBarUtil.initWhiteLight(this);
        } else {
            StatusBarUtil.initBlackLight(this);
        }
        // 0.5 设置标题
        String title = setTitle();
        if (TextUtils.isEmpty(title)) {
            findViewById(R.id.ab_titleRoot).setVisibility(View.GONE);
        } else {
            // 沉浸式处理
            ScreenUtil.setStatusBarHeightTop(findViewById(R.id.ab_titleRoot));
            ((TextView) findViewById(R.id.ab_titleTv)).setText(title);
            findViewById(R.id.ab_backIv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != backListenner) {
                        backListenner.exit();
                    } else {
                        finish();
                    }
                }
            });
            // 右侧副标题
            String sTitle = setSecondTitle();
            if (!TextUtils.isEmpty(sTitle)) {
                TextView rightTv = (TextView) findViewById(R.id.ab_secondTitleTv);
                rightTv.setVisibility(View.VISIBLE);
                rightTv.setText(sTitle);
                rightTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != rightListenner) {
                            rightListenner.rightClick();
                        }
                    }
                });
            }
        }
        // 1.TODO 标题相关操作、沉浸式、打开分享按钮等
        // 2.Use DataBinding创建内部布局，添加内容布局
        viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), setLayout(), null, false);
        // 2.1 设置统一背景，方便切换夜间模式
        viewDataBinding.getRoot().setBackgroundColor(getBgColor());
        // 3.添加内容布局
        FrameLayout ab_contentRoot = findViewById(R.id.ab_contentRoot);
        ab_contentRoot.addView(viewDataBinding.getRoot());
        // 3.1 添加全局转圈
        mStateView = setStateView();
        if (null == mStateView) {
            mStateView = StateView.inject(ab_contentRoot);
        }
        //        // 4. 统一注解Arouter - 提到前面去注解，早点获取navigation参数
        //        ARouter.getInstance().inject(this);
        // 由基础页面创建请求服务，管理生命周期
        baseControlPresenter = new BaseControlPresenter(this);
        // 初始化View、适配器等调用
        initLayout(this);
        // 事件绑定
        eventHandler(this);
        // 数据请求调用
        requestData(this);
        // 注册生命周期监听 - 然后做一些绑定、解绑、埋点等操作
        getLifecycle().addObserver(new ActivityObserver(this, getLifecycle()));
    }

    /**
     * 防重复点击事件
     *
     * @param view
     * @param listener
     */
    @BindingAdapter("android:onClick")
    public static void onClick(final View view, final View.OnClickListener listener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TimeUtil.isFastDoubleClick()) {
                    if (null != listener) {
                        listener.onClick(view);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != baseControlPresenter) {
            baseControlPresenter.releaseResource();
            baseControlPresenter = null;
        }
    }

    /**
     * 获取对应的DataBinding注解对象
     *
     * @return
     */
    public T getViewDataBinding() {
        if (null == viewDataBinding) {
            throw new NullPointerException("Not init viewDataBinding!");
        }
        return viewDataBinding;
    }

    @Override
    public void showDialog() {
        if (null == mStateView) {
            return;
        }
        mStateView.showLoading();
    }

    @Override
    public void disDialog() {
        if (null == mStateView) {
            return;
        }
        mStateView.showContent();
    }

    @Override
    public void retryDialog() {
        if (null == mStateView) {
            return;
        }
        mStateView.showRetry();
    }

    @Override
    public void emptyDialog() {
        if (null == mStateView) {
            return;
        }
        mStateView.showEmpty();
    }

    @Override
    public void emptyDialog(String msg) {
        if (null == mStateView) {
            return;
        }
        mStateView.showEmpty(msg);
    }

    /**
     * des: 重试监听
     * author: hl
     *
     * @param onRetryClickListener
     */
    protected void setOnRetryClickListener(StateView.OnRetryClickListener onRetryClickListener) {
        if (null == mStateView) {
            return;
        }
        ///< 设置重试事件
        mStateView.setOnRetryClickListener(onRetryClickListener);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showTost(msg);
    }

    @Override
    public void onLoinOut(String _functionName, Object object) {

    }

    @Override
    public void onThirdBind(String _functionName, int code) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {
        if (current_page > 1) --current_page;
        if (null != getSmartRefreshLayout()) {
            if (null != getSmartRefreshLayout()) {
                if (1 == current_page) {
                    getSmartRefreshLayout().finishRefresh();
                } else {
                    getSmartRefreshLayout().finishLoadMore();
                }
            }
        }
    }

    @Override
    public void onNomoreData(String _functionName, Object t) {
        if (current_page > 1) --current_page;
        if (null != getSmartRefreshLayout()) {
            getSmartRefreshLayout().setNoMoreData(true);
        }
    }

    @Override
    public void onFinishFresh() {
        getSmartRefreshLayout().finishRefresh();
    }

    @Override
    public void onFinishLoadMore() {
        getSmartRefreshLayout().finishLoadMore();
    }

    // 页面返回刷新组件
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }

    /**
     * 设置标题
     *
     * @param mTitle
     */
    public void setBarTitle(String mTitle) {
        ((TextView) findViewById(R.id.ab_titleTv)).setText(mTitle);
    }

    /**
     * 退出事件监听
     *
     * @param backListenner
     */
    public void setBackListenner(BackListenner backListenner) {
        this.backListenner = backListenner;
    }

    /**
     * 右侧标题点击事件
     *
     * @param rightListenner
     */
    public void setRightListenner(RightListenner rightListenner) {
        this.rightListenner = rightListenner;
    }

    /**
     * 退出监听事件
     */
    public interface BackListenner {
        void exit();
    }

    public interface RightListenner {
        void rightClick();
    }

    protected boolean bIsLightThenDark() {             // 设置系统状态栏颜色是亮色还是暗色
        return true;
    }

    protected String setTitle() {                       // 设置标题
        return "";
    }

    protected String setSecondTitle() {                    // 设置右侧标题
        return "";
    }

    // 默认背景颜色
    protected int getBgColor() {
        return ContextCompat.getColor(this, R.color.page_bg_color);
    }

    protected StateView setStateView() { // 重新设置StateView，不采用默认的
        return null;
    }

    public abstract int setLayout();                   // 设置布局

    public abstract void initLayout(Context context);  // 动态调整布局控件大小、间距、初始适配器等

    public abstract void requestData(Context context);  // 发起数据请求

    public abstract void eventHandler(Context context);  // 事件处理
    //.....
}