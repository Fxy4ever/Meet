package com.mredrock.cyxbs.summer.bean;

/**
 * create by:Fxymine4ever
 * time: 2018/11/13
 */
public class TokenBean {

    /**
     * data : {"token":"747e270e8d6b4a6183b359792eab6988"}
     * info : success
     * status : 200
     */

    private DataBean data;
    private String info;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * token : 747e270e8d6b4a6183b359792eab6988
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
