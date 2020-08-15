package com.hl.module_shoppingcart.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hl.base_module.page.BaseWithServiceFragment;
import com.hl.module_shoppingcart.R;
import com.hl.module_shoppingcart.databinding.FragmentWalkGiftBinding;
import com.hl.module_shoppingcart.model.bean.GettedGiftItemBean;
import com.hl.module_shoppingcart.view.adapter.GettedGitfAdatper;
import com.hl.module_shoppingcart.view.event.ShopCartEventHandler;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalkGiftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkGiftFragment extends BaseWithServiceFragment<FragmentWalkGiftBinding> {
    private GettedGitfAdatper gettedGitfAdatper;
    private List<GettedGiftItemBean> gettedGiftItemBeans = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalkGiftFragment.
     */
    public static WalkGiftFragment newInstance(String param1, String param2) {
        WalkGiftFragment fragment = new WalkGiftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_walk_gift;
    }

    @Override
    public void initLayout(Context context) {
        GettedGiftItemBean gettedGiftItemBean = new GettedGiftItemBean();
        gettedGiftItemBeans.add(gettedGiftItemBean);
        gettedGiftItemBeans.add(gettedGiftItemBean);
        gettedGiftItemBeans.add(gettedGiftItemBean);
        gettedGiftItemBeans.add(gettedGiftItemBean);
        gettedGiftItemBeans.add(gettedGiftItemBean);
        gettedGiftItemBeans.add(gettedGiftItemBean);
        // 初始化适配器并附近到RV
        gettedGitfAdatper = new GettedGitfAdatper(requireContext(), gettedGiftItemBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().fwgGitfListRv.setLayoutManager(linearLayoutManager);
        getViewDataBinding().fwgGitfListRv.setAdapter(gettedGitfAdatper);
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getShopcartEvent()) {
            getViewDataBinding().setShopcartEvent(new ShopCartEventHandler());
        }
        getViewDataBinding().fwgSmartRefresh.setEnableLoadMore(false);
        getViewDataBinding().fwgSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
