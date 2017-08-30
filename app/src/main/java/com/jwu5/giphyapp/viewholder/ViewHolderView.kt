package com.jwu5.giphyapp.viewholder

import android.graphics.Color

import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList

/**
 * Created by Jiawei on 8/29/2017.
 */
interface ViewHolderView {
    fun setButtonColor(color: Int)
    fun removeItemFromView(item: GiphyModel)
    fun updateAdapter()
    fun getInFavoritesColor(): Int
    fun getNotInFavoritesColor(): Int
    val tag: String
}
