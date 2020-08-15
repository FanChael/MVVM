package com.hl.module_location.view;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.module_location.R;
import com.hl.module_location.databinding.ActivityAddEditorLocationBinding;
import com.hl.module_location.view.event.LocationAddEditorEventHandler;

@Route(path = ArouterPath.ADDRESS_EDITOR_ADD_LOCATION)
public class AddEditorLocationActivity extends BaseWithServiceActivity<ActivityAddEditorLocationBinding> {
    @Autowired
    public String tag;

    @Override
    protected String setTitle() {
        return tag + "收货地址";
    }

    @Override
    protected boolean bIsLightThenDark() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_add_editor_location;
    }

    @Override
    public void initLayout(Context context) {
    }

    @Override
    public void requestData(Context context) {

    }

    @Override
    public void eventHandler(Context context) {
        if (null == getViewDataBinding().getLocationEditor()) {
            getViewDataBinding().setLocationEditor(new LocationAddEditorEventHandler(this));
        }
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (110 == requestCode) {
                String provinceCode = data.getStringExtra("provinceCode");
                String cityCode = data.getStringExtra("cityCode");
                String areaCode = data.getStringExtra("areaCode");
                String last = areaCode;
                String last2 = areaCode;
            }
        }
    }
}
