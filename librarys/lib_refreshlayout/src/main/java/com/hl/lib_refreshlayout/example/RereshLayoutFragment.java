package com.hl.lib_refreshlayout.example;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hl.base_module.page.BaseFragment;
import com.hl.lib_refreshlayout.R;
import com.hl.lib_refreshlayout.databinding.FragmentTestForBinding;
import com.hl.lib_refreshlayout.handler.RefreshListenner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RereshLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RereshLayoutFragment extends BaseFragment<FragmentTestForBinding> {
    private List<RvBean> rvBeanList = new ArrayList<>();
    private RvAdatper rvAdatper;

    public RereshLayoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestForFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RereshLayoutFragment newInstance(String param1, String param2) {
        RereshLayoutFragment fragment = new RereshLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_test_for;
    }

    @Override
    public void initLayout(Context context) {

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
        rvAdatper = new RvAdatper(requireContext(), rvBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().ftfRv.setLayoutManager(linearLayoutManager);
        getViewDataBinding().ftfRv.setAdapter(rvAdatper);

        // 刷新回调监听
        getViewDataBinding().ftfRef.setOnRefreshListenner(new RefreshListenner() {
            @Override
            public void OnRefresh() {
                // 启动网络去请求数据，这里搞个线程模拟下
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 假设请求成功，此时开始加载数据(或者也可以直接结束进度条，去另外一个回调加载数据)
                        getViewDataBinding().ftfRef.finishRefresh(2);
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
                            getViewDataBinding().ftfRef.finishLoadMore(1);
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
                    getViewDataBinding().ftfRef.setNoMoreData();
                }
            }
        });
    }

    @Override
    public void eventHandler(Context context) {

    }
}
