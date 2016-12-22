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

    private ZhihuApi mZhihuApi;

    // I was wondering why this object is needed.
    private Object zhihuMonitor = new Object();


    public ZhihuApi getZhihuApi() {

        OkHttpClient client = new OkHttpClient.Builder().build();

        if (mZhihuApi == null) {
            synchronized (zhihuMonitor) {
                if (mZhihuApi == null) {
                    mZhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuApi.class);
                }
            }
        }
        LogUtils.d("check zhihuApi implement = true");
        return mZhihuApi;
    }
}

