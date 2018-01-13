package com.unbounds.trakt.Search;

/**
 * Created by lydts on 1/12/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CustomFragmentPageAdapter extends FragmentPagerAdapter{
    private static final String TAG = CustomFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 4;
    public CustomFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ShowsSearchFragment showsSearchFragment = ShowsSearchFragment.createInstance(ShowsSearchFragment.Type.POPULAR);
                return showsSearchFragment;
            case 1:
                ShowsSearchFragment showsSearchFragment1 = ShowsSearchFragment.createInstance(ShowsSearchFragment.Type.TRENDING);
                return showsSearchFragment1;
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
                return "Songs";
            case 1:
                return "Playlists";
            case 2:
                return "Albums";
            case 3:
                return "Artists";
        }
        return null;
    }
}