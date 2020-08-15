package com.hl.lib_miniui.view.toggle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
* 根据图片设置宽高的Toggle，解决默认图片被拉伸的问题
*@Author: hl
*@Date: created at 2020/3/27 10:09
*@Description: com.hl.base_module.ui
*/
public class SToggleButton extends androidx.appcompat.widget.AppCompatToggleButton {
    private OnCheckedStateListenner mOnCheckedStateListenner;
    private int bd_w, bd_h;

    public SToggleButton(Context context) {
        super(context);
        init(context, null);
    }

    public SToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化获取background，然后获取图片宽高进行设置
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 获取togglge背景图片的大小，用来设置控件大小
        TypedArray taSelf = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.background});
        StateListDrawable backG = (StateListDrawable) taSelf.getDrawable(0);
        BitmapDrawable bitmapDrawable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            bitmapDrawable = (BitmapDrawable) backG.getStateDrawable(0);
        } else {
            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) backG.getConstantState();
            bitmapDrawable = (BitmapDrawable) drawableContainerState.getChild(0);
        }
        Bitmap bd = bitmapDrawable.getBitmap();
        bd_w = bd.getWidth();
        bd_h = bd.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int specModeW = MeasureSpec.getMode(widthMeasureSpec);
        final int specSizeW = MeasureSpec.getSize(widthMeasureSpec);
        final int specModeH = MeasureSpec.getMode(heightMeasureSpec);
        final int specSizeH = MeasureSpec.getSize(heightMeasureSpec);
        int resultW = bd_w;
        int resultH = bd_h;
        switch (specModeW) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                resultW = specSizeW;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (specModeH) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                resultH = specSizeH;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(resultW, resultH);
    }

    /**
     * 选中取消状态监听
     * @param onCheckedStateListenner
     */
    public void registerOnCheckedState(OnCheckedStateListenner onCheckedStateListenner){
        this.mOnCheckedStateListenner = onCheckedStateListenner;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCheckedStateListenner){
                    mOnCheckedStateListenner.onStateCheckd(isChecked());
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOnCheckedStateListenner = null;
    }

    /**
     * 选中取消状态回调
     */
    public interface OnCheckedStateListenner{
        void onStateCheckd(boolean bChecked);
    }
}
