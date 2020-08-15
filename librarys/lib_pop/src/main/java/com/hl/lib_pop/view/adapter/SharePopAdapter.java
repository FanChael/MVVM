package com.hl.lib_pop.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.lib_pop.R;
import com.hl.lib_pop.view.bean.PicInfoBean;

import java.util.List;

public class SharePopAdapter extends BaseMutilayoutAdapter<PicInfoBean> {
    public SharePopAdapter(Context context, List<PicInfoBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<PicInfoBean>(LayoutInflater.from(context)
                .inflate(R.layout.dialog_fragment_share_pic_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {
        // 根据屏幕宽度和间距，调整图片显示宽高
        AppCompatImageView dfspi_gitfPic = (AppCompatImageView) baseHolder.getView(R.id.dfspi_gitfPic);
        ScreenUtil.setConstraintLayoutWH(dfspi_gitfPic,
                (ScreenUtil.SCREEN_WIDTH - DensityUtil.dip2px(context, 15) * 4 - DensityUtil.dip2px(context, 6)) / 3,
                (ScreenUtil.SCREEN_WIDTH - DensityUtil.dip2px(context, 15) * 4 - DensityUtil.dip2px(context, 6)) / 3);
    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, PicInfoBean s, int postion, int itemViewType) {
        baseHolder.setPic(context, R.id.dfspi_gitfPic, s.getUrl());
    }
}
