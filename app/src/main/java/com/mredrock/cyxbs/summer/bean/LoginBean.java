package com.mredrock.cyxbs.summer.bean;

/**
 * create by:Fxymine4ever
 * time: 2019/6/5
 */
public class LoginBean {

    /**
     * massage : 登陆成功
     * code : 200
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.N7SVh5VwF2czL9PhlspVOfEUgrJ2xChwJVZKrRS6Woo"}
     * total : 0
     */

    private String massage;
    private int code;
    private DataBean data;
    private int total;

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.N7SVh5VwF2czL9PhlspVOfEUgrJ2xChwJVZKrRS6Woo
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "massage='" + massage + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
