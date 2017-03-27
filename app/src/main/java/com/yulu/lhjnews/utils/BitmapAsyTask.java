package com.yulu.lhjnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.yulu.lhjnews.listener.BitmapListener;
import com.yulu.lhjnews.lrucache.BitmapManager;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BitmapAsyTask extends AsyncTask<String,Void,Bitmap> {

    BitmapListener bitmaplistener;
    public BitmapAsyTask(BitmapListener bitmaplistener){
        this.bitmaplistener=bitmaplistener;

    }



    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap=null;
        try {
            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
             bitmap = BitmapFactory.decodeStream(inputStream);
            BitmapManager.getInstance().getCache().put(strings[0],bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        bitmaplistener.getBitmap(bitmap);
    }
}
