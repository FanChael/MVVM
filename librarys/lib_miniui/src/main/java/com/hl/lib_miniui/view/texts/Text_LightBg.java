package com.hl.lib_miniui.view.texts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

public class Text_LightBg extends androidx.appcompat.widget.AppCompatTextView {
    private Paint paint;
    private int bg_color;
    private int key_size;
    private int mWidth, mHeight;

    public Text_LightBg(Context context) {
        super(context);
        init(context, null);
    }

    public Text_LightBg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Text_LightBg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 设置关键字颜色和大小 int keyColor, String text, String keyword, int keySize
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Text_Value);
        // 获取关键字
        String key_word = ta.getString(R.styleable.Text_Value_value_keyword);
        // 获取关键字颜色
        bg_color = ta.getColor(R.styleable.Text_Value_value_keycolor, 0xFFFF0000);
        // 获取关键字大小属性的设置
        key_size = (int) ta.getDimensionPixelSize(R.styleable.Text_Value_value_keysize, 20);
        ta.recycle();

        if (null == paint) {
            paint = new Paint();
            paint.setColor(bg_color);
            paint.setTextSize(key_size);
            paint.setAntiAlias(true);
        }
        Rect rect = new Rect();
        paint.getTextBounds(key_word, 0, key_word.length(), rect);
        mWidth = rect.width();
        mHeight = rect.height();

        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, key_size);
        //setTextColor(context.getResources().getColor(R.color.white));
        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        setText(key_word);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0,
                mWidth + DensityUtil.dip2pxF(getContext(), 4),
                mHeight + DensityUtil.dip2pxF(getContext(),4),
                DensityUtil.dip2pxF(getContext(),2),
                DensityUtil.dip2pxF(getContext(),2), paint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 左右增加2dp
        setMeasuredDimension((int) (mWidth + DensityUtil.dip2pxF(getContext(),4)),
                (int) (mHeight + DensityUtil.dip2pxF(getContext(),4)));
    }

    /**
     * 更新文本
     * @param keyword
     * @param keyword
     */
    public void updateText(String keyword){
        Rect rect = new Rect();
        paint.getTextBounds(keyword, 0, keyword.length(), rect);
        mWidth = rect.width();
        mHeight = rect.height();
        setText(keyword);
    }
}
