//package com.hl.modules_personal.view.event;
//
//import android.content.Intent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.alibaba.android.arouter.core.LogisticsCenter;
//import com.alibaba.android.arouter.facade.Postcard;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.hl.base_module.appcomponent.UserManager;
//import com.hl.base_module.constant.ArouterPath;
//import com.hl.base_module.handler.BaseHandlers;
//import com.hl.modules_personal.databinding.FragmentPersonalBinding;
//import com.hl.modules_personal.view.PersonalFragment_Bak_NightOrLigth;
//
///**
// * 点击事件统一到一个类
// *
// * @Author: hl
// * @Date: created at 2020/3/27 10:43
// * @Description: com.hl.modules_personal.view.event
// */
//public class PersonalEventHandlerBak extends BaseHandlers<PersonalFragment_Bak_NightOrLigth, FragmentPersonalBinding> {
//
//    public PersonalEventHandlerBak(PersonalFragment_Bak_NightOrLigth fragment) {
//        super(fragment, fragment.getViewDataBinding());
//    }
//
//    /**
//     * 点击登录
//     *
//     * @param view
//     */
//    public void OnClickNameTv(View view){
//        // 带返回值的跳转
//        Postcard postcard = ARouter.getInstance()
//                .build(ArouterPath.LOGIN_ACTIVITY)
//                .withString("from", "我是个人中心跳转过来的!");
//        LogisticsCenter.completion(postcard);
//        Intent intent = new Intent(getContext(), postcard.getDestination());
//        intent.putExtras(postcard.getExtras());
//        ((AppCompatActivity)getContext()).startActivityFromFragment(getBindedView(), intent, 110);
//    }
//
//    /**
//     * 退出登录
//     * @param view
//     */
//    public void OnClickLogout(View view) {
//        // 清楚本地缓存账号
//        UserManager.clearCount();
//        getBindingView().fpNameTv.setText("点击登录");
//        getBindingView().fpNameTv.setEnabled(true);
//        getBindingView().fpExitIv.setVisibility(View.GONE);
//    }
//
//    /**
//     * 登录页面返回处理
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        if (-1 == resultCode) {
//            switch (requestCode){
//                case 110:
//                    getBindingView().fpNameTv.setText(data.getStringExtra("user"));
//                    break;
//            }
//        }
//    }
//}
