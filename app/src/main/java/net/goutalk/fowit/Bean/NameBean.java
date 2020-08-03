package net.goutalk.fowit.Bean;

public class NameBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"memberId":"5ffbc9583e7648649280529b6a01e6d1","name":null,"nickName":null,"sex":null,"mobileNo":"18782046493","password":"e10adc3949ba59abbe56e057f20f883e","email":null,"wechatNo":"13551259347?n=2","alipayNo":null}
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
         * memberId : 5ffbc9583e7648649280529b6a01e6d1
         * name : null
         * nickName : null
         * sex : null
         * mobileNo : 18782046493
         * password : e10adc3949ba59abbe56e057f20f883e
         * email : null
         * wechatNo : 13551259347?n=2
         * alipayNo : null
         */

        private String memberId;
        private Object name;
        private Object nickName;
        private Object sex;
        private String mobileNo;
        private String password;
        private Object email;
        private String wechatNo;
        private Object alipayNo;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getWechatNo() {
            return wechatNo;
        }

        public void setWechatNo(String wechatNo) {
            this.wechatNo = wechatNo;
        }

        public Object getAlipayNo() {
            return alipayNo;
        }

        public void setAlipayNo(Object alipayNo) {
            this.alipayNo = alipayNo;
        }
    }
}
