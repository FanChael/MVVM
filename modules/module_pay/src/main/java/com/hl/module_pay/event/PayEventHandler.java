package com.hl.module_pay.event;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.hl.lib_pop.view.pay.PayPop;
import com.hl.module_pay.model.bean.AlipayInfoBean;
import com.hl.module_pay.model.bean.PayResult;
import com.hl.module_pay.model.bean.WeiXinPayInfoBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

public class PayEventHandler {
    // 微信支付文档 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5
    // 支付宝文档 https://opendocs.alipay.com/open/204/105296
    private AppCompatActivity context;
    private PayGo payGo;
    /**
     * 微信支付
     */
    private IWXAPI api;
    /**
     * 支付宝
     */
    private static final int SDK_PAY_FLAG = 110;
    /**
     * 支付结果
     */
    @SuppressWarnings("unchecked")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    //                    Toast.makeText(TicketDetailActivity.this, resultInfo,
                    //                            Toast.LENGTH_LONG).show();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, payResult.getMemo(),
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        }
    };

    public PayEventHandler(AppCompatActivity context) {
        this.context = context;
        api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp("appid");
    }

    /**
     * 支付弹窗，需要依赖pop库
     */
    public void payGetGift(){
        PayPop.getInstance()
                .setPrice("100大洋")
                .setPayBack(new PayPop.PayBack() {
                    @Override
                    public void howToPay(PayPop.PAY_TYPE pay_type) {
                       if (pay_type == PayPop.PAY_TYPE.WEBCHAT) {
                            // 微信
                            boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
                            if (isPaySupported) {
                                // TODO 获取微信订单信息
                                //            baseControlPresenter.addParam("id", order_id)
                                //                    .postData(WeiXinPayInfoBean.class, new ResultCallBack<WeiXinPayInfoBean>() {
                                //                    }, null, false);
                                if (null != payGo) {
                                    payGo.startWxPay();
                                }
                            } else {
                                //context.showToast("请确认已安装微信!");
                                payGo.message("请确认已安装微信!");
                            }
                        } else if (pay_type == PayPop.PAY_TYPE.ALIPAY) {
                            //TODO  获取支付宝订单信息
                            //            baseControlPresenter.addParam("id", order_id)
                            //                        .postData(AlipayInfoBean.class, new ResultCallBack<AlipayInfoBean>() {
                            //                        }, null, false);
                            if (null != payGo) {
                                payGo.startAliPay();
                            }
                        }
                    }
                }).showSafely(context.getSupportFragmentManager(), "支付弹窗");
    }

    /**
     * 微信支付
     *
     * @param wxpayInfo
     */
    public void payWx(WeiXinPayInfoBean wxpayInfo) {
        ///< 微信 - 支付后确定订单
        PayReq request = new PayReq();
        request.appId = wxpayInfo.getOrder_info().getAppid();
        request.nonceStr = wxpayInfo.getOrder_info().getNoncestr();
        request.partnerId = wxpayInfo.getOrder_info().getPartnerid();
        request.prepayId = wxpayInfo.getOrder_info().getPrepayid();
        request.packageValue = wxpayInfo.getOrder_info().getPackageX();
        request.timeStamp = wxpayInfo.getOrder_info().getTimestamp() + "";
        request.sign = wxpayInfo.getOrder_info().getSign();
        api.sendReq(request);
    }

    /**
     * 支付宝支付
     *
     * @param alipayInfo
     */
    public void payAli(AlipayInfoBean alipayInfo) {
        ///< 支付宝
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(alipayInfo.getOrder_info(), true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 设置支付监听 - 有弹窗时使用
     * @param payGo
     */
    public PayEventHandler setPayGo(PayGo payGo){
        this.payGo = payGo;
        return this;
    }

    public interface PayGo{
        void startWxPay();
        void startAliPay();
        void message(String msg);
    }
}
