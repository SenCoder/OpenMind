package com.tcl.openmind.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.squareup.picasso.Picasso;
import com.tcl.openmind.BuildConfig;
import com.tcl.openmind.R;
import com.tcl.openmind.adapter.api.IDataLoader;
import com.tcl.openmind.config.Config;
import com.tcl.openmind.data.netease.NeteaseNews;
import com.tcl.openmind.ui.widget.FourThreeImageView;
import com.tcl.openmind.util.DBUtils;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class NeteaseAdapter extends BaseAdapter {

    private Context mContext;

    private boolean showLoadingMore;

    private ArrayList<NeteaseNews> mNewsList = new ArrayList<>();

    public NeteaseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.d("onCreateViewHolder");
        switch (viewType) {
            case NOMAL_ITEM:
                LogUtils.d("NeteaseViewHolder create");
                return new NeteaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_netease, parent, false));
            case TYPE_LOADING_MORE:
                LogUtils.d("LoadingMoreHolder create");
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
        case NOMAL_ITEM:
            bindViewHolderNormal((NeteaseViewHolder) holder);
            break;
        case TYPE_LOADING_MORE:
            bindLoadingViewHolder((LoadingMoreHolder) holder);
            break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0) {
            return  NOMAL_ITEM;
        }
        return TYPE_LOADING_MORE;
    }

    private void bindViewHolderNormal(final NeteaseViewHolder holder) {
        final NeteaseNews newsItem = mNewsList.get(holder.getAdapterPosition());

        if (DBUtils.getDB(mContext).isRead(Config.NETEASE, newsItem.getTitle(), 1)) {
            holder.textView.setTextColor(Color.GRAY);
            holder.sourceTextview.setTextColor(Color.GRAY);
        }
        else {
            holder.textView.setTextColor(Color.BLACK);
            holder.sourceTextview.setTextColor(Color.BLACK);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getDB(mContext).insertHasRead(Config.NETEASE, newsItem.getTitle(), 1);
                holder.textView.setTextColor(Color.GRAY);
                holder.sourceTextview.setTextColor(Color.GRAY);
                // go to detail activity
            }
        });
        holder.textView.setText(newsItem.getTitle());
        holder.sourceTextview.setText(newsItem.getSource());
        Picasso.with(mContext).
                load(newsItem.getImgsrc())
                .resize(128, 128 * 3/4)
                .centerCrop().into(holder.imageView);
    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder) {
        holder.progressBar.setVisibility(showLoadingMore == true ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    @Override
    public void clearData() {
        mNewsList.clear();
        notifyDataSetChanged();
    }

    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1: RecyclerView.NO_POSITION;
    }

    @Override
    public void loadingStart() {
        if (showLoadingMore) return;
        showLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingEnd() {
        if (!showLoadingMore) return;
        showLoadingMore = false;
        notifyItemRemoved(getLoadingMoreItemPosition());
    }

    public void addItems(ArrayList<NeteaseNews> newsList) {
        if (mNewsList != null) {
//            mNewsList.remove(0);
            mNewsList.addAll(newsList);
            LogUtils.i("add new items and notifyDataSetChanged");
            notifyDataSetChanged();
        }
    }

    class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class NeteaseViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final LinearLayout linearLayout;
        final TextView sourceTextview;
        FourThreeImageView imageView;

        NeteaseViewHolder(View itemView) {
            super(itemView);
            imageView = (FourThreeImageView) itemView.findViewById(R.id.image_netease);
            textView = (TextView) itemView.findViewById(R.id.text_netease);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.netease_item_layout);
            sourceTextview= (TextView) itemView.findViewById(R.id.text_netease_source);
        }
    }
}
