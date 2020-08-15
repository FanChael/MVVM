package com.hl.module_location.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseActivity;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.storage.ResourceUtil;
import com.hl.module_location.R;
import com.hl.module_location.databinding.ActivityCitySelectBinding;
import com.hl.module_location.model.bean.CityListBean;
import com.hl.module_location.view.adapter.CityAdapter;
import com.hl.module_location.view.wave.WaveSideBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *@Description: 城市索引界面
 *@Author: hl
 *@Time: 2019/3/28 15:43
 */
@Route(path = ArouterPath.CITY_SELECTED)
public class CitySelectActivity extends BaseActivity<ActivityCitySelectBinding> {
    private CityAdapter adapter;
    @Autowired
    public List<CityListBean> city_list;
    @Autowired
    public String default_city;
    private static final String[] hot_cityName = {
            "北京", "上海", "广州", "深圳",
            "成都", "杭州", "南京", "苏州",
            "重庆", "天津", "武汉", "西安"
    };
    private List<CityListBean> hot_list = new ArrayList<>();

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_city_select;
    }

    @Override
    public void initLayout(Context context) {
        // 输入框需要设置顶部间距
        ScreenUtil.setStatusBarHeightTop(getViewDataBinding().acsSearchRoot);

        // 没有参数则自行构造一个城市列表
        if (null == city_list) {
            city_list = (null != getIntent().getExtras() ?
                    getIntent().getExtras().getParcelableArrayList("city_list") : null);
            if (null == city_list) {
                city_list = new ArrayList<>();
                // 加载本地城市列表数据
                String cityJson = ResourceUtil.readAssertResource(CitySelectActivity.this, "citys.json");
                // 直接原生解析
                try {
                    JSONObject jsonObject = new JSONObject(cityJson);
                    JSONArray jsonArray = jsonObject.getJSONArray("provinces");
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("citys");
                        for (int j = 0; j < jsonArray1.length(); ++j) {
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                            String cityName = jsonObject2.getString("citysName");
                            CityListBean cityListBean = new CityListBean();
                            cityListBean.setId("" + i + "#" + j);
                            cityListBean.setName(cityName);
                            city_list.add(cityListBean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // 创建线性的布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getViewDataBinding().acsCityRv.setLayoutManager(linearLayoutManager);
        ///< 增加悬浮标题 - PowerGroupListener
        PowerGroupListener groupListener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                return city_list.get(position).getLetter();
            }

            @Override
            public View getGroupView(int position) {
                ///< 获取自定定义的组View
                View view = getLayoutInflater().inflate(R.layout.cityadapter_title_item, null, false);
                TextView titleTv = view.findViewById(R.id.cai_titleTv);
                titleTv.setText(city_list.get(position).getLetter());
                return view;
            }
        };
        //PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder - StickyDecoration
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(groupListener)
                //重置span（使用GridLayoutManager时必须调用）
                //.resetSpan(mRecyclerView, (GridLayoutManager) manager)
                .setGroupBackground(ContextCompat.getColor(context, R.color.little_gray))
                .build();
        ///< 需要在setLayoutManager()之后调用addItemDecoration()
        getViewDataBinding().acsCityRv.addItemDecoration(decoration);
        ///< 适配器
        adapter = new CityAdapter(this, city_list);
        // 设置适配器
        getViewDataBinding().acsCityRv.setAdapter(adapter);
        // 设置默认城市
        getViewDataBinding().acsAmapCityName.setText(default_city);
        // 添加热门城市
        initHotCitys();
    }

    /**
     * 添加热门城市
     */
    private void initHotCitys() {
        // Can nothing to do like this
        /*
        hot_list.clear();
        for (int i = 0; i < hot_cityName.length; ++i) {
            CityListBean cityListBean = new CityListBean();
            cityListBean.setId("" + i);
            cityListBean.setName(hot_cityName[i]);
            hot_list.add(cityListBean);
        }
         */
        // 直接添加到组件就行了，不需要id等信息
        getViewDataBinding().acsHotCitys.addRadioButton(this,
                Arrays.asList(hot_cityName), 32, 4);
    }

    @Override
    public void requestData(Context context) {
        ///< 按照中文拼音首字母排序
        //Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        //Collections.sort(city_list, comparator);
        Collections.sort(city_list, new Comparator<CityListBean>() {
            @Override
            public int compare(CityListBean o1, CityListBean o2) {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(o1.getName(), o2.getName());
            }
        });
        /*全部不要
        CityListBean cityListBeanA = new CityListBean();
        cityListBeanA.setId("0");
        cityListBeanA.setName("全部");
        city_list.add(0, cityListBeanA);
        */
    }

    @Override
    public void eventHandler(Context context) {
        getViewDataBinding().acsSideView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPos(letter);
                if (pos != -1) {
                    //cityRv.scrollToPosition(pos);
                    ///< 滚动并置顶
                    ((LinearLayoutManager) getViewDataBinding().acsCityRv.getLayoutManager()).scrollToPositionWithOffset(pos, 0);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseMutilayoutAdapter.OnItemClickListener<CityListBean>() {
            @Override
            public void onClick(View v, int position, CityListBean cityListBean, int itemViewType, Object externParams) {
                Intent data = new Intent();
                data.putExtra("city_name", cityListBean.getName());
                setResult(RESULT_OK, data);
                finish();
            }
        });
        // 热门城市选择
        getViewDataBinding().acsHotCitys.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent data = new Intent();
                data.putExtra("city_name", hot_cityName[checkedId]);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
