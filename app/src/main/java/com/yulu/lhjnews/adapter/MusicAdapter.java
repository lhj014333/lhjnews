package com.yulu.lhjnews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yulu.lhjnews.R;
import com.yulu.lhjnews.bean.NewsInfo;
import com.yulu.lhjnews.listener.BitmapListener;
import com.yulu.lhjnews.lrucache.BitmapManager;
import com.yulu.lhjnews.utils.BitmapAsyTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class MusicAdapter extends BaseAdapter{
   LinkedList<NewsInfo> list=new LinkedList<NewsInfo>();
    Context context;
    public MusicAdapter(FragmentActivity activity) {
        this.context=activity;
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
    ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            holder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.society_item,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.society_icon);
            holder.title= (TextView) convertView.findViewById(R.id.society_title);
            holder.matter= (TextView) convertView.findViewById(R.id.society_matter);
            holder.time= (TextView) convertView.findViewById(R.id.society_time);
            convertView.setTag(holder);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.matter.setText(list.get(position).getSummary());
        holder.time.setText(list.get(position).getStamp());
        Bitmap bitmap = BitmapManager.getInstance().getBitmap(list.get(position).getIcon());
        if(bitmap==null){
            new BitmapAsyTask(new BitmapListener() {
                @Override
                public void getBitmap(Bitmap bitmap) {
                    holder.icon.setImageBitmap(bitmap);
                }
            }).execute(list.get(position).getIcon());
        }else{
            holder.icon.setImageBitmap(bitmap);
        }

        return convertView;
    }
    class ViewHolder{
        ImageView icon;
        TextView title;
        TextView matter;
        TextView time;
    }

    public void setDataList(LinkedList<NewsInfo> dataList) {
        this.list = dataList;
    }
}
