package com.jwu5.giphyapp.giphyfavorites

import android.content.Context

import com.jwu5.giphyapp.dataSingleton.SavedFavorites
import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * Created by Jiawei on 8/29/2017.
 */
class FavoritesPresenter(private val mView: FavoritesView, private val mContext: Context) {
    private val mFavorites: SavedFavorites

    init {
        mFavorites = SavedFavorites.instance
    }

    val favorites: LinkedHashMap<String, GiphyModel>
        get() = mFavorites.favorites!!

    fun updateUI(position: Int) {
        val tempFavorites = mFavorites.favorites
        val tempList = ArrayList<GiphyModel>()
        val keys = tempFavorites!!.keys

        for (k in keys) {
            tempList.add(tempFavorites[k]!!)
        }

        mView.updateAdapter(tempList, position)
    }
}
