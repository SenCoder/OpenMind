package com.tcl.openmind.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tcl.openmind.R;
import com.tcl.openmind.adapter.api.IDataLoader;
import com.tcl.openmind.data.netease.NeteaseNews;
import com.tcl.openmind.ui.widget.FourThreeImageView;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class NeteaseAdapter extends BaseAdapter {

    private ArrayList<NeteaseNews> topNewsList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return topNewsList.size();
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

    class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class TopNewsViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final LinearLayout linearLayout;
//        final TextView sourceTextview;
        FourThreeImageView imageView;

        TopNewsViewHolder(View itemView) {
            super(itemView);
            imageView = (FourThreeImageView) itemView.findViewById(R.id.image_netease);
            textView = (TextView) itemView.findViewById(R.id.text_netease);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.netease_item_layout);
//            sourceTextview= (TextView) itemView.findViewById(R.id.item_text_source_id);
        }
    }
}
