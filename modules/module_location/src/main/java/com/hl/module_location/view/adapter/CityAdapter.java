package com.hl.module_location.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hl.base_module.adapter.BaseMulViewHolder;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.module_location.R;
import com.hl.module_location.model.bean.CityListBean;

import java.util.List;

/**
 * Created by hl on 2018/3/14.
 */

public class CityAdapter extends BaseMutilayoutAdapter<CityListBean> {
    private List<CityListBean> datas;
    public CityAdapter(Context context, List<CityListBean> datas) {
        super(context, datas);
        this.datas = datas;
    }

    @Override
    protected BaseMulViewHolder getHolder(Context context, ViewGroup parent, int viewType) throws Exception {
        return new BaseMulViewHolder<CityListBean>(LayoutInflater.from(context).inflate(R.layout.cityadapter_item, parent, false));
    }

    @Override
    protected void handleHolder(Context context, BaseMulViewHolder baseHolder, int viewType) {

    }

    @Override
    protected void bindData(Context context, BaseMulViewHolder baseHolder, CityListBean cityListBean, int postion, int itemViewType) {
        if (cityListBean.getName().equals("啊")){
            baseHolder.setText(R.id.cai_titleTv, "全部");
        }else{
            baseHolder.setText(R.id.cai_titleTv, cityListBean.getName());
        }
        addOnItemClickListener(baseHolder.getItemView(), postion, cityListBean, itemViewType, null);
    }

    /**
     * 获取字母大概的显示位置
     * @param letter
     * @return
     */
    public int getLetterPos(String letter){
        int letterPos = -1;
        for (int i = 0; i < datas.size(); ++i){
            if (datas.get(i).getLetter().equals(letter)){
                letterPos = i;
                break;
            }
        }
        return letterPos;
    }
}
