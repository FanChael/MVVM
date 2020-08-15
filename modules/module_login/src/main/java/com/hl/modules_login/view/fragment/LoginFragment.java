package com.hl.modules_login.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hl.base_module.appcomponent.UserManager;
import com.hl.base_module.page.BaseWithServiceFragment;
import com.hl.base_module.util.edittext.TextInputEditTextWatcher;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.viewmodel.SelfViewModelFactory;
import com.hl.modules_login.R;
import com.hl.modules_login.databinding.FragmentLoginBinding;
import com.hl.modules_login.model.bean.UserInfoBean;
import com.hl.modules_login.view.event.LoginNavEventHandler;
import com.hl.modules_login.viewmodel.UserViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseWithServiceFragment<FragmentLoginBinding> {
    public UserViewModel userViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout(Context context) {
        // 设置距离标题栏的高度(系统默认标题栏高度为56dp)
        ScreenUtil.setMargin(getViewDataBinding().flTopCard,
                -10000, ScreenUtil.STATUS_BAR_HEIGHT * 2,
                -10000, -10000);

        getViewDataBinding().flUserNameEt.setText("小坑神周杰伦");
        getViewDataBinding().flUserPassEt.setText("988815xy");
    }

    @Override
    public void requestData(Context context) {
        // 自定义ModelFactory创建ViewModel
        userViewModel = new ViewModelProvider(this, new SelfViewModelFactory(baseControlPresenter)).get(UserViewModel.class);
        // 监听Live数据变化
        userViewModel.getUserLiveData().observe(this, new Observer<UserInfoBean>() {
            @Override
            public void onChanged(UserInfoBean userInfoBean) {
                // 0.保存用户信息
                UserManager.saveUser(userInfoBean.getUsername(), userInfoBean.getToken(), "", "");

                // 1. 直接返回结果到跳转页面
                Intent intent = new Intent();
                intent.putExtra("user", "登录成功了，我叫" + userInfoBean.getUsername());
                requireActivity().setResult(RESULT_OK, intent);

                // 2. 用Eventbus通知其他页面
                // EventBus.getDefault().post(new MessageEvent(userInfoBean.getUsername()));

                // 3. 路由到主页面，然后切换到某个碎片，实现跨页面跳转
                //                ARouter.getInstance()
                //                        .build(ArouterPath.HOME_ACTIVITY)
                //                        .withInt(HomePath.WHICH, HomePath.HOME_PAGE)
                //                        .navigation();

                requireActivity().finish();
            }
        });
    }

    @Override
    public void eventHandler(Context context) {
        getViewDataBinding().flUserNameEt.addTextChangedListener(new TextInputEditTextWatcher(getViewDataBinding().flUserNameTL));
        getViewDataBinding().flUserPassEt.addTextChangedListener(new TextInputEditTextWatcher(getViewDataBinding().flUserPassTL));

        // 注册事件对象
        if (null == getViewDataBinding().getLoginHandler()) {
            getViewDataBinding().setLoginHandler(new LoginNavEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {
        if (t instanceof UserInfoBean) {
            UserInfoBean result = (UserInfoBean) t;
            userViewModel.getUserLiveData().setValue(result);
        }
    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
