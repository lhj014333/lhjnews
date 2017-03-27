package com.yulu.lhjnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yulu.lhjnews.bean.WarNewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class DBWarManager {
    static DBWarManager dbWarManager;
    DBWarhepler dbWarhepler;

    private DBWarManager(Context context){
          dbWarhepler=new DBWarhepler(context);
    }
    public static DBWarManager getInstance(Context context){
        if(dbWarManager==null){
            dbWarManager=new DBWarManager(context);
        }
        return dbWarManager;
    }
    // 查找nid返回对象
    public List<WarNewsInfo> selectNid(int id){
        List<WarNewsInfo> list = new ArrayList<WarNewsInfo>();
        SQLiteDatabase readableDatabase = dbWarhepler.getReadableDatabase();
        String sql="select * from warnews where nid="+id;
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
                WarNewsInfo warNewsInfo = new WarNewsInfo( summary, icon, stamp, title, nid, link, type);
                list.add(warNewsInfo);

            }while (cursor.moveToNext());
        }
        return list;
    }

    //添加
    public void insert(WarNewsInfo warNewsInfo){
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("summary",warNewsInfo.getSummary());
        values.put("icon",warNewsInfo.getIcon());
        values.put("stamp", warNewsInfo.getStamp());
        values.put("title",warNewsInfo.getTitle());
        values.put("nid",warNewsInfo.getNid());
        values.put("link",warNewsInfo.getLink());
        values.put("type",warNewsInfo.getType());
        writableDatabase.insert("warnews",null,values);
    }
    //删除
    public void delete(WarNewsInfo warnewsinfo){
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        String[] strings = {String.valueOf(warnewsinfo.getNid())};
        writableDatabase.delete("warnews","nid=?",strings);


    }
    // 查找
    public List<WarNewsInfo> select(){
        List<WarNewsInfo> list = new ArrayList<>();
        SQLiteDatabase readableDatabase = dbWarhepler.getReadableDatabase();
        String sql="select * from warnews";
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
                WarNewsInfo warNewsInfo = new WarNewsInfo( summary, icon, stamp, title, nid, link, type);
                list.add(warNewsInfo);

            }while (cursor.moveToNext());

        }


        return list;
    }
    //修改
    public void update(WarNewsInfo warNewsInfo){
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("summary",warNewsInfo.getSummary());
        values.put("icon",warNewsInfo.getSummary());
        values.put("stamp",warNewsInfo.getSummary());
        values.put("title",warNewsInfo.getSummary());
        values.put("nid",warNewsInfo.getSummary());
        values.put("link",warNewsInfo.getSummary());
        values.put("type",warNewsInfo.getSummary());
        String[] strings = {String.valueOf(warNewsInfo.getNid())};
        writableDatabase.update("warnews",values,"nis=?",strings);


    }
    //获取数据库是否有值
    public long getCount(){
        SQLiteDatabase readableDatabase = dbWarhepler.getReadableDatabase();
        String sql="select count (*)from warnews";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            long aLong = cursor.getLong(0);
            cursor.close();
            return aLong;
        }
        return 0;
    }





}
