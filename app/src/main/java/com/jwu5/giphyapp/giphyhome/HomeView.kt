package com.jwu5.giphyapp.giphyhome

import com.jwu5.giphyapp.network.model.GiphyModel
import java.util.ArrayList

/**
 * Created by Jiawei on 8/29/2017.
 */
interface HomeView {
    fun setLoading()
    fun setLoadingComplete()
    fun showTrendingList(items: ArrayList<GiphyModel>?)
    fun showSearchList(items: ArrayList<GiphyModel>?)
    fun updateAdapter(items: ArrayList<GiphyModel>?, position: Int)
}
