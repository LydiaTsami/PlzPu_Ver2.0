package com.unbounds.trakt.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.R;
import com.unbounds.trakt.api.model.Movie;

import java.util.ArrayList;

/**
 * Created by lydts on 1/12/2018.
 */

public class PopularMoviesFragment extends Fragment implements IMoviesLoadedListener ,AdapterView.OnItemClickListener{
    private static int ARGUMENT_TYPE;
    private PopularMovieAdapter adapter;
    public static ArrayList<Movie> mMovieList;
    private GridView gridview;

    int page_count = 1;
    int max_pages = 100;  //initially

    public PopularMoviesFragment(){
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mMovieList = new ArrayList<>();
        final View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        gridview = (GridView) view.findViewById(R.id.search_gridview);
        gridview.setOnScrollListener(onScrollListener());
        gridview.setOnItemClickListener(this);

        String url = BuildConfig.BASE_API_URL + "/movies/popular?page=" +page_count+"&extended=full";
        System.out.println("Got in Popular " + url);
        getDataFromUrl(url);
        adapter = new PopularMovieAdapter(getActivity(),mMovieList);
        System.out.println("MovieList: "+adapter.mMovieList);
        gridview.setAdapter(adapter);

        return view;

    }

    @Override
    public void onPopularMoviesLoaded(ArrayList<Movie> movies, int scroll) {
        mMovieList.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    private void getDataFromUrl(String url) {
        new LoadMoviesFromUrlTask(getActivity(), url, this).execute();
    }

    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println("ScrollListener");
                int threshold = 1;
                int count = gridview.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (gridview.getLastVisiblePosition() >= count - threshold && page_count < max_pages) {
                        Log.i("TAG", "loading more data");
                        page_count++;
                        // Execute LoadMoreDataTask AsyncTask
                        String url = BuildConfig.BASE_API_URL + "/movies/popular?page=" +page_count+ "&extended=full";
                        getDataFromUrl(url);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Movie movie = mMovieList.get(position);

        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("year",movie.getYear());
        intent.putExtra("tagline",movie.getTagline());
        intent.putExtra("overview",movie.getOverview());
        intent.putExtra("genres",movie.getGenres());
        intent.putExtra("homepage",movie.getHomepage());
        intent.putExtra("imdb",movie.getImdb());
        intent.putExtra("tmdb",movie.getTmdb());
        intent.putExtra("trailer",movie.getTrailer());
        intent.putExtra("rating",movie.getRating());
        intent.putExtra("runtime",movie.getRuntime());

        startActivity(intent);
    }


}