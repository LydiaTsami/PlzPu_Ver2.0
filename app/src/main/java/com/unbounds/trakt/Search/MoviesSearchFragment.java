package com.unbounds.trakt.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.unbounds.trakt.ApiWrapper;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Movie;
import com.unbounds.trakt.json.JsonSerializer;

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lydts on 1/12/2018.
 */

public class MoviesSearchFragment extends Fragment{
    private static final String ARGUMENT_TYPE = "";

    public enum Type {
        TRENDING,
        POPULAR
    }

    public static MoviesSearchFragment createInstance(final Type type) {
        final MoviesSearchFragment fragment = new MoviesSearchFragment();
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
        final MovieAdapter adapter = new MovieAdapter(getActivity());

        if (type == Type.POPULAR) {
            ApiWrapper.getPopularMovies().map(new Func1<Movie[], MovieWrapper[]>() {
                @Override
                public MovieWrapper[] call(final Movie[] movies) {
                    final MovieWrapper[] wrappedMovie = new MovieWrapper[movies.length];
                    for (int i = 0; i < movies.length; i++) {
                        wrappedMovie[i] = new MovieWrapper(movies[i]);
                        //new LoadFromUrlTask(wrappedMovie[i].getmMovie()).execute();
                        //System.out.println("movie: " + wrappedMovie[i].getmMovie().getTitle() + " Image path: " + wrappedMovie[i].getmMovie().getIds().getImageUrl());
                    }
                    return wrappedMovie;
                }
            }).subscribe(new Action1<MovieWrapper[]>() {
                @Override
                public void call(final MovieWrapper[] movieWrappers) {
                    adapter.setData(movieWrappers);
                    gridview.setAdapter(adapter);
                }
            });
        } else if (type == Type.TRENDING) {
            ApiWrapper.getTrendingMovies().map(new Func1<Movie[], MovieWrapper[]>() {
                @Override
                public MovieWrapper[] call(final Movie[] movies) {
                    final MovieWrapper[] wrappedMovie = new MovieWrapper[movies.length];
                    for (int i = 0; i < movies.length; i++) {
                        wrappedMovie[i] = new MovieWrapper(movies[i]);
                    }
                    return wrappedMovie;
                }
            }).subscribe(new Action1<MovieWrapper[]>() {
                @Override
                public void call(final MovieWrapper[] movieWrappers) {
                    adapter.setData(movieWrappers);
                    gridview.setAdapter(adapter);
                }
            });
        }

        return view;

    }

}