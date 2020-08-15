package com.hl.modules_main.view.event;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.handler.BaseHandlers;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.lib_pop.view.share.SharePop;
import com.hl.modules_main.view.HomeFragment;
import com.hl.modules_main.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class HomeEventHandler extends BaseHandlers<HomeFragment, FragmentHomeBinding> {

    public HomeEventHandler(HomeFragment fragment) {
        super(fragment, fragment.getViewDataBinding());
    }

    /**
     * 名称点击事件
     *
     * @param view
     */
    public void OnClickNameTv(View view) {
        ToastUtil.showTost("主页名称点击事件", true);
    }

    /**
     * 跳转到领取商品页面
     */
    public void gotoShoppingCar() {
        // 跳转到挑战页面
        ARouter.getInstance()
                .build(ArouterPath.SHOPING_CAR)
                .navigation();
    }

    /**
     * 获取首页列表数据
     * @param page
     */
    public void getHomeListData(int page, boolean _bRefresh) {
        getBindedView().homeViewModel.getHomeList(1, page, _bRefresh);
    }

    /**
     * 增加步数
     * @param stempSum
     */
    public void updateIncrementStepSum(int stempSum) {
        // 未登录则不统计了
        if (!UserManager.bIsLogin()){
            return;
        }
        getBindedView().homeViewModel.updateIncreateStepSum(stempSum);
    }

    /**
     * 分享得礼
     */
    public void shareGift(){
        if (!UserManager.bIsLogin()) {
            ARouter.getInstance()
                    .build(ArouterPath.LOGIN_ACTIVITY)
                    .navigation();
            return;
        }
        List<String> picList = new ArrayList<>();
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592283437665&di=74a53cd311efd06735817d03ed9606fa&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592283437665&di=74a53cd311efd06735817d03ed9606fa&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg");
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592283437665&di=74a53cd311efd06735817d03ed9606fa&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg");
        SharePop.getInstance()
                .setImageList(picList)
                .showSafely(((FragmentActivity)getContext()).getSupportFragmentManager(),
                        "分享得礼");
    }
}
