package com.hl.lib_refreshlayout.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.hl.lib_refreshlayout.view.HLRefreshLayout;

/**
 * 刷新结束相关Handler延迟处理
 */
public class RefreshHandler extends Handler {
    private HLRefreshLayout hlRefreshLayout;

    public RefreshHandler(HLRefreshLayout viewGroup) {
        this.hlRefreshLayout = viewGroup;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (msg.what == HLRefreshLayout.State.LOAD_FINISH.ordinal()) {
            try {
                hlRefreshLayout.finishLoadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (msg.what == HLRefreshLayout.State.REFRESH_FINISH.ordinal()) {
            try {
                hlRefreshLayout.finishRefresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
