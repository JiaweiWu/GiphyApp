package com.jwu5.giphyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.gifdecoder.GifHeader;
import com.jwu5.giphyapp.model.GiphyModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyRecyclerViewAdapter extends RecyclerView.Adapter<GiphyViewHolder>{
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<GiphyModel> mGiphyItems;
    private Context mContext;
    private boolean isLoadingAdded = false;

    public GiphyRecyclerViewAdapter(ArrayList<GiphyModel> giphyItems, Context context) {
        mGiphyItems = giphyItems;
        mContext = context;
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.giphy_item, viewGroup, false);

        return new GiphyViewHolder(view, mContext, this);
    }

    @Override
    public void onBindViewHolder(GiphyViewHolder viewHolder, int position) {
        GiphyModel giphyItem = mGiphyItems.get(position);
        viewHolder.bindGiphyItem(giphyItem);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mGiphyItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public int getItemCount() {
        return mGiphyItems.size();
    }

    public void addItems(ArrayList<GiphyModel> items) {
        mGiphyItems.addAll(items);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mGiphyItems = new ArrayList<>();
        notifyDataSetChanged();
    }

}
