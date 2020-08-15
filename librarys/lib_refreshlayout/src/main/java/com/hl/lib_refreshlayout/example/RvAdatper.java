package com.hl.lib_refreshlayout.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.lib_refreshlayout.R;

import java.util.List;

public class RvAdatper extends BaseMutilayoutAdapter<RvBean> {
    public RvAdatper(Context context, List<RvBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<RvBean>(LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, RvBean rvBean, int postion, int itemViewType) {
        baseHolder.setText(R.id.rl_title, rvBean.getTitle());
    }
}
