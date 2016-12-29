package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tcl.openmind.R;
import com.tcl.openmind.adapter.LookAdapter;
import com.tcl.openmind.presenter.imp.LookPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shengyuan on 16-12-19.
 */

public class LookFragment extends BaseFragment {

//    @InjectView(R.id.progress)
//    private ProgressBar mProgressBar;

    @InjectView(R.id.recycle_look)
    protected RecyclerView mRecyclerView;

    private LookPresenter mPresenter;
    private LookAdapter mAdapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(mContext = getContext()).inflate(R.layout.fm_look, container, false);

        ButterKnife.inject(this, view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initListener();
        initView();

        checkNetwork();
        if (isNetworkAvailable()) {
            mPresenter.loadAndroidData();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
        mPresenter.unSubscribe();
    }

    private void initView() {
        mPresenter = new LookPresenter(this);
        mAdapter = new LookAdapter(mContext);

        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

//    @Override
//    public void showProgressDialog() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//    }

//    @Override
//    public void hideProgressDialog() {
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//        }
//    }

    @Override
    public void loadDate() {

    }

    @Override
    public void loadMoreDate() {

    }
}
