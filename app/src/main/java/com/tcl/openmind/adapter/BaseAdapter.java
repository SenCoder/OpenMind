package com.tcl.openmind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.tcl.openmind.adapter.api.IDataLoader;

/**
 * Created by shengyuan on 16-12-19.
 */

public class BaseAdapter extends RecyclerView.Adapter implements IDataLoader {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void loadingStart() {

    }

    @Override
    public void loadingEnd() {

    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0) {
            return  NOMAL_ITEM;
        }
        return TYPE_LOADING_MORE;
    }

}
