package com.mredrock.cyxbs.summer.bean;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.io.Serializable;

public class AskBean implements Serializable {


    /**
     * askName : 标题
     * voice : {"name":"sound1534845120.mp3","url":"http://lc-UYy61kgD.cn-n1.lcfile.com/iaHIGNpIWExwWP60SB8XI0e7NPNIFAXzOjagMRXL.mp3"}
     * author : {"__type":"Pointer","className":"_User","objectId":"5b6c16d017d0090035afd2e4"}
     * photo : {"name":"1534845133.jpg","url":"http://lc-UYy61kgD.cn-n1.lcfile.com/wXtluosBe9WERPlzIJMkLq45t6DXRxNjyuMAAWbb.jpg"}
     * askContent : 内容
     * objectId : 5b7be0d6fe88c20039f6d347
     * createdAt : 2018-08-21T09:52:22.047Z
     * updatedAt : 2018-08-21T09:52:22.047Z
     */

    private String askName;
    private AVFile voice;
    private AVUser author;
    private AVFile photo;
    private String askContent;
    private String objectId;
    private String createdAt;
    private String updatedAt;
    private AVObject askInfo;

    public AVObject getAskInfo() {
        return askInfo;
    }

    public void setAskInfo(AVObject askInfo) {
        this.askInfo = askInfo;
    }

    public String getAskName() {
        return askName;
    }

    public void setAskName(String askName) {
        this.askName = askName;
    }

    public AVFile getVoice() {
        return voice;
    }

    public void setVoice(AVFile voice) {
        this.voice = voice;
    }

    public AVUser getAuthor() {
        return author;
    }

    public void setAuthor(AVUser author) {
        this.author = author;
    }

    public AVFile getPhoto() {
        return photo;
    }

    public void setPhoto(AVFile photo) {
        this.photo = photo;
    }

    public String getAskContent() {
        return askContent;
    }

    public void setAskContent(String askContent) {
        this.askContent = askContent;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
