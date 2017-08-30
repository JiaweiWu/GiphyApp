package com.jwu5.giphyapp.viewholder

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jwu5.giphyapp.adapters.GiphyRecyclerViewAdapter
import com.jwu5.giphyapp.R
import com.jwu5.giphyapp.network.model.GiphyModel

/**
 * Created by Jiawei on 8/16/2017.
 */

class GiphyViewHolder(itemView: View, private val mContext: Context, private val mAdapter: GiphyRecyclerViewAdapter, override val tag: String) : RecyclerView.ViewHolder(itemView), ViewHolderView {
    private val mItemImageView: ImageView
    private val mFavoriteButton: ImageButton
    private val mProgressBar: ProgressBar
    private val WHITE = Color.WHITE
    private val PINK = Color.argb(255, 255, 102, 153)

    protected var mViewHolderPresenter: ViewHolderPresenter

    init {
        mItemImageView = itemView.findViewById(R.id.Giphy_image_view) as ImageView
        mProgressBar = itemView.findViewById(R.id.progress_bar) as ProgressBar
        mFavoriteButton = itemView.findViewById(R.id.Giphy_image_button) as ImageButton
        mViewHolderPresenter = ViewHolderPresenter(this, mContext)
    }

    fun bindGiphyItem(item: GiphyModel) {
        Glide.with(mContext).load(item.url).listener(object : RequestListener<String, GlideDrawable> {
            override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                mProgressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                mProgressBar.visibility = View.GONE
                return false
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mItemImageView)

        mViewHolderPresenter.setFavoriteIcon(item)

        mFavoriteButton.setOnClickListener { mViewHolderPresenter.saveToFavorites(item) }
    }

    override fun getInFavoritesColor(): Int {
        return PINK;
    }

    override fun getNotInFavoritesColor(): Int {
        return WHITE;
    }

    override fun setButtonColor(color: Int) {
        mFavoriteButton.setColorFilter(color)
    }

    override fun removeItemFromView(item: GiphyModel) {
        mAdapter.removeItem(item)
    }

    override fun updateAdapter() {
        mAdapter.notifyItemChanged(adapterPosition)
    }

}
