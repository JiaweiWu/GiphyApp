package com.jwu5.giphyapp.network

import com.jwu5.giphyapp.network.model.Datum

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Jiawei on 8/29/2017.
 */
class GiphyRequestService(private val mGiphyNetworkRequest: GiphyNetworkRequest) {

    fun getTrending(callback: ResponseCallback, limit: Int, offset: Int) {
        makeNetworkCall(mGiphyNetworkRequest.getTrending(limit, offset), callback)
    }

    fun getSearchedResults(callback: ResponseCallback, query: String?, limit: Int, offset: Int) {
        makeNetworkCall(mGiphyNetworkRequest.getSearchedGifs(query, limit, offset), callback)
    }

    private fun makeNetworkCall(observable: Observable<Datum>, callback: ResponseCallback) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Datum> {
            override fun onSubscribe(@NonNull d: Disposable) {
                callback.onSubscribe()
            }

            override fun onNext(@NonNull datum: Datum) {
                callback.onSuccess(datum)
            }

            override fun onError(@NonNull e: Throwable) {
                callback.onError(e)
            }

            override fun onComplete() {
                callback.onComplete()
            }
        })
    }

    interface ResponseCallback {
        fun onSubscribe()

        fun onSuccess(datum: Datum)

        fun onError(e: Throwable)

        fun onComplete()
    }

}
