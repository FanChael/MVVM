//package com.hl.modules_personal.view;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.CompoundButton;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.CircleCrop;
//import com.hl.base_module.appcomponent.UserManager;
//import com.hl.base_module.constant.ArouterPath;
//import com.hl.base_module.page.BaseFragment;
//import com.hl.base_module.util.app.ToastUtil;
//import com.hl.base_module.util.screen.ScreenUtil;
//import com.hl.base_module.util.system.DayNightUtil;
//import com.hl.modules_personal.R;
//import com.hl.modules_personal.databinding.FragmentPersonalBakNightorlightBindingImpl;
//import com.hl.modules_personal.databinding.FragmentPersonalBinding;
//import com.hl.modules_personal.view.event.PersonalEventHandler;
//import com.hl.modules_personal.view.event.PersonalEventHandlerBak;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PersonalFragment_Bak_NightOrLigth#newInstance} factory method to
// * create an instance of this fragment.
// */
////@Route(path = ArouterPath.PERSONAL_FRAGMENT)
//public class PersonalFragment_Bak_NightOrLigth extends BaseFragment<FragmentPersonalBakNightorlightBinding> {
//    // 碎片databinding一把
//    private FragmentPersonalBakNightorlightBinding fragmentPersonalBinding;
//
//    public PersonalFragment_Bak_NightOrLigth() {
//
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PersonalFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PersonalFragment_Bak_NightOrLigth newInstance(String param1, String param2) {
//        PersonalFragment_Bak_NightOrLigth fragment = new PersonalFragment_Bak_NightOrLigth();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public int setLayout() {
//        return R.layout.fragment_personal_bak_nightorlight;
//    }
//
//    @Override
//    public void initLayout(Context context) {
//        fragmentPersonalBinding = getViewDataBinding();
//        // Glide加载圆形头像
//        Glide.with(requireContext())
//                .load(R.drawable.header)
//                .transform(new CircleCrop())
//                //.placeholder(R.drawable.loading_spinner)
//                .into(fragmentPersonalBinding.fpHeaderIv);
//        // 动态设置距离标题栏的高度
//        ScreenUtil.setStatusBarHeightTop(fragmentPersonalBinding.fpExitIv);
//        ScreenUtil.setStatusBarHeightTop(fragmentPersonalBinding.fpDayNightTb);
//    }
//
//    @Override
//    public void requestData(Context context) {
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!TextUtils.isEmpty(UserManager.getName())) {
//            fragmentPersonalBinding.fpNameTv.setText(UserManager.getName());
//            fragmentPersonalBinding.fpNameTv.setEnabled(false);
//            fragmentPersonalBinding.fpExitIv.setVisibility(View.VISIBLE);
//        } else {
//            fragmentPersonalBinding.fpNameTv.setText("点击登录");
//            fragmentPersonalBinding.fpNameTv.setEnabled(true);
//            fragmentPersonalBinding.fpExitIv.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void eventHandler(Context context) {
//        // 注册事件对象
//        if (null == fragmentPersonalBinding.getPersonalHanler()) {
//            fragmentPersonalBinding.setPersonalHanler(new PersonalEventHandlerBak(this));
//        }
//        // 初始化夜间白天模式开关
//        if (!DayNightUtil.bIsDay(context)) {
//            fragmentPersonalBinding.fpDayNightTb.setChecked(true);
//        }
//        fragmentPersonalBinding.fpDayNightTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    ToastUtil.showTost("切换到夜间模式", true);
//                    // 保存夜间状态
//                    DayNightUtil.setIsDay(requireContext(), false);
//                } else {
//                    ToastUtil.showTost("切换到白天模式", true);
//                    // 保存白天状态
//                    DayNightUtil.setIsDay(requireContext(), true);
//                }
//                requireActivity().finish();
//                startActivity(new Intent(requireContext(), requireContext().getClass()));
//                requireActivity().overridePendingTransition(0, 0);
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        fragmentPersonalBinding.getPersonalHanler().onActivityResult(requestCode, resultCode, data);
//    }
//}
