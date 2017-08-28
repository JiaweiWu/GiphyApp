package com.jwu5.giphyapp;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jwu5.giphyapp.model.Datum;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jiawei on 8/15/2017.
 */
public class GiphyNetworkRequest {
    private GiphyService mGiphyService;

    public GiphyNetworkRequest(String urlEndPoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlEndPoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGiphyService = retrofit.create(GiphyService.class);
    }


    public Observable<Datum> getTrending(int limit, int offset) {
        return mGiphyService.getTrendingGifs(GiphyService.API_KEY, limit, offset);
    }

    public Observable<Datum> getSearchedGifs(String queryTerm, int limit, int offset) {
        return mGiphyService.getSearchedGifs(GiphyService.API_KEY, queryTerm, limit, offset);
    }
}
