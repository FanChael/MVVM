package com.hl.modules_personal.view.event;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.handler.BaseHandlers;
import com.hl.modules_personal.R;
import com.hl.modules_personal.view.PersonalFragment;
import com.hl.modules_personal.databinding.FragmentPersonalBinding;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class PersonalEventHandler extends BaseHandlers<PersonalFragment, FragmentPersonalBinding> {

    public PersonalEventHandler(PersonalFragment fragment) {
        super(fragment, fragment.getViewDataBinding());
    }

    /**
     * 点击登录
     *
     * @param view
     */
    public void OnClickNameTv(View view) {
        // 带返回值的跳转
        Postcard postcard = ARouter.getInstance()
                .build(ArouterPath.LOGIN_ACTIVITY)
                .withString("from", "我是个人中心跳转过来的!");
        LogisticsCenter.completion(postcard);
        Intent intent = new Intent(getContext(), postcard.getDestination());
        intent.putExtras(postcard.getExtras());
        ((AppCompatActivity) getContext()).startActivityFromFragment(getBindedView(), intent, 110);
    }

    /**
     * 退出登录
     *
     * @param view
     */
    public void OnClickLogout(View view) {
        // 清楚本地缓存账号
        UserManager.clearCount();
        getBindingView().fpNameTv.setText("点击登录");
        getBindingView().fpNameTv.setEnabled(true);
    }

    /**
     * 登录页面返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (-1 == resultCode) {
            switch (requestCode) {
                case 110:
                    getBindingView().fpNameTv.setText(data.getStringExtra("phone"));
                    getBindingView().fpInviteNumberTv.setText(data.getStringExtra("invite_code"));
                    break;
            }
        }
    }

    /**
     * 头像设置
     */
    public void updatePersonalState() {
        if (UserManager.bIsLogin()) {
            Glide.with(getContext())
                    .load(UserManager.getUserImg())
                    .placeholder(R.drawable.personal_img_defualt)
                    .error(R.drawable.personal_img_defualt)
                    .into(getBindingView().fpHeaderIv);
            getBindingView().fpNameTv.setText(UserManager.getPhone());
            getBindingView().fpInviteNumberTv.setText("邀请码: " + UserManager.getInviteCode());
            getBindingView().fpCopyBordTv.setVisibility(View.VISIBLE);
            getBindingView().fpNameTv.setEnabled(false);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.personal_img_defualt)
                    .into(getBindingView().fpHeaderIv);
            getBindingView().fpNameTv.setText("点击登录");
            getBindingView().fpInviteNumberTv.setText("邀请码: ------");
            getBindingView().fpCopyBordTv.setVisibility(View.GONE);
            getBindingView().fpNameTv.setEnabled(true);
        }
    }

    /**
     * 跳转到设置界面
     */
    public void toSettingPage(){
        ARouter.getInstance()
                .build(ArouterPath.SETTING_PAGE)
                .navigation();
    }

    /**
     * 跳转到在线客服
     */
    public void toOnlineService(){
        ARouter.getInstance()
                .build(ArouterPath.USER_ONLINE_S_PAGE)
                .navigation();
    }

    /**
     * 跳转到领取订单
     */
    public void toOrder(){
        ARouter.getInstance()
                .build(ArouterPath.USER_ODERS_PAGE)
                .navigation();
    }

    /**
     * 跳转到收货地址
     */
    public void toAddress(){
        ARouter.getInstance()
                .build(ArouterPath.ADDRESS_LOCATION)
                .navigation();
    }

    /**
     * 跳转到毛豆余额
     */
    public void toMaoDouYuE(){
        ARouter.getInstance()
                .build(ArouterPath.DONGDOU_YUE)
                .navigation();
    }
}
