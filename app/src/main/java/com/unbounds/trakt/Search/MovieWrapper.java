package com.unbounds.trakt.Search;

import com.unbounds.trakt.api.model.Movie;

/**
 * Created by lydts on 1/12/2018.
 */

public class MovieWrapper {

    private final Long mWatchers;
    private final Movie mMovie;

    MovieWrapper(final Long watchers, final Movie movie) {
        mWatchers = watchers;
        mMovie = movie;
    }

    public MovieWrapper(final Movie movie) {
        mWatchers = null;
        mMovie = movie;
        setMovieUrl();
    }

    public void setMovieUrl(){
        System.out.println("mMovie "+ mMovie.getTitle());
        new LoadFromUrlTask(mMovie).execute();
    }

    public long getWatchers() {
        return mWatchers;
    }

    public Movie getmMovie() {
        return mMovie;
    }

    public boolean hasWatchers() {
        return mWatchers == null ? false : true;
    }
}
