package com.hl.base_module.util.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

/*
 *@Description: Shape创建工具
 *@Author: hl
 *@Time: 2019/2/19 16:42
 */
public class ShapeUtil {
    /**
     * 创建一个Shape - GradientDrawable
     *
     * @param _strokeWidth - 沿边线厚度；无需传入-1
     * @param _roundRadius - 圆角半径；无需传入-1
     * @param _shape       - shape绘制类型(rectangle、oval等)；无需传入-1，将采用默认的GradientDrawable.RECTANGLE
     * @param _strokeColor - 沿边线颜色；无需传入null/""
     * @param _fillColor   - 内部填充颜色
     * @return
     */
    public static GradientDrawable createShape(int _strokeWidth,
                                               int _roundRadius, int _shape,
                                               String _strokeColor, String _fillColor) {
        int strokeWidth = _strokeWidth; // px not dp
        int roundRadius = _roundRadius; // px not dp
        int strokeColor = -1;
        if (null != _strokeColor && !_strokeColor.equals("")) {
            strokeColor = Color.parseColor(_strokeColor);
        }
        int fillColor = Color.parseColor(_fillColor);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);

        if (-1 == _shape) {
            gd.setShape(GradientDrawable.RECTANGLE);
        } else {
            gd.setShape(_shape);
        }

        if (-1 != roundRadius) {
            gd.setCornerRadius(roundRadius);
        }
        if (-1 != strokeWidth && -1 != strokeColor) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        return gd;
    }

    /**
     * 创建一个Shape - GradientDrawable
     *
     * @param _strokeWidth - 沿边线厚度；无需传入-1
     * @param _roundRadius - 四个角度都需要；无需传入null
     * @param _shape       - shape绘制类型(rectangle、oval等)；无需传入-1，将采用默认的GradientDrawable.RECTANGLE
     * @param _strokeColor - 沿边线颜色；无需传入null/""
     * @param _fillColor   - 内部填充颜色
     * @return
     */
    public static GradientDrawable createShape(int _strokeWidth,
                                               float[] _roundRadius, int _shape,
                                               String _strokeColor, int _fillColor) {
        int strokeWidth = _strokeWidth; // px not dp
        float[] roundRadius = _roundRadius; // px not dp
        int strokeColor = -1;
        if (!TextUtils.isEmpty(_strokeColor)) {
            strokeColor = Color.parseColor(_strokeColor);
        }
        int fillColor = _fillColor;

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);

        if (-1 == _shape) {
            gd.setShape(GradientDrawable.RECTANGLE);
        } else {
            gd.setShape(_shape);
        }

        if (null != roundRadius) {
            gd.setCornerRadii(new float[]{
                    roundRadius[0], roundRadius[0],
                    roundRadius[1], roundRadius[1],
                    roundRadius[2], roundRadius[2],
                    roundRadius[3], roundRadius[3]});
        }
        if (-1 != strokeWidth && -1 != strokeColor) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        return gd;
    }
}
