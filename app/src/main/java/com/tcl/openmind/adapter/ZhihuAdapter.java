package com.tcl.openmind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.tcl.openmind.data.zhihu.ZhihuDailyItem;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<ZhihuDailyItem> mDailyItems = new ArrayList<>();

    public ZhihuAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDailyItems.size();
    }

    @Override
    public void clearData() {
        if (mDailyItems != null) {
            mDailyItems.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void loadingStart() {

    }

    @Override
    public void loadingEnd() {

    }

    public void addItems(ArrayList<ZhihuDailyItem> stories) {
        mDailyItems.addAll(stories);
        notifyDataSetChanged();
    }
}
