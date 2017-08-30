package com.jwu5.giphyapp.dataSingleton;

import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.LinkedHashMap;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class SavedFavorites {
    private static final String FILENAME = "file.sav";
    private LinkedHashMap<String, GiphyModel> mFavorites;

    private static SavedFavorites mInstance = null;

    public SavedFavorites() {
        mFavorites = new LinkedHashMap<>();
    }

    public static SavedFavorites getInstance() {
        if(mInstance == null) {
            mInstance = new SavedFavorites();
        }
        return mInstance;
    }

    public LinkedHashMap<String, GiphyModel> getFavorites() {
        return mFavorites;
    }

    public void setFavorites(LinkedHashMap<String, GiphyModel> favorites) {
        mFavorites = favorites;
    }

    public void addFavorite(GiphyModel item) {
        mFavorites.put(item.getId(), item);
    }

    public void removeFavorites(GiphyModel item) {
        mFavorites.remove(item.getId());
    }

    public boolean isItInFavorites(GiphyModel item) {
        return mFavorites.containsKey(item.getId());
    }
}
