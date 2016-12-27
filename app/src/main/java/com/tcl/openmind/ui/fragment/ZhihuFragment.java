package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
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
    private ProgressBar mProgressBar;

    private TextView mNoConnectionText;

    private ZhihuPresenter mPresenter;
    private RecyclerView mRecyclerView;

    private ZhihuAdapter mAdapter;
    private String mCurrentLoadDate = "0";


    public ZhihuFragment() {
        setContext(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fm_zhihu, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_zhihu);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        checkNetwork(view);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        initView();

        if (isNetworkAvailable()) {
            LogUtils.d("check network available = true");
            loadDate();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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

    public void initView() {

        mAdapter = new ZhihuAdapter(mContext);
        mPresenter = new ZhihuPresenter(mContext, this);

        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        );
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadDate() {

        if (mAdapter.getItemCount() > 0) {
            mAdapter.clearData();
        }
        mCurrentLoadDate = "0";
        mPresenter.getLatestZhihuNews();
    }

    @Override
    public void loadMoreDate() {
        mAdapter.loadingStart();
        mPresenter.getTheDaily(mCurrentLoadDate);
    }

    public void updateList(ZhihuDaily zhihuDaily) {
        if (isLoading()) {
            setLoading(false);
            mAdapter.loadingEnd();
        }
        LogUtils.d("check zhihuDaily is not null : " + (zhihuDaily != null));
        mCurrentLoadDate = zhihuDaily.getDate();
        LogUtils.d("check zhihuDaily.date is not null: " + (zhihuDaily.getDate() != null));
        mAdapter.addItems(zhihuDaily.getStories());
//        if the new data is not full of the screen, need load more data
        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    private void checkNetwork(View view) {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        setNetworkAvailable(activeNetworkInfo != null && activeNetworkInfo.isConnected());

        if (!isNetworkAvailable()) {//不判断容易抛出空指针异常
            LogUtils.d("Network is bad");
            hideProgressDialog();
            if (mNoConnectionText == null) {
                ViewStub stub_text = (ViewStub) view.findViewById(R.id.stub_no_connection_text);
                mNoConnectionText = (TextView) stub_text.inflate();
            }
        }
    }

}
