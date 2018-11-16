package com.mredrock.cyxbs.summer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * create by:Fxymine4ever
 * time: 2018/11/14
 */
public class MeetQuestionBean implements Parcelable {

    /**
     * data : {"question":{"question_answer1":"","question_answer2":"","question_answer3":"","question_name1":"柳小橙喜欢黄思杰吗？","question_name2":"柳小橙喜欢蓝思杰吗？","question_name3":"柳小橙喜欢绿思杰吗？","question_note":"no","userQusetion":{"id":0,"question_id":"21e7445169644794b37fafe1132e6735","question_note":"no","timestamp":1542212359355,"user_id":"5ba3566c9f5454006f2fc1ae"},"user_id":"5ba3566c9f5454006f2fc1ae"},"question_id":"da21d44603564e24ad7cb59f750a339a"}
     * info : success
     * status : 200
     */

    private DataBean data;
    private String info;
    private int status;

    protected MeetQuestionBean(Parcel in) {
        info = in.readString();
        status = in.readInt();
    }

    public static final Creator<MeetQuestionBean> CREATOR = new Creator<MeetQuestionBean>() {
        @Override
        public MeetQuestionBean createFromParcel(Parcel in) {
            return new MeetQuestionBean(in);
        }

        @Override
        public MeetQuestionBean[] newArray(int size) {
            return new MeetQuestionBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(info);
        dest.writeInt(status);

    }

    public static class DataBean  implements Parcelable {

        @Override
        public String toString() {
            return "DataBean{" +
                    "question=" + question +
                    ", question_id='" + question_id + '\'' +
                    '}';
        }

        /**
         * question : {"question_answer1":"","question_answer2":"","question_answer3":"","question_name1":"柳小橙喜欢黄思杰吗？","question_name2":"柳小橙喜欢蓝思杰吗？","question_name3":"柳小橙喜欢绿思杰吗？","question_note":"no","userQusetion":{"id":0,"question_id":"21e7445169644794b37fafe1132e6735","question_note":"no","timestamp":1542212359355,"user_id":"5ba3566c9f5454006f2fc1ae"},"user_id":"5ba3566c9f5454006f2fc1ae"}
         * question_id : da21d44603564e24ad7cb59f750a339a
         */

        private QuestionBean question;
        private String question_id;

        protected DataBean(Parcel in) {
            question_id = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(question_id);
        }

        public static class QuestionBean  implements Parcelable{

            @Override
            public String toString() {
                return "QuestionBean{" +
                        "question_answer1='" + question_answer1 + '\'' +
                        ", question_answer2='" + question_answer2 + '\'' +
                        ", question_answer3='" + question_answer3 + '\'' +
                        ", question_name1='" + question_name1 + '\'' +
                        ", question_name2='" + question_name2 + '\'' +
                        ", question_name3='" + question_name3 + '\'' +
                        ", question_note='" + question_note + '\'' +
                        ", userQusetion=" + userQusetion +
                        ", user_id='" + user_id + '\'' +
                        '}';
            }

            /**
             * question_answer1 :
             * question_answer2 :
             * question_answer3 :
             * question_name1 : 柳小橙喜欢黄思杰吗？
             * question_name2 : 柳小橙喜欢蓝思杰吗？
             * question_name3 : 柳小橙喜欢绿思杰吗？
             * question_note : no
             * userQusetion : {"id":0,"question_id":"21e7445169644794b37fafe1132e6735","question_note":"no","timestamp":1542212359355,"user_id":"5ba3566c9f5454006f2fc1ae"}
             * user_id : 5ba3566c9f5454006f2fc1ae
             */

            private String question_answer1;
            private String question_answer2;
            private String question_answer3;
            private String question_name1;
            private String question_name2;
            private String question_name3;
            private String question_note;
            private UserQusetionBean userQusetion;
            private String user_id;

            protected QuestionBean(Parcel in) {
                question_answer1 = in.readString();
                question_answer2 = in.readString();
                question_answer3 = in.readString();
                question_name1 = in.readString();
                question_name2 = in.readString();
                question_name3 = in.readString();
                question_note = in.readString();
                user_id = in.readString();
            }

            public static final Creator<QuestionBean> CREATOR = new Creator<QuestionBean>() {
                @Override
                public QuestionBean createFromParcel(Parcel in) {
                    return new QuestionBean(in);
                }

                @Override
                public QuestionBean[] newArray(int size) {
                    return new QuestionBean[size];
                }
            };

            public String getQuestion_answer1() {
                return question_answer1;
            }

            public void setQuestion_answer1(String question_answer1) {
                this.question_answer1 = question_answer1;
            }

            public String getQuestion_answer2() {
                return question_answer2;
            }

            public void setQuestion_answer2(String question_answer2) {
                this.question_answer2 = question_answer2;
            }

            public String getQuestion_answer3() {
                return question_answer3;
            }

            public void setQuestion_answer3(String question_answer3) {
                this.question_answer3 = question_answer3;
            }

            public String getQuestion_name1() {
                return question_name1;
            }

            public void setQuestion_name1(String question_name1) {
                this.question_name1 = question_name1;
            }

            public String getQuestion_name2() {
                return question_name2;
            }

            public void setQuestion_name2(String question_name2) {
                this.question_name2 = question_name2;
            }

            public String getQuestion_name3() {
                return question_name3;
            }

            public void setQuestion_name3(String question_name3) {
                this.question_name3 = question_name3;
            }

            public String getQuestion_note() {
                return question_note;
            }

            public void setQuestion_note(String question_note) {
                this.question_note = question_note;
            }

            public UserQusetionBean getUserQusetion() {
                return userQusetion;
            }

            public void setUserQusetion(UserQusetionBean userQusetion) {
                this.userQusetion = userQusetion;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(question_answer1);
                dest.writeString(question_answer2);
                dest.writeString(question_answer3);
                dest.writeString(question_name1);
                dest.writeString(question_name2);
                dest.writeString(question_name3);
                dest.writeString(question_note);
                dest.writeString(user_id);
            }

            public static class UserQusetionBean  implements Parcelable {

                @Override
                public String toString() {
                    return "UserQusetionBean{" +
                            "id=" + id +
                            ", question_id='" + question_id + '\'' +
                            ", question_note='" + question_note + '\'' +
                            ", timestamp=" + timestamp +
                            ", user_id='" + user_id + '\'' +
                            '}';
                }

                /**
                 * id : 0
                 * question_id : 21e7445169644794b37fafe1132e6735
                 * question_note : no
                 * timestamp : 1542212359355
                 * user_id : 5ba3566c9f5454006f2fc1ae
                 */

                private int id;
                private String question_id;
                private String question_note;
                private long timestamp;
                private String user_id;

                protected UserQusetionBean(Parcel in) {
                    id = in.readInt();
                    question_id = in.readString();
                    question_note = in.readString();
                    timestamp = in.readLong();
                    user_id = in.readString();
                }

                public static final Creator<UserQusetionBean> CREATOR = new Creator<UserQusetionBean>() {
                    @Override
                    public UserQusetionBean createFromParcel(Parcel in) {
                        return new UserQusetionBean(in);
                    }

                    @Override
                    public UserQusetionBean[] newArray(int size) {
                        return new UserQusetionBean[size];
                    }
                };

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getQuestion_id() {
                    return question_id;
                }

                public void setQuestion_id(String question_id) {
                    this.question_id = question_id;
                }

                public String getQuestion_note() {
                    return question_note;
                }

                public void setQuestion_note(String question_note) {
                    this.question_note = question_note;
                }

                public long getTimestamp() {
                    return timestamp;
                }

                public void setTimestamp(long timestamp) {
                    this.timestamp = timestamp;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(id);
                    dest.writeString(question_id);
                    dest.writeString(question_note);
                    dest.writeLong(timestamp);
                    dest.writeString(user_id);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "MeetQuestionBean{" +
                "data=" + data +
                ", info='" + info + '\'' +
                ", status=" + status +
                '}';
    }
}
