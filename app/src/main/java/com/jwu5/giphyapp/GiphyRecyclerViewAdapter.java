package com.jwu5.giphyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.model.GiphyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyRecyclerViewAdapter extends RecyclerView.Adapter<GiphyViewHolder>{
    private ArrayList<GiphyModel> mGiphyItems;
    private Context mContext;

    public GiphyRecyclerViewAdapter(ArrayList<GiphyModel> giphyItems, Context context) {
        mGiphyItems = giphyItems;
        mContext = context;
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.giphy_item, viewGroup, false);

        return new GiphyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(GiphyViewHolder viewHolder, int position) {
        GiphyModel giphyItem = mGiphyItems.get(position);
        viewHolder.bindGiphyItem(giphyItem);
    }

    @Override
    public int getItemCount() {
        return mGiphyItems.size();
    }

    public void addItem(GiphyModel item) {
        mGiphyItems.add(item);
        notifyDataSetChanged();
    }
}
