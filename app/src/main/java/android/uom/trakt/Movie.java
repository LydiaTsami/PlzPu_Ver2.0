package android.uom.trakt;

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
    private String tagline,overview,trailer,homepage,genres;
    private int runtime;
    private double rating;


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

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
