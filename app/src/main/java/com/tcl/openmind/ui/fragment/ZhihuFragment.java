package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;
import com.tcl.openmind.adapter.ZhihuAdapter;
import com.tcl.openmind.data.zhihu.ZhihuDaily;
import com.tcl.openmind.presenter.imp.ZhihuPresenter;

import butterknife.ButterKnife;

/**
 * Created by shengyuan on 16-12-19.
 */
public class ZhihuFragment extends BaseFragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private TextView mNoConnectionText;

    LinearLayoutManager mLayoutManager;

    private boolean isNetworkAvailable;
    private boolean mMonitoringConnectivity;
    private boolean isLoading;

    private ZhihuAdapter mAdapter;
    private ZhihuPresenter mPresenter;
    private String mCurrentLoadDate = "0";

    RecyclerView.OnScrollListener loadingMoreListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fm_zhihu, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_zhihu);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        checkConnectivity(view);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListener();

        if (isNetworkAvailable) {
            LogUtils.d("check network available = true");
            loadDate();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(mContext);

        mAdapter = new ZhihuAdapter(mContext);
        mPresenter = new ZhihuPresenter(mContext, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // move down
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        isLoading = true;
                        loadMoreDate();
                    }
                }
            }
        };
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
    protected void loadDate() {
        super.loadDate();
        LogUtils.d("check loadDate = true");
        if (mAdapter.getItemCount() > 0) {
            mAdapter.clearData();
        }
        mCurrentLoadDate = "0";
        mPresenter.getLatestZhihuNews();
    }

    private void loadMoreDate() {
        mAdapter.loadingStart();
//        if (mCurrentLoadDate == null)
//            mCurrentLoadDate = "0";
        mPresenter.getTheDaily(mCurrentLoadDate);
    }

    public void updateList(ZhihuDaily zhihuDaily) {
        if (isLoading) {
            isLoading = false;
            mAdapter.loadingEnd();
        }
        LogUtils.d("zhihuDaily is not null : " + (zhihuDaily != null));
        mCurrentLoadDate = zhihuDaily.getDate();
        LogUtils.d("zhihuDaily.date : " + (zhihuDaily.getDate() != null));
        mAdapter.addItems(zhihuDaily.getStories());
//        if the new data is not full of the screen, need load more data
        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    private void checkConnectivity(View view) {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        isNetworkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (!isNetworkAvailable && mProgressBar != null) {//不判断容易抛出空指针异常
            LogUtils.d("Network is OK");
            mProgressBar.setVisibility(View.INVISIBLE);
            if (mNoConnectionText == null) {
                ViewStub stub_text = (ViewStub) view.findViewById(R.id.stub_no_connection_text);
                mNoConnectionText = (TextView) stub_text.inflate();
            }

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                connectivityManager.registerNetworkCallback(
                        new NetworkRequest.Builder()
                                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                        mConnectivityCallback);

                mMonitoringConnectivity = true;
            }
        }
    }

    private ConnectivityManager.NetworkCallback mConnectivityCallback;


}
