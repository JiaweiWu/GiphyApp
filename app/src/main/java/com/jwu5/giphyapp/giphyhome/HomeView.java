package com.jwu5.giphyapp.giphyhome;

import com.jwu5.giphyapp.network.model.GiphyModel;
import java.util.ArrayList;

/**
 * Created by Jiawei on 8/29/2017.
 */
public interface HomeView {
    void setLoading();
    void setLoadingComplete();
    void showTrendingList(ArrayList<GiphyModel> items);
    void showSearchList(ArrayList<GiphyModel> items);
    void updateAdapter(ArrayList<GiphyModel> items, int position);
}
