package com.jwu5.giphyapp.giphyfavorites

import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList

/**
 * Created by Jiawei on 8/29/2017.
 */
interface FavoritesView {
    fun updateAdapter(items: ArrayList<GiphyModel>?, position: Int)
}
