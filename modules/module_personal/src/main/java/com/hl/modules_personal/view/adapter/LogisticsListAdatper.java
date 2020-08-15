package com.hl.modules_personal.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.modules_personal.R;
import com.hl.modules_personal.model.bean.LogisticsItemBean;

import java.util.List;

public class LogisticsListAdatper extends BaseMutilayoutAdapter<LogisticsItemBean> {
    public LogisticsListAdatper(Context context, List<LogisticsItemBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<LogisticsItemBean>(LayoutInflater.from(context).inflate(R.layout.activity_logistics_info_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, LogisticsItemBean logisticsItemBean, int postion, int itemViewType) {
        // 对线隐藏显示的控制
        if (0 == postion) {
            baseHolder.setBackGround(context, R.id.alit_dot, R.drawable.circle_green_dot_shape);
            baseHolder.setVisible(R.id.alit_dotLineTop, ViewGroup.GONE);
        } else {
            baseHolder.setBackGround(context, R.id.alit_dot, R.drawable.circle_gray_dot_shape);
            baseHolder.setVisible(R.id.alit_dotLineTop, ViewGroup.VISIBLE);
        }
        if (postion == (getItemCount() - 1)) {
            baseHolder.setVisible(R.id.alit_dotLineBottom, ViewGroup.GONE);
        } else {
            baseHolder.setVisible(R.id.alit_dotLineBottom, ViewGroup.VISIBLE);
        }
        addOnItemClickListener(baseHolder.getItemView(), postion, logisticsItemBean, itemViewType, null);
    }
}
