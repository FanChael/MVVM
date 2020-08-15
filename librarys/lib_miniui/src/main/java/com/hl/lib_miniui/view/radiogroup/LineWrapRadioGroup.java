package com.hl.lib_miniui.view.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * 自动换行的RadioGroup
 */
public class LineWrapRadioGroup extends RadioGroup {
    private static final String TAG = "RadioGroup";

    //@hl 记录是第几行，需要做如果是第三行开始，则需要开始隐藏这些View
    private static final int MAX_SHOW_COUNT = 2;
    private int line_cout = 0;
    private boolean bFirtLine = true;
    private int totalShowHeight, totalFolderShowHeight = 0;
    //@hl 控制是否显示所有
    private boolean bShowAll = false;

    public LineWrapRadioGroup(Context context) {
        super(context);
    }

    public LineWrapRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //调用ViewGroup的方法，测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //最大的宽
        int maxWidth = 0;
        //累计的高
        int totalHeight = 0;

        //当前这一行的累计行宽
        int lineWidth = 0;
        //当前这行的最大行高
        int maxLineHeight = 0;
        //用于记录换行前的行宽和行高
        int oldHeight;
        int oldWidth;

        int count = getChildCount();
        //假设 widthMode和heightMode都是AT_MOST
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            //得到这一行的最高
            oldHeight = maxLineHeight;
            //当前最大宽度
            oldWidth = maxWidth;

            int deltaX = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            if (lineWidth + deltaX + getPaddingLeft() + getPaddingRight() > widthSize) {//如果折行,height增加
                //和目前最大的宽度比较,得到最宽。不能加上当前的child的宽,所以用的是oldWidth
                maxWidth = Math.max(lineWidth, oldWidth);
                //重置宽度
                lineWidth = deltaX;

                // 额外的margin_top间距，一旦换行，也就是从第二行开始就需要增加margin_top，记得累积高度
                int extern_margin_top = params.topMargin;

                //累加高度
                totalHeight += oldHeight;
                //重置行高,当前这个View，属于下一行，因此当前最大行高为这个child的高度加上margin
                maxLineHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin + extern_margin_top;

                //@hl 两行之后只显示两行的高度，不再增加换行高度
                ++line_cout;
                if (line_cout <= MAX_SHOW_COUNT) {
                    totalFolderShowHeight = totalHeight;
                }
            } else {
                //不换行，累加宽度
                lineWidth += deltaX;
                //不换行，计算行最高
                int deltaY = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxLineHeight = Math.max(maxLineHeight, deltaY);
            }
            if (i == count - 1) {
                //前面没有加上下一行的高，如果是最后一行，还要再叠加上最后一行的最高的值
                totalHeight += maxLineHeight;
                //计算最后一行和前面的最宽的一行比较
                maxWidth = Math.max(lineWidth, oldWidth);
            }
        }

        //加上当前容器的padding值
        maxWidth += getPaddingLeft() + getPaddingRight();
        totalHeight += getPaddingTop() + getPaddingBottom();
        //@hl 保存用于全部展开使用
        totalShowHeight = totalHeight;
        if (bShowAll) {
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                    heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
        } else {
            //@hl 高度如果超过两行，则只显示两行
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                    heightMode == MeasureSpec.EXACTLY ? heightSize :
                            (totalFolderShowHeight > 0 ? totalFolderShowHeight : totalShowHeight));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //pre为前面所有的child的相加后的位置
        int preLeft = getPaddingLeft();
        int preTop = getPaddingTop();
        //记录每一行的最高值
        int maxHeight = 0;
        //@hl 第一行不能加，之后需要添加margintop
        int extern_margin_top = 0;
        // 初始化 - 这个布局会回调几次
        bFirtLine = true;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int left;
            //@hl int extern_margin_top = 0;
            //r-l为当前容器的宽度。如果子view的累积宽度大于容器宽度，就换行。
            if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + getPaddingRight() > (r - l)) {
                //重置
                preLeft = getPaddingLeft();
                //要选择child的height最大的作为设置
                preTop = preTop + maxHeight;
                maxHeight = getChildAt(i).getMeasuredHeight() + params.topMargin + params.bottomMargin;
                //left坐标 - 刚换行的第一个不增加leftmargin
                left = preLeft;
                // 额外的margin_top间距，一旦换行，也就是从第二行开始就需要增加margin_top，记得累积
                extern_margin_top = extern_margin_top + params.topMargin;

                bFirtLine = false;
            } else { //不换行,计算最大高度
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
                //@hl 换行第一次的第二个控件不能再加一次preLeft
                if (!bFirtLine) {
                    //left坐标
                    left = preLeft;
                } else {
                    //left坐标
                    left = preLeft + params.leftMargin;
                }
            }
            //left坐标
            //@hl int left = preLeft + params.leftMargin;
            //top坐标
            int top = preTop + extern_margin_top; //params.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            //为子view布局
            child.layout(left, top, right, bottom);
            //计算布局结束后，preLeft的值
            preLeft += params.leftMargin + child.getMeasuredWidth() + params.rightMargin;
        }
    }

    /**
     * 点击显示其他控件
     */
    public void requestShowMeasure(boolean _bShowAll) {
        bShowAll = _bShowAll;
        requestLayout();
    }

    /**
     * 是否超过两行
     * @return
     */
    public boolean bHasMultiline(){
        return line_cout >= MAX_SHOW_COUNT;
    }
}
