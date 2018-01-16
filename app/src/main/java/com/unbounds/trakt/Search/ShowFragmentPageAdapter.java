package com.unbounds.trakt.Search;

/**
 * Created by lydts on 1/12/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unbounds.trakt.login.LoginManager;
import com.unbounds.trakt.progress.ProgressFragment;


public class ShowFragmentPageAdapter extends FragmentPagerAdapter{
    private static final String TAG = ShowFragmentPageAdapter.class.getSimpleName();
    private static final int FRAGMENT_COUNT = 2;
    public ShowFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if(LoginManager.getInstance().isLoggedIn()) {
            switch (position) {
                case 0: {
                    return new ProgressFragment();
                }
                case 1: {
                    PopularShowsFragment popularShowsFragment = new PopularShowsFragment();
                    return popularShowsFragment;
                }
//            case 2:{
//                    PopularShowsFragment popularShowsFragment = new PopularShowsFragment();
//                    return popularShowsFragment;
//            }
            }
        }
        else {
            switch (position) {
                case 0: {
                    PopularShowsFragment popularShowsFragment = new PopularShowsFragment();
                    return popularShowsFragment;
                }
//            case 2:{
//                    PopularShowsFragment popularShowsFragment = new PopularShowsFragment();
//                    return popularShowsFragment;
//            }
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
        if(LoginManager.getInstance().isLoggedIn()) {
            switch (position) {
                case 0:
                    return "Watched Progress";
                case 1:
                    return "Popular";
//            case 2:
//                return "Trending";
            }
        }
        else {
            switch (position) {
                case 0:
                    return "Popular";
//            case 2:
//                return "Trending";
            }
        }
        return null;
    }
}