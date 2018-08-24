package com.mredrock.cyxbs.summer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.mredrock.cyxbs.summer.R;
import com.mredrock.cyxbs.summer.bean.ChatBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends MultiLayoutBaseAdapter {
    private List<ChatBean> beans;
    private String TAG = "chat";
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
        Log.d(TAG, "getItemType: "+beans.get(i).getKind());
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

                break;
            case 2://我的音频

                break;
            case 3://你的文字
                TextView yourTextContent = baseHolder.getView(R.id.summer_chat_left_str_content);
                CircleImageView yourTextAvatar = baseHolder.getView(R.id.summer_chat_left_str_avatar);
                yourTextContent.setText(((AVIMTextMessage)beans.get(i).getMessage()).getText());
                Glide.with(getContext()).load(yourAvatar).apply(new RequestOptions().override(100,100).error(R.drawable.summer_user_avatar)).into(yourTextAvatar);
                break;
            case 4://你的图片

                break;
            case 5://你的音频

                break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    public void setYourAvatar(String yourAvatar) {
        Log.d(TAG, "setYourAvatar: "+yourAvatar);
        this.yourAvatar = yourAvatar;
    }

    public void setMyAvatar(String myAvatar) {
        Log.d(TAG, "setMyAvatar: "+myAvatar);
        this.myAvatar = myAvatar;
    }
}
