package com.chanshiguan.yumeng;

public class goods {


    private String goodsname;
    private String shopname1;
    private String money;
    private String type;

    private int imageId;
    public goods(String goodsname, int imageId, String money, String shopname1) {
        this.goodsname = goodsname;
        this.money = money;
        this.shopname1 = shopname1;
        this.imageId = imageId;
    }

    public String getName() {
        return goodsname;
    }
    public int getImageId() {
        return imageId;
    }
    public String getShopname1() {
        return shopname1;
    }
    public String getmoney() {
        return money;
    }
    public String gettype() {
        return type;
    }
}
