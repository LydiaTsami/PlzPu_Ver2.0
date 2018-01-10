package com.unbounds.trakt.api.model.request;

import com.unbounds.trakt.api.model.Episode;
import com.unbounds.trakt.api.model.Movie;
import com.unbounds.trakt.api.model.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maclir on 2015-11-21.
 */
public class WatchedItems {
    private final List<Movie> movies;
    private final List<Show> shows;
    private final List<Episode> episodes;

    public WatchedItems(final List<Movie> movies, final List<Show> shows, final List<Episode> episodes) {
        this.movies = movies;
        this.shows = shows;
        this.episodes = episodes;
    }

    public static class Builder {

        private final List<Movie> mMovies = new ArrayList<>();
        private final List<Show> mShows = new ArrayList<>();
        private final List<Episode> mEpisodes = new ArrayList<>();

        public Builder addMovie(final Movie movie) {
            mMovies.add(movie);
            return this;
        }

        public Builder addShow(final Show show) {
            mShows.add(show);
            return this;
        }

        public Builder addEpisode(final Episode episode) {
            mEpisodes.add(episode);
            return this;
        }

        public WatchedItems create() {
            return new WatchedItems(
                    mMovies.isEmpty() ? null : mMovies,
                    mShows.isEmpty() ? null : mShows,
                    mEpisodes.isEmpty() ? null : mEpisodes
            );
        }
    }
}
