package com.jwu5.giphyapp.dataSingleton

import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.LinkedHashMap

/**
 * Created by Jiawei on 8/29/2017.
 */
class SavedFavorites {
    var favorites: LinkedHashMap<String, GiphyModel>? = null

    init {
        favorites = LinkedHashMap<String, GiphyModel>()
    }

    fun addFavorite(item: GiphyModel) {
        favorites!!.put(item.id!!, item)
    }

    fun removeFavorites(item: GiphyModel) {
        favorites!!.remove(item.id)
    }

    fun isItInFavorites(item: GiphyModel): Boolean {
        return favorites!!.containsKey(item.id)
    }

    companion object {
        private val FILENAME = "file.sav"

        private var mInstance: SavedFavorites? = null

        val instance: SavedFavorites
            get() {
                if (mInstance == null) {
                    mInstance = SavedFavorites()
                }
                return mInstance!!
            }
    }
}
