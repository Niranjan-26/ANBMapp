package com.example.anbmapp.SQlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavDB extends SQLiteOpenHelper {
    private  static  int DB_VERSION = 1;
    private static String DATABASE_NAME = "FavBandsDB";
    private static String TABLE_NAME = "favoriteTable";
//    public static String KEY_ID = "id";
    public static String ITEM_BAND_NAME = "fav_Band_Name";
    public static String ITEM_BAND_GENRE = "fav_Band_Genre";
    public static String ITEM_BAND_BASED_CITY = "fav_Band_Based_City";
    public static String ITEM_BAND_EMAIL = "fav_Band_Email";
    public static String FAVORITE_STATUS = "fav_Status";

    //dont forget to write this spaces
    private  static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ITEM_BAND_NAME+ " TEXT,"
            + ITEM_BAND_GENRE + " TEXT," + ITEM_BAND_BASED_CITY+" TEXT,"
            + ITEM_BAND_EMAIL + " TEXT," + FAVORITE_STATUS+" TEXT)";

    public FavDB(Context context){ super(context,DATABASE_NAME, null,DB_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //create empty table
    public void insertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //enter the values
        for(int x = 1; x < 11; x++){
//            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS, "0");

            db.insert(TABLE_NAME, null, cv);
        }
    }

    public  void insertIntoTheDatabase(String Band_Name, String Genre, String Based_City, String Email, String FavStatus){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_BAND_NAME, Band_Name);
        cv.put(ITEM_BAND_GENRE, Genre);
        cv.put(ITEM_BAND_BASED_CITY, Based_City);
        cv.put(ITEM_BAND_EMAIL, Email);
        cv.put(FAVORITE_STATUS, FavStatus);
        db.insert(TABLE_NAME, null, cv);
        Log.d("FavDB status", Band_Name + ", fav_Status - "+FavStatus+" - . " + cv);

    }
    //reall all data
    public Cursor read_all_data(String fav_Band_Name){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + ITEM_BAND_NAME+"="+fav_Band_Name+"";
        return db.rawQuery(sql, null, null);

    }
    //remove line from database
    public  void remove_fav(String id){
        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "UPDATE " + TABLE_NAME + " SET "+ FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
//        db.execSQL(sql);
        Log.d("remove", id.toString());
    }

    //select all favorite list
    public Cursor select_all_fav_list(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql, null, null);
    }
}
