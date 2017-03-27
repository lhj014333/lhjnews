package com.yulu.lhjnews.fragment;

;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.activity.WebActivity;
import com.yulu.lhjnews.adapter.WarAdapter;
import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.db.DBWarManager;
import com.yulu.lhjnews.utils.WarJsonUtils;

import java.util.ArrayList;
import java.util.List;


public class WarFragment extends Fragment {
    View view;
    ListView listview;
    WarAdapter warAdapter;
    RequestQueue volley;
    List<WarNewsInfo> list;
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_war, null);
        //初始化控件
        initView();
        //设置适配器
        setUpAdapter();
        //设置数据源
        setUpData();
        return view;
    }

    private void setUpData() {
        if (DBWarManager.getInstance(getActivity()).getCount() == 0) {
            setListData();
        } else {
            list = DBWarManager.getInstance(getActivity()).select();
            warAdapter.setDateList(list);
            warAdapter.notifyDataSetChanged();

        }


    }

    private void setListData() {

        volley = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("2222", response);
                list = WarJsonUtils.getInstance().getListDate(response, getActivity());
                warAdapter.setDateList(list);
                warAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "你已进入没有网络的二次元时代", Toast.LENGTH_LONG).show();
            }
        });

        volley.add(stringRequest);


    }

    private void setUpAdapter() {
        warAdapter = new WarAdapter(getActivity());
        listview.setAdapter(warAdapter);

    }

    private void initView() {
        listview = (ListView) view.findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", list.get(position).getLink());
                intent.putExtra("nid",list.get(position).getNid());
                startActivity(intent);


            }
        });

    }
}
