package com.mredrock.cyxbs.summer.bean;

import com.avos.avoscloud.AVFile;

import java.io.Serializable;
import java.util.List;

public class AskBean implements Serializable {

    /**
     * askId : aidjwio141i24j12oijd
     * askName : BeJson
     * askAvatar : http://www.bejson.com
     * isFavorite : true
     * askContent : this is content
     * favNum : 4
     * updateTime : 2018-08-24
     * createTime : 2018-02-24
     * voice : https://baidu.com
     * photos : ["htppasdjioawd","valudndwaioe","dawjiodjaio"]
     */

    private String askId;
    private String askName;
    private String askAvatar;
    private boolean isFavorite;
    private String askContent;
    private int favNum;
    private String updateTime;
    private String createTime;
    private String authorName;
    private AVFile voice;
    private List<AVFile> photos;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAskId() {
        return askId;
    }

    public void setAskId(String askId) {
        this.askId = askId;
    }

    public String getAskName() {
        return askName;
    }

    public void setAskName(String askName) {
        this.askName = askName;
    }

    public String getAskAvatar() {
        return askAvatar;
    }

    public void setAskAvatar(String askAvatar) {
        this.askAvatar = askAvatar;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getAskContent() {
        return askContent;
    }

    public void setAskContent(String askContent) {
        this.askContent = askContent;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public AVFile getVoice() {
        return voice;
    }

    public void setVoice(AVFile voice) {
        this.voice = voice;
    }

    public List<AVFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<AVFile> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "AskBean{" +
                "askId='" + askId + '\'' +
                ", askName='" + askName + '\'' +
                ", askAvatar='" + askAvatar + '\'' +
                ", isFavorite=" + isFavorite +
                ", askContent='" + askContent + '\'' +
                ", favNum=" + favNum +
                ", updateTime='" + updateTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", authorName='" + authorName + '\'' +
                ", voice=" + voice +
                ", photos=" + photos +
                '}';
    }
}
