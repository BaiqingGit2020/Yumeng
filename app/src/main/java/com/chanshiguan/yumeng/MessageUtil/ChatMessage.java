package com.chanshiguan.yumeng.MessageUtil;

public class ChatMessage {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private String SendID;
    private String GetID;
    private int type;
    public ChatMessage(String sendID,String getID,String content, int type) {
        this.content = content;
        this.type = type;
        this.SendID=sendID;
        this.GetID=getID;
    }
    public String getContent() {
        return content;
    }
    public int getType() {
        return type;
    }
    public String getSendID(){return SendID;}
    public String getGetID(){return GetID;}
}
