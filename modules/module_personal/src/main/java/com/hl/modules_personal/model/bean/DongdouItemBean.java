package com.hl.modules_personal.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class DongdouItemBean extends BaseMulDataModel {

    /**
     * uid : 10000
     * water_type : 0
     * bill_type : 1
     * bill_id : 1
     * principal : 1000.00
     * number : 1.00
     * balance : 999.00
     * message : 任务消耗
     * create_time : 2020-06-20 16:07:45
     */

    private int uid;
    private int water_type;
    private int bill_type;
    private String bill_id;
    private String principal;
    private String number;
    private String balance;
    private String message;
    private String create_time;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getWater_type() {
        return water_type;
    }

    public void setWater_type(int water_type) {
        this.water_type = water_type;
    }

    public int getBill_type() {
        return bill_type;
    }

    public void setBill_type(int bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
