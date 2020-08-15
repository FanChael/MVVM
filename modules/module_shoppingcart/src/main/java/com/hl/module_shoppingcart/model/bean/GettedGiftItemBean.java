package com.hl.module_shoppingcart.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class GettedGiftItemBean extends BaseMulDataModel {
    private int id;
    private String name;
    private String price;
    private int number;
    private String flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
