package com.tcl.openmind.data.zhihu;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuDaily {

    private String mData;

    private ArrayList<ZhihuDailyItem> mZhihuDailyItems;

    private ArrayList<ZhihuDailyItem> stories;

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        this.mData = data;
    }

    public ArrayList<ZhihuDailyItem> getZhihuDailyItems() {
        return mZhihuDailyItems;
    }

    public void setZhihuDailyItems(ArrayList<ZhihuDailyItem> zhihuDailyItems) {
        this.mZhihuDailyItems = zhihuDailyItems;
    }

    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }
}
