package com.hl.module_location.view;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.rv.RecycleViewDivider;
import com.hl.base_module.viewmodel.SelfViewModelFactory;
import com.hl.module_location.R;
import com.hl.module_location.databinding.ActivityLocationListBinding;
import com.hl.module_location.model.bean.AddressListBean;
import com.hl.module_location.view.adapter.AddressListAdapter;
import com.hl.module_location.view.event.LocationListEventHandler;
import com.hl.module_location.viewmodel.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

@Route(path = ArouterPath.ADDRESS_LOCATION)
public class LocationListActivity extends BaseWithServiceActivity<ActivityLocationListBinding> {
    private AddressListAdapter addressListAdapter;
    private List<AddressListBean> addressListBeans = new ArrayList<>();
    private LocationViewModel locationViewModel;

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
        return R.layout.activity_location_list;
    }

    @Override
    public void initLayout(Context context) {
        locationViewModel = new ViewModelProvider(this,
                new SelfViewModelFactory(baseControlPresenter))
                .get(LocationViewModel.class);
        locationViewModel.getAddrLiveData().observe(this, new Observer<List<AddressListBean>>() {
            @Override
            public void onChanged(List<AddressListBean> addressLists) {

            }
        });

        // 测试数据
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setAddressName("王先生");
        addressListBean.setAddressPhone("158*******3");
        addressListBean.setIsDefault(1);
        addressListBean.setLabelName("家屁屁");
        addressListBean.setSiteInfo("四川省凉山州西昌市");
        addressListBeans.add(addressListBean);
        addressListBean = new AddressListBean();
        addressListBean.setAddressName("王先生2");
        addressListBean.setAddressPhone("158*******3");
        addressListBean.setIsDefault(0);
        addressListBean.setLabelName("公司");
        addressListBean.setSiteInfo("四川省凉山州西昌市");
        addressListBeans.add(addressListBean);
        addressListBeans.add(addressListBean);
        addressListBeans.add(addressListBean);

        addressListAdapter = new AddressListAdapter(this, addressListBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().allLocationListRv.addItemDecoration(new RecycleViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                1, R.color.little_gray));
        getViewDataBinding().allLocationListRv.setLayoutManager(linearLayoutManager);
        getViewDataBinding().allLocationListRv.setAdapter(addressListAdapter);
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getAddrEvent()) {
            getViewDataBinding().setAddrEvent(new LocationListEventHandler());
        }
        addressListAdapter.setOnItemClickListener(new BaseMutilayoutAdapter.OnItemClickListener<AddressListBean>() {
            @Override
            public void onClick(View v, int position, AddressListBean addressListBean, int itemViewType, Object externParams) {
                getViewDataBinding().getAddrEvent().editAddress();
            }
        });
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
