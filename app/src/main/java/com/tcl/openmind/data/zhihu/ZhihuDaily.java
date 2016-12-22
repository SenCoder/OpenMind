package com.tcl.openmind.data.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Java bean.
 */

public class ZhihuDaily {

    @SerializedName("date")
    private String date;

    @SerializedName("top_stories")
    private ArrayList<ZhihuDailyItem> mDailyItems;

    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getDailyItems() {
        return mDailyItems;
    }

    public void setDailyItems(ArrayList<ZhihuDailyItem> dailyItems) {
        this.mDailyItems = dailyItems;
    }

    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }
}
