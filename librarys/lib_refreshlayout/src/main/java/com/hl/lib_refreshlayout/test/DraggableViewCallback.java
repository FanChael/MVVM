package com.hl.lib_refreshlayout.test;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * 测试类，后续实践之
 */
public class DraggableViewCallback extends ViewDragHelper.Callback {
    public DraggableViewCallback(ViewGroup hlRefreshLayout) {
    }

    @Override
    public boolean tryCaptureView(@NonNull View child, int pointerId) {
        return true;
    }

    @Override
    public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
        super.onViewPositionChanged(changedView, left, top, dx, dy);
        Log.e("test", "changedView=" + changedView);
        Log.e("test", "left=" + left);
        Log.e("test", "top=" + top);
        Log.e("test", "dx=" + dx);
        Log.e("test", "dy=" + dy);
    }

    @Override
    public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
        super.onViewReleased(releasedChild, xvel, yvel);
    }

    @Override
    public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
        // 屏蔽掉水平方向
        return 0;
    }

    @Override
    public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
        // 垂直方向打开
        return 1;
    }
}
