package com.mredrock.cyxbs.summer.bean;

/**
 * create by:Fxymine4ever
 * time: 2018/11/14
 */
public class MeetBackBean {

    /**
     * data : {"question_map":{"question2_preset":"不喜欢","question1_title":"柳小橙喜欢黄思杰吗？","question1_preset":"喜欢","question3_preset":"喜欢","question1_answer":"多花时间","question3_title":"柳小橙喜欢绿思杰吗？","question2_answer":"啊哈哈","question3_answer":"啊哈哈哈","question2_title":"柳小橙喜欢蓝思杰吗？"},"score_map":{"question3":"0.510603","question2":"0.4519","question1":"0.546791"}}
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
         * question_map : {"question2_preset":"不喜欢","question1_title":"柳小橙喜欢黄思杰吗？","question1_preset":"喜欢","question3_preset":"喜欢","question1_answer":"多花时间","question3_title":"柳小橙喜欢绿思杰吗？","question2_answer":"啊哈哈","question3_answer":"啊哈哈哈","question2_title":"柳小橙喜欢蓝思杰吗？"}
         * score_map : {"question3":"0.510603","question2":"0.4519","question1":"0.546791"}
         */

        private QuestionMapBean question_map;
        private ScoreMapBean score_map;

        public QuestionMapBean getQuestion_map() {
            return question_map;
        }

        public void setQuestion_map(QuestionMapBean question_map) {
            this.question_map = question_map;
        }

        public ScoreMapBean getScore_map() {
            return score_map;
        }

        public void setScore_map(ScoreMapBean score_map) {
            this.score_map = score_map;
        }

        public static class QuestionMapBean {
            /**
             * question2_preset : 不喜欢
             * question1_title : 柳小橙喜欢黄思杰吗？
             * question1_preset : 喜欢
             * question3_preset : 喜欢
             * question1_answer : 多花时间
             * question3_title : 柳小橙喜欢绿思杰吗？
             * question2_answer : 啊哈哈
             * question3_answer : 啊哈哈哈
             * question2_title : 柳小橙喜欢蓝思杰吗？
             */

            private String question2_preset;
            private String question1_title;
            private String question1_preset;
            private String question3_preset;
            private String question1_answer;
            private String question3_title;
            private String question2_answer;
            private String question3_answer;
            private String question2_title;

            public String getQuestion2_preset() {
                return question2_preset;
            }

            public void setQuestion2_preset(String question2_preset) {
                this.question2_preset = question2_preset;
            }

            public String getQuestion1_title() {
                return question1_title;
            }

            public void setQuestion1_title(String question1_title) {
                this.question1_title = question1_title;
            }

            public String getQuestion1_preset() {
                return question1_preset;
            }

            public void setQuestion1_preset(String question1_preset) {
                this.question1_preset = question1_preset;
            }

            public String getQuestion3_preset() {
                return question3_preset;
            }

            public void setQuestion3_preset(String question3_preset) {
                this.question3_preset = question3_preset;
            }

            public String getQuestion1_answer() {
                return question1_answer;
            }

            public void setQuestion1_answer(String question1_answer) {
                this.question1_answer = question1_answer;
            }

            public String getQuestion3_title() {
                return question3_title;
            }

            public void setQuestion3_title(String question3_title) {
                this.question3_title = question3_title;
            }

            public String getQuestion2_answer() {
                return question2_answer;
            }

            public void setQuestion2_answer(String question2_answer) {
                this.question2_answer = question2_answer;
            }

            public String getQuestion3_answer() {
                return question3_answer;
            }

            public void setQuestion3_answer(String question3_answer) {
                this.question3_answer = question3_answer;
            }

            public String getQuestion2_title() {
                return question2_title;
            }

            public void setQuestion2_title(String question2_title) {
                this.question2_title = question2_title;
            }
        }

        public static class ScoreMapBean {
            /**
             * question3 : 0.510603
             * question2 : 0.4519
             * question1 : 0.546791
             */

            private String question3;
            private String question2;
            private String question1;

            public String getQuestion3() {
                return question3;
            }

            public void setQuestion3(String question3) {
                this.question3 = question3;
            }

            public String getQuestion2() {
                return question2;
            }

            public void setQuestion2(String question2) {
                this.question2 = question2;
            }

            public String getQuestion1() {
                return question1;
            }

            public void setQuestion1(String question1) {
                this.question1 = question1;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "question_map=" + question_map +
                    ", score_map=" + score_map +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MeetBackBean{" +
                "data=" + data +
                ", info='" + info + '\'' +
                ", status=" + status +
                '}';
    }
}
