package com.jwu5.giphyapp.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/17/2017.
 */
public class Datum {
    @SerializedName("data")
    @Expose
    private ArrayList<GiphyModel> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public ArrayList<GiphyModel> getData() {
        return data;
    }

    public void setData(ArrayList<GiphyModel> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
