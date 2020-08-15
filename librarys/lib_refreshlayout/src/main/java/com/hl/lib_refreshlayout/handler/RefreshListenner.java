package com.hl.lib_refreshlayout.handler;

/**
 * 刷新相关状态回调
 */
public interface RefreshListenner {
    void OnRefresh();

    void OnRefreshFinish();

    void OnLoadMore();

    void OnLoadMoreFinish();
}
