package com.chanshiguan.yumeng.Bean;

import java.util.List;

public class FoodRemarkBean {

    /**
     * Statute : success
     * RemarkItem : [{"RemarkTime":"2018年2月3日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2019年4月3日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务"},{"RemarkTime":"2019年1月4日","RemarkUserName":"1234","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。"},{"RemarkTime":"2018年4月5日","RemarkUserName":"12345","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年4月28日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年4月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年5月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年6月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年7月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年8月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年9月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年10月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年11月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2018年12月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年12月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"花钱的评价，麻烦你们认真点！产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"产品总体不错，包装严实。再说商家服务：点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"产品总体不错，包装严实。点赞啦。"},{"RemarkTime":"2017年8月29日","RemarkUserName":"123","RemarkDetail":"产品总体不错，包装严实。"}]
     */

    private String Statute;
    private List<RemarkItemBean> RemarkItem;

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String Statute) {
        this.Statute = Statute;
    }

    public List<RemarkItemBean> getRemarkItem() {
        return RemarkItem;
    }

    public void setRemarkItem(List<RemarkItemBean> RemarkItem) {
        this.RemarkItem = RemarkItem;
    }

    public static class RemarkItemBean {
        /**
         * RemarkTime : 2018年2月3日
         * RemarkUserName : 123
         * RemarkDetail : 这是一条好评段子，花钱的评价，麻烦你们认真点！先说商品质量：产品总体不错，包装严实。再说商家服务：点赞啦。
         */

        private String RemarkTime;
        private String RemarkUserName;
        private String RemarkDetail;

        public String getRemarkTime() {
            return RemarkTime;
        }

        public void setRemarkTime(String RemarkTime) {
            this.RemarkTime = RemarkTime;
        }

        public String getRemarkUserName() {
            return RemarkUserName;
        }

        public void setRemarkUserName(String RemarkUserName) {
            this.RemarkUserName = RemarkUserName;
        }

        public String getRemarkDetail() {
            return RemarkDetail;
        }

        public void setRemarkDetail(String RemarkDetail) {
            this.RemarkDetail = RemarkDetail;
        }
    }
}
