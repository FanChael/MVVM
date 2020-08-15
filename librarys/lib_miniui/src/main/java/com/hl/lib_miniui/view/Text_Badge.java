package com.hl.lib_miniui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.hl.lib_miniui.R;

/**
 * 带圆形背景的文字控件
 *
 * @Author: hl
 * @Date: created at 2020/5/9 11:25
 * @Description: com.hl.lib_miniui.view
 */
public class Text_Badge extends AppCompatTextView {
    private Paint paint, text_paint;
    private int fill_color, text_color;
    private int padding_v;
    private Rect rect = new Rect();

    // 绘制单纯圆点时设置一个距离顶部的间距
    private final static int margin_top = 20;

    public Text_Badge(Context context) {
        super(context);
        this.init(context, null);
    }

    public Text_Badge(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public Text_Badge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    /**
     * 初始化函数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Text_Badge);
        // 获取角标填充颜色
        fill_color = ta.getColor(R.styleable.Text_Badge_nbg_color, 0xFFFF0000);
        // 获取角标内部间距
        padding_v = (int) ta.getDimension(R.styleable.Text_Badge_nbg_padding, 20);
        if (padding_v < 10) padding_v = 10;
        // 获取字体大小，字体颜色和样式
        TypedArray taSelf = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.textSize, android.R.attr.textColor});
        float text_size = taSelf.getDimensionPixelSize(0, 0);
        text_color = taSelf.getColor(1, 0xffffffff);
        // 背景画笔
        if (null == paint) {
            paint = new Paint();
            paint.setColor(fill_color);
            paint.setAntiAlias(true);
            paint.setTextSize(text_size);
        }
        // 文字画笔
        if (null == text_paint) {
            text_paint = new TextPaint();
            text_paint.setColor(text_color);
            text_paint.setAntiAlias(true);
            text_paint.setTypeface(Typeface.DEFAULT_BOLD);
            text_paint.setTextSize(text_size);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 由于外部会动态设置文本，所以每次我们都重新获取文字宽高
        paint.getTextBounds(getText().toString(), 0, getText().toString().length(), rect);

        // ViewGroup的最大宽度和高度
        int maxWidth = rect.width();
        int maxHeight = rect.height();

        // 自定义View没有子控件，不需要测量子控件

        /**测量ViewGroup**/
        // ViewGroup内边距 - 不考虑自身的getPadding...()，需要用户动态设置nbg_padding属性
        maxWidth += padding_v * 2;
        maxHeight += padding_v * 2;

        // 然后再与容器本身的最小宽高对比，取其最大值 - 有一种情况就是带背景图片的容器，要考虑图片尺寸
        // But,这里已经有字体实际宽高了，不需要再兼容其他情况了
        //maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        //maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxHeight = maxWidth = (Math.max(maxWidth, maxHeight));

        // 是否显示数字还是只是红点
        if (!TextUtils.isEmpty(getText().toString())) {
            // 设置ViewGroup尺寸
            setMeasuredDimension(maxWidth, maxHeight);
        } else {
            setMeasuredDimension(30, 30 + margin_top);
        }
    }

    /**
     * 扩展绘制，增加圆形背景
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制字体之前先绘制背景
        int radius = getWidth();
        if (!TextUtils.isEmpty(getText().toString())) {
            canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
            //super.onDraw(canvas);
            // 自行绘制， 注意文字绘制的范围是rect.height, 所以左下角y值高度要重新计算，建议自行绘制一下就知道了
            canvas.drawText(getText().toString(), padding_v, (radius + rect.height()) / 2, text_paint);
        } else {
            canvas.drawCircle(radius / 2, radius / 2 + margin_top, radius / 2, paint);
        }
    }
}
