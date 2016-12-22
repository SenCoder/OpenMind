package com.tcl.openmind.presenter.imp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.config.Config;
import com.tcl.openmind.core.ApiManager;
import com.tcl.openmind.data.zhihu.ZhihuDaily;
import com.tcl.openmind.data.zhihu.ZhihuDailyItem;
import com.tcl.openmind.presenter.api.IZhihuPresenter;
import com.tcl.openmind.ui.fragment.ZhihuFragment;
import com.tcl.openmind.util.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuPresenter extends BasePresenter implements IZhihuPresenter {

    private ZhihuFragment mFragment;
    private CacheUtil mCacheUtil;
    private Gson mGson = new Gson();

    public ZhihuPresenter(Context context, ZhihuFragment fragment) {
        mFragment = fragment;
        mCacheUtil = CacheUtil.get(context);

    }

    public void getLatestZhihuNews() {
        LogUtils.d("check getLatestZhihuNews = true");
        mFragment.showProgressDialog();
        Subscription subscription = ApiManager.getInstance().getZhihuApi().getLatestDaily()
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        LogUtils.d("check getZhihuDaily = true");
                        String date = zhihuDaily.getDate();
                        LogUtils.d("date = " + zhihuDaily.getDate());
                        for (ZhihuDailyItem item: zhihuDaily.getStories()) {
                            LogUtils.d(" zhihu daily item: " + item.getTitle());
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
                        mFragment.hideProgressDialog();
                        LogUtils.e(e.toString());
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mFragment.hideProgressDialog();
                        mCacheUtil.put(Config.ZHIHU, mGson.toJson(zhihuDaily));
                        LogUtils.d("loadMoreDate is to call");
                        LogUtils.d("check zhihuDaily.date = " + zhihuDaily.getDate());

                        mFragment.updateList(zhihuDaily);
                    }
                });
    }

    public void getTheDaily(String currentLoadDate) {
        LogUtils.d("check currentLoadDate is not null : " + (currentLoadDate != null));
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
                        mFragment.hideProgressDialog();
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mFragment.hideProgressDialog();
                        LogUtils.d("getTheDaily onNext callback");
                        mFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }

}
