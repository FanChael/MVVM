package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/**
 * 左边图标，右边文本控件
*@Author: hl
*@Date: created at 2020/6/23 14:45
*@Description: com.hl.lib_miniui.view.texts
*/
public class Icon_Text extends LinearLayout {
    private AppCompatImageView imageViewL;
    private AppCompatTextView textViewR;

    public Icon_Text(Context context) {
        super(context);
        init(context, null);
    }

    public Icon_Text(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Icon_Text(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public Icon_Text(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 水平布局
        setOrientation(HORIZONTAL);

        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconL_TextR);
        Drawable left_icon = ta.getDrawable(R.styleable.IconL_TextR_it_left_icon);
        // 获取关键字
        String right_texttip = ta.getString(R.styleable.IconL_TextR_it_right_text);
        // 获取关键字颜色大小
        int right_textcolor = ta.getColor(R.styleable.IconL_TextR_it_right_textcolor, 0x999999);
        int right_textsize = (int) ta.getDimension(R.styleable.IconL_TextR_it_right_textsize, 28);
        ta.recycle();

        LayoutParams layoutParamsL = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsL.gravity= Gravity.CENTER_VERTICAL;
        imageViewL = new AppCompatImageView(context);
        imageViewL.setLayoutParams(layoutParamsL);
        imageViewL.setImageDrawable(left_icon);

        LayoutParams layoutParamsR = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsR.gravity = Gravity.CENTER_VERTICAL;
        layoutParamsR.leftMargin = DensityUtil.dip2px(context, 6);
        textViewR = new AppCompatTextView(context);
        textViewR.setLayoutParams(layoutParamsR);
        textViewR.setTextSize(TypedValue.COMPLEX_UNIT_PX, right_textsize);
        textViewR.setTextColor(right_textcolor);
        textViewR.setText(TextUtils.isEmpty(right_texttip) ? "----" : right_texttip);

        addView(imageViewL);
        addView(textViewR);
    }
}
