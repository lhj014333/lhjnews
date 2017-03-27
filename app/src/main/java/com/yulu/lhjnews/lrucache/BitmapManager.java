package com.yulu.lhjnews.lrucache;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BitmapManager {
    static BitmapManager bitmapManager;
    BitmapCache cache;
    private BitmapManager(){

    }
    public static BitmapManager getInstance(){
        if(bitmapManager==null){
            bitmapManager=new BitmapManager();
        }
        return bitmapManager;
    }


    public BitmapCache getCache(){
        int size= (int) (Runtime.getRuntime().maxMemory()/8);

        if(cache==null){
            cache = new BitmapCache(size);
        }
        return cache;


    }

    public Bitmap getBitmap(String key){
        Bitmap bitmap =getCache().get(key);
        if(bitmap!=null){
            return bitmap;
        }else {
            Map<String, SoftReference<Bitmap>> map = getCache().getSoftReferenceMap();
            SoftReference<Bitmap> softReference = map.get(key);
            if(softReference!=null){
                Bitmap bitmap1 = softReference.get();
                return bitmap1;

            }else {
                map.remove(key);
                return null;
            }
        }



    }


}
