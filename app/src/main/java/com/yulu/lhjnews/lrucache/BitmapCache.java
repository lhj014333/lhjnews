package com.yulu.lhjnews.lrucache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BitmapCache extends LruCache<String,Bitmap> {
    Map<String,SoftReference<Bitmap>> map=new HashMap<String,SoftReference<Bitmap>>();

    public BitmapCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(oldValue);
        map.put(key,softReference);


    }


    public Map<String, SoftReference<Bitmap>> getSoftReferenceMap() {
        return map;
    }
}
