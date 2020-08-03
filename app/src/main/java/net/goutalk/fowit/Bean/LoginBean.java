package net.goutalk.fowit.Bean;

import java.io.Serializable;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/03/15
 * @modified 20/03/15
 * @description com.haichuang.chefu.beans
 */
public class LoginBean implements Serializable {

    /**
     * status : 0
     * msg : null
     * data : {"user":{"id":7,"img":null,"name":null,"sex":null,"phone":"13882086203","qq":null,"wx":null,"password":"123456","province":null,"city":null,"area":null,"integral":null,"userType":1,"createdAt":"2020-03-15 14:23:07","updatedAt":"2020-03-15 14:23:07","status":1,"isDelete":0},"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODQzNDMzNzIsInVzZXJJZCI6N30.KVErLKAVmyLEfE2izttLwq1xRWEMqBmLQ_71ByWOduc"}
     * success : true
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int status) {
        this.code = status;
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



    public static class DataBean implements Serializable {

        private String easyfowitToken;
        public String getEasyfowitToken() {
            return easyfowitToken;
        }

        public void setEasyfowitToken(String easyfowitToken) {
            this.easyfowitToken = easyfowitToken;
        }
    }
}
