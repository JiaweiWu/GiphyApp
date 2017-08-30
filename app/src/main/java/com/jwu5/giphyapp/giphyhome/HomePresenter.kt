package com.jwu5.giphyapp.giphyhome

import com.jwu5.giphyapp.network.GiphyNetworkRequest
import com.jwu5.giphyapp.network.GiphyRequestService
import com.jwu5.giphyapp.network.model.Datum
import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList

/**
 * Created by Jiawei on 8/29/2017.
 */
class HomePresenter(private val mView: HomeView) {

    private val mGiphyNetworkRequest = GiphyNetworkRequest("https://api.giphy.com/v1/gifs/")
    private val mGiphyRequestService = GiphyRequestService(mGiphyNetworkRequest)

    fun getTrendingList(limit: Int, offset: Int) {
        val callback = object : GiphyRequestService.ResponseCallback {
            override fun onSubscribe() {
                mView.setLoading()
            }

            override fun onSuccess(datum: Datum) {
                mView.showTrendingList(datum.data)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                mView.setLoadingComplete()
            }
        }

        mGiphyRequestService.getTrending(callback, limit, offset)
    }

    fun getSearchedList(query: String?, limit: Int, offset: Int) {
        val callback = object : GiphyRequestService.ResponseCallback {
            override fun onSubscribe() {
                mView.setLoading()
            }

            override fun onSuccess(datum: Datum) {
                mView.showSearchList(datum.data)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                mView.setLoadingComplete()
            }
        }

        mGiphyRequestService.getSearchedResults(callback, query, limit, offset)
    }

    fun updateUI(items: ArrayList<GiphyModel>?, position: Int) {

        mView.updateAdapter(items, position)
    }
}
