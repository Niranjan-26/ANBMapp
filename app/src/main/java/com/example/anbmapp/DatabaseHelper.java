package com.example.anbmapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favourite.db";
    public static final String TABLE_NAME = "favband_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BAND_ID";
    public static final String COL_3 = "USER_ID";
    public static final String COL_4 = "IS_FAV";
    public static final String COL_5 = "BAND_NAME";
    public static final String COL_6 = "BAND_PHONE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,BAND_ID INTEGER,USER_ID INTEGER,IS_FAV TEXT,BAND_NAME TEXT,BAND_PHONE INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String BAND_ID,String USER_ID,String IS_FAV,String BAND_NAME,String BAND_PHONE){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,BAND_ID);
        contentValues.put(COL_3,USER_ID);
        contentValues.put(COL_4,IS_FAV);
        contentValues.put(COL_5,BAND_NAME);
        contentValues.put(COL_6,BAND_PHONE);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;

        }
    }
    public Cursor getAllData(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where USER_ID = '" + userid+ "'" , null);
        return res;
    }
    public Integer deleteData (String bandid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "BAND_ID = ?", new String[] {bandid});

    }
    public Cursor getAllFavStatus(String userid, String bandids, String isfav){
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where USER_ID = '" + userid+ "'"+ "AND BAND_ID = '" + bandid+ "'" , null);
        Cursor res = db.rawQuery("select * from favband_table where USER_ID=? and BAND_ID=? and IS_FAV=?",
                new String [] {userid,bandids,isfav});
        return res;
    }
    public Cursor getBandPhone(String bandid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where BAND_ID = '" + bandid+ "'" , null);
        return res;
    }

}
