package com.hl.modules_personal.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.modules_personal.R;
import com.hl.modules_personal.model.bean.OrderItemBean;

import java.util.List;

public class OrderListAdatper extends BaseMutilayoutAdapter<OrderItemBean> {
    public OrderListAdatper(Context context, List<OrderItemBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<OrderItemBean>(LayoutInflater.from(context).inflate(R.layout.fragment_order_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, OrderItemBean orderItemBean, int postion, int itemViewType) {
        addOnItemClickListener(baseHolder.getView(R.id.foi_chagneOrDelte), postion, orderItemBean, itemViewType, "修改收货地址");
        addOnItemClickListener(baseHolder.getView(R.id.foi_cuiWuliuAgain), postion, orderItemBean, itemViewType, "催促发货");
        addOnItemClickListener(baseHolder.getView(R.id.foi_orderSureOrEvaluate), postion, orderItemBean, itemViewType, "立即评价");
        addOnItemClickListener(baseHolder.getItemView(), postion, orderItemBean, itemViewType, "all");
    }
}
