package com.tcl.openmind.presenter.imp;

import android.content.Context;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.core.ApiManager;
import com.tcl.openmind.data.gank.PageData;
import com.tcl.openmind.ui.fragment.BaseFragment;
import com.tcl.openmind.ui.fragment.LookFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shengyuan on 16-12-19.
 */

public class LookPresenter extends BasePresenter {

    private LookFragment mFragment;

    public LookPresenter(LookFragment fragment) {
        mFragment = fragment;
    }

    public void loadAndroidData() {

        mFragment.showProgressBar();
        Subscription subscription = ApiManager.getInstance().getGankApi()
                .getAndroidPages("10", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.hideProgressBar();
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(PageData pageData) {

                        ArrayList<PageData.AndroidBean> beans = pageData.getResults();
                        for (PageData.AndroidBean bean: beans) {
                            LogUtils.d(bean.getCreatedAt());
                            LogUtils.d(bean.getUrl());
                            LogUtils.d("check bean.getImages() != null: " + (bean.getImages() != null));
                        }
                    }
                });
        addSubscription(subscription);
    }

}
