package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Show;

public final class WatchedShow {
    private final long plays;
    private final String lastWatchedAt;
    private final Show show;
    private final ExtendedSeason seasons[];

    private WatchedShow(final long plays, final String lastWatchedAt, final Show show, final ExtendedSeason[] seasons) {
        this.plays = plays;
        this.lastWatchedAt = lastWatchedAt;
        this.show = show;
        this.seasons = seasons;
    }

    public long getPlays() {
        return plays;
    }

    public String getLastWatchedAt() {
        return lastWatchedAt;
    }

    public Show getShow() {
        return show;
    }

    public ExtendedSeason[] getSeasons() {
        return seasons;
    }
}
