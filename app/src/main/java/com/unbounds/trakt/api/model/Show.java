package com.unbounds.trakt.api.model;

/**
 * Created by maclir on 2015-11-21.
 */
public class Show {
    private final String title;
    private final long year;
    private final Images images;
    private final Ids ids;

    public Show(final String title, final long year, final Images images, final Ids ids) {
        this.title = title;
        this.year = year;
        this.images = images;
        this.ids = ids;
    }

    public Images getImages() {
        return images;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Show)) return false;

        final Show show = (Show) o;

        return !(ids != null ? !ids.equals(show.ids) : show.ids != null);

    }

    @Override
    public int hashCode() {
        return ids != null ? ids.hashCode() : 0;
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
        private final long trakt;
        private final String slug;
        private final long tvdb;
        private final String imdb;
        private final long tmdb;
        private final long tvrage;

        public Ids(final long trakt, final String slug, final long tvdb, final String imdb, final long tmdb, final long tvrage) {
            this.trakt = trakt;
            this.slug = slug;
            this.tvdb = tvdb;
            this.imdb = imdb;
            this.tmdb = tmdb;
            this.tvrage = tvrage;
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

        public long getTmdb() {
            return tmdb;
        }

        public long getTvrage() {
            return tvrage;
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
