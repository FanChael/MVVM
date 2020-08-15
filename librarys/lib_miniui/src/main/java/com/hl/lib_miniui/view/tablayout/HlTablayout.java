package com.hl.lib_miniui.view.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.hl.lib_miniui.R;

import java.util.List;

/**
 * 简单定义个Tablelayout，简化tab的添加，添加支持角标
*@Author: hl
*@Date: created at 2020/5/28 14:03
*@Description: com.hl.lib_miniui.view.tablayout
*/
public class HlTablayout extends TabLayout {
    private int text_size, text_selected_size;

    public HlTablayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public HlTablayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HlTablayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化配置
     * @param context
     */
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.hl_tablatyou_styleable);
        text_size = ta.getDimensionPixelOffset(R.styleable.hl_tablatyou_styleable_s_tab_text_size, 32);
        text_selected_size = ta.getDimensionPixelOffset(R.styleable.hl_tablatyou_styleable_s_tab_text_selected_size, 36);

        //事件监听，改变字体大小和样式
        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                changeTabTextStyle(tab);
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });
    }

    /**
     * 设置标题，添加Tab
     */
    public void setTableTitles(List<String> stringList){
        // 已经添加过tab了，直接改样式即可
        if (getTabCount() > 0) {
            // 添加Tab控件
            for (String msg : stringList) {
                addTab(newTab().setText(msg));
            }
        }
        // 设置Tab自定义四布局
        for (int i = 0; i < getTabCount(); ++i) {
            Tab tab = getTabAt(i);
            // 设置自定义布局
            tab.setCustomView(R.layout.tab_badge);
            View view = tab.getCustomView();
            if (null != view) {
                // 布局信息
                TextView textView = view.findViewById(R.id.tab_textMsg);
                // 默认第0个选中的话，加粗
                if (0 == i) {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
                textView.setText(stringList.get(i));
            }
        }
    }

    /**
     * 设置Tab标题等样式，TabLayoutMediator会调用
     */
    public void setTableTitles(Tab tab, String title, boolean bBold){
        // 设置自定义布局
        tab.setCustomView(R.layout.tab_badge);
        View view = tab.getCustomView();
        if (null != view) {
            // 布局信息
            TextView textView = view.findViewById(R.id.tab_textMsg);
            // 加粗
            if (bBold) {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            textView.setText(title);
        }
    }

    /**
     * 改变选中字体的大小
     * @param currentTab
     */
    private void changeTabTextStyle(Tab currentTab){
        // 设置Tab自定义四布局
        for (int i = 0; i < getTabCount(); ++i) {
            Tab tab = getTabAt(i);
            View view = tab.getCustomView();
            if (null != view) {
                // 布局信息
                TextView textView = view.findViewById(R.id.tab_textMsg);
                if (tab == currentTab) {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_selected_size);
                } else {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
                }
            }
        }
    }
}
