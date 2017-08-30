package com.jwu5.giphyapp.giphyfavorites;

import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/29/2017.
 */
public interface FavoritesView {
    public void updateAdapter(ArrayList<GiphyModel> items, int position);
}
