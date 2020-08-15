package com.hl.module_location.view.event;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.module_location.view.AmapChooseActivity;

public class AmapChooseEventHandler {
    private Context mContext;

    public AmapChooseEventHandler(Context context){
        this.mContext = context;
    }

    /**
     * 跳转到城市选择界面
     */
    public void citySelect(){
        Postcard postcard = ARouter.getInstance()
                .build(ArouterPath.CITY_SELECTED)
                .withString("default_city", ((AmapChooseActivity)mContext).locationCityName);
        //.withParcelableArrayList("city_list", (ArrayList<? extends Parcelable>) city_list);
        LogisticsCenter.completion(postcard);
        Intent intent = new Intent(mContext, postcard.getDestination());
        intent.putExtras(postcard.getExtras());
        ((AppCompatActivity) mContext).startActivityForResult(intent, 110);

        // 带返回值的跳转
        /*List<CityListBean> city_list = new ArrayList<>();
        CityListBean cityListBean = new CityListBean();
        cityListBean.setName("皮皮虾");
        cityListBean.setLetter("P");
        city_list.add(cityListBean);
         */

        /*
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("city_list",
                (ArrayList<? extends Parcelable>) city_list);
        List<CityListBeanB> city_list2 = new ArrayList<>();
        CityListBeanB cityListBean2 = new CityListBeanB();
        cityListBean2.setName("皮皮虾");
        cityListBean2.setLetter("P");
        city_list2.add(cityListBean2);
        bundle.putParcelableArrayList("city_list2",
                (ArrayList<? extends Parcelable>) city_list2);
         */
    }
}
