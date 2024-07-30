package com.lfc.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Message;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.lfc.framework.utils.LogUtils;

import java.io.IOException;

/**
 * 媒体播放，封装了MediaPlayer类
 */

public class MediaPlayerManager {

    //播放
    public static final int MEDIA_STATUS_PLAY = 0;
    //暂停
    public static final int MEDIA_STATUS_PAUSE = 1;
    //停止
    public static final int MEDIA_STATUS_STOP = 2;

    //当前状态
    public static int MEDIA_STATUS = MEDIA_STATUS_PAUSE;

    private MediaPlayer mMediaPlayer;

    private static final int H_PROGRESS = 1000;

    private OnMusicProgressListener musicProgressListener;

    /**
     * 计算歌曲的进度：
     * 1.开始播放的时候就开始循环计算时长
     * 2.将进度计算结果对外抛出
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case H_PROGRESS:
                    if (musicProgressListener != null) {
                        //拿到当前时长
                        int currentPosition = getCurrentPosition();
                        int pos = (int) (((float) currentPosition / (float) getDuration()) * 100);
                        musicProgressListener.OnProgress(currentPosition, pos);
                        mHandler.sendEmptyMessageDelayed(H_PROGRESS, 1000);
                    }
                    break;
            }
            return false;
        }
    });

    public MediaPlayerManager() {
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 是否在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    /**
     * 开始播放
     *
     * @param path
     */
    public void startPlay(AssetFileDescriptor path) {
        try {
            //如果播放第二首歌曲或者第三首歌曲，之前的状态都要重置
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();  //装载
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    public void startPlay(String path) {
        try {
            //如果播放第二首歌曲或者第三首歌曲，之前的状态都要重置
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();  //装载
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {
        if (isPlaying()) {
            mMediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            mHandler.removeMessages(H_PROGRESS);
        }
    }


    /**
     * 继续播放
     */
    public void continuePlay() {
        mMediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        mMediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);
    }


    /**
     * 获取当前位置
     *
     * @return
     */
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取总时长
     *
     * @return
     */
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }


    /**
     * 是否循环
     *
     * @param isLooping
     */
    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    /**
     * 播放结束
     *
     * @param listener
     */
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(listener);
    }

    /**
     * 跳转位置
     *
     * @param ms
     */
    public void seekTo(int ms) {
        mMediaPlayer.seekTo(ms);
    }

    /**
     * 播放错误
     *
     * @param listener
     */
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(listener);
    }

    /**
     * 播放进度
     *
     * @param listener
     */
    public void setOnProgressListener(OnMusicProgressListener listener) {
        musicProgressListener = listener;
    }

    public interface OnMusicProgressListener {
        void OnProgress(int progress, int pos);
    }
}
