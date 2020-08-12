package net.goutalk.fowit.Bean;

import java.util.List;

public class VideoListBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"pageNum":1,"pageSize":10,"size":7,"startRow":1,"endRow":7,"total":7,"pages":1,"list":[{"videoId":"7","categoryId":"11","videoName":"英雄难过美人关：朱一龙看到美丽的张馨予，当场心软！","thumbnail":"http://img04.video.baomihua.com/854_480/46689896.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2020-07-17 09:21:06","minutes":null,"playUrl":"http://aliyun48001.baomihua.com/1de6b48c989e3a472b7f41d52e2c0d00/5f110400/4669/46689896_9_eb216aa046355fd4bd22ca10f430d34d.mp4","filepath":null,"depict":null,"isRecommend":true,"status":2,"viewsDay":null,"commentsDay":null,"source":null,"sourceUrl":null,"views":0,"comments":0,"ups":0,"shares":0},{"videoId":"4","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"5","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"6","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"2","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":null,"depict":null,"isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":null,"sourceUrl":null,"views":null,"comments":null,"ups":null,"shares":null},{"videoId":"3","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":1,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"1","categoryId":"2","videoName":"李亚鹏秀女儿李嫣弹钢琴照 长发飞扬小美女神似妈妈王菲","thumbnail":"https://img04.video.baomihua.com/230_172/37787907.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2018-06-03 16:01:18","minutes":"1:28","playUrl":"http://www.w3cschool.cc/try/demo_source/mov_bbb.mp4","filepath":null,"depict":null,"isRecommend":false,"status":2,"viewsDay":3,"commentsDay":10,"source":null,"sourceUrl":null,"views":null,"comments":null,"ups":null,"shares":null}],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"lastPage":1,"firstPage":1}
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
         * pageNum : 1
         * pageSize : 10
         * size : 7
         * startRow : 1
         * endRow : 7
         * total : 7
         * pages : 1
         * list : [{"videoId":"7","categoryId":"11","videoName":"英雄难过美人关：朱一龙看到美丽的张馨予，当场心软！","thumbnail":"http://img04.video.baomihua.com/854_480/46689896.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2020-07-17 09:21:06","minutes":null,"playUrl":"http://aliyun48001.baomihua.com/1de6b48c989e3a472b7f41d52e2c0d00/5f110400/4669/46689896_9_eb216aa046355fd4bd22ca10f430d34d.mp4","filepath":null,"depict":null,"isRecommend":true,"status":2,"viewsDay":null,"commentsDay":null,"source":null,"sourceUrl":null,"views":0,"comments":0,"ups":0,"shares":0},{"videoId":"4","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"5","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"6","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"2","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":null,"depict":null,"isRecommend":false,"status":2,"viewsDay":0,"commentsDay":null,"source":null,"sourceUrl":null,"views":null,"comments":null,"ups":null,"shares":null},{"videoId":"3","categoryId":"2","videoName":"被删亲热视频","thumbnail":"http://img04.video.baomihua.com/640_360/37743672.jpg","memberId":"","headUrl":null,"nickName":null,"createTime":"2018-06-03 16:57:40","minutes":"2:39","playUrl":"http://video.easymis.org/joke/recreation/20180603/5b6aad12c74511e48ww001ec9e282f9.mp4","filepath":"","depict":"","isRecommend":false,"status":2,"viewsDay":1,"commentsDay":null,"source":"","sourceUrl":"","views":null,"comments":null,"ups":null,"shares":null},{"videoId":"1","categoryId":"2","videoName":"李亚鹏秀女儿李嫣弹钢琴照 长发飞扬小美女神似妈妈王菲","thumbnail":"https://img04.video.baomihua.com/230_172/37787907.jpg","memberId":null,"headUrl":null,"nickName":null,"createTime":"2018-06-03 16:01:18","minutes":"1:28","playUrl":"http://www.w3cschool.cc/try/demo_source/mov_bbb.mp4","filepath":null,"depict":null,"isRecommend":false,"status":2,"viewsDay":3,"commentsDay":10,"source":null,"sourceUrl":null,"views":null,"comments":null,"ups":null,"shares":null}]
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * lastPage : 1
         * firstPage : 1
         */

        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int lastPage;
        private int firstPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * videoId : 7
             * categoryId : 11
             * videoName : 英雄难过美人关：朱一龙看到美丽的张馨予，当场心软！
             * thumbnail : http://img04.video.baomihua.com/854_480/46689896.jpg
             * memberId : null
             * headUrl : null
             * nickName : null
             * createTime : 2020-07-17 09:21:06
             * minutes : null
             * playUrl : http://aliyun48001.baomihua.com/1de6b48c989e3a472b7f41d52e2c0d00/5f110400/4669/46689896_9_eb216aa046355fd4bd22ca10f430d34d.mp4
             * filepath : null
             * depict : null
             * isRecommend : true
             * status : 2
             * viewsDay : null
             * commentsDay : null
             * source : null
             * sourceUrl : null
             * views : 0
             * comments : 0
             * ups : 0
             * shares : 0
             */

            private String videoId;
            private String categoryId;
            private String videoName;
            private String thumbnail;
            private Object memberId;
            private Object headUrl;
            private Object nickName;
            private String createTime;
            private Object minutes;
            private String playUrl;
            private Object filepath;
            private Object depict;
            private boolean isRecommend;
            private int status;
            private Object viewsDay;
            private Object commentsDay;
            private Object source;
            private Object sourceUrl;
            private int views;
            private int comments;
            private int ups;
            private int shares;
            public boolean getmData() {
                return mData;
            }

            public void setmData(boolean mData) {
                this.mData = mData;
            }

            private boolean mData;
            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getVideoName() {
                return videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public Object getMemberId() {
                return memberId;
            }

            public void setMemberId(Object memberId) {
                this.memberId = memberId;
            }

            public Object getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(Object headUrl) {
                this.headUrl = headUrl;
            }

            public Object getNickName() {
                return nickName;
            }

            public void setNickName(Object nickName) {
                this.nickName = nickName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getMinutes() {
                return minutes;
            }

            public void setMinutes(Object minutes) {
                this.minutes = minutes;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public Object getFilepath() {
                return filepath;
            }

            public void setFilepath(Object filepath) {
                this.filepath = filepath;
            }

            public Object getDepict() {
                return depict;
            }

            public void setDepict(Object depict) {
                this.depict = depict;
            }

            public boolean isIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(boolean isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getViewsDay() {
                return viewsDay;
            }

            public void setViewsDay(Object viewsDay) {
                this.viewsDay = viewsDay;
            }

            public Object getCommentsDay() {
                return commentsDay;
            }

            public void setCommentsDay(Object commentsDay) {
                this.commentsDay = commentsDay;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getSourceUrl() {
                return sourceUrl;
            }

            public void setSourceUrl(Object sourceUrl) {
                this.sourceUrl = sourceUrl;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getUps() {
                return ups;
            }

            public void setUps(int ups) {
                this.ups = ups;
            }

            public int getShares() {
                return shares;
            }

            public void setShares(int shares) {
                this.shares = shares;
            }
        }
    }
}
