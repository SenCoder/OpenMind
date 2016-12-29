package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;
import com.tcl.openmind.adapter.NeteaseAdapter;
import com.tcl.openmind.adapter.ZhihuAdapter;
import com.tcl.openmind.data.netease.NewsListBean;
import com.tcl.openmind.presenter.imp.NeteasePresenter;
import com.tcl.openmind.presenter.imp.ZhihuPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shengyuan on 16-12-19.
 */

public class NeteaseFragment extends BaseFragment {

    private NeteaseAdapter mAdapter;
    private RecyclerView.OnScrollListener mLoadingMoreListener;
    private NeteasePresenter mPresenter;

    private int currentIndex;

    @InjectView(R.id.recycle_netease)
    protected RecyclerView mRecyclerView;
    
    @InjectView(R.id.progress)
    protected ProgressBar mProgressBar;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_netease, container, false);
        mContext = getActivity();
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initListener();
        initView();
        checkNetwork();

        if (isNetworkAvailable()) {
            LogUtils.d("check network available = true");
            loadDate();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        ButterKnife.reset(this);
    }

    private void initView() {
        mAdapter = new NeteaseAdapter(mContext);
        mPresenter = new NeteasePresenter(this);

        mRecyclerView.setLayoutManager(getLayoutManager());
        // if you are sure that every item gain the same space, it is better to use setHasFixedSize.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showProgressDialog() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void loadDate() {
        if (mAdapter.getItemCount() > 0) {
            mAdapter.clearData();
        }
        currentIndex = 0;
        mPresenter.getNewsList(currentIndex);
    }

    @Override
    public void loadMoreDate() {
        // request 20 more news each time.
        mAdapter.loadingStart();
        currentIndex += 20;
        mPresenter.getNewsList(currentIndex);
    }

    public void updateList(NewsListBean newsListBean) {
        if (isLoading()) {
            setLoading(false);
            mAdapter.loadingEnd();
        }
        hideProgressDialog();
        mAdapter.addItems(newsListBean.getNewsList());
    }

    private void checkNetwork() {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        setNetworkAvailable(activeNetworkInfo != null && activeNetworkInfo.isConnected());

        if (!isNetworkAvailable()) {//不判断容易抛出空指针异常
            LogUtils.d("Network is bad");
            hideProgressDialog();
//            if (mNoConnectionText == null) {
//                ViewStub stub_text = (ViewStub) view.findViewById(R.id.stub_no_connection_text);
//                mNoConnectionText = (TextView) stub_text.inflate();
//            }
        }
    }

}
