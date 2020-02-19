package com.chanshiguan.yumeng.Bean;

import java.util.List;

public class HospitalBean {

    /**
     * Statute : success
     * HospitalItem : [{"HospitalID":"112233","HospitalName":"宠物商店1","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123/HospitalImage/112233.jpg"},{"HospitalID":"112234","HospitalName":"宠物商店2","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123/HospitalImage/112234.jpg"},{"HospitalID":"112235","HospitalName":"宠物商店3","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123/HospitalImage/112235.jpg"},{"HospitalID":"112236","HospitalName":"宠物商店3","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123/HospitalImage/112236.jpg"},{"HospitalID":"112237","HospitalName":"宠物商店3","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123/HospitalImage/112237.jpg"},{"HospitalID":"112238","HospitalName":"宠物商店3","HospitalAddress":"驱蚊器二群翁驱蚊器群二","HospitalTips":"安全 卫生","HospitalDetail":"阒其无人群二无去玩儿","HospitalImageUrl":"http://192.168.113.123"}]
     */

    private String Statute;
    private List<HospitalItemBean> HospitalItem;

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String Statute) {
        this.Statute = Statute;
    }

    public List<HospitalItemBean> getHospitalItem() {
        return HospitalItem;
    }

    public void setHospitalItem(List<HospitalItemBean> HospitalItem) {
        this.HospitalItem = HospitalItem;
    }

    public static class HospitalItemBean {
        /**
         * HospitalID : 112233
         * HospitalName : 宠物商店1
         * HospitalAddress : 驱蚊器二群翁驱蚊器群二
         * HospitalTips : 安全 卫生
         * HospitalDetail : 阒其无人群二无去玩儿
         * HospitalImageUrl : http://192.168.113.123/HospitalImage/112233.jpg
         */

        private String HospitalID;
        private String HospitalName;
        private String HospitalAddress;
        private String HospitalTips;
        private String HospitalDetail;
        private String HospitalImageUrl;

        public String getHospitalID() {
            return HospitalID;
        }

        public void setHospitalID(String HospitalID) {
            this.HospitalID = HospitalID;
        }

        public String getHospitalName() {
            return HospitalName;
        }

        public void setHospitalName(String HospitalName) {
            this.HospitalName = HospitalName;
        }

        public String getHospitalAddress() {
            return HospitalAddress;
        }

        public void setHospitalAddress(String HospitalAddress) {
            this.HospitalAddress = HospitalAddress;
        }

        public String getHospitalTips() {
            return HospitalTips;
        }

        public void setHospitalTips(String HospitalTips) {
            this.HospitalTips = HospitalTips;
        }

        public String getHospitalDetail() {
            return HospitalDetail;
        }

        public void setHospitalDetail(String HospitalDetail) {
            this.HospitalDetail = HospitalDetail;
        }

        public String getHospitalImageUrl() {
            return HospitalImageUrl;
        }

        public void setHospitalImageUrl(String HospitalImageUrl) {
            this.HospitalImageUrl = HospitalImageUrl;
        }
    }
}
