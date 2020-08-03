package net.goutalk.fowit.Base;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/03/20
 * @modified 20/03/20
 * @description pro.haichuang.youpin.beans
 */
public class BaseMsgBean {
    /**
     * status : 1
     * msg : ERROR
     * data : null
     * success : false
     */

    private int code;
    private String msg;
    private Object data;


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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
