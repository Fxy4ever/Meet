package com.mredrock.cyxbs.summer.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

    public static void setAudioInChat(Context context, String url,LinearLayout play){
        if(url.length()>0){
            AudioPlayer audioPlayer = new AudioPlayer();
            audioPlayer.setStatusChangedListener(AudioPlayer.Status.STATUS_READY, (lapt, status, msg) -> {
                onPlayerInChatStatusChanged(lapt,context, status, msg, play);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_COMPLETE, (lapt, status, msg) -> {
                onPlayerInChatStatusChanged(lapt,context, status, msg, play);

            }).setStatusChangedListener(AudioPlayer.Status.STATUS_ERROR, (lapt, status, msg) -> {
                onPlayerInChatStatusChanged(lapt, context,status, msg, play);
            });
            play.setOnClickListener(v -> audioPlayer.Play(context, url));
        }
    }

    private static void onPlayerStatusChanged(AudioPlayer lapt, int status, @Nullable Object msg, ImageButton button){
        switch (status){
            case AudioPlayer.Status.STATUS_READY:
                button.setBackgroundResource(R.drawable.summer_icon_play_light);
                break;
            case AudioPlayer.Status.STATUS_COMPLETE:
                button.setBackgroundResource(R.drawable.summer_icon_play);
                break;
            case AudioPlayer.Status.STATUS_ERROR:
                button.setBackgroundResource(R.drawable.summer_icon_play);
                break;
        }
    }

    private static void onPlayerInChatStatusChanged(AudioPlayer lapt,Context context, int status, @Nullable Object msg, LinearLayout parent){
        switch (status){
            case AudioPlayer.Status.STATUS_READY:
                Log.d("player", "音频播放开始");
                break;
            case AudioPlayer.Status.STATUS_COMPLETE:
                Log.d("player", "音频播放完成 ");
                break;
            case AudioPlayer.Status.STATUS_ERROR:
                Log.d("player", "音频播放失败");
                break;
        }
    }

}
