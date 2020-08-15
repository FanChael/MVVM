package com.hl.mvvm.wxapi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hl.base_module.util.app.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static String TAG = "WXEntryActivity";

    private IWXAPI api;
    //    private MyHandler handler;
    //
    //    private static class MyHandler extends Handler {
    //        private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;
    //
    //        public MyHandler(WXEntryActivity wxEntryActivity) {
    //            wxEntryActivityWeakReference = new WeakReference<WXEntryActivity>(wxEntryActivity);
    //        }
    //
    //        @Override
    //        public void handleMessage(Message msg) {
    //            int tag = msg.what;
    //            switch (tag) {
    //                case NetworkUtil.GET_TOKEN: {
    //                    Bundle data = msg.getData();
    //                    JSONObject json = null;
    //                    try {
    //                        json = new JSONObject(data.getString("result"));
    //                        String openId, accessToken, refreshToken, scope;
    //                        openId = json.getString("openid");
    //                        accessToken = json.getString("access_token");
    //                        refreshToken = json.getString("refresh_token");
    //                        scope = json.getString("scope");
    //                        Intent intent = new Intent(wxEntryActivityWeakReference.get(), SendToWXActivity.class);
    //                        intent.putExtra("openId", openId);
    //                        intent.putExtra("accessToken", accessToken);
    //                        intent.putExtra("refreshToken", refreshToken);
    //                        intent.putExtra("scope", scope);
    //                        wxEntryActivityWeakReference.get().startActivity(intent);
    //                    } catch (JSONException e) {
    //                        Log.e(TAG, e.getMessage());
    //                    }
    //                }
    //            }
    //        }
    //    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

        api = WXAPIFactory.createWXAPI(this, "APP_ID", false);
        // handler = new MyHandler(this);

        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                //goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                //goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d(TAG, "onPayFinish,errCode=" + resp.errCode);
            int errCord = resp.errCode;
            if (errCord == 0) {
                ToastUtil.showTost("支付成功");
            } else if (errCord == -2) {
                ToastUtil.showTost("取消支付");
            } else {
                ToastUtil.showTost("支付失败");
            }
            // 这里接收到了返回的状态码可以进行相应的操作
            finish();
        }
    }

//	private void goToGetMsg() {
//		Intent intent = new Intent(this, GetFromWXActivity.class);
//		intent.putExtras(getIntent());
//		startActivity(intent);
//		finish();
//	}

//	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//		WXMediaMessage wxMsg = showReq.message;
//		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//		StringBuffer msg = new StringBuffer();
//		msg.append("description: ");
//		msg.append(wxMsg.description);
//		msg.append("\n");
//		msg.append("extInfo: ");
//		msg.append(obj.extInfo);
//		msg.append("\n");
//		msg.append("filePath: ");
//		msg.append(obj.filePath);
//
//		Intent intent = new Intent(this, ShowFromWXActivity.class);
//		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//		startActivity(intent);
//		finish();
//	}
}