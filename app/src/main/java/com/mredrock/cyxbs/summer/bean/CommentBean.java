package com.mredrock.cyxbs.summer.bean;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

public class CommentBean {

    /**
     * content : content
     * user : asdasd
     * askInfo : asdas
     * voice : asd
     */

    private String content;
    private AVUser user;
    private AVObject askInfo;
    private AVFile voice;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public AVObject getAskInfo() {
        return askInfo;
    }

    public void setAskInfo(AVObject askInfo) {
        this.askInfo = askInfo;
    }

    public AVFile getVoice() {
        return voice;
    }

    public void setVoice(AVFile voice) {
        this.voice = voice;
    }
}
