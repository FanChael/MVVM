package com.hl.modules_main.model.bean;

import com.hl.anotation.NotProguard;
import com.hl.base_module.adapter.BaseMulDataModel;

@NotProguard
public class Home_Task_Item_Bean extends BaseMulDataModel{

    /**
     * task_name : 任务名称
     * taskId : 1
     * task_type : 1
     * part_num : 0
     * task_price : 1.00
     * task_pd_num : 3
     * total_step : 3000
     * total_minute : 4320
     * task_repeat : 1
     * deliver_time : 24
     * productList : [{"target_step":1000,"target_minute":1440,"sort":1,"cp_product_id":1,"pd_name":"测试商品","pd_img":"","commerce_price":"0.00"},{"target_step":1000,"target_minute":1440,"sort":2,"cp_product_id":25,"pd_name":"测试商品","pd_img":"","commerce_price":"0.00"},{"target_step":1000,"target_minute":1440,"sort":3,"cp_product_id":34,"pd_name":"测试商品","pd_img":"","commerce_price":"0.00"}]
     */

    private String task_name;
    private int taskId;
    private int task_type;
    private int part_num;
    private String task_price;
    private int task_pd_num;
    private int total_step;
    private int total_minute;
    private int task_repeat;
    private int deliver_time;
    private ProductBean product;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }

    public int getPart_num() {
        return part_num;
    }

    public void setPart_num(int part_num) {
        this.part_num = part_num;
    }

    public String getTask_price() {
        return task_price;
    }

    public void setTask_price(String task_price) {
        this.task_price = task_price;
    }

    public int getTask_pd_num() {
        return task_pd_num;
    }

    public void setTask_pd_num(int task_pd_num) {
        this.task_pd_num = task_pd_num;
    }

    public int getTotal_step() {
        return total_step;
    }

    public void setTotal_step(int total_step) {
        this.total_step = total_step;
    }

    public int getTotal_minute() {
        return total_minute;
    }

    public void setTotal_minute(int total_minute) {
        this.total_minute = total_minute;
    }

    public int getTask_repeat() {
        return task_repeat;
    }

    public void setTask_repeat(int task_repeat) {
        this.task_repeat = task_repeat;
    }

    public int getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(int deliver_time) {
        this.deliver_time = deliver_time;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    @NotProguard
    public static class ProductBean {
        /**
         * target_step : 1000
         * target_minute : 1440
         * sort : 1
         * cp_product_id : 1
         * pd_name : 测试商品
         * pd_img :
         * commerce_price : 0.00
         */

        private int target_step;
        private int target_minute;
        private int sort;
        private int cp_product_id;
        private String pd_name;
        private String pd_img;
        private String commerce_price;

        public int getTarget_step() {
            return target_step;
        }

        public void setTarget_step(int target_step) {
            this.target_step = target_step;
        }

        public int getTarget_minute() {
            return target_minute;
        }

        public void setTarget_minute(int target_minute) {
            this.target_minute = target_minute;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getCp_product_id() {
            return cp_product_id;
        }

        public void setCp_product_id(int cp_product_id) {
            this.cp_product_id = cp_product_id;
        }

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

        public String getCommerce_price() {
            return commerce_price;
        }

        public void setCommerce_price(String commerce_price) {
            this.commerce_price = commerce_price;
        }
    }
}
