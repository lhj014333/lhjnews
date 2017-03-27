package com.yulu.lhjnews.bean;

/**
 * Created by Administrator on 2017/3/21.
 */

public class FavoriteNewsInfo {

    private String summary ;
    private String icon ;
    private String stamp ;
    private String title ;
    private int nid ;
    private String link ;
    private int type ;

    public FavoriteNewsInfo( String summary, String icon, String stamp, String title, int nid, String link, int type) {

        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
        this.type = type;
    }



    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public String getStamp() {
        return stamp;
    }

    public String getTitle() {
        return title;
    }

    public int getNid() {
        return nid;
    }

    public String getLink() {
        return link;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "FavoriteNewsInfo{" +
                "summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", stamp='" + stamp + '\'' +
                ", title='" + title + '\'' +
                ", nid=" + nid +
                ", link='" + link + '\'' +
                ", type=" + type +
                '}';
    }
}
