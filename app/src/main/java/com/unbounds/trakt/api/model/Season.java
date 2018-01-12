package com.unbounds.trakt.api.model;

/**
 * Created by maclir on 2015-11-21.
 */
public class Season {
    private final long number;
    private final Ids ids;
    private final Episode[] episodes;

    public Season(final long number, final Ids ids, final Episode[] episodes) {
        this.number = number;
        this.ids = ids;
        this.episodes = episodes;
    }

    public long getNumber() {
        return number;
    }

    public Ids getIds() {
        return ids;
    }

    public Episode[] getEpisodes() {
        return episodes;
    }

    public static class Ids {
        private final long trakt;
        private final long tvdb;
        private final long tmdb;
        private final Ids.Tvrage tvrage;

        public Ids(final long trakt, final long tvdb, final long tmdb, final Ids.Tvrage tvrage) {
            this.trakt = trakt;
            this.tvdb = tvdb;
            this.tmdb = tmdb;
            this.tvrage = tvrage;
        }

        public static final class Tvrage {

            public Tvrage() {
            }
        }

        public long getTrakt() {
            return trakt;
        }

        public long getTvdb() {
            return tvdb;
        }

        public long getTmdb() {
            return tmdb;
        }

        public Tvrage getTvrage() {
            return tvrage;
        }
    }
}