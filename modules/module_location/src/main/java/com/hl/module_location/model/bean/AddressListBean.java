package com.hl.module_location.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class AddressListBean extends BaseMulDataModel {

    /**
     * addressId : 3
     * addressName : 梁歌
     * provinceCode : 110000
     * cityCode : 110100
     * areaCode : 130126
     * addressInfo : 北方明珠大厦
     * addressPhone : 18201093522
     * isDefault : 0
     * siteInfo : 北京市-北京市-灵寿县-北方明珠大厦
     */

    private int addressId;
    private String labelName;
    private String addressName;
    private int provinceCode;
    private int cityCode;
    private int areaCode;
    private String addressInfo;
    private String addressPhone;
    private int isDefault;
    private String siteInfo;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(String siteInfo) {
        this.siteInfo = siteInfo;
    }
}
