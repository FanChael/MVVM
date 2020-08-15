package com.hl.module_pay.model.bean;

import com.google.gson.annotations.SerializedName;
import com.hl.anotation.NotProguard;

@NotProguard
public class WeiXinPayInfoBean {
    /**
     * order_info : {"appid":"wx96cea88d645fd34e","noncestr":"acji3qn8y7voiglwnd3ttohlpb82fyiu","package":"Sign=WXPay","partnerid":"1364896402","prepayid":"wx1310384809122825a1d985c11402229500","timestamp":1565663855,"sign":"5C3018B23042B734707ECC2794FF483B"}
     */

    private OrderInfoBean order_info;

    private int is_payment; // 额外标识状态，微信回调通知设置

    public WeiXinPayInfoBean(){}
    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public int getIs_payment() {
        return is_payment;
    }

    public void setIs_payment(int is_payment) {
        this.is_payment = is_payment;
    }

    @NotProguard
    public static class OrderInfoBean {
        /**
         * appid : wx96cea88d645fd34e
         * noncestr : acji3qn8y7voiglwnd3ttohlpb82fyiu
         * package : Sign=WXPay
         * partnerid : 1364896402
         * prepayid : wx1310384809122825a1d985c11402229500
         * timestamp : 1565663855
         * sign : 5C3018B23042B734707ECC2794FF483B
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX = "Sign=WXPay";
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;

        public OrderInfoBean(){}
        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
