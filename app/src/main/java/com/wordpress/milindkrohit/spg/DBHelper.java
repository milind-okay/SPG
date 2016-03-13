package com.wordpress.milindkrohit.spg;

/**
 * Created by milind on 13/3/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MilindZeroCross.db";
    public static final String TABLE_NAME = "payment_gateways";
    public static final String COLUMN_ID = "id";
    public static final String PAY_NAME = "name";
    public static final String IMAGE = "image";
    public static final String BRANDING = "branding";
    public static final String RATEING = "rating";
    public static final String CURRENCIES = "currencies";
    public static final String TRAN = "transaction_fees";
    public static final String FEE = "setup_fee";
    public static final String PAY_STR = "pat_str";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table payment_gateways " +
                        "(id integer primary key, name text, branding integer , rating double, currencies text,setup_fee integer,transaction_fees text)"
        );
        db.execSQL(
                "create table str" +
                        "(sid integer primary key, pay_str text)"
        );
    }
    public boolean insertContact(String name, int id,Double rating,int tfee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("id",id);
        contentValues.put("setup_fee",tfee);
        contentValues.put("rating", rating);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean insertStr(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pat_str", name);


        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS payment_gateways");
        onCreate(db);
    }
    public boolean updateFriendsList(Integer id, String friendlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("friendlist", friendlist);


        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }


    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from payment_gateways where id="+id+"", null );
        return res;
    }
    public Cursor getDataRating(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from payment_gateways ORDER BY rating DESC", null );
        return res;
    }
    public Cursor getDataSetup(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from payment_gateways ORDER BY setup_fee DESC", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }




    public boolean updateScore (Integer id, int first_player_score,int second_player_score,int ties)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_player_score",first_player_score);
        contentValues.put("second_player_score",second_player_score);
        contentValues.put("ties",ties);
        db.update("score", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PAY_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}

