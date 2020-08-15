package com.hl.modules_home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.util.system.AppUtil;
import com.hl.base_module.util.app.NavigationBarUtil;

public class SplashActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///< 是否是第一次启动
        if (AppUtil.isFirstOpen(this)) {
            return;
        }
        // 隐藏状态栏
        NavigationBarUtil.hideNavigationBar(this);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        countDownTimer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(SplashActivity.this, "" + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                // 直接传递Bundle
                Bundle params = new Bundle();
                params.putString("scheme", "我是开机画面");
                ARouter.getInstance()
                        .build("/module_home/homeactivity")
                        .with(params)
                        .navigation();
                finish();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
