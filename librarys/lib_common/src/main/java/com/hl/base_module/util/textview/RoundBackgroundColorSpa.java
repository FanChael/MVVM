package com.hl.base_module.util.textview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * span包含三部分：shape、文字和距离其他文字的空白
 */

public class RoundBackgroundColorSpa extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int textSize;
    private int mSize, diffSize;
    private int radius;

    private float originTextH, nowTextH, diffH;

    public RoundBackgroundColorSpa(int bgColor, int textColor, int textSize, int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.textSize = textSize;
        this.radius = radius;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //我们需要区分一点，关键字绘制前我们控件文本大小是设置了的。而我们想要关键字不一样大小，
        //则需要获取原始大小，然后根据关键字大小来计算边框大小，位置，字体绘制位置等
        originTextH = (paint.descent() - paint.ascent());

        // 是否需要保证关键字最多为文本大小？
        // textSize = textSize > (int) paint.getTextSize() ? (int) paint.getTextSize() : textSize;
        // This is important...后续计算均是以我们设置了关键字大小计算的(不然你绘制的时候会想不清楚位置)
        paint.setTextSize(textSize);

        //绘制整体往上平移，以文本顶部为准
        nowTextH = (int) (paint.descent() - paint.ascent());
        diffH = (originTextH - nowTextH)/2 - 4; // 平移后超过多了点，少平移一些

        // 关键字后面增加一定间距，太挤了不好！增加了间距之后，我们绘制的过程需要减回来，不然边框就绘制出去了！
        diffSize = (int) paint.measureText("  ");
        return (mSize = (int) (paint.measureText(text, start, end )) + diffSize);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int originalColor = paint.getColor();
        float defaultStrokeWidth = paint.getStrokeWidth();
        //绘制圆角矩形
        paint.setColor(this.bgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        //画圆角矩形背景
        RectF oval = new RectF(
                x + 2.5f,
                y + paint.ascent() - 2 - diffH,
                x + mSize + radius * 2 - diffSize,
                y + paint.descent() + 2 - diffH);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, radius, radius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径

        //画文字
        paint.setColor(this.textColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(defaultStrokeWidth);
        canvas.drawText(text, start, end, x + radius, y - diffH, paint);

        //将paint复原
        paint.setColor(originalColor);
    }
}