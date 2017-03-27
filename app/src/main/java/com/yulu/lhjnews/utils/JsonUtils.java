package com.yulu.lhjnews.utils;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.yulu.lhjnews.bean.NewsInfo;
import com.yulu.lhjnews.db.DBManeger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class JsonUtils{
    static JsonUtils jsonUtils;
    private JsonUtils(){

    }
    public static JsonUtils getInstance(){
        if(jsonUtils==null){
            jsonUtils=new JsonUtils();
        }
        return jsonUtils;
    }


    public List<NewsInfo> getListData(String data, FragmentActivity activity){
        List<NewsInfo> list = new ArrayList<NewsInfo>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++){

                JSONObject json= (JSONObject) jsonArray.get(i);

                String summary = json.getString("summary");
                String icon = json.getString("icon");
                String stamp = json.getString("stamp");
                String title = json.getString("title");
                int nid = json.getInt("nid");
                String link = json.getString("link");
                int type = json.getInt("type");
                NewsInfo newsInfo = new NewsInfo(summary, icon, stamp, title, nid, link, type);

                DBManeger.getInstance(activity).insert(newsInfo);

                 list.add(newsInfo);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;


    }
    public LinkedList<NewsInfo> getListJson(String data, FragmentActivity activity){
        LinkedList<NewsInfo> list = new LinkedList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++){

                JSONObject json= (JSONObject) jsonArray.get(i);

                String summary = json.getString("summary");
                String icon = json.getString("icon");
                String stamp = json.getString("stamp");
                String title = json.getString("title");
                int nid = json.getInt("nid");
                String link = json.getString("link");
                int type = json.getInt("type");
                NewsInfo newsInfo = new NewsInfo(summary, icon, stamp, title, nid, link, type);


                list.add(newsInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



}
