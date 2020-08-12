package net.goutalk.fowit.Bean;

import java.util.List;

public class OderListBean {
    /**
     * code : 0
     * msg : 成功
     * data : {"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"total":12,"pages":2,"list":[{"orderId":"3f947a454463430a9b3932a98661e7c7","orderNo":"1161496546777296761","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:27:53","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"44d1475167664bddba5eef39303bd0e1","orderNo":"1161496546777296762","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-09-16 13:27:57","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"5ac95d367f574586b63444c9d35bedfe","orderNo":"1161496546777296763","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-04 13:28:01","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"96c161492f3b49babe0abdc06a359cda","orderNo":"1161496546777296764","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-05 13:28:04","totalPrice":"42.96","shareMemberFee":"1.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"9cfb9ca841bb425e9b03bbe85db21bee","orderNo":"1161496546777296765","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:08","totalPrice":"42.96","shareMemberFee":"2.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"a15816a02e6b47a5bd61edf238915d6b","orderNo":"1161496546777296766","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:11","totalPrice":"42.96","shareMemberFee":"3.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"a8b8feef4249441196fe1dfd55e92247","orderNo":"1161496546777296767","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:14","totalPrice":"42.96","shareMemberFee":"4.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"c2654119b65844f095bb81f90ca09b3b","orderNo":"2161496546777296761","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:17","totalPrice":"42.96","shareMemberFee":"5.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"e343ca7878624217a8938bc7b2a625a0","orderNo":"2161496546777296762","orderStatus":3,"orderType":4,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:20","totalPrice":"42.96","shareMemberFee":"1.2","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"e60feb1464634862a4f0d3e202622328","orderNo":"2161496546777296763","orderStatus":3,"orderType":4,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:24","totalPrice":"42.96","shareMemberFee":"1.5","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"}],"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2],"navigateFirstPage":1,"navigateLastPage":2,"firstPage":1,"lastPage":2}
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
         * size : 10
         * startRow : 1
         * endRow : 10
         * total : 12
         * pages : 2
         * list : [{"orderId":"3f947a454463430a9b3932a98661e7c7","orderNo":"1161496546777296761","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:27:53","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"44d1475167664bddba5eef39303bd0e1","orderNo":"1161496546777296762","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-09-16 13:27:57","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"5ac95d367f574586b63444c9d35bedfe","orderNo":"1161496546777296763","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-04 13:28:01","totalPrice":"42.96","shareMemberFee":"1","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"96c161492f3b49babe0abdc06a359cda","orderNo":"1161496546777296764","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-05 13:28:04","totalPrice":"42.96","shareMemberFee":"1.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"9cfb9ca841bb425e9b03bbe85db21bee","orderNo":"1161496546777296765","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:08","totalPrice":"42.96","shareMemberFee":"2.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"a15816a02e6b47a5bd61edf238915d6b","orderNo":"1161496546777296766","orderStatus":3,"orderType":3,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:11","totalPrice":"42.96","shareMemberFee":"3.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"a8b8feef4249441196fe1dfd55e92247","orderNo":"1161496546777296767","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:14","totalPrice":"42.96","shareMemberFee":"4.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"c2654119b65844f095bb81f90ca09b3b","orderNo":"2161496546777296761","orderStatus":3,"orderType":2,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:17","totalPrice":"42.96","shareMemberFee":"5.99","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"e343ca7878624217a8938bc7b2a625a0","orderNo":"2161496546777296762","orderStatus":3,"orderType":4,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:20","totalPrice":"42.96","shareMemberFee":"1.2","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"},{"orderId":"e60feb1464634862a4f0d3e202622328","orderNo":"2161496546777296763","orderStatus":3,"orderType":4,"itemTitle":"景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶","paidTime":"2020-08-10 13:28:24","totalPrice":"42.96","shareMemberFee":"1.5","itemImg":"//img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg"}]
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2]
         * navigateFirstPage : 1
         * navigateLastPage : 2
         * firstPage : 1
         * lastPage : 2
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
        private int firstPage;
        private int lastPage;
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

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
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
             * orderId : 3f947a454463430a9b3932a98661e7c7
             * orderNo : 1161496546777296761
             * orderStatus : 3
             * orderType : 2
             * itemTitle : 景德镇陶瓷杯带盖办公室会议杯家用喝水杯子4只套装酒店宾馆泡茶
             * paidTime : 2020-08-10 13:27:53
             * totalPrice : 42.96
             * shareMemberFee : 1
             * itemImg : //img.alicdn.com/tfscom/i3/2787502958/O1CN01IJjhpy1XioA03zuw5_!!2787502958.jpg
             */

            private String orderId;
            private String orderNo;
            private int orderStatus;
            private int orderType;
            private String itemTitle;
            private String paidTime;
            private String totalPrice;
            private String shareMemberFee;
            private String itemImg;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public String getItemTitle() {
                return itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getPaidTime() {
                return paidTime;
            }

            public void setPaidTime(String paidTime) {
                this.paidTime = paidTime;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getShareMemberFee() {
                return shareMemberFee;
            }

            public void setShareMemberFee(String shareMemberFee) {
                this.shareMemberFee = shareMemberFee;
            }

            public String getItemImg() {
                return itemImg;
            }

            public void setItemImg(String itemImg) {
                this.itemImg = itemImg;
            }
        }
    }
}
