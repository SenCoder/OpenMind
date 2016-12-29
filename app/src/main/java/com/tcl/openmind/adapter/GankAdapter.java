package com.tcl.openmind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tcl.openmind.R;
import com.tcl.openmind.data.gank.PageData;
import com.tcl.openmind.ui.widget.FourThreeImageView;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class GankAdapter extends BaseAdapter {

    private ArrayList<PageData.AndroidBean> mPages;

    private Context mContext;

    private boolean isLoadingMore;

    public GankAdapter(Context context) {
        mContext = context;
        mPages = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NOMAL_ITEM:
                return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_gank, parent, false));
            case TYPE_LOADING_MORE:
                return new NeteaseAdapter.LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        switch (type) {
            case NOMAL_ITEM:
                bindNormalViewHolder((NormalViewHolder)holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingMoreViewHolder((LoadingMoreViewHolder) holder);
                break;
        }
    }

    private void bindNormalViewHolder(NormalViewHolder viewHolder, int position) {
        PageData.AndroidBean androidBean = mPages.get(position);
        viewHolder.textView.setText(androidBean.getDesc());
//        if (androidBean.getImages() != null) {
//            Picasso.with(mContext).load(androidBean.getUrl())
//                    .resize(128, 96)
//                    .centerCrop()
//                    .into(viewHolder.imageView);
//        }
    }

    private void bindLoadingMoreViewHolder(LoadingMoreViewHolder viewHolder) {
        viewHolder.progressBar.setVisibility(isLoadingMore ? View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mPages.size();
    }

    @Override
    public void clearData() {
        if (mPages != null) {
            mPages.clear();
            notifyDataSetChanged();
        }
    }

    private int getLoadingMoreItemPosition() {
        return isLoadingMore ? getItemCount() - 1: RecyclerView.NO_POSITION;
    }

    @Override
    public void loadingStart() {
        if (isLoadingMore) return;
        isLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingEnd() {
        if (!isLoadingMore) return;
        isLoadingMore = false;
        notifyItemRemoved(getLoadingMoreItemPosition());
    }

    public void addItems(ArrayList<PageData.AndroidBean> beans) {
        mPages.addAll(beans);
        notifyDataSetChanged();
    }

    static class LoadingMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder {

//        FourThreeImageView imageView;
        TextView textView;

        public NormalViewHolder(View itemView) {
            super(itemView);
//            imageView = (FourThreeImageView) itemView.findViewById(R.id.image_look);
            textView = (TextView) itemView.findViewById(R.id.text_look);
        }
    }
}
