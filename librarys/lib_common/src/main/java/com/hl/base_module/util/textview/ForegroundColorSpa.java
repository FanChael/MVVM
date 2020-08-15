package com.hl.base_module.util.textview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

/**
 * span包含三部分：shape、文字和距离其他文字的空白
 */

public class ForegroundColorSpa extends ReplacementSpan {
    private int keyColor;
    private int mSize, diffSize;
    private int keySize;

    public ForegroundColorSpa(int keyColor, int keySize) {
        super();
        this.keyColor = keyColor;
        this.keySize = keySize;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        // 关键字后面增加一定间距，太挤了不好！增加了间距之后，我们绘制的过程需要减回来，不然边框就绘制出去了！
        diffSize = (int) paint.measureText("  ");
        return (mSize = (int) (paint.measureText(text, start, end) + diffSize));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int originalColor = paint.getColor();

        //画文字
        paint.setColor(keyColor);
        paint.setTextSize(keySize);
        canvas.drawText(text, start, end, x + diffSize/2, y, paint);

        //将paint复原
        paint.setColor(originalColor);
    }
}