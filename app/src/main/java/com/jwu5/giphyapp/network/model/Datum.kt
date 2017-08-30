package com.jwu5.giphyapp.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Jiawei on 8/17/2017.
 */
class Datum {
    @SerializedName("data")
    @Expose
    var data: ArrayList<GiphyModel>? = null
    @SerializedName("pagination")
    @Expose
    var pagination: Pagination? = null

}
