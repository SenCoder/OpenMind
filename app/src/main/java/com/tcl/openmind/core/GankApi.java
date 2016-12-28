package com.tcl.openmind.core;

import com.tcl.openmind.data.gank.PageData;
import com.tcl.openmind.data.gank.GirlData;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by shengyuan on 16-12-27.
 */

public interface GankApi {

    @GET("/api/data/福利/{}/{}")
    Observable<GirlData> getGirlData();

    Observable<PageData> getPages();

    @GET("/api/data/Android/{}/{}")
    Observable<PageData> getDataByDate();


}
