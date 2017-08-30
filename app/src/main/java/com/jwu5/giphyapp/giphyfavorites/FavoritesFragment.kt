package com.jwu5.giphyapp.giphyfavorites

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter
import com.jwu5.giphyapp.R
import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList

/**
 * Created by Jiawei on 8/15/2017.
 */
class FavoritesFragment : Fragment(), FavoritesView {

    private var mGiphyRecyclerView: RecyclerView? = null
    private var mGiphyRecyclerViewAdapter: GiphyRecyclerViewAdapter? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var mFavoritesPresenter: FavoritesPresenter? = null
    private var mOrientation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFavoritesPresenter = FavoritesPresenter(this, activity)
        mOrientation = activity.resources.configuration.orientation
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        mOrientation = newConfig!!.orientation
        mFavoritesPresenter!!.updateUI(mGridLayoutManager!!.findFirstVisibleItemPosition())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_giphy_view, container, false)

        mGiphyRecyclerView = v.findViewById(R.id.fragment_giphy_view_recycler_view) as RecyclerView
        mGridLayoutManager = GridLayoutManager(activity, 2)
        mGiphyRecyclerView!!.layoutManager = mGridLayoutManager
        mFavoritesPresenter!!.updateUI(mGridLayoutManager!!.findFirstVisibleItemPosition())
        return v
    }

    override fun onResume() {
        super.onResume()
        mFavoritesPresenter!!.updateUI(mGridLayoutManager!!.findFirstVisibleItemPosition())
    }

    override fun updateAdapter(items: ArrayList<GiphyModel>?, position: Int) {
        mGiphyRecyclerViewAdapter = GiphyRecyclerViewAdapter(activity, TAB_NAME, mOrientation)
        mGiphyRecyclerViewAdapter!!.setItemList(items)
        mGiphyRecyclerView!!.adapter = mGiphyRecyclerViewAdapter
        mGiphyRecyclerView!!.scrollToPosition(position)
    }

    companion object {
        val TAB_NAME = "Favorites"

        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

}
