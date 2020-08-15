package com.hl.lib_banner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_banner.R;
import com.hl.lib_banner.view.indicator.DotIndicator;
import com.hl.lib_banner.view.viewpaper2.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播Vp2
 * 1.带圆角stoke
 * 2.vp2测量和layout了一定的margin
 * 3.vp2 banner退出对rv做了资源释放
 *
 * @Author: hl
 * @Date: created at 2020/4/29 10:17
 * @Description: com.hl.lib_banner.view
 */
public class BannerVP2 extends ViewGroup {
    // 画笔 - 给Vp背景润个色、圆角troke
    private Paint mPaint;                   // 画笔，颜色，宽度这些可以自定义属性，然后获取动态调整
    private RectF rectf;                    // 边框线绘制的范围
    private int strok_w;                    // 边框线宽度，圆角=strok_w*2，具体可调节
    private int stroke_color = 0xffe4e4e4;  // 边框颜色
    private boolean bling = false;          // 边框是否闪烁
    private LooperColor looperColor;

    // 抽出去独立开来
    //    // Dot画笔
    //    private Paint dotPaint;
    //    private int dot_color = 0xffe4e4e4;  // Dot圆点默认颜色
    //    private int dot_selected_color = 0xffe4e4e4;  // Dot选中颜色
    //    private int dot_radius;
    //    private int dot_space;

    // 指示器类型
    private IndicatorType indicatorType = IndicatorType.NONE;
    // 指示器相关属性
    private int oritention;
    private int dot_color;
    private int dot_selectetColor;
    private int dot_radius;
    private int dot_space;

    public enum IndicatorType {
        NONE, DOT_LEFT, DOT_CENTER, DOT_RIGHT
    }

    // ViewPaper2、适配器、数据列表
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private List<String> imgList = new ArrayList<>();

    // 指示器
    private DotIndicator dotIndicator;

    // 定时轮播
    private Handler handler = new Handler();
    private int loopDelay = 1500;
    private LooperRunnable looperRunnable;
    private int current_index = 0;
	
	//手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    public BannerVP2(Context context) {
        super(context);
        this.init(context, null);
    }

    public BannerVP2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public BannerVP2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    // 支持Margin的固定写法，下面照抄就行了，至于为什么，可以去看源码，但是我觉得直接记住就ok了
    // --代码里面如果已经动态设置了MarginLayoutParams，这里可以不用
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BannerVP2);
        // 获取滑动方向
        oritention = ta.getInt(R.styleable.BannerVP2_orientation, ViewPager2.ORIENTATION_HORIZONTAL);

        /***背景属性****/
        // 获取圆角线宽度，圆角半径是宽度的2倍
        strok_w = (int) ta.getDimension(R.styleable.BannerVP2_stokeSize,
                DensityUtil.dip2pxF(getContext(), 0.0f));
        // 获取边框颜色
        stroke_color = ta.getColor(R.styleable.BannerVP2_cornerColor, 0xffe4e4e4);

        /***dot圆点属性****/
        // 获取圆点默认颜色
        dot_color = ta.getColor(R.styleable.BannerVP2_dotColor, 0xffe4e4e4);
        // 获取圆点选中后的颜色 - 这个颜色跟snaker同色
        dot_selectetColor = ta.getColor(R.styleable.BannerVP2_dotSelectColor, 0xff2051fa);
        // 获取圆点半径
        dot_radius = (int) ta.getDimension(R.styleable.BannerVP2_dotRadius,
                DensityUtil.dip2pxF(getContext(), 6.0f));
        // 获取圆点距离
        dot_space = (int) ta.getDimension(R.styleable.BannerVP2_dotSpace,
                DensityUtil.dip2pxF(getContext(), 6.0f));
        // 获取显示位置 - 垂直方向滑动圆点只提供左右居中
        indicatorType = IndicatorType.values()[ta.getInt(R.styleable.BannerVP2_indicatorType, 0)];

        // 创建Vp2
        viewPager2 = new ViewPager2(context);
        viewPager2.setOrientation(oritention);
        // 配置Margin参数 - 也可以重写generateLayoutParams、generateLayoutParams、generateDefaultLayoutParams
        //        MarginLayoutParams layoutParams = new MarginLayoutParams(
        //                ViewGroup.LayoutParams.MATCH_PARENT,
        //                ViewGroup.LayoutParams.MATCH_PARENT);
        //        layoutParams.setMargins(strok_w, strok_w, strok_w, strok_w); // 4个参数按顺序分别是左上右下
        //        viewPager2.setLayoutParams(layoutParams);
        // 背景设置玩耍
        //viewPager2.setBackgroundColor(Color.parseColor("#03DAC5"));
        // 添加到ViewGroup
        addView(viewPager2);
        // addView之后会由我们重写的默认的generateDefaultLayoutParams获得一个MarginLayoutParams
        // 然后设置maring，让圆角不被图片透过来
        MarginLayoutParams layoutParams = (MarginLayoutParams) viewPager2.getLayoutParams();
        layoutParams.setMargins(strok_w, strok_w, strok_w, strok_w); // 4个参数按顺序分别是左上右下

        // 初始化Vp2适配器
        imgList.clear();
        viewPagerAdapter = new ViewPagerAdapter(imgList);

        // 设置指示器并监听
        if (indicatorType != IndicatorType.NONE) {
            setIndicatorType(indicatorType);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                // 简化上面的调用
                measureChildWithMargins(view,
                        widthMeasureSpec, 0,
                        heightMeasureSpec, 0);

                // 获取最大宽度和高度的累加值（在ViewGroup内容包裹的情况下，需要用来设置整个组合控件的宽高)
                maxWidth = Math.max(maxWidth, view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight, view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            }
        }

        /**测量ViewGroup**/
        // ViewGroup内边距
        maxWidth += getPaddingLeft() + getPaddingRight();
        maxHeight += getPaddingTop() + getPaddingBottom();

        // 然后再与容器本身的最小宽高对比，取其最大值 - 有一种情况就是带背景图片的容器，要考虑图片尺寸
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());

        // 设置ViewGroup尺寸，采用resolveSize，简化@2的判断的方式
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));

        // @2然后根据ViewGroup容易模式进行对应的宽高设置
        //                int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        //                int wSize = MeasureSpec.getSize(widthMeasureSpec);
        //                int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //                int hSize = MeasureSpec.getSize(heightMeasureSpec);
        //                // WrapContent模式
        //                if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
        //                    setMeasuredDimension(maxWidth, maxHeight);
        //                }
        //                // 精确尺寸的模式
        //                else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.EXACTLY) {
        //                    setMeasuredDimension(wSize, hSize);
        //                }
        //                // 宽度精确尺寸，高度内容包裹
        //                else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.AT_MOST) {
        //                    setMeasuredDimension(wSize, maxHeight);
        //                }
        //                // 高度精确尺寸，宽度内容包裹
        //                else if (hSpecMode == MeasureSpec.EXACTLY && wSpecMode == MeasureSpec.AT_MOST) {
        //                    setMeasuredDimension(maxWidth, hSize);
        //                }
        //                // 宽度尺寸不确定，高度确定
        //                else if (wSpecMode == MeasureSpec.UNSPECIFIED) {
        //                    setMeasuredDimension(maxWidth, hSize);
        //                }
        //                // 高度尺寸不确定，宽度确定
        //                else if (hSpecMode == MeasureSpec.UNSPECIFIED) {
        //                    setMeasuredDimension(wSize, maxHeight);
        //                }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 布局子控件，支持margin
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childW = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childH = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 布局适当做一些偏移，解决出现间隙的问题
            child.layout(lp.leftMargin, lp.topMargin - 1, childW - lp.leftMargin + 1, childH - lp.bottomMargin + 1);
        }
    }

    /**
     * 绘制，添加圆角stroke
     *
     * @param canvas
     */
    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawViewGroupBG(canvas);    //放在super前是后景，相反是前景，前景会覆盖子布局
        if (indicatorType == BannerVP2.IndicatorType.DOT_LEFT ||
                indicatorType == BannerVP2.IndicatorType.DOT_CENTER ||
                indicatorType == BannerVP2.IndicatorType.DOT_RIGHT) {
            drawDotView(canvas);        // 绘制Dot圆点
        }
    }

    /**
     * 请求上层控件不拦截Touch事件，交由子类处理，解决滑动冲突
	 * @后来又增加了上下滑动的处理，这样能避免上下滑动无法滚动列表的问题；并且只保持ViewPaper左右滑动
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
                //Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                getParent().requestDisallowInterceptTouchEvent(false);
            } else if (y2 - y1 > 50) {
                //Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                getParent().requestDisallowInterceptTouchEvent(false);
            } else if (x1 - x2 > 50) {
                //Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                getParent().requestDisallowInterceptTouchEvent(true);
            } else if (x2 - x1 > 50) {
                //Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 绘制一个圆角边框中间透明的背景
     *
     * @param canvas
     */
    private void drawViewGroupBG(Canvas canvas) {
        canvas.save();
        if (strok_w > 0) {
            if (null == mPaint) {
                mPaint = new Paint();
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(strok_w);
                mPaint.setAntiAlias(true);
            }
            // 无聊的变色
            if (stroke_color == 0xffe4e4e4 && bling) {
                stroke_color = 0xFF0000FF;
            } else {
                stroke_color = 0xffe4e4e4;
            }
            mPaint.setColor(stroke_color);
            if (null == rectf) {
                rectf = new RectF(strok_w / 2, strok_w / 2,
                        getWidth() - strok_w / 2, getHeight() - strok_w / 2);
            }
            canvas.drawRoundRect(rectf,
                    strok_w * 2, strok_w * 2, mPaint);
            canvas.restore();
        }
    }

    /**
     * 指示器圆点绘制
     *
     * @param canvas
     */
    private void drawDotView(Canvas canvas) {
        if (null == dotIndicator ||
                null == imgList ||
                imgList.size() <= 0) {
            return;
        }
        // 启动绘制刷新
        dotIndicator.draw(canvas, imgList.size(), getWidth(), getHeight(), indicatorType);
    }

    /**
     * 资源释放
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 确保Adapter#onDetachedFromRecyclerView被调用
        if (null != viewPager2) {
            viewPager2.setAdapter(null);
        }
    }

    /**
     * 控制不可见时不轮询，减少内存占用
     *
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            startLoop();
        } else {
            stop();
        }
    }

    /*************************对外方法*******************/
    /**
     * 设置数据 - 会清除之前的数据
     *
     * @param imgList
     * @return
     */
    public BannerVP2 setData(List<String> imgList) {
        this.imgList.clear();
        this.imgList.addAll(imgList);
        return this;
    }

    /**
     * 设置指示器显示类型
     *
     * @param indicatorType
     * @return
     */
    public BannerVP2 setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
        switch (indicatorType) {
            case DOT_LEFT:
            case DOT_CENTER:
            case DOT_RIGHT:
                if (null == dotIndicator) {
                    viewPager2.unregisterOnPageChangeCallback(dotIndicator);
                }
                dotIndicator = new DotIndicator(this, new OnTouchListener() {
                    @Override
                    public void onTouch(int state) {
                        if (MotionEvent.ACTION_DOWN == state) {
                            stop();
                        } else {
                            startLoop();
                        }
                    }
                });
                dotIndicator.setDotRadius(dot_color, dot_selectetColor, dot_radius, dot_space);
                // 设置滚动监听
                viewPager2.registerOnPageChangeCallback(dotIndicator);
                break;
        }
        return this;
    }

    /**
     * 设置适配器 - 第一版暂时未加入自动轮播
     * loopDelaySec - 可以为0，表示不轮询
     *
     * @return
     */
    public BannerVP2 start(float loopDelaySec) {
        this.loopDelay = (int) (loopDelaySec * 1000f);
        if (null == viewPager2.getAdapter()) {
            viewPager2.setAdapter(viewPagerAdapter);
        } else {
            current_index = 0;
            viewPager2.setCurrentItem(current_index);
            viewPagerAdapter.notifyDataSetChanged();
        }
        if (loopDelaySec > 0) {
            startLoop();
        }
        return this;
    }

    /**
     * 是否允许边框闪烁
     *
     * @return
     */
    public BannerVP2 enableBlingBling(boolean bling) {
        this.bling = bling;
        return this;
    }

    /**
     * 停止轮播
     */
    public void stop() {
        if (null != handler && null != looperRunnable) {
            looperRunnable.setbIsStop(true);
            handler.removeCallbacks(looperRunnable);
        }
        if (null != handler && null != looperColor) {
            looperColor.setbIsStop(true);
            handler.removeCallbacks(looperColor);
        }
    }

    /**
     * 开始轮播
     */
    private void startLoop() {
        if (null != handler) {
            if (null == looperRunnable) {
                looperRunnable = new LooperRunnable();
            }
            if (null == looperColor) {
                looperColor = new LooperColor();
            }
            // 这个判断方法Q才支持
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //                if (handler.hasCallbacks(looperRunnable)) {
            //
            //                }
            //            }
            // 先移除，然后重新添加
            stop();
            handler.postDelayed(looperRunnable.setbIsStop(false), loopDelay);
            // bling的情况下才执行闪烁
            if (bling) {
                handler.postDelayed(looperColor.setbIsStop(false), loopDelay);
            }
        }
    }

    /**
     * 闪烁
     */
    class LooperColor implements Runnable {
        private boolean bIsStop = false;

        public LooperColor setbIsStop(boolean bIsStop) {
            this.bIsStop = bIsStop;
            return this;
        }

        @Override
        public void run() {
            if (null != handler && !bIsStop) {
                invalidate();
                handler.postDelayed(this, 100);
            }
        }
    }

    /**
     * 定时轮播
     */
    class LooperRunnable implements Runnable {
        private boolean bIsStop = false;

        public LooperRunnable setbIsStop(boolean bIsStop) {
            this.bIsStop = bIsStop;
            return this;
        }

        @Override
        public void run() {
            if (null != handler && !bIsStop && loopDelay > 0) {
                current_index = viewPager2.getCurrentItem();
                ++current_index;
                boolean bsmoothScroll = true;
                if (current_index >= (viewPagerAdapter.getItemCount() - 1)) {
                    current_index = 0;
                    bsmoothScroll = false;
                } else {
                    bsmoothScroll = true;
                }
                viewPager2.setCurrentItem(current_index, bsmoothScroll);
                // 轮播
                handler.postDelayed(this, loopDelay);
            }
        }
    }

    /**
     * Vp2的setTouch是无效的， SO自定义Vp2, Touch回调，ViewPager2.OnPageChangeCallback里面处理回调
     */
    public interface OnTouchListener {
        void onTouch(int state);
    }
}
