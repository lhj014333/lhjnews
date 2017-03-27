package com.yulu.lhjnews.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yulu.lhjnews.R;
import com.yulu.lhjnews.adapter.FragmentAdapter;
import com.yulu.lhjnews.fragment.MovieFragment;
import com.yulu.lhjnews.fragment.MusicFragment;
import com.yulu.lhjnews.fragment.PressFragment;
import com.yulu.lhjnews.fragment.SocietyFragment;
import com.yulu.lhjnews.fragment.WarFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    RadioButton society;
    RadioButton war;
    RadioButton music;
    RadioButton press;
    RadioButton movie;
    ViewPager vpager;

    //当前页卡编号
    private int currIndex = 0;

    //动画图片
    private ImageView cursor;

    //动画图片偏移量
    private int offset = 0;
    private int position_one;
    private int position_two;
    //存放Fragment
    List<Fragment> fragmentlist;
    //管理Fragment
    FragmentManager fragmentManager;

    //动画图片宽度
    private int bmpW;
    Context context;
    SlidingMenu slidingMenu;
    private ImageView main_title_home;
    private RelativeLayout news;
    private RelativeLayout favorite;
    private DrawerLayout drawer;
    private RelativeLayout dd;
    private ImageView share;
    private ImageView exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initDrawer();
        initSldes();


        initButton();

        //初始化ImageView
        initImageView();

        //初始化Fragment
        initFragment();

        //初始化ViewPager
        initViewPager();
        //设置监听
        setClick();
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        dd = (RelativeLayout) findViewById(R.id.dddd);
        share = (ImageView) findViewById(R.id.main_title_share);
        exit = (ImageView) findViewById(R.id.exit);
        share.setOnClickListener(this);
        exit.setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = dd.getLayoutParams();
        layoutParams.width=getResources().getDisplayMetrics().widthPixels/3*2;
        //dd.setLayoutParams(layoutParams);
    }

    private void setClick() {
        favorite = (RelativeLayout) findViewById(R.id.favorite);
        news = (RelativeLayout) findViewById(R.id.news);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FavoriteActivity.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSldes() {
        //创建SlidingMenu对象
         slidingMenu = new SlidingMenu(context);
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

    private void initButton() {
        society= (RadioButton) findViewById(R.id.society);
        war    = (RadioButton) findViewById(R.id.war);
        music  = (RadioButton) findViewById(R.id.music);
        press  = (RadioButton) findViewById(R.id.newbutton);
        movie  = (RadioButton) findViewById(R.id.movie);
        main_title_home = (ImageView) findViewById(R.id.main_title_home);
        main_title_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });



        society.setOnClickListener(new MyOnClickListener(0));
        war.setOnClickListener(new MyOnClickListener(1));
        music.setOnClickListener(new MyOnClickListener(2));
        press.setOnClickListener(new MyOnClickListener(3));
        movie.setOnClickListener(new MyOnClickListener(4));
    }

    private void initImageView() {

        cursor= (ImageView) findViewById(R.id.cursor);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 获取分辨率宽度
        int screenW = dm.widthPixels;

        bmpW = (screenW/5);

        //设置动画图片宽度
        setBmpW(cursor,bmpW);
        offset = 0;

        //动画图片偏移量赋值
        position_one = (int) (screenW /5.0);
        position_two = position_one * 2;


    }
    /**
     * 设置动画图片宽度
     * @param mWidth
     */
    private void setBmpW(ImageView imageView,int mWidth){
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    private void initFragment() {

        fragmentlist=new ArrayList<Fragment>();
        fragmentlist.add(new SocietyFragment());
        fragmentlist.add(new WarFragment());
        fragmentlist.add(new MusicFragment());
        fragmentlist.add(new PressFragment());
        fragmentlist.add(new MovieFragment());
        fragmentManager = getSupportFragmentManager();


    }

    private void initViewPager() {
         vpager= (ViewPager) findViewById(R.id.vPager);
        vpager.setAdapter(new FragmentAdapter(fragmentManager, fragmentlist));

        //让ViewPager缓存2个页面
        vpager.setOffscreenPageLimit(2);

        //设置默认打开第一页
        vpager.setCurrentItem(0);

        //将顶部文字恢复默认值
        resetTextViewTextColor();
        society.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));

        //设置viewpager页面滑动监听事件
        vpager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_title_share:
                drawer.openDrawer(dd);
                break;
            case  R.id.exit :
                drawer.closeDrawer(dd);
                break;
        }
    }

    /**
     * 头标点击监听
     * @author weizhi
     * @version 1.0
     */
    public class MyOnClickListener implements View.OnClickListener{
        private int index = 0 ;
        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            vpager.setCurrentItem(index);

        }
    }
    /**
     * 页卡切换监听
     * @author weizhi
     * @version 1.0
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{


        @Override
        public void onPageSelected(int position) {
            Animation animation = null ;
            switch (position){

                //当前为页卡1
                case 0:
                    //从页卡1跳转转到页卡2
                    if(currIndex == 1){
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextViewTextColor();
                        society.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }else if(currIndex == 2){//从页卡1跳转转到页卡3
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        resetTextViewTextColor();
                        society.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;

                //当前为页卡2
                case 1:
                    //从页卡1跳转转到页卡2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextViewTextColor();
                        war.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 2) { //从页卡1跳转转到页卡2
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        resetTextViewTextColor();
                        war.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;

                //当前为页卡3
                case 2:
                    //从页卡1跳转转到页卡2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        resetTextViewTextColor();
                        music.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    } else if (currIndex == 1) {//从页卡1跳转转到页卡2
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        resetTextViewTextColor();
                        music.setTextColor(getResources().getColor(R.color.main_top_tab_color_2));
                    }
                    break;
            }
            currIndex = position;

            animation.setFillAfter(true);// true:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);



        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /**
     * 将顶部文字恢复默认值
     */
    private void resetTextViewTextColor(){

        society.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        war.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        music.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        press.setTextColor(getResources().getColor(R.color.main_top_tab_color));
        movie.setTextColor(getResources().getColor(R.color.main_top_tab_color));
    }
    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }
}
