package com.hl.modules_personal.view.activity;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.ActivityLogisticsInfoBinding;
import com.hl.modules_personal.model.bean.LogisticsItemBean;
import com.hl.modules_personal.view.adapter.LogisticsListAdatper;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.USER_LOGITICS_PAGE)
public class LogisticsInfoActivity extends BaseWithServiceActivity<ActivityLogisticsInfoBinding> {
    private LogisticsListAdatper logisticsListAdatper;
    private List<LogisticsItemBean> logisticsItemBeanList = new ArrayList<>();

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
        return "查看物流";
    }

    @Override
    public int setLayout() {
        return R.layout.activity_logistics_info;
    }

    @Override
    public void initLayout(Context context) {
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsItemBeanList.add(new LogisticsItemBean());
        logisticsListAdatper = new LogisticsListAdatper(this, logisticsItemBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().alifWuliuList.setLayoutManager(linearLayoutManager);
        getViewDataBinding().alifWuliuList.setAdapter(logisticsListAdatper);
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
