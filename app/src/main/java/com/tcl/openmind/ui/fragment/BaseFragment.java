package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;

import butterknife.InjectView;


/**
 * Created by shengyuan on 16-12-19.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isNetworkAvailable;
    private boolean isLoading;
    private Context mContext;
    private LinearLayoutManager mLayoutManager;

    @InjectView(R.id.progress)
    protected ProgressBar mProgressBar;

    RecyclerView.OnScrollListener loadingMoreListener;

    public void setContext(Context context) {
        mContext = context;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }


    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void initListener() {
        mLayoutManager = new LinearLayoutManager(mContext);
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
                    if (!isLoading() && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        setLoading(true);
                        loadMoreData();
                    }
                }
            }
        };
    }

    protected boolean checkNetwork() {
        final ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        setNetworkAvailable(activeNetworkInfo != null && activeNetworkInfo.isConnected());

        if (!isNetworkAvailable()) {//不判断容易抛出空指针异常
            LogUtils.d("Network is bad");
        }
        return isNetworkAvailable();
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void initView();

    public abstract void loadData();

    public abstract void loadMoreData();

}
