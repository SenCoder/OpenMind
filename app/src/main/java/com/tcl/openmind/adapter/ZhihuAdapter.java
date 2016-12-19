package com.tcl.openmind.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lidroid.xutils.util.LogUtils;
import com.tcl.openmind.R;
import com.tcl.openmind.config.Config;
import com.tcl.openmind.data.zhihu.ZhihuDailyItem;
import com.tcl.openmind.util.DBUtils;

import java.util.ArrayList;

/**
 * Created by shengyuan on 16-12-19.
 */

public class ZhihuAdapter extends BaseAdapter {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int NOMAL_ITEM = 1;

    private Context mContext;

    private ArrayList<ZhihuDailyItem> mDailyItems = new ArrayList<>();
    private boolean showLoadingMore;

    public ZhihuAdapter(Context context) {
        mContext = context;
        LogUtils.d("ZhihuAdapter construct");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.d("ZhihuAdapter#onCreateViewHolder");
        switch (viewType) {
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, parent, false)
                );
            case NOMAL_ITEM:
                return new ZhihuViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_zhihu, parent, false)
                );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtils.d("ZhihuAdapter#onBindViewHolder");
        int type = getItemViewType(position);
        switch (type) {
            case NOMAL_ITEM:
                bindViewHolderNormal((ZhihuViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }
    }

    private void bindViewHolderNormal(final ZhihuViewHolder holder, int position) {
        final ZhihuDailyItem zhihuDailyItem = mDailyItems.get(holder.getAdapterPosition());

        if (DBUtils.getDB(mContext).isRead(Config.ZHIHU, zhihuDailyItem.getId(), 1))
            holder.textView.setTextColor(Color.GRAY);
        else
            holder.textView.setTextColor(Color.BLACK);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                goDescribeActivity(holder,zhihuDailyItem);

            }
        });
        holder.textView.setText(zhihuDailyItem.getTitle());
        holder.linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        goDescribeActivity(holder,zhihuDailyItem);
                    }
                });


//        Glide.with(mContext)
//                .load(mDailyItems.get(position).getImages()[0])
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }

//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (!zhihuDailyItem.hasFadedIn) {
//                            holder.imageView.setHasTransientState(true);
//                            final ObservableColorMatrix cm = new ObservableColorMatrix();
//                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
//                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                                @Override
//                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                    holder.imageView.setColorFilter(new ColorMatrixColorFilter(cm));
//                                }
//                            });
//                            animator.setDuration(2000L);
//                            animator.setInterpolator(new AccelerateInterpolator());
//                            animator.addListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    holder.imageView.clearColorFilter();
//                                    holder.imageView.setHasTransientState(false);
//                                    animator.start();
//                                    zhihuDailyItem.hasFadedIn = true;
//
//                                }
//                            });
//                        }
//
//                        return false;
//                    }
//                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .centerCrop().override(widthPx, heighPx)
//                .into(new DribbbleTarget(holder.imageView, false));

    }


    private void bindLoadingViewHolder(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore == true ? View.VISIBLE : View.INVISIBLE);
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

    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
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
        final int loadingPos = getLoadingMoreItemPosition();
        showLoadingMore = false;
        notifyItemRemoved(loadingPos);
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
