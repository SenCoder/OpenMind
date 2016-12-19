package com.tcl.openmind.data.zhihu;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuDaily {

    private String mData;

    private ArrayList<ZhihuDailyItem> mDailyItems;

    private ArrayList<ZhihuDailyItem> stories;

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        this.mData = data;
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
