package com.mredrock.cyxbs.summer.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.ChatBean;
import com.mredrock.cyxbs.summer.utils.AudioUtil;
import com.mredrock.cyxbs.summer.utils.DialogBuilder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends MultiLayoutBaseAdapter {
    private List<ChatBean> beans;

    private int TEXT_MINE = 0;
    private int PHOTO_MINE = 1;
    private int AUDIO_MINE = 2;
    private int TEXT_YOU = 3;
    private int PHOTO_YOU = 4;
    private int AUDIO_YOU = 5;

    private String yourAvatar;
    private String myAvatar;



    public ChatListAdapter(Context context, List<ChatBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.beans = data;
    }

    @Override
    public int getItemType(int i) {
        switch (beans.get(i).getKind()) {
            case "我的文字":
                return TEXT_MINE;
            case "我的图片":
                return PHOTO_MINE;
            case "我的语音":
                return AUDIO_MINE;
            case "你的文字":
                return TEXT_YOU;
            case "你的图片":
                return PHOTO_YOU;
            default://你的语音
                return AUDIO_YOU;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0://我的文字
                TextView myTextContent = baseHolder.getView(R.id.summer_chat_right_str_content);
                CircleImageView myTextAvatar = baseHolder.getView(R.id.summer_chat_right_str_avatar);
                myTextContent.setText(((AVIMTextMessage)beans.get(i).getMessage()).getText());
                Glide.with(getContext()).load(myAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(myTextAvatar);
                break;
            case 1://我的图片
                CircleImageView myImgAvatar = baseHolder.getView(R.id.summer_chat_right_photo_avatar);
                ImageView myImg = baseHolder.getView(R.id.summer_chat_right_photo_img);
                Glide.with(getContext()).load(myAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(myImgAvatar);
                Glide.with(getContext()).load(((AVIMImageMessage)beans.get(i).getMessage()).getFileUrl()).apply(new RequestOptions().override(100,100).error(R.drawable.ic_launcher_background)).into(myImg);
                myImg.setOnClickListener(v->{
                    Dialog dialog = DialogBuilder.buildImgDialog(getContext(),((AVIMImageMessage)beans.get(i).getMessage()).getFileUrl());
                    dialog.show();
                });
                break;
            case 2://我的音频
                CircleImageView myAudioAvatar = baseHolder.getView(R.id.summer_chat_right_voice_avatar);
                LinearLayout myAudioPlayer = baseHolder.getView(R.id.summer_chat_right_voice_parent);
                TextView myAudioTime = baseHolder.getView(R.id.summer_chat_right_voice_time);
                myAudioTime.setText(((AVIMAudioMessage)beans.get(i).getMessage()).getDuration()+"s");
                Glide.with(getContext()).load(myAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(myAudioAvatar);
                myAudioPlayer.setOnClickListener(v->{
                    AudioUtil.setAudioInChat(getContext(),((AVIMAudioMessage)beans.get(i).getMessage()).getFileUrl(),myAudioPlayer);
                });
                break;
            case 3://你的文字
                TextView yourTextContent = baseHolder.getView(R.id.summer_chat_left_str_content);
                CircleImageView yourTextAvatar = baseHolder.getView(R.id.summer_chat_left_str_avatar);
                yourTextContent.setText(((AVIMTextMessage)beans.get(i).getMessage()).getText());
                Glide.with(getContext()).load(yourAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(yourTextAvatar);
                break;
            case 4://你的图片
                CircleImageView yourImgAvatar = baseHolder.getView(R.id.summer_chat_left_photo_avatar);
                ImageView yourImg = baseHolder.getView(R.id.summer_chat_left_photo_img);
                Glide.with(getContext()).load(yourAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(yourImgAvatar);
                Glide.with(getContext()).load(((AVIMImageMessage)beans.get(i).getMessage()).getFileUrl()).apply(new RequestOptions().override(100,100).error(R.drawable.ic_launcher_background)).into(yourImg);
                yourImg.setOnClickListener(v->{
                    Dialog dialog = DialogBuilder.buildImgDialog(getContext(),((AVIMImageMessage)beans.get(i).getMessage()).getFileUrl());
                    dialog.show();
                });
                break;
            case 5://你的音频
                CircleImageView yourAudioAvatar = baseHolder.getView(R.id.summer_chat_left_voice_avatar);
                LinearLayout yourAudioPlayer = baseHolder.getView(R.id.summer_chat_left_voice_parent);
                TextView yourAudioTime = baseHolder.getView(R.id.summer_chat_left_voice_time);
                yourAudioTime.setText(((AVIMAudioMessage)beans.get(i).getMessage()).getDuration()+"s");
                Glide.with(getContext()).load(yourAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(yourAudioAvatar);
                yourAudioPlayer.setOnClickListener(v->{
                    AudioUtil.setAudioInChat(getContext(),((AVIMAudioMessage)beans.get(i).getMessage()).getFileUrl(),yourAudioPlayer);
                });
                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    public void setYourAvatar(String yourAvatar) {
        this.yourAvatar = yourAvatar;
    }

    public void setMyAvatar(String myAvatar) {
        this.myAvatar = myAvatar;
    }

    public void setChatData(List<ChatBean> list){
        beans.clear();
        beans.addAll(list);
        notifyDataSetChanged();
    }
}