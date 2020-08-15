package com.hl.lib_media.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/** 视频播放控件
*@Author: hl
*@Date: created at 2020/5/26 16:24
*@Description: com.hl.lib_media.view
*/
public class VideoLayout extends ViewGroup {
    // ExoPlayer相关
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private OnplayState onplayState;

    public VideoLayout(Context context) {
        super(context);
        init(context, null);
    }

    public VideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VideoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public VideoLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化播放器等信息
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (null == simpleExoPlayer) {
            // 创建一个播放器
            simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        }
        if (null == playerView) {
            playerView = new PlayerView(getContext());
            playerView.setUseController(false);
            addView(playerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /***子控件也测量一下***/
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 然后根据ViewGroup容易模式进行对应的宽高设置
        //int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        //int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View child = getChildAt(i);
            child.layout(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * 初始化视频资源
     * @param videoUrl
     */
    public void initMediaSource(Context context, String videoUrl){
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "video/mpeg"));
        MediaSource mediaSource = new ProgressiveMediaSource
                .Factory(dataSourceFactory)
                //.setTag(mUrl.getKey())
                .createMediaSource(Uri.parse(videoUrl));

        // 播放监听
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_IDLE: // nothing to play
                        break;
                    case Player.STATE_BUFFERING: //缓冲
                        break;
                    case Player.STATE_READY: // 播放状态
                        if (null != onplayState) {
                            if (playWhenReady) {
                                onplayState.onPlay();
                            } else {
                                onplayState.onPause();
                            }
                        }
                        break;
                    case Player.STATE_ENDED: // 播放完成
                        if (null != onplayState) {
                            onplayState.onComplete();
                        }
                        break;
                }
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                // Load metadata for mediaId and update the UI.
                //mpl_musicName.setText(simpleExoPlayer.getCurrentTag().toString());
            }
        });

        // 准备播放
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.prepare(mediaSource);
        //mpl_musicName.setText(firstName);
        playerView.setPlayer(simpleExoPlayer);
    }

    /**
     * 声音开关
     *
     * @param bChecked
     */
    public void audioOnOff(boolean bChecked) {
        if (null != simpleExoPlayer) {
            simpleExoPlayer.setVolume(bChecked ? 1 : 0);
        }
    }

    /**
     * 播放或者暂停
     *
     * @param bPlay
     */
    public void playOrPause(boolean bPlay) {
        if (null != simpleExoPlayer) {
            if (bPlay && simpleExoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                simpleExoPlayer.seekTo(0);
            }
            simpleExoPlayer.setPlayWhenReady(bPlay);
        }
    }

    /**
     * 播放状态监听 - 如果要确保能监听到开始播放，建议放到initMediaSource方法之前
     * @param onplayState
     */
    public void addListenner(OnplayState onplayState){
        this.onplayState = onplayState;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != simpleExoPlayer) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    /**
     * 播放开始和结束回调
     */
    public interface OnplayState{
        void onPlay();
        void onPause();
        void onComplete();
    }
}
