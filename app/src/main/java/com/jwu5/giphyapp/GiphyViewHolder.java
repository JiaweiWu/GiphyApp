package com.jwu5.giphyapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.jwu5.giphyapp.model.GiphyModel;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder{
    private ImageView mItemImageView;
    private ImageButton mFavoriteButton;
    private Context mContext;
    private GiphyRecyclerViewAdapter mAdapter;

    final private int PINK = Color.argb(255, 255, 102, 153);
    final private int WHITE = Color.WHITE;

    public GiphyViewHolder(View itemView, Context context, GiphyRecyclerViewAdapter adapter) {
        super(itemView);
        mContext = context;
        mItemImageView = (ImageView)itemView.findViewById(R.id.Giphy_image_view);
        mFavoriteButton = (ImageButton)itemView.findViewById(R.id.Giphy_image_button);
        mAdapter = adapter;
    }

    public void bindGiphyItem(final GiphyModel item) {
        final MainActivity activity = (MainActivity) mContext;
        if(activity.getFavorites().containsKey(item.getId())) {
            mFavoriteButton.setColorFilter(PINK);
        } else {
            mFavoriteButton.setColorFilter(WHITE);
        }

        Glide
                .with(mContext)
                .load(item.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mItemImageView);

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.getFavorites().containsKey(item.getId())) {
                    activity.removeFavorites(item);
                    mFavoriteButton.setColorFilter(WHITE);
                } else {
                    activity.addFavorite(item);
                    mFavoriteButton.setColorFilter(PINK);
                }
                mAdapter.notifyItemChanged(getAdapterPosition());
            }
        });
    }
}
