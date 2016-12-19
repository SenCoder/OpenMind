package com.tcl.openmind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tcl.openmind.R;
import com.tcl.openmind.data.zhihu.ZhihuDailyItem;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuAdapter extends BaseAdapter {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int NOMAL_ITEM = 1;

    private Context mContext;

    private ArrayList<ZhihuDailyItem> mDailyItems = new ArrayList<>();

    public ZhihuAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING_MORE:
                break;
            case NOMAL_ITEM:
                break;
        }
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

    public int getItemViewType(int position) {
        if (position < getItemCount()
                && getItemCount() > 0) {
            return NOMAL_ITEM;
        }
        return TYPE_LOADING_MORE;
    }

    class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class ZhihuViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final LinearLayout linearLayout;
        ImageView imageView;

        ZhihuViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_zhihu);
            textView = (TextView) itemView.findViewById(R.id.text_zhihu);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
        }
    }
}
