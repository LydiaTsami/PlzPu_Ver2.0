package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Episode;
import com.unbounds.trakt.api.model.Movie;
import com.unbounds.trakt.api.model.Season;
import com.unbounds.trakt.api.model.Show;

/**
 * Created by maclir on 2015-11-21.
 */
public final class AddHistory {
    private final Added added;
    private final NotFound notFound;

    public AddHistory(final Added added, final NotFound notFound) {
        this.added = added;
        this.notFound = notFound;
    }

    public Added getAdded() {
        return added;
    }

    public NotFound getNotFound() {
        return notFound;
    }

    public static final class Added {
        private final long movies;
        private final long episodes;

        private Added(final long movies, final long episodes) {
            this.movies = movies;
            this.episodes = episodes;
        }

        public long getMovies() {
            return movies;
        }

        public long getEpisodes() {
            return episodes;
        }
    }

    public static final class NotFound {
        private final Movie[] movies;
        private final Show[] shows;
        private final Season[] seasons;
        private final Episode[] episodes;

        public NotFound(final Movie[] movies, final Show[] shows, final Season[] seasons, final Episode[] episodes) {
            this.movies = movies;
            this.shows = shows;
            this.seasons = seasons;
            this.episodes = episodes;
        }

        public Movie[] getMovies() {
            return movies;
        }

        public Show[] getShows() {
            return shows;
        }

        public Season[] getSeasons() {
            return seasons;
        }

        public Episode[] getEpisodes() {
            return episodes;
        }
    }
}
