package net.goutalk.fowit.Bean;

import java.util.List;

public class QustionBean {
    /**
     * code : 0
     * msg : 成功
     * data : [{"questionId":"2","bankId":"0","questionType":1,"rank":1,"questionName":"美国家庭在感恩节基本都会吃的传统食物是？","nameExplainType":1,"choiceA":"披萨","choiceB":"火鸡","choiceC":"平安果","choiceD":"牛肉","answer":"B","answerAnalysis":null,"attachmentId":null,"attachmentName":null,"score":1,"creatorId":null,"createTime":null,"updateId":null,"updateTime":null,"status":1},{"questionId":"1","bankId":"0","questionType":1,"rank":1,"questionName":"玉米原产于：","nameExplainType":1,"choiceA":"中国","choiceB":"日本","choiceC":"美国","choiceD":"摩西个","answer":"D","answerAnalysis":null,"attachmentId":null,"attachmentName":null,"score":1,"creatorId":null,"createTime":null,"updateId":null,"updateTime":null,"status":1}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * questionId : 2
         * bankId : 0
         * questionType : 1
         * rank : 1
         * questionName : 美国家庭在感恩节基本都会吃的传统食物是？
         * nameExplainType : 1
         * choiceA : 披萨
         * choiceB : 火鸡
         * choiceC : 平安果
         * choiceD : 牛肉
         * answer : B
         * answerAnalysis : null
         * attachmentId : null
         * attachmentName : null
         * score : 1
         * creatorId : null
         * createTime : null
         * updateId : null
         * updateTime : null
         * status : 1
         */

        private String questionId;
        private String bankId;
        private int questionType;
        private int rank;
        private String questionName;
        private int nameExplainType;
        private String choiceA;
        private String choiceB;
        private String choiceC;
        private String choiceD;
        private String answer;
        private Object answerAnalysis;
        private Object attachmentId;
        private Object attachmentName;
        private int score;
        private Object creatorId;
        private Object createTime;
        private Object updateId;
        private Object updateTime;
        private int status;

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public int getNameExplainType() {
            return nameExplainType;
        }

        public void setNameExplainType(int nameExplainType) {
            this.nameExplainType = nameExplainType;
        }

        public String getChoiceA() {
            return choiceA;
        }

        public void setChoiceA(String choiceA) {
            this.choiceA = choiceA;
        }

        public String getChoiceB() {
            return choiceB;
        }

        public void setChoiceB(String choiceB) {
            this.choiceB = choiceB;
        }

        public String getChoiceC() {
            return choiceC;
        }

        public void setChoiceC(String choiceC) {
            this.choiceC = choiceC;
        }

        public String getChoiceD() {
            return choiceD;
        }

        public void setChoiceD(String choiceD) {
            this.choiceD = choiceD;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Object getAnswerAnalysis() {
            return answerAnalysis;
        }

        public void setAnswerAnalysis(Object answerAnalysis) {
            this.answerAnalysis = answerAnalysis;
        }

        public Object getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(Object attachmentId) {
            this.attachmentId = attachmentId;
        }

        public Object getAttachmentName() {
            return attachmentName;
        }

        public void setAttachmentName(Object attachmentName) {
            this.attachmentName = attachmentName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public Object getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Object creatorId) {
            this.creatorId = creatorId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateId() {
            return updateId;
        }

        public void setUpdateId(Object updateId) {
            this.updateId = updateId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
