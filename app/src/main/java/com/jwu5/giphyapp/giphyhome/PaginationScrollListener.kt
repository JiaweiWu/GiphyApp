package com.jwu5.giphyapp.giphyhome

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AbsListView

/**
 * Created by Jiawei on 8/28/2017.
 * Taken from http://blog.iamsuleiman.com/android-pagination-tutorial-getting-started-recyclerview/
 */

abstract class PaginationScrollListener(internal var mGridLayoutManager:

                                        GridLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = mGridLayoutManager.itemCount
        val visibleItemCount = mGridLayoutManager.childCount
        val firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItem >= totalItemCount) {
            onLoadMore(totalItemCount)
        }
    }

    abstract fun onLoadMore(totalItemCount: Int)
}

