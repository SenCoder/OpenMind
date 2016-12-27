package com.tcl.openmind.data.netease;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by yuansheng on 25/12/2016.
 */

public class NewsListBean {

    @SerializedName("T1348647909107")
    ArrayList<NeteaseNews> newsList;
    public ArrayList<NeteaseNews> getNewsList() {
        return newsList;
    }
    public void setNewsList(ArrayList<NeteaseNews> newsList) {
        this.newsList = newsList;
    }
}
