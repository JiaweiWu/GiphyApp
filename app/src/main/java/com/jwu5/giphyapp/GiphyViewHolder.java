package com.jwu5.giphyapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jwu5.giphyapp.model.GiphyModel;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder{
    private ImageView mItemImageView;
    private ImageButton mFavoriteButton;
    private ProgressBar mProgressBar;
    private Context mContext;
    private GiphyRecyclerViewAdapter mAdapter;

    final private int PINK = Color.argb(255, 255, 102, 153);
    final private int WHITE = Color.WHITE;

    public GiphyViewHolder(View itemView, Context context, GiphyRecyclerViewAdapter adapter) {
        super(itemView);
        mContext = context;
        mItemImageView = (ImageView)itemView.findViewById(R.id.Giphy_image_view);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        mFavoriteButton = (ImageButton)itemView.findViewById(R.id.Giphy_image_button);
        mAdapter = adapter;
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

        final MainActivity activity = (MainActivity) mContext;
        if(activity.getFavorites().containsKey(item.getId())) {
            mFavoriteButton.setColorFilter(PINK);
        } else {
            mFavoriteButton.setColorFilter(WHITE);
        }

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.getFavorites().containsKey(item.getId())) {
                    activity.removeFavorites(item);
                    mAdapter.removeItem(item);
                    mFavoriteButton.setColorFilter(WHITE);
                    Toast.makeText(activity, "Removed From Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    activity.addFavorite(item);
                    mFavoriteButton.setColorFilter(PINK);
                    Toast.makeText(activity, "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
                mAdapter.notifyItemChanged(getAdapterPosition());
            }
        });
    }
}
