package com.hl.base_module.util.canvas;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.hl.base_module.util.screen.DensityUtil;

/**
 * 自定义绘制转圈
 * Created by hl on 2018/4/7.
 */

public class CircleRotateDrawable extends Drawable implements Animatable, ValueAnimator.AnimatorUpdateListener {
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mProgressDegree = 0;
    protected int mProgressStep = 15;
    protected ValueAnimator mValueAnimator;
    protected RectF rectF = new RectF();
    private int strokeW = 4;
    protected Paint mPaint = new Paint();

    public CircleRotateDrawable() {
        mValueAnimator = ValueAnimator.ofInt(30, 3600);
        mValueAnimator.setDuration(10000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //mValueAnimator.setRepeatMode(ValueAnimator.RESTART);

        try {
            strokeW = DensityUtil.dip2px(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPaint.setStrokeWidth(strokeW);
        mPaint.setColor(0xff2051fa);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        mProgressDegree += mProgressStep;
        if (mProgressDegree > 360) {
            mProgressDegree = mProgressStep;
        }
        final Drawable drawable = CircleRotateDrawable.this;
        drawable.invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Drawable drawable = CircleRotateDrawable.this;
        final Rect bounds = drawable.getBounds();
        final int width = bounds.width();
        final int height = bounds.height();

        if (mWidth != width || mHeight != height) {
            rectF.left = 0 + strokeW;
            rectF.top = 0 + strokeW;
            rectF.right = width - strokeW;
            rectF.bottom = height - strokeW;
            mWidth = width;
            mHeight = height;
        }

        ///< 绘制起始角度mProgressDegree, 滑过的角度(360就是一个圆圈)
        canvas.save();
        canvas.rotate(mProgressDegree, rectF.centerX(), rectF.centerY());
        canvas.drawArc(rectF, 5, 345, false, mPaint);
        canvas.restore();
    }

    @Override
    public void start() {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.start();
        }
    }

    @Override
    public void stop() {
        if (mValueAnimator.isRunning()) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mValueAnimator.isRunning();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
