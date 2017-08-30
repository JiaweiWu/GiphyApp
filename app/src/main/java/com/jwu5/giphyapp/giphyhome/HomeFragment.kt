package com.jwu5.giphyapp.giphyhome

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter
import com.jwu5.giphyapp.R
import com.jwu5.giphyapp.network.model.GiphyModel

import java.util.ArrayList

class HomeFragment : Fragment(), HomeView {

    private var mGiphyRecyclerView: RecyclerView? = null
    private var mGridLayoutManager: GridLayoutManager? = null
    private var mGiphyRecyclerViewAdapter: GiphyRecyclerViewAdapter? = null

    private var mQuery: String? = null
    private var mSearchView: SearchView? = null

    private var isLoading = false
    private var trending = true
    private var mOrientation: Int = 0

    private var mHomePresenter: HomePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)

        mOrientation = activity.resources.configuration.orientation
        mHomePresenter = HomePresenter(this)
        mGiphyRecyclerViewAdapter = GiphyRecyclerViewAdapter(activity, TAB_NAME, mOrientation)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        mOrientation = newConfig!!.orientation
        mHomePresenter!!.updateUI(mGiphyRecyclerViewAdapter!!.items, mGridLayoutManager!!.findFirstVisibleItemPosition())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_giphy_view, container, false)

        mGiphyRecyclerView = v.findViewById(R.id.fragment_giphy_view_recycler_view) as RecyclerView
        mGridLayoutManager = GridLayoutManager(activity, 2)
        mGiphyRecyclerView!!.layoutManager = mGridLayoutManager
        mGiphyRecyclerView!!.adapter = mGiphyRecyclerViewAdapter

        if (trending) {
            mHomePresenter!!.getTrendingList(LIMIT, 0)
        }

        mGiphyRecyclerView!!.addOnScrollListener(object : PaginationScrollListener(mGridLayoutManager!!) {
            override fun onLoadMore(totalItemCount: Int) {
                if (!isLoading) {
                    if (trending) {
                        mHomePresenter!!.getTrendingList(LIMIT, totalItemCount)
                    } else {
                        mHomePresenter!!.getSearchedList(mQuery, LIMIT, totalItemCount)
                    }
                }
            }
        })
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater!!.inflate(R.menu.fragment_giphy_view, menu)
        val searchItem = menu!!.findItem(R.id.menu_item_search)
        mSearchView = searchItem.actionView as SearchView

        mSearchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                val result = s.trim { it <= ' ' }.replace(" ".toRegex(), "-").toLowerCase()
                mQuery = s
                mGiphyRecyclerViewAdapter!!.removeAll()
                mHomePresenter!!.getSearchedList(result, LIMIT, 0)
                mSearchView!!.clearFocus()
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_item_clear -> {
                mSearchView!!.setQuery("", false)
                mSearchView!!.isIconified = true
                mHomePresenter!!.getTrendingList(LIMIT, 0)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showTrendingList(items: ArrayList<GiphyModel>?) {
        if (!trending) {
            mGiphyRecyclerViewAdapter!!.setItemList(items)
            trending = true
            return
        }
        mGiphyRecyclerViewAdapter!!.addItems(items)
    }

    override fun showSearchList(items: ArrayList<GiphyModel>?) {
        if (trending) {
            mGiphyRecyclerViewAdapter!!.setItemList(items)
            trending = false
            return
        }
        mGiphyRecyclerViewAdapter!!.addItems(items)
    }

    override fun setLoading() {
        isLoading = true
    }

    override fun setLoadingComplete() {
        isLoading = false
    }

    override fun onResume() {
        super.onResume()
        mGiphyRecyclerViewAdapter!!.notifyDataSetChanged()
    }

    override fun updateAdapter(items: ArrayList<GiphyModel>?, position: Int) {
        mGiphyRecyclerViewAdapter = GiphyRecyclerViewAdapter(activity, TAB_NAME, mOrientation)
        mGiphyRecyclerViewAdapter!!.setItemList(items)
        mGiphyRecyclerView!!.adapter = mGiphyRecyclerViewAdapter
        mGiphyRecyclerView!!.scrollToPosition(position)
    }

    companion object {
        val TAB_NAME = "Home"
        private val LIMIT = 12

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
