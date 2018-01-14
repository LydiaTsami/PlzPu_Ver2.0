package com.unbounds.trakt.api.model;

/**
 * Created by maclir on 2015-11-21.
 */
public class Movie {
    private String title;
    private final long year;
    private final Ids ids;

    public Movie(final String title, final long year, final Ids ids) {
        this.title = title;
        this.year = year;
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public long getYear() {
        return year;
    }

    public Ids getIds() {
        return ids;
    }

    public static class Ids {
        private final long trakt;
        private final String slug;
        private final String imdb;
        private final long tmdb;
        private String url;

        public Ids(final long trakt, final String slug, final String imdb, final long tmdb) {
            this.trakt = trakt;
            this.slug = slug;
            this.imdb = imdb;
            this.tmdb = tmdb;
            this.url= null;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageUrl(){
            return this.url;
        }

        public long getTrakt() {
            return trakt;
        }

        public String getSlug() {
            return slug;
        }

        public String getImdb() {
            return imdb;
        }

        public long getTmdb() {
            return tmdb;
        }

    }

}
