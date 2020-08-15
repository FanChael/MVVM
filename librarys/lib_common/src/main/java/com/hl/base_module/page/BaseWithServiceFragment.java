package com.hl.base_module.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.nukc.stateview.StateView;
import com.hl.base_module.R;
import com.hl.base_module.page.observer.FragmentObserver;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.base_module.util.time.TimeUtil;
import com.hl.lib_network.controller.BaseControlContract;
import com.hl.lib_network.controller.presenter.BaseControlPresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * 基础Fragment页面
 *
 * @Author: hl
 * @Date: created at 2020/3/26 11:11
 * @Description: com.hl.base_module.page
 * 1.采用DataBinding绑定数据-后期可替换为kt
 * 2.Aroute路由加持
 */
public abstract class BaseWithServiceFragment<T extends ViewDataBinding> extends Fragment implements BaseControlContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public String mParam1;
    public String mParam2;
    public int current_page = 1;

    private T viewDataBinding;

    // 全局加载进度条，增加一个是否开启全局进度条的方式
    private StateView mStateView;

    /**
     * 请求服务
     */
    public BaseControlPresenter baseControlPresenter = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    public BaseWithServiceFragment setParams(String param1, String param2) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        setArguments(args);
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 0.Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        // 1. TODO 标题等操作 - 暂时未自定义标题栏
        // 2.Use DataBinding创建内容布局
        viewDataBinding = DataBindingUtil.inflate(inflater, setLayout(), container, false);
        // 3.添加内容布局
        FrameLayout fb_contentRoot = view.findViewById(R.id.fb_contentRoot);
        fb_contentRoot.addView(viewDataBinding.getRoot());
        // 3.1 添加全局转圈
        mStateView = setStateView();
        if (null == mStateView) {
            mStateView = StateView.inject(fb_contentRoot);
        }
        // 4. 统一注解Arouter
        ARouter.getInstance().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化View、适配器等调用
        initLayout(requireContext());
        // 由基础页面创建请求服务，管理生命周期
        baseControlPresenter = new BaseControlPresenter(this);
        //点击等事件处理
        eventHandler(requireContext());
        // 数据请求调用
        requestData(requireContext());
        // 注册生命周期监听 - 然后做一些绑定、解绑、埋点等操作
        getLifecycle().addObserver(new FragmentObserver(requireContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != baseControlPresenter) {
            baseControlPresenter.releaseResource();
            baseControlPresenter = null;
        }
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
    protected void setOnRetryClickListener(OnRetryListener onRetryClickListener) {
        if (null == mStateView) {
            return;
        }
        ///< 设置重试事件
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onRetryClick();
                }
            }
        });
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
    public void onFinishFresh() {
        getSmartRefreshLayout().finishRefresh();
    }

    @Override
    public void onFinishLoadMore() {
        getSmartRefreshLayout().finishLoadMore();
    }

    @Override
    public void onNomoreData(String _functionName, Object t) {
        if (current_page > 1) --current_page;
        if (null != getSmartRefreshLayout()) {
            getSmartRefreshLayout().setNoMoreData(true);
        }
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


    // 返回刷新组件
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return null;
    }

    /**
     * 子类实现相关界面初始化，数据初始化等操作
     */
    protected StateView setStateView() { // 重新设置StateView，不采用默认的
        return null;
    }

    public abstract int setLayout();

    public abstract void initLayout(Context context);  // 动态调整布局控件大小、间距、初始适配器等

    public abstract void requestData(Context context);  // 发起数据请求

    public abstract void eventHandler(Context context);  // 事件处理

    //.....
    public interface OnRetryListener {
        void onRetryClick();
    }
}
