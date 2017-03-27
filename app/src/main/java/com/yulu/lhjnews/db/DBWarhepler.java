package com.yulu.lhjnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/18.
 */

public class DBWarhepler extends SQLiteOpenHelper {
    public DBWarhepler(Context context) {
        super(context, "warnews.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table warnews (id  integer primary key autoincrement, summary ,  icon,  stamp,  title,  nid,  link,  type)";
        String sql1 = "create table favorite (id  integer primary key autoincrement, summary ,  icon,  stamp,  title,  nid,  link,  type)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
