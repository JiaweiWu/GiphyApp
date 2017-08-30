package com.jwu5.giphyapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

import com.jwu5.giphyapp.giphyfavorites.FavoritesFragment
import com.jwu5.giphyapp.giphyhome.HomeFragment

import java.util.HashMap

/**
 * Created by Jiawei on 8/15/2017.
 */
class GiphyFragmentPagerAdapter(private val mFragmentManager: FragmentManager) : FragmentPagerAdapter(mFragmentManager) {

    private val PAGE_COUNT = 2
    private val tabTitles = arrayOf(HomeFragment.TAB_NAME, FavoritesFragment.TAB_NAME)
    private val mFragmentTags: MutableMap<Int, String>

    init {
        mFragmentTags = HashMap<Int, String>()
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return HomeFragment.newInstance()
        } else {
            return FavoritesFragment.newInstance()
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val `object` = super.instantiateItem(container, position)
        if (`object` is Fragment) {
            val tag = `object`.tag
            mFragmentTags.put(position, tag)
        }
        return `object`
    }

    fun getFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val tag = mFragmentTags[position]
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag)
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}
