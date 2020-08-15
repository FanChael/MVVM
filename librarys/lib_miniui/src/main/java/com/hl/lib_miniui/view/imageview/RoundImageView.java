package com.hl.lib_miniui.view.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.hl.lib_miniui.R;

/**
 * 圆角图片
*@Author: hl
*@Date: created at 2020/6/1 17:51
*@Description: com.hl.lib_miniui.view.imageview
*/
public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int radius = 20;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageViewGoogle, defStyleAttr, 0);
        radius = a.getDimensionPixelSize(R.styleable.CircleImageViewGoogle_civ_border_width, 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(path); //设置可显示的区域，canvas四个角会被剪裁掉
        super.onDraw(canvas);
    }
}
