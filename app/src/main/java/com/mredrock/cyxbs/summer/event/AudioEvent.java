package com.mredrock.cyxbs.summer.event;

import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;

public class AudioEvent {
    private AVIMAudioMessage message;

    public AVIMAudioMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMAudioMessage message) {
        this.message = message;
    }
}
