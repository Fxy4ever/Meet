package com.mredrock.cyxbs.summer.bean;

public class userBean {

    /**
     * email : 381391264@qq.com
     * username : fxy
     * emailVerified : false
     * avatar : {"name":"summer_bg1.jpeg","url":"http://lc-UYy61kgD.cn-n1.lcfile.com/8a63b1333c054910f988.jpeg"}
     * mobilePhoneVerified : false
     * objectId : 5b6c16d017d0090035afd2e4
     * createdAt : 2018-08-09T10:26:24.408Z
     * updatedAt : 2018-08-20T13:19:19.273Z
     * desc : I'm a freshman
     */

    private String email;
    private String username;
    private boolean emailVerified;
    private AvatarBean avatar;
    private boolean mobilePhoneVerified;
    private String objectId;
    private String createdAt;
    private String updatedAt;
    private String desc;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class AvatarBean {
        /**
         * name : summer_bg1.jpeg
         * url : http://lc-UYy61kgD.cn-n1.lcfile.com/8a63b1333c054910f988.jpeg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
