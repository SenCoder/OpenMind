package com.tcl.openmind.core;

import com.lidroid.xutils.util.LogUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shengyuan on 16-12-20.
 */

public class ApiManager {


    private ApiManager() {

    }

    public static ApiManager getInstance() {
        if (instance == null)
            instance = new ApiManager();
        return instance;
    }

    private static ApiManager instance;
    private static OkHttpClient sClient = new OkHttpClient.Builder().build();

    private ZhihuApi mZhihuApi;
    private NeteaseApi mNeteaseApi;

    // I was wondering why this object is needed.
    private Object monitor = new Object();


    public ZhihuApi getZhihuApi() {

        synchronized (monitor) {
            if (mZhihuApi == null) {
                mZhihuApi = new Retrofit.Builder()
                        .baseUrl("http://news-at.zhihu.com")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(sClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(ZhihuApi.class);
            }
        }
        LogUtils.d("check zhihuApi implement = true");
        return mZhihuApi;
    }

    public NeteaseApi getNeteaseApi() {
        synchronized (monitor) {
            if (mNeteaseApi == null) {
                mNeteaseApi = new Retrofit.Builder().baseUrl("http://c.m.163.com")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(sClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(NeteaseApi.class);
            }
        }
        return mNeteaseApi;
    }

}

