package com.tcl.openmind.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcl.openmind.presenter.imp.LookPresenter;

import java.util.Date;

/**
 * Created by shengyuan on 16-12-19.
 */

public class LookFragment extends BaseFragment {

    private LookPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPresenter = new LookPresenter(this);
        mPresenter.getGankDataByDate();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void hideProgressDialog() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void loadDate() {

    }

    @Override
    public void loadMoreDate() {

    }
}
