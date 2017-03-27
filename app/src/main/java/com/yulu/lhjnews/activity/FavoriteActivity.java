package com.yulu.lhjnews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.adapter.FavoriteAdapter;
import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.db.DBFavoriteManager;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ImageView home;
    private ImageView share;
    private ListView listView;
    List<WarNewsInfo> list=new ArrayList<WarNewsInfo>();
    private FavoriteAdapter favoriteAdapter;
    SlidingMenu slidingMenu;
    private RelativeLayout favorite;
    private RelativeLayout news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initSldes();
        initView();
        setUpAdapter();
        setUpDate();
        setClick();
    }

    private void setClick() {
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               DBFavoriteManager.getInstance(FavoriteActivity.this).delect(list.get(position).getNid());
                setUpDate();
               Toast.makeText(FavoriteActivity.this,"已删除",Toast.LENGTH_SHORT).show();

               return false;

           }
       });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this,FavoriteActivity.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initSldes() {
        //创建SlidingMenu对象
        slidingMenu = new SlidingMenu(FavoriteActivity.this);
        //设置参数
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置布局
        slidingMenu.setMenu(R.layout.slidingmenu_item);
        //slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置菜单显示宽度
        int width=(getResources().getDisplayMetrics().widthPixels)/3*2;
        slidingMenu.setBehindWidth(width);
        //关联activity
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);


    }

    private void setUpDate() {
        list= DBFavoriteManager.getInstance(this).select();
        favoriteAdapter.setDateList(list);
        favoriteAdapter.notifyDataSetChanged();

    }

    private void setUpAdapter() {
        favoriteAdapter = new FavoriteAdapter(this);
        listView.setAdapter(favoriteAdapter);


    }

    private void initView() {
        home = (ImageView) findViewById(R.id.favorite_home);
        share = (ImageView) findViewById(R.id.favorite_share);
        listView = (ListView) findViewById(R.id.listview);
        favorite = (RelativeLayout) findViewById(R.id.favorite);
        news = (RelativeLayout) findViewById(R.id.news);
    }



}
