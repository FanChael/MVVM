package com.hl.module_shoppingcart.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.module_pay.event.PayEventHandler;
import com.hl.module_shoppingcart.R;
import com.hl.module_shoppingcart.databinding.ActivityShoppingBinding;
import com.hl.module_shoppingcart.view.fragment.WalkGiftFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.SHOPING_CAR)
public class ShoppingCartActivity extends BaseWithServiceActivity<ActivityShoppingBinding> {
    private List<String> titles = new ArrayList<>();
    {
        titles.add("运动得礼");
        titles.add("活动得礼");
    }

    @Override
    public int getBgColor(){
        return ContextCompat.getColor(this, R.color.bg_gray);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shopping;
    }

    @Override
    public void initLayout(Context context) {
        // 距离顶部间距走一走
        ScreenUtil.setStatusBarHeightTop(getViewDataBinding().aspTitleBar);

        // 设置标题 采用TabLayoutMediator的话，需要内部处理样式
        // getViewDataBinding().aspTablayout.setTableTitles(titles);

        // 绑定碎片
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WalkGiftFragment.newInstance("walk", "运动得礼"));
        fragments.add(WalkGiftFragment.newInstance("active", "活动得礼"));
        FragmentStateAdapter fragmentStateAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return null == fragments ? 0 : fragments.size();
            }
        };
        getViewDataBinding().aspContentV.setAdapter(fragmentStateAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(getViewDataBinding().aspTablayout,
                getViewDataBinding().aspContentV, (tab, position) -> {
            getViewDataBinding().aspTablayout.setTableTitles(tab,
                    titles.get(position), 0 == position ? true : false);
        });
        tabLayoutMediator.attach();
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getPayEvent()) {
            getViewDataBinding().setPayEvent(new PayEventHandler(this).setPayGo(new PayEventHandler.PayGo() {
                @Override
                public void startWxPay() {

                }

                @Override
                public void startAliPay() {

                }

                @Override
                public void message(String msg) {

                }
            }));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
