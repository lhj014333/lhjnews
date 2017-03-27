package com.yulu.lhjnews.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.adapter.MovieAdapter;
import com.yulu.lhjnews.bean.NewsInfo;
import com.yulu.lhjnews.utils.JsonUtils;

import java.util.LinkedList;
import java.util.List;


public class MovieFragment extends Fragment {

    private PullToRefreshListView lv;
    private MovieAdapter movieAdapter;
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    LinkedList<NewsInfo> list = new LinkedList<NewsInfo>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie,null);
        lv = (PullToRefreshListView) view.findViewById(R.id.ptfs);
        setUpAdapter();
        setUpDate();
        return view;
    }

    private void setUpDate() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                list= (LinkedList<NewsInfo>) JsonUtils.getInstance().getListData(response, getActivity());
                movieAdapter.setDataList(list);
                movieAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



    }

    private void setUpAdapter() {
        movieAdapter = new MovieAdapter(getActivity());
        lv.setAdapter(movieAdapter);

    }
}
