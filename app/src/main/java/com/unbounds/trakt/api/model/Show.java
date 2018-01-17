package com.unbounds.trakt.api.model;

import java.io.Serializable;

/**
 * Created by lydts on 2017-12-21.
 */
public class Show implements Serializable {
    private  String title;
    private  long year;
    private  String overview;
    private  String status;
    private  Images images;
    private  Ids ids;
    private String url;
    private  long trakt;
    private  String slug;
    private  long tvdb;
    private  String imdb,trailer;
    private  String tmdb;
    private  long tvrage;
    private int runtime;
    private double rating;

    public Show(final String title, final String overview, final long year, String status, final Images images, final Ids ids) {
        this.title = title;
        this.year = year;
        this.images = images;
        this.ids = ids;
        this.overview = overview;
        this.status = status;
    }

    public Show(){       new Ids();}

    public void setTrakt(long trakt) {
        this.trakt = trakt;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public void setTmdb(String tmdb) {
        this.tmdb = tmdb;
    }

    public long getTrakt() {
        return trakt;
    }

    public String getSlug() {
        return slug;
    }

    public long getTvdb() {
        return tvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public String getTmdb() {
        return tmdb;
    }

    public long getTvrage() {
        return tvrage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public String getImageUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
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

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Show)) return false;

        final Show show = (Show) o;

        return !(ids != null ? !ids.equals(show.ids) : show.ids != null);

    }

    public static class Images {
        private final Banner banner;
        private final Poster poster;
        private final Thumb thumb;
        private final Logo logo;
        private final Fanart fanart;
        private final Clearart clearart;

        public Images(final Banner banner, final Poster poster, final Thumb thumb, final Logo logo, final Fanart fanart, final Clearart clearart) {
            this.banner = banner;
            this.poster = poster;
            this.thumb = thumb;
            this.logo = logo;
            this.fanart = fanart;
            this.clearart = clearart;
        }

        public Banner getBanner() {
            return banner;
        }

        public Poster getPoster() {
            return poster;
        }

        public Thumb getThumb() {
            return thumb;
        }

        public Logo getLogo() {
            return logo;
        }

        public Fanart getFanart() {
            return fanart;
        }

        public Clearart getClearart() {
            return clearart;
        }

        public static class Banner {
            private final String full;

            public Banner(final String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }
        }

        public static class Clearart {
            private final String full;

            public Clearart(final String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }
        }

        public static class Fanart {
            private final String full;
            private final String medium;
            private final String thumb;

            public Fanart(final String full, final String medium, final String thumb) {
                this.full = full;
                this.medium = medium;
                this.thumb = thumb;
            }

            public String getFull() {
                return full;
            }

            public String getMedium() {
                return medium;
            }

            public String getThumb() {
                return thumb;
            }
        }

        public static class Logo {
            private final String full;

            public Logo(final String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }
        }

        public static class Poster {
            private final String full;
            private final String medium;
            private final String thumb;

            public Poster(final String full, final String medium, final String thumb) {
                this.full = full;
                this.medium = medium;
                this.thumb = thumb;
            }

            public String getFull() {
                return full;
            }

            public String getMedium() {
                return medium;
            }

            public String getThumb() {
                return thumb;
            }
        }

        public static class Thumb {
            private final String full;

            public Thumb(final String full) {
                this.full = full;
            }

            public String getFull() {
                return full;
            }
        }
    }

    public static class Ids {
        private  long trakt;
        private  String slug;
        private  long tvdb;
        private  String imdb;
        private  long tmdb;
        private  long tvrage;


        public Ids(final long trakt, final String slug, final long tvdb, final String imdb, final long tmdb, final long tvrage) {
            this.trakt = trakt;
            this.slug = slug;
            this.tvdb = tvdb;
            this.imdb = imdb;
            this.tmdb = tmdb;
            this.tvrage = tvrage;
        }

        public Ids(){}

        public long getTrakt() {
            return trakt;
        }

        public void setTrakt(long trakt) {
            this.trakt = trakt;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public long getTvdb() {
            return tvdb;
        }

        public void setTvdb(long tvdb) {
            this.tvdb = tvdb;
        }

        public String getImdb() {
            return imdb;
        }

        public void setImdb(String imdb) {
            this.imdb = imdb;
        }

        public long getTmdb() {
            return tmdb;
        }

        public void setTmdb(long tmdb) {
            this.tmdb = tmdb;
        }

        public long getTvrage() {
            return tvrage;
        }

        public void setTvrage(long tvrage) {
            this.tvrage = tvrage;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Ids)) return false;

            final Ids ids = (Ids) o;

            if (trakt != ids.trakt) return false;
            return !(slug != null ? !slug.equals(ids.slug) : ids.slug != null);

        }

        @Override
        public int hashCode() {
            int result = (int) (trakt ^ (trakt >>> 32));
            result = 31 * result + (slug != null ? slug.hashCode() : 0);
            return result;
        }
    }
}