package com.hl.module_location.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.module_location.R;
import com.hl.module_location.model.bean.AddressListBean;
import com.hl.module_location.model.bean.ExternPoiItem;

import java.util.List;

public class AmapListAdapter extends BaseMutilayoutAdapter<ExternPoiItem> {
    private int selectedPosition = 0;

    public AmapListAdapter(Context context, List<ExternPoiItem> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<AddressListBean>(LayoutInflater.from(context).inflate(R.layout.amap_view_holder_result, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, ExternPoiItem poiItem, int postion, int itemViewType) {
        baseHolder.setText(R.id.avhr_Title, poiItem.getPoiItem().getTitle());
        baseHolder.setText(R.id.avhr_subTitle,
                (null == poiItem.getPoiItem().getCityName() ? "" : poiItem.getPoiItem().getCityName()) +
                        (null == poiItem.getPoiItem().getAdName() ? "" : poiItem.getPoiItem().getAdName()) +
                        poiItem.getPoiItem().getSnippet());
        if (postion == selectedPosition) {
            baseHolder.setTextColor(R.id.avhr_Title, Color.parseColor("#ff3098ee"));
            baseHolder.setDrawable(context, R.id.avhr_IconStateChange, R.drawable.add_icon_add_s);
        } else {
            baseHolder.setTextColor(R.id.avhr_Title, Color.parseColor("#ff333333"));
            baseHolder.setDrawable(context, R.id.avhr_IconStateChange, R.drawable.add_icon_circle);
        }
        addOnItemClickListener(baseHolder.getItemView(), postion, poiItem, itemViewType, null);
    }
}
