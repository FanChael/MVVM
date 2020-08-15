package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/**
 * 图片文字Icon按钮+角标
 * 1.上面图标，下面文字
 *
 * @Author: hl
 * @Date: created at 2020/5/19 21:51
 * @Description: com.hl.lib_miniui.view
 */
public class Text_Icon_Badge extends LinearLayout {
    private TextView textViewCompat;
    private Icon_Badge icon_badge;

    public Text_Icon_Badge(Context context) {
        super(context);
        init(context, null);
    }

    public Text_Icon_Badge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Text_Icon_Badge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public Text_Icon_Badge(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 获取设置的属性信息
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 获取文本大小，颜色，文本，图标
        TypedArray taSelf = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.textSize,
                        android.R.attr.textColor,
                        android.R.attr.text,
                        android.R.attr.icon});
        float text_size = taSelf.getDimensionPixelOffset(0, 0);
        int text_color = taSelf.getColor(1, 0xffffffff);
        String text_string = taSelf.getString(2);
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Text_Icon);
        Drawable icon_id = ta.getDrawable(R.styleable.Text_Icon_icon_drawable);
        taSelf.recycle();

        // 创建文本
        textViewCompat = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewCompat.setLayoutParams(layoutParams);
        textViewCompat.setText(text_string);
        textViewCompat.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
        textViewCompat.setTextColor(text_color);

        // 创建图标
        icon_badge = new Icon_Badge(context);
        LayoutParams layoutParams2 = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 角标右侧占据了一定空间，整体再右移有点，尽量看上去居中
        layoutParams2.leftMargin = DensityUtil.dip2px(context, 2);
        layoutParams2.gravity = Gravity.CENTER_HORIZONTAL;
        icon_badge.setPadding(0, 0, 0, 0);
        icon_badge.setLayoutParams(layoutParams2);
        icon_badge.setImage(icon_id);
        //icon_badge.setNumber("5");

        // 先添加图片，再添加文字
        addView(icon_badge);
        addView(textViewCompat);
    }

    /**
     * 设置角标
     * @param number
     */
    public void setNumber(String number){
        icon_badge.setNumber(number);
    }
}
