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

import com.tcl.openmind.R;
import com.tcl.openmind.adapter.GankAdapter;
import com.tcl.openmind.data.gank.PageData;
import com.tcl.openmind.presenter.imp.GankPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shengyuan on 16-12-19.
 */

public class GankFragment extends BaseFragment {

    @InjectView(R.id.recycle_gank)
    protected RecyclerView mRecyclerView;

    private GankPresenter mPresenter;
    private GankAdapter mAdapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(mContext = getContext()).inflate(R.layout.fm_gank, container, false);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initListener();
        initView();

        if (checkNetwork()) {
            loadData();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
        mPresenter.unSubscribe();
    }

    @Override
    protected void initView() {
        mPresenter = new GankPresenter(this);
        mAdapter = new GankAdapter(mContext);

        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {
        if (mAdapter.getItemCount() > 0) {
            mAdapter.clearData();
        }
        mPresenter.loadAndroidData();
    }

    @Override
    public void loadMoreData() {

    }

    public void updateList(PageData pageData) {
        if (isLoading()) {
            setLoading(false);
            mAdapter.loadingEnd();
        }
        hideProgressBar();
        mAdapter.addItems(pageData.getResults());
    }

}
