package com.chanshiguan.yumeng;


public class People {
    public int ID = -1;
    public String Name;
    public int Count;
    public float Money;
    public String Type;
    public String Shopname;
    @Override
    public String toString(){
        String result = "";
        result += "ID：" + this.ID + "，";
        result += "姓名：" + this.Name + "，";
        result += "数量：" + this.Count + "， ";
        result += "价格：" + this.Money + "，";
        result += "种类：" + this.Type + "，";
        result += "商店名：" + this.Shopname + "，";
        return result;
    }
}