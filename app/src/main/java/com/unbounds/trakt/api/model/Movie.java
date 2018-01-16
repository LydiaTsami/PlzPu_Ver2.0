package com.unbounds.trakt.api.model;

/**
 * Created by maclir on 2015-11-21.
 */
public class Movie {
    private String title;
    private long year;
    private  long trakt;
    private  String slug;
    private  String imdb;
    private  String tmdb;
    private String url;

    public Movie(final String title, final long year) {
        this.title = title;
        this.year = year;
    }

    public Movie(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public long getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
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

        public String getTmdb() {
            return tmdb;
        }

        public  void setImdb(String imdb){this.imdb = imdb;}

        public void setTmdb(String tmdb) {
            this.tmdb = tmdb;
        }

        public void setTrakt(long trakt) {
            this.trakt = trakt;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

}
