package com.yulu.lhjnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yulu.lhjnews.bean.NewsInfo;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/3/17.
 */

public class DBManeger {
    static DBManeger dbManeger;
    DBhepler dBhepler;
    private DBManeger(Context context){
        dBhepler=new DBhepler(context);
    }
    public static DBManeger getInstance(Context context){
        if(dbManeger==null){
            dbManeger=new DBManeger(context);
        }
        return dbManeger;
    }


    //添加
    public void insert(NewsInfo newinfo){
        SQLiteDatabase writableDatabase = dBhepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("summary", newinfo.getSummary());
        contentValues.put("icon", newinfo.getIcon());
        contentValues.put("stamp", newinfo.getStamp());
        contentValues.put("title", newinfo.getTitle());
        contentValues.put("nid", newinfo.getNid());
        contentValues.put("link", newinfo.getLink());
        contentValues.put("type", newinfo.getType());
        writableDatabase.insert("news",null,contentValues);


    }
    //查找
    public List<NewsInfo> select(){
        List<NewsInfo> list = new ArrayList<>();

        SQLiteDatabase readableDatabase = dBhepler.getReadableDatabase();
        String sql="select * from news";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do {
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int nid = cursor.getInt(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                NewsInfo news = new NewsInfo(summary, icon, stamp, title, nid, link, type);
                list.add(news);



            }while (cursor.moveToNext());

        }
        return list;


    }


    //改
    public void update(NewsInfo newinfo){
        SQLiteDatabase writableDatabase = dBhepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("summary", newinfo.getSummary());
        contentValues.put("icon", newinfo.getIcon());
        contentValues.put("stamp", newinfo.getStamp());
        contentValues.put("title", newinfo.getTitle());
        contentValues.put("nid", newinfo.getNid());
        contentValues.put("link", newinfo.getLink());
        contentValues.put("type", newinfo.getType());

        String[] strings = {String.valueOf(newinfo.getNid())};
        writableDatabase.update("news",contentValues,"nid=?",strings);


    }
    //删除

    public  void delete(NewsInfo newinfo){
        SQLiteDatabase writableDatabase = dBhepler.getWritableDatabase();

        String[] s = new String[] {String.valueOf(newinfo.getNid())};
        writableDatabase.delete("news","nid=?",s);


    }

    //获取数据库中是否有值
    public long getCount(){
        SQLiteDatabase readableDatabase = dBhepler.getReadableDatabase();
        String sql="select count(*) from news";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            long aLong = cursor.getLong(0);
            cursor.close();
            return aLong;

        }


        return 0;
    }



}
