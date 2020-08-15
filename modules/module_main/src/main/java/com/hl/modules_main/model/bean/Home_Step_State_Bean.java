package com.hl.modules_main.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

import java.util.List;

@NotProguard
public class Home_Step_State_Bean extends BaseMulDataModel{

    /**
     * uTaskId : 15
     * taskTypeId : 1
     * spend_step : 3000
     * spend_minute : 0.18
     * productList : [{"pd_name":"10步韩版百搭秋冬保暖围巾2条装花色随机发","pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","pd_num":1},{"pd_name":"女士秋冬韩版名媛修脸显瘦绒帽","pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","pd_num":1},{"pd_name":"冬季女士超级保暖加厚纯棉袜","pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","pd_num":1}]
     */

    private String uTaskId;
    private String taskTypeId;
    private int spend_step;
    private String spend_minute;
    private List<ProductListBean> productList;

    public String getUTaskId() {
        return uTaskId;
    }

    public void setUTaskId(String uTaskId) {
        this.uTaskId = uTaskId;
    }

    public String getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(String taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public int getSpend_step() {
        return spend_step;
    }

    public void setSpend_step(int spend_step) {
        this.spend_step = spend_step;
    }

    public String getSpend_minute() {
        return spend_minute;
    }

    public void setSpend_minute(String spend_minute) {
        this.spend_minute = spend_minute;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    @NotProguard
    public static class ProductListBean {
        /**
         * pd_name : 10步韩版百搭秋冬保暖围巾2条装花色随机发
         * pd_img : http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png
         * pd_num : 1
         */

        private String pd_name;
        private String pd_img;
        private int pd_num;

        public String getPd_name() {
            return pd_name;
        }

        public void setPd_name(String pd_name) {
            this.pd_name = pd_name;
        }

        public String getPd_img() {
            return pd_img;
        }

        public void setPd_img(String pd_img) {
            this.pd_img = pd_img;
        }

        public int getPd_num() {
            return pd_num;
        }

        public void setPd_num(int pd_num) {
            this.pd_num = pd_num;
        }
    }
}
