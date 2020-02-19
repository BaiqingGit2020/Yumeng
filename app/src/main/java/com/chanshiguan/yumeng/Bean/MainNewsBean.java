package com.chanshiguan.yumeng.Bean;

import java.util.List;

public class MainNewsBean {

    /**
     * Statute : success
     * NewsItem : [{"NewsID":"1","NewsName":"宠物与狗，你养的是什么","NewsDetail":"清明假期可否寄养一下她的狗狗时，我欣然答应了","NewsImage":"https://mmbiz.qpic.cn/mmbiz_jpg/dvib0l1CJHkWbwbMibcLCFvKG1NU6Cwufm9z7IVVUezINPUgPXnpng95PTRtz020Rdvoo5xeFQqkCRicF9qOKWkVw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1","NewsUrl":"https://mp.weixin.qq.com/s?__biz=MzU3NTc0NDMyMA==&mid=2247484696&idx=1&sn=38497e8eff5cbc2bb739d673c2bb47f9&chksm=fd1f3537ca68bc21151c6c8082165acd1327c3b5a6864c87f13efe989d821377573585ee0d0b&scene=7&ascene=0&devicetype=android-28&version=2700033c&nettype=WIFI&abtest_cookie=BQABAAgACgALABIAEwAGAJ6GHgAjlx4AVpkeAMSZHgDUmR4A3JkeAAAA&lang=zh_CN&pass_ticket=lNtWq3tKwfbts3dmDfzbyb5sKswI7dlETF%2B6LVRZ9HGeAOVYm%2FDNZwGOv%2FSR%2BAJI&wx_header=1"},{"NewsID":"2","NewsName":"宠物市场研究报告","NewsDetail":"人均GDP的提升、宠物饲养比例的提高、宠物消费意愿的增强，将持续推动宠物产业的发展。","NewsImage":"https://mmbiz.qpic.cn/mmbiz_jpg/m18ToqibvWsRSceX2Gc4Adx11WFIWznIpBwdvkw0Uy2DCIXqecYSWFzr46P9TWmNlLgHR8PTicTrD0BUJsrzFXYQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1","NewsUrl":"https://mp.weixin.qq.com/s?__biz=MjM5MjI0Nzk5NA==&mid=2650066976&idx=2&sn=d74e904d1366bf4c700e83117b67b819&chksm=bea949f589dec0e379b2d4fa4107970f21385eeb38ba3629b02461172fe4632c7835b995527e&scene=7&ascene=0&devicetype=android-28&version=2700033c&nettype=WIFI&abtest_cookie=BQABAAgACgALABIAEwAGAJ6GHgAjlx4AVpkeAMSZHgDUmR4A3JkeAAAA&lang=zh_CN&pass_ticket=lNtWq3tKwfbts3dmDfzbyb5sKswI7dlETF%2B6LVRZ9HGeAOVYm%2FDNZwGOv%2FSR%2BAJI&wx_header=1"}]
     */

    private String Statute;
    private List<NewsItemBean> NewsItem;

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String Statute) {
        this.Statute = Statute;
    }

    public List<NewsItemBean> getNewsItem() {
        return NewsItem;
    }

    public void setNewsItem(List<NewsItemBean> NewsItem) {
        this.NewsItem = NewsItem;
    }

    public static class NewsItemBean {
        /**
         * NewsID : 1
         * NewsName : 宠物与狗，你养的是什么
         * NewsDetail : 清明假期可否寄养一下她的狗狗时，我欣然答应了
         * NewsImage : https://mmbiz.qpic.cn/mmbiz_jpg/dvib0l1CJHkWbwbMibcLCFvKG1NU6Cwufm9z7IVVUezINPUgPXnpng95PTRtz020Rdvoo5xeFQqkCRicF9qOKWkVw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1
         * NewsUrl : https://mp.weixin.qq.com/s?__biz=MzU3NTc0NDMyMA==&mid=2247484696&idx=1&sn=38497e8eff5cbc2bb739d673c2bb47f9&chksm=fd1f3537ca68bc21151c6c8082165acd1327c3b5a6864c87f13efe989d821377573585ee0d0b&scene=7&ascene=0&devicetype=android-28&version=2700033c&nettype=WIFI&abtest_cookie=BQABAAgACgALABIAEwAGAJ6GHgAjlx4AVpkeAMSZHgDUmR4A3JkeAAAA&lang=zh_CN&pass_ticket=lNtWq3tKwfbts3dmDfzbyb5sKswI7dlETF%2B6LVRZ9HGeAOVYm%2FDNZwGOv%2FSR%2BAJI&wx_header=1
         */

        private String NewsID;
        private String NewsName;
        private String NewsDetail;
        private String NewsImage;
        private String NewsUrl;

        public String getNewsID() {
            return NewsID;
        }

        public void setNewsID(String NewsID) {
            this.NewsID = NewsID;
        }

        public String getNewsName() {
            return NewsName;
        }

        public void setNewsName(String NewsName) {
            this.NewsName = NewsName;
        }

        public String getNewsDetail() {
            return NewsDetail;
        }

        public void setNewsDetail(String NewsDetail) {
            this.NewsDetail = NewsDetail;
        }

        public String getNewsImage() {
            return NewsImage;
        }

        public void setNewsImage(String NewsImage) {
            this.NewsImage = NewsImage;
        }

        public String getNewsUrl() {
            return NewsUrl;
        }

        public void setNewsUrl(String NewsUrl) {
            this.NewsUrl = NewsUrl;
        }
    }
}
