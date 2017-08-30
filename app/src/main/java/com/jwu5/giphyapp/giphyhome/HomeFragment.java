package com.jwu5.giphyapp.giphyhome;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter;
import com.jwu5.giphyapp.R;
import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;
public class HomeFragment extends Fragment implements HomeView{
    public static final String TAB_NAME = "Home";
    private static final int LIMIT = 12;

    private RecyclerView mGiphyRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private GiphyRecyclerViewAdapter mGiphyRecyclerViewAdapter;

    private String mQuery = null;
    private SearchView mSearchView;

    private boolean isLoading = false;
    private boolean trending = true;
    private int mOrientation;

    private HomePresenter mHomePresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mOrientation = getActivity().getResources().getConfiguration().orientation;
        mHomePresenter = new HomePresenter(this);
        mGiphyRecyclerViewAdapter = new GiphyRecyclerViewAdapter(getActivity(), TAB_NAME, mOrientation);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOrientation = newConfig.orientation;
        mHomePresenter.updateUI(mGiphyRecyclerViewAdapter.getItems(), mGridLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mGiphyRecyclerView.setLayoutManager(mGridLayoutManager);
        mGiphyRecyclerView.setAdapter(mGiphyRecyclerViewAdapter);

        if (trending) {
            mHomePresenter.getTrendingList(LIMIT, 0);
        }

        mGiphyRecyclerView.addOnScrollListener(new PaginationScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int totalItemCount) {
                if (!isLoading) {
                    if(trending) {
                        mHomePresenter.getTrendingList(LIMIT, totalItemCount);
                    } else {
                        mHomePresenter.getSearchedList(mQuery, LIMIT, totalItemCount);
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_giphy_view, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String result = s.trim().replaceAll(" ", "-").toLowerCase();
                mQuery = s;
                mGiphyRecyclerViewAdapter.removeAll();
                mHomePresenter.getSearchedList(result, LIMIT, 0);
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
                mSearchView.setQuery("", false);
                mSearchView.setIconified(true);
                mHomePresenter.getTrendingList(LIMIT, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showTrendingList(ArrayList<GiphyModel> items) {
        if(!trending) {
            mGiphyRecyclerViewAdapter.setItemList(items);
            trending = true;
            return;
        }
        mGiphyRecyclerViewAdapter.addItems(items);
    }

    @Override
    public void showSearchList(ArrayList<GiphyModel> items) {
        if(trending) {
            mGiphyRecyclerViewAdapter.setItemList(items);
            trending = false;
            return;
        }
        mGiphyRecyclerViewAdapter.addItems(items);
    }

    @Override
    public void setLoading() {
        isLoading = true;
    }

    @Override
    public void setLoadingComplete() {
        isLoading = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGiphyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAdapter(ArrayList<GiphyModel> items, int position) {
        mGiphyRecyclerViewAdapter = new GiphyRecyclerViewAdapter(getActivity(), TAB_NAME, mOrientation);
        mGiphyRecyclerViewAdapter.setItemList(items);
        mGiphyRecyclerView.setAdapter(mGiphyRecyclerViewAdapter);
        mGiphyRecyclerView.scrollToPosition(position);
    }
}
