package com.chanshiguan.yumeng;

import java.util.List;

public class FriendBean {

    /**
     * Statute : success
     * FriendsItem : [{"FriendId":"1234","FriendName":"哈士奇","FriendDetail":"拆家小能手","FriendHeadUrl":"/HeadImage/1234.jpg"},{"FriendId":"1111","FriendName":"金毛","FriendDetail":"温馨暖男","FriendHeadUrl":"/HeadImage/1111.jpg"},{"FriendId":"2222","FriendName":"泰迪","FriendDetail":"舞动的青春","FriendHeadUrl":""},{"FriendId":"3333","FriendName":"拉布拉多","FriendDetail":"哈哈旺旺","FriendHeadUrl":""},{"FriendId":"4444","FriendName":"中华田园犬","FriendDetail":"萌-----的呛不住","FriendHeadUrl":"/HeadImage/4444.jpg"}]
     */

    private String Statute;
    private List<FriendsItemBean> FriendsItem;

    public String getStatute() {
        return Statute;
    }

    public void setStatute(String Statute) {
        this.Statute = Statute;
    }

    public List<FriendsItemBean> getFriendsItem() {
        return FriendsItem;
    }

    public void setFriendsItem(List<FriendsItemBean> FriendsItem) {
        this.FriendsItem = FriendsItem;
    }

    public static class FriendsItemBean {
        /**
         * FriendId : 1234
         * FriendName : 哈士奇
         * FriendDetail : 拆家小能手
         * FriendHeadUrl : /HeadImage/1234.jpg
         */

        private String FriendId;
        private String FriendName;
        private String FriendDetail;
        private String FriendHeadUrl;

        public String getFriendId() {
            return FriendId;
        }

        public void setFriendId(String FriendId) {
            this.FriendId = FriendId;
        }

        public String getFriendName() {
            return FriendName;
        }

        public void setFriendName(String FriendName) {
            this.FriendName = FriendName;
        }

        public String getFriendDetail() {
            return FriendDetail;
        }

        public void setFriendDetail(String FriendDetail) {
            this.FriendDetail = FriendDetail;
        }

        public String getFriendHeadUrl() {
            return FriendHeadUrl;
        }

        public void setFriendHeadUrl(String FriendHeadUrl) {
            this.FriendHeadUrl = FriendHeadUrl;
        }
    }
}
