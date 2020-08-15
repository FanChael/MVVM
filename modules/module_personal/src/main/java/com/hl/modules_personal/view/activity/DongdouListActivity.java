package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityDongdouListBinding;
import com.hl.modules_personal.model.bean.DongdouItemBean;
import com.hl.modules_personal.view.adapter.DongdouListAdatper;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.DONGDOU_LIST)
public class DongdouListActivity extends BaseWithServiceActivity<ActivityDongdouListBinding> {
    private List<DongdouItemBean> dongdouItemBeanList = new ArrayList<>();
    private DongdouListAdatper dongdouListAdatper;

    @Override
    protected String setTitle() {
        return "动豆收支明细";
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_dongdou_list;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return getViewDataBinding().addlSmartRf;
    }

    @Override
    public void initLayout(Context context) {
        dongdouItemBeanList.add(new DongdouItemBean());
        dongdouItemBeanList.add(new DongdouItemBean());
        dongdouItemBeanList.add(new DongdouItemBean());
        dongdouItemBeanList.add(new DongdouItemBean());
        dongdouItemBeanList.add(new DongdouItemBean());

        dongdouListAdatper = new DongdouListAdatper(this, dongdouItemBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().addlSmartRv.setLayoutManager(linearLayoutManager);
        getViewDataBinding().addlSmartRv.setAdapter(dongdouListAdatper);
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
