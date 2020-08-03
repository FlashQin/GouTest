package net.goutalk.fowit.Bean;

public class SingBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"id":"12e20ba39498424c9a03ab7ea6518807","memberId":"5ffbc9583e7648649280529b6a01e6d1","signCount":1,"count":1,"updateTime":"2020-07-13 00:00:00"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12e20ba39498424c9a03ab7ea6518807
         * memberId : 5ffbc9583e7648649280529b6a01e6d1
         * signCount : 1
         * count : 1
         * updateTime : 2020-07-13 00:00:00
         */

        private String id;
        private String memberId;
        private int signCount;
        private int count;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public int getSignCount() {
            return signCount;
        }

        public void setSignCount(int signCount) {
            this.signCount = signCount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
