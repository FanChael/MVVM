package com.hl.base_module.util.rv;

import androidx.recyclerview.widget.RecyclerView;

public class RvUtil {

    /**
     * 判断是否滚动到底部
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
