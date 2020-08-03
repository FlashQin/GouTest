package net.goutalk.fowit.Bean;

import java.util.List;

public class PromBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"hotList":[{"helpId":"0101","parentId":"01","title":"金币奖励太少?","content":null,"priority":1,"isDisplay":true,"isCatalog":false,"isHot":true}],"categoryList":[{"helpId":"02","parentId":"2","title":"账号问题","content":null,"priority":2,"isDisplay":true,"isCatalog":true,"isHot":false},{"helpId":"01","parentId":"2","title":"金币问题","content":null,"priority":1,"isDisplay":true,"isCatalog":true,"isHot":false}]}
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
        private List<HotListBean> hotList;
        private List<CategoryListBean> categoryList;

        public List<HotListBean> getHotList() {
            return hotList;
        }

        public void setHotList(List<HotListBean> hotList) {
            this.hotList = hotList;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public static class HotListBean {
            /**
             * helpId : 0101
             * parentId : 01
             * title : 金币奖励太少?
             * content : null
             * priority : 1
             * isDisplay : true
             * isCatalog : false
             * isHot : true
             */

            private String helpId;
            private String parentId;
            private String title;
            private Object content;
            private int priority;
            private boolean isDisplay;
            private boolean isCatalog;
            private boolean isHot;

            public String getHelpId() {
                return helpId;
            }

            public void setHelpId(String helpId) {
                this.helpId = helpId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public boolean isIsDisplay() {
                return isDisplay;
            }

            public void setIsDisplay(boolean isDisplay) {
                this.isDisplay = isDisplay;
            }

            public boolean isIsCatalog() {
                return isCatalog;
            }

            public void setIsCatalog(boolean isCatalog) {
                this.isCatalog = isCatalog;
            }

            public boolean isIsHot() {
                return isHot;
            }

            public void setIsHot(boolean isHot) {
                this.isHot = isHot;
            }
        }

        public static class CategoryListBean {
            /**
             * helpId : 02
             * parentId : 2
             * title : 账号问题
             * content : null
             * priority : 2
             * isDisplay : true
             * isCatalog : true
             * isHot : false
             */

            private String helpId;
            private String parentId;
            private String title;
            private Object content;
            private int priority;
            private boolean isDisplay;
            private boolean isCatalog;
            private boolean isHot;

            public String getHelpId() {
                return helpId;
            }

            public void setHelpId(String helpId) {
                this.helpId = helpId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public boolean isIsDisplay() {
                return isDisplay;
            }

            public void setIsDisplay(boolean isDisplay) {
                this.isDisplay = isDisplay;
            }

            public boolean isIsCatalog() {
                return isCatalog;
            }

            public void setIsCatalog(boolean isCatalog) {
                this.isCatalog = isCatalog;
            }

            public boolean isIsHot() {
                return isHot;
            }

            public void setIsHot(boolean isHot) {
                this.isHot = isHot;
            }
        }
    }
}
