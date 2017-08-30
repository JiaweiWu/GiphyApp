package com.jwu5.giphyapp.giphyfavorites;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter;
import com.jwu5.giphyapp.R;
import com.jwu5.giphyapp.network.model.GiphyModel;

import java.util.ArrayList;

/**
 * Created by Jiawei on 8/15/2017.
 */
public class FavoritesFragment extends Fragment implements FavoritesView{
    public static final String TAB_NAME = "Favorites";

    private RecyclerView mGiphyRecyclerView;
    private GiphyRecyclerViewAdapter mGiphyRecyclerViewAdapter;
    private GridLayoutManager mGridLayoutManager;
    private FavoritesPresenter mFavoritesPresenter;
    private int mOrientation;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoritesPresenter = new FavoritesPresenter(this, getActivity());
        mOrientation = getActivity().getResources().getConfiguration().orientation;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOrientation = newConfig.orientation;
        mFavoritesPresenter.updateUI(mGridLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mGiphyRecyclerView.setLayoutManager(mGridLayoutManager);
        mFavoritesPresenter.updateUI(mGridLayoutManager.findFirstVisibleItemPosition());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFavoritesPresenter.updateUI(mGridLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    public void updateAdapter(ArrayList<GiphyModel> items, int position) {
        mGiphyRecyclerViewAdapter = new GiphyRecyclerViewAdapter(getActivity(), TAB_NAME, mOrientation);
        mGiphyRecyclerViewAdapter.setItemList(items);
        mGiphyRecyclerView.setAdapter(mGiphyRecyclerViewAdapter);
        mGiphyRecyclerView.scrollToPosition(position);
    }

}
