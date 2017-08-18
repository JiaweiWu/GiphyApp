package com.jwu5.giphyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jwu5.giphyapp.model.Datum;
import com.jwu5.giphyapp.model.GiphyModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyFragment extends Fragment {

    public static final String TAG = GiphyFragment.class.getSimpleName();

    private RecyclerView mGiphyRecyclerView;
    private CompositeDisposable mCompositeDisposable;

    public static GiphyFragment newInstance() {
        return new GiphyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GiphyService networkService = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GiphyService.class);

        networkService.getTrendingGifs(GiphyService.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Datum>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }
                @Override
                public void onNext(@NonNull Datum datum) {
                    for(GiphyModel gif : datum.getData()) {
                        Log.d(TAG, "Gif ID: " + gif.getId());
                    }
                }
                @Override
                public void onError(@NonNull Throwable e) {

                }
                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

//        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
//        mGiphyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        return v;
    }
}
