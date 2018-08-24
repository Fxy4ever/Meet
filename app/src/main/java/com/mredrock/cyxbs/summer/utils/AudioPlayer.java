package com.mredrock.cyxbs.summer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_COMPLETE;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_ERROR;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_NULL;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_PAUSE;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_PLAYING;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_READY;
import static com.mredrock.cyxbs.summer.utils.AudioPlayer.Status.STATUS_STOP;

public class AudioPlayer {
    private String TAG = "audio";

    private MediaPlayer mPlayer = new MediaPlayer();
    private AudioPlayer audioPlayer;

    private float volume = 0.5f;
    private int currrentStatus = STATUS_NULL;

    public MediaPlayer getmPlayer() {
        return mPlayer;
    }

    @SuppressLint("UseSparseArrays")
    private Map<Integer, OnStatusChangedListener> listenerMap = new HashMap<>();

    public AudioPlayer() {
        audioPlayer = this;
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(mp -> {
            setCurrentStatus(STATUS_READY, mp.getDuration());
            mp.setVolume(volume, volume);
            mp.start();
            setCurrentStatus(STATUS_PLAYING, null);
        });

        mPlayer.setOnErrorListener((mp, what, extra) -> {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                setCurrentStatus(STATUS_STOP, null);
            }
            setCurrentStatus(STATUS_ERROR, null);
            return false;
        });

        mPlayer.setOnCompletionListener(mp -> {
            setCurrentStatus(STATUS_COMPLETE, null);
        });
    }


    public void Play(Context context, String sFile) {
        Uri uri = Uri.parse(sFile);
        try {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                setCurrentStatus(STATUS_STOP, null);
            }
            mPlayer.reset();
            mPlayer.setDataSource(context,uri);
            mPlayer.prepareAsync();

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    public void setVolume(@FloatRange(from = 0.0f, to = 1.0f) float fVal) {
        volume = fVal;
    }


    public void Pause() {
        if (mPlayer.isPlaying()) {
            setCurrentStatus(STATUS_PAUSE, null);
            mPlayer.pause();
        }
    }



    public void Resume() {
        if (currrentStatus == STATUS_PAUSE) {
            setCurrentStatus(STATUS_PLAYING, null);
            mPlayer.start();
        }
    }


    private void setCurrentStatus(int nVal, Object obj) {
        currrentStatus = nVal;
        if (listenerMap.containsKey(nVal)) {
            OnStatusChangedListener listener = listenerMap.get(nVal);
            if (listener != null) {
                listener.onStatusChanged(audioPlayer, nVal, obj);
            }
        }
    }


    public interface Status {
        public int STATUS_NULL = 0;
        public int STATUS_READY = 1;
        public int STATUS_PLAYING = 2;
        public int STATUS_PAUSE = 3;
        public int STATUS_COMPLETE = 4;
        public int STATUS_STOP = 5;
        public int STATUS_ERROR = 9;
    }

    public interface OnStatusChangedListener {
        public void onStatusChanged(AudioPlayer lapt, int status, @Nullable Object other);
    }

    public AudioPlayer setStatusChangedListener(@NonNull int nStatus, OnStatusChangedListener listener) {
        listenerMap.put(nStatus, listener);
        return this;
    }

    protected void finalize() {
        if (mPlayer.isPlaying())
            mPlayer.stop();

        mPlayer.release();
        mPlayer = null;
    }
}
