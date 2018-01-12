package com.unbounds.trakt.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.unbounds.trakt.ApiWrapper;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Show;
import com.unbounds.trakt.json.JsonSerializer;

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lydts on 1/12/2018.
 */

public class ShowsSearchFragment extends Fragment {

    private static final String ARGUMENT_TYPE = "POPULAR";

    public enum Type {
        TRENDING,
        POPULAR
    }

    public static ShowsSearchFragment createInstance(final Type type) {
        final ShowsSearchFragment fragment = new ShowsSearchFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TYPE, JsonSerializer.toJson(type));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        final GridView gridview = (GridView) view.findViewById(R.id.search_gridview);

        final Type type = JsonSerializer.fromJson(getArguments().getString(ARGUMENT_TYPE), Type.class);
        final ShowAdapter adapter = new ShowAdapter(getActivity());

        if (type == Type.POPULAR) {
            System.out.println("Got In");
            ApiWrapper.getPopularShows().map(new Func1<Show[], ShowWrapper[]>() {
                @Override
                public ShowWrapper[] call(final Show[] shows) {
                    final ShowWrapper[] wrappedShow = new ShowWrapper[shows.length];
                    for (int i = 0; i < shows.length; i++) {
                        wrappedShow[i] = new ShowWrapper(shows[i]);
                    }
                    return wrappedShow;
                }
            }).subscribe(new Action1<ShowWrapper[]>() {
                @Override
                public void call(final ShowWrapper[] showWrappers) {
                    adapter.setData(showWrappers);
                    gridview.setAdapter(adapter);
                }
            });
        } else if (type == Type.TRENDING) {
            ApiWrapper.getTrendingShows().map(new Func1<Show[], ShowWrapper[]>() {
                @Override
                public ShowWrapper[] call(final Show[] shows) {
                    final ShowWrapper[] wrappedShow = new ShowWrapper[shows.length];
                    for (int i = 0; i < shows.length; i++) {
                        wrappedShow[i] = new ShowWrapper(shows[i]);
                    }
                    return wrappedShow;
                }
            }).subscribe(new Action1<ShowWrapper[]>() {
                @Override
                public void call(final ShowWrapper[] showWrappers) {
                    adapter.setData(showWrappers);
                    gridview.setAdapter(adapter);
                }
            });
        }

        return view;

    }
}