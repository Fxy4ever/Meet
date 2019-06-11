package com.mredrock.cyxbs.summer.bean;

/**
 * create by:Fxymine4ever
 * time: 2019/6/5
 */
public class NetBean {

    /**
     * massage : success!
     * code : 200
     * data : {"userId":18,"userName":"fxy1234123","phone":"11111111111","password":"123456","age":20}
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
         * userId : 18
         * userName : fxy1234123
         * phone : 11111111111
         * password : 123456
         * age : 20
         */

        private int userId;
        private String userName;
        private String phone;
        private String password;
        private int age;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", password='" + password + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NetBean{" +
                "massage='" + massage + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", total=" + total +
                '}';
    }
}
