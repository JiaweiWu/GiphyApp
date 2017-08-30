package com.jwu5.giphyapp.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jwu5.giphyapp.network.model.Datum

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jiawei on 8/15/2017.
 */
class GiphyNetworkRequest(urlEndPoint: String) {
    private val mGiphyService: GiphyApiService

    init {
        val retrofit = Retrofit.Builder().baseUrl(urlEndPoint).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()

        mGiphyService = retrofit.create(GiphyApiService::class.java)
    }


    fun getTrending(limit: Int, offset: Int): Observable<Datum> {
        return mGiphyService.getTrendingGifs(GiphyApiService.API_KEY, limit, offset)
    }

    fun getSearchedGifs(queryTerm: String?, limit: Int, offset: Int): Observable<Datum> {
        return mGiphyService.getSearchedGifs(GiphyApiService.API_KEY, queryTerm, limit, offset)
    }
}
