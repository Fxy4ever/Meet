package com.mredrock.cyxbs.summer.utils;

import android.util.Log;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.mredrock.cyxbs.summer.event.AudioEvent;
import com.mredrock.cyxbs.summer.event.ImageEvent;
import com.mredrock.cyxbs.summer.event.TextEvent;

import org.greenrobot.eventbus.EventBus;

public class MyMessageHandler  extends AVIMMessageHandler{
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
        if(message instanceof AVIMTextMessage){
            TextEvent event = new TextEvent();
            event.setMessage((AVIMTextMessage) message);
            EventBus.getDefault().post(event);
        }else if(message instanceof AVIMImageMessage){
            ImageEvent event = new ImageEvent();
            event.setMessage((AVIMImageMessage) message);
            EventBus.getDefault().post(event);
        }else{
            AudioEvent event = new AudioEvent();
            event.setMessage((AVIMAudioMessage) message);
            EventBus.getDefault().post(event);
        }
    }

    public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

    }
}
