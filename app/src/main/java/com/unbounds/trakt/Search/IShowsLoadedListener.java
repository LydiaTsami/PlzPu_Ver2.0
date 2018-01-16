package com.unbounds.trakt.Search;

import com.unbounds.trakt.api.model.Show;

import java.util.ArrayList;

/**
 * Created by lydts on 1/16/2018.
 */

public interface IShowsLoadedListener {

    void onShowsLoaded(ArrayList<Show> movies, int scroll);
}
