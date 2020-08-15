package com.hl.module_location.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.lib_miniui.view.texts.Text_LightBg;
import com.hl.module_location.R;
import com.hl.module_location.model.bean.AddressListBean;

import java.util.List;

public class AddressListAdapter extends BaseMutilayoutAdapter<AddressListBean> {
    public AddressListAdapter(Context context, List<AddressListBean> datas) {
        super(context, datas);
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<AddressListBean>(LayoutInflater.from(context).inflate(R.layout.activity_location_list_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {
    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, AddressListBean addressList, int postion, int itemViewType) {
        baseHolder.setText(R.id.alli_addrName, addressList.getAddressName());
        baseHolder.setText(R.id.alli_addrPhone, addressList.getAddressPhone());
        baseHolder.setText(R.id.alli_addrSiteInfo, addressList.getSiteInfo());
        // 是否是默认地址
        if (1 == addressList.getIsDefault()) {
            baseHolder.setVisible(R.id.alli_isDefaultPic, View.VISIBLE);
            baseHolder.setVisible(R.id.alli_addrDefultState, View.VISIBLE);
        } else {
            baseHolder.setVisible(R.id.alli_isDefaultPic, View.GONE);
            baseHolder.setVisible(R.id.alli_addrDefultState, View.GONE);
        }
        if (!TextUtils.isEmpty(addressList.getLabelName())){
            Text_LightBg text_lightBg = (Text_LightBg) baseHolder.getView(R.id.alli_addrHomeCompany);
            text_lightBg.updateText(addressList.getLabelName());
        }
        addOnItemClickListener(baseHolder.getView(R.id.alli_editorPic), postion, addressList, itemViewType, null);
    }
}
