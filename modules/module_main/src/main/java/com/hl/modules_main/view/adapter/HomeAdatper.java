package com.hl.modules_main.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.lib_miniui.view.progress.GiftProgress;
import com.hl.modules_main.R;
import com.hl.modules_main.model.bean.Home_Task_Item_Bean;

import java.util.List;

public class HomeAdatper extends BaseMutilayoutAdapter<Home_Task_Item_Bean> {
    public HomeAdatper(Context context, List<Home_Task_Item_Bean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<Home_Task_Item_Bean>(LayoutInflater.from(context).inflate(R.layout.fragment_home_gifts_layout, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, Home_Task_Item_Bean datasBean, int postion, int itemViewType) {
        // 步数奖品
        GiftProgress giftProgress = (GiftProgress) baseHolder.getView(R.id.fhgl_GiftPic);
        giftProgress.setImage(datasBean.getProduct().getPd_img());
        giftProgress.setProgress(1);
        // 步数总数
        //Text_Value fhgl_stepCount = (Text_Value) baseHolder.getView(R.id.fhgl_stepCount);
        //fhgl_stepCount.setText(datasBean.getTarget_step() + "步");
        baseHolder.setText(R.id.fhgl_stepCount, datasBean.getProduct().getTarget_step() + "步");
        // 多少人参与
        baseHolder.setText(R.id.fhgl_partake, datasBean.getPart_num() + "人参与");
        // 产品名称
        baseHolder.setText(R.id.fhgl_giftName, datasBean.getProduct().getPd_name());

        addOnItemClickListener(baseHolder.getView(R.id.fhgl_SpeedUp), postion, datasBean, itemViewType, null);
    }
}
