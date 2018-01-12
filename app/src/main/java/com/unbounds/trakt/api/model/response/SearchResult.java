package com.unbounds.trakt.api.model.response;

import com.unbounds.trakt.api.model.Episode;
import com.unbounds.trakt.api.model.Movie;
import com.unbounds.trakt.api.model.Person;
import com.unbounds.trakt.api.model.Show;

/**
 * Created by lydts on 1/12/2018.
 */

public final class SearchResult {
    private final String type;
    private final double score;
    private final Show show;
    private final Movie movie;
    private final Episode episode;
    private final Person person;

    public SearchResult(String type, double score, Show show, Movie movie, Episode episode, Person person) {
        this.type = type;
        this.score = score;
        this.show = show;
        this.movie = movie;
        this.episode = episode;
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public double getScore() {
        return score;
    }

    public Show getShow() {
        return show;
    }

    public Movie getMovie() {
        return movie;
    }

    public Episode getEpisode() {
        return episode;
    }

    public Person getPerson() {
        return person;
    }
}