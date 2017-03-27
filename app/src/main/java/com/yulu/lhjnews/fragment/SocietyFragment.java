package com.yulu.lhjnews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yulu.lhjnews.R;
import com.yulu.lhjnews.activity.MainActivity;
import com.yulu.lhjnews.activity.WebActivity;
import com.yulu.lhjnews.adapter.SocietyAdapter;
import com.yulu.lhjnews.bean.NewsInfo;
import com.yulu.lhjnews.db.DBManeger;
import com.yulu.lhjnews.utils.HttpUtils;
import com.yulu.lhjnews.utils.JsonUtils;

import java.util.List;


public class SocietyFragment extends Fragment {
    View view;
    ListView listview;
    SocietyAdapter societyAdapter;
    List<NewsInfo> list;
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                Toast.makeText(getActivity(), "你已进入没有网络的二次元时代", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                list = (List<NewsInfo>) msg.obj;

                societyAdapter.setDataList(list);
                societyAdapter.notifyDataSetChanged();

            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_society, null);
        //初始化数据
        initView();


        //设置适配器
        setUpAdapter();
        //设置数据源
      setUpData();

        return view;
    }

    private void setUpData() {
        if(DBManeger.getInstance(getActivity()).getCount()==0){
            setListData();

        }else{
            List<NewsInfo> select = DBManeger.getInstance(getActivity()).select();
            societyAdapter.setDataList(select);
            societyAdapter.notifyDataSetChanged();
        }


    }

    public void setListData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = HttpUtils.getInstance().httpGet(url);
                if (data != null) {
                    list = JsonUtils.getInstance().getListData(data,getActivity());


                    Message msg = Message.obtain();
                    msg.what = 2;
                    msg.obj = list;
                    handler.sendMessage(msg);
                } else {
                    handler.sendEmptyMessage(1);

                }


            }
        }).start();

    }

    private void setUpAdapter() {
        societyAdapter = new SocietyAdapter(getActivity());
        listview.setAdapter(societyAdapter);
    }

    private void initView() {
        listview = (ListView) view.findViewById(R.id.society_listview);
      /*  listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",list.get(position).getLink());
                startActivity(intent);
            }
        });*/

    }
}
