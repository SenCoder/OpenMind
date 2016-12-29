package com.tcl.openmind.core;

import com.tcl.openmind.data.gank.PageData;
import com.tcl.openmind.data.gank.GirlData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
 *
 * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
 * 请求个数： 数字，大于0
 * 第几页：数字，大于0
 * 例：
 *   http://gank.io/api/data/Android/10/1
 *   http://gank.io/api/data/福利/10/1
 *   http://gank.io/api/data/iOS/20/2
 *   http://gank.io/api/data/all/20/2
 */

public interface GankApi {

    @GET("/api/data/福利/{number}/{index}")
    Observable<GirlData> getGirlData(@Path("number") String number,
                                     @Path("index") String index);

    @GET("/api/data/Android/{number}/{index}")
    Observable<PageData> getAndroidPages(@Path("number") String number,
                                         @Path("index") String index);

    @GET("/api/data/day/{year}/{month}/{day}")
    Observable<PageData> getDataByDate(@Path("year") String year,
                                       @Path("month") String month,
                                       @Path("day") String day);

}
