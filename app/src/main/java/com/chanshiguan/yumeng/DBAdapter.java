package com.chanshiguan.yumeng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    private static final String DB_NAME = "people.db";
    private static final String DB_TABLE = "peopleinfo";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COUNT = "count";
    public static final String KEY_MONEY = "money";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SHOPNAME = "shopname";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context _context) {
        context = _context;
    }

    /** Close the database */
    public void close() {
        if (db != null){
            db.close();
            db = null;
        }
    }

    /** Open the database */
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }


    public long insert(People people) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_NAME, people.Name);
        newValues.put(KEY_COUNT, people.Count);
        newValues.put(KEY_MONEY, people.Money);
        newValues.put(KEY_TYPE, people.Type);
        newValues.put(KEY_SHOPNAME, people.Shopname);

        return db.insert(DB_TABLE, null, newValues);
    }


    public People[] queryAllData() {
        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_COUNT, KEY_MONEY,KEY_TYPE,KEY_SHOPNAME},
                null, null, null, null, null);
        return ConvertToPeople(results);
    }

    public People[] queryOneData(String name) {

        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_COUNT, KEY_MONEY,KEY_TYPE,KEY_SHOPNAME},
                KEY_NAME+" like ? ", new String[]{"%" + name + "%"},null, null, null, null);
        return ConvertToPeople(results);
    }
    public People[] queryOneData2(String name) {

        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_COUNT, KEY_MONEY,KEY_TYPE,KEY_SHOPNAME},
                KEY_TYPE+" like ? ", new String[]{"%" + name + "%"},null, null, null, null);
        return ConvertToPeople(results);
    }
    private People[] ConvertToPeople(Cursor cursor){
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        People[] peoples = new People[resultCounts];
        for (int i = 0 ; i<resultCounts; i++){
            peoples[i] = new People();
            peoples[i].ID = cursor.getInt(0);
            peoples[i].Name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            peoples[i].Count = cursor.getInt(cursor.getColumnIndex(KEY_COUNT));
            peoples[i].Money = cursor.getFloat(cursor.getColumnIndex(KEY_MONEY));
            peoples[i].Type = cursor.getString(cursor.getColumnIndex(KEY_TYPE));
            peoples[i].Shopname = cursor.getString(cursor.getColumnIndex(KEY_SHOPNAME));
            cursor.moveToNext();
        }
        return peoples;
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE,  KEY_ID + "=" + id, null);
    }

    public long updateOneData(long id , People people){
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, people.Name);
        updateValues.put(KEY_COUNT, people.Count);
        updateValues.put(KEY_MONEY, people.Money);
        updateValues.put(KEY_TYPE, people.Type);
        updateValues.put(KEY_SHOPNAME, people.Shopname);
        return db.update(DB_TABLE, updateValues,  KEY_ID + "=" + id, null);
    }

    /** 静态Helper类，用于建立、更新和打开数据库*/
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME+ " text not null, " + KEY_COUNT+ " integer," + KEY_MONEY + " float,"+
                KEY_TYPE+ " text not null, "+KEY_SHOPNAME+ " text not null);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }
}
