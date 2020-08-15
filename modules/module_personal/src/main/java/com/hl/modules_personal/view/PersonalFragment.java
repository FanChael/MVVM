package com.hl.modules_personal.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseFragment;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.FragmentPersonalBinding;
import com.hl.modules_personal.view.event.PersonalEventHandler;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Route(path = ArouterPath.PERSONAL_FRAGMENT)
public class PersonalFragment extends BaseFragment<FragmentPersonalBinding> {
    // 碎片databinding一把
    private FragmentPersonalBinding fragmentPersonalBinding;

    public PersonalFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalFragment newInstance(String param1, String param2) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initLayout(Context context) {
        fragmentPersonalBinding = getViewDataBinding();
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentPersonalBinding.getPersonalHanler().updatePersonalState();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fragmentPersonalBinding.getPersonalHanler().updatePersonalState();
        }
    }

    @Override
    public void eventHandler(Context context) {
        // 注册事件对象
        if (null == fragmentPersonalBinding.getPersonalHanler()) {
            fragmentPersonalBinding.setPersonalHanler(new PersonalEventHandler(this));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 登录成功已经将用户信息保存到本地，此时会走onResume的updatePersonalState方法，所以下面可以不用调用
        //fragmentPersonalBinding.getPersonalHanler().onActivityResult(requestCode, resultCode, data);
    }
}
