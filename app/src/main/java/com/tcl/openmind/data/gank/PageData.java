package com.tcl.openmind.data.gank;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuansheng on 27/12/2016.
 */

public class PageData {

    @SerializedName("error")
    private boolean isError;

    @SerializedName("results")
    private ArrayList<AndroidBean> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public ArrayList<AndroidBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<AndroidBean> results) {
        this.results = results;
    }

    public class AndroidBean {

        /**
         * _id : 58631c7e421aa9723a5a77e7
         * createdAt : 2016-12-28T09:59:26.111Z
         * desc : 站在谷爹的肩膀上，修改 v4 包中 SlidingPaneLayout 的源码来实现滑动返回布局
         * images : ["http://img.gank.io/e12d9e14-a0d6-4117-94bc-327661df336a"]
         * publishedAt : 2016-12-28T11:57:39.616Z
         * source : web
         * type : Android
         * url : https://github.com/bingoogolapple/BGASwipeBackLayout-Android
         * used : true
         * who : 王浩
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}



