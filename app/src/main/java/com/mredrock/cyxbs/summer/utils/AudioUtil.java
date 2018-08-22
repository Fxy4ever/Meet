package com.mredrock.cyxbs.summer.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;
import com.mredrock.cyxbs.summer.R;

public class AudioUtil {

    /**
     * 声音按钮和秒数肯定是一起的，做了个固定的封装，未考虑耦合
     * @param context
     * @param voice
     * @param playTime
     * @param play
     */
    public static void setAudio(Context context, AVFile voice, TextView playTime, ImageButton play){
        if(voice!=null){
            AudioPlayer audioPlayer = new AudioPlayer();
            audioPlayer.setStatusChangedListener(AudioPlayer.Status.STATUS_READY, (lapt, status, msg) -> {
                playTime.setText(audioPlayer.getmPlayer().getDuration() / 1000 + "s");
                onPlayerStatusChanged(lapt, status, msg, play);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_COMPLETE, (lapt, status, msg) -> {
                playTime.setText("");
                onPlayerStatusChanged(lapt, status, msg, play);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_ERROR, (lapt, status, msg) -> {
                playTime.setText("");
                onPlayerStatusChanged(lapt, status, msg, play);
            });

            play.setOnClickListener(v -> audioPlayer.Play(context, voice.getUrl()));
        }else{
            play.setVisibility(View.GONE);
            playTime.setVisibility(View.GONE);
        }
    }

    private static void onPlayerStatusChanged(AudioPlayer lapt, int status, @Nullable Object msg, ImageButton button){
        switch (status){
            case AudioPlayer.Status.STATUS_READY:
                button.setImageResource(R.drawable.summer_icon_play_light);
                break;
            case AudioPlayer.Status.STATUS_COMPLETE:
                button.setImageResource(R.drawable.summer_icon_play);
                break;
            case AudioPlayer.Status.STATUS_ERROR:
                button.setImageResource(R.drawable.summer_icon_play);
                break;
        }
    }

}
