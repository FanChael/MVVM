package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityOdersBinding;
import com.hl.modules_personal.view.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.USER_ODERS_PAGE)
public class OdersActivity extends BaseWithServiceActivity<ActivityOdersBinding> {
    private List<String> titles = new ArrayList<>();
    {
        titles.add("全部领取");
        titles.add("步数领取");
        titles.add("活动领取");
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    protected int getBgColor() {
        return ContextCompat.getColor(this, R.color.deep_more_gray);
    }

    @Override
    protected String setTitle() {
        return "领取订单";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_oders;
    }

    @Override
    public void initLayout(Context context) {
        // 绑定碎片
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(OrderFragment.newInstance("all", "全部领取"));
        fragments.add(OrderFragment.newInstance("step", "步数领取"));
        fragments.add(OrderFragment.newInstance("active", "活动领取"));
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
        getViewDataBinding().aodOrderListVp2.setAdapter(fragmentStateAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(getViewDataBinding().aodOrdersTitles,
                getViewDataBinding().aodOrderListVp2, (tab, position) -> {
            getViewDataBinding().aodOrdersTitles.setTableTitles(tab,
                    titles.get(position), 0 == position ? true : false);
        });
        tabLayoutMediator.attach();
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {

    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
