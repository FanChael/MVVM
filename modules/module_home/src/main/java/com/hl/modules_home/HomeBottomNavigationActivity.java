package com.hl.modules_home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.constant.HomePath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.lib_miniui.view.texts.Text_Badge;
import com.hl.modules_home.databinding.ActivityHomeBottomnavigationBinding;

import java.util.HashMap;

@Route(path = ArouterPath.HOME_ACTIVITY)
public class HomeBottomNavigationActivity extends BaseWithServiceActivity<ActivityHomeBottomnavigationBinding> {
    private HashMap<Integer, Fragment> fragmentList = new HashMap<>();

    @Autowired
    public String scheme = "";

    @Override
    public int setLayout() {
        return R.layout.activity_home_bottomnavigation;
    }

    @Override
    public void initLayout(Context context) {
        // 加载碎片
        fragmentList.put(HomePath.HOME_PAGE, (Fragment) ARouter.getInstance().build(ArouterPath.HOME_FRAGMENT).navigation());
        fragmentList.put(HomePath.PERSONAL_PAGE, (Fragment) ARouter.getInstance().build(ArouterPath.PERSONAL_FRAGMENT).navigation());

        // 禁止左右滑动
        getViewDataBinding().ahbContentVp.setUserInputEnabled(false);
        // 碎片容纳数量，防止切换销毁
        getViewDataBinding().ahbContentVp.setOffscreenPageLimit(2);
        getViewDataBinding().ahbContentVp.setAdapter(new FragmentStateAdapter(
                getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return null == fragmentList ? 0 : fragmentList.size();
            }
        });

        // 取消导航栏图标着色
        getViewDataBinding().ahbBottomBNv.setItemIconTintList(null);

        // a.加载底部菜单
        getViewDataBinding().ahbBottomBNv.inflateMenu(R.menu.navigation_bottom);
        // b.设置图片距离顶部的间距
        //BottomNavigationViewHelper.setImageMarginTop(getViewDataBinding().ahbBottomBNv, DensityUtil.dip2px(15));
        // c.底部导航添加角标 - 暂时不用
        // initBadge();

        Log.e(TAG, null == scheme ? "no scheme" : scheme);
    }

    /**
     * 初始化角标
     */
    private void initBadge() {
        // 获取整个的NavigationView
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) getViewDataBinding().ahbBottomBNv.getChildAt(0);

        // 这里就是获取所添加的每一个Tab(或者叫menu)，
        BottomNavigationItemView bottomNavigationItemView0 = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(0);
        // 加载我们的角标View，新创建的一个布局
        View badge0 = LayoutInflater.from(this).inflate(R.layout.menu_badge, bottomNavigationMenuView, false);
        // 添加到Tab上
        bottomNavigationItemView0.addView(badge0);
        // 设置数量为空字符串或者下面代码不使用，则紧紧是绘制圆点
        Text_Badge text_badge0 = badge0.findViewById(R.id.menu_badge_msg_count);
        text_badge0.setText("");
        text_badge0.setVisibility(View.VISIBLE);

        // 这里就是获取所添加的每一个Tab(或者叫menu)，
        BottomNavigationItemView bottomNavigationItemView1 = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(1);
        // 加载我们的角标View，新创建的一个布局
        View badge1 = LayoutInflater.from(this).inflate(R.layout.menu_badge, bottomNavigationMenuView, false);
        // 添加到Tab上
        bottomNavigationItemView1.addView(badge1);
        // 设置数量
        Text_Badge text_badge1 = badge1.findViewById(R.id.menu_badge_msg_count);
        text_badge1.setText("22");
        text_badge1.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestData(Context context) {
    }

    @Override
    public void eventHandler(final Context context) {
        // 底部菜单点击切换Viewpaper
        getViewDataBinding().ahbBottomBNv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_main) {
                    getViewDataBinding().ahbContentVp.setCurrentItem(0);
                } else if (item.getItemId() == R.id.navigation_personal) {
                    getViewDataBinding().ahbContentVp.setCurrentItem(1);
                }
                return true;
            }
        });

        // Viewpaper滑动切换底部菜单
        getViewDataBinding().ahbContentVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getViewDataBinding().ahbBottomBNv.getMenu().getItem(position).setChecked(true);
                // b.设置图片大小 - 属性里面可以设置，看xml注释信息
                // BottomNavigationViewHelper.setImageSize(getViewDataBinding().ahbBottomBNv, 20, 20);
                //BottomNavigationViewHelper.setImageMarginTop(getViewDataBinding().ahbBottomBNv, DensityUtil.dip2px(15));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 底部菜单再次点击事件回调 - 这里我们可以做转圈刷新当前页面的效果
        //        getViewDataBinding().ahbBottomBNv.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
        //            @Override
        //            public void onNavigationItemReselected(@NonNull MenuItem item) {
        //                int itemId = item.getItemId();
        //                if (itemId == R.id.navigation_main) {
        //                    // 再次点击首页，通知首页进行刷新
        //                    EventBus.getDefault().post(new MessageEvent("再次点击刷新", "update_home"));
        //                    BottomNavigationViewHelper.replaceRefreshImage(getViewDataBinding().ahbBottomBNv, 0, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589021367118&di=06f571cc13c0aca8f6b4afe85a328b24&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170914%2F6e33a4c4ff5d4ce2b6ce0cd8523c3733.gif");
        //                } else if (itemId == R.id.navigation_personal) {
        //                }
        //            }
        //        });

        // 设置默认选中哪个碎片
        getViewDataBinding().ahbContentVp.setCurrentItem(0);
    }

    //    /**
    //     * 收到刷新
    //     *
    //     * @param messageEvent
    //     */
    //    @Subscribe(threadMode = ThreadMode.MAIN)
    //    public void updateRefreshFinish(MessageEvent messageEvent) {
    //        if (messageEvent.getObject().equals("update_home_finish")) {
    //            // 可以模拟请求成功后加载网络图标
    //            BottomNavigationViewHelper.replaceRefreshImage(getViewDataBinding().ahbBottomBNv,
    //                    1, "https://file01.16sucai.com/d/file/2012/1023/20121023075322530.png");
    //            BottomNavigationViewHelper.replaceRefreshImage(getViewDataBinding().ahbBottomBNv, 0, "https://file01.16sucai.com/d/file/2012/1023/20121023075322560.png");
    //        }
    //    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 路由处理
        if (null != intent && null != intent.getExtras()) {
            Bundle bundle = intent.getExtras();
            switch (bundle.getInt(HomePath.WHICH, -1)) {
                case HomePath.HOME_PAGE:
                    getViewDataBinding().ahbContentVp.setCurrentItem(HomePath.HOME_PAGE);
                    getViewDataBinding().ahbBottomBNv.getMenu().getItem(HomePath.HOME_PAGE).setChecked(true);
                    break;
                case HomePath.PERSONAL_PAGE:
                    getViewDataBinding().ahbContentVp.setCurrentItem(HomePath.PERSONAL_PAGE);
                    getViewDataBinding().ahbBottomBNv.getMenu().getItem(HomePath.PERSONAL_PAGE).setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
