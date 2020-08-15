package com.hl.lib_refreshlayout.example;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.lib_refreshlayout.R;
import com.hl.lib_refreshlayout.databinding.ActivityRereshLayoutBinding;
import com.hl.lib_refreshlayout.handler.RefreshListenner;

import java.util.ArrayList;
import java.util.List;

public class RereshLayoutActivity extends BaseWithServiceActivity<ActivityRereshLayoutBinding> {
    private ActivityRereshLayoutBinding activityRereshLayoutBinding;
    private RvAdatper rvAdatper;
    private List<RvBean> rvBeanList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_reresh_layout;
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public void initLayout(Context context) {
        activityRereshLayoutBinding = getViewDataBinding();
    }

    @Override
    public void requestData(Context context) {
        RvBean rvBean = new RvBean();
        rvBean.setTitle("放大镜分类点击发了多少附近的说法!");
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvBeanList.add(rvBean);
        rvAdatper = new RvAdatper(this, rvBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        activityRereshLayoutBinding.arlRv.setLayoutManager(linearLayoutManager);
        activityRereshLayoutBinding.arlRv.setAdapter(rvAdatper);

        // 刷新回调监听
        activityRereshLayoutBinding.arlRef.setOnRefreshListenner(new RefreshListenner() {
            @Override
            public void OnRefresh() {
                // 启动网络去请求数据，这里搞个线程模拟下
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 假设请求成功，此时开始加载数据(或者也可以直接结束进度条，去另外一个回调加载数据)
                        activityRereshLayoutBinding.arlRef.finishRefresh(2);
                    }
                }).start();
            }

            @Override
            public void OnRefreshFinish() {
                rvBeanList.clear();
                RvBean rvBean = new RvBean();
                rvBean.setTitle("刷新了，刷新了!");
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvAdatper.notifyDataSetChanged();
            }

            @Override
            public void OnLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2);
                            // 假设请求成功，此时开始加载数据(或者也可以直接结束进度条，去另外一个回调加载数据)
                            activityRereshLayoutBinding.arlRef.finishLoadMore(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void OnLoadMoreFinish() {
                RvBean rvBean = new RvBean();
                rvBean.setTitle("新添加的，好伐!");
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvBeanList.add(rvBean);
                rvAdatper.notifyDataSetChanged();

                // 数据大于30就不再加载更多数据了
                if (rvBeanList.size() > 30) {
                    activityRereshLayoutBinding.arlRef.setNoMoreData();
                }
            }
        });
    }

    @Override
    public void eventHandler(Context context) {

    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    public void onFailed(String _functionName, String _message) {

    }
}
