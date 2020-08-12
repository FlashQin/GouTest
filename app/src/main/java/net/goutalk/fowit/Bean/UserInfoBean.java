package net.goutalk.fowit.Bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

    /**
     * code : 0
     * msg : 成功
     * data : {"memberId":"72559fb5aa4c418abf5c799a782ef0ee","memberNo":"17777777777","sex":1,"age":null,"password":null,"headUrl":null,"mobileNo":"17777777777","email":"裤袜不塌","modifyTime":null,"createTime":"2020-07-07 12:01:46","name":null,"nickName":"阿露露","enabled":1,"coinQuantity":2967,"usableCoinQuantity":null,"idNo":null,"wechatNo":"图库邋遢去","wxOpenid":"","alipayNo":null,"inviteCode":"0462889290","activeCode":null,"qrcode":null,"currentDateCoinQuantity":1000,"inviteQuantity":5,"inviteIncome":10000,"activeCodeFlag":0}
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
         * memberId : 72559fb5aa4c418abf5c799a782ef0ee
         * memberNo : 17777777777
         * sex : 1
         * age : null
         * password : null
         * headUrl : null
         * mobileNo : 17777777777
         * email : 裤袜不塌
         * modifyTime : null
         * createTime : 2020-07-07 12:01:46
         * name : null
         * nickName : 阿露露
         * enabled : 1
         * coinQuantity : 2967
         * usableCoinQuantity : null
         * idNo : null
         * wechatNo : 图库邋遢去
         * wxOpenid :
         * alipayNo : null
         * inviteCode : 0462889290
         * activeCode : null
         * qrcode : null
         * currentDateCoinQuantity : 1000
         * inviteQuantity : 5
         * inviteIncome : 10000
         * activeCodeFlag : 0
         */

        private String memberId;
        private String memberNo;
        private int sex;
        private Object age;
        private Object password;
        private Object headUrl;
        private String mobileNo;
        private String email;
        private Object modifyTime;
        private String createTime;
        private Object name;
        private String nickName;
        private int enabled;
        private int coinQuantity;
        private Object usableCoinQuantity;
        private Object idNo;
        private String wechatNo;

        public String getTaobaoPid() {
            return taobaoPid;
        }

        public void setTaobaoPid(String taobaoPid) {
            this.taobaoPid = taobaoPid;
        }

        public int getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(int commissionRate) {
            this.commissionRate = commissionRate;
        }

        private String taobaoPid;
        private int commissionRate;
        private String wxOpenid;
        private Object alipayNo;
        private String inviteCode;
        private Object activeCode;
        private Object qrcode;
        private int currentDateCoinQuantity;
        private int inviteQuantity;
        private int inviteIncome;
        private int activeCodeFlag;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(Object headUrl) {
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

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public int getCoinQuantity() {
            return coinQuantity;
        }

        public void setCoinQuantity(int coinQuantity) {
            this.coinQuantity = coinQuantity;
        }

        public Object getUsableCoinQuantity() {
            return usableCoinQuantity;
        }

        public void setUsableCoinQuantity(Object usableCoinQuantity) {
            this.usableCoinQuantity = usableCoinQuantity;
        }

        public Object getIdNo() {
            return idNo;
        }

        public void setIdNo(Object idNo) {
            this.idNo = idNo;
        }

        public String getWechatNo() {
            return wechatNo;
        }

        public void setWechatNo(String wechatNo) {
            this.wechatNo = wechatNo;
        }

        public String getWxOpenid() {
            return wxOpenid;
        }

        public void setWxOpenid(String wxOpenid) {
            this.wxOpenid = wxOpenid;
        }

        public Object getAlipayNo() {
            return alipayNo;
        }

        public void setAlipayNo(Object alipayNo) {
            this.alipayNo = alipayNo;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public Object getActiveCode() {
            return activeCode;
        }

        public void setActiveCode(Object activeCode) {
            this.activeCode = activeCode;
        }

        public Object getQrcode() {
            return qrcode;
        }

        public void setQrcode(Object qrcode) {
            this.qrcode = qrcode;
        }

        public int getCurrentDateCoinQuantity() {
            return currentDateCoinQuantity;
        }

        public void setCurrentDateCoinQuantity(int currentDateCoinQuantity) {
            this.currentDateCoinQuantity = currentDateCoinQuantity;
        }

        public int getInviteQuantity() {
            return inviteQuantity;
        }

        public void setInviteQuantity(int inviteQuantity) {
            this.inviteQuantity = inviteQuantity;
        }

        public int getInviteIncome() {
            return inviteIncome;
        }

        public void setInviteIncome(int inviteIncome) {
            this.inviteIncome = inviteIncome;
        }

        public int getActiveCodeFlag() {
            return activeCodeFlag;
        }

        public void setActiveCodeFlag(int activeCodeFlag) {
            this.activeCodeFlag = activeCodeFlag;
        }
    }
}
