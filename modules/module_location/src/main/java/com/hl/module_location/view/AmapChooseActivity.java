package com.hl.module_location.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseActivity;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.softkeyboard.SoftKeyBoardUtil;
import com.hl.base_module.util.system.PermissionUtil;
import com.hl.module_location.R;
import com.hl.module_location.databinding.ActivityAmapChooseBinding;
import com.hl.module_location.model.bean.ExternPoiItem;
import com.hl.module_location.view.adapter.AmapListAdapter;
import com.hl.module_location.view.event.AmapChooseEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 高德地区点选
*@Author: hl
*@Date: created at 2020/6/12 11:41
*@Description: com.hl.module_location.view
*/
@Route(path = ArouterPath.ADDRESS_MAP_VIEW)
public class AmapChooseActivity extends BaseActivity<ActivityAmapChooseBinding> implements LocationSource,
        AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {
    private AutoCompleteTextView searchText;
    private AMap aMap;
    private MapView mapView;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Marker locationMarker;
    private ProgressBar progressBar = null;
    private GeocodeSearch geocoderSearch;
    private List<PoiItem> poiItems;// poi数据
    private PoiSearch.Query query;// Poi查询条件类
    private LatLonPoint searchLatlonPoint;
    private boolean isItemClickAction;

    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiItem firstItem;
    private boolean isInputKeySearch;
    private String inputSearchKey;
    private AmapListAdapter searchResultAdapter;
    private List<ExternPoiItem> resultData = new ArrayList<>();
    private PoiSearch poiSearch;
    // 目前不区分类型
    private String[] items = {"住宅区", "学校", "楼宇", "商场"};
    private String searchType = "";//items[0];
    private String searchKey = "";

    // Search
    private List<Tip> autoTips;
    private String searchCityName = "北京";
    public String locationCityName = "北京";
    private boolean isfirstinput = true;

    @Override
    protected String setTitle() {
        return "收货地址";
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_amap_choose;
    }

    @Override
    public void initLayout(Context context) {
        searchText = getViewDataBinding().amcSearchView;
        mapView = getViewDataBinding().amcMapView;
        mapView.onCreate(getIntent().getExtras());
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        searchResultAdapter = new AmapListAdapter(this, resultData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().amcLocationList.setLayoutManager(linearLayoutManager);
        getViewDataBinding().amcLocationList.setAdapter(searchResultAdapter);

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
        progressBar.setId(View.generateViewId());
        ((ConstraintLayout) getViewDataBinding().getRoot()).addView(progressBar);
        // 添加布局属性 方式1
        //ConstraintLayout.LayoutParams clayoutParams = new ConstraintLayout.LayoutParams(100, 100);
        //clayoutParams.topToTop = ConstraintSet.PARENT_ID;
        // 添加布局属性 方式2
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) getViewDataBinding().getRoot());
        constraintSet.connect(progressBar.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(progressBar.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(progressBar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(progressBar.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.applyTo((ConstraintLayout) getViewDataBinding().getRoot());

        super.setPermissionsListenner(new PermissionsGrantedListenner() {
            @Override
            public void onSucess() {
                // 重新发起定位
                mlocationClient.startLocation();
            }
        });
        super.requestPermission(PermissionUtil.REQUEST_LOCATION, true);
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getAmapEvent()){
            getViewDataBinding().setAmapEvent(new AmapChooseEventHandler(this));
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isItemClickAction && !isInputKeySearch) {
                    geoAddress();
                    startJumpAnimation();
                }
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                isInputKeySearch = false;
                isItemClickAction = false;
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });

        // 回车搜索 - 不需要搜索了。靠搜索处理的列表点击搜索
        getViewDataBinding().amcSearchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && (
                        keyCode == KeyEvent.KEYCODE_SEARCH ||
                                keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    SoftKeyBoardUtil.hideKeyBord(getViewDataBinding().amcSearchView, AmapChooseActivity.this);
                }
                return false;
            }
        });

        // 搜索Ajax
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (newText.length() > 0) {
                    InputtipsQuery inputquery = new InputtipsQuery(newText, searchCityName);
                    Inputtips inputTips = new Inputtips(AmapChooseActivity.this, inputquery);
                    inputquery.setCityLimit(true);
                    inputTips.setInputtipsListener(inputtipsListener);
                    inputTips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MY", "setOnItemClickListener");
                if (autoTips != null && autoTips.size() > position) {
                    SoftKeyBoardUtil.hideKeyBord(getViewDataBinding().amcSearchView, AmapChooseActivity.this);
                    Tip tip = autoTips.get(position);
                    searchPoi(tip);
                }
            }
        });

        // 地区列表点击事件
        searchResultAdapter.setOnItemClickListener(new BaseMutilayoutAdapter.OnItemClickListener<ExternPoiItem>() {
            @Override
            public void onClick(View v, int position, ExternPoiItem externPoiItem, int itemViewType, Object externParams) {
                PoiItem poiItem = externPoiItem.getPoiItem();
                Intent intent = new Intent();
                intent.putExtra("provinceCode", poiItem.getProvinceCode());
                intent.putExtra("cityCode", poiItem.getCityCode());
                intent.putExtra("areaCode", poiItem.getAdCode());
                setResult(RESULT_OK, intent);
                finish();
                /* TODO 这里选中后不做处理了。直接拿到位置跳回上个页面
                if (position != searchResultAdapter.getSelectedPosition()) {
                    PoiItem poiItem = (PoiItem) resultData.get(position).getPoiItem();
                    LatLng curLatlng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());

                    isItemClickAction = true;

                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));

                    searchResultAdapter.setSelectedPosition(position);
                    searchResultAdapter.notifyDataSetChanged();
                }*/
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 获取选择的城市
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (110 == requestCode) {
                searchCityName = data.getStringExtra("city_name");
                getViewDataBinding().amcCityName.setText(searchCityName);
            }
        }
    }

    // 搜索联想列表
    private Inputtips.InputtipsListener inputtipsListener = new Inputtips.InputtipsListener() {
        @Override
        public void onGetInputtips(List<Tip> list, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                autoTips = list;
                List<String> listString = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    listString.add(list.get(i).getName());
                }
                ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.route_inputs, listString);
                searchText.setAdapter(aAdapter);
                aAdapter.notifyDataSetChanged();
                if (isfirstinput) {
                    isfirstinput = false;
                    searchText.showDropDown();
                }
            } else {
                showToast("erroCode " + rCode);
            }
        }
    };

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        // TODO @hl
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            // mlocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);

                // @hl 设置当前城市
                locationCityName = searchCityName = amapLocation.getCity();
                getViewDataBinding().amcCityName.setText(searchCityName);

                LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));

                isInputKeySearch = false;

                searchText.setText("");

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        // TODO @hl
        //        if (mlocationClient == null) {
        //            mlocationClient = new AMapLocationClient(this);
        //            mLocationOption = new AMapLocationClientOption();
        //            //设置定位监听
        //            mlocationClient.setLocationListener(this);
        //            //设置为高精度定位模式
        //            mLocationOption.setOnceLocation(true);
        //            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //            //设置定位参数
        //            mlocationClient.setLocationOption(mLocationOption);
        //            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        //            // 在定位结束后，在合适的生命周期调用onDestroy()方法
        //            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //            mlocationClient.startLocation();
        //        }
    }

    @Override
    public void deactivate() {
        //TODO  @hl mListener = null;
        if (mlocationClient != null) {
            //TODO  @hl mlocationClient.stopLocation();
            //TODO  @hl mlocationClient.onDestroy();
        }
        //TODO  @hl  mlocationClient = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
                firstItem = new PoiItem("regeo", searchLatlonPoint, address, address);
                doSearchQuery();
            }
        } else {
            showToast("error code is " + rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        updateListview(poiItems);
                    } else {
                        showToast("无搜索结果");
                    }
                }
            } else {
                showToast("无搜索结果");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 更新列表中的item
     *
     * @param poiItems
     */
    private void updateListview(List<PoiItem> poiItems) {
        resultData.clear();
        searchResultAdapter.setSelectedPosition(0);
        resultData.add(new ExternPoiItem(firstItem));
        for (int i = 0; i < poiItems.size(); ++i) {
            resultData.add(new ExternPoiItem(poiItems.get(i)));
        }
        searchResultAdapter.notifyDataSetChanged();
    }

    public void showDialog() {
        if (null != progressBar){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void dismissDialog() {
        if (null != progressBar) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);

    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {

        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= DensityUtil.dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
        showDialog();
        searchText.setText("");
        if (searchLatlonPoint != null) {
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    private void searchPoi(Tip result) {
        isInputKeySearch = true;
        inputSearchKey = result.getName();//getAddress(); // + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
        searchLatlonPoint = result.getPoint();
        firstItem = new PoiItem("tip", searchLatlonPoint, inputSearchKey, result.getAddress());
        firstItem.setCityName(result.getDistrict());
        firstItem.setAdName("");
        resultData.clear();

        searchResultAdapter.setSelectedPosition(0);

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude()), 16f));

        SoftKeyBoardUtil.hideKeyBord(searchText, this);
        doSearchQuery();
    }

    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
//        Log.i("MY", "doSearchQuery");
        currentPage = 0;
        query = new PoiSearch.Query(searchKey, searchType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));//
            poiSearch.searchPOIAsyn();
        }
    }
}
