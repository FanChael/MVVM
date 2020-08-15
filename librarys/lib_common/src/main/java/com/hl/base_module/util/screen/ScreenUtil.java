package com.hl.base_module.util.screen;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Author:hl
 * Time:  2018/5/24 11:34
 * Des:   屏幕信息相关工具
 * 1. 屏幕宽高
 * 2. 设置控件的间距，宽高，字体大小
 */
public class ScreenUtil {
    ///< 屏幕宽高【三维】
    public static int SCREEN_HEIGHT = 1920;
    public static int SCREEN_WIDTH = 1080;
    public static float ratio_width = 1;
    public static float ratio_height = 1;

    ///< 状态栏高度
    public static int STATUS_BAR_HEIGHT = 0;

    private ScreenUtil() {
    }

    /**
     * 初始化并设置上下文
     */
    public static void init(Context mContext) throws Exception {
        ///< 字体
        //textTypeface = Typeface.createFromAsset (context.getAssets() , "huakanghaibaotiW12.ttc" );
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_WIDTH = dm.widthPixels;
        ///< TODO 如果宽大于高，宽高互换 - 单纯做竖屏需要处理下吧？？？
        //        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
        //            int temp = SCREEN_HEIGHT;
        //            SCREEN_HEIGHT = SCREEN_WIDTH;
        //            SCREEN_WIDTH = temp;
        //        }

        ratio_height = ratio_width = (float) ((Math.sqrt(SCREEN_HEIGHT * SCREEN_HEIGHT + SCREEN_WIDTH * SCREEN_WIDTH)) / (Math.sqrt(1080 * 1080 + 1920 * 1920)));
        // 获得状态栏高度
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        STATUS_BAR_HEIGHT = mContext.getResources().getDimensionPixelSize(resourceId);
        if (STATUS_BAR_HEIGHT <= 0) {
            throw new Exception("获取状态高度失败");
        }
    }

    /**
     * 设置边距 - 参数如果为-10000， 则表示使用原始值
     */
    public static void setMargin(View v, int _l, int _t, int _r, int _b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int l = (int) (_l * ratio_width), t = (int) (_t * ratio_height),
                    r = (int) (_r * ratio_width), b = (int) (_b * ratio_height);
            if (-10000 == _l) {
                l = p.leftMargin;
            }
            if (-10000 == _r) {
                r = p.rightMargin;
            }
            if (-10000 == _t) {
                t = p.topMargin;
            }
            if (-10000 == _b) {
                b = p.bottomMargin;
            }
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 设置顶部间距为标题栏高度
     */
    public static void setStatusBarHeightTop(View v) {
        setStatusBarHeightTop(v, 1);
    }

    /**
     * 设置顶部间距为标题栏高度*N倍
     */
    public static void setStatusBarHeightTop(View v, int n) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int l = p.leftMargin, t = STATUS_BAR_HEIGHT * n,
                    r = p.rightMargin, b = p.bottomMargin;
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 设置边距 - 参数如果为-10000， 则表示使用原始值
     */
    public static void setMarginNoRatio(View v, int _l, int _t, int _r, int _b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int l = _l, t = _t, r = _r, b = _b;
            if (-10000 == _l) {
                l = p.leftMargin;
            }
            if (-10000 == _r) {
                r = p.rightMargin;
            }
            if (-10000 == _t) {
                t = p.topMargin;
            }
            if (-10000 == _b) {
                b = p.bottomMargin;
            }
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 设置边距 - 参数如果为-10000， 则表示使用原始值
     */
    public static void setMarginByW(View v, int _l, int _t, int _r, int _b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int l = (int) (_l * ratio_width), t = (int) (_t * ratio_width),
                    r = (int) (_r * ratio_width), b = (int) (_b * ratio_width);
            if (-10000 == _l) {
                l = p.leftMargin;
            }
            if (-10000 == _r) {
                r = p.rightMargin;
            }
            if (-10000 == _t) {
                t = p.topMargin;
            }
            if (-10000 == _b) {
                b = p.bottomMargin;
            }
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    ///< 设置组件的padding值
    public static void setPadding(View v, int left, int top, int right, int bottom) {
        v.setPadding((int) (left * ratio_width), (int) (top * ratio_height), (int) (right * ratio_width), (int) (bottom * ratio_height));
    }

    ///< 设置组件的padding值
    public static void setPaddingNoRatio(View v, int left, int top, int right, int bottom) {
        v.setPadding((int) (left), (int) (top), (int) (right), (int) (bottom));
    }

    ///< 设置父组件是ViewGroup的控件的宽高
    public static void setViewGroupWH(View v, int w, int h) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    public static void setViewGroupWHNoRatio(View v, int w, int h) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = w;
        layoutT.height = h;
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的宽高(高和宽相同)
    public static void setViewGroupByW(View v, int w) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (w * ratio_width);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的宽高（宽和高相同）
    public static void setViewGroupByH(View v, int h) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (h * ratio_height);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的宽
    public static void setViewGroupW(View v, int w) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的高
    public static void setViewGroupH(View v, int h) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的高
    public static void setViewGroupHNoRatio(View v, int h) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.height = h;
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ViewGroup的控件的高
    public static void setViewGroupHNoRatio(View v, int width, int height) {
        ViewGroup.LayoutParams layoutT = (ViewGroup.LayoutParams) v.getLayoutParams();
        layoutT.width = width;
        layoutT.height = height;
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是LinearLayout的控件的宽高
    public static void resizeLinearLayoutWH(View v, int w, int h) {
        LayoutParams layoutT = (LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是LinearLayout的控件的宽高
    public static void setLinearLayoutWH(View v, int w, int h) {
        LayoutParams param = new LayoutParams((int) (w * ratio_width), (int) (h * ratio_height));
        v.setLayoutParams(param);
    }

    ///< 设置父组件是LinearLayout的控件的宽
    public static void resizeLinearLayoutW(View v, int w) {
        LayoutParams layoutT = (LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是LinearLayout的控件的宽
    public static void resizeLinearLayoutByW(View v, int w) {
        LayoutParams layoutT = (LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = layoutT.width;
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是LinearLayout的控件的宽
    public static void resizeLinearLayoutByH(View v, int h) {
        LayoutParams layoutT = (LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        layoutT.width = layoutT.height;
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是LinearLayout的控件的高
    public static void resizeLinearLayoutH(View v, int h) {
        LayoutParams layoutT = (LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    /// < 设置父组件是RelativeLayout的控件的宽高
    public static void setFrameLayoutWH(View v, int w, int h) {
        FrameLayout.LayoutParams layoutT = (FrameLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是RelativeLayout的控件的宽高
    public static void setRelativeLayoutWH(View v, int w, int h) {
        RelativeLayout.LayoutParams layoutT = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是ConstraintLayout的控件的宽高
    public static void setConstraintLayoutWH(View v, int w, int h) {
        ConstraintLayout.LayoutParams layoutT = (ConstraintLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    /**
     * 以屏幕宽度作为宽度的情况下，不要再做比例缩放了呀...
     *
     * @param v
     * @param w
     * @param h
     */
    public static void setConstraintLayoutWHNoRatio(View v, int w, int h) {
        ConstraintLayout.LayoutParams layoutT = (ConstraintLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = w;
        layoutT.height = h;
        v.setLayoutParams(layoutT);
    }

    /**
     * 以屏幕宽度作为宽度的情况下，不要再做比例缩放了呀...
     *
     * @param v
     * @param w
     * @param h
     */
    public static void setLinearLayoutWHNoRatio(View v, int w, int h) {
        LinearLayout.LayoutParams layoutT = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = w;
        layoutT.height = h;
        v.setLayoutParams(layoutT);
    }

    public static void setConstraintLayoutH(View v, int h) {
        ConstraintLayout.LayoutParams layoutT = (ConstraintLayout.LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是RelativeLayout的控件的宽（高不变）
    public static void setRelativeLayoutW(View v, int w) {
        RelativeLayout.LayoutParams layoutT = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是RelativeLayout的控件的高(宽不变)
    public static void setRelativeLayoutH(View v, int h) {
        RelativeLayout.LayoutParams layoutT = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是RelativeLayout的控件的宽高（高和宽相同）
    public static void setRelativeLayoutByW(View v, int w) {
        RelativeLayout.LayoutParams layoutT = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (w * ratio_width);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父组件是RelativeLayout的控件的宽高（高和宽相同）
    public static void setRelativeLayoutByH(View v, int h) {
        RelativeLayout.LayoutParams layoutT = (RelativeLayout.LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        layoutT.width = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父窗口为AbsListView的控件宽高
    public static void setAbsListViewWH(View v, int w, int h) {
        AbsListView.LayoutParams layoutT = (AbsListView.LayoutParams) v.getLayoutParams();
        layoutT.width = (int) (w * ratio_width);
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    ///< 设置父窗口为AbsListView的控件宽高
    public static void setAbsListViewH(View v, int h) {
        AbsListView.LayoutParams layoutT = (AbsListView.LayoutParams) v.getLayoutParams();
        layoutT.height = (int) (h * ratio_height);
        v.setLayoutParams(layoutT);
    }

    /**
     * 设置控件字体大小
     *
     * @param view
     * @param textSize
     */
    public static void setTextSize(View view, int textSize) {
        float size = textSize * ratio_height < 19 ? 19 : textSize * ratio_height;
        if (view instanceof Button) {
            ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        } else if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        } else if (view instanceof EditText) {
            ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }
}
