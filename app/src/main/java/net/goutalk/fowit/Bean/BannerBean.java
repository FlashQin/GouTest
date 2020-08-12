package net.goutalk.fowit.Bean;

import java.util.List;

public class BannerBean {
    /**
     * code : 0
     * msg : 成功
     * data : [{"id":"2","title":"0元购","url":"2","imgUrl":"http://image.51wit.cn/20200812/sy_banner_lyg.png","channel":2,"section":"2","splash":null,"content":"新用户专享就是这么任性","priority":1,"status":1},{"id":"6","title":"17元办卡","url":"2","imgUrl":"http://image.51wit.cn/20200812/sy_banner_qqgjlk.png","channel":2,"section":"2","splash":null,"content":"限时办卡","priority":1,"status":1}]
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
         * id : 2
         * title : 0元购
         * url : 2
         * imgUrl : http://image.51wit.cn/20200812/sy_banner_lyg.png
         * channel : 2
         * section : 2
         * splash : null
         * content : 新用户专享就是这么任性
         * priority : 1
         * status : 1
         */

        private String id;
        private String title;
        private String url;
        private String imgUrl;
        private int channel;
        private String section;
        private Object splash;
        private String content;
        private int priority;
        private int status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public Object getSplash() {
            return splash;
        }

        public void setSplash(Object splash) {
            this.splash = splash;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
