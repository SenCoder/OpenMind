package com.tcl.openmind.presenter.imp;

import com.tcl.openmind.presenter.api.IPresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shengyuan on 16-12-19.
 */

public abstract class BasePresenter implements IPresenter {

    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unSubscribe() {

        // TODO: 16/8/17 find when unsubcrible
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
