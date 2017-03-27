package com.yulu.lhjnews.utils;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.db.DBWarManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class WarJsonUtils {
    static  WarJsonUtils warJsonUtils;
    private WarJsonUtils(){

    }
    public static WarJsonUtils getInstance(){
        if(warJsonUtils==null){
            warJsonUtils=new WarJsonUtils();
        }
        return warJsonUtils;

    }


    public List<WarNewsInfo> getListDate(String data, FragmentActivity activity){
       List<WarNewsInfo> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject josh = (JSONObject) jsonArray.get(i);
                String summary = josh.getString("summary");
                String icon = josh.getString("icon");
                String stamp = josh.getString("stamp");
                String title = josh.getString("title");
                int nid = josh.getInt("nid");
                String link = josh.getString("link");
                int type = josh.getInt("type");
                WarNewsInfo warNewsInfo = new WarNewsInfo(summary, icon, stamp, title, nid, link, type);
                DBWarManager.getInstance(activity).insert(warNewsInfo);
                list.add(warNewsInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }






}
