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
import android.widget.Button;

import com.jwu5.giphyapp.model.Datum;
import com.jwu5.giphyapp.model.GiphyModel;

import java.util.ArrayList;

import io.reactivex.Observable;
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
    private GridLayoutManager mGridLayoutManager;
    private GiphyRecyclerViewAdapter mGiphyRecyclerViewAdapter;
    private boolean trending = true;
    private String mQuery = null;
    private SearchView mSearchView;

    private boolean isLoading = false;

    public static GiphyFragment newInstance() {
        return new GiphyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mGiphyNetworkRequest = new GiphyNetworkRequest("https://api.giphy.com/v1/gifs/");
        mGiphyRecyclerViewAdapter = new GiphyRecyclerViewAdapter(mItems, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mGiphyRecyclerView.setLayoutManager(mGridLayoutManager);
        mGiphyRecyclerView.setAdapter(mGiphyRecyclerViewAdapter);

        mGiphyRecyclerView.addOnScrollListener(new PaginationScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int totalItemCount) {
                if (!isLoading) {
                    if(trending) {
                        makeGiphyNetworkCall(mGiphyNetworkRequest.getTrending(6, totalItemCount));
                    } else {
                        makeGiphyNetworkCall(mGiphyNetworkRequest.getSearchedGifs(mQuery, 6, totalItemCount));
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGiphyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_giphy_view, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) searchItem.getActionView();

        makeGiphyNetworkCall(mGiphyNetworkRequest.getTrending(6, 0));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String result = s.trim().replaceAll(" ", "-").toLowerCase();
                mGiphyRecyclerViewAdapter.removeAll();
                mQuery = s;
                trending = false;
                makeGiphyNetworkCall(mGiphyNetworkRequest.getSearchedGifs(result, 6, 0));

                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_clear:
                if(!trending) {
                    mSearchView.setQuery("", false);
                    mSearchView.setIconified(true);
                    trending = true;
                    mGiphyRecyclerViewAdapter.removeAll();
                    makeGiphyNetworkCall(mGiphyNetworkRequest.getTrending(6, 0));
                    return true;
                } else {
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void makeGiphyNetworkCall(Observable<Datum> observable) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Datum>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        isLoading = true;
                    }
                    @Override
                    public void onNext(@NonNull Datum datum) {
                        mGiphyRecyclerViewAdapter.addItems(datum.getData());
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Log.e(TAG, TAG + ": Error");
                    }
                    @Override
                    public void onComplete() {
                        isLoading = false;
                        Log.d(TAG, TAG + ": Complete");
                    }
                });
    }
}
