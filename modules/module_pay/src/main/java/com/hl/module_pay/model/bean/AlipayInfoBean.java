package com.hl.module_pay.model.bean;

import com.hl.anotation.NotProguard;

@NotProguard
public class AlipayInfoBean {

    /**
     * order_info : alipay_sdk=alipay-sdk-php-20180705&app_id=2016061401514745&biz_content=%7B%22productCode%22%3A%22QUICK_MSECURITY_PAY%22%2C%22body%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%98v4GxpQZvN3O%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%AF%E4%BB%983756%22%2C%22total_amount%22%3A%220.01%22%2C%22out_trade_no%22%3A%22201907291331541564378314%22%2C%22timeout_express%22%3A%221m%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.lieyunwang.com%2Fpay%2Ffenghui%2Fali%2Fnotify-url&sign_type=RSA2&timestamp=2019-07-29+13%3A31%3A54&version=1.0&sign=E3GsOaNW8XMomCIktQnCiIsyQ1YWl1%2FRvYc1Ml16lVHbZzeIZzFreBSCkrxeFrLUa3MoUuH0t14Ty%2FDXJEzCLjFLnDtN5L81HdrUmXBltEK%2FgyufOiY3EMbhcBhkJwgb72MjJqI8WngQb8SsBP8rgtHpvM4k%2BiOtzejB4CS6YDajcBlOjTcBapw71FPrmMktW25AUfz%2BLguzbG7JF7jes1GgIJk1yHSAslVwJjL2YZrl1%2BWl6GwLeO14bTSwGVovRe8jgINO78KdWl0k8OBlBlgZBoecd6fB66zbR0zX4%2FlUkfbQupSoyBzk5DiachGkexbqK%2FNbTWCh%2B1zHyAc3sQ%3D%3D
     */

    private String order_info;

    public AlipayInfoBean(){}
    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }
}
