package net.goutalk.fowit.Bean;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/03/20
 * @modified 20/03/20
 * @description pro.haichuang.youpin.beans
 */
public class SimgMsgBean {
    /**
     * status : 1
     * msg : ERROR
     * data : null
     * success : false
     */

    private int code;
    private String msg;



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



}
