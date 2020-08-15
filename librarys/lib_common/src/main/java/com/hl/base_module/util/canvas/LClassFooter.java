package com.hl.base_module.util.canvas;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hl.base_module.util.screen.DensityUtil;

/**
 * 底部刷新布局自定义
 */
public class LClassFooter extends LinearLayout {
    private TextView mHeaderText;                    // 标题文本
    private ImageView mProgressView;                // 刷新动画视图
    private CircleRotateDrawable rotateDrawable;    // 刷新视图对应Drawable

    public LClassFooter(Context context) {
        this(context, null);
    }

    public LClassFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LClassFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LClassFooter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context);
    }

    /**
     * 初始化自定义旋转的Drawable
     *
     * @param context
     */
    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.parseColor("#efefef"));

        rotateDrawable = new CircleRotateDrawable();
        mProgressView = new ImageView(context);
        mProgressView.setImageDrawable(rotateDrawable);
        addView(mProgressView, DensityUtil.dip2px(context, 26), DensityUtil.dip2px(context, 26));

        mHeaderText = new TextView(context);
        mHeaderText.setTextColor(Color.parseColor("#000000"));
        mHeaderText.setText("  加载更多...");
        mHeaderText.setTextSize(16);
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // 转圈环: 上下间距走一走，注意是加载之后才能获取
        //        LinearLayout.MarginLayoutParams p = (LinearLayout.MarginLayoutParams) mProgressView.getLayoutParams();
        //        p.topMargin = 10;
        //        p.bottomMargin = 10;
        //        p.setMargins(p.leftMargin, 15, p.rightMargin, 15);
        //        mProgressView.requestLayout();
        setMinimumHeight(DensityUtil.dip2px(context, 46));
    }

    /**
     * 设置加载状态
     */
    public void setLoadData(){
        mProgressView.setVisibility(VISIBLE);
        mHeaderText.setText("  加载更多...");
        onStart();
    }

    /**
     * 设置没有数据状态
     */
    public void setNoMoreData(){
        mHeaderText.setText("没有更多数据了");
        onFinish();
        mProgressView.setVisibility(GONE);
    }

    /**
     * 启动转圈动画
     */
    public void onStart() {
        if (null != rotateDrawable)
            rotateDrawable.start(); //开始动画
    }

    /**
     * 停止转圈动画
     * @return
     */
    public int onFinish() {
        if (null != rotateDrawable)
            rotateDrawable.stop(); //停止动画
        return 0;                  //TODO 延迟0毫秒之后再弹回
    }
}
