package com.jwu5.giphyapp.giphyfavorites;

import android.content.Context;

import com.jwu5.giphyapp.dataSingleton.SavedFavorites;
import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class FavoritesPresenter {
    private final FavoritesView mView;
    private Context mContext;
    private SavedFavorites mFavorites;

    public FavoritesPresenter(FavoritesView view, Context context) {
        mView = view;
        mContext = context;
        mFavorites = SavedFavorites.getInstance();
    }

    public LinkedHashMap<String, GiphyModel> getFavorites() {
        return mFavorites.getFavorites();
    }

    public void updateUI(int position) {
        LinkedHashMap<String, GiphyModel> tempFavorites = mFavorites.getFavorites();
        ArrayList<GiphyModel> tempList = new ArrayList<>();
        Set<String> keys = tempFavorites.keySet();

        for (String k : keys) {
            tempList.add(tempFavorites.get(k));
        }

        mView.updateAdapter(tempList, position);
    }
}
