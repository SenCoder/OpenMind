package com.tcl.openmind.presenter.imp;

import android.content.Context;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.BuildConfig;
import com.tcl.openmind.config.Config;
import com.tcl.openmind.core.ApiManager;
import com.tcl.openmind.data.zhihu.ZhihuDaily;
import com.tcl.openmind.data.zhihu.ZhihuDailyItem;
import com.tcl.openmind.presenter.api.IPresenter;
import com.tcl.openmind.ui.fragment.ZhihuFragment;
import com.tcl.openmind.util.CacheUtil;

import java.util.Arrays;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuPresenter extends BasePresenter implements IPresenter {

    private ZhihuFragment mFragment;
    private CacheUtil mCacheUtil;
    private Gson mGson = new Gson();

    public ZhihuPresenter(Context context, ZhihuFragment fragment) {
        mFragment = fragment;
        mCacheUtil = CacheUtil.get(context);

    }

    public void getLatestZhihuNews() {

        mFragment.showProgressBar();
        Subscription subscription = ApiManager.getInstance().getZhihuApi().getLatestDaily()
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        LogUtils.d(Thread.currentThread().getName());
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem item: zhihuDaily.getStories()) {
                            if (BuildConfig.DEBUG) {
                                LogUtils.d(" zhihu daily item: " + item.getTitle());
                                LogUtils.d(" zhihu daily item: " + Arrays.toString(item.getImages()));
                            }
                            item.setDate(date);
                        }
                        return zhihuDaily;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.hideProgressBar();
                        LogUtils.e(e.toString());
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mFragment.hideProgressBar();
                        mCacheUtil.put(Config.ZHIHU, mGson.toJson(zhihuDaily));
                        if (BuildConfig.DEBUG) {
                            LogUtils.d("check zhihuDaily.date = " + zhihuDaily.getDate());
                        }
                        mFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }

    public void getTheDaily(String currentLoadDate) {
        if (BuildConfig.DEBUG && (currentLoadDate == null)) {
            LogUtils.e("check currentLoadDate is not null fail");
        }

        Subscription subscription = ApiManager.getInstance().getZhihuApi().getTheDaily(currentLoadDate)
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem item: zhihuDaily.getStories()) {
                            item.setDate(date);
                        }
                        return zhihuDaily;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mFragment.hideProgressBar();
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mFragment.hideProgressBar();
                        LogUtils.d("getTheDaily onNext callback");
                        mFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }

}
