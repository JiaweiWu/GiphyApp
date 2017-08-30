package com.jwu5.giphyapp.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Jiawei on 8/17/2017.
 */
class Pagination {
    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("offset")
    @Expose
    var offset: Int? = null

}
