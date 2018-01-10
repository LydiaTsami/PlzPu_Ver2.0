package com.unbounds.trakt.api.model;

/**
 * Created by maclir on 2015-11-21.
 */
public class Episode {
    private final long season;
    private final long number;
    private final String title;
    private final Ids ids;

    public Episode(final long season, final long number, final String title, final Ids ids) {
        this.season = season;
        this.number = number;
        this.title = title;
        this.ids = ids;
    }

    public long getSeason() {
        return season;
    }

    public long getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public Ids getIds() {
        return ids;
    }

    public static class Ids {
        private final long trakt;
        private final long tvdb;
        private final String imdb;
        private final long tmdb;
        private final long tvrage;

        public Ids(final long trakt, final long tvdb, final String imdb, final long tmdb, final long tvrage) {
            this.trakt = trakt;
            this.tvdb = tvdb;
            this.imdb = imdb;
            this.tmdb = tmdb;
            this.tvrage = tvrage;
        }

        public long getTrakt() {
            return trakt;
        }

        public long getTvdb() {
            return tvdb;
        }

        public String getImdb() {
            return imdb;
        }

        public long getTmdb() {
            return tmdb;
        }

        public long getTvrage() {
            return tvrage;
        }
    }
}
