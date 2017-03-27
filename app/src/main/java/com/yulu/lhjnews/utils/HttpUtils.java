package com.yulu.lhjnews.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/17.
 */

public class HttpUtils {

    static HttpUtils httpUtils;
    private HttpUtils(){

    }
    public static HttpUtils getInstance(){
        if(httpUtils==null){
            httpUtils=new HttpUtils();
        }
        return httpUtils;
    }


    public String httpGet(String url)  {
        String data=null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse execute = httpClient.execute(httpGet);
            HttpEntity entity = execute.getEntity();
             data = EntityUtils.toString(entity);
            Log.e("data000000",data);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return data;
    }


}
