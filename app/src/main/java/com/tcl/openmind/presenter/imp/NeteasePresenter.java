package com.tcl.openmind.presenter.imp;

import android.content.Context;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.core.ApiManager;
import com.tcl.openmind.data.netease.NewsListBean;
import com.tcl.openmind.ui.fragment.BaseFragment;
import com.tcl.openmind.ui.fragment.NeteaseFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shengyuan on 16-12-19.
 */

public class NeteasePresenter extends BasePresenter {

    private NeteaseFragment mFragment;

    public NeteasePresenter(NeteaseFragment fragment) {

    }

    public void getNewsList(int t) {
        mFragment.showProgressDialog();
        Subscription subscription= ApiManager.getInstance().getNeteaseApi().getNews(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mFragment.hideProgressDialog();
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onNext(NewsListBean newsList) {
                        mFragment.hideProgressDialog();
//                        mFragment.upda(newsList);

                    }
                });
        addSubscription(subscription);
    }
}
