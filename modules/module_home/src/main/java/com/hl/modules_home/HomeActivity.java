package com.hl.modules_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.constant.HomePath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.app.ToastUtil;
import com.hl.lib_miniui.view.toggle.SToggleButton;
import com.hl.modules_home.databinding.ActivityHomeBinding;

import java.util.HashMap;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

//@Route(path = ArouterPath.HOME_ACTIVITY)
public class HomeActivity extends BaseWithServiceActivity<ActivityHomeBinding> {
    private TabLayoutMediator tabLayoutMediator;
    private HashMap<Integer, Fragment> fragmentList = new HashMap<>();

    @Autowired
    public String scheme = "";

    @Override
    public int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initLayout(Context context) {
        // 加载碎片
        fragmentList.put(HomePath.HOME_PAGE, (Fragment) ARouter.getInstance().build(ArouterPath.HOME_FRAGMENT).navigation());
        fragmentList.put(HomePath.PERSONAL_PAGE, (Fragment) ARouter.getInstance().build(ArouterPath.PERSONAL_FRAGMENT).navigation());
        // 碎片容纳数量，防止切换销毁
        getViewDataBinding().ahContentVp.setOffscreenPageLimit(2);
        // @添加到ViewPaper
        getViewDataBinding().ahContentVp.setAdapter(new FragmentPagerAdapter(
                getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return null == fragmentList ? 0 : fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case HomePath.HOME_PAGE:
                        return "首页";
                    case HomePath.PERSONAL_PAGE:
                        return "个人中心";
                }
                return "暂无";
            }

            // @添加到ViewPaper2
            //            @NonNull
            //            @Override
            //            public Fragment createFragment(int position) {
            //                return fragmentList.get(position);
            //            }
            //
            //            @Override
            //            public int getItemCount() {
            //                return null == fragmentList ? 0 : fragmentList.size();
            //            }
        });
        // @添加到ViewPaper2
        //        tabLayoutMediator = new TabLayoutMediator(getViewDataBinding().ahBottomTbL, getViewDataBinding().ahContentVp, new TabLayoutMediator.TabConfigurationStrategy() {
        //            @Override
        //            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        //                switch (position) {
        //                    case 0:
        //                        tab.setText("首页");
        //                        tab.setIcon(R.drawable.bottom_home_selector);
        //                        break;
        //                    case 1:
        //                        tab.setText("个人中心");
        //                        tab.setIcon(R.drawable.bottom_personal_selector);
        //                        break;
        //                }
        //            }
        //        });
        //        tabLayoutMediator.attach();
        // @添加到ViewPaper
        getViewDataBinding().ahBottomTbL.setupWithViewPager(getViewDataBinding().ahContentVp);
        // @设置Icon
        int resId = -1;
        for (int i = 0; i < getViewDataBinding().ahBottomTbL.getTabCount(); ++i) {
            switch (i) {
                case 0:
                    resId = R.drawable.bottom_walk_selector;
                    break;
                case 1:
                    resId = R.drawable.bottom_personal_selector;
                    break;
            }
            if (-1 != resId){
                getViewDataBinding().ahBottomTbL.getTabAt(i).setIcon(resId);
            }
        }
        // 默认选中第几个
        getViewDataBinding().ahBottomTbL.getTabAt(HomePath.HOME_PAGE).select();
        Log.e(TAG, null == scheme ? "no scheme" : scheme);
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(final Context context) {
        // 竖直togle开关事件监听
        getViewDataBinding().ahStogBtn.registerOnCheckedState(new SToggleButton.OnCheckedStateListenner() {
            @Override
            public void onStateCheckd(boolean bChecked) {
                ToastUtil.showTost("干啥妮=" + bChecked, true);
            }
        });

        // 切换碎片【可以隐藏一些控件】
        getViewDataBinding().ahStogBtn.setVisibility(View.GONE);
        getViewDataBinding().ahBottomTbL.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (1 == tab.getPosition()) {
                    getViewDataBinding().ahStogBtn.setVisibility(View.GONE);
                } else if (0 == tab.getPosition()) {
                    getViewDataBinding().ahStogBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 路由处理
        if (null != intent && null != intent.getExtras()) {
            Bundle bundle = intent.getExtras();
            switch (bundle.getInt(HomePath.WHICH, -1)) {
                case HomePath.HOME_PAGE:
                    getViewDataBinding().ahBottomTbL.getTabAt(HomePath.HOME_PAGE).select();
                    break;
                case HomePath.PERSONAL_PAGE:
                    getViewDataBinding().ahBottomTbL.getTabAt(HomePath.PERSONAL_PAGE).select();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != tabLayoutMediator) {
            tabLayoutMediator.detach();
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
