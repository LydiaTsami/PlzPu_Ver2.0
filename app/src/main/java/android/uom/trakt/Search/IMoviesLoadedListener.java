package android.uom.trakt.Search;

import android.uom.trakt.Movie;

import java.util.ArrayList;

/**
 * Created by lydts on 1/16/2018.
 */

public interface IMoviesLoadedListener {

    void onPopularMoviesLoaded(ArrayList<Movie> movies, int scroll);
}
