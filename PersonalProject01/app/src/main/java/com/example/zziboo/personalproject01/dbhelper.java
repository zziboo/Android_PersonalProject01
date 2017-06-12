package com.example.zziboo.personalproject01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by zziboo on 2017-05-12.
 */

public class dbhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "URL.db";

    public dbhelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table urltable" + "(title text primary key, url text, day text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS urltable");
        onCreate(db);
    }

    public boolean insertURL(String title, String url, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", title);
        contentValues.put("url", url);
        contentValues.put("day", day);

        long affected=db.insert("urltable", null, contentValues);
        return affected>0;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from urltable", null);
        return res;
    }

    public int numberofRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "urltable");
        return numRows;
    }

    public Integer deleteUrl(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("urltable", "title = ? ", new String[]{title});
    }

    public ArrayList getAllUrl(){
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from urltable", null);
        res.moveToFirst();
        while (res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("title")));
            res.moveToNext();
        }
        return array_list;
    }
}
