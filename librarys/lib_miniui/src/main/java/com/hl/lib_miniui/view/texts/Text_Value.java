package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.util.AttributeSet;

import com.hl.base_module.util.textview.HighLightKeyWordUtil;
import com.hl.lib_miniui.R;

public class Text_Value extends androidx.appcompat.widget.AppCompatTextView {
    private int key_color;
    private int key_size;
    private String key_word;

    public Text_Value(Context context) {
        super(context);
        init(context, null);
    }

    public Text_Value(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Text_Value(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 设置关键字颜色和大小 int keyColor, String text, String keyword, int keySize
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Text_Value);
        // 获取关键字
        key_word = ta.getString(R.styleable.Text_Value_value_keyword);
        // 获取关键字颜色
        key_color = ta.getColor(R.styleable.Text_Value_value_keycolor, 0xFFFF0000);
        // 获取关键字大小属性的设置
        key_size = (int) ta.getDimension(R.styleable.Text_Value_value_keysize, 20);
        ta.recycle();

        // 文字高亮部分增加点左右间距
        String spaceKeyWord = " " + key_word + " ";
        String lastText = getText().toString().replace(key_word, spaceKeyWord);
        // 设置高亮啊
        SpannableString spannableString = HighLightKeyWordUtil.getHighLightKeyWord(
                key_color, lastText, spaceKeyWord, key_size);
        setText(spannableString);
    }

    /**
     * 更新文本
     * @param text
     * @param keyword
     */
    public void updateText(String text, String keyword){
        // 文字高亮部分增加点左右间距
        String spaceKeyWord = " " + keyword + " ";
        String lastText = text.replace(keyword, spaceKeyWord);
        // 设置高亮啊
        SpannableString spannableString = HighLightKeyWordUtil.getHighLightKeyWord(
                key_color, lastText, spaceKeyWord, key_size);
        setText(spannableString);
    }

    /**
     * 返回关键字
     * @return
     */
    public String getKey_word(){
        return key_word;
    }
}
