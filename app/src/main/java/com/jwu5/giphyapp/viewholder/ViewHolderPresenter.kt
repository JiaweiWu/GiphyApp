package com.jwu5.giphyapp.viewholder

import android.content.Context
import android.widget.Toast

import com.jwu5.giphyapp.dataSingleton.SavedFavorites
import com.jwu5.giphyapp.giphyfavorites.FavoritesFragment
import com.jwu5.giphyapp.giphyhome.HomeFragment
import com.jwu5.giphyapp.network.model.GiphyModel

/**
 * Created by Jiawei on 8/29/2017.
 */
class ViewHolderPresenter(private val mView: ViewHolderView, private val mContext: Context) {
    private val mFavorites: SavedFavorites

    init {
        mFavorites = SavedFavorites.instance
    }

    fun saveToFavorites(item: GiphyModel) {
        if (mFavorites.isItInFavorites(item)) {
            mFavorites.removeFavorites(item)
            if (mView.tag == FavoritesFragment.TAB_NAME) {
                mView.removeItemFromView(item)
            }
            mView.setButtonColor(mView.getNotInFavoritesColor());
            Toast.makeText(mContext, "Removed From Favorites", Toast.LENGTH_SHORT).show()
        } else {
            mFavorites.addFavorite(item)
            mView.setButtonColor(mView.getInFavoritesColor())
            Toast.makeText(mContext, "Added to Favorites", Toast.LENGTH_SHORT).show()
        }
        mView.updateAdapter()
    }

    fun setFavoriteIcon(item: GiphyModel) {
        if (mFavorites.isItInFavorites(item)) {
            mView.setButtonColor(mView.getInFavoritesColor())
        } else {
            mView.setButtonColor(mView.getNotInFavoritesColor())
        }
    }
}
