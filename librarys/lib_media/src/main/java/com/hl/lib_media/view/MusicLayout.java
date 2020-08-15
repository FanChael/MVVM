package com.hl.lib_media.view;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hl.lib_media.R;
import com.hl.lib_miniui.view.toggle.SToggleButton;

import java.util.Map;

/**
 * 音乐播放器组合控件
 *
 * @Author: hl
 * @Date: created at 2020/5/21 17:15
 * @Description: com.hl.lib_media.view
 */
public class MusicLayout extends ConstraintLayout {
    // 播放控制按钮
    private SToggleButton mpl_musicPlayPause;
    private SToggleButton mpl_musicVolumToggle;
    private AppCompatImageView mpl_musicChangePause;
    private AppCompatTextView mpl_musicName;

    // ExoPlayer相关
    private SimpleExoPlayer simpleExoPlayer;

    public MusicLayout(Context context) {
        super(context);
        init(context, null);
    }

    public MusicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MusicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // 初始化MediaPlayer
    private void init(Context context, AttributeSet attrs) {
        LinearLayout inflate = (LinearLayout) inflate(context, R.layout.music_player_layout, null);
        addView(inflate);

        mpl_musicPlayPause = inflate.findViewById(R.id.mpl_musicPlayPause);
        mpl_musicVolumToggle = inflate.findViewById(R.id.mpl_musicVolumToggle);
        mpl_musicChangePause = inflate.findViewById(R.id.mpl_musicChangePause);
        mpl_musicName = inflate.findViewById(R.id.mpl_musicName);

        if (null == simpleExoPlayer) {
            // 创建一个播放器
            simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        }

        // 播放暂停
        mpl_musicPlayPause.registerOnCheckedState(new SToggleButton.OnCheckedStateListenner() {
            @Override
            public void onStateCheckd(boolean bChecked) {
                playOrPause(bChecked);
            }
        });
        // 声音开关
        mpl_musicVolumToggle.registerOnCheckedState(new SToggleButton.OnCheckedStateListenner() {
            @Override
            public void onStateCheckd(boolean bChecked) {
                if (null != simpleExoPlayer) {
                    simpleExoPlayer.setVolume(bChecked ? 1 : 0);
                }
            }
        });
        // 下一首
        mpl_musicChangePause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != simpleExoPlayer) {
                    simpleExoPlayer.next();
                }
            }
        });
    }

    /**
     * 初始化播放源列表
     *
     * @param context
     */
    public void initMusicSource(Context context, Map<String, String> musicList) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "audio/mpeg"));
        // This is the MediaSource representing the media to be played.
        ConcatenatingMediaSource concateMediaSource = new ConcatenatingMediaSource();

        String firstName = null;
        // 构造播放列表
        for (Map.Entry<String, String> mUrl : musicList.entrySet()) {
            if (TextUtils.isEmpty(firstName)) {
                firstName = mUrl.getKey();
            }
            MediaSource mediaSource = new ProgressiveMediaSource
                    .Factory(dataSourceFactory)
                    .setTag(mUrl.getKey())
                    .createMediaSource(Uri.parse(mUrl.getValue()));
            concateMediaSource.addMediaSource(mediaSource);
        }

        // 可以添加到循环播放列表中循环播放
        MediaSource loopingMediaSource = new LoopingMediaSource(concateMediaSource);

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
                        break;
                    case Player.STATE_ENDED: // 播放完成
                        break;
                }
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                // Load metadata for mediaId and update the UI.
                mpl_musicName.setText(simpleExoPlayer.getCurrentTag().toString());
            }
        });

        // 准备播放
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.prepare(loopingMediaSource);
        mpl_musicName.setText(firstName);
    }

    /**
     * 播放或者暂停
     *
     * @param bPlay
     */
    public void playOrPause(boolean bPlay) {
        if (null != simpleExoPlayer) {
            simpleExoPlayer.setPlayWhenReady(bPlay);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != simpleExoPlayer) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }
}
