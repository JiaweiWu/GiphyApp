package com.jwu5.giphyapp.giphyhome;

import com.jwu5.giphyapp.network.GiphyNetworkRequest;
import com.jwu5.giphyapp.network.GiphyRequestService;
import com.jwu5.giphyapp.network.model.Datum;
import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class HomePresenter {

    private final GiphyNetworkRequest mGiphyNetworkRequest = new GiphyNetworkRequest("https://api.giphy.com/v1/gifs/");
    private final GiphyRequestService mGiphyRequestService = new GiphyRequestService(mGiphyNetworkRequest);
    private final HomeView mView;

    public HomePresenter(HomeView view) {
        mView = view;
    }

    public void getTrendingList(int limit, int offset) {
        GiphyRequestService.ResponseCallback callback = new GiphyRequestService.ResponseCallback() {
            @Override
            public void onSubscribe() {
                mView.setLoading();
            }

            @Override
            public void onSuccess(Datum datum) {
                mView.showTrendingList(datum.getData());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                mView.setLoadingComplete();
            }
        };

        mGiphyRequestService.getTrending(callback, limit, offset);
    }

    public void getSearchedList(final String query, int limit, int offset) {
        GiphyRequestService.ResponseCallback callback = new GiphyRequestService.ResponseCallback() {
            @Override
            public void onSubscribe() {
                mView.setLoading();
            }

            @Override
            public void onSuccess(Datum datum) {
                mView.showSearchList(datum.getData());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                mView.setLoadingComplete();
            }
        };

        mGiphyRequestService.getSearchedResults(callback, query, limit, offset);
    }

    public void updateUI(ArrayList<GiphyModel> items, int position) {

        mView.updateAdapter(items, position);
    }
}
