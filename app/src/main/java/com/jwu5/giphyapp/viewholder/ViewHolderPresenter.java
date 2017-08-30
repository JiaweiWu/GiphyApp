package com.jwu5.giphyapp.viewholder;

import android.content.Context;
import android.widget.Toast;

import com.jwu5.giphyapp.dataSingleton.SavedFavorites;
import com.jwu5.giphyapp.giphyfavorites.FavoritesFragment;
import com.jwu5.giphyapp.giphyhome.HomeFragment;
import com.jwu5.giphyapp.network.model.GiphyModel;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class ViewHolderPresenter {
    private final ViewHolderView mView;
    private final Context mContext;
    private SavedFavorites mFavorites;

    public ViewHolderPresenter(ViewHolderView view, Context context) {
        mView = view;
        mContext = context;
        mFavorites = SavedFavorites.getInstance();
    }

    public void saveToFavorites(GiphyModel item) {
        if (mFavorites.isItInFavorites(item)) {
            mFavorites.removeFavorites(item);
            if (mView.getTag().equals(FavoritesFragment.TAB_NAME)) {
                mView.removeItemFromView(item);
            }
            mView.setButtonColor(mView.NOT_IN_FAVORITES_COLOR);
            Toast.makeText(mContext, "Removed From Favorites", Toast.LENGTH_SHORT).show();
        } else {
            mFavorites.addFavorite(item);
            mView.setButtonColor(mView.IN_FAVORITES_COLOR);
            Toast.makeText(mContext, "Added to Favorites", Toast.LENGTH_SHORT).show();
        }
        mView.updateAdapter();
    }

    public void setFavoriteIcon(GiphyModel item) {
        if (mFavorites.isItInFavorites(item)) {
            mView.setButtonColor(mView.IN_FAVORITES_COLOR);
        } else {
            mView.setButtonColor(mView.NOT_IN_FAVORITES_COLOR);
        }
    }
}
