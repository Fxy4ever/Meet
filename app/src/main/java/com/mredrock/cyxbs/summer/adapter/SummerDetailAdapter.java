package com.mredrock.cyxbs.summer.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.AskBean;
import com.mredrock.cyxbs.summer.bean.CommentBean;
import com.mredrock.cyxbs.summer.utils.AudioPlayer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SummerDetailAdapter extends MultiLayoutBaseAdapter {
    private int NORMAL = 0;
    private List<CommentBean> list;

    public SummerDetailAdapter(Context context, List<CommentBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.list = data;
    }

    @Override
    public int getItemType(int i) {
        return NORMAL;
    }

    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                CircleImageView avatar = baseHolder.getView(R.id.summer_comment_avatar);
                TextView name = baseHolder.getView(R.id.summer_comment_name);
                TextView content = baseHolder.getView(R.id.summer_comment_content);
                ImageButton play = baseHolder.getView(R.id.summer_comment_play);
                TextView playTime = baseHolder.getView(R.id.summer_comment_playTime);
                TextView time = baseHolder.getView(R.id.summer_comment_time);

                time.setText(list.get(i).getTime());

                AVUser user = list.get(i).getUser();
                if(user.getAVFile("avatar")!=null){
                    Glide.with(getContext()).load(user.getAVFile("avatar").getUrl()).into(avatar);
                }
                name.setText(user.getUsername());
                if(list.get(i).getContent()!=null&&!list.get(i).getContent().equals("")){
                    content.setText(list.get(i).getContent());
                }

                if(list.get(i).getVoice()!=null){
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

                    play.setOnClickListener(v -> audioPlayer.Play(getContext(), list.get(i).getVoice().getUrl()));
                }else{
                    play.setVisibility(View.GONE);
                    playTime.setVisibility(View.GONE);
                }

                break;
        }
    }

    private void onPlayerStatusChanged(AudioPlayer lapt, int status, @Nullable Object msg, ImageButton button){
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


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
