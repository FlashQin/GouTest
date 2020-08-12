package net.goutalk.fowit.Bean;

public class JDJsonBean {
    /**
     * goodsReq : {"pageSize":"1","eliteId":"22","pageIndex":"1"}
     */

    private GoodsReqBean goodsReq;

    public GoodsReqBean getGoodsReq() {
        return goodsReq;
    }

    public void setGoodsReq(GoodsReqBean goodsReq) {
        this.goodsReq = goodsReq;
    }

    public static class GoodsReqBean {
        /**
         * pageSize : 1
         * eliteId : 22
         * pageIndex : 1
         */

        private String pageSize;
        private String eliteId;
        private String pageIndex;

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getEliteId() {
            return eliteId;
        }

        public void setEliteId(String eliteId) {
            this.eliteId = eliteId;
        }

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }
    }
}
