package com.hl.lib_miniui.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.hl.base_module.util.glide.GlideUtil;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;
import com.hl.lib_miniui.view.imageview.CircleImageViewFromGoogle;

/**
 * 完成环状进度条
 *
 * @Author: hl
 * @Date: created at 2020/5/22 11:15
 * @Description: com.hl.lib_miniui.view.progress
 */
public class GiftProgress extends ViewGroup {
    private Paint mPaint;
    private float complatedPercent;
    private int s_gift_circle_color;
    private int s_gift_circle_s_color;
    private int s_gift_outcircle_color;

    private CircleImageViewFromGoogle imageView;

    public GiftProgress(Context context) {
        super(context);
        init(context, null);
    }

    public GiftProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GiftProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.gift_progress_styleable);

        // 进度相关颜色 进度颜色、走过的颜色、背景颜色
        s_gift_circle_color = ta.getColor(R.styleable.gift_progress_styleable_s_gift_circle_color, 0xFFE5E5E5);
        s_gift_circle_s_color = ta.getColor(R.styleable.gift_progress_styleable_s_gift_circle_s_color, 0xFF2C97EE);
        s_gift_outcircle_color = ta.getColor(R.styleable.gift_progress_styleable_s_gift_outcircle_color, 0xFF7ABBED);
        ta.recycle();

        // 创建一个图片控件
        imageView = new CircleImageViewFromGoogle(getContext());
        addView(imageView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCircle(canvas, s_gift_outcircle_color, DensityUtil.dip2px(getContext(), 1.5f), 0);
        drawCircle(canvas, s_gift_circle_color, DensityUtil.dip2px(getContext(), 4), 1);
        drawCircle(canvas, s_gift_circle_s_color, DensityUtil.dip2px(getContext(), 4), 2);
    }

    /**
     * 绘制圈圈
     *
     * @param canvas
     * @param color
     * @param strok_w
     */
    private void drawCircle(Canvas canvas, int color, int strok_w, int type) {
        if (null == mPaint) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
        }
        mPaint.setColor(color);
        mPaint.setStrokeWidth(strok_w);
        if (0 == type) { //外圈
            canvas.drawCircle(
                    getWidth() / 2, getHeight() / 2,
                    (getWidth() - strok_w * 2) / 2, mPaint);
        } else if (1 == type) { // 内圈
            canvas.drawArc(getWidth() * 1 / 9, getHeight() * 1 / 9,
                    getWidth() * 8 / 9, getHeight() * 8 / 9,
                    0, 360,
                    false, mPaint);
        } else if (2 == type) { // 绘制进度
            canvas.drawArc(getWidth() * 1 / 9, getHeight() * 1 / 9,
                    getWidth() * 8 / 9, getHeight() * 8 / 9,
                    0, 360 * complatedPercent,
                    false, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSizeW = MeasureSpec.getSize(widthMeasureSpec);
        int specSizeH = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(specSizeW, specSizeH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        imageView.layout(getWidth() / 9, getHeight() * 1 / 9, getWidth() * 8 / 9, getHeight() * 8 / 9);
    }

    /**
     * 设置图片
     *
     * @param picUrl
     */
    public void setImage(String picUrl) {
        GlideUtil.initImageWithFileCache(getContext(), picUrl, imageView);
        requestLayout();
        invalidate();
    }

    /**
     * 设置完成进度
     *
     * @param percent
     */
    public void setProgress(float percent) {
        this.complatedPercent = percent;
        invalidate();
    }
}
