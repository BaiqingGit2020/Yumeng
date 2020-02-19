package com.chanshiguan.yumeng.Bean;

import java.util.List;

//通过GsonFormat生成的Foodbean类
public class FoodBean {

    /**
     * status : 0
     * message : picture
     * item : [{"ItemPictureUrl":"http://192.168.123.113/Food/1.jpg","ItemName":"狗粮","ItemPrice":"25","id":"1","ItemNum":"20"},{"ItemPictureUrl":"http://192.168.123.113/Food/2.jpg","ItemName":"猫粮","ItemPrice":"02","id":"2","ItemNum":"30"}]
     */

    private String status;
    private String message;
    private List<ItemBean> item;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * ItemPictureUrl : http://192.168.123.113/Food/1.jpg
         * ItemName : 狗粮
         * ItemPrice : 25
         * id : 1
         * ItemNum : 20
         */

        private String ItemPictureUrl;
        private String ItemName;
        private String ItemPrice;
        private String id;
        private String ItemNum;

        public String getItemPictureUrl() {
            return ItemPictureUrl;
        }

        public void setItemPictureUrl(String ItemPictureUrl) {
            this.ItemPictureUrl = ItemPictureUrl;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getItemPrice() {
            return ItemPrice;
        }

        public void setItemPrice(String ItemPrice) {
            this.ItemPrice = ItemPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemNum() {
            return ItemNum;
        }

        public void setItemNum(String ItemNum) {
            this.ItemNum = ItemNum;
        }
    }
}
