package com.jwu5.giphyapp.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter;
import com.jwu5.giphyapp.R;
import com.jwu5.giphyapp.network.model.GiphyModel;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder implements ViewHolderView{
    private ImageView mItemImageView;
    private ImageButton mFavoriteButton;
    private ProgressBar mProgressBar;
    private Context mContext;
    private GiphyRecyclerViewAdapter mAdapter;
    private String TAG;

    protected ViewHolderPresenter mViewHolderPresenter;

    public GiphyViewHolder(View itemView, Context context, GiphyRecyclerViewAdapter adapter, String tag) {
        super(itemView);
        mContext = context;
        mItemImageView = (ImageView)itemView.findViewById(R.id.Giphy_image_view);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        mFavoriteButton = (ImageButton)itemView.findViewById(R.id.Giphy_image_button);
        mAdapter = adapter;

        TAG = tag;
        mViewHolderPresenter = new ViewHolderPresenter(this, mContext);
    }

    public void bindGiphyItem(final GiphyModel item) {
        Glide
                .with(mContext)
                .load(item.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mItemImageView);

        mViewHolderPresenter.setFavoriteIcon(item);

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewHolderPresenter.saveToFavorites(item);
            }
        });
    }

    @Override
    public void setButtonColor(int color) {
        mFavoriteButton.setColorFilter(color);
    }

    @Override
    public void removeItemFromView(GiphyModel item) {
        mAdapter.removeItem(item);
    }

    @Override
    public void updateAdapter() {
        mAdapter.notifyItemChanged(getAdapterPosition());
    }

    @Override
    public String getTag() {
        return TAG;
    }

}
