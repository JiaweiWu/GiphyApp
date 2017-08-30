package com.jwu5.giphyapp.network


import com.jwu5.giphyapp.network.model.Datum
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Jiawei on 8/16/2017.
 */
interface GiphyApiService {

    @GET("trending")
    fun getTrendingGifs(
            @Query("api_key") apiKey: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int): Observable<Datum>

    @GET("search")
    fun getSearchedGifs(
            @Query("api_key") apiKey: String,
            @Query("q") queryTerm: String?,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int): Observable<Datum>

    companion object {

        val API_KEY = "849d70e5f5f74406a34eee5530a250ec"
    }
}
