package com.hl.lib_miniui.view.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hl.base_module.util.glide.GlideUtil;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/**
 * 圆形外边框圆角图片
 *
 * @Author: hl
 * @Date: created at 2020/5/22 11:15
 * @Description: com.hl.lib_miniui.view.imageview.CircleImageView
 */
public class OutCircleLineImageView extends ViewGroup {
    private Paint mPaint;
    private int s_gift_outcircle_color;

    private ImageView imageView;

    public OutCircleLineImageView(Context context) {
        super(context);
        init(context, null);
    }

    public OutCircleLineImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OutCircleLineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        s_gift_outcircle_color = ta.getColor(R.styleable.gift_progress_styleable_s_gift_outcircle_color, 0xFF7ABBED);
        ta.recycle();

        // 创建一个图片控件
        imageView = new ImageView(getContext());
        addView(imageView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCircle(canvas, s_gift_outcircle_color, DensityUtil.dip2px(getContext(), 1.5f));
    }

    /**
     * 绘制圈圈
     * @param canvas
     * @param color
     * @param strok_w
     */
    private void drawCircle(Canvas canvas, int color, int strok_w) {
        if (null == mPaint) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
        }
        mPaint.setColor(color);
        mPaint.setStrokeWidth(strok_w);
        canvas.drawCircle(
                getWidth()/2, getHeight()/2,
                (getWidth() - strok_w * 2)/2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSizeW = MeasureSpec.getSize(widthMeasureSpec);
        int specSizeH = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(specSizeW, specSizeH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        imageView.layout(getWidth() * 3 / 10, getHeight() * 1 / 5,
                getWidth() * 7 / 10, getHeight() * 4 / 5);
    }

    /**
     * 设置图片
     *
     * @param picUrl
     */
    public void setImage(String picUrl) {
        GlideUtil.initImageWithFileCache(getContext(), "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=896126139,1849014785&fm=26&gp=0.jpg", imageView);
        requestLayout();
        invalidate();
    }
}
