package com.hl.module_shoppingcart.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.lib_miniui.view.imageview.OutCircleLineImageView;
import com.hl.module_shoppingcart.R;
import com.hl.module_shoppingcart.model.bean.GettedGiftItemBean;

import java.util.List;

public class GettedGitfAdatper extends BaseMutilayoutAdapter<GettedGiftItemBean> {
    public GettedGitfAdatper(Context context, List<GettedGiftItemBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<GettedGiftItemBean>(LayoutInflater.from(context).inflate(R.layout.fragment_walk_gift_list_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, GettedGiftItemBean gettedGiftItemBean, int postion, int itemViewType) {
        OutCircleLineImageView outCircleLineImageView = (OutCircleLineImageView) baseHolder.getView(R.id.fwli_GiftCircleView);
        outCircleLineImageView.setImage("");
    }
}
