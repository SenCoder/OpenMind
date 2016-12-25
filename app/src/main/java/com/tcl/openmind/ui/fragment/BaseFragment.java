package com.tcl.openmind.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tcl.openmind.adapter.BaseAdapter;


/**
 * Created by shengyuan on 16-12-19.
 */

public abstract class BaseFragment extends Fragment {

    private boolean isNetworkAvailable;
    private boolean isLoading;
    private Context mContext;
    private LinearLayoutManager mLayoutManager;

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
                        loadMoreDate();
                    }
                }
            }
        };
    }

    public abstract void showProgressDialog();

    public abstract void hideProgressDialog();

    public abstract void loadDate();

    public abstract void loadMoreDate();

}
