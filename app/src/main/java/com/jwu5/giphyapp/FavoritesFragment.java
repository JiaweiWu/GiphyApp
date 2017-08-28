package com.jwu5.giphyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwu5.giphyapp.model.GiphyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Jiawei on 8/15/2017.
 */
public class FavoritesFragment extends Fragment {
    private LinkedHashMap<String, GiphyModel> mFavorites;
    private RecyclerView mGiphyRecyclerView;
    public GiphyRecyclerViewAdapter mGiphyRecyclerViewAdapter;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavorites = new LinkedHashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giphy_view, container, false);

        mGiphyRecyclerView = (RecyclerView)v.findViewById(R.id.fragment_giphy_view_recycler_view);
        mGiphyRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        UpdateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFavorites = ((MainActivity) getActivity()).getFavorites();
        UpdateUI();
    }

    public void UpdateUI() {
        ArrayList<GiphyModel> tempList = new ArrayList<>();
        Set<String> keys = mFavorites.keySet();

        for (String k : keys) {
            tempList.add(mFavorites.get(k));
        }
        mGiphyRecyclerViewAdapter = new GiphyRecyclerViewAdapter(tempList, getActivity());
        mGiphyRecyclerView.setAdapter(mGiphyRecyclerViewAdapter);
    }
}
