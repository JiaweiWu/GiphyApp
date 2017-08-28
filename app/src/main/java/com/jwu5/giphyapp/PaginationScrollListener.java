package com.jwu5.giphyapp;

import android.widget.AbsListView;

/**
 * Created by Jiawei on 8/28/2017.
 */

public abstract class PaginationScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;

    private int currentPage = 0;
}
