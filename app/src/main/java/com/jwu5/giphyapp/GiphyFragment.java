package com.jwu5.giphyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.model.Datum;
import com.jwu5.giphyapp.model.GiphyModel;

import java.util.ArrayList;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GiphyFragment extends Fragment {

    public static final String TAG = GiphyFragment.class.getSimpleName();

    private RecyclerView mGiphyRecyclerView;
    private ArrayList<GiphyModel> mItems = new ArrayList<>();
    private GiphyNetworkRequest mGiphyNetworkRequest;

    public static GiphyFragment newInstance() {
        return new GiphyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mGiphyNetworkRequest = new GiphyNetworkRequest("https://api.giphy.com/v1/gifs/");

        mGiphyNetworkRequest.getTrending(12, 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Datum>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }
                @Override
                public void onNext(@NonNull Datum datum) {
                    mItems = datum.getData();
                    setupAdapter();
                }
                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                    Log.e(TAG, TAG + ": Error");
                }
                @Override
                public void onComplete() {
                    Log.d(TAG, TAG + ": Complete");
                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
        mGiphyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_giphy_view, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "THIS IS THE STRING: " + s);
                String result = s.trim().replaceAll(" ", "-").toLowerCase();
                mGiphyNetworkRequest.getSearchedGifs(result, 12, 0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Datum>() {
                                       @Override
                                       public void onSubscribe(@NonNull Disposable d) {}
                                       @Override
                                       public void onNext(@NonNull Datum datum) {
                                           mItems = datum.getData();
                                           setupAdapter();
                                       }
                                       @Override
                                       public void onError(@NonNull Throwable e) {}
                                       @Override
                                       public void onComplete() {}
                                   });
                                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    private void setupAdapter() {
        if (isAdded()) {
            mGiphyRecyclerView.setAdapter(new GiphyRecyclerViewAdapter(mItems, getActivity()));
        }
    }
}
