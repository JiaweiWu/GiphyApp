package com.jwu5.giphyapp.viewholder;

import android.graphics.Color;

import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/29/2017.
 */
public interface ViewHolderView {

    public final static int IN_FAVORITES_COLOR = Color.argb(255, 255, 102, 153);
    public final static int NOT_IN_FAVORITES_COLOR = Color.WHITE;
    public void setButtonColor(int color);
    public void removeItemFromView(GiphyModel item);
    public void updateAdapter();
    public String getTag();
}
