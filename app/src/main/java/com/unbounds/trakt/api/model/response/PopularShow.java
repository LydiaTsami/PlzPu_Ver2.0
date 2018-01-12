package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Show;

/**
 * Created by lydts on 1/12/2018.
 */

public class PopularShow {

    private final long watchers;
    private final Show show;

    private PopularShow(final long watchers, final Show show) {
        this.watchers = watchers;
        this.show = show;
    }

    public long getWatchers() {
        return watchers;
    }

    public Show getShow() {
        return show;
    }
}
