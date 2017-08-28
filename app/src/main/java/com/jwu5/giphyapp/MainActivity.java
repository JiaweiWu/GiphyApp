package com.jwu5.giphyapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwu5.giphyapp.model.Datum;
import com.jwu5.giphyapp.model.GiphyModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<GiphyModel> mFavorites;
    private GiphyFragmentPagerAdapter mGiphyFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFavorites = new ArrayList<>();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGiphyFragmentPagerAdapter = new GiphyFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this, mFavorites);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        viewPager.setAdapter(mGiphyFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixel) {
            }
            @Override
            public void onPageSelected(int position) {
                Fragment fragment = mGiphyFragmentPagerAdapter.getFragment(position);
                if (fragment != null) {
                    fragment.onResume();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = (TabLayout)findViewById(R.id.main_activity_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    protected void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(mFavorites, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<List<GiphyModel>>() {}.getType();

            mFavorites = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addFavorite(GiphyModel item) {
        mFavorites.add(item);
    }

    public ArrayList<GiphyModel> getFavorites() {
        return mFavorites;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveInFile();
    }
}
