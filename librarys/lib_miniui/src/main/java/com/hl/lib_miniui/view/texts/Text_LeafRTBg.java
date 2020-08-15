package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.ui.ShapeUtil;
import com.hl.lib_miniui.R;

public class Text_LeafRTBg extends androidx.appcompat.widget.AppCompatTextView {
    private int text_color, bg_color;
    private int text_size;
    private int shape_color;

    public Text_LeafRTBg(Context context) {
        super(context);
        init(context, null);
    }

    public Text_LeafRTBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Text_LeafRTBg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 设置关键字颜色和大小 int keyColor, String text, String keyword, int keySize
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Text_Leaf);
        // 获取关键字
        String text_word = ta.getString(R.styleable.Text_Leaf_leaf_textword);
        // 获取关键字颜色
        text_color = ta.getColor(R.styleable.Text_Leaf_leaf_textcolor, 0xffffffff);
        // 背景颜色
        bg_color = ta.getColor(R.styleable.Text_Leaf_leaf_bgcolor, 0xff3a70db);
        // 获取关键字大小属性的设置
        text_size = (int) ta.getDimension(R.styleable.Text_Leaf_leaf_textsize, 22);
        ta.recycle();

        // 设置内间距
        setPadding(DensityUtil.dip2px(context, 13),
                DensityUtil.dip2px(context, 3),
                DensityUtil.dip2px(context, 13),
                DensityUtil.dip2px(context, 3));
        // 设置背景色
        setBackground(ShapeUtil.createShape(
                -1,
                new float[]{0, DensityUtil.dip2px(context, 9),
                        0, DensityUtil.dip2px(context, 11)},
                -1, null, bg_color));
        setTextColor(text_color);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
        if (!TextUtils.isEmpty(text_word)) {
            setText(text_word);
        }
    }

    /**
     * 更新文本
     * @param textword
     */
    public void updateText(String textword){
        setText(textword);
    }
}
