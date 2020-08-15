package com.hl.lib_refreshlayout.view;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hl.base_module.util.canvas.LClassFooter;
import com.hl.base_module.util.canvas.LClassHeader;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_refreshlayout.test.DraggableViewCallback;
import com.hl.lib_refreshlayout.handler.RefreshHandler;
import com.hl.lib_refreshlayout.handler.RefreshListenner;

/**
 * 刷新控件自定义 - ViewGroup
 *
 * @Author: hl
 * @Date: created at 2020/4/24 17:07
 * @Description: com.hl.lib_refreshlayout.view
 */
public class HLRefreshLayout extends ViewGroup {
    private static String TAG = HLRefreshLayout.class.getName();

    // Drag辅助类
    private ViewDragHelper viewDragHelper;

    // Footer
    private LClassFooter lClassFooter;
    private int footerH = 0, maxFooterH;
    private boolean bLoadMoreData = true; // 是否允许加载更多

    // Header
    private LClassHeader lClassHeader;
    private int headerH = 0, maxHeaderH;
    private boolean bRefreshData = true;    // 是否允许下拉刷新

    // 当前处于什么状态
    private State load_state = State.NONE;
    // 刷新/加载回调
    private RefreshListenner refreshListenner;

    public enum State {
        NONE, NOMORE_DATA,
        START_LOAD, START_REFRESH,
        LOADING, REFRESHING,
        LOAD_FINISH, REFRESH_FINISH
    }

    /**
     * 列表刷新控件
     */
    private RecyclerView recyclerView;
    //private CustomLinearLayoutManager customLinearLayoutManager; //Rv布局管理器，可以控制rv是否可以滚动

    public HLRefreshLayout(Context context) {
        this(context, null);
    }

    public HLRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HLRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HLRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context);
    }

    /**
     * 初始化Footer，Header等
     *
     * @param context
     */
    private void init(Context context) {
        // 添加一个Footer
        maxFooterH = DensityUtil.dip2px(context, 46);
        lClassFooter = new LClassFooter(getContext());
        // 添加一个Header
        maxHeaderH = DensityUtil.dip2px(context, 46);
        lClassHeader = new LClassHeader(getContext());

        // Nothing use..
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DraggableViewCallback(this));

        // 添加刷新头Header
        if (null != lClassHeader.getParent() &&
                lClassHeader.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) lClassHeader.getParent();
            parent.removeView(lClassHeader);
        }
        addView(lClassHeader, LinearLayout.LayoutParams.MATCH_PARENT, maxHeaderH);

        // 添加底部加载Footer
        if (null != lClassFooter.getParent() &&
                lClassFooter.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) lClassFooter.getParent();
            parent.removeView(lClassFooter);
        }
        addView(lClassFooter, LinearLayout.LayoutParams.MATCH_PARENT, maxFooterH);
    }

    private float touchY1, diffY;
    private float touchX1, diffX;
    private static final float FLIP_DISTANCE = 50;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("HLRefreshLayout dTouch", String.valueOf(ev.getAction()));

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchY1 = ev.getY();
                touchX1 = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                diffY = touchY1 - ev.getY();
                diffX = ev.getX() - touchX1;
                touchY1 = ev.getY();
                touchX1 = ev.getX();

                // 向下滚动
                if (diffY <= 0) {
                    // 此时如果是加载中的状态，则不能触发下拉收回的操作
                    if (load_state != State.LOADING ||
                            load_state == State.NOMORE_DATA) {
                        footerH -= Math.abs(diffY) / 3;
                        if (footerH <= 0) {
                            footerH = 0;
                        }
                        lClassFooter.onStart();
                        requestLayout();
                    }

                    // Rv不能向下滑动了
                    if (!recyclerView.canScrollVertically(-1) &&
                            (load_state != State.LOADING)) {
                        headerH += Math.abs(diffY) / 3;
                        if (headerH > maxHeaderH) {
                            headerH = maxHeaderH;
                        }
                        load_state = State.START_REFRESH;
                        lClassHeader.onStart();
                        requestLayout();
                    }
                } else { // 向上滚动
                    Log.e("test", "-1-1-1");
                    if (load_state != State.REFRESHING) {
                        Log.e("test", "0000");
                        headerH -= diffY / 3;
                        if (headerH <= 0) {
                            headerH = 0;
                        }
                        requestLayout();
                    }

                    // 显示加载更多的情况，此时不进行加载更多回调；即不改变状态
                    if (!recyclerView.canScrollVertically(1) &&
                            load_state == State.NOMORE_DATA) {
                        Log.e("test", "1111");
                        footerH += diffY / 3;
                        if (footerH > maxFooterH) {
                            footerH = maxFooterH;
                        }
                        lClassFooter.onStart();
                        requestLayout();
                    } else if (!recyclerView.canScrollVertically(1) && // Rv不能向上滑动了，此时继续上拉
                            (load_state != State.REFRESHING)) {
                        Log.e("test", "2222");
                        footerH += diffY / 3;
                        if (footerH > maxFooterH) {
                            footerH = maxFooterH;
                        }
                        load_state = State.START_LOAD;
                        lClassFooter.onStart();
                        requestLayout();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.e("test", "4444 " + load_state);
                if (load_state == State.START_LOAD) {
                    // 如果没有上拉到一定距离，则不触发加载更多
                    if (footerH < (maxFooterH * 3 / 4)) {
                        // 不触发加载更多，此时不是加载中的状态
                        load_state = State.NONE;
                        lClassFooter.onFinish();
                        // 此时Recycleview还有有点没有置底，我们滚动一下
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        footerH = 0;
                    } else {
                        // 如果达到3/4，则全部展开
                        footerH = maxFooterH;
                        // 此时Recycleview还有有点没有置底，我们滚动一下
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        // 达到一定距离，开始进行加载中显示；
                        // 此时保持在底部，除非调用finishLoadMore方法
                        // 禁止Recycleview滚动
                        //customLinearLayoutManager.setScrollEnabled(false);
                        // 设置处于加载中的状态
                        load_state = State.LOADING;
                        lClassFooter.setLoadData();
                        if (null != refreshListenner) {
                            refreshListenner.OnLoadMore();
                        }
                    }
                } else if (load_state == State.START_REFRESH) {
                    // 如果没有下拉不到一定距离，则不触发刷新
                    if (headerH < (maxHeaderH * 3 / 4)) {
                        // 不触发加载更多，此时不是加载中的状态
                        load_state = State.NONE;
                        lClassHeader.onFinish();
                        headerH = 0;
                    } else {
                        // 如果达到3/4，则全部展开
                        headerH = maxHeaderH;
                        // 此时Recycleview还有有点没有置顶，我们滚动一下
                        //recyclerView.scrollToPosition(0);
                        // 达到一定距离，开始进行加载中显示；
                        // 此时保持在底部，除非调用finishLoadMore方法
                        // 禁止Recycleview滚动
                        //customLinearLayoutManager.setScrollEnabled(false);
                        // 设置处于加载中的状态
                        load_state = State.REFRESHING;
                        lClassHeader.setFreshData();
                        if (null != refreshListenner) {
                            refreshListenner.OnRefresh();
                        }
                    }
                } else if (load_state == State.NOMORE_DATA){
                    // 如果没有上拉到一定距离，则不触发加载更多
                    if (footerH < (maxFooterH * 3 / 4)) {
                        lClassFooter.onFinish();
                        footerH = 0;
                    } else {
                        // 如果达到3/4，则全部展开
                        footerH = maxFooterH;
                        // 此时Recycleview还有有点没有置底，我们滚动一下
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                }
                requestLayout();
                Log.e("HLRefreshLayout t UP", "UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    //    @Override
    //    public boolean onInterceptTouchEvent(MotionEvent ev) {
    //        Log.e("onInterceptTouchEvent", String.valueOf(ev.getAction()));
    //        // Rv不能向上滑动了
    //        if (!recyclerView.canScrollVertically(1)) {
    //            // 计算下拉的距离，大于某个值，此时
    //            Log.e("HLRefreshLayout Action", String.valueOf(ev.getAction()));
    //            Log.e("HLRefreshLayout onInte", String.valueOf(ev.getY()));
    //            switch (ev.getAction()) {
    //                case MotionEvent.ACTION_DOWN:
    //                    touchY1 = ev.getY();
    //                    break;
    //                case MotionEvent.ACTION_MOVE:
    //                    diffY = touchY1 - ev.getY();
    //                    touchY1 = ev.getY();
    //                    Log.e("HLRefreshLayout diffY", String.valueOf(diffY));
    //                    if (diffY <= 0) {
    //                        //ev.setAction(MotionEvent.ACTION_UP);
    //                        //onInterceptTouchEvent(ev);
    //                        return false;
    //                    } else {
    //                        //                        footerH += diffY;
    //                        //                        if (footerH > maxFooterH) {
    //                        //                            footerH = maxFooterH;
    //                        //                        }
    //                        return false;
    //                    }
    //                case MotionEvent.ACTION_UP:
    //                    Log.e("HLRefreshLayout UP", "UP");
    //                    return false;
    //            }
    //            //Log.e("HLRefreshLayout onInte", String.valueOf(ev.getRawY()));
    //        }
    //        return super.onInterceptTouchEvent(ev);
    //    }

    //    @Override
    //    public boolean onTouchEvent(MotionEvent ev) {
    //        Log.e("HLRefreshLayout t on", "onTouchEvent");
    //        switch (ev.getAction()) {
    //            case MotionEvent.ACTION_DOWN:
    //                touchY1 = ev.getY();
    //                break;
    //            case MotionEvent.ACTION_MOVE:
    //                diffY = touchY1 - ev.getY();
    //                touchY1 = ev.getY();
    //                Log.e("HLRefreshLayout t diffY", String.valueOf(diffY));
    //                if (diffY <= 0) {
    //                    return false;
    //                } else {
    //                    footerH += diffY/5;
    //                    if (footerH > maxFooterH) {
    //                        footerH = maxFooterH;
    //                    }
    //                    requestLayout();
    //                    return true;
    //                }
    //            case MotionEvent.ACTION_UP:
    //                Log.e("HLRefreshLayout t UP", "UP");
    //                return false;
    //        }
    //        //viewDragHelper.processTouchEvent(event);
    //        return false;
    //    }

    //    @Override
    //    public void computeScroll() {
    //        if (viewDragHelper.continueSettling(true)) {
    //            invalidate();
    //        } else {
    //            super.computeScroll();
    //        }
    //    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure");
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Footer、Header需要测量一把，否则无法获取到位置
        //        lClassFooter.measure(widthMeasureSpec, heightMeasureSpec);
        //        lClassHeader.measure(widthMeasureSpec, heightMeasureSpec);

        /***自行测量***/
        // ViewGroup的最大宽度和高度
        int maxWidth = 0;
        int maxHeight = 0;

        /**测量子控件*/
        int childViewCount = getChildCount();
        for (int i = 0; i < childViewCount; ++i) {
            View view = getChildAt(i);
            if (view.getVisibility() != GONE) {
                // 获取子控件的属性 - 暂时未考虑margin，padding的情况
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                //                // 测量子控件的宽高
                //                final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
                //                final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, layoutParams.height);
                //                // 然后真正测量下子控件 - 到这一步我们就对子控件进行了宽高的设置了咯
                //                view.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                // 简化上面的调用
                measureChild(view, widthMeasureSpec, heightMeasureSpec);

                // 然后再次获取真正的子控件的宽高属性
                // layoutParams = view.getLayoutParams();
                // 获取最大宽度和高度的累加值（在ViewGroup内容包裹的情况下，需要用来设置整个组合控件的宽高)
                maxWidth = Math.max(maxWidth, view.getMeasuredWidth());
                if (view instanceof RecyclerView) {
                    maxHeight += view.getMeasuredHeight();
                }
            }
        }

        /**测量ViewGroup**/
        // 然后再与容器本身的最小宽高对比，取其最大值 - 有一种情况就是带背景图片的容器，要考虑图片尺寸
        maxWidth = Math.max(maxWidth, getMinimumWidth());
        maxHeight = Math.max(maxHeight, getMinimumHeight());

        // 然后根据ViewGroup容易模式进行对应的宽高设置
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        // WrapContent模式
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(maxWidth, maxHeight);
        }
        // 精确尺寸的模式
        else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(wSize, hSize);
        }
        // 宽度精确尺寸，高度内容包裹
        else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSize, maxHeight);
        }
        // 高度精确尺寸，宽度内容包裹
        else if (hSpecMode == MeasureSpec.EXACTLY && wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(maxWidth, hSize);
        }
        // 宽度尺寸不确定，高度确定
        else if (wSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(maxWidth, hSize);
        }
        // 高度尺寸不确定，宽度确定
        else if (hSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(wSize, maxHeight);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout");

        // ///< 获取范围初始左上角 - 这个决定子控件绘制的位置，我们绘制理论可以从0,0开始，margin容器本身已经考虑过了...所以别和margin混淆了
        int leftPos = getPaddingLeft();
        int topPos = getPaddingTop();

        ///< 获取范围初始右下角 - 如果考虑控件的位置，比如靠右，靠下等可能就要利用右下角范围来进行范围计算了...
        ///< 后面我们逐步完善控件的时候用会用到这里...
        //int rightPos = right - left - getPaddingRight();
        //int rightBottom = bottom - top - getPaddingBottom();

        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View childView = getChildAt(i);

            ///< 控件占位的情况下进行计算
            if (childView.getVisibility() != GONE) {
                int childW = childView.getMeasuredWidth();
                int childH = childView.getMeasuredHeight();

                // 计算控件的上下左右范围
                int cleft = leftPos;
                int cright = cleft + childW;
                int ctop = topPos;
                int cbottom = ctop + childH;

                if (!(childView instanceof LClassFooter) &&
                        !(childView instanceof LClassHeader)) {
                    ///< 下一个控件的左上角需要向y轴移动上一个控件的高度 - 不然都重叠了!
                    topPos += childH;
                }

                // 记录Recycleview，之后做加载更多提示等操作
                if (childView instanceof RecyclerView) {
                    recyclerView = (RecyclerView) childView;
                    //customLinearLayoutManager = (CustomLinearLayoutManager) recyclerView.getLayoutManager();
                    childView.layout(cleft, ctop + headerH, cright, cbottom - footerH);
                    topPos -= footerH;
                } else {
                    childView.layout(cleft, ctop, cright, cbottom);
                }
            }
        }

        // Footer子控件摆放
        if (null != recyclerView && null != lClassFooter && bLoadMoreData) {
            //            if (null != lClassFooter.getParent() &&
            //                    lClassFooter.getParent() instanceof ViewGroup) {
            //                ViewGroup parent = (ViewGroup) lClassFooter.getParent();
            //                parent.removeView(lClassFooter);
            //            }
            //            addView(lClassFooter, LinearLayout.LayoutParams.MATCH_PARENT, maxFooterH);
            lClassFooter.layout(leftPos, topPos,
                    leftPos + recyclerView.getWidth(),
                    topPos + footerH);

            // 旗下的子控件也需要摆放
            int footerChildCount = lClassFooter.getChildCount();
            for (int i = 0; i < footerChildCount; ++i) {
                View fooView = lClassFooter.getChildAt(i);

                if (fooView.getVisibility() != GONE) {
                    int fl = fooView.getLeft();
                    int fr = fooView.getRight();
                    int ft = fooView.getTop();
                    int fb = fooView.getBottom();
                    // 一些调试参数
                    //                    LinearLayout.LayoutParams flayoutParams = (LinearLayout.LayoutParams) fooView.getLayoutParams();
                    //                    int fChildW = flayoutParams.width;
                    //                    int fChildH = flayoutParams.height;
                    //                    Rect viewRect = new Rect();
                    //                    int[] poss = new int[2];
                    //                    fooView.getLocationInSurface(poss);
                    fooView.layout(fl, ft, fr, fb);
                }
            }

            // 如下之前调试用的
            //                        TextView textView = new TextView(getContext());
            //                        textView.setText("内容包裹");
            //                        textView.setTextColor(Color.parseColor("#03DAC5"));
            //                        textView.setGravity(Gravity.CENTER);
            //                        textView.setBackgroundColor(Color.parseColor("#FF0000"));
            //                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //                        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            //                        addView(textView);
            //                        textView.layout(leftPos, topPos,
            //                                leftPos + recyclerView.getWidth(),
            //                                topPos + 100);
        }

        // Header子控件摆放
        if (null != recyclerView && null != lClassHeader && bRefreshData) {
            //            if (null != lClassHeader.getParent() &&
            //                    lClassHeader.getParent() instanceof ViewGroup) {
            //                ViewGroup parent = (ViewGroup) lClassHeader.getParent();
            //                parent.removeView(lClassHeader);
            //            }
            //            addView(lClassHeader, LinearLayout.LayoutParams.MATCH_PARENT, maxHeaderH);
            lClassHeader.layout(leftPos, 0,
                    leftPos + recyclerView.getWidth(),
                    0 + headerH);

            // 旗下的子控件也需要摆放
            int headerChildCount = lClassHeader.getChildCount();
            for (int i = 0; i < headerChildCount; ++i) {
                View heaView = lClassHeader.getChildAt(i);

                if (heaView.getVisibility() != GONE) {
                    int fl = heaView.getLeft();
                    int fr = heaView.getRight();
                    int ft = heaView.getTop();
                    int fb = heaView.getBottom();
                    // 一些调试参数
                    //                    LinearLayout.LayoutParams flayoutParams = (LinearLayout.LayoutParams) fooView.getLayoutParams();
                    //                    int fChildW = flayoutParams.width;
                    //                    int fChildH = flayoutParams.height;
                    //                    Rect viewRect = new Rect();
                    //                    int[] poss = new int[2];
                    //                    fooView.getLocationInSurface(poss);
                    heaView.layout(fl, ft, fr, fb);
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.e(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
        recyclerView = null;
        //customLinearLayoutManager = null;
    }

    /**
     * 刷新事件回调
     *
     * @param onRefreshListenner
     */
    public void setOnRefreshListenner(RefreshListenner onRefreshListenner) {
        this.refreshListenner = onRefreshListenner;
    }

    /**
     * 自动刷新
     */
    public void autoRefresh(){
        load_state =State.START_REFRESH;
        headerH = maxHeaderH;
        load_state = State.REFRESHING;
        lClassHeader.setFreshData();
        if (null != refreshListenner) {
            refreshListenner.OnRefresh();
        }
        requestLayout();
    }

    /**
     * 延迟消息处理
     */
    private RefreshHandler handler = new RefreshHandler(this);

    /**
     * 加载更多结束
     */
    public void finishLoadMore(long seconds) {
        if (load_state == State.REFRESHING) {
            return;
        }

        Message message = handler.obtainMessage();
        message.what = State.LOAD_FINISH.ordinal();
        handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + seconds * 1000);
    }

    /**
     * UI线程，非延迟
     */
    public void finishLoadMore() throws Exception {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new Exception("非UI线程不能进行控件操作!");
        }

        // 标志刷新结束
        load_state = State.NONE;
        // RV此时可滑动
        //customLinearLayoutManager.setScrollEnabled(true);
        // 结束加载更多动画
        lClassFooter.onFinish();
        // Footer消失
        footerH = 0;
        requestLayout();
        if (null != refreshListenner) {
            refreshListenner.OnLoadMoreFinish();
        }
    }

    /**
     * 刷新结束
     */
    public void finishRefresh(long seconds) {
        if (load_state == State.LOADING) {
            return;
        }

        // 刷新后置顶一下
        recyclerView.scrollToPosition(0);

        Message message = handler.obtainMessage();
        message.what = State.REFRESH_FINISH.ordinal();
        handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + seconds * 1000);
    }

    /**
     * UI线程，非延迟
     */
    public void finishRefresh() throws Exception {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new Exception("非UI线程不能进行控件操作!");
        }

        // 标志刷新结束
        load_state = State.NONE;
        // RV此时可滑动
        //customLinearLayoutManager.setScrollEnabled(true);
        // 结束刷新动画
        lClassHeader.onFinish();
        // Footer消失
        headerH = 0;
        requestLayout();
        if (null != refreshListenner) {
            refreshListenner.OnRefreshFinish();
        }

        // 刷新后置顶一下
        recyclerView.scrollToPosition(0);
    }

    /**
     * 无更多数据
     */
    public void setNoMoreData() {
        load_state = State.NOMORE_DATA;
        footerH = 0;
        lClassFooter.setNoMoreData();
        requestLayout();
    }
}
