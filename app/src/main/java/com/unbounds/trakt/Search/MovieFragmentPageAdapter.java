package com.unbounds.trakt.Search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lydts on 1/13/2018.
 */

public class MovieFragmentPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = MovieFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 2;
    public MovieFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                PopularMoviesFragment moviesSearchFragment = new PopularMoviesFragment();
                return moviesSearchFragment;}
            case 1:{
                TrendingMoviesFragment moviesSearchFragment2 = new TrendingMoviesFragment();
                return moviesSearchFragment2;}
        }
        return null;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Popular";
            case 1:
                return "Trending";
        }
        return null;
    }
}
