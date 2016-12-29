package com.tcl.openmind.presenter.imp;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.core.ApiManager;
import com.tcl.openmind.data.netease.NeteaseNews;
import com.tcl.openmind.data.netease.NewsListBean;
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
        mFragment = fragment;
    }

    public void getNewsList(int index) {
        mFragment.showProgressBar();
        Subscription subscription= ApiManager.getInstance().getNeteaseApi().getNews(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.hideProgressBar();
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onNext(NewsListBean newsList) {
                        mFragment.hideProgressBar();
                        LogUtils.d("check newsList is not null: " + (newsList != null));

                        for (NeteaseNews item: newsList.getNewsList()) {
                            LogUtils.d(item.getTitle());
                            LogUtils.d(item.getImgsrc());
                        }
                        mFragment.updateList(newsList);
                    }
                });
        addSubscription(subscription);
    }
}
