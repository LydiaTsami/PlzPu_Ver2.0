package com.unbounds.trakt.Search;

/**
 * Created by lydts on 1/12/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unbounds.trakt.progress.ProgressFragment;


public class ShowFragmentPageAdapter extends FragmentPagerAdapter{
    private static final String TAG = ShowFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 4;
    public ShowFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProgressFragment();
            case 1:{
                ShowsSearchFragment showsSearchFragment = ShowsSearchFragment.createInstance(ShowsSearchFragment.Type.POPULAR);
                return showsSearchFragment;}
            case 2:{
                try {
                    ShowsSearchFragment showsSearchFragment1 = ShowsSearchFragment.createInstance(ShowsSearchFragment.Type.TRENDING);
                    return showsSearchFragment1;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Problem: " +e);
                }
            }
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
                return "Watched Progress";
            case 1:
                return "Popular";
            case 2:
                return "Trending";
        }
        return null;
    }
}