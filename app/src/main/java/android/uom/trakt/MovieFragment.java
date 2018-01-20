package android.uom.trakt;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.uom.trakt.Search.MovieFragmentPageAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lydts on 1/13/2018.
 */

public class MovieFragment extends Fragment{
    private static final String TAG = MovieFragment.class.getSimpleName();
    private MovieFragmentPageAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public MovieFragment() {
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(android.uom.trakt.R.layout.fragment_movie, container, false);

        adapter = new MovieFragmentPageAdapter(getChildFragmentManager());
        tabLayout = (TabLayout)view.findViewById(android.uom.trakt.R.id.tabs_movie);
        viewPager = (ViewPager)view.findViewById(android.uom.trakt.R.id.view_pager_movie);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout)view.findViewById(android.uom.trakt.R.id.tabs_movie);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
