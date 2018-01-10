package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Episode;

/**
 * Created by maclir on 2015-11-21.
 */
public class ExtendedEpisode extends Episode {
    private final long plays;
    private final boolean completed;
    private final String lastWatchedAt;

    private ExtendedEpisode(final long season, final long number, final String title, final Ids ids, final long plays, final boolean completed, final String lastWatchedAt) {
        super(season, number, title, ids);
        this.plays = plays;
        this.completed = completed;
        this.lastWatchedAt = lastWatchedAt;
    }

    public long getPlays() {
        return plays;
    }

    public String getLastWatchedAt() {
        return lastWatchedAt;
    }

    public boolean isCompleted() {
        return completed;
    }
}
