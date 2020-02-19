package com.chanshiguan.yumeng.Bean;

import java.util.List;

public class ExpertZoneBean {

    /**
     * Statute : success
     * ExpertZoneItem : [{"ExpertID":"20190405","ExpertName":"育萌专家","ExpertZoneTime":"2019年4月4日","ExpertZoneTipsOne":"狗狗脱毛","ExpertZoneTipsTwo":"泪痕","ExpertZoneDetail":"狗狗每年的春、秋两季是换毛的旺季，在夏天和冬天到来之前，它们需要更换皮毛来适应比较极端的高温或低温环境。在换毛旺季，狗狗的毛就会一缕一团地掉，用手摸一下狗狗手上也会沾很多狗毛。","ExpertZoneMethod":"每天带狗狗到室外或者在卫生间里梳理毛发，用狗狗专用的梳子来帮狗狗去除脱落的毛发，这样就能减少家中的地面、沙发上的狗毛。同时要注意给狗狗补充营养和水分，帮助狗狗顺利脱毛。","ExpertHeadImageUrl":"/HeadImage/20190405.jpg"},{"ExpertID":"20190406","ExpertName":"育萌专家","ExpertZoneTime":"2019年4月5日","ExpertZoneTipsOne":"狗狗脱毛","ExpertZoneTipsTwo":"泪痕","ExpertZoneDetail":"狗狗一般是吃狗粮的，但是铲屎官们出于各种原因还是会喂一些其他食物给狗狗，有时候还会给人吃的饭菜给它。这样的做法可以帮助狗狗补充更全面的营养，是没错，但是如果喂太多，容易导致狗狗摄入太多盐分，进而引起脱毛。","ExpertZoneMethod":"少给狗狗吃人吃的食物，即使喂肉类，也记得喂水煮，不加盐。另外可以偶尔给狗狗买一些零食，补充些营养。","ExpertHeadImageUrl":"/HeadImage/20190406.jpg"},{"ExpertID":"20190407","ExpertName":"育萌专家","ExpertZoneTime":"2019年4月5日","ExpertZoneTipsOne":"狗狗脱毛","ExpertZoneTipsTwo":"泪痕","ExpertZoneDetail":"如果狗狗洗澡太频繁、没有用狗狗专用沐浴露、身上长了寄生虫等就会引起皮肤病的发生，这时候狗狗就会异常脱毛。这种情况下，狗狗会经常用爪子挠痒痒，作为铲屎官一定要注意检查。","ExpertZoneMethod":"控制狗狗的洗澡次数，夏天最多一周洗一次，冬天可以半个月洗一次。购买狗狗专用的沐浴露，不要给它用人用的洗发水或沐浴露。定期给狗狗驱虫，如果发现狗狗身上有红斑等皮肤病，即使买药给狗狗涂。","ExpertHeadImageUrl":"/HeadImage/20190407.jpg"}]
     */

    private String Statute;
    private List<ExpertZoneItemBean> ExpertZoneItem;

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String Statute) {
        this.Statute = Statute;
    }

    public List<ExpertZoneItemBean> getExpertZoneItem() {
        return ExpertZoneItem;
    }

    public void setExpertZoneItem(List<ExpertZoneItemBean> ExpertZoneItem) {
        this.ExpertZoneItem = ExpertZoneItem;
    }

    public static class ExpertZoneItemBean {
        /**
         * ExpertID : 20190405
         * ExpertName : 育萌专家
         * ExpertZoneTime : 2019年4月4日
         * ExpertZoneTipsOne : 狗狗脱毛
         * ExpertZoneTipsTwo : 泪痕
         * ExpertZoneDetail : 狗狗每年的春、秋两季是换毛的旺季，在夏天和冬天到来之前，它们需要更换皮毛来适应比较极端的高温或低温环境。在换毛旺季，狗狗的毛就会一缕一团地掉，用手摸一下狗狗手上也会沾很多狗毛。
         * ExpertZoneMethod : 每天带狗狗到室外或者在卫生间里梳理毛发，用狗狗专用的梳子来帮狗狗去除脱落的毛发，这样就能减少家中的地面、沙发上的狗毛。同时要注意给狗狗补充营养和水分，帮助狗狗顺利脱毛。
         * ExpertHeadImageUrl : /HeadImage/20190405.jpg
         */

        private String ExpertID;
        private String ExpertName;
        private String ExpertZoneTime;
        private String ExpertZoneTipsOne;
        private String ExpertZoneTipsTwo;
        private String ExpertZoneDetail;
        private String ExpertZoneMethod;
        private String ExpertHeadImageUrl;

        public String getExpertID() {
            return ExpertID;
        }

        public void setExpertID(String ExpertID) {
            this.ExpertID = ExpertID;
        }

        public String getExpertName() {
            return ExpertName;
        }

        public void setExpertName(String ExpertName) {
            this.ExpertName = ExpertName;
        }

        public String getExpertZoneTime() {
            return ExpertZoneTime;
        }

        public void setExpertZoneTime(String ExpertZoneTime) {
            this.ExpertZoneTime = ExpertZoneTime;
        }

        public String getExpertZoneTipsOne() {
            return ExpertZoneTipsOne;
        }

        public void setExpertZoneTipsOne(String ExpertZoneTipsOne) {
            this.ExpertZoneTipsOne = ExpertZoneTipsOne;
        }

        public String getExpertZoneTipsTwo() {
            return ExpertZoneTipsTwo;
        }

        public void setExpertZoneTipsTwo(String ExpertZoneTipsTwo) {
            this.ExpertZoneTipsTwo = ExpertZoneTipsTwo;
        }

        public String getExpertZoneDetail() {
            return ExpertZoneDetail;
        }

        public void setExpertZoneDetail(String ExpertZoneDetail) {
            this.ExpertZoneDetail = ExpertZoneDetail;
        }

        public String getExpertZoneMethod() {
            return ExpertZoneMethod;
        }

        public void setExpertZoneMethod(String ExpertZoneMethod) {
            this.ExpertZoneMethod = ExpertZoneMethod;
        }

        public String getExpertHeadImageUrl() {
            return ExpertHeadImageUrl;
        }

        public void setExpertHeadImageUrl(String ExpertHeadImageUrl) {
            this.ExpertHeadImageUrl = ExpertHeadImageUrl;
        }
    }
}
