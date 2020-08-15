package com.hl.lib_miniui.view.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hl.lib_miniui.R;

import java.util.Timer;
import java.util.TimerTask;

public class TranslateImageViews extends View {
    private Bitmap bitmap1, bitmap2;
    private Rect rectB1;
    private RectF rect1;
    private int vW, vH, bitmap1W;
    private float translate_progress = 0;

    private Rect rectB2;
    private RectF rect2;
    private int bitmap2W;

    // 标记是否交叉参数
    private boolean startChange = false;

    private final Timer timer = new Timer();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressGo();
        }
    };
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    public TranslateImageViews(Context context) {
        super(context);
        init(context, null);
    }

    public TranslateImageViews(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TranslateImageViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TranslateImageViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 从自定义属性获取值
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Translate_ImageViews);
        //BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        //options.inJustDecodeBounds = true;
        bitmap1 = ((BitmapDrawable) ta.getDrawable(R.styleable.Translate_ImageViews_late1)).getBitmap();
        bitmap2 = ((BitmapDrawable) ta.getDrawable(R.styleable.Translate_ImageViews_late2)).getBitmap();
        ta.recycle();

        // 图片源范围
        rectB1 = new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
        bitmap1W = bitmap1.getWidth(); // bitmap1W = bitmap1.getWidth() / (bitmap1.getDensity() / 160);

        rectB2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        bitmap2W = bitmap2.getWidth(); // bitmap1W = bitmap1.getWidth() / (bitmap1.getDensity() / 160);
    }

    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //        int specSizeW = MeasureSpec.getSize(widthMeasureSpec);
    //        int specSizeH = MeasureSpec.getSize(heightMeasureSpec);
    //        setMeasuredDimension(specSizeW, specSizeH);
    //    }

    @Override
    protected void onDraw(Canvas canvas) {
        // setBackgroundColor(Color.TRANSPARENT);
        // rectB1 图片范围(left =0, right = bitmapW, top = 0, bottom 0 bitmapH 为当前图片宽高)
        // rect1 绘制目标范围(left = 0, right = viewW, top = 0, bottom = view 为当前控件宽高)
        // 交替位移参数
        if (startChange) {
            canvas.drawBitmap(bitmap1, rectB2, rect2, null);
            canvas.drawBitmap(bitmap2, rectB1, rect1, null);
        } else {
            canvas.drawBitmap(bitmap1, rectB1, rect1, null);
            canvas.drawBitmap(bitmap2, rectB2, rect2, null);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.rect1 = new RectF(0, 0, getWidth(), getHeight());
        this.rect2 = new RectF(getWidth(), 0, getWidth(), getHeight());
        this.vW = w;
        this.vH = h;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    /**
     * 重新调整绘制范围，实现位移效果
     */
    private void progressGo(){
        // 速度进度
        translate_progress += 0.5f;

        // 绘制超过了屏幕宽度，则恢复一下
        if (translate_progress >= vW) {
            // 一旦一遍完成，则需要交换参数，实现无缝衔接！
            startChange = !startChange;

            translate_progress = 0;
            rect1.right = vW;
            rectB1.left = 0;

            rect2.left = vW;
            rectB2.right = 0;
        }
        // 绘制目标范围
        rect1.right = vW - translate_progress;
        // 图片绘制源范围 - 进度按照比例来计算
        rectB1.left = (int) (bitmap1W / vW * translate_progress);//(bitmap1W - x) / bitmap1H = ((vW - translate_progress) / vH);

        // 第二张接着的图片目标范围
        rect2.left = vW - translate_progress;
        // 第二张接着的图片源范围
        rectB2.right = (int) (bitmap2W / vW * translate_progress);

        // 刷新绘制
        invalidate();

        // @方式2 平移绘制范围
        //        // 速度进度
        //        translate_progress += 0.05;
        //
        //        // 绘制超过了屏幕宽度，则恢复一下
        //        if (translate_progress >= vW) {
        //            translate_progress = 0;
        //            rect1.left = 0;
        //            rect1.right = vW;
        //        }
        //        // 绘制目标范围
        //        rect1.left -= translate_progress;
        //        rect1.right -= translate_progress;
        //        invalidate();
    }

    /**
     * 开始位移绘制动画
     */
    public void startAnimation(long period){
        if (null == timer) {
            return;
        }
        timer.schedule(task, 1000, period);
    }

    /**
     * 停止位移绘制动画
     */
    public void stopAnimation(){
        if (null == timer) {
            return;
        }
        timer.cancel();
    }
}
