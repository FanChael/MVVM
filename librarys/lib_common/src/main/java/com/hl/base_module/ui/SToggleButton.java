package com.hl.base_module.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hl.base_module.R;
import com.hl.base_module.util.ui.SelectorUtil;

/**
 * 简单代码设置了svn的selector，也可以直接定义selector。
 * 这里为了学习一把如何自定义selector哈！
*@Author: hl
*@Date: created at 2020/3/27 10:09
*@Description: com.hl.base_module.ui
*/
public class SToggleButton extends androidx.appcompat.widget.AppCompatToggleButton {
    private OnCheckedStateListenner mOnCheckedStateListenner;

    public SToggleButton(Context context) {
        this(context, null);
    }

    public SToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SelectorUtil.addSelectorFromDrawable(context, R.drawable.ic_switch, R.drawable.ic_switch_checked, this);
    }

    /**
     * 选中取消状态监听
     * @param onCheckedStateListenner
     */
    public void registerOnCheckedState(OnCheckedStateListenner onCheckedStateListenner){
        this.mOnCheckedStateListenner = onCheckedStateListenner;
        this.setOnClickListener(new View.OnClickListener() {
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
