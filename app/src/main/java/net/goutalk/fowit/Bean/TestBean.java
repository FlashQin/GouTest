package net.goutalk.fowit.Bean;

/**
 * @author lzy <axhlzy@live.cn>
 * @created 20/03/20
 * @modified 20/03/20
 * @description pro.haichuang.youpin.beans
 */
public class TestBean {
    /**
     * status : 1
     * msg : ERROR
     * data : null
     * success : false
     */

    private int status;
    private String msg;
    private Object data;
    private boolean success;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
