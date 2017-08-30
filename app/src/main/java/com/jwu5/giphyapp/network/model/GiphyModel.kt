package com.jwu5.giphyapp.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by Jiawei on 8/17/2017.
 */
class GiphyModel {
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
        get() = PREVIEW_URL + id + "/200w.gif"

    companion object {
        val PREVIEW_URL = "https://i.giphy.com/media/"
    }
}
