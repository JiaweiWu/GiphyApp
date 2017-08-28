package com.jwu5.giphyapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jiawei on 8/17/2017.
 */
public class GiphyModel implements Serializable {
    public static final String PREVIEW_URL = "https://i.giphy.com/media/";
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return PREVIEW_URL + id + "/200w.gif";
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
