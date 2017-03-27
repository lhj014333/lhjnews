package com.yulu.lhjnews.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.fragment.WarFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class WarAdapter extends BaseAdapter {
    List<WarNewsInfo> list = new ArrayList<WarNewsInfo>();
    Context context;
    ViewHolder holder;

    public WarAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.war_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.war_icon);
            holder.title = (TextView) convertView.findViewById(R.id.war_title);
            holder.matter = (TextView) convertView.findViewById(R.id.war_matter);
            holder.time = (TextView) convertView.findViewById(R.id.war_time);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.matter.setText(list.get(position).getSummary());
        holder.time.setText(list.get(position).getStamp());
        imageDate();


        return convertView;
    }


    class ViewHolder {
        ImageView icon;
        TextView title, matter, time;
    }

    public void setDateList(List<WarNewsInfo> list) {
        this.list = list;
    }


    public void imageDate() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, MyCache.getInstence());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(holder.icon, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        for (int i = 0; i < list.size(); i++) {
            imageLoader.get(list.get(i).getIcon(), imageListener, 80, 80);
        }
    }

    static class MyCache implements ImageLoader.ImageCache {

        static MyCache mycache;
        LruCache<String, Bitmap> lruCache;

        private MyCache() {
            int size = (int) Runtime.getRuntime().maxMemory();
            lruCache = new LruCache<String, Bitmap>(size / 8) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }

            };
        }

        public static MyCache getInstence() {
            if (mycache == null) {
                mycache = new MyCache();
            }
            return mycache;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return lruCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            lruCache.put(url, bitmap);
        }
    }
}
