package com.hl.lib_miniui.view.numbers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.base_module.util.glide.GlideUtil;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 重叠头像列表
 * 1.横向排列，最多N个
*@Author: hl
*@Date: created at 2020/5/23 21:31
*@Description: com.hl.lib_miniui.view.numbers
*/
public class RoundImageList extends ViewGroup {
    private WeakReference<Context> contextWeakReference;
    private int imgCount = 3;
    private int specSizeSize;

    private Paint paint;

    public RoundImageList(Context context) {
        super(context);
        init(context, null);
    }

    public RoundImageList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public RoundImageList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化相关配置
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TextView需要测量，而ImageView指定了宽高，所以这里可以不需要测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        specSizeSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(specSizeSize * imgCount, specSizeSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View view = getChildAt(i);
            if (view instanceof ImageView) {
                if (i > 0) {
                    view.layout(specSizeSize * i - i * (specSizeSize / 3), 0, specSizeSize * i + specSizeSize - i * (specSizeSize / 3), specSizeSize);
                } else {
                    view.layout(specSizeSize * i, 0, specSizeSize * i + specSizeSize, specSizeSize);
                }
            } else if (view instanceof TextView) {
                int tLeft = (specSizeSize * (i - 1) + specSizeSize - (i - 1) * (specSizeSize / 3)) + 3;
                int w = view.getMeasuredWidth();
                int h = view.getMeasuredHeight();
                view.layout(tLeft, (specSizeSize - h)/2, tLeft + w, (specSizeSize - h)/2 + h);
            }
        }
    }

    /**
     * 添加图片信息
     * @param imgList
     */
    public void setImage(List<String> imgList) {
        if (imgList.size() < 1) {
            return;
        }
        // 移除已有的，避免重复添加； 此方法移除不刷新
        removeAllViewsInLayout();

        imgCount = imgList.size();
        // 设置圆角图片，并添加到ViewGroup
        for (String img: imgList) {
            ImageView imageView = new ImageView(contextWeakReference.get());
            GlideUtil.initRoundImageCache(contextWeakReference.get(), img, imageView);
            LayoutParams layoutParams = new LayoutParams(specSizeSize, specSizeSize);
            imageView.setLayoutParams(layoutParams);
            addView(imageView);
        }
        if (imgList.size() >= 3) { // 目前没有指定宽高，所以OnMeasure需要测量一把
            TextView textView = new TextView(contextWeakReference.get());
            textView.setTextColor(Color.parseColor("#ff999999"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText("...");
            addView(textView);
        }
        requestLayout();
        invalidate();
    }
}
