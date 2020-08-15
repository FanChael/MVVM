package com.hl.lib_miniui.view.sswitch;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Switch;

import com.hl.base_module.util.image.ImageUtil;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/**
 * 扩展Switch
 */
public class SSwitch extends Switch {
    ///< 尺寸
    private int width = 160;
    private final int minWSize = 160;
    private int height = 60;
    private final int minHSize = 60;
    private boolean[] bAtMost = new boolean[]{false, false};

    ///< 基本属性
    private int track_bg_color;
    private int track_bg_s_color;
    private int track_stroke_color;
    private int track_stroke_s_color;
    private int track_corner_radius;
    private int thumb_bg_color;
    private int thumb_bg_s_color;
    private int thumb_stroke_color;
    private int thumb_stroke_s_color;
    private int thumb_size;
    private int thumb_special_w;

    ///< 图片设置方式
    private Drawable thumb_bg_s_drawble = null;
    private Drawable thumb_bg_drawble = null;
    private Drawable track_bg_s_drawble = null;
    private Drawable track_bg_drawble = null;

    public SSwitch(Context context) {
        this(context, null);
    }

    public SSwitch(Context context, AttributeSet attrs) {
        //this(context,attrs,0);
        super(context, attrs);
        intStyledAttributes(context, attrs, 0);
        setAttributes(context);
    }

    public SSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        intStyledAttributes(context, attrs, defStyleAttr);
        ///< 获取控件尺寸后进行属性设置 - 主要是适配thumb滑动按钮
        //setAttributes(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ///< 非完全自定义-只是获取宽高尺寸信息而已
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            width = minWSize;
            height = minHSize;
            bAtMost[0] = true;
            bAtMost[1] = true;
        } else if (wSpecMode == MeasureSpec.EXACTLY && hSpecMode == MeasureSpec.EXACTLY) {
            width = wSize;
            height = hSize;
            bAtMost[0] = false;
            bAtMost[1] = false;
        } else if (wSpecMode == MeasureSpec.EXACTLY) {
            width = wSize;
            height = minHSize;
            bAtMost[0] = false;
            bAtMost[1] = true;
        }  else if (hSpecMode == MeasureSpec.EXACTLY) {
            width = minWSize;
            height = hSize;
            bAtMost[0] = true;
            bAtMost[1] = false;
        }else if (wSpecMode == MeasureSpec.AT_MOST) {
            width = minWSize;
            height = hSize;
            bAtMost[0] = true;
            bAtMost[1] = false;
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            width = wSize;
            height = minHSize;
            bAtMost[0] = false;
            bAtMost[1] = true;
        }
        ///< switch的宽度 - 该宽度需要设置最小宽度，否则无法全部充满
        this.setSwitchMinWidth((0 == thumb_size) ? (thumb_size > width / 2 ? thumb_size * 2 : width) : thumb_size);
        ///< 获取到具体宽高后进行相关属性设置 - 主要是适配thumb滑动按钮
        //setAttributes(getContext());
    }

    /**
     * 设置基本属性
     */
    private void setAttributes(Context context) {
        GradientDrawable thumb_gd = null;
        GradientDrawable thumb_s_gd = null;
        GradientDrawable track_gd = null;
        GradientDrawable track_s_gd = null;

        ///< 以资源图片作为背景的方式，则不用创建GradientDrawable了
        ///< 此时修正一下根据设置的宽高修正下图片宽高，switch按钮也兼容下
        if (null != track_bg_s_drawble && null != track_bg_drawble &&
            null != thumb_bg_s_drawble && null != thumb_bg_drawble) {
            if (bAtMost[0] && bAtMost[1]) {
            } else if (!bAtMost[0] && bAtMost[1]) {
                track_bg_s_drawble = ImageUtil.zoomDrawable(context, track_bg_s_drawble, width, track_bg_s_drawble.getIntrinsicHeight());
                track_bg_drawble = ImageUtil.zoomDrawable(context, track_bg_drawble, width, track_bg_drawble.getIntrinsicHeight());

                thumb_bg_s_drawble = ImageUtil.zoomDrawable(context, thumb_bg_s_drawble, thumb_bg_s_drawble.getIntrinsicHeight(), thumb_bg_s_drawble.getIntrinsicHeight());
                thumb_bg_drawble = ImageUtil.zoomDrawable(context, thumb_bg_drawble, thumb_bg_drawble.getIntrinsicHeight(), thumb_bg_drawble.getIntrinsicHeight());
            } else if (bAtMost[0] && !bAtMost[1]) {
                track_bg_s_drawble = ImageUtil.zoomDrawable(context, track_bg_s_drawble, track_bg_s_drawble.getIntrinsicWidth(), height);
                track_bg_drawble = ImageUtil.zoomDrawable(context, track_bg_drawble, track_bg_drawble.getIntrinsicWidth(), height);


                thumb_bg_s_drawble = ImageUtil.zoomDrawable(context, thumb_bg_s_drawble, height, height);
                thumb_bg_drawble = ImageUtil.zoomDrawable(context, thumb_bg_drawble, height, height);
            } else {
                track_bg_s_drawble = ImageUtil.zoomDrawable(context, track_bg_s_drawble, width, height);
                track_bg_drawble = ImageUtil.zoomDrawable(context, track_bg_drawble, width, height);

                thumb_bg_s_drawble = ImageUtil.zoomDrawable(context, thumb_bg_s_drawble, height, height);
                thumb_bg_drawble = ImageUtil.zoomDrawable(context, thumb_bg_drawble, height, height);
            }

        } else {
            thumb_gd = new GradientDrawable();
            thumb_s_gd = new GradientDrawable();
            track_gd = new GradientDrawable();
            track_s_gd = new GradientDrawable();

            thumb_gd.setColor(thumb_bg_color);
            if (0 == thumb_special_w){
                thumb_gd.setShape(GradientDrawable.OVAL);
                thumb_gd.setSize((0 == thumb_size) ? height : thumb_size, (0 == thumb_size) ? height : thumb_size);
            }else{
                thumb_gd.setShape(GradientDrawable.RECTANGLE);
                thumb_gd.setCornerRadius(track_corner_radius);
                thumb_gd.setSize(thumb_special_w, (0 == thumb_size) ? height : thumb_size);
            }
            thumb_gd.setStroke(DensityUtil.dip2px(context, 1), thumb_stroke_color);

            thumb_s_gd.setColor(thumb_bg_s_color);
            if (0 == thumb_special_w){
                thumb_s_gd.setShape(GradientDrawable.OVAL);
                thumb_s_gd.setSize((0 == thumb_size) ? height : thumb_size, (0 == thumb_size) ? height : thumb_size);
            }else{
                thumb_s_gd.setShape(GradientDrawable.RECTANGLE);
                thumb_s_gd.setCornerRadius(track_corner_radius);
                thumb_s_gd.setSize(thumb_special_w, (0 == thumb_size) ? height : thumb_size);
            }
            thumb_s_gd.setStroke(DensityUtil.dip2px(context, 1), thumb_stroke_s_color);

            track_gd.setShape(GradientDrawable.RECTANGLE);
            track_gd.setColor(track_bg_color);
            track_gd.setCornerRadius(track_corner_radius);
            track_gd.setStroke(DensityUtil.dip2px(context, 1), track_stroke_color);

            track_s_gd.setShape(GradientDrawable.RECTANGLE);
            track_s_gd.setColor(track_bg_s_color);
            track_s_gd.setCornerRadius(track_corner_radius);
            track_s_gd.setStroke(DensityUtil.dip2px(context, 1), track_stroke_s_color);
        }

        ///< 开关按钮
        int checked = android.R.attr.state_checked;
        StateListDrawable thumb_stateListDrawable = new StateListDrawable();
        if (null != thumb_bg_s_drawble && null != thumb_bg_drawble) {
            thumb_stateListDrawable.addState(new int[]{checked}, thumb_bg_s_drawble);
            thumb_stateListDrawable.addState(new int[]{-checked}, thumb_bg_drawble);
        } else {
            thumb_stateListDrawable.addState(new int[]{checked}, thumb_s_gd);
            thumb_stateListDrawable.addState(new int[]{-checked}, thumb_gd);
        }

        ///< 开关背景
        StateListDrawable track_stateListDrawable = new StateListDrawable();
        if (null != track_bg_s_drawble && null != track_bg_drawble) {
            track_stateListDrawable.addState(new int[]{checked}, track_bg_s_drawble);
            track_stateListDrawable.addState(new int[]{-checked}, track_bg_drawble);
        } else {
            track_stateListDrawable.addState(new int[]{checked}, track_s_gd);
            track_stateListDrawable.addState(new int[]{-checked}, track_gd);
        }

        this.setThumbDrawable(thumb_stateListDrawable);
        this.setTrackDrawable(track_stateListDrawable);
    }

    /**
     * 初始化/获取属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void intStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        ///< @b 获取自定义属性数值值-方便取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.sswitch_styleable);

        track_bg_color = ta.getColor(R.styleable.sswitch_styleable_s_track_bg_color, -1);
        track_bg_s_color = ta.getColor(R.styleable.sswitch_styleable_s_track_bg_s_color, -1);
        track_stroke_color = ta.getColor(R.styleable.sswitch_styleable_s_track_stroke_color, -1);
        track_stroke_s_color = ta.getColor(R.styleable.sswitch_styleable_s_track_stroke_s_color, -1);
        if (-1 == track_stroke_color) {
            track_stroke_color = track_bg_color;
        }
        if (-1 == track_stroke_s_color) {
            track_stroke_s_color = track_bg_s_color;
        }
        track_corner_radius = ta.getDimensionPixelOffset(R.styleable.sswitch_styleable_s_track_corner_radius, 0);
        thumb_bg_color = ta.getColor(R.styleable.sswitch_styleable_s_thumb_bg_color, -1);
        thumb_bg_s_color = ta.getColor(R.styleable.sswitch_styleable_s_thumb_bg_s_color, -1);
        thumb_stroke_color = ta.getColor(R.styleable.sswitch_styleable_s_thumb_stroke_color, -1);
        thumb_stroke_s_color = ta.getColor(R.styleable.sswitch_styleable_s_thumb_stroke_s_color, -1);
        if (-1 == thumb_stroke_color) {
            thumb_stroke_color = thumb_bg_color;
        }
        if (-1 == thumb_stroke_s_color) {
            thumb_stroke_s_color = thumb_bg_s_color;
        }
        thumb_size = ta.getDimensionPixelOffset(R.styleable.sswitch_styleable_s_thumb_size, 0);
        thumb_special_w = ta.getDimensionPixelOffset(R.styleable.sswitch_styleable_s_thumb_special_w, 0);

        ///< 获取资源图片
        thumb_bg_s_drawble = ta.getDrawable(R.styleable.sswitch_styleable_s_thumb_drawble);
        thumb_bg_drawble = ta.getDrawable(R.styleable.sswitch_styleable_s_thumb_s_drawble);
        track_bg_s_drawble = ta.getDrawable(R.styleable.sswitch_styleable_s_track_drawble);
        track_bg_drawble = ta.getDrawable(R.styleable.sswitch_styleable_s_track_s_drawble);

        ta.recycle();
    }
}
