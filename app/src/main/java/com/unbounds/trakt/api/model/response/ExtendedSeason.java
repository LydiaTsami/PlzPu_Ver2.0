package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Season;

public class ExtendedSeason extends Season {
    private final long aired;
    private final long completed;

    private ExtendedSeason(final long number, final Ids ids, final ExtendedEpisode[] episodes, final long aired, final long completed) {
        super(number, ids, episodes);
        this.aired = aired;
        this.completed = completed;
    }


    public long getAired() {
        return aired;
    }

    public long getCompleted() {
        return completed;
    }
}
