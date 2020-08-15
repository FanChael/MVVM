package com.hl.lib_miniui.view.radiogroup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.lib_miniui.R;

import java.util.List;

/**
 * 自动换行的RadioGroup
 */
public class LineWrapNoFoldRadioGroup extends RadioGroup {
    private static final String TAG = "RadioGroup";

    // @hl private boolean bFirtLine = true;
    // 充值样式 - 上一个选中的View
    private View lastView;

    public LineWrapNoFoldRadioGroup(Context context) {
        super(context);
    }

    public LineWrapNoFoldRadioGroup(Context context, AttributeSet attrs) {
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
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
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
        // @hl int extern_margin_top = 0;
        // 初始化 - 这个布局会回调几次
        // @hl bFirtLine = true;
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
                // @hl extern_margin_top = extern_margin_top + params.topMargin;

                //@hl bFirtLine = false;
            } else { //不换行,计算最大高度
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
                //@hl 换行第一次的第二个控件不能再加一次preLeft
                //                if (!bFirtLine) {
                //                    //left坐标
                //                    left = preLeft;
                //                } else {
                //                    //left坐标
                //                    left = preLeft + params.leftMargin;
                //                }
                left = preLeft + params.leftMargin;
            }
            //left坐标
            //@hl int left = preLeft + params.leftMargin;
            //top坐标
            int top = preTop + params.topMargin;//extern_margin_top; //params.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();
            //为子view布局
            child.layout(left, top, right, bottom);
            //计算布局结束后，preLeft的值
            preLeft += params.leftMargin + child.getMeasuredWidth() + params.rightMargin;
        }
    }

    /**
     * 普通RadioButton文本样式
     * @param context
     * @param valueBeanList
     * @param heightDp
     * @param columCount
     */
    public void addRadioButton(Activity context, List<String> valueBeanList, int heightDp, int columCount) {
        // 左右间距都是15dp
        int lastW = ScreenUtil.SCREEN_WIDTH -
                DensityUtil.dip2px(context, 15 * 2) -
                DensityUtil.dip2px(context, 10 * (columCount - 1)) -
                DensityUtil.dip2px(context, 15);
        lastW = lastW / columCount;
        for (int i = 0; i < valueBeanList.size(); ++i) {
            RadioButton radioButton = (RadioButton) context.getLayoutInflater().inflate(R.layout.hotcity_radiobutton_item, null);
            radioButton.setId(i); // checkedId
            radioButton.setText(valueBeanList.get(i));
            LayoutParams lp = new LayoutParams(lastW,
                    DensityUtil.dip2px(context, heightDp));
            if (0 == (i % columCount)) { // 第一列左侧无间距
                if (i >= columCount) {
                    lp.setMargins(0, DensityUtil.dip2px(context, 10), 0, 0);
                }
            } else {
                if (i >= columCount) {
                    lp.setMargins(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10), 0, 0);
                } else {
                    lp.setMargins(DensityUtil.dip2px(context, 10), 0, 0, 0);
                }
            }
            addView(radioButton, lp);
        }
    }

    /**
     * 充值样式单选，自定义布局
     * @param context
     * @param maodou_number
     * @param maodou_number_price
     * @param columCount
     */
    public void addRadioView(Activity context,
                             List<String> maodou_number, List<String> maodou_number_price,
                             int columCount) {
        // 左右间距都是15dp
        int lastW = ScreenUtil.SCREEN_WIDTH -
                DensityUtil.dip2px(context, 15 * 2) -
                DensityUtil.dip2px(context, 10 * (columCount - 1));
        lastW = lastW / columCount;
        for (int i = 0; i < maodou_number.size(); ++i) {
            View view = context.getLayoutInflater().inflate(R.layout.dongdou_number_radiobutton_item, null);
            view.setId(i);
            ((TextView) view.findViewById(R.id.riv_topTitle)).setText(maodou_number.get(i));
            ((TextView) view.findViewById(R.id.riv_bottomTitle)).setText(maodou_number_price.get(i));
            MarginLayoutParams lp = new MarginLayoutParams(lastW,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (0 == (i % columCount)) { // 第一列左侧无间距
                if (i >= columCount) {
                    lp.setMargins(0, DensityUtil.dip2px(context, 10), 0, 0);
                }
            } else {
                if (i >= columCount) {
                    lp.setMargins(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10), 0, 0);
                } else {
                    lp.setMargins(DensityUtil.dip2px(context, 10), 0, 0, 0);
                }
            }
            // @onclick 选中第4个
            if (4 == i) {
                view.setBackground(AppCompatResources.getDrawable(context, R.drawable.green_stroke_little_cornner_shape));
                ((TextView) view.findViewById(R.id.riv_topTitle)).setTextColor(Color.parseColor("#ff3098ee"));
                lastView = view;
            }
            addView(view, lp);
            // @onclick 添加点击事件，准备做自己做互斥
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastView != v) {
                        v.setBackground(AppCompatResources.getDrawable(context, R.drawable.green_stroke_little_cornner_shape));
                        lastView.setBackground(AppCompatResources.getDrawable(context, R.drawable.gray_stroke_little_cornner_shape));

                        ((TextView) v.findViewById(R.id.riv_topTitle)).setTextColor(Color.parseColor("#ff3098ee"));
                        ((TextView) v.findViewById(R.id.riv_bottomTitle)).setTextColor(Color.parseColor("#ff3098ee"));
                        ((TextView) lastView.findViewById(R.id.riv_topTitle)).setTextColor(Color.parseColor("#ff333333"));
                        ((TextView) lastView.findViewById(R.id.riv_bottomTitle)).setTextColor(Color.parseColor("#ff333333"));

                        lastView = v;
                    }
                }
            });
        }
    }

    /**
     * 充值样式，返回当前选中的View
     * @return
     */
    public View getLastView(){
        return lastView;
    }
}
