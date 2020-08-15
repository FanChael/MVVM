package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/**
 * 图标+带角标
 *
 * @Author: hl
 * @Date: created at 2020/5/22 11:15
 * @Description: com.hl.lib_miniui.view.texts
 */
public class Icon_Badge extends View {
    private Paint paint, text_paint;
    private int fill_color, text_color, text_size;
    private Rect text_rect;
    private String textMsg;
    private int badge_size = 6;
    // 图标
    private Bitmap icon;
    private int icon_w, icon_h;

    public Icon_Badge(Context context) {
        super(context);
        init(context, null);
    }

    public Icon_Badge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Icon_Badge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public Icon_Badge(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        //开启硬件离屏缓存：解决黑色问题，效率比关闭硬件加速高。暂时没有发现其他影响
        setLayerType(LAYER_TYPE_HARDWARE, null);

        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Icon_Badge);
        fill_color = ta.getColor(R.styleable.Icon_Badge_iconbadge_textbg, 0xfffc5d35);
        text_color = ta.getColor(R.styleable.Icon_Badge_iconbadge_textcolor, 0xffffffff);
        text_size = ta.getDimensionPixelOffset(R.styleable.Icon_Badge_iconbadge_textsize, 20);
        // 获取图片
        Drawable iconDrawable = ta.getDrawable(R.styleable.Icon_Badge_iconbadge_drawable);
        if (null != iconDrawable) {
            BitmapDrawable iconBitmap = (BitmapDrawable) iconDrawable;
            icon = iconBitmap.getBitmap();
            icon_w = icon.getWidth();
            icon_h = icon.getHeight();
        }
        ta.recycle();

        // 文字画笔
        if (null == text_paint) {
            text_paint = new Paint();
            text_paint.setTextSize(text_size);
            text_paint.setTypeface(Typeface.DEFAULT_BOLD);
            text_paint.setColor(text_color);
            text_paint.setAntiAlias(true);
            badge_size = DensityUtil.dip2px(context, 6);
        }

        // 背景画笔
        if (null == paint) {
            paint = new Paint();
            paint.setColor(fill_color);
            paint.setAntiAlias(true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(icon_w + badge_size * 2 / 3, icon_h + badge_size * 2 / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制图片
        if (null != icon) {
            canvas.drawBitmap(icon,  0, 0 + badge_size * 2 / 3, null);
            // 绘制角标背景
            canvas.drawCircle(icon_w - badge_size * 1 / 3, badge_size * 2/3, badge_size/2, paint);
            // 绘制文字 - 以角标背景中心坐标做定位
            if (!TextUtils.isEmpty(textMsg)) {
                canvas.drawText(textMsg,
                        icon_w - badge_size * 1 / 3 - text_rect.width()/2 - 2, //  - 2 字体有一定偏差
                        badge_size * 2 / 3 + text_rect.height()/2, text_paint);
            }
        }
    }

    /**
     * 设置图片信息
     * @param icon_id
     */
    public void setImage(Drawable icon_id){
        BitmapDrawable iconBitmap = (BitmapDrawable) icon_id;
        icon = iconBitmap.getBitmap();
        icon_w = icon.getWidth();
        icon_h = icon.getHeight();
    }

    /**
     * 设置角标数字
     */
    public void setNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return;
        }
        textMsg = number;
        text_rect = new Rect();
        text_paint.getTextBounds(number, 0, number.length(), text_rect);
        badge_size = (text_rect.width() > text_rect.height() ? text_rect.width() : text_rect.height()) + DensityUtil.dip2px(getContext(), 6);
        requestLayout();
        invalidate();
    }
}
