package com.mredrock.cyxbs.summer.utils;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mredrock.cyxbs.summer.event.TextEvent;

import org.greenrobot.eventbus.EventBus;

public class MyMessageHandler  extends AVIMMessageHandler{
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
        if(message instanceof AVIMTextMessage){
            TextEvent event = new TextEvent();
            event.setMessage((AVIMTextMessage) message);
            EventBus.getDefault().post(event);
            Log.d("chat",message.getContent()+"自定义");
        }
        // TODO: 2018/8/24 音频 图片的msg
    }

    public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

    }
}
