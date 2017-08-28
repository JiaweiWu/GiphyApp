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
    public ImageButton mFavoriteButton;
    private Context mContext;

    public GiphyViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            mItemImageView = (ImageView)itemView.findViewById(R.id.Giphy_image_view);
            mFavoriteButton = (ImageButton)itemView.findViewById(R.id.Giphy_image_button);
    }

    public void bindGiphyItem(final GiphyModel item) {
        Glide
                .with(mContext)
                .load(item.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mItemImageView);

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity context = (MainActivity) mContext;
                context.addFavorite(item);
                mFavoriteButton.setColorFilter(Color.argb(255, 255, 102, 153));
            }
        });
    }
}
