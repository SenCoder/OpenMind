package com.tcl.openmind.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tcl.openmind.R;
import com.tcl.openmind.adapter.NeteaseAdapter;
import com.tcl.openmind.presenter.imp.NeteasePresenter;

import butterknife.InjectView;

/**
 * Created by shengyuan on 16-12-19.
 */

public class NeteaseFragment extends BaseFragment {

    private NeteaseAdapter mAdapter;
    private RecyclerView.OnScrollListener mLoadingMoreListener;
    private NeteasePresenter mPresenter;
    LinearLayoutManager mLayoutManager;

    private int currentIndex;

    @InjectView(R.id.recycle_netease)
    protected RecyclerView mRecyclerView;
    
    @InjectView(R.id.progress)
    protected ProgressBar mProgressBar;

    private android.content.Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_netease, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initListener();
        initView();
    }

    private void initView() {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void loadDate() {

    }

    @Override
    public void loadMoreDate() {

    }
}
