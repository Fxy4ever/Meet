package com.mredrock.cyxbs.summer.bean;

import com.avos.avoscloud.im.v2.AVIMMessage;

public class ChatBean {
    private AVIMMessage message;
    private String kind;

    public AVIMMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMMessage message) {
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
