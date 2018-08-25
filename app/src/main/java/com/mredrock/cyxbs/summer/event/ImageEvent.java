package com.mredrock.cyxbs.summer.event;

import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;

public class ImageEvent {
    private AVIMImageMessage message;

    public AVIMImageMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMImageMessage message) {
        this.message = message;
    }
}
