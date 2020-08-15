package com.hl.lib_miniui.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.hl.base_module.util.screen.DensityUtil;
import com.hl.lib_miniui.R;

/** 走路进度条
*@Author: hl
*@Date: created at 2020/5/22 11:15
*@Description: com.hl.lib_miniui.view.progress
*/
public class StepProgress extends View {
    private int step_tip_color;
    private int step_tip_size;

    private int stepprogress_color;
    private int stepprogress_s_color;
    private int stepprogress_bg_color;

    // 小人图片大小
    private Bitmap pepleImg;
    private int bd_w, bd_h;
    // 字体高度
    private int textH;
    private int step_tip_text_top;

    // 相关绘制画笔
    private Paint progressBg, getProgressLine;
    private Paint textPainter; //文字
    // 进度宽高，以及距离上一个控件的间距信息，便于绘制
    private int progress_bg_w, progress_bg_h,
            progress_w, progress_h, progress_bg_top, progress_top;
    private RectF progress_bg_rect = new RectF();
    // 进度的格子数
    private final int progress_line_count = 20;
    private int current_line_index = 3;
    private float progress_bg_radius = 5;
    private float progress_radius = 3;

    // 进度文本提示
    private String stepTipString = "3818/6000步";

    public StepProgress(Context context) {
        super(context);
        init(context, null);
    }

    public StepProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StepProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public StepProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 获取设置属性值
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        //开启硬件离屏缓存：解决黑色问题，效率比关闭硬件加速高。暂时没有发现其他影响
        setLayerType(LAYER_TYPE_HARDWARE, null);

        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.step_progress_styleable);

        // 获取小人
        BitmapDrawable people = (BitmapDrawable) ta.getDrawable(R.styleable.step_progress_styleable_s_step_people);
        pepleImg = people.getBitmap();
        bd_w = pepleImg.getWidth();
        bd_h = pepleImg.getHeight();

        // 获取提示文字颜色
        step_tip_color = ta.getColor(R.styleable.step_progress_styleable_s_step_tip_color, 0xff999999);
        // 获取提示文字大小属性的设置
        step_tip_size = ta.getDimensionPixelOffset(R.styleable.step_progress_styleable_s_step_tip_size, 20);
        // 进度相关颜色 进度颜色、走过的颜色、背景颜色
        stepprogress_color = ta.getColor(R.styleable.step_progress_styleable_s_stepprogress_color, 0xff67808e);
        stepprogress_s_color = ta.getColor(R.styleable.step_progress_styleable_s_stepprogress_s_color, 0xfff9cb29);
        stepprogress_bg_color = ta.getColor(R.styleable.step_progress_styleable_s_stepprogress_bg_color, 0xfff0f6fb);
        ta.recycle();

        // 获取文字高度
        textPainter = new Paint();
        textPainter.setColor(step_tip_color);
        textPainter.setTextSize(step_tip_size);
        textPainter.setAntiAlias(true);
        Rect rectWH = new Rect();
        textPainter.getTextBounds("6000步高度",0,"6000步高度".length(), rectWH);
        textH = rectWH.height();

        // 走路进度和背景绘制
        progressBg = new Paint();
        progressBg.setColor(stepprogress_bg_color);
        progressBg.setStyle(Paint.Style.FILL);
        progressBg.setAntiAlias(true);

        getProgressLine = new Paint();
        getProgressLine.setColor(stepprogress_s_color);
        getProgressLine.setStyle(Paint.Style.FILL);
        getProgressLine.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 目前控件宽度只需要测量就行，高度需要根据我们想展示的内容做一个累积
        //final int specModeW = MeasureSpec.getMode(widthMeasureSpec);
        int specSizeW = MeasureSpec.getSize(widthMeasureSpec);
        //final int specModeH = MeasureSpec.getMode(heightMeasureSpec);
        //final int specSizeH = MeasureSpec.getSize(heightMeasureSpec);

        // 分别计算出控件的宽度和高度
        specSizeW = specSizeW > 0 ? specSizeW : DensityUtil.dip2px(getContext(),200);
        {
            // 走路进度条宽高
            progress_w = DensityUtil.dip2px(getContext(),5f);
            progress_h = DensityUtil.dip2px(getContext(),19f);
            // 走路进度背景宽高
            progress_bg_w = progress_w * (progress_line_count*2 + 1);
            // 如果默认的宽度超过了测量的宽度，则重新计算进度的背景宽度、进度条的宽度
            if (progress_bg_w > specSizeW) {
                progress_bg_w = specSizeW;
                // 修正走路进度的宽度
                progress_w = progress_bg_w / (progress_line_count*2 + 1);
            }
            progress_bg_h = progress_h + DensityUtil.dip2px(getContext(),8)*2;

            // 控件距离上一个控件的距离
            progress_bg_top = DensityUtil.dip2px(getContext(),3.5f);
            progress_top = DensityUtil.dip2px(getContext(),8);
            step_tip_text_top = DensityUtil.dip2px(getContext(),13.5f);

            // 填充绘制圆角进度背景的范围
            progress_bg_rect.left = 0;
            progress_bg_rect.right = progress_bg_w;
            progress_bg_rect.top = progress_h + progress_top;
            progress_bg_rect.bottom = progress_bg_rect.top + progress_bg_h;

            progress_bg_radius = DensityUtil.dip2px(getContext(), 5);
            progress_radius = DensityUtil.dip2px(getContext(), 3);
        }
        int p_h = bd_h + progress_bg_top + progress_bg_h + step_tip_text_top + textH * 2;

        // 设置一个高度
        setMeasuredDimension(specSizeW, p_h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas); // for -> android:background

        // 绘制小人
        canvas.drawBitmap(pepleImg, 0, 0, null);
        // 绘制进度背景
        canvas.drawRoundRect(progress_bg_rect, progress_bg_radius, progress_bg_radius, progressBg);
        int stat_index = 0;
        // 绘制进度
        getProgressLine.setColor(stepprogress_s_color);
        for (stat_index = 0; stat_index < current_line_index && stat_index < progress_line_count; ++stat_index) {
            RectF progressRect = new RectF();
            progressRect.left = progress_bg_rect.left + progress_w * (stat_index * 2 + 1);
            progressRect.right = progress_bg_rect.left + progress_w * (stat_index * 2 + 1) + progress_w;
            progressRect.top = progress_bg_rect.top + progress_h/2;
            progressRect.bottom = progress_bg_rect.top + progress_h/2 + progress_h;
            canvas.drawRoundRect(progressRect, progress_radius, progress_radius, getProgressLine);
        }
        // 换个颜色
        getProgressLine.setColor(stepprogress_color);
        // 剩下的全部绘制暗色非进度状态
        for (; stat_index < progress_line_count; ++stat_index) {
            RectF progressRect = new RectF();
            progressRect.left = progress_bg_rect.left + progress_w * (stat_index * 2 + 1);
            progressRect.right = progress_bg_rect.left + progress_w * (stat_index * 2 + 1) + progress_w;
            progressRect.top = progress_bg_rect.top + progress_h/2;
            progressRect.bottom = progress_bg_rect.top + progress_h/2 + progress_h;
            canvas.drawRoundRect(progressRect, progress_radius, progress_radius, getProgressLine);
        }

        // 绘制步数
        canvas.drawText(stepTipString,
                progress_bg_w - textPainter.measureText(stepTipString),
                progress_bg_rect.bottom + step_tip_text_top + textH,
                textPainter);
    }
}
