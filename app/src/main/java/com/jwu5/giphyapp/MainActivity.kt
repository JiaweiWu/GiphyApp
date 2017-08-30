package com.jwu5.giphyapp

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.jwu5.giphyapp.adapters.GiphyFragmentPagerAdapter
import com.jwu5.giphyapp.dataSingleton.SavedFavorites
import com.jwu5.giphyapp.network.model.GiphyModel
import java.util.LinkedHashMap

/*
Freshworks Android Giphy Sample App

The Assignment:
Use the Giphy API to create an application which allows user's to search, view, and favourite gifs.

Specifications:
There will be one activity with two Fragments.
The fragments should be swipeable using a tablayout and viewpager.

First Fragment:
Contains a search bar at the top.
Contains a recycler view that displays searched gifs.
Loading indicator while searching.
The default items in the recycler view should be the trending gifs.

Second Fragment:
Contains a recycler view that displays a grid of favourited gifs stored locally.

List/Grid Item:
Should contain a view of the gif running.
Should contain a favourite button which allows favouriting and unfavouriting.
The favourited items should be stored locally on the device.

Bonus:
Use Android MVP or MVVVM architecture.
Use RxJava
Use Kotlin instead of Java.
Add pagination (infinite scrolling) to the gif lists.
 */

class MainActivity : AppCompatActivity() {
    private var mGiphyFragmentPagerAdapter: GiphyFragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        mGiphyFragmentPagerAdapter = GiphyFragmentPagerAdapter(supportFragmentManager)
        val viewPager = findViewById(R.id.main_activity_view_pager) as ViewPager?
        viewPager!!.adapter = mGiphyFragmentPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixel: Int) {
            }

            override fun onPageSelected(position: Int) {
                val fragment = mGiphyFragmentPagerAdapter!!.getFragment(position)
                fragment?.onResume()
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        val tabLayout = findViewById(R.id.main_activity_tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
        SavedFavorites.instance.favorites = utils.loadFromFile(FILENAME, this)
    }

    public override fun onPause() {
        super.onPause()
        utils.saveInFile(SavedFavorites.instance.favorites, FILENAME, this)
    }

    companion object {
        private val FILENAME = "file.sav"
    }
}
