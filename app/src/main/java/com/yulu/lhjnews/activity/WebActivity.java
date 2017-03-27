package com.yulu.lhjnews.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.bean.WarNewsInfo;
import com.yulu.lhjnews.db.DBFavoriteManager;
import com.yulu.lhjnews.db.DBWarManager;

import java.util.List;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView wb;
    private ProgressBar pb;
    private String url;
    private ImageView webhome;
    private ImageView webfavorite;
    SlidingMenu slidingMenu;
    private RelativeLayout favorite;
    private RelativeLayout news;
    private PopupWindow popup;
    private View view;
    private int size;
    private ImageView share;
    private RelativeLayout web;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        initSldes();
        initView();
        initUpDate();
        setClick();


    }


    private void initSldes() {
        //创建SlidingMenu对象
        slidingMenu = new SlidingMenu(WebActivity.this);
        //设置参数
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置布局
        slidingMenu.setMenu(R.layout.slidingmenu_item);
        //slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置菜单显示宽度
        int width = (getResources().getDisplayMetrics().widthPixels) / 3 * 2;
        slidingMenu.setBehindWidth(width);
        //关联activity
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);


    }

    private void setClick() {
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wb.loadUrl(url);
                return true;
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(view.GONE);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPupopWindow();
            }
        });

    }

    private void showPupopWindow() {
        if (popup == null) {
            view = LayoutInflater.from(this).inflate(R.layout.share_layout_item, null);
            web = (RelativeLayout) findViewById(R.layout.activity_web);
            size = getResources().getDisplayMetrics().widthPixels;
            height = getResources().getDisplayMetrics().heightPixels;
            popup = new PopupWindow(view, size, size / 2);
            //设置焦点在弹窗上
            popup.setFocusable(true);
            //设置在外点击消失
            popup.setOutsideTouchable(true);
            popup.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));


        }
        popup.showAsDropDown(share,0,height/2+100);


    }

    private void initUpDate() {
        url = getIntent().getStringExtra("url");
        Log.e("url0000000", url);
        wb.loadUrl(url);


    }

    private void initView() {
        wb = (WebView) findViewById(R.id.webview);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        webhome = (ImageView) findViewById(R.id.web_home);
        webfavorite = (ImageView) findViewById(R.id.web_favorite);
        favorite = (RelativeLayout) findViewById(R.id.favorite);
        news = (RelativeLayout) findViewById(R.id.news);
        share = (ImageView) findViewById(R.id.web_share);
        webhome.setOnClickListener(this);
        favorite.setOnClickListener(this);
        news.setOnClickListener(this);
        webfavorite.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_home:
                slidingMenu.toggle();
                break;
            case R.id.web_favorite:
                int nid = getIntent().getIntExtra("nid", -1);
                int j = 0;
                List<WarNewsInfo> warNewsInfos = DBWarManager.getInstance(this).selectNid(nid);
                if (DBFavoriteManager.getInstance(this).count() > 0) {
                    List<WarNewsInfo> select = DBFavoriteManager.getInstance(this).select();

                    for (int i = 0; i < select.size(); i++) {
                        if (nid == select.get(i).getNid()) {
                            Toast.makeText(this, "资源已收藏", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    DBFavoriteManager.getInstance(this).insert(warNewsInfos.get(j));
                    Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();


                } else {
                    for (j = 0; j < warNewsInfos.size(); j++) {
                        DBFavoriteManager.getInstance(this).insert(warNewsInfos.get(j));
                        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
                    }


                }


                break;
            case R.id.favorite:
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.news:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;


        }
    }
}
