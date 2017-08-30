package com.jwu5.giphyapp.network;

import com.jwu5.giphyapp.network.model.Datum;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class GiphyRequestService {
    private final GiphyNetworkRequest mGiphyNetworkRequest;

    public GiphyRequestService(GiphyNetworkRequest giphyNetworkRequest) {
        mGiphyNetworkRequest = giphyNetworkRequest;
    }

    public void getTrending(final ResponseCallback callback, int limit, int offset) {
        makeNetworkCall(mGiphyNetworkRequest.getTrending(limit, offset), callback);
    }

    public void getSearchedResults(final ResponseCallback callback ,String query, int limit, int offset) {
        makeNetworkCall(mGiphyNetworkRequest.getSearchedGifs(query, limit, offset), callback);
    }

    private void makeNetworkCall(Observable<Datum> observable, final ResponseCallback callback) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Datum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        callback.onSubscribe();
                    }
                    @Override
                    public void onNext(@NonNull Datum datum) {
                        callback.onSuccess(datum);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(e);
                    }
                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });
    }

    public interface ResponseCallback {
        void onSubscribe();

        void onSuccess(Datum datum);

        void onError(Throwable e);

        void onComplete();
    }

}
