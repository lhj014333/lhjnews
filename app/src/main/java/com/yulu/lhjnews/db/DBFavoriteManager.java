package com.yulu.lhjnews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yulu.lhjnews.bean.FavoriteNewsInfo;
import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.fragment.WarFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class DBFavoriteManager {
    static DBFavoriteManager dbFavoriteManager;
    DBWarhepler dbWarhepler;

    private DBFavoriteManager(Context context) {
        dbWarhepler = new DBWarhepler(context);
    }

    public static DBFavoriteManager getInstance(Context context) {
        if (dbFavoriteManager == null) {
            dbFavoriteManager = new DBFavoriteManager(context);
        }
        return dbFavoriteManager;
    }

    //添加
    public WarNewsInfo insert(WarNewsInfo favoriteNewsInfo) {
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("summary", favoriteNewsInfo.getSummary());
        values.put("icon", favoriteNewsInfo.getIcon());
        values.put("stamp", favoriteNewsInfo.getStamp());
        values.put("title", favoriteNewsInfo.getTitle());
        values.put("nid", favoriteNewsInfo.getNid());
        values.put("link", favoriteNewsInfo.getLink());
        values.put("type", favoriteNewsInfo.getType());
        writableDatabase.insert("favorite", null, values);

        return favoriteNewsInfo;
    }

    //删除
    public void delect(int nid) {
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        String sql = "delete from favorite where nid=" + nid;
        Cursor cursor = writableDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {


            } while (cursor.moveToNext());
        }


    }

    //查找
    public List<WarNewsInfo> select() {
        List<WarNewsInfo> list = new ArrayList<WarNewsInfo>();
        SQLiteDatabase readableDatabase = dbWarhepler.getReadableDatabase();
        String sql = "select * from favorite";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {

                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int nid = cursor.getInt(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                WarNewsInfo warNewsInfo = new WarNewsInfo(summary, icon, stamp, title, nid, link, type);
                list.add(warNewsInfo);

            } while (cursor.moveToNext());
        }
        return list;
    }

    //修改
    public void update(WarNewsInfo warnewsInfo) {
        SQLiteDatabase writableDatabase = dbWarhepler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("summary", warnewsInfo.getSummary());
        values.put("icon", warnewsInfo.getIcon());
        values.put("stamp", warnewsInfo.getStamp());
        values.put("title", warnewsInfo.getTitle());
        values.put("nid", warnewsInfo.getNid());
        values.put("link", warnewsInfo.getLink());
        values.put("type", warnewsInfo.getType());
        String[] strings = {String.valueOf(warnewsInfo.getNid())};
        writableDatabase.update("favorite", values, null, strings);


    }

    //获取数据库是否有值
    public long count() {
        SQLiteDatabase readableDatabase = dbWarhepler.getReadableDatabase();
        String sql = "select count (*) from favorite";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            long aLong = cursor.getLong(0);
            cursor.close();
            return aLong;
        }
        return 0;
    }

}