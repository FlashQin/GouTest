package net.goutalk.fowit.Bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    /**
     * code : 0
     * msg : 功
     * data : {"memberId":"2016110202340575","memberNo":"1","sex":1,"age":1,"password":null,"headUrl":"http://www.51wit.cn/images/app/headUrl.png","mobileNo":"13551259347","email":null,"modifyTime":"2020-06-11 10:48:47","createTime":"2020-06-11 10:48:43","name":"谭宇杰","enabled":1,"coinQuantity":2700,"inviteCode":"1234567890","activeCode":null,"qrcode":null,"currentDateCoinQuantity":1000,"inviteQuantity":5}
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

    public static class DataBean implements Serializable{
        /**
         * memberId : 2016110202340575
         * memberNo : 1
         * sex : 1
         * age : 1
         * password : null
         * headUrl : http://www.51wit.cn/images/app/headUrl.png
         * mobileNo : 13551259347
         * email : null
         * modifyTime : 2020-06-11 10:48:47
         * createTime : 2020-06-11 10:48:43
         * name : 谭宇杰
         * enabled : 1
         * coinQuantity : 2700
         * inviteCode : 1234567890
         * activeCode : null
         * qrcode : null
         * currentDateCoinQuantity : 1000
         * inviteQuantity : 5
         */

        private String memberId;
        private String memberNo;
        private String sex;
        private String age;
        private String password;
        private String headUrl;
        private String mobileNo;
        private String email;
        private String modifyTime;
        private String createTime;
        private String name;
        private String enabled;
        private String coinQuantity;
        private String inviteCode;
        private String activeCode;
        private String qrcode;
        private String currentDateCoinQuantity;
        private String inviteQuantity;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(String memberNo) {
            this.memberNo = memberNo;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getCoinQuantity() {
            return coinQuantity;
        }

        public void setCoinQuantity(String coinQuantity) {
            this.coinQuantity = coinQuantity;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getActiveCode() {
            return activeCode;
        }

        public void setActiveCode(String activeCode) {
            this.activeCode = activeCode;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getCurrentDateCoinQuantity() {
            return currentDateCoinQuantity;
        }

        public void setCurrentDateCoinQuantity(String currentDateCoinQuantity) {
            this.currentDateCoinQuantity = currentDateCoinQuantity;
        }

        public String getInviteQuantity() {
            return inviteQuantity;
        }

        public void setInviteQuantity(String inviteQuantity) {
            this.inviteQuantity = inviteQuantity;
        }
    }
}
