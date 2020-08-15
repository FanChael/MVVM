package com.hl.modules_personal.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.modules_personal.R;
import com.hl.modules_personal.model.bean.DongdouItemBean;
import com.hl.modules_personal.model.bean.OrderItemBean;

import java.util.List;

public class DongdouListAdatper extends BaseMutilayoutAdapter<DongdouItemBean> {
    public DongdouListAdatper(Context context, List<DongdouItemBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<OrderItemBean>(LayoutInflater.from(context).inflate(R.layout.activity_dongdou_list_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, DongdouItemBean dongdouItemBean, int postion, int itemViewType) {
    }
}
