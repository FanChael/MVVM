package com.hl.base_module.page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.R;
import com.hl.base_module.page.observer.ActivityObserver;
import com.hl.base_module.util.app.StatusBarUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.system.PermissionUtil;
import com.hl.base_module.util.time.TimeUtil;
import com.hl.lib_network.controller.BaseView;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/** 基础Activity页面
*@Author: hl
*@Date: created at 2020/3/26 11:11
*@Description: com.hl.base_module.page
 * 1.采用DataBinding绑定数据-后期可替换为kt
 * 2.Aroute路由加持
*/
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BaseView {
    public String TAG = getClass().getName();

    /**
     * 权限
     */
    private String settingTitle = "提示";
    private String settingContent = "必要权限";
    private boolean mbUnnecessary = true;

    private T viewDataBinding;
    // 事件处理
    private BackListenner backListenner;
    private PermissionsGrantedListenner permissionsListenner;

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
        if (TextUtils.isEmpty(title)){
            findViewById(R.id.ab_titleRoot).setVisibility(View.GONE);
        } else {
            // 沉浸式处理
            ScreenUtil.setStatusBarHeightTop(findViewById(R.id.ab_titleRoot));
            ((TextView)findViewById(R.id.ab_titleTv)).setText(title);
            findViewById(R.id.ab_backIv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != backListenner){
                        backListenner.exit();
                    } else {
                        finish();
                    }
                }
            });
        }
        // 1.TODO 标题相关操作、沉浸式、打开分享按钮等
        // 2.Use DataBinding创建内部布局，添加内容布局
        viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), setLayout(), null, false);
        // 2.1 设置统一背景，方便切换夜间模式
        viewDataBinding.getRoot().setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg_color));
        // 3.添加内容布局
        FrameLayout ab_contentRoot = findViewById(R.id.ab_contentRoot);
        ab_contentRoot.addView(viewDataBinding.getRoot());
        //        // 3. 统一注解Arouter
        //        ARouter.getInstance().inject(this);
        // 初始化View、适配器等调用
        initLayout(this);
        // 数据请求调用
        requestData(this);
        // 事件绑定
        eventHandler(this);
        // 注册生命周期监听 - 然后做一些绑定、解绑、埋点等操作
        getLifecycle().addObserver(new ActivityObserver(this, getLifecycle()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 当从软件设置界面，返回当前程序时候
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                break;

        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.i("InfoFlashDetails", "onPermissionsGranted");
        if (null != permissionsListenner) {
            permissionsListenner.onSucess();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.i("InfoFlashDetails", "onPermissionsDenied");
        if (mbUnnecessary) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setTitle(settingTitle)
                        .setRationale(settingContent)
                        .setPositiveButton("同意")
                        .setNegativeButton("取消") // "" 是无法隐藏取消按钮的，必须加空格
                        .setRequestCode(requestCode)
                        .build()
                        .show();
            } else {
                // 如果权限必要的话，那么取消则直接退出界面
                finish();
            }
        }
    }

    /**
     * 权限申请
     *
     * @param permissionType - PermissionUtil.REQUEST_NEED等权限类型
     * @param bUnnecessary   - 是否必要设置(必要的话，则拒绝后会提示到设置界面)
     */
    protected void requestPermission(int permissionType, boolean bUnnecessary) {
        mbUnnecessary = bUnnecessary;
        switch (permissionType) {
            case PermissionUtil.REQUEST_NEED:
                settingTitle = "所需权限";
                settingContent = "缺少需要权限，将要跳转到设置界面";
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,
                                PermissionUtil.REQUEST_NEED, PermissionUtil.PERMISSIONS_NEED)
                                .setRationale("应用需要获取必要的权限完成一些操作")
                                .build());
                break;
            case PermissionUtil.REQUEST_STORAGE:
                settingTitle = "读写";
                settingContent = "缺少存储权限，将要跳转到设置界面";
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,
                                PermissionUtil.REQUEST_STORAGE, PermissionUtil.PERMISSIONS_WR)
                                .setRationale("应用需要获取读写权限完成一些操作")
                                .build());
                break;
            case PermissionUtil.REQUEST_CAMERA:
                settingTitle = "媒体";
                settingContent = "缺少相机权限，将要跳转到设置界面";
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,
                                PermissionUtil.REQUEST_CAMERA, PermissionUtil.PERMISSIONS_CAMERA)
                                .setRationale("应用需要相机权限完成一些操作")
                                .build());
                break;
            case PermissionUtil.REQUEST_LOCATION:
                settingTitle = "定位";
                settingContent = "缺少定位权限，将要跳转到设置界面";
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,
                                PermissionUtil.REQUEST_LOCATION, PermissionUtil.PERMISSIONS_LOCATION)
                                .setRationale("应用需要定位权限完成一些操作")
                                .build());
                break;
        }
    }

    /**
     * 防重复点击事件
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

    }

    @Override
    public void disDialog() {

    }

    @Override
    public void retryDialog() {

    }

    @Override
    public void emptyDialog() {

    }

    @Override
    public void emptyDialog(String msg) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onLoinOut(String _functionName, Object object) {

    }

    @Override
    public void onThirdBind(String _functionName, int code) {

    }

    @Override
    public void onFinishFresh() {
    }

    @Override
    public void onFinishLoadMore() {
    }

    /**
     * 退出事件监听
     * @param backListenner
     */
    public void setBackListenner(BackListenner backListenner){
        this.backListenner = backListenner;
    }
    /**
     * 权限成功回调通知
     * @param permissionsListenner
     */
    public void setPermissionsListenner(PermissionsGrantedListenner permissionsListenner){
        this.permissionsListenner = permissionsListenner;
    }
    /**
     * 退出监听事件
     */
    public interface BackListenner{
        void exit();
    }

    /**
     * 定位权限回调
     */
    public interface PermissionsGrantedListenner{
        void onSucess();
    }
    protected boolean bIsLightThenDark(){             // 设置系统标题栏颜色是亮色还是暗色
        return true;
    }
    protected String setTitle(){                       // 设置标题
        return "";
    }        // 标题设置，默认自定义不显示标题栏

    public abstract int setLayout();                   // 设置布局

    public abstract void initLayout(Context context);  // 动态调整布局控件大小、间距、初始适配器等

    public abstract void requestData(Context context);  // 发起数据请求

    public abstract void eventHandler(Context context);  // 事件处理
    //.....
}
