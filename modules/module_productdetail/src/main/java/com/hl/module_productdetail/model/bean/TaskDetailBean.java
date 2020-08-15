package com.hl.module_productdetail.model.bean;

import com.hl.anotation.NotProguard;

import java.util.List;

@NotProguard
public class TaskDetailBean {

    /**
     * task_name : 任务名称
     * taskId : 1
     * taskTypeId : 1
     * task_type : 1
     * part_num : 0
     * task_pd_num : 3
     * total_step : 3000
     * total_minute : 4320
     * task_repeat : 1
     * deliver_time : 24
     * pay_type : 0
     * task_price : 0.00
     * productList : [{"target_step":1000,"target_minute":1440,"sort":1,"pd_id":1,"pd_name":"10步韩版百搭秋冬保暖围巾2条装花色随机发","vice_name":"秋冬保暖围巾","pd_desc":"韩版百搭秋冬保暖围巾2条装花色随机发","pd_num":1,"pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","video_url":"http://ts7.sztv.com.cn/szmg/vod/2020/04/17/da36d035ab394d1295deb39eca12d678/h264_1200k_mp4.mp4","commerce_price":"100.00","pd_img_json":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/a5a4e07420e236cb0021c914015f0612.png\"]","pd_detail":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/dc62ed5bbe9dcdc7de59ab98cd369038.jpg\"]","deliver_time":24},{"target_step":1000,"target_minute":1440,"sort":2,"pd_id":25,"pd_name":"女士秋冬韩版名媛修脸显瘦绒帽","vice_name":"明星款绒帽","pd_desc":"女士秋冬韩版名媛修脸显瘦绒帽","pd_num":1,"pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","video_url":"","commerce_price":"18.00","pd_img_json":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/b31dc9b7b4cdc08fb829fd85a44f7e03.png\",\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/2c425a182370435c0cd6f3103d44c8db.png\",\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/c8f21047cd3a3b08e5f5081e10ada431.png\"]","pd_detail":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/7a0d6c715485f3ee9a02e778887c3a40.png\"]","deliver_time":24},{"target_step":1000,"target_minute":1440,"sort":3,"pd_id":34,"pd_name":"冬季女士超级保暖加厚纯棉袜","vice_name":"加厚保暖袜","pd_desc":"冬季女士超级保暖加厚纯棉袜","pd_num":1,"pd_img":"http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png","video_url":"","commerce_price":"12.00","pd_img_json":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/d934acfe15e763c6c37edfdfeae9fecc.png\",\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/95241ec81af7b941d74083527a13042f.png\",\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/d95d93a079922d4d8429321f9eaf0e1d.png\"]","pd_detail":"[\"http:\\/\\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\\/c6aad37630a1d005035c2555d5c24f03.jpg\"]","deliver_time":24}]
     */

    private String task_name;
    private int taskId;
    private int taskTypeId;
    private int task_type;
    private int part_num;
    private int task_pd_num;
    private int total_step;
    private int total_minute;
    private int task_repeat;
    private int deliver_time;
    private int pay_type;
    private String task_price;
    private List<ProductListBean> productList;

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

    public int getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(int taskTypeId) {
        this.taskTypeId = taskTypeId;
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

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getTask_price() {
        return task_price;
    }

    public void setTask_price(String task_price) {
        this.task_price = task_price;
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
         * target_step : 1000
         * target_minute : 1440
         * sort : 1
         * pd_id : 1
         * pd_name : 10步韩版百搭秋冬保暖围巾2条装花色随机发
         * vice_name : 秋冬保暖围巾
         * pd_desc : 韩版百搭秋冬保暖围巾2条装花色随机发
         * pd_num : 1
         * pd_img : http://goods-xiaoliyun.oss-cn-beijing.aliyuncs.com/a5a4e07420e236cb0021c914015f0612.png
         * video_url : http://ts7.sztv.com.cn/szmg/vod/2020/04/17/da36d035ab394d1295deb39eca12d678/h264_1200k_mp4.mp4
         * commerce_price : 100.00
         * pd_img_json : ["http:\/\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\/a5a4e07420e236cb0021c914015f0612.png"]
         * pd_detail : ["http:\/\/goods-xiaoliyun.oss-cn-beijing.aliyuncs.com\/dc62ed5bbe9dcdc7de59ab98cd369038.jpg"]
         * deliver_time : 24
         */

        private int target_step;
        private int target_minute;
        private int sort;
        private int pd_id;
        private String pd_name;
        private String vice_name;
        private String pd_desc;
        private int pd_num;
        private String pd_img;
        private String video_url;
        private String commerce_price;
        private String pd_img_json;
        private String pd_detail;
        private int deliver_time;

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

        public int getPd_id() {
            return pd_id;
        }

        public void setPd_id(int pd_id) {
            this.pd_id = pd_id;
        }

        public String getPd_name() {
            return pd_name;
        }

        public void setPd_name(String pd_name) {
            this.pd_name = pd_name;
        }

        public String getVice_name() {
            return vice_name;
        }

        public void setVice_name(String vice_name) {
            this.vice_name = vice_name;
        }

        public String getPd_desc() {
            return pd_desc;
        }

        public void setPd_desc(String pd_desc) {
            this.pd_desc = pd_desc;
        }

        public int getPd_num() {
            return pd_num;
        }

        public void setPd_num(int pd_num) {
            this.pd_num = pd_num;
        }

        public String getPd_img() {
            return pd_img;
        }

        public void setPd_img(String pd_img) {
            this.pd_img = pd_img;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getCommerce_price() {
            return commerce_price;
        }

        public void setCommerce_price(String commerce_price) {
            this.commerce_price = commerce_price;
        }

        public String getPd_img_json() {
            return pd_img_json;
        }

        public void setPd_img_json(String pd_img_json) {
            this.pd_img_json = pd_img_json;
        }

        public String getPd_detail() {
            return pd_detail;
        }

        public void setPd_detail(String pd_detail) {
            this.pd_detail = pd_detail;
        }

        public int getDeliver_time() {
            return deliver_time;
        }

        public void setDeliver_time(int deliver_time) {
            this.deliver_time = deliver_time;
        }
    }
}
