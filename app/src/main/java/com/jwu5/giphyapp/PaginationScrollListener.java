package com.jwu5.giphyapp;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Jiawei on 8/28/2017.
 * Taken from http://blog.iamsuleiman.com/android-pagination-tutorial-getting-started-recyclerview/
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    GridLayoutManager mGridLayoutManager;

    public PaginationScrollListener(GridLayoutManager gridLayoutManager) {
        mGridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = mGridLayoutManager.getItemCount();
        int visibleItemCount = mGridLayoutManager.getChildCount();
        int firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + firstVisibleItem) >= totalItemCount)
        {
            Log.d("", "Visible Count: " + visibleItemCount + " First Count: " + firstVisibleItem +
                " Total Count: " + totalItemCount);
            onLoadMore(totalItemCount);
        }
    }

    abstract public void onLoadMore(int totalItemCount);
}

