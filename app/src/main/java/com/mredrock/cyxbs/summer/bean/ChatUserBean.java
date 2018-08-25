package com.mredrock.cyxbs.summer.bean;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;

public class ChatUserBean {
    private AVUser avUser;
    private String conversationId;
    private AVObject conversation;

    public AVObject getConversation() {
        return conversation;
    }

    public void setConversation(AVObject conversation) {
        this.conversation = conversation;
    }

    public AVUser getAvUser() {
        return avUser;
    }

    public void setAvUser(AVUser avUser) {
        this.avUser = avUser;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
