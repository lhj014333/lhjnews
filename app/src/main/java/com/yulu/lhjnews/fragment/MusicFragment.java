package com.yulu.lhjnews.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.adapter.MusicAdapter;
import com.yulu.lhjnews.bean.NewsInfo;
import com.yulu.lhjnews.utils.HttpUtils;
import com.yulu.lhjnews.utils.JsonUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MusicFragment extends Fragment {

    private PullToRefreshListView lv;
    private MusicAdapter musicAdapter;
    LinkedList<NewsInfo> list=new LinkedList<NewsInfo>();
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
         if(msg.what==1){
             list= (LinkedList<NewsInfo>) msg.obj;
             musicAdapter.setDataList(list);
             musicAdapter.notifyDataSetChanged();
         }else {
             list= (LinkedList<NewsInfo>) msg.obj;
             musicAdapter.setDataList(list);
             musicAdapter.notifyDataSetChanged();

         }if(msg.what==2){
                list= (LinkedList<NewsInfo>) msg.obj;
                musicAdapter.setDataList(list);
                musicAdapter.notifyDataSetChanged();
            }
            lv.onRefreshComplete();

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, null);
        lv = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        setUpAdapter();
        setUpData();
       pulltoRefresh();
        return view;
    }

    private void pulltoRefresh() {
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                lv.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                  setUpDa1();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                lv.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                setUpDa2();
            }
        });
    }



    private void setUpData() {
       new Thread(new Runnable() {
           @Override
           public void run() {

               String data = HttpUtils.getInstance().httpGet(url);
               list=JsonUtils.getInstance().getListJson(data,getActivity());
               Message msg = Message.obtain();
               msg.obj=list;
               handler.sendMessage(msg);
           }

       }).start();

    }
    private void setUpDa1() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String data = HttpUtils.getInstance().httpGet(url);
                LinkedList<NewsInfo> listJson = JsonUtils.getInstance().getListJson(data, getActivity());

                for(int i=0;i<listJson.size();i++){
                    list.addFirst(listJson.get(i));
                }
                Message msg = Message.obtain();
                msg.what=1;
                msg.obj=list;
                handler.sendMessage(msg);
            }

        }).start();

    }
    private void setUpDa2() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String data = HttpUtils.getInstance().httpGet(url);
                LinkedList<NewsInfo> listJson = JsonUtils.getInstance().getListJson(data, getActivity());

                for(int i=0;i<listJson.size();i++){
                    list.addLast(listJson.get(i));
                }
                Message msg = Message.obtain();
                msg.what=2;
                msg.obj=list;
                handler.sendMessage(msg);
            }

        }).start();

    }

    private void setUpAdapter() {
        musicAdapter = new MusicAdapter(getActivity());
        lv.setAdapter(musicAdapter);

    }
}
