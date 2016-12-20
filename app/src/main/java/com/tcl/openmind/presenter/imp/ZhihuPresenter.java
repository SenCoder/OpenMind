package com.tcl.openmind.presenter.imp;

import android.content.Context;

import com.google.gson.Gson;
import com.tcl.openmind.presenter.api.IZhihuPresenter;
import com.tcl.openmind.ui.fragment.ZhihuFragment;
import com.tcl.openmind.util.CacheUtil;

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

    public void getLastZhihuNews() {

    }

    public void getTheDaily(String currentLoadData) {

    }

}
