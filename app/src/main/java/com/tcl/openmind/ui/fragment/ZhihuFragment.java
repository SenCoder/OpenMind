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
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;
import com.tcl.openmind.adapter.ZhihuAdapter;
import com.tcl.openmind.data.zhihu.ZhihuDaily;
import com.tcl.openmind.presenter.imp.ZhihuPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by shengyuan on 16-12-19.
 */
public class ZhihuFragment extends BaseFragment {

    private Context mContext;

    private TextView mNoConnectionText;

    private ZhihuPresenter mPresenter;

    @InjectView(R.id.recycle_zhihu)
    protected RecyclerView mRecyclerView;

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
        ButterKnife.inject(this, view);

        checkNetwork();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        initView();

        if (checkNetwork()) {
            LogUtils.d("check network available = true");
            loadData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        mPresenter.unSubscribe();
    }

    @Override
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
    public void loadData() {

        if (mAdapter.getItemCount() > 0) {
            mAdapter.clearData();
        }
        mCurrentLoadDate = "0";
        mPresenter.getLatestZhihuNews();
    }

    @Override
    public void loadMoreData() {
        mAdapter.loadingStart();
        mPresenter.getTheDaily(mCurrentLoadDate);
    }

    public void updateList(ZhihuDaily zhihuDaily) {
        if (isLoading()) {
            setLoading(false);
            mAdapter.loadingEnd();
        }
        mCurrentLoadDate = zhihuDaily.getDate();
        mAdapter.addItems(zhihuDaily.getStories());
//        if the new data is not full of the screen, need load more data
        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreData();
        }
    }

}
