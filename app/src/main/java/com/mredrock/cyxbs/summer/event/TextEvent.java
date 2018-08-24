package com.mredrock.cyxbs.summer.event;

import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

public class TextEvent {
    public AVIMTextMessage message;

    public TextEvent() {
    }

    public AVIMTextMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMTextMessage message) {
        this.message = message;
    }
}
