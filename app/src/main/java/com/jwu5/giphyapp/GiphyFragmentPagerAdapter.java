package com.jwu5.giphyapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.jwu5.giphyapp.model.Datum;
import com.jwu5.giphyapp.model.GiphyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jiawei on 8/15/2017.
 */
public class GiphyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Home", "Favorites"};
    private FragmentManager mFragmentManager;
    private Map<Integer, String> mFragmentTags;

    private Context mContext;
    private ArrayList<GiphyModel> mFavorites;

    public GiphyFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<GiphyModel> favorites) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<>();
        mContext = context;
        mFavorites = favorites;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return GiphyFragment.newInstance();
        }
        else {
            return FavoritesFragment.newInstance();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
