package net.goutalk.fowit.Bean;

import java.util.List;

public class VideoTabBean {
    /**
     * code : 0
     * msg : 成功
     * data : [{"url":"1","title":"推荐"},{"url":"category=11","title":"影视"},{"url":"category=1","title":"搞笑"},{"url":"category=2","title":"娱乐"},{"url":"category=12","title":"音乐"},{"url":"category=6","title":"舞蹈"},{"url":"category=13","title":"美女"}]
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
         * url : 1
         * title : 推荐
         */

        private String url;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
