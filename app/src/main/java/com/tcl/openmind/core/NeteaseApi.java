package com.tcl.openmind.core;

import com.tcl.openmind.data.netease.NewsListBean;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yuansheng on 25/12/2016.
 */

public interface NeteaseApi {

    @GET("http://c.m.163.com/nc/article/headline/T11348647909107/{id}-20.html")
    Observable<NewsListBean> getNews(@Path("id") int id);

    @GET("http://c.m.163.com/nc/article/{id}/full.html")
    Observable<String> getNewsDetail(@Path("id") String id);

}
