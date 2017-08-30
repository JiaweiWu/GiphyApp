package com.jwu5.giphyapp.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.R;
import com.jwu5.giphyapp.network.model.GiphyModel;
import com.jwu5.giphyapp.viewholder.GiphyViewHolder;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/16/2017.
 */

public class GiphyRecyclerViewAdapter extends RecyclerView.Adapter<GiphyViewHolder>{
    private ArrayList<GiphyModel> mGiphyItems;
    private Context mContext;
    private String TAG;
    private int mOrientation;

    public GiphyRecyclerViewAdapter(Context context, String tag, int orientation) {
        mGiphyItems = new ArrayList<>();
        mContext = context;
        TAG = tag;
        mOrientation = orientation;
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.giphy_item_land, viewGroup, false);
        } else {
            view = inflater.inflate(R.layout.giphy_item, viewGroup, false);
        }
        return new GiphyViewHolder(view, mContext, this, TAG);
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

    public void addItems(ArrayList<GiphyModel> items) {
        mGiphyItems.addAll(items);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mGiphyItems = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void removeItem(GiphyModel item) {
        mGiphyItems.remove(item);
        notifyDataSetChanged();
    }

    public void setItemList(ArrayList<GiphyModel> itemList) {
        mGiphyItems = itemList;
        notifyDataSetChanged();
    }

    public ArrayList<GiphyModel> getItems() {
        return mGiphyItems;
    }

}
