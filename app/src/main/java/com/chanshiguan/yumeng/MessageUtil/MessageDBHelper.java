package com.chanshiguan.yumeng.MessageUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MessageDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private String DB_NAME = "ChatMessage";
    public static final String TABLE_NAME = "Orders";

    public MessageDBHelper(Context context,String name) {
        super(context, name, null, DB_VERSION);
        this.DB_NAME=name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table Orders(Id integer primary key, CustomName text, OrderPrice integer, Country text);
        String sql = "create table if not exists " + DB_NAME + " (MessageID integer primary key autoincrement,GetID text , SendID text, Content text, Type integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}
