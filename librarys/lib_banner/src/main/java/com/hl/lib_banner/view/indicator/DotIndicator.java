package com.hl.lib_banner.view.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_banner.view.BannerVP2;

/**
 * 圆点指示器
 *
 * @Author: hl
 * @Date: created at 2020/4/30 10:04
 * @Description: com.hl.lib_banner.view.indicator
 */
public class DotIndicator extends ViewPager2.OnPageChangeCallback {
    private ViewGroup viewGroup;
    // Dot画笔
    private Paint dotPaint;                      // Dot画笔
    private Paint snakePaint;                    // 贪吃蛇画笔
    private int dot_color = 0xffe4e4e4;          // Dot圆点默认颜色
    private int dot_selected_color = 0xff2051fa;  // Dot选中颜色
    private int dot_radius;
    private int dot_space;
    private int current_pos = 0;
    private float positionOffset = 0;

    private BannerVP2.OnTouchListener mOnTouchListener;

    public DotIndicator(BannerVP2 bannerVP2, BannerVP2.OnTouchListener onTouchListener) {
        DotIndicator(bannerVP2);
        this.mOnTouchListener = onTouchListener;
    }

    public DotIndicator DotIndicator(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        this.dot_radius = DensityUtil.dip2px(viewGroup.getContext(), 6);
        this.dot_space = DensityUtil.dip2px(viewGroup.getContext(), 6);
        if (null == this.dotPaint) {
            this.dotPaint = new Paint();
            this.dotPaint.setStyle(Paint.Style.FILL);
            this.dotPaint.setAntiAlias(true);
        }
        if (null == this.snakePaint) {
            this.snakePaint = new Paint();
            this.snakePaint.setStrokeWidth(this.dot_radius * 2);
            this.snakePaint.setStrokeCap(Paint.Cap.ROUND);
            this.snakePaint.setAntiAlias(true);
            this.snakePaint.setColor(dot_selected_color);
        }
        return this;
    }

    /**
     * 设置相关属性
     *
     * @param dot_color
     * @param dot_selected_color
     * @param dot_radius
     * @param dot_space
     * @return
     */
    public DotIndicator setDotRadius(int dot_color, int dot_selected_color, int dot_radius, int dot_space) {
        this.dot_color = dot_color;
        this.dot_selected_color = dot_selected_color;
        this.dot_radius = dot_radius;
        this.dot_space = dot_space;
        return this;
    }

    /**
     * Dot绘制
     *
     * @param canvas
     * @param count         - banner个数
     * @param width         - banner宽
     * @param height        - banner高
     * @param indicatorType
     */
    public void draw(Canvas canvas, int count, int width, int height, BannerVP2.IndicatorType indicatorType) {
        int leftX = dot_radius * 2, topY = height - dot_radius * 2;
        if (indicatorType == BannerVP2.IndicatorType.DOT_RIGHT) {
            leftX = width - (count * dot_radius * 2 + dot_space * (count - 1));
        } else if (indicatorType == BannerVP2.IndicatorType.DOT_CENTER) {
            leftX = (width - (count * dot_radius * 2 + dot_space * (count - 1))) / 2;
        }
        canvas.save();
        for (int i = 0; i < count; ++i) {
            int last_pos = current_pos % count;
            // 画贪吃蛇
            if (last_pos < (count - 1)) {
                if (positionOffset >= 0.0000001) {
                    canvas.drawLine(
                            leftX + last_pos * (dot_space + dot_radius * 2),
                            topY, leftX + last_pos * (dot_space + dot_radius * 2) + (dot_space + dot_radius * 2) * positionOffset,
                            topY, snakePaint);
                }
            }
            // 画原点
            if (i == last_pos) {
                dotPaint.setColor(dot_selected_color);
            } else {
                dotPaint.setColor(dot_color);
            }
            canvas.drawCircle(
                    leftX + (dot_space + dot_radius * 2) * i,
                    topY,
                    dot_radius, dotPaint);
        }
        canvas.restore();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.current_pos = position;
        this.positionOffset = positionOffset;
        viewGroup.invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 返回Down和UP事件回调
        if (null != mOnTouchListener) {
            if (ViewPager2.SCROLL_STATE_DRAGGING == state) {
                mOnTouchListener.onTouch(MotionEvent.ACTION_DOWN);
            } else if (ViewPager2.SCROLL_STATE_IDLE == state) {
                mOnTouchListener.onTouch(MotionEvent.ACTION_UP);
            }
        }
    }
}
