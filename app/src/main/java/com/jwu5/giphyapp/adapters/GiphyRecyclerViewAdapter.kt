package com.jwu5.giphyapp.adapters

import android.content.Context
import android.content.res.Configuration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jwu5.giphyapp.R
import com.jwu5.giphyapp.network.model.GiphyModel
import com.jwu5.giphyapp.viewholder.GiphyViewHolder

import java.util.ArrayList

/**
 * Created by Jiawei on 8/16/2017.
 */

class GiphyRecyclerViewAdapter(private val mContext: Context, private val TAG: String, private val mOrientation: Int) : RecyclerView.Adapter<GiphyViewHolder>() {
    var items: ArrayList<GiphyModel>? = null
        private set

    init {
        items = ArrayList<GiphyModel>()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GiphyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view: View
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.giphy_item_land, viewGroup, false)
        } else {
            view = inflater.inflate(R.layout.giphy_item, viewGroup, false)
        }
        return GiphyViewHolder(view, mContext, this, TAG)
    }

    override fun onBindViewHolder(viewHolder: GiphyViewHolder, position: Int) {
        val giphyItem = items!![position]
        viewHolder.bindGiphyItem(giphyItem)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    fun addItems(items: ArrayList<GiphyModel>?) {
        this.items!!.addAll(items!!)
        notifyDataSetChanged()
    }

    fun removeAll() {
        items = ArrayList<GiphyModel>()
        notifyDataSetChanged()
    }

    fun removeItem(item: GiphyModel?) {
        items!!.remove(item)
        notifyDataSetChanged()
    }

    fun setItemList(itemList: ArrayList<GiphyModel>?) {
        items = itemList
        notifyDataSetChanged()
    }

}
