package net.goutalk.fowit.Bean;

import com.google.gson.annotations.SerializedName;

public class WXPAYbean {
    /**
     * data : {"package":"Sign=WXPay","appid":"wxfe42ff9a68f0c86b","sign":"E44C1CDA3711CC035A88F2B59B9D5099","partnerid":"1594694181","prepayid":"wx2615475998774425ce79ad8b1069938400","noncestr":"kA97w7edAyC1B7hb","timestamp":"1590479280"}
     * status : 0
     * success : true
     */

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

    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean {
        /**
         * package : Sign=WXPay
         * appid : wxfe42ff9a68f0c86b
         * sign : E44C1CDA3711CC035A88F2B59B9D5099
         * partnerid : 1594694181
         * prepayid : wx2615475998774425ce79ad8b1069938400
         * noncestr : kA97w7edAyC1B7hb
         * timestamp : 1590479280
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        private String out_trade_no;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
